import axios from '../libs/axios.min.js';
import qs from '../libs/qs.js';

// test用户的key
const ACCESS_KEY = '5551bc9b-c872-4c87-8e51-66b431c604de';

// 服务器基础地址
const BASE_URL = 'http://localhost:8080/javawebproject';
// token相关
const TOKEN_KEY = 'teach_project_token';
// 保存token信息到本地
function saveToken(data) {
  if (data && data.token) {
    localStorage.setItem(TOKEN_KEY, data.token);
  }
}
// 读取本地保存的token
function loadToken() {
  let token = localStorage.getItem(TOKEN_KEY);
  return token ? token : '';
}

let ajax = {
  // 1：请求的地址，2：请求的参数（json格式）
  // 3：应答结果的处理（回调）函数，4：请求方式，可选,默认为POST
  send: (url, params, callback, method = 'POST') => {
    // 参数和地址处理
    let rdata = qs.stringify(params, { allowDots: true });
    // get请求是处理url
    let rurl = BASE_URL + url;
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
        token: loadToken(),
      },
    });
    // 请求的结果处理
    promise
      .then((resp) => {
        console.log('应答结果', resp);
        // 正常请求结果
        saveToken(resp.data);
        callback(resp.data);
      })
      .catch((err) => {
        console.error('请求错误', err);
        callback({ success: false, message: '请求异常' });
      });
  },
  // 上传文件的方法
  file: (file, fileinfo, callback) => {
    const url = BASE_URL + '/user/file/upload';
    // 文件上传必须使用FormData对象传递
    let formdata = new FormData();
    // 通过append添加数据
    formdata.append('fileinfo', fileinfo);
    formdata.append('file', file);
    // 发起文件版本的ajax
    let promise = axios({
      url: url,
      method: 'POST',
      data: formdata,
      headers: {
        token: loadToken(),
        // 文件上传的必须类型
        'Content-Type': 'multipart/form-data',
      },
    });

    // 请求的结果处理
    promise
      .then((resp) => {
        console.log('应答结果', resp);
        // 正常请求结果
        saveToken(resp.data);
        callback(resp.data);
      })
      .catch((err) => {
        console.error('请求错误', err);
        callback({ success: false, message: '请求异常' });
      });
  },
  // 获取文件的下载地址
  getFileUrl: (fid) => {
    return `${BASE_URL}/user/file/download?fid=${fid}`;
  },
  // 判定地址是否为上传的文件url地址
  isFileUrl: (url) => {
    return url.startsWith(BASE_URL + '/user/file/download?fid=');
  },
  // 获取下载地址中的fid值
  getUrlFid: (url) => {
    return url.replace(BASE_URL + '/user/file/download?fid=', '');
  },
};

export default ajax;

export { ajax as ajax, ACCESS_KEY as accessKey };

