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
        <span slot="title">All Events</span>
      </el-menu-item>
      <el-menu-item index="2">
        <el-icon><icon-menu /></el-icon>
        <span slot="title">Events Participated</span>
      </el-menu-item>
      <el-menu-item index="3">
        <el-icon><document /></el-icon>
        <span slot="title">Events Subscribed</span>
      </el-menu-item>
      <el-menu-item index="4">
        <el-icon><setting /></el-icon>
        <span slot="title">Navigator Four</span>
      </el-menu-item>
      </el-menu>
    </div>
    <div class="content">
      <div v-if="activeIndex === '1'" class="search-section">
        <el-input
            placeholder="Type here to search..."
            v-model="searchQuery">
          <template #prefix>
            <el-icon><search /></el-icon>
          </template>
        </el-input>
      </div>
      <!-- Additional content sections for other menu items could be added here -->
      <div class="events-display" v-if="activeIndex === '1'">
        <el-card v-for="event in events" :key="event.id" class="event-card">
          <img :src="event.image" alt="Event Image" class="event-image">
          <div style="padding: 14px;">
            <h5>{{ event.title }}</h5>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>



<script lang="ts" setup>
import { ref } from 'vue';
import { Document, Menu as IconMenu, Setting, Search } from '@element-plus/icons-vue';

const activeIndex = ref('1'); // Default active index set to '1' for search
const searchQuery = ref('');
const events = ref([
  { id: 1, title: 'Event 1', image: './src/assets/event1.jpg' },
  { id: 2, title: 'Event 2', image: './src/assets/event2.jpg' },
  { id: 3, title: 'Event 3', image: './src/assets/event3.jpg' },
  { id: 4, title: 'Event 4', image: './src/assets/event4.jpg' },
  { id: 5, title: 'Event 5', image: './src/assets/event5.jpg' },
  { id: 6, title: 'Event 6', image: './src/assets/event6.jpg' }
]);


// 更新 handleSelect 方法以处理菜单选择动作
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

.el-input .el-input__inner {
  padding-left: 35px;
}

.search-section .el-input {
  width: 80%;
}

.el-input .el-icon {
  margin-right: 8px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%; /* Use full width of the sidebar */
  min-height: 400px;
}

.events-display {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  padding: 20px;
}

.event-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.event-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}
</style>
