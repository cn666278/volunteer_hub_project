<template>
  <div class="home">
    <!-- Carousel for displaying images -->
    <el-carousel interval="5000" height="500">
      <el-carousel-item v-for="item in carouselItems" :key="item.id">
        <img :src="item.src" :alt="item.alt" class="carousel-image">
      </el-carousel-item>
    </el-carousel>

    <!-- Project Introduction Section -->
    <div class="project-introduction">
      <h1>WSA Volunteer Hub</h1>
      <p>The WSA Volunteer Hub was set up with the objective of providing our members with crucial support in the running of their events. The role of volunteers is so important in the smooth-running and even existence of the competitions that our members put on for athletes and fans to enjoy; so, it’s vital that there’s a reliable hub of volunteers for them to call upon.</p>
      <p>With our mission of supporting our members and ensuring they remain robust and resilient, this Volunteer Hub will give them just that!</p>
      <p>It’s no secret that the sports industry is a tough one to get into; neither is it a secret that sporting events are so often supported by the work of volunteers. This, as a result, provides people searching for CPD opportunities or even a day of enjoyment a perfect scenario!</p>
      <p>The WSA Volunteer Hub helped member organisation Badminton Wales, for example, who were supported for the Welsh International Badminton Championships 2023 when they came to the Sport Wales National Centre.</p>
      <p>We talked to one of their volunteers at the event, Penney Bell, about her experience with the system and volunteering in general, as well as Badminton Wales Major Events Officer Beth Nesham about the role of the WSA Volunteer Hub and its impact for the organisation, including sourcing a new Board member!</p>
    </div>

    <!-- Activities section with a button and activity display -->
    <div class="activities-section">
      <h1>Events</h1> <!-- New Title -->
      <div class="activities-display">
        <el-card v-for="activity in activities" :key="activity.id" class="activity-card">
          <img :src="activity.image" alt="Activity Image" class="activity-image">
          <div style="padding: 14px;">
            <h5>{{ activity.title }}</h5>
          </div>
        </el-card>
      </div>
      <div class="activities-button-container">
        <el-button class="more-activities-btn" type="text">Find out more!</el-button></div>
    </div>
  </div>
</template>
<script lang='ts'>
import {ElButton, ElCard, ElCarousel, ElCarouselItem} from "element-plus";

export default {
  name: 'Index',
  components: {
    ElCarousel,
    ElCarouselItem,
    ElButton,
    ElCard
  },
  data() {
    return {
      carouselItems: [
        { id: 1, src: './src/assets/volunteer1.jpg', alt: 'Image 1' },
        { id: 2, src: './src/assets/volunteer2.png', alt: 'Image 2' },
        { id: 3, src: './src/assets/volunteer3.jpg', alt: 'Image 3' }
      ],
      activities: [
        { id: 1, title: 'Activity 1', image: './src/assets/volunteer3.jpg' },
        { id: 2, title: 'Activity 2', image: './src/assets/volunteer3.jpg' },
        { id: 3, title: 'Activity 3', image: './src/assets/volunteer3.jpg' },
        { id: 4, title: 'Activity 4', image: './src/assets/volunteer3.jpg' },
        { id: 5, title: 'Activity 5', image: './src/assets/volunteer3.jpg' },
        { id: 6, title: 'Activity 6', image: './src/assets/volunteer3.jpg' }
      ]
    };
  }
};
</script>
<style lang='scss'>
.home {
  padding-top: 20px;
  width: 95%;
  max-width: 1200px; /* 最大宽度确保在大屏幕上也能对齐 */
  margin: auto; /* 自动外边距确保居中 */
  color: #333; /* 统一字体颜色 */
}

.project-introduction, .activities-section {
  padding: 20px;
  background-color: #f5f5f5; /* 仅为 project-introduction 设置 */
  margin: 20px 0; /* 顶部和底部外边距相同 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 仅为 project-introduction 设置 */
  border-radius: 8px; /* 仅为 project-introduction 设置 */
}

.project-introduction {
  background-color: #f5f5f5;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.activities-section {
  background-color: transparent; /* 确保活动部分无背景色 */
  box-shadow: none; /* 活动部分无阴影 */
}

.activities-section h1, .project-introduction h1 {
  margin-bottom: 16px; /* 标题下边距 */
  text-align: center; /* 标题居中 */
}

.carousel-image {
  width: 100%; /* 宽度充满容器 */
  height: auto; /* 高度自动 */
  display: block; /* 图片显示为块级元素 */
}

.activities-display {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 三列布局 */
  gap: 20px;
}

.activity-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.activity-image {
  width: 100%; /* 图片宽度充满卡片 */
  height: auto; /* 自动高度以保持宽高比 */
  object-fit: cover; /* 图片内容填充 */
}

.activities-button-container {
  text-align: right; /* 文字右对齐，会影响内部的按钮对齐 */
  width: 100%; /* 确保容器宽度与活动展示区相同 */
  margin-top: 20px; /* 与活动展示区的间隔 */
}

.more-activities-btn {
  display: inline-block; /* 将按钮设为内联块级元素，使其能响应 text-align */
  border: none; /* 无边框 */
  background-color: transparent; /* 背景透明 */
  color: #333; /* 深灰色字体 */
}

/*Mobile page style settings*/
@media (max-width: 600px) { /* 设置最小值 */
  .content {
    padding: 10px;

  }

  .project-introduction, .activities-section {
    padding: 10px;
    margin: 10px 0;
  }

  .activities-display {
    grid-template-columns: 1fr; /* 在小屏幕上使用单列布局 */
  }

  .carousel-image {
    width: 100%; /* 宽度充满容器 */
    height: 200px;
    display: block; /* 图片显示为块级元素 */
  }
}
</style>