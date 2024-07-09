import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/index.ts'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import { createPinia } from "pinia";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import api from './api/api.ts'
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

const app = createApp(App);
app.config.globalProperties.$api = api;
app.use(ElementPlus).use(router).use(pinia).mount('#app')
