<template>
  <div class="container">
    <div class="content">
      <div v-if="activeIndex === '1'" class="search-section">
        <el-input
            :placeholder="$t('events.searchPlaceholder')"
            v-model="searchQuery">
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>
      <div class="blog-section" v-if="activeIndex === '1'">
        <div class="blog-display">
          <el-card v-for="post in filteredEvents" :key="post.id" class="blog-card" @click="navigateToEvent(post.id)">
            <img :src="post.uploadedFilePath" alt="Event Image" class="blog-image">
            <div class="blog-info event-details">
              <div class="blog-author-date event-author-date">
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.organizationName }}
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

// 获取事件列表并且加载每个事件的组织信息
const loadAllEvents = async () => {
  try {
    const response = await api.getAllEvents();
    const events = response;

    // Fetch images for each event
    allEvents.value = await Promise.all(
        events.map(async (event) => {
          const uploadedFilePath = await fetchEventImage(event.eventPic);
          return {
            ...event,
            uploadedFilePath,
          };
        })
    );
  } catch (error) {
    ElMessage.error('Failed to load events');
  }
};

// Fetch the image for each event
const fetchEventImage = async (eventPicId) => {
  try {
    const response = await proxy.$api.getfiles({ id: eventPicId });
    const base64Data = response;
    const mimeType = response.mimeType || 'image/jpeg';
    const byteCharacters = atob(base64Data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: mimeType });
    return URL.createObjectURL(blob);
  } catch (error) {
    console.error('Error fetching event image:', error);
    return '';
  }
};

onMounted(() => {
  loadAllEvents();
});

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
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
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
  transition: transform 0.2s;
}

.blog-card:hover {
  transform: scale(1.05);
}

.blog-card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

.event-details {
  padding: 14px;
  text-align: left;
  width: 100%;
}

.event-author-date {
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

.el-icon {
  color: #a9181a;
  margin-right: 5px;
}
}

h5 {
  color: #666;
  font-size: 1.2rem;
  font-weight: bold;
  margin: 5px 0;
}

p {
  color: #666;
  font-size: 1rem;
}

@media screen and (max-width: 768px) {
  .blog-display {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }
}

@media screen and (max-width: 480px) {
  .blog-display {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  }

  .blog-card img {
    height: 150px;
  }
}
</style>
