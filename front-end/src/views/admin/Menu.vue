<template>
  <div>
    <el-button @click="toggleTheme">Toggle Theme</el-button>
    <p>The current theme is: {{ theme }}</p>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import useThemeStore from '../../store/theme'; // 导入自定义的theme store

const themeStore = useThemeStore(); // 创建theme store实例

const toggleTheme = () => {
  themeStore.toggleTheme(); // 调用store中的toggleTheme action
};

const theme = computed(() => themeStore.theme); // 使用computed来实时获取主题值

console.log('Current theme:', theme.value); // Debugging line
</script>
