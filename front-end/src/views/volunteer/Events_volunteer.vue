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
          <el-icon><IconMenu /></el-icon>
          <span slot="title">Events Participated</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><Document /></el-icon>
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
              <div class="button-container">
                <el-button v-if="post.status === 'accepted'" type="primary" @click="navigateToDiscussion(post.id)" class="discuss-button">Discuss</el-button>
                <div class="status-box" :class="{'status-pending': post.status === 'pending', 'status-accepted': post.status === 'accepted'}">
                  {{ post.status }}
                </div>
              </div>
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
import { ref, onMounted, getCurrentInstance } from 'vue';
import { Document, Menu as IconMenu, User, Calendar } from '@element-plus/icons-vue';
import api from '../../api/api';
import useUser from '../../store/user';
const { proxy } = getCurrentInstance();
const activeIndex = ref('2');
import { useRouter } from "vue-router";
let router = useRouter();
const userStore = useUser();
const subscribedEvents = ref([]);
const participatedEvents = ref([]);

const navigateToDiscussion = (eventId: string) => {
  router.push({ name: 'EventDiscussion', query: { event: eventId } });
};

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
      status: event.status,
    }));
  } catch (error) {
    console.error('Error fetching subscribed events:', error);
  }
};

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
      status: event.status,
    }));
  } catch (error) {
    console.error('Error fetching participated events:', error);
  }
};

const handleSelect = (index: string) => {
  activeIndex.value = index;
  if (index === '3') {
    fetchSubscribedEvents();
  } else if (index === '2') {
    fetchParticipatedEvents();
  }
};

const navigateToEvent = (eventId: string) => {
  proxy.$router.push({ name: 'EventDetail', params: { id: eventId } });
};

onMounted(() => {
  fetchParticipatedEvents();
});
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;
}

.sidebar {
  flex: 0 0 200px;
  background: #fff;
  overflow-y: auto;
}

.content {
  flex-grow: 1;
  overflow-y: auto;
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
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  position: relative;
}

img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
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
  color: red;
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
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 0.9rem;
  color: white;
  text-align: center;
  position: absolute;
  right: 10px;
  bottom: 10px;
}

.status-pending {
  background-color: #f39c12;
}

.status-accepted {
  background-color: #27ae60;
}

.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.discuss-button {
  position: absolute;
  left: 10px;
  bottom: 10px;
}

/* Responsive design for mobile devices */
@media (max-width: 800px) {
  .sidebar {
    flex: 0 0 80px;
  }

  .el-menu-item span {
    display: none;
  }

  .blog-display {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 480px) {
  .blog-display {
    grid-template-columns: 1fr;
  }

  img {
    height: 150px;
  }
}

.el-icon {
  color: #a9181a;
  margin-right: 5px;
}
</style>

