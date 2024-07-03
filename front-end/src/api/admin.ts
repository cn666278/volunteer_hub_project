import { $post } from "../utils/request.ts"
import { md5 } from "md5js"

export const $login = async (params: object | any) => {
    params.password = md5((md5(params.password,32).split('').reverse().join('')),32);
    let res = await $post("login", params);
    console.log(res);
}