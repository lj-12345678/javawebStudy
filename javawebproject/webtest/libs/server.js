import { AjaxClass } from './AjaxClass.js';

let server = new AjaxClass(
  'http://localhost:8080/javawebproject',
  'javaweb_token_key'
);

export default server;
export { server as server };
