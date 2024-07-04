import { $get, $post } from "../../utils/request.ts";
import Mock from "mockjs";

/*
 * using data from apifox/backend
 */

// role list
// export const $list = async () => {
//   let res = await $get("/role/list");
//   console.log(res);
//   return res;
// };

// addRole
// export const $addRole = async (params: object) => {
//   let res = await $post("/role/addRole", params);
//   return res;
// };

/*
 * using mock data locally
 */

let List: any[] = [];
// const count = 200

// for (let i = 0; i < count; i++) {
//   List.push(
//     Mock.mock({
//       id: Mock.Random.guid(),
//       name: Mock.Random.cname(),
//       addr: Mock.mock('@county(true)'),
//       'age|18-60': 1,
//       birth: Mock.Random.date(),
//       sex: Mock.Random.integer(0, 1)
//     })
//   )
// }

List = [
  { roleId: 1, roleName: "admin" },
  { roleId: 2, roleName: "organizer" },
  { roleId: 3, roleName: "volunteer" },
];

/**
 * getRoleList
 * @param
 * @return {{code: number, count: number, data: *[]}}
 */
export const $getRoleList = () => {
  // let res = await $get("/role/list");
  // delete role with roleId = 0
  List = List.filter((role: any) => role.roleId !== 0);
  return List;
};

// getRoleList
export const $getSingleRole = async (id: number) => {
  // let res = await $get("/role/getSingleRole", params);
  const currentUser = List.find((user: any) => user.roleId === id);
  console.log(currentUser);
  return currentUser;
};

/**
 * Update Role
 * @param roleId, roleName
 * @return {{code: number, data: {message: string}}}
 */
export const $updateRole = async (params: {
  roleId: any;
  roleName: string;
}) => {
  // let res = await $post("/role/updateRole", params);
  const { roleId, roleName } = params;

  // check if roleName already exists
  const isRoleNameExists = List.some((role: any) => role.roleName == roleName);
  if (isRoleNameExists) {
    return {
      code: -1,
      data: {
        message: "Update failed, roleName already exists",
      },
    };
  }
  List = List.map((role: any) => {
    if (role.roleId === roleId) {
      return {
        roleId: roleId, 
        roleName: roleName, // update roleName
      };
    }
    return role;
  });
  return {
    code: 200,
    data: {
      message: "Edit Success",
    },
  };
};

/**
 * Add Role
 * @param roleId, roleName
 * @return {{code: number, data: {message: string}}}
 */
export const $addRole = async (params: { roleId: any; roleName: string }) => {
  // let res = await $post("/role/addRole", params);
  // return res;
  const { roleName } = params;
  // get max roleId in List, avoid duplicate roleId
  const maxRoleId = List.reduce((maxId: number, role: any) => {
    return Math.max(maxId, role.roleId);
  }, 0);
  const newRoleId = maxRoleId + 1;
  // add new role to List tail, if need to add to head, use unshift
  List.push({
    roleId: newRoleId,
    roleName: roleName,
  });
  return {
    code: 200,
    data: {
      message: "Add Success",
    },
  };
};

/**
 * Delete Role
 * @param id
 * @return {*}
 */
export const $deleteRole = async (id: number) => {
  // let res = await $post("/role/deleteRole", params);
  if (!id) {
    return {
      code: -999,
      message: "roleId is required",
    };
  } else {
    List = List.filter((role: any) => role.roleId !== id);
    return {
      code: 200,
      message: "Delete Success",
    };
  }
};

/**
 * Patch Remove
 * @param config
 * @return {{code: number, data: {message: string}}}
 */
export const $batchremove = async (ids: string) => {
  // let res = await $post("/role/batchremove", params);
  ids = ids.split(",")[0];
  List = List.filter((role: any) => !ids.includes(role.roleId));
  return {
    code: 20000,
    data: {
      message: "Batch Remove Success",
    },
  };
};
