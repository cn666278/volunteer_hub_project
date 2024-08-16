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
        <el-menu-item index="2">
          <el-icon><icon-menu /></el-icon>
          <span slot="title">Events Participated</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><document /></el-icon>
          <span slot="title">Events Subscribed</span>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="content">
      <!-- Events Participated Section -->
      <div class="blog-section" v-if="activeIndex === '2'">
        <div class="blog-display">
          <el-card v-for="post in ongoingEvents" :key="post.id" class="blog-card" @click="navigateToEvent(post.id)">
            <img :src="post.image" alt="Blog Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.organizationName }}
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
      <!-- Events Subscribed Section -->
      <div class="blog-section" v-if="activeIndex === '3'">
        <div class="blog-display">
          <el-card v-for="post in subscribedEvents" :key="post.id" class="blog-card" @click="navigateToEvent(post.id)">
            <img :src="post.image" alt="Blog Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.organizationName }}
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
import { ref, onMounted } from 'vue';
import { Document, Menu as IconMenu, User, Calendar } from '@element-plus/icons-vue';
import api from '../../api/api'; // 引入api文件
import useUser from '../../store/user'; // 假设您有一个用于获取用户登录信息的store

const activeIndex = ref('2'); // 默认设置为 '2' 显示 Events Participated 页面
const searchQuery = ref('');

const userStore = useUser();
const subscribedEvents = ref([]); // 保存已订阅活动的列表

const ongoingEvents = ref([
  // Ongoing event data...
]);

const fetchSubscribedEvents = async () => {
  try {
    const volunteerId = userStore.user.loginId;
    const response = await api.getSubscribedEvents({ volunteerId });
    subscribedEvents.value = response.map(event => ({
      id: event.id,
      image: event.eventPic, // 确保 eventPic 是正确的字段名
      title: event.title,
      date: event.startDate, // 确保 startDate 是正确的字段名
      organizationName: event.organizationName, // 确保 organizerName 是正确的字段名，显示组织者名称
      description: event.description,
    }));
  } catch (error) {
    console.error('Error fetching subscribed events:', error);
  }
};

const handleSelect = (index: string) => {
  activeIndex.value = index;
  if (index === '3') {
    fetchSubscribedEvents();
  }
};

const navigateToEvent = (eventId: string) => {
  proxy.$router.push({ name: 'EventDetail', params: { id: eventId } });
};

onMounted(() => {
  // 初始化其他数据
});
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

.blog-section {
  padding: 20px;
  background-color: #f5f5f5;
  margin: 20px 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;
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
}

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
}

.blog-author-date {
  display: flex;
  align-items: center;
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 10px;
}

.author-icon, .date-icon {
  color: red; /* Icon color */
  margin-right: 5px;
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
}

.author-details, .date-details {
  display: flex;
  align-items: center;
}

.el-icon {
  color: #007BFF; /* Changed icon color to blue */
  margin-right: 5px;
}
</style>
