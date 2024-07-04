import { $get, $post } from "../utils/request.ts";
import Mock from 'mockjs'

/*
* using data from apifox/backend
*/ 

// role list
export const $list = async () => {
  let res = await $get("/role/list");
  console.log(res);
  return res;
};

// addRole
export const $addRole = async (params: object) => {
    let res = await $post("/role/addRole",   params);
    return res;
  };

/* 
* using mock data locally
*/

function param2Obj(url: string) {
  const search = url.split('?')[1]
  if (!search) {
    return {}
  }
  return JSON.parse(
    '{"' +
    decodeURIComponent(search)
      .replace(/"/g, '\\"')
      .replace(/&/g, '","')
      .replace(/=/g, '":"') +
    '"}'
  )
}

let List: any[] = []
const count = 200

for (let i = 0; i < count; i++) {
  List.push(
    Mock.mock({
      id: Mock.Random.guid(),
      name: Mock.Random.cname(),
      addr: Mock.mock('@county(true)'), 
      'age|18-60': 1,
      birth: Mock.Random.date(),
      sex: Mock.Random.integer(0, 1)
    })
  )
}

export default {
  /**
   * getUserList
   * @param name, page, limit
   * @return {{code: number, count: number, data: *[]}}
   */
  getUserList: (config: { url: any; }) => {
    const { name, page = 1, limit = 20 } = param2Obj(config.url)
    const mockList = List.filter(user => {
      if (name && user.name.indexOf(name) === -1 && user.addr.indexOf(name) === -1) return false
      return true
    })
    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))
    return {
      code: 200,
      data: {
        list: pageList,
        count: mockList.length,
      }
    }
  },
  /**
   * Create User
   * @param name, addr, age, birth, sex
   * @return {{code: number, data: {message: string}}}
   */
  createUser: (config: { body: string; }) => {
    const { name, addr, age, birth, sex } = JSON.parse(config.body)
    console.log(JSON.parse(config.body))
    List.unshift({
      id: Mock.Random.guid(),
      name: name,
      addr: addr,
      age: age,
      birth: birth,
      sex: sex
    })
    return {
      code: 200,
      data: {
        message: 'Add Success'
      }
    }
  },
  /**
   * Delete User
   * @param id
   * @return {*}
   */
  deleteUser: (config: { url: any; }) => {
    const { id } = param2Obj(config.url)
    if (!id) {
      return {
        code: -999,
        message: 'Parameter id is required'
      }
    } else {
      List = List.filter(u => u.id !== id)
      return {
        code: 200,
        message: 'Delete Success'
      }
    }
  },
  /**
   * Patch Remove User
   * @param config
   * @return {{code: number, data: {message: string}}}
   */
  batchremove: (config: { url: any; }) => {
    let { ids } = param2Obj(config.url)
    ids = ids.split(',')
    List = List.filter(u => !ids.includes(u.id))
    return {
      code: 20000,
      data: {
        message: 'Batch Remove Success'
      }
    }
  },
  /**
   * Edit User
   * @param id, name, addr, age, birth, sex
   * @return {{code: number, data: {message: string}}}
   */
  updateUser: (config: { body: string; }) => {
    const { id, name, addr, age, birth, sex } = JSON.parse(config.body)
    const sex_num = parseInt(sex)
    List.some(u => {
      if (u.id === id) {
        u.name = name
        u.addr = addr
        u.age = age
        u.birth = birth
        u.sex = sex_num
        return true
      }
    })
    return {
      code: 200,
      data: {
        message: 'Edit Success'
      }
    }
  }
}