import { App } from 'vue'
import { createI18n } from 'vue-i18n'
import { zh } from './zh'
import { en } from './en'
import { cy } from './cy'

console.log(zh)
console.log(en)

const isBrowser = typeof window !== 'undefined' && typeof navigator !== 'undefined';
const language = isBrowser ? (navigator.language || 'en').toLocaleLowerCase() : 'en'; // Get the language setting of the browser
const locale = isBrowser ? (localStorage.getItem('lang') || language) : 'en';

const i18n = createI18n({
  legacy: false,
  locale: locale, // Prioritize getting the language setting from local storage, if not available, use the browser's default language
  fallbackLocale: 'en', // Use the fallback language when the current language cannot find a matching translation
  messages: {
    en,
    zh,
    cy
  }
})

export const initI18n = (app: App<Element>) => {
  app.use(i18n)
}
