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
      path: "/home/admin",
      name: "Home Admin",
      component: () => import("../views/home/admin/HomeAdmin.vue"),
    },
    {
      path: "/home/volunteer",
      name: "Home Volunteer",
      component: () => import("../views/home/volunteer/HomeVolunteer.vue"),
    },
    {
      path: "/home/organizer",
      name: "Home Organizer",
      component: () => import("../views/home/organizer/HomeOrganizer.vue"),
      children: [
        {
          path: "/event",
          name: "Event",
          component: () => import("../views/home/organizer/Event.vue"),
        },
      ]
    },
  ],
});

export default routes;