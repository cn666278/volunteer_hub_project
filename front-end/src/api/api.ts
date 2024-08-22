/**
 * The api module is used to encapsulate all the api interfaces of the project
 */
import request from "./request.ts";
export default {
  // login组件
  login(params: any) {
    return request({
      url: "/login",
      method: "post",
      data: params,
      mock: false,
    });
  },
  getLoginUserInfo(params: any) {
    return request({
      url: "/getLoginUserInfo",
      method: "post",
      data: params,
      mock: false,
    });
  },
  /** Admin */
  //  dashboard 组件
  getTableData(params: any) {
    return request({
      url: "/admin/home/getTableData",
      method: "get",
      data: params,
      mock: true, // 是否使用mock数据, true则使用apifox/后端数据，false则使用本地数据
    });
  },
  getEchartData() {
    return request({
      url: "/admin/getEchartData",
      method: "get",
      mock: false,
    });
  },
  getEventStatus(){
    return request({
      url: "/event/getEventStats",
      method: "get",
      mock: false,
    });
  },
  getCountData(){
    return request({
      url: "/admin/getCountData",
      method: "get",
      mock: false,
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
  // event approve 组件
  getEventList(params: any) {
    return request({
      url: "/event/getEventList",
      method: "get",
      data: params,
      mock: false,
    });
  },
  getEventsByDateRange(params: any) {
      return request({
      url: "/event/getEventsByDateRange",
      method: "post",
      data: params,
      mock: false,
      });
  },
  getUserInfoByOrganizerId(params: any) {
    return request({
      url: "/organizer/getUserByOrganizerId",
      method: "post",
      data: params,
      mock: false,
    });
  },
  approveEvent(params: any) {
    return request({
      url: "/event/approveEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },
  rejectEvent(params: any) {
    return request({
      url: "/event/rejectEvent",
      method: "post",
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
  deleteMessage(params: any) {
    return request({
      url: '/messages/deleteMessage',
      method: 'post',
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

  addRewardStoreItem(params: any) {
    return request({
      url: '/rewardstore/add',
      method: 'post',
      data: params,
      mock: false,
    });
  },


  updateRewardStoreItem(params: any) {
    return request({
      url: '/rewardstore/update',
      method: 'put',
      data: params,
      mock: false,
    });
  },


  deleteRewardStoreItem(params: any) {
    return request({
      url: `/rewardstore/delete/${params.id}`,
      method: 'delete',
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
  uploadFileForVolunteer(params: any) {
    return request({
      url: "/uploadForVolunteer",
      method: "post",
      data: params,
      mock: false,
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  },
  uploadAvatorForVolunteer(params: any) {
    return request({
      url: "/uploadAvatorForVolunteer",
      method: "post",
      data: params,
      mock: false,
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
  },
  getfiles(params: any) {
    return request({
      url: `/files/${params.id}`,
      method: 'get',
      responseType: 'text',
      mock: false,
    });
  },


    //Hao Li, Adding, deleting, and checking events
  getAllEvents(params: any) {
    return request({
      url: "/event/getAllEvents",
      method: "get",
      data: params,
      mock: false,
    });
  },

  getLatestEvents() {
    return request({
      url: "/event/getLatestEvents",
      method: "get",
      mock: false,
    });
  },

  getEventById(params: any) {
    return request({
      url: `/event/${params.id}`,
      method: 'get',
      mock: false,
    });
  },

  // getUserById(params: any) {
  //   return request({
  //     url: `/user/${params.id}`,
  //     method: "get",
  //     mock: false,
  //   });
  // },

  updateUserProfile(params: any) {
    return request({
      url: "/user/updateProfile",
      method: "put",
      data: params,
      mock: false,
    });
  },

  getRatingsByVolunteerId(params: any) {
    return request({
      url: `/user/${params.volunteerId}/ratings`,
      method: "get",
      mock: false,
    });
  },

  getVolunteerInfoByVolunteerId(params: any) {
    return request({
      url: `/user/${params.volunteerId}/volunteerinfo`,
      method: "get",
      mock: false,
    });
  },

  subscribeForEvent(params: any) {
    return request({
      url: "/event/subscribeForEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },

  getSubscribedEvents(params: any) {
    return request({
      url: "/event/getSubscribedEvents",
      method: "post",
      data: params,
      mock: false,
    });
  },

  registerForEvent(params: any) {
    return request({
      url: "/event/registerForEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },

  getParticipatedEvents(params: any) {
    return request({
      url: "/event/getParticipatedEvents",
      method: "post",
      data: params,
      mock: false,
    });
  },

  getCredentialsByVolunteerId(params: any) {
    return request({
      url: "/volunteer/getCredentialsByVolunteerId",
      method: "post",
      data: params,
      mock: false,
    });
  },

  deleteCredential(params: any) {
    return request({
      url: '/volunteer/deleteCredential',
      method: 'post',
      data: params,
      mock: false,
    });
  },

  updateCredential(params: any) {
    return request({
      url: '/volunteer/updateCredential',
      method: 'post',
      data: params,
      mock: false,
    });
  },
  getVolunteerByUserId(params: any) {
    return request({
      url: `/volunteer/getVolunteerByUserId/${params.userId}`,
      method: "get",
      mock: false,
    });
  },

  getRolesByEventId(params: any) {
    return request({
      url: "/event/getRolesByEventId",
      method: "post",
      data: params,
      mock: false,
    });
  },
  // navigateToEvent(params: any) {
  //   router.push({ name: 'EventDetail', params: { id: params.id } });
  // },

  changeUserPassword(params: any) {
    return request({
      url: "/user/changePassword",
      method: "post",
      data: params,
      mock: false,
    });
  }





}

