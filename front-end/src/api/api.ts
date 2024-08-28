/**
 * The api module is used to encapsulate all the api interfaces of the project
 */
import request from "./request.ts";
export default {
  // login component
  login(params: any) {
    return request({
      url: "/login",
      method: "post",
      data: params,
      mock: false,
    });
  },
  // Retrieve logged-in user information
  getLoginUserInfo(params: any) {
    return request({
      url: "/getLoginUserInfo",
      method: "post",
      data: params,
      mock: false,
    });
  },

  /** Admin */

  // dashboard component: Retrieve table data
  getTableData(params: any) {
    return request({
      url: "/admin/home/getTableData",
      method: "get",
      data: params,
      mock: true, // Use mock data if true, otherwise use real data
    });
  },

  // Retrieve chart data for dashboard
  getEchartData() {
    return request({
      url: "/admin/getEchartData",
      method: "get",
      mock: false,
    });
  },

  // Retrieve event statistics
  getEventStatus() {
    return request({
      url: "/event/getEventStats",
      method: "get",
      mock: false,
    });
  },

  // Retrieve count data for the dashboard
  getCountData() {
    return request({
      url: "/admin/getCountData",
      method: "get",
      mock: false,
    });
  },

  // role component: Retrieve the list of roles
  getRoleList(params: any) {
    return request({
      url: "/admin/role/getRoleList",
      method: "get",
      data: params,
      mock: false,
    });
  },

  // Retrieve a single role by its ID
  getSingleRole(params: any) {
    return request({
      url: "/admin/role/getSingleRole",
      method: "get",
      data: params,
      mock: false,
    });
  },

  // Update an existing role
  updateRole(params: any) {
    return request({
      url: "/admin/role/updateRole",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Add a new role
  addRole(params: any) {
    return request({
      url: "/admin/role/addRole",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Delete a role
  deleteRole(params: any) {
    return request({
      url: "/admin/role/deleteRole",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // user component: Upload a user image
  uploadImg(params: any) {
    return request({
      url: "/admin/user/uploadImg",
      method: "post",
      data: params,
      mock: true,
    });
  },

  // Retrieve user information by user ID
  getUserById(params: any) {
    return request({
      url: "/admin/user/getUserById",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Retrieve a list of users
  getUserList(params: any) {
    return request({
      url: "/admin/user/getUserList",
      method: "get",
      data: params, // Example: data: {total: 0, page: 1}
      mock: false, // Fetch data from server, not using mock data
    });
  },

  // Add a new user
  addUser(params: any) {
    return request({
      url: "/admin/user/addUser",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Update existing user information
  updateUser(params: any) {
    return request({
      url: "/admin/user/updateUser",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Delete a user
  deleteUser(params: any) {
    return request({
      url: "/admin/user/deleteUser",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // event approval component: Retrieve the list of events
  getEventList(params: any) {
    return request({
      url: "/event/getEventList",
      method: "get",
      data: params,
      mock: false,
    });
  },

  // Retrieve events within a specific date range
  getEventsByDateRange(params: any) {
    return request({
      url: "/event/getEventsByDateRange",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Retrieve user information by organizer ID
  getUserInfoByOrganizerId(params: any) {
    return request({
      url: "/organizer/getUserByOrganizerId",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Approve an event
  approveEvent(params: any) {
    return request({
      url: "/event/approveEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Reject an event
  rejectEvent(params: any) {
    return request({
      url: "/event/rejectEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },

  /** Organizer */

  // event API: Retrieve events by specific date
  getEventsByDate(params: any) {
    return request({
      url: "/event/getEventsByDate",
      method: "get",
      data: params,
      mock: false,
    });
  },

  // Register a new event
  registerEvent(params: any) {
    return request({
      url: "/event/registerEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Retrieve events by organizer ID and filter criteria
  getEventsByOrganizerIdAndFilters(params: any) {
    return request({
      url: "/event/getEventsByOrganizerIdAndFilters",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Edit event information by event ID
  editEventById(params: any) {
    return request({
      url: "/event/editEventById",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Update volunteer status for an event
  updateVolunteerStatus(params: any) {
    return request({
      url: "/event/updateVolunteerStatus",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Retrieve messages by event ID
  getMessagesByEventId(params: any) {
    return request({
      url: "/messages/getMessagesByEventId",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Delete a message
  deleteMessage(params: any) {
    return request({
      url: '/messages/deleteMessage',
      method: 'post',
      data: params,
      mock: false,
    });
  },

  /** volunteer API */

  // Retrieve volunteers by event ID
  getVolunteersByEventId(params: any) {
    return request({
      url: "/volunteer/getVolunteersByEventId",
      method: "get",
      data: params,
      mock: false,
    });
  },

  // Submit a comment by a volunteer
  submitComment(params: any) {
    return request({
      url: "/volunteer/submitComment",
      method: "post",
      data: params,
      mock: false,
    });
  },

  /** store API */

  // Retrieve items from the reward store
  getItems(params: any) {
    return request({
      url: "/rewardstore/items",
      method: "get",
      data: params,
      mock: false,
    });
  },

  // Redeem an item from the reward store
  redeemItem(params: any) {
    return request({
      url: "/rewardstore/redeem",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Add a new item to the reward store
  addRewardStoreItem(params: any) {
    return request({
      url: '/rewardstore/add',
      method: 'post',
      data: params,
      mock: false,
    });
  },

  // Update an existing item in the reward store
  updateRewardStoreItem(params: any) {
    return request({
      url: '/rewardstore/update',
      method: 'put',
      data: params,
      mock: false,
    });
  },

  // Delete an item from the reward store
  deleteRewardStoreItem(params: any) {
    return request({
      url: `/rewardstore/delete/${params.id}`,
      method: 'delete',
      mock: false,
    });
  },

  /** file API */

  // Upload a file
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

  // Upload a file for a volunteer
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

  // Upload a profile picture (avatar) for a volunteer
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

  // Retrieve files by file ID
  getfiles(params: any) {
    return request({
      url: `/files/${params.id}`,
      method: 'get',
      responseType: 'text',
      mock: false,
    });
  },

  // Retrieve all events
  getAllEvents(params: any) {
    return request({
      url: "/event/getAllEvents",
      method: "get",
      data: params,
      mock: false,
    });
  },

  // Retrieve the latest events
  getLatestEvents() {
    return request({
      url: "/event/getLatestEvents",
      method: "get",
      mock: false,
    });
  },

  // Retrieve an event by its ID
  getEventById(params: any) {
    return request({
      url: `/event/${params.id}`,
      method: 'get',
      mock: false,
    });
  },

  // Update user profile
  updateUserProfile(params: any) {
    return request({
      url: "/user/updateProfile",
      method: "put",
      data: params,
      mock: false,
    });
  },

  // Retrieve ratings by volunteer ID
  getRatingsByVolunteerId(params: any) {
    return request({
      url: `/user/${params.volunteerId}/ratings`,
      method: "get",
      mock: false,
    });
  },

  // Retrieve volunteer information by volunteer ID
  getVolunteerInfoByVolunteerId(params: any) {
    return request({
      url: `/user/${params.volunteerId}/volunteerinfo`,
      method: "get",
      mock: false,
    });
  },

  // Subscribe to an event
  subscribeForEvent(params: any) {
    return request({
      url: "/event/subscribeForEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Retrieve subscribed events
  getSubscribedEvents(params: any) {
    return request({
      url: "/event/getSubscribedEvents",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Register for an event
  registerForEvent(params: any) {
    return request({
      url: "/event/registerForEvent",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Retrieve participated events
  getParticipatedEvents(params: any) {
    return request({
      url: "/event/getParticipatedEvents",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Retrieve credentials by volunteer ID
  getCredentialsByVolunteerId(params: any) {
    return request({
      url: "/volunteer/getCredentialsByVolunteerId",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Delete a credential
  deleteCredential(params: any) {
    return request({
      url: '/volunteer/deleteCredential',
      method: 'post',
      data: params,
      mock: false,
    });
  },

  // Update a credential
  updateCredential(params: any) {
    return request({
      url: '/volunteer/updateCredential',
      method: 'post',
      data: params,
      mock: false,
    });
  },

  // Retrieve volunteer information by user ID
  getVolunteerByUserId(params: any) {
    return request({
      url: `/volunteer/getVolunteerByUserId/${params.userId}`,
      method: "get",
      mock: false,
    });
  },

  // Retrieve roles associated with an event by event ID
  getRolesByEventId(params: any) {
    return request({
      url: "/event/getRolesByEventId",
      method: "post",
      data: params,
      mock: false,
    });
  },

  // Change user password
  changeUserPassword(params: any) {
    return request({
      url: "/user/changePassword",
      method: "post",
      data: params,
      mock: false,
    });
  }
}
