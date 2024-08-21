<template>
  <div class="Index">
    <!-- Header -->
    <div class="header">
      <el-header>
        <Navbar/>
      </el-header>
    </div>

    <!-- Main content -->
    <div class="content">
      <router-view></router-view>
    </div>


    <!-- Footer -->
    <div class="footer">
    <VolunteerFooter/>
    </div>
  </div>
</template>


<script>
import { ElCarousel, ElCarouselItem, ElButton, ElCard } from 'element-plus';
import Navbar from './VolunteerNavbar.vue';
import VolunteerFooter from './VolunteerFooter.vue';

export default {
  name: 'Index',
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
.content{
  margin-top: 10px;
}
</style>
