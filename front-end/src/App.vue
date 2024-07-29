<template>
  <!-- <el-config-provider :locale="locale"> -->
    <router-view></router-view>
  <!-- </el-config-provider> -->
</template>

<!-- <script lang="ts" setup>
  import { computed } from 'vue';
  import en from 'element-plus/dist/locale/en.mjs';
  import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
  import useLocale from './hooks/locale';

  const { currentLocale } = useLocale();
  const locale = computed(() => {
    switch (currentLocale.value) {
      case 'zhCn':
        return zhCn;
      case 'en':
        return en;
      default:
        return en;
    }
  });
</script> -->
