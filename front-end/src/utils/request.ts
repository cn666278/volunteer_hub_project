import axios from "axios";
import { baseURL_dev } from "../config/baseURL.ts";

/**
 * second encapsulation of axios, extract the request address to the api folder
 * benefits: 1. easy to maintain 2. easy to modify 3. easy to view 4. easy to manage 5. easy to test
 */

// create an axios instance
const instance = axios.create({
  baseURL: baseURL_dev,
  timeout: 30000, // Request timeout 30s
});

// add request interceptor
instance.interceptors.request.use(function (config) {
  // each request will add a token to the header
  console.log(config)
  config.headers['token'] = sessionStorage.getItem("token");
  return config;
}, function (error) {
  // Do something with request error
  return Promise.reject(error);
});

// add response interceptor
instance.interceptors.response.use(function (response) {
  // any 2xx status code will be caught here
  return response;
}, function (error) {
  // any non-2xx status code will be caught here
  return Promise.reject(error);
});

// get method
export const $get = async (url: string, params: object = {}) => {
  let { data } = await instance.get(url, { params });
  return data;
};

// post method
export const $post = async (url: string, params: object = {}) => {
  let {data} = await instance.post(url, params);
  return data;
};