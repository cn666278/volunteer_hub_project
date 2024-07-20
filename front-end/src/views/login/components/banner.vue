<template>
  <div class="banner">
    <div class="banner-inner">
      <a-carousel class="carousel" animation-name="fade"  :auto-play="{interval: 5000}">
        <a-carousel-item v-for="item in carouselItem" :key="item.slogan">
          <div :key="item.slogan" class="carousel-item">
            <div class="carousel-title">{{ item.slogan }}</div>
            <div class="carousel-sub-title">{{ item.subSlogan }}</div>
            <img class="carousel-image" :src="item.image" />
          </div>
        </a-carousel-item>
      </a-carousel>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue';
import bannerImage1 from '../../../assets/login-banner1.png';
import bannerImage2 from '../../../assets/login-banner2.png';
import bannerImage3 from '../../../assets/login-banner3.png';
import { Carousel } from '@arco-design/web-vue';

const carouselItem = computed(() => [
  {
    slogan: "Welcome to WSA Volunteer Hub",
    subSlogan: "hello",
    image: bannerImage1,
  },
  {
    slogan: "Earn volunteer vouchers and rewards",
    subSlogan: "a massive thank you",
    image: bannerImage2,
  },
  {
    slogan: "We are here to help the sports community",
    subSlogan: "you can make a difference",
    image: bannerImage3,
  },
]);
</script>

<style lang="less" scoped>
.banner {
  display: flex;
  align-items: center;
  justify-content: center;

  &-inner {
    flex: 1;
    height: 100%;
  }
}

.carousel {
  height: 100%;

  &-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  &-title {
    color: var(--color-fill-1);
    font-weight: 500;
    font-size: 20px;
    line-height: 28px;
  }

  &-sub-title {
    margin-top: 8px;
    color: var(--color-fill-2);
    font-size: 14px;
    line-height: 22px;
  }

  &-image {
    width: 320px;
    margin-top: 30px;
  }
}
</style>