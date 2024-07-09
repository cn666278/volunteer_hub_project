/**
 * 整个项目的api的管理文件
 */
import request from "./request.ts";
export default {
  // login组件
  login(params: any) {
    return request({
      url: "/login",
      method: "post",
      data: params,
      mock: true,
    });
  },
  getLoginUserInfo(params: any) {
    return request({
      url: "/admin/getUserInfo",
      method: "post",
      data: params,
      mock: true,
    });
  },
  // home组件，左侧表格数据获取
  getTableData(params: any) {
    return request({
      url: "/admin/home/getTableData",
      method: "get",
      data: params,
      mock: true, // 是否使用mock数据, true则使用apifox/后端数据，false则使用本地数据
    });
  },
  getCountData() {
    return request({
      url: "/admin/home/getCountData",
      method: "get",
      mock: true,
    });
  },
  getEchartData() {
    return request({
      url: "/admin/home/getEchartData",
      method: "get",
      mock: true,
    });
  },
  // role组件
  getRoleList(params: any) {
    return request({
      url: "/admin/role/getRoleList",
      method: "get",
      data: params,
      mock: false,
    });
  },
  getSingleRole(params: any) {
    return request({
      url: "/admin/role/getSingleRole",
      method: "get",
      data: params,
      mock: false,
    });
  },
  updateRole(params: any) {
    return request({
      url: "/admin/role/updateRole",
      method: "post",
      data: params,
      mock: false,
    });
  },
  addRole(params: any) {
    return request({
      url: "/admin/role/addRole",
      method: "post",
      data: params,
      mock: false,
    });
  },
  deleteRole(params: any) {
    return request({
      url: "/admin/role/deleteRole",
      method: "get",
      data: params,
      mock: false,
    });
  },
  // user组件
  getUserData(params: any) {
    return request({
      url: "/admin/user/getUser",
      method: "get",
      data: params, // data: {total: 0, page: 1}
      mock: false, // 本地获取数据，不使用mock(apifox)数据
    });
  },
  addUser(params: any) {
    return request({
      url: "/admin/user/add",
      method: "post",
      data: params,
      mock: false,
    });
  },
  editUser(params: any) {
    return request({
      url: "/admin/user/edit",
      method: "post",
      data: params,
      mock: false,
    });
  },
  deleteUser(params: any) {
    return request({
      url: "/admin/user/delete",
      method: "get", // 因为删除请求不需要传递参数，所以使用get方法
      data: params,
      mock: false,
    });
  },
  // 根据用户名不同，返回不一样的菜单列表
  //   getMenu(prams: any){
  //     return request({
  //       url: "/permission/getMenu",
  //       method: "post",
  //       data: prams,
  //       mock: false,
  //     });
  //   }
};
