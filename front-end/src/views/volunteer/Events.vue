<template>
  <div class="container">
    <div class="content">
      <!-- Search section, visible only if the active index is '1' -->
      <div v-if="activeIndex === '1'" class="search-section">
        <!-- Input field for searching events -->
        <el-input
            :placeholder="$t('events.searchPlaceholder')"
            v-model="searchQuery">
          <!-- Prefix icon for the search input -->
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>

      <!-- Blog section to display events, visible only if the active index is '1' -->
      <div class="blog-section" v-if="activeIndex === '1'">
        <div class="blog-display">
          <!-- Loop through filtered events and display them as cards -->
          <el-card v-for="post in filteredEvents" :key="post.id" class="blog-card" @click="navigateToEvent(post.id)">
            <!-- Event image -->
            <img :src="post.uploadedFilePath" alt="Event Image" class="blog-image">
            <!-- Event details including author, date, title, and description -->
            <div class="blog-info event-details">
              <div class="blog-author-date event-author-date">
                <!-- Organization name and event start date -->
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

// Reactive variable to hold all events data
const allEvents = ref([]);
// Active tab index, used to control which section is visible
const activeIndex = ref('1');
// Search query entered by the user
const searchQuery = ref('');
// Get the current Vue instance to access proxy methods like routing and API calls
const { proxy } = getCurrentInstance();

// Function to load all events from the server
const loadAllEvents = async () => {
  try {
    // Fetch all events from the API
    const response = await api.getAllEvents();
    const events = response;

    // Fetch and attach images for each event
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
    // Display error message if event loading fails
    ElMessage.error('Failed to load events');
  }
};

// Function to fetch event image by eventPicId
const fetchEventImage = async (eventPicId) => {
  try {
    // API call to get the image file data
    const response = await proxy.$api.getfiles({ id: eventPicId });
    const base64Data = response;
    const mimeType = response.mimeType || 'image/jpeg';

    // Convert base64 string to binary data
    const byteCharacters = atob(base64Data);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: mimeType });

    // Create a URL for the image blob
    return URL.createObjectURL(blob);
  } catch (error) {
    console.error('Error fetching event image:', error);
    return '';
  }
};

// Lifecycle hook to load events when the component is mounted
onMounted(() => {
  loadAllEvents();
});

// Function to navigate to the event detail page when a card is clicked
const navigateToEvent = (eventId: string) => {
  proxy.$router.push({ name: 'EventDetail', params: { id: eventId } });
};

// Function to format event dates for display
const formatDate = (dateString: string) => {
  const options: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  };
  return new Date(dateString).toLocaleDateString(undefined, options);
};

// Computed property to filter events based on the search query
const filteredEvents = computed(() => {
  if (!searchQuery.value) return allEvents.value;
  return allEvents.value.filter(event =>
      event.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      (event.description && event.description.toLowerCase().includes(searchQuery.value.toLowerCase()))
  );
});
</script>

<style scoped>
/* Main container styling */
.container {
  display: flex;
  height: 100vh;
}

/* Content section styling */
.content {
  flex-grow: 1;
  overflow-y: auto;
}

/* Search section styling */
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

/* Icon styling within input fields */
.el-input .el-icon {
  margin-right: 8px;
  color: #409EFF;
}

/* Blog section styling */
.blog-section {
  padding: 20px;
  background-color: #f5f5f5;
  margin: 20px 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;
}

/* Grid layout for displaying blog cards */
.blog-display {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

/* Individual blog card styling */
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

/* Hover effect on blog cards */
.blog-card:hover {
  transform: scale(1.05);
}

/* Blog image styling */
.blog-card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

/* Event details section within the blog card */
.event-details {
  padding: 14px;
  text-align: left;
  width: 100%;
}

/* Styling for author and date details */
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
}

/* Icon styling within event details */
.el-icon {
  color: #a9181a;
  margin-right: 5px;
}

/* Event title styling */
h5 {
  color: #666;
  font-size: 1.2rem;
  font-weight: bold;
  margin: 5px 0;
}

/* Event description styling */
p {
  color: #666;
  font-size: 1rem;
}

/* Responsive styling for smaller screens */
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
