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
      // component: () => import("../views/volunteer/VolunteerPersonal.vue"),
      // component: () => import("../views/volunteer/Index.vue"),

    },
    {
      path: "/login",
      redirect: "/",
    },
    {
      path: "/register",
      name: "Register",
      meta: { title: "Register" },
      component: () => import("../views/Register.vue"),
    },
    {
      path: "/volunteer",
      name: "Volunteer Page",
      meta: { title: "Volunteer Page" },
      component: () => import("../views/volunteer/Index.vue"),
      children: [
        {
          path: "",
          name: "Volunteer Home",
          meta: { title: "Home" },
          component: () => import("../views/volunteer/Home.vue"),
        },
        {
          path: "/volunteer/personal",
          name: "Volunteer Personal",
          meta: { title: "Personal" },
          component: () => import("../views/volunteer/VolunteerPersonal.vue"),
        },
      ],
    },
    {
      path: "/organizer",
      name: "Organizer Page",
      meta: { title: "Organizer Page" },
      component: () => import("../views/organizer/Index.vue"),
      children: [
        {
          path: "",
          name: "Organizer Home",
          meta: { title: "Home" },
          component: () => import("../views/organizer/Home.vue"),
        },
        {
          path: "/event",
          name: "Event",
          meta: { title: "Event" },
          component: () => import("../views/organizer/Event.vue"),
        },
      ],
    },
    {
      path: "/admin",
      name: "Admin Page",
      meta: { title: "Admin Page" },
      component: () => import("../views/admin/Index.vue"),
      children: [
        {
          path: "",
          name: "Admin Home",
          meta: { title: "Home" },
          component: () => import("../views/admin/Home.vue"),
        },
        {
          path: "/admin/role",
          name: "Role",
          meta: { title: "Role" },
          component: () => import("../views/admin/user/Role.vue"),
        },
        {
          path: "/admin/user",
          name: "User",
          meta: { title: "User" },
          component: () => import("../views/admin/user/User.vue"),
        },
        {
          path: "/admin/personal",
          name: "Personal",
          meta: { title: "Personal" },
          component: () => import("../views/admin/user/Personal.vue"),
        },
      ],
    },
  ],
});

// route guard, check if login, if not, redirect to login page
routes.beforeEach((to, from, next) => {
  next();
});

// change page title, use route meta.title
routes.afterEach((to, from) => {
  // protect, if no meta.title, do not change title
  if (to.meta.title) {
    document.title = String(to.meta.title);
  }
});

export default routes;
