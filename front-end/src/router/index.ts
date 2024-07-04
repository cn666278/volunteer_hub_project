import { createRouter, createWebHashHistory } from "vue-router";
import "pace-progressbar";
import "pace-progressbar/themes/blue/pace-theme-minimal.css";

const routes = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      name: "Login",
      meta: { title: "Login" },
      component: () => import("../views/Login.vue"),
    },
    {
      path: "/login",
      redirect: "/",
    },
    {
      path: "/home/admin",
      name: "Admin",
      meta: { title: "Admin Page" },
      component: () => import("../views/home/admin/Index.vue"),
      children: [
        {
          path: "/home/admin/home",
          name: "Home",
          meta: { title: "Home" },
          component: () => import("../views/home/admin/Home.vue"),
        },
        {
          path: "/home/admin/role",
          name: "Role",
          meta: { title: "Role" },
          component: () => import("../views/home/admin/user/Role.vue"),
        },
        {
          path: "/home/admin/user",
          name: "User",
          meta: { title: "User" },
          component: () => import("../views/home/admin/user/User.vue"),
        },
        {
          path: "/home/admin/personal",
          name: "Personal",
          meta: { title: "Personal" },
          component: () => import("../views/home/admin/user/Personal.vue"),
        },
      ],
    },
    {
      path: "/home/volunteer",
      name: "Home Volunteer",
      meta: { title: "Home Volunteer" },
      component: () => import("../views/home/volunteer/HomeVolunteer.vue"),
    },
    {
      path: "/home/organizer",
      name: "Home Organizer",
      meta: { title: "Home Organizer" },
      component: () => import("../views/home/organizer/HomeOrganizer.vue"),
      children: [
        {
          path: "/event",
          name: "Event",
          meta: { title: "Event" },
          component: () => import("../views/home/organizer/Event.vue"),
        },
      ],
    },
  ],
});

// 路由守卫，判断是否登录，未登录跳转到登录页
// route guard, check if login, if not, redirect to login page
routes.beforeEach((to, from, next) => {
  next();
});

// 修改页面标题, 通过路由元信息meta.title
// change page title, use route meta.title
routes.afterEach((to, from) => {
  // 保护性判断，如果没有设置meta.title，则不修改标题
  // protect, if no meta.title, do not change title
  if (to.meta.title) {
    document.title = String(to.meta.title);
  }
});

export default routes;
