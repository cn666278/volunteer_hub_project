<template>
  <div class="container">
    <div class="sidebar">
      <el-menu
          :default-active="activeIndex"
          class="el-menu-vertical-demo"
          background-color="#ffffff"
          text-color="#303133"
          active-text-color="#409EFF"
          @select="handleSelect"
      >
        <el-menu-item index="1">
          <el-icon><search /></el-icon>
          <span slot="title">{{ $t('events.allEvents') }}</span>
        </el-menu-item>
        <el-menu-item index="2">
          <el-icon><icon-menu /></el-icon>
          <span slot="title">{{ $t('events.participatedEvents') }}</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><document /></el-icon>
          <span slot="title">{{ $t('events.subscribedEvents') }}</span>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="content">
      <div v-if="activeIndex === '1'" class="search-section">
        <el-input
            :placeholder="$t('events.searchPlaceholder')"
            v-model="searchQuery">
          <template #prefix>
            <el-icon><search /></el-icon>
          </template>
        </el-input>
      </div>
      <!-- All Events Section -->
      <div class="blog-section" v-if="activeIndex === '1'">
        <div class="blog-display">
          <el-card v-for="post in allEvents" :key="post.id" class="blog-card">
            <img :src="post.image" alt="Blog Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.author }}
                </div>
                <div class="date-details">
                  <el-icon><Calendar /></el-icon>
                  {{ post.date }}
                </div>
              </div>
              <h5>{{ post.title }}</h5>
              <p>{{ post.description }}</p>
            </div>
          </el-card>
        </div>
      </div>
      <!-- Participated Events Section -->
      <div class="blog-section" v-if="activeIndex === '2'">
        <div class="blog-display">
          <el-card v-for="post in ongoingEvents" :key="post.id" class="blog-card">
            <img :src="post.image" alt="Blog Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.author }}
                </div>
                <div class="date-details">
                  <el-icon><Calendar /></el-icon>
                  {{ post.date }}
                </div>
              </div>
              <h5>{{ post.title }}</h5>
              <p>{{ post.description }}</p>
            </div>
          </el-card>
        </div>
      </div>
      <!-- Subscribed Events Section -->
      <div class="blog-section" v-if="activeIndex === '3'">
        <div class="blog-display">
          <el-card v-for="post in closedEvents" :key="post.id" class="blog-card">
            <img :src="post.image" alt="Blog Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.author }}
                </div>
                <div class="date-details">
                  <el-icon><Calendar /></el-icon>
                  {{ post.date }}
                </div>
              </div>
              <h5>{{ post.title }}</h5>
              <p>{{ post.description }}</p>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { Document, Menu as IconMenu, Search, User, Calendar } from '@element-plus/icons-vue';

const activeIndex = ref('1'); // Default active index set to '1' for all events
const searchQuery = ref('');

const allEvents = ref([
  {
    id: 1,
    image: './src/assets/event-judo.png',
    title: 'The British Adaptive & VI Open',
    date: '30.07.2024',
    author: 'Welsh Judo',
    description: 'Welsh Judo is hosting the British Adaptive & VI Open in Swansea, showcasing inclusive judo for visually impaired athletes.'
  },
  {
    id: 2,
    image: './src/assets/event-badminto.png',
    title: 'Yonex Welsh International Challenge',
    date: '30.07.2024',
    author: 'Badminton Wales',
    description: 'Badminton Wales is hosting the Yonex Welsh International Challenge in Cardiff, attracting top badminton talent from around the world.'
  },
  {
    id: 3,
    image: './src/assets/event-tabletennis.png',
    title: 'The magic of marketing, the science of sales',
    date: '30.07.2024',
    author: 'Table Tennis Wales',
    description: 'Table Tennis Wales is hosting the Senior Team Championships of Wales, featuring elite team competition in table tennis.'
  },
  {
    id: 4,
    image: './src/assets/event-hocky.png',
    title: 'International Hockey Matches',
    date: '30.07.2024',
    author: 'Hockey Wales',
    description: 'Hockey Wales is hosting an International Series over a long weekend, bringing exciting international hockey matches to Wales.'
  },
  {
    id: 5,
    image: './src/assets/event-cycling.png',
    title: 'Masters National Track Championships',
    date: '30.07.2024',
    author: 'Welsh Cycling',
    description: 'Welsh Cycling is hosting the Masters National Track Championships in Newport, highlighting competitive cycling for master athletes.'
  },
  {
    id: 6,
    image: './src/assets/event-snow.png',
    title: 'Welsh Outdoor Championships',
    date: '30.07.2024',
    author: 'Snowsports Wales',
    description: 'Snowsports Wales is hosting the Welsh Outdoor Championships in Pontypool, celebrating outdoor snow sports competitions.'
  }
]);

