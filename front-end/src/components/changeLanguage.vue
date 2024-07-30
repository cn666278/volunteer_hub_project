<template>
  <div class="custom-container">
    <img :src="flagIcon" class="language-icon" alt="language icon" />
    <el-select class="custom-select" v-model="lang" @change="changeLocale" popper-class="custom-popper">
      <el-option :label="t('language.chinese')" value="zh" />
      <el-option :label="t('language.english')" value="en" />
    </el-select>
  </div>
</template>

<script setup lang="ts">
import { ref, getCurrentInstance, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { ElMessage } from 'element-plus'

// 获取当前实例
const { proxy } = getCurrentInstance() as any
const { t } = useI18n()

// 获取存储的语言，默认是中文
const lang = ref(localStorage.getItem('lang') || 'zh')

const currentLocale = computed(() => {
  return proxy.$i18n.locale;
})

// 国旗图标映射
import cnIcon from '../assets/cn-icon.png'
import ukIcon from '../assets/uk-icon.png'

const flagIcons: { [key: string]: string } = {
  zh: cnIcon,
  en: ukIcon,
}

const flagIcon = computed(() => {
  return flagIcons[lang.value]
})

// 切换语言函数
const changeLocale = (locale: string) => {
  proxy.$i18n.locale = locale;
  localStorage.setItem('lang', locale);
  ElMessage.success(t('header.actions.locale'));
}

// 初始化时调用切换语言函数以确保语言设置正确
changeLocale(lang.value)
</script>

<style scoped>
:deep(.el-select__wrapper){
  background-color: #a9181a;
}

:deep(.el-select__placeholder){
    color: white;
  }

.custom-container {
  display: flex;
  align-items: center;
}

.language-icon {
  width: 24px;
  height: 24px;
  margin-right: 10px;
  margin-left: 10px;
}

.custom-select {
  width: 100px;
  border: none;
  background: none;
  color: rgb(255, 143, 15);
  padding: 0;
}

.custom-select .el-input__inner {
  border: none;
  background-color: #a9181a;
  color: rgb(255, 143, 15);
  padding: 0;
  cursor: pointer;
}

.custom-popper .el-select-dropdown__item {
  color: #a9181a;
}

.custom-popper .el-select-dropdown__item:hover {
  background-color: #d9d8d8;
}
</style>
