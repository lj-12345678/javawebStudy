import { server } from '../libs/server.js';

let preInfo = document.getElementById('preInfo');
let btnUser = document.getElementById('btnUser');
let btnLogout = document.getElementById('btnLogout');
let txtUsername = document.getElementById('txtUsername');
let txtPassword = document.getElementById('txtPassword');
let btnLogin = document.getElementById('btnLogin');

//获取用户
btnUser.addEventListener('click', () => {
  server.post('/get.token', {}, (data) => {
    preInfo.innerHTML = '用户信息：' + JSON.stringify(data);
  });
});

//安全退出
btnLogout.addEventListener('click', () => {
  server.post('/logout.token', {}, (data) => {
    preInfo.innerHTML = '用户安全退出信息：' + JSON.stringify(data);
  });
});

//用户登录
btnLogin.addEventListener('click', () => {
  let info = {
    username: txtUsername.value,
    password: txtPassword.value,
  };
  server.post('/login.token', info, (data) => {
    preInfo.innerHTML = '用户登录信息：' + JSON.stringify(data);
  });
});
