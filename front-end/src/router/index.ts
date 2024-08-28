// @ts-ignore
import { createRouter, createWebHashHistory } from "vue-router";
import "pace-progressbar";
import "pace-progressbar/themes/blue/pace-theme-minimal.css";

const routes = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/login",
      name: "Login",
      meta: { title: "Login" },
      component: () => import("../views/login/Login.vue"),
    },
    // {
    //   path: "/login",
    //   redirect: "/",
    // },
    {
      path: "/register",
      name: "Register",
      meta: { title: "Register" },
      component: () => import("../views/login/Register.vue"),
    },
    {
      path: "/demo",
      name: "Demo",
      meta: { title: "Register" },
      component: () => import("../views/volunteer/EventRegister.vue"),
    },
    {
      path: "/test",
      name: "Test",
      meta: { title: "Test" },
      component: () => import("../views/admin/Menu.vue"),
    },
    {
      path: "/",
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
          name: "Events",
          meta: { title: "Events" },
          component: () => import("../views/volunteer/Events.vue"),
        },
        {
          path: "/volunteer/event/:id",
          name: "EventDetail",
          meta: { title: "Event Detail" },
          component: () => import("../views/volunteer/Events_detail.vue"),
        },
        {
          path: "/volunteer/events_volunteer",
          name: "Volunteer Events",
          meta: { title: "Volunteer Events" },
          component: () => import("../views/volunteer/Events_volunteer.vue"),
        },
        {
          path: "/volunteer/eventRegister",
          name: "Volunteer Event Register",
          meta: { title: "Event Register" },
          component: () => import("../views/volunteer/EventRegister.vue"),
        },
        {
          path: "/volunteer/comments",
          name: "Volunteer Comments",
          meta: { title: "Comments" },
          component: () => import("../views/volunteer/Comments.vue"),
        },
        {
          path: "/volunteer/information",
          name: "Volunteer Information",
          meta: { title: "Information" },
          component: () => import("../views/volunteer/Information.vue"),
        },
        {
          path: "/volunteer/share",
          name: "Volunteer Share",
          meta: { title: "Share" },
          component: () => import("../views/volunteer/Share.vue"),
        },
        {
          path: "/",
          name: "Volunteer Event",
          meta: { title: "Event" },
          component: () => import("../views/volunteer/EventRegister.vue"),
        },
        {
          path: "/volunteer/rewardStore",
          name: "RewardStore",
          meta: { title: "RewardStore" },
          component: () => import("../views/volunteer/RewardStore.vue"),
        },
        {
          path: "/volunteer/discussion",
          name: "EventDiscussion",
          meta: { title: "EventDiscussion" },
          component: () => import("../views/volunteer/EventDiscussion.vue"),
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
        {
          path: "/eventReview",
          name: "EventReview",
          meta: { title: "EventReview" },
          component: () => import("../views/organizer/EventReview.vue"),
        },
        {
          path: '/commentVolunteer',
          name: 'CommentVolunteer',
          meta: { title: "CommentVolunteer" },
          component: () => import('../views/organizer/CommentVolunteer.vue'),
        },
        {
          path: '/commentDetail',
          name: 'CommentDetail',
          meta: { title: "CommentDetail" },
          component: () => import('../views/organizer/CommentDetail.vue'),
        },
        {
          path: '/eventDiscuss',
          name: 'EventDiscuss',
          meta: { title: "EventDiscuss" },
          component: () => import('../views/organizer/EventDiscuss.vue'),
        },
        {
          path: '/uploadTest',
          name: 'UploadTest',
          meta: { title: "UploadTest" },
          component: () => import('../views/organizer/UploadTest.vue'),
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
          path: "/admin/rewardstore",
          name: "RewardStoreForAdmin",
          meta: { title: "RewardStoreForAdmin" },
          component: () => import("../views/admin/user/RewardStore.vue"),
        },
        {
          path: "/admin/personal",
          name: "Personal",
          meta: { title: "Personal" },
          component: () => import("../views/admin/user/Personal.vue"),
        },
        {
          path: "/admin/approve/event",
          name: "ApproveEvent",
          meta: { title: "ApproveEvent" },
          component: () => import("../views/admin/approve/Event.vue"),
        },
        {
          path: "/admin/menu",
          name: "Menu",
          meta: { title: "Menu" },
          component: () => import("../views/admin/Menu.vue"),
        },
        {
          path: "/admin/eventData",
          name: "EventData",
          meta: { title: "EventData" },
          component: () => import("../views/admin/analyse/EventData.vue"),
        },
        {
          path: "/admin/dataCount",
          name: "DataCount",
          meta: { title: "DataCount" },
          component: () => import("../views/admin/analyse/DataCount.vue"),
        },
        {
          path: "/admin/registerVolunteer",
          name: "RegisterVolunteer",
          meta: { title: "RegisterVolunteer" },
          component: () => import("../views/admin/analyse/RegisterVolunteer.vue"),
        },
        {
          path: "/admin/activeUser",
          name: "ActiveUsers",
          meta: { title: "ActiveUsers" },
          component: () => import("../views/admin/analyse/ActiveUsers.vue"),
        },
        {
          path: "/admin/organizerData",
          name: "OrganizerData",
          meta: { title: "OrganizerData" },
          component: () => import("../views/admin/analyse/OrganizerData.vue"),
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
