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
          <el-card v-for="post in participatedEvents" :key="post.id" class="blog-card">
            <img :src="post.image" alt="Blog Image" class="blog-image" @click="navigateToEvent(post.id)">
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
              <div class="status-box" :class="{'status-pending': post.status === 'pending', 'status-accepted': post.status === 'accepted'}">
                {{ post.status }}
              </div>
              <!-- 新增的 Discuss 按钮 -->
              <el-button type="primary" @click="navigateToDiscussion(post.id)">Discuss</el-button>
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
              <div class="status-box" :class="{'status-pending': post.status === 'pending', 'status-accepted': post.status === 'accepted'}">
                {{ post.status }}
              </div>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted,getCurrentInstance } from 'vue';
import { Document, Menu as IconMenu, User, Calendar } from '@element-plus/icons-vue';
import api from '../../api/api'; // 引入api文件
import useUser from '../../store/user'; // 假设您有一个用于获取用户登录信息的store
const { proxy } = getCurrentInstance();
const activeIndex = ref('2'); // 默认设置为 '2' 显示 Events Participated 页面
const searchQuery = ref('');
import { useRouter } from "vue-router";
let router = useRouter();
const userStore = useUser();
const subscribedEvents = ref([]); // 保存已订阅活动的列表
const participatedEvents = ref([]); // 保存用户参与的活动列表
const navigateToDiscussion = (eventId: string) => {
  console.log("navigateToDiscussion",eventId)
  router.push({ name: 'EventDiscussion', query: { event: eventId } });
};

// 获取用户订阅的活动
const fetchSubscribedEvents = async () => {
  try {
    const volunteerId = userStore.user.id;
    const response = await api.getSubscribedEvents({ volunteerId });
    subscribedEvents.value = response.map(event => ({
      id: event.id,
      image: event.eventPic,
      title: event.title,
      date: event.startDate,
      organizationName: event.organizationName,
      description: event.description,
      status: event.status, // 确保从后端获取status
    }));
  } catch (error) {
    console.error('Error fetching subscribed events:', error);
  }
};

// 获取用户参与的活动
const fetchParticipatedEvents = async () => {
  try {
    const volunteerId = userStore.user.id;
    const response = await api.getParticipatedEvents({ volunteerId });
    participatedEvents.value = response.map(event => ({
      id: event.id,
      image: event.eventPic,
      title: event.title,
      date: event.startDate,
      organizationName: event.organizationName,
      description: event.description,
      status: event.status, // 确保从后端获取status
    }));
  } catch (error) {
    console.error('Error fetching participated events:', error);
  }
};

// 处理菜单选择
const handleSelect = (index: string) => {
  activeIndex.value = index;
  if (index === '3') {
    fetchSubscribedEvents();
  } else if (index === '2') {
    fetchParticipatedEvents();
  }
};

// 导航到活动详情页
const navigateToEvent = (eventId: string) => {
  proxy.$router.push({ name: 'EventDetail', params: { id: eventId } });
};

onMounted(() => {
  fetchParticipatedEvents(); // 页面加载时获取参与的活动
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

.status-box {
  margin-top: 10px;
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 0.9rem;
  color: white;
  text-align: center;
  width: fit-content;
  margin-left: auto;
  margin-right: auto;
  background-color:  #a9181a;
}

.status-pending {
  background-color: #f39c12; /* Pending 状态的背景颜色 */
}

.status-accepted {
  background-color: #27ae60; /* Accepted 状态的背景颜色 */
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
