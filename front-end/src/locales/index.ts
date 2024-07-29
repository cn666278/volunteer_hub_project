import { App } from 'vue'
import { createI18n } from 'vue-i18n'
import { zh } from './zh'
import { en } from './en'
console.log(zh)
console.log(en)

const language = (navigator.language || 'en').toLocaleLowerCase() // 获取浏览器的语言设置
const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem('lang') || language, // 优先从本地存储获取语言设置，如果没有则使用浏览器默认语言
  fallbackLocale: 'en', // 当前语言无法找到匹配的翻译时，使用的备选语言
  messages: {
    en,
    zh
  }
})

export const initI18n = (app: App<Element>) => {
  app.use(i18n)
}
