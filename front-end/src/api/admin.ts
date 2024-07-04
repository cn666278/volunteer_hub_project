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
  // console.log(data[0].message);
  if (code === 200) {
    ElNotification({
      title: "Notification",
      message: data.message,
      type: "success",
    });
    // if login success, store token in sessionStorage
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
  let res = await $get("admin/getUserInfo", params);
  console.log(res);
  return res;
};
