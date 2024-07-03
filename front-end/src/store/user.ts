import { defineStore } from "pinia";

export default defineStore("user", {
  state: () => ({
    user: {
      username: "",
      loginId: "",
      photo: "",
      phone: "",
      email: "",
      role: {
        roleId: 0,
        roleName: "",
      },
    },
  }),
  actions: {
    setUser(user: {
      username: string;
      loginId: string;
      photo: string;
      phone: string;
      email: string;
      role: { roleId: number; roleName: string };
    }) {
      this.user = user;
    },
  },
});
