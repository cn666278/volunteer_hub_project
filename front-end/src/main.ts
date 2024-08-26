import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import global from 'global';
import router from './router/index.ts'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from "pinia";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import api from './api/api.ts'
import './api/mock.ts'
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import 'vue-cal/dist/vuecal.css';
import { initI18n } from './locales/index.ts'
import useThemeStore from './store/theme'; // 引入主题store

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
window.global = global;

const app = createApp(App);

// 注册Element Plus图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);  
}

// 初始化多语言支持
initI18n(app)

// 设置全局属性
app.config.globalProperties.$api = api;

// 初始化主题
const themeStore = useThemeStore(pinia); // 传递 pinia 实例
themeStore.initializeTheme(); // 确保页面初次加载时应用主题

// 挂载Vue应用
app.use(ElementPlus).use(router).use(pinia).mount('#app')

// if (process.env.NODE_ENV === 'production') {
//     console.log = function () {};
// }
