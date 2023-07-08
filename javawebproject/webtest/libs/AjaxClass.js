import axios from './axios.min.js';
import qs from './qs.js';

/**
 * Ajax请求封装类
 */
class AjaxClass {
  constructor(baseUrl, tokenkey) {
    this.baseUrl = baseUrl;
    this.tokenkey = tokenkey;
  }
  /**
   * 保存服务器应答数据中的token
   * @param {*} data 服务器应答数据
   */
  savaToken(data) {
    if (data && data.token) {
      localStorage.setItem(this.tokenkey, data.token);
    }
  }
  /**
   * 获取本地存储的token信息
   * @returns token信息
   */
  loadToken() {
    let token = localStorage.getItem(this.tokenkey);
    return token ? token : '';
  }
  /**
   * 发送ajax请求
   * @param {*} url api地址
   * @param {*} params 请求参数
   * @param {*} callback 应答处理回调函数
   * @param {*} method 请求方式,可选参数，默认为POST
   */
  send(url, params, callback, method = 'POST') {
    let instance = this;
    // 参数和地址处理
    let rdata = qs.stringify(params, { allowDots: true });
    // get请求是处理url
    let rurl = instance.baseUrl + url;
    if ('get' == method.toLowerCase()) {
      rurl = rurl + '?' + rdata;
      rdata = '';
    }
    // axios的ajax请求
    let promise = axios({
      url: rurl,
      data: rdata,
      method: method,
      headers: {
        token: instance.loadToken(),
        ts: new Date().getTime(),
      },
    });
    // 请求的结果处理
    promise
      .then((resp) => {
        console.log('应答结果', resp);
        instance.savaToken(resp.data);
        // 正常请求结果
        callback(resp.data);
      })
      .catch((err) => {
        console.error('请求错误', err);
        callback({ success: false, message: '请求异常' });
      });
  }

  /**
   * 发送post请求
   * @param {*} url api地址
   * @param {*} params 请求参数
   * @param {*} callback 应答处理回调函数
   */
  post(url, params, callback) {
    this.send(url, params, callback, 'POST');
  }
  /**
   * 发送get请求
   * @param {*} url api地址
   * @param {*} params 请求参数
   * @param {*} callback 应答处理回调函数
   */
  get(url, params, callback) {
    this.send(url, params, callback, 'GET');
  }
  /**
   * 发送delete请求
   * @param {*} url api地址
   * @param {*} params 请求参数
   * @param {*} callback 应答处理回调函数
   */
  delete(url, callback) {
    this.send(url, {}, callback, 'DELETE');
  }

  /**
   * 发送json数据版本的ajax请求
   * @param {*} url api地址
   * @param {*} params 请求参数
   * @param {*} callback 应答处理回调函数
   * @param {*} method 请求方式,可选参数，默认为POST
   */
  sendJson(url, params, callback, method = 'POST') {
    let instance = this;
    // 参数和地址处理
    let rdata = qs.stringify(params, { allowDots: true });
    // get请求是处理url
    let rurl = instance.baseUrl + url;
    if ('get' == method.toLowerCase()) {
      rurl = rurl + '?' + rdata;
      params = '';
    }
    // axios的ajax请求
    let promise = axios({
      url: rurl,
      data: params,
      method: method,
      headers: {
        token: instance.loadToken(),
        ts: new Date().getTime(),
      },
    });
    // 请求的结果处理
    promise
      .then((resp) => {
        console.log('应答结果', resp);
        instance.savaToken(resp.data);
        // 正常请求结果
        callback(resp.data);
      })
      .catch((err) => {
        console.error('请求错误', err);
        callback({ success: false, message: '请求异常' });
      });
  }

  /**
   * 发送json数据post请求
   * @param {*} url api地址
   * @param {*} params 请求参数
   * @param {*} callback 应答处理回调函数
   */
  postJson(url, params, callback) {
    this.sendJson(url, params, callback, 'POST');
  }
}

export default AjaxClass;
export { AjaxClass as AjaxClass };
