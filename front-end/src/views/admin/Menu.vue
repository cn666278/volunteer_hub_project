<template>
  <!-- 无子选择的菜单显示 -->
  <div class="he">
    Menu
  </div>
  <div>
    <h1>
      {{ $t('header.welcome') }}
    </h1>
    <p>
      {{ $t('homePage.useV3TS') }}
    </p>
  </div>
  <!-- <changeLanguage class="mr10" /> -->
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import changeLanguage from './changeLanguage.vue';
const router = useRouter()

// const props = defineProps({
//   subRoutes: {
//     type: Object,
//     default: {}
//   }
// })
// const handleClick = (path: string) => {
//   router.push(path)
// }
</script>

<style lang="less"></style>
