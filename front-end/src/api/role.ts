import { $get, $post } from "../utils/request.ts";

// role list
export const $list = async () => {
  let res = await $get("/role/list");
  console.log(res);
  return res;
};

// addRole
export const $addRole = async (params: object) => {
    let res = await $post("/role/addRole", params);
    return res;
  };