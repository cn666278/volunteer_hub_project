<template>
  <div class="MainPage">
    <!-- Header -->
    <el-header>
      <Navbar/>
    </el-header>

    <!-- Main content -->
    <div class="content">
      <!-- Carousel for displaying images -->
      <el-carousel interval="5000" height="500px">
        <el-carousel-item v-for="item in carouselItems" :key="item.id">
          <!-- Updated image style for center alignment -->
          <img :src="item.src" :alt="item.alt" class="carousel-image">
        </el-carousel-item>
      </el-carousel>

      <!-- Activities section with a button and activity display -->
      <div class="activities-section">
        <el-button class="all-activities-btn" type="text">所有活动</el-button>
        <div class="activities-display">
          <el-card v-for="activity in activities" :key="activity.id" class="activity-card">
            <img :src="activity.image" alt="Activity Image" class="activity-image">
            <div style="padding: 14px;">
              <h5>{{ activity.title }}</h5>
            </div>
          </el-card>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <VolunteerFooter/>
  </div>
</template>

<script>
import { ElCarousel, ElCarouselItem, ElButton, ElCard } from 'element-plus';
import Navbar from './volunteer/VolunteerNavbar.vue';
import VolunteerFooter from './volunteer/VolunteerFooter.vue';

export default {
  name: 'MainPage',
  components: {
    Navbar,
    VolunteerFooter,
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

<style scoped>
.content {
  padding-top: 20px;
}

.carousel-image {
  width: 95%; /* Maintain 90% width */
  height: 100%; /* Full height as specified */
  margin: 0 auto; /* Auto margin to center the image horizontally */
  display: block; /* Ensures the image is treated as block-level for margin to work */
}

.activities-section {
  width: 95%;
  position: relative;
  margin: 0 auto; /* Auto margin to center the image horizontally */
  display: block; /* Ensures the image is treated as block-level for margin to work */
}

.all-activities-btn {
  position: absolute;
  right: 0;
  top: 0;
  color: #333; /* Dark gray color */
  border: none; /* Remove border */
  background-color: transparent; /* Remove background */
}

.activities-display {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.activity-card {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.activity-image {
  width: 100%;
  object-fit: cover;
}
</style>
