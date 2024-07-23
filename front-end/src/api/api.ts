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
      url: "/getLoginUserInfo",
      method: "post",
      data: params,
      mock: true,
    });
  },
  /** Admin */
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
      method: "post",
      data: params,
      mock: false,
    });
  },
  // user组件
  uploadImg(params: any) {
    return request({
      url: "/admin/user/uploadImg",
      method: "post",
      data: params,
      mock: true,
    });
  },
  getUserById(params: any) {
    return request({
      url: "/admin/user/getUserById",
      method: "post",
      data: params,
      mock: false,
    });
  },
  getUserList(params: any) {
    return request({
      url: "/admin/user/getUserList",
      method: "get",
      data: params, // data: {total: 0, page: 1}
      mock: false, // 本地获取数据，不使用mock(apifox)数据
    });
  },
  addUser(params: any) {
    return request({
      url: "/admin/user/addUser",
      method: "post",
      data: params,
      mock: false,
    });
  },
  updateUser(params: any) {
    return request({
      url: "/admin/user/updateUser",
      method: "post",
      data: params,
      mock: false,
    });
  },
  deleteUser(params: any) {
    return request({
      url: "/admin/user/deleteUser",
      method: "post", 
      data: params,
      mock: false,
    });
  },
  // list组件
  getEventList(params: any) {
    return request({
      url: "/admin/list/evnets",
      method: "get",
      data: params,
      mock: true,
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
  /** Organizer */
  /**
   * event api
   */
  getEventsByDate(params: any) {
    return request({
      url: "/event/getEventsByDate",
      method: "get",
      data: params,
      mock: false,
    });
  },
  registerEvent(params: any) {
    return request({
      url: "/event/registerEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },
  getEventsByOrganizerIdAndFilters(params: any) {
    return request({
      url: "/event/getEventsByOrganizerIdAndFilters",
      method: "post",
      data: params,
      mock: false,
    });
  },
  editEventById(params: any) {
    return request({
      url: "/event/editEventById",
      method: "post",
      data: params,
      mock: false,
    });
  },
  updateVolunteerStatus(params: any) {
    return request({
      url: "/event/updateVolunteerStatus",
      method: "post",
      data: params,
      mock: false,
    });
  },
  getMessagesByEventId(params: any) {
    return request({
      url: "/messages/getMessagesByEventId",
      method: "post",
      data: params,
      mock: false,
    });
  },
  /**
   * volunteer api
   */
  getVolunteersByEventId(params: any) {
    return request({
      url: "/volunteer/getVolunteersByEventId",
      method: "get",
      data: params,
      mock: false,
    });
  },
  submitComment(params: any) {
    return request({
      url: "/volunteer/submitComment",
      method: "post",
      data: params,
      mock: false,
    });
  },
  /**
   * store api
   */
  getItems(params: any) {
    return request({
      url: "/rewardstore/items",
      method: "get",
      data: params,
      mock: false,
    });
  },
  redeemItem(params: any) {
    return request({
      url: "/rewardstore/redeem",
      method: "post",
      data: params,
      mock: false,
    });
  },
  /**
   * file api
   */
  uploadFile(params: any) {
    return request({
      url: "/upload",
      method: "post",
      data: params,
      mock: false,
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  },
  getfiles(params) {
    return request({
      url: `/files/${params.id}`,
      method: 'get',
      responseType: 'text',
      mock: false,
    });
  }
};
