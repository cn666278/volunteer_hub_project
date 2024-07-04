// /**
//  * 整个项目的api的管理文件(暂未启用！！)
//  */
// import request from "./request.ts";
// export default {
//   // home组件，左侧表格数据获取
//   getTableData(params: any) {
//     return request({
//       url: "/home/getTableData",
//       method: "get",
//       data: params,
//       mock: true, // 是否使用mock数据, true则使用apifox数据，false则使用本地数据
//     });
//   },
//   getCountData(){
//     return request({
//       url: "/home/getCountData",
//       method: "get",
//       mock: true,
//     });
//   },
//   getEchartData() {
//     return request({
//       url: "/home/getEchartData",
//       method: "get",
//       mock: true,
//     });
//   },
//   getUserData(params: any) {
//     return request({
//       url: "/user/getUser",
//       method: "get",
//       data: params, // data: {total: 0, page: 1}
//       mock: false, // 本地获取数据，不使用mock(apifox)数据
//     });
//   },
//   addUser(params: any){
//     return request({
//       url: "/user/add",
//       method: "post",
//       data: params,
//       mock: false,
//     });
//   },
//   editUser(params: any){
//     return request({
//       url: "/user/edit",
//       method: "post",
//       data: params,
//       mock: false,
//     });
//   },
//   deleteUser(params: any){
//     return request({
//       url: "/user/delete",
//       method: "get", // 因为删除请求不需要传递参数，所以使用get方法
//       data: params,
//       mock: false,
//     });
//   },
//   // 根据用户名不同，返回不一样的菜单列表
//   getMenu(prams: any){
//     return request({
//       url: "/permission/getMenu",
//       method: "post",
//       data: prams,
//       mock: false,
//     });
//   }
// };
