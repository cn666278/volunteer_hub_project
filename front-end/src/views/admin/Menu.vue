<template>
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
import changeLanguage from '../../components/changeLanguage.vue';
const router = useRouter()

</script>

<style lang="less"></style>
