import axios from "axios";
import config from "../config/index";
import { ElMessage } from "element-plus";

/**
 * 二次封装axios，将请求地址抽离到api文件夹中 
 * 好处：1.便于维护，2.便于修改，3.便于管理 4.便于查找 5.便于复用 6.便于测试
 */

const NETWORK_ERROR = "Network error, please try again later..";

const service = axios.create({
  baseURL: config.baseApi,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 30000,
});

service.interceptors.request.use(
    (req) => {
      const token = sessionStorage.getItem('token');
      console.log("Token:", token);
      if (token) {
        req.headers['Authorization'] = 'Bearer ' + token;
      }
      return req;
    },
    (error) => {
      return Promise.reject(error);
    }
);

service.interceptors.response.use((res) => {
  const { code, data, msg } = res.data;
  // 根据后端，视情况修改状态码
  if (code === 200) {
    return data;
  } else {
    ElMessage.error(msg || NETWORK_ERROR);
    return Promise.reject(msg || NETWORK_ERROR); // Reject the promise and display the error message 
    // why reject the promise? Because the return value of the request method is a promise, 
    // and the return value of the promise is the data returned by the server. 
    // If the request fails, the promise should be rejected, 
    // and the error message should be displayed in the catch method.
  }
});

// 封装的核心函数, 发送请求, 返回Promise
function request(options: any){
  options.method = options.method || 'get';
  // 处理get请求的参数
  if(options.method.toLowerCase() === 'get'){
    options.params = options.data;
  }
  // 对mock数据进行处理
  let isMock = config.mock;
  if(typeof options.mock !== 'undefined'){
    isMock = options.mock; // 如果options中有mock字段，就使用options中的mock字段
  }
  // 对线上（生产）环境的处理
  if(config.env === 'prod'){
    // 如果是生产环境, 就不使用mock数据, 避免线上环境出现问题
    service.defaults.baseURL = config.baseApi;
  } else {
    // 如果是开发环境，就使用mock数据
    service.defaults.baseURL = isMock ? config.mockApi : config.baseApi;
  }
  return service(options);

}

export default request;