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
          <span slot="title">{{ $t('events.ongoingEvents') }}</span>
        </el-menu-item>
        <el-menu-item index="3">
          <el-icon><document /></el-icon>
          <span slot="title">{{ $t('events.closedEvents') }}</span>
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
      <div class="blog-section" v-if="activeIndex === '1'">
        <div class="blog-display">
          <el-card v-for="post in filteredEvents" :key="post.id" class="blog-card" @click="navigateToEvent(post.id)">
            <img :src="post.eventPic" alt="Event Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.organizer }}
                </div>
                <div class="date-details">
                  <el-icon><Calendar /></el-icon>
                  {{ formatDate(post.date) }}
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
import { ref, onMounted, computed, getCurrentInstance } from 'vue';
import { Search, User, Calendar } from '@element-plus/icons-vue';
import { ElMessage } from "element-plus";
import api from '../../api/api';

const allEvents = ref([]);
const activeIndex = ref('1');
const searchQuery = ref('');
const { proxy } = getCurrentInstance();

const loadAllEvents = async () => {
  const response = await api.getAllEvents()
      .catch(error => {
        ElMessage.error(error.message);
      });
  if (response) {
    allEvents.value = response;
  }
};

onMounted(() => {
  loadAllEvents();
});

const handleSelect = (index: string) => {
  activeIndex.value = index;
};

const navigateToEvent = (eventId: string) => {
  proxy.$router.push({ name: 'EventDetail', params: { id: eventId } });
};


const formatDate = (dateString: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  };
  return new Date(dateString).toLocaleDateString(undefined, options);
};

const filteredEvents = computed(() => {
  if (!searchQuery.value) return allEvents.value;
  return allEvents.value.filter(event =>
      event.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      (event.description && event.description.toLowerCase().includes(searchQuery.value.toLowerCase()))
  );
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

.search-section {
  padding: 20px;
  display: flex;
  justify-content: center;
}

.el-input {
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
  width: 100%;
  min-height: 400px;
}

.blog-section {
  padding: 20px;
  background-color: #f5f5f5;
  margin: 20px 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;
}

h1 {
  margin-bottom: 16px;
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
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.blog-card img {
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

.placeholder-section {
  padding: 20px;
  text-align: center;
  color: #999;
  font-size: 1.2rem;
}
</style>
