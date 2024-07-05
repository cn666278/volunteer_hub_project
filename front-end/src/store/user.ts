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
    clearUser() {
      sessionStorage.clear();
      this.user = {
        username: "",
        loginId: "",
        photo: "",
        phone: "",
        email: "",
        role: {
          roleId: 0,
          roleName: "",
        },
      };
    },
  },
  // data persistence
  // persist: true // default: localStorage (permanent storage in the browser, not cleared when the browser is closed)
  persist: {
    storage: sessionStorage, // use sessionStorage (temporary storage in the browser, cleared when the browser is closed)
  },
});
