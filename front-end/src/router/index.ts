import { createRouter, createWebHashHistory } from "vue-router";

const routes = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      name: "Login",
      component: () => import("../views/Login.vue"),
    },
    {
      path: "/login",
      redirect: "/",
    },
    {
      path: "/main",
      name: "Main",
      component: () => import("../views/Main.vue"),
    },
  ],
});

export default routes;