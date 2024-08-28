import { defineStore } from 'pinia';

export default defineStore('theme', {
  state: () => ({
    theme: localStorage.getItem('theme') || 'light',
  }),
  actions: {
    toggleTheme() {
      this.theme = this.theme === 'light' ? 'dark' : 'light';
      document.documentElement.setAttribute('data-theme', this.theme);
      localStorage.setItem('theme', this.theme);
    },
    initializeTheme() {
      // 设置主题并确保主题立即应用
      document.documentElement.setAttribute('data-theme', this.theme);
    },
  },
  persist: true, // 使状态持久化存储到localStorage
});
