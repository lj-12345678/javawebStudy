import ajax from '../libs/ajax.js';

let preResult = document.getElementById('preResult');
ajax.send('/get.token', { test: '请求参数' }, (data) => {
  preResult.append(JSON.stringify(data, null, 2));
});