const ongoingEvents = ref([
  {
    id: 1,
    image: './src/assets/event-judo.png',
    title: 'The British Adaptive & VI Open',
    date: '30.07.2024',
    author: 'Welsh Judo',
    description: 'Welsh Judo is hosting the British Adaptive & VI Open in Swansea, showcasing inclusive judo for visually impaired athletes.'
  },
  {
    id: 2,
    image: './src/assets/event-badminto.png',
    title: 'Yonex Welsh International Challenge',
    date: '30.07.2024',
    author: 'Badminton Wales',
    description: 'Badminton Wales is hosting the Yonex Welsh International Challenge in Cardiff, attracting top badminton talent from around the world.'
  },
  {
    id: 3,
    image: './src/assets/event-tabletennis.png',
    title: 'The magic of marketing, the science of sales',
    date: '30.07.2024',
    author: 'Table Tennis Wales',
    description: 'Table Tennis Wales is hosting the Senior Team Championships of Wales, featuring elite team competition in table tennis.'
  }
]);

const closedEvents = ref([
  {
    id: 4,
    image: './src/assets/event-hocky.png',
    title: 'International Hockey Matches',
    date: '30.07.2024',
    author: 'Hockey Wales',
    description: 'Hockey Wales is hosting an International Series over a long weekend, bringing exciting international hockey matches to Wales.'
  },
  {
    id: 5,
    image: '',
    title: 'Masters National Track Championships',
    date: '30.07.2024',
    author: 'Welsh Cycling',
    description: 'Welsh Cycling is hosting the Masters National Track Championships in Newport, highlighting competitive cycling for master athletes.'
  },
  {
    id: 6,
    image: './src/assets/event-snow.png',
    title: 'Welsh Outdoor Championships',
    date: '30.07.2024',
    author: 'Snowsports Wales',
    description: 'Snowsports Wales is hosting the Welsh Outdoor Championships in Pontypool, celebrating outdoor snow sports competitions.'
  }
]);

const handleSelect = (index: string, indexPath: string[]) => {
  console.log('Menu item selected:', index);
  activeIndex.value = index; // 更新活跃索引以触发内容区域的变更
};

const handleOpen = (key: string, keyPath: string[]) => {
  console.log('Opened:', key, keyPath);
};

const handleClose = (key: string, keyPath: string[]) => {
  console.log('Closed:', key, keyPath);
};
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh; /* Full viewport height */
}

.sidebar {
  flex: 0 0 200px; /* Sidebar width is fixed to 200px */
  background: #fff;
  overflow-y: auto; /* Makes the sidebar scrollable */
}

.content {
  flex-grow: 1; /* Content takes up the remaining space */
  overflow-y: auto; /* Makes the content area scrollable */
}

.search-section {
  padding: 20px;
  display: flex;
  justify-content: center;
}

.el-input{
  --el-input-border-color: #c0c4cc;
}

.el-input .el-input__inner {
  padding-left: 35px;
}

.search-section .el-input {
  width: 80%;
}

.el-input .el-icon {
  margin-right: 8px;
  color: #409EFF;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%; /* Use full width of the sidebar */
  min-height: 400px;
}

.blog-section {
  padding: 20px;
  background-color: #f5f5f5;
  margin: 20px 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;

h1 {
  margin-bottom: 16px;
}
}

.blog-display {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.blog-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
  border: 1px solid #ddd; /* Add border to the card */
  border-radius: 8px; /* Rounded corners */
  box-shadow: 0 2px 5px rgba(0,0,0,0.1); /* Light shadow */

img {
  width: 100%;
  height: 200px; /* Set image height */
  object-fit: cover; /* Ensure image is fully displayed */
  border-radius: 8px; /* Rounded corners for the image */
}

.blog-info {
  padding: 14px;
  text-align: left;
  width: 100%;

.blog-author-date {
  display: flex;
  align-items: center;
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 10px;

.author-icon, .date-icon {
  color: red; /* Icon color */
  margin-right: 5px;
}
}

h5 {
  color: #333;
  font-size: 1.2rem;
  font-weight: bold;
  margin: 5px 0;
}

p {
  color: #666;
  font-size: 1rem;
}
}
}

@media (max-width: 800px) {
  .blog-display {
    grid-template-columns: 1fr;
  }
}

.blog-author-date {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 10px;

.author-details, .date-details {
  display: flex;
  align-items: center;

.el-icon {
  color: #007BFF; /* Changed icon color to blue */
  margin-right: 5px;
}
}
}
</style>
