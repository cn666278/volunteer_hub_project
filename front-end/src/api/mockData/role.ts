import Mock from "mockjs";

/*
 * using mock data locally
 */

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

let List: any[] = [];

List = [
  { roleId: 1, roleName: "admin" },
  { roleId: 2, roleName: "organizer" },
  { roleId: 3, roleName: "volunteer" },
];

export default {
  /**
   * getRoleList
   * @param
   * @return {{code: number, count: number, data: *[]}}
   */
  getRoleList: () => {
    // delete role with roleId = 0
    List = List.filter((role: any) => role.roleId !== 0);
    return {
      code: 200,
      data: List,
    };
  },

  /**
   * getSingleRole
   * @param id
   * @return {{code: number, data: *}}
   */
  getSingleRole: (config: any) => {
    const { roleId } = param2Obj(config.url);
    const role = List.find((role) => role.roleId === Number(roleId));
    return {
      code: 200,
      data: role,
    };
  },

  /**
   * Update Role
   * @param roleId, roleName
   * @return {{code: number, data: {message: string}}}
   */
  updateRole: (config: any) => {
    const { roleId, roleName } = JSON.parse(config.body);

    // check if roleName already exists
    const isRoleNameExists = List.some(
      (role: any) => role.roleName == roleName
    );
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
  },

  /**
   * Add Role
   * @param roleId, roleName
   * @return {{code: number, data: {message: string}}}
   */
  addRole: (config: any) => {
    const { roleName } = JSON.parse(config.body);
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
  },

  /**
   * Delete Role
   * @param roleId
   * @return {{code: number, data: {message: string}}}
   */
  deleteRole: (config: any) => {
    const { roleId } = JSON.parse(config.body);
    if (!roleId) {
      return {
        code: -999,
        data: {
          message: "roleId is required",
        }
      };
    } else {
      List = List.filter((role: any) => role.roleId !== roleId);
      return {
        code: 200,
        data: {
          message: "Delete Success",
        }
      };
    }
  },
};
