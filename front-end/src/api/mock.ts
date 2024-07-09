import Mock from 'mockjs'
import homeApi from './mockData/home'
import roleApi from './mockData/role'

// 拦截请求，返回本地模拟数据
Mock.mock('/home/getData', homeApi.getHomeData)

// 本地获取数据
// apifox/后端请求数据？
// Mock.mock(/user\/getUser/, 'get', userApi.getUserList) // 使用正则匹配，拦截/user/getUser请求（\是转义字符）
// Mock.mock(/user\/add/, 'post', userApi.createUser)
// Mock.mock(/user\/edit/, 'post', userApi.updateUser)
// Mock.mock(/user\/delete/, 'get', userApi.deleteUser)