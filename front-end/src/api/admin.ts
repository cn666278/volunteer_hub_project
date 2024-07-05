import { $get, $post } from "../utils/request.ts";
import { md5 } from "md5js";
import { ElNotification } from "element-plus";

export const $login = async (params: object | any) => {
  params.password = md5(
    md5(params.password, 32).split("").reverse().join(""),
    32
  );
  let res = await $post("login", params);
  console.log(res);
  const { code, data } = res;
  if (code === 200) {
    ElNotification({
      title: "Notification",
      message: data.message,
      type: "success",
    });
    // if login success, save token to sessionStorage
    sessionStorage.setItem("token", data.token);
    return res;
  } else {
    ElNotification({
      title: "Notification",
      message: data.message,
      type: "error",
    });
  }
  return res;
};

// get user info
export const $getUserInfo = async (params: object) => {
  // let res = await $get("/api/admin/getUserInfo", params); // from back-end
  let res = await $get("admin/getUserInfo", params);
  console.log(res);
  return res;
};

// mock data
let mockData = {
  count: 1,
  data: [
    {
      id: 1,
      loginId: "admin",
      username: "admin",
      password: "21232f297a57a5a743894a0e4a801fc3",
      phone: "10086",
      email: "123@gmail.com",
      photo: "https://s2.loli.net/2024/06/07/hjc65p2HRtKYFbG.png",
      role: { roleId: 1, roleName: "admin" },
      roleId: 1,
    },
    {
      id: 2,
      loginId: "organizer",
      username: "organizer",
      password: "21232f297a57a5a743894a0e4a801fc3",
      phone: "10086",
      email: "stu@163.edu.com",
      photo: "https://s2.loli.net/2024/06/07/fID2KtWhqJoMrAH.png",
      role: { roleId: 2, roleName: "organizer" },
      roleId: 2,
    },
    {
      id: 3,
      loginId: "volunteer",
      username: "volunteer",
      password: "21232f297a57a5a743894a0e4a801fc3",
      phone: "10086",
      email: "helloworld@cf.ac.uk",
      photo: "https://s2.loli.net/2024/06/07/avPj6oe9tDyOkmn.png",
      role: { roleId: 3, roleName: "volunteer" },
      roleId: 3,
    },
  ],
  pageIndex: 1,
  pageSize: 10,
};

/**
 * get user info by LoginId
 * @param
 * @return user
 */

export const $getUserByLoginId = async (loginId: string) => {
  const user = mockData.data.find((user) => user.loginId === loginId);
  return user;
};

/**
 * get user list
 * @param：{ roleId:（defualt: 0）, pageIndex：（default: 1）, pageSize:（default: 10） }
 * @return {{count: number, data: *[], pageIndex: number, pageSize: number}}
 */
export const $getUserList = async (params: {
  pageIndex: number;
  pageSize: number;
  roleId: number;
}) => {
  const { pageIndex, pageSize, roleId } = params;
  const start = (pageIndex - 1) * pageSize;
  const end = start + pageSize;
  let userList = mockData.data;
  // roleId = 0 : get all users
  if (roleId != 0) {
    userList = userList.filter((user) => user.roleId === roleId);
  }
  // Pagination
  userList = userList.slice(start, end);

  return {
    count: mockData.data.length,
    data: userList,
    pageIndex,
    pageSize,
  };
};

// Add user
export const $addUser = async (params: any) => {
  // let res = await $post("admin/addUser", params);
  console.log(params);
  // add id, avoid duplicate id
  params.id = mockData.data[mockData.data.length - 1].id + 1;
  // md5 password
  params.password = md5(
    md5(params.password, 32).split("").reverse().join(""),
    32
  );
  // check if roleName
  if (params.roleId == 1) {
    params.role = { roleId: 1, roleName: "admin" };
  } else if (params.roleId == 2) {
    params.role = { roleId: 2, roleName: "organizer" };
  } else {
    params.role = { roleId: 3, roleName: "volunteer" };
  }

  mockData.data.push({
    ...params,
  });
  console.log(mockData);
  return {
    code: 200,
    data: {
      message: "Add user successfully",
    },
  };
};

/**
 * Edit user
 * @param id, username, phone, email, photo, roleId
 * @return {{code: number, data: {message: string}}}
 */
export const $updateUser = async (params: {
  id: number | any;
  roleId: number | any;
  username: string;
  phone: string;
  email: string;
  photo: string;
}) => {
  // let res = await $post("admin/updateUser", params);
  const { id, username, phone, email, photo, roleId } = params;
  //check roleName
  let roleName = "";
  if (roleId == 1) {
    roleName = "admin";
  } else if (roleId == 2) {
    roleName = "organizer";
  } else {
    roleName = "volunteer";
  }
  mockData.data = mockData.data.map((user) => {
    if (user.id === id) {
      return {
        ...user,
        username,
        phone,
        email,
        photo,
        roleId,
        role: { roleId, roleName },
      };
    }
    return user;
  });
  return {
    code: 200,
    data: {
      message: "Edit user successfully",
    },
  };
};

/**
 * Delete user
 * @param id
 * @return {{code: number, data: {message: string}}}
 */
export const $deleteUser = async (id: number) => {
  // let res = await $post("admin/deleteUser", { id });
  mockData.data = mockData.data.filter((user) => user.id !== id);
  return {
    code: 200,
    data: {
      message: "Delete user successfully",
    },
  };
};
