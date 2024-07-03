import { $post } from "../utils/request.ts"

export const $login = async (params: object) => {
    let res = await $post("login", params);
    console.log(res);
}