import axios from "axios";
import config from "../config/index";
import { ElMessage } from "element-plus";

/**
 * Second encapsulation of axios, extracting the request address into the api folder.
 * Benefits:
 * 1. Easier to maintain
 * 2. Easier to modify
 * 3. Easier to manage
 * 4. Easier to find
 * 5. Easier to reuse
 * 6. Easier to test
 */

const NETWORK_ERROR = "Network error, please try again later..";

const service = axios.create({
  baseURL: config.baseApi,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 30000, // Set a timeout of 30 seconds for all requests
});

service.interceptors.request.use(
    (req) => {
      const token = sessionStorage.getItem('token');
      console.log("Token:", token);
      if (token) {
        req.headers['Authorization'] = 'Bearer ' + token; // Attach token to request headers if available
      }
      return req;
    },
    (error) => {
      return Promise.reject(error); // Reject the promise if there's an error in the request configuration
    }
);

service.interceptors.response.use((res) => {
  console.log("res start",res)
  if (res.config.responseType === 'text') {
    return res.data; // If the response type is text, return the response data as is
  }
  const { code, data, msg } = res.data;
  // Modify status code based on backend requirements
  if (code === 200) {
    return data;
  } else {
    ElMessage.error(msg || NETWORK_ERROR); // Display an error message if the request fails
    return Promise.reject(msg || NETWORK_ERROR);
    // Reject the promise and display the error message.
    // Why reject the promise? Because the return value of the request method is a promise,
    // and the return value of the promise is the data returned by the server.
    // If the request fails, the promise should be rejected,
    // and the error message should be handled in the catch method.
  }
});

// Core function of the encapsulation, sends a request and returns a Promise
function request(options: any) {
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

  // Handle production environment
  if (config.env === 'prod') {
    // In a production environment, do not use mock data to avoid issues in the live environment
    service.defaults.baseURL = config.baseApi;
  } else {
    // In a development environment, use mock data if isMock is true
    service.defaults.baseURL = isMock ? config.mockApi : config.baseApi;
  }

  return service(options); // Send the request using the configured axios instance
}

export default request;
