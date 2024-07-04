import { $get, $post } from "../utils/request.ts";

export const $list = async () => {
  let res = await $get("/role/list");
  console.log(res);
  return res;
};