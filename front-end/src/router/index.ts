// @ts-ignore
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
      component: () => import("../views/login/Login.vue"),
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
      component: () => import("../views/login/Register.vue"),
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
          path: "/volunteer/personalInfo",
          name: "Volunteer personalInfo",
          meta: { title: "personalInfo" },
          component: () => import("../views/volunteer/PersonalInfo.vue"),
        },
        {
          path: "/volunteer/personal",
          name: "Volunteer Personal",
          meta: { title: "Personal" },
          component: () => import("../views/volunteer/VolunteerPersonal.vue"),
        },
        {
          path: "/volunteer/credentials",
          name: "Volunteer Credentials",
          meta: { title: "Credentials" },
          component: () => import("../views/volunteer/Credentials.vue"),
        },
        {
          path: "/volunteer/events",
          name: "Volunteer Events",
          meta: { title: "Events" },
          component: () => import("../views/volunteer/Event.vue"),
        }
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
          path: "/myEvents",
          name: "myEvents",
          meta: { title: "MyEvents" },
          component: () => import("../views/organizer/MyEvents.vue"),
        },
        {
          path: "/eventRegister",
          name: "EventRegister",
          meta: { title: "EventRegister" },
          component: () => import("../views/organizer/EventRegister.vue"),
        },
        {
          path: "/eventEdit",
          name: "EventEdit",
          meta: { title: "EventEdit" },
          component: () => import("../views/organizer/EditEvent.vue"),
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
