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
      <!-- All Events Section -->
      <div class="blog-section" v-if="activeIndex === '1'">
        <div class="blog-display">
          <el-card v-for="post in filteredEvents" :key="post.id" class="blog-card">
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
      <!-- Ongoing Events Section -->
      <div class="blog-section" v-if="activeIndex === '2'">
        <div class="blog-display">
          <el-card v-for="post in ongoingEvents" :key="post.id" class="blog-card">
            <img :src="post.eventPic" alt="Event Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.organizerId }}
                </div>
                <div class="date-details">
                  <el-icon><Calendar /></el-icon>
                  {{ formatDate(post.startDate) }}
                </div>
              </div>
              <h5>{{ post.title }}</h5>
              <p>{{ post.description }}</p>
            </div>
          </el-card>
        </div>
      </div>
      <!-- Closed Events Section -->
      <div class="blog-section" v-if="activeIndex === '3'">
        <div class="blog-display">
          <el-card v-for="post in closedEvents" :key="post.id" class="blog-card">
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

const allEvents = ref([]);
const ongoingEvents = ref([]);
const closedEvents = ref([]);
const activeIndex = ref('1');
const searchQuery = ref('');
const { proxy } = getCurrentInstance();

const loadAllEvents = async () => {
  const response = await proxy.$api.getAllEvents()
      .catch(error => {
        ElMessage.error(error.message);
      });
  if (response) {
    allEvents.value = response; // 假设 response.data 是你想要的事件数组
  }
  console.log(allEvents.value); // 验证数据是否正确加载
};

onMounted(() => {
  loadAllEvents();
  // Log the initial value of filteredEvents
  console.log(filteredEvents.value);
});

const handleSelect = (index: string) => {
  activeIndex.value = index;
};

// Format date
const formatDate = (dateString: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  };
  return new Date(dateString).toLocaleDateString(undefined, options);
};

// Computed property for filtered events based on search query
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
  border: 1px solid #ddd; /* Add border to the card */
  border-radius: 8px; /* Rounded corners */
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* Light shadow */
}

.blog-card img {
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

.placeholder-section {
  padding: 20px;
  text-align: center;
  color: #999;
  font-size: 1.2rem;
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
