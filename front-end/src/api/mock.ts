import Mock from 'mockjs'
import homeApi from './mockData/home'
import roleApi from './mockData/role'
import userApi from './mockData/user'

// 拦截请求，返回本地模拟数据
Mock.mock('/home/getData', homeApi.getHomeData)

// 本地获取数据
// apifox/后端请求数据？
// user
Mock.mock(/\/admin\/user\/getUserList/, 'get', userApi.getUserList)
Mock.mock(/\/admin\/user\/getUserByLoginId/, 'get', userApi.getUserByLoginId)
Mock.mock(/\/admin\/user\/addUser/, 'post', userApi.addUser)
Mock.mock(/\/admin\/user\/updateUser/, 'post', userApi.updateUser)
Mock.mock(/\/admin\/user\/deleteUser/, 'post', userApi.deleteUser)
// role
Mock.mock(/\/admin\/role\/getRoleList/, 'get', roleApi.getRoleList)
Mock.mock(/\/admin\/role\/getSingleRole/, 'get', roleApi.getSingleRole)
Mock.mock(/\/admin\/role\/addRole/, 'post', roleApi.addRole)
Mock.mock(/\/admin\/role\/updateRole/, 'post', roleApi.updateRole)
Mock.mock(/\/admin\/role\/deleteRole/, 'post', roleApi.deleteRole)
