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
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)
window.global = global;
const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);  
}
initI18n(app)
app.config.globalProperties.$api = api;
app.use(ElementPlus).use(router).use(pinia).mount('#app')

// if (process.env.NODE_ENV === 'production') {
    // console.log = function () {};
//   }
  
