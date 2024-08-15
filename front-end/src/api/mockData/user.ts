import { md5 } from "md5js";

// mock data for user

// get请求从config.url获取参数，post从config.body中获取参数
function param2Obj(url: string) {
  const search = url.split("?")[1];
  if (!search) {
    return {};
  }
  return JSON.parse(
    '{"' +
      decodeURIComponent(search)
        .replace(/"/g, '\\"')
        .replace(/&/g, '","')
        .replace(/=/g, '":"') +
      '"}'
  );
}

let mockData = {
  count: 1,
  data: [
    {
      id: 1,
      loginId: "admin",
      username: "admin",
      phone: "10086",
      email: "123@gmail.com",
      photo: "https://s2.loli.net/2024/06/07/hjc65p2HRtKYFbG.png",
      role: { roleId: 1, roleName: "admin" },
    },
    {
      id: 2,
      loginId: "organizer",
      username: "organizer",
      phone: "10086",
      email: "stu@163.edu.com",
      photo: "https://s2.loli.net/2024/06/07/fID2KtWhqJoMrAH.png",
      role: { roleId: 2, roleName: "organizer" },
    },
    {
      id: 3,
      loginId: "volunteer",
      username: "volunteer",
      phone: "10086",
      email: "helloworld@cf.ac.uk",
      photo: "https://s2.loli.net/2024/06/07/avPj6oe9tDyOkmn.png",
      role: { roleId: 3, roleName: "volunteer" },
    },
  ],
  pageIndex: 1,
  pageSize: 10,
};

// import { mockData } from "./mockData";
export default {
  /**
   * get user info by Id
   * @param 
   * @return user
   */
  getUserById: (config: any) => {
    // 为什么还有body? get方法？
    // console.log(config)
    const { id } = JSON.parse(config.body);
    const user = mockData.data.find((user) => user.id === Number(id));
    return {
      code: 200,
      data: user,
    };
  },

  /**
   * get user list
   * @param：{ roleId:（defualt: 0）, pageIndex：（default: 1）, pageSize:（default: 10） }
   * @return {{count: number, data: *[], pageIndex: number, pageSize: number}}
   */
  getUserList: () => {
    let userList = mockData.data;
    // const { pageIndex, pageSize, roleId } = params;
    // const start = (pageIndex - 1) * pageSize;
    // const end = start + pageSize;
    // roleId = 0 : get all users
    // if (roleId != 0) {
    //   userList = userList.filter((user) => user.roleId === roleId);
    // }
    // // Pagination
    // userList = userList.slice(start, end);
    return {
      code: 200,
      data: {
        count: userList.length,
        list: userList,
        // pageIndex,
        // pageSize,
      },
    };
  },

  // Add user
  // addUser: (params: any) => {
  //   let { id, role, roleId } = JSON.parse(params.body);
  //   // add id, avoid duplicate id
  //   id = mockData.data[mockData.data.length - 1].id + 1;
  //   // md5 password
  //   // params.password = md5(
  //   //   md5(params.password, 32).split("").reverse().join(""),
  //   //   32
  //   // );
  //   // check if roleName
  //   if (roleId == 1) {
  //     role = { roleId: 1, roleName: "admin" };
  //   } else if (roleId == 2) {
  //     role = { roleId: 2, roleName: "organizer" };
  //   } else {
  //     role = { roleId: 3, roleName: "volunteer" };
  //   }
  //
  //   mockData.data.push({
  //     id: id,
  //     role: role,
  //     ...JSON.parse(params.body),
  //   });
  //   return {
  //     code: 200,
  //     data: {
  //       message: "Add user successfully",
  //     },
  //   };
  // },

  /**
   * Edit user
   * @param id, username, phone, email, photo, roleId
   * @return {{code: number, data: {message: string}}}
   */
  // updateUser: (params: any) => {
  //   const { id, username, phone, email, photo, roleId } = JSON.parse(
  //     params.body
  //   );
  //   //check roleName
  //   let roleName = "";
  //   if (roleId == 1) {
  //     roleName = "admin";
  //   } else if (roleId == 2) {
  //     roleName = "organizer";
  //   } else {
  //     roleName = "volunteer";
  //   }
  //   mockData.data = mockData.data.map((user) => {
  //     if (user.id === id) {
  //       return {
  //         ...user,
  //         username,
  //         phone,
  //         email,
  //         photo,
  //         roleId,
  //         role: { roleId, roleName },
  //       };
  //     }
  //     return user;
  //   });
  //   return {
  //     code: 200,
  //     data: {
  //       message: "Edit user successfully",
  //     },
  //   };
  // },

  /**
   * Delete user
   * @param id
   * @return {{code: number, data: {message: string}}}
   */
  // deleteUser: (params: any) => {
  //   const { id } = JSON.parse(params.body);
  //   mockData.data = mockData.data.filter((user) => user.id !== id);
  //   return {
  //     code: 200,
  //     data: {
  //       message: "Delete user successfully",
  //     },
  //   };
  // },
};
