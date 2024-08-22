<template>
  <div class="container">
    <!-- Sidebar Menu for navigation -->
    <div class="sidebar">
      <el-menu
          :default-active="activeIndex"
          class="el-menu-vertical-demo"
          background-color="#ffffff"
          text-color="#303133"
          active-text-color="#409EFF"
          @select="handleSelect"
      >
        <!-- Menu item for 'Events Participated' -->
        <el-menu-item index="2">
          <el-icon><IconMenu /></el-icon>
          <span slot="title">Events Participated</span>
        </el-menu-item>
        <!-- Menu item for 'Events Subscribed' -->
        <el-menu-item index="3">
          <el-icon><Document /></el-icon>
          <span slot="title">Events Subscribed</span>
        </el-menu-item>
      </el-menu>
    </div>

    <!-- Content area displaying events based on the selected menu item -->
    <div class="content">
      <!-- Section for displaying 'Events Participated' -->
      <div class="blog-section" v-if="activeIndex === '2'">
        <div class="blog-display">
          <el-card v-for="post in participatedEvents" :key="post.id" class="blog-card">
            <!-- Event image -->
            <img :src="post.image" alt="Blog Image" class="blog-image" @click="navigateToEvent(post.id)">
            <div class="blog-info">
              <div class="blog-author-date">
                <!-- Organization name and event date -->
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.organizationName }}
                </div>
                <div class="date-details">
                  <el-icon><Calendar /></el-icon>
                  {{ post.date }}
                </div>
              </div>
              <!-- Event title and description -->
              <h5>{{ post.title }}</h5>
              <p>{{ post.description }}</p>
              <!-- Button for discussion and status display -->
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

      <!-- Section for displaying 'Events Subscribed' -->
      <div class="blog-section" v-if="activeIndex === '3'">
        <div class="blog-display">
          <el-card v-for="post in subscribedEvents" :key="post.id" class="blog-card" @click="navigateToEvent(post.id)">
            <!-- Event image -->
            <img :src="post.image" alt="Blog Image" class="blog-image">
            <div class="blog-info">
              <div class="blog-author-date">
                <!-- Organization name and event date -->
                <div class="author-details">
                  <el-icon><User /></el-icon>
                  {{ post.organizationName }}
                </div>
                <div class="date-details">
                  <el-icon><Calendar /></el-icon>
                  {{ post.date }}
                </div>
              </div>
              <!-- Event title and description -->
              <h5>{{ post.title }}</h5>
              <p>{{ post.description }}</p>
              <!-- Display event status -->
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
import { useRouter } from "vue-router";

// Get the current Vue instance and router
const { proxy } = getCurrentInstance();
const router = useRouter();

// Track the active menu index
const activeIndex = ref('2');

// Reference to user store
const userStore = useUser();

// Arrays to hold events data
const subscribedEvents = ref([]);
const participatedEvents = ref([]);

// Function to navigate to the discussion page for a specific event
const navigateToDiscussion = (eventId: string) => {
  router.push({ name: 'EventDiscussion', query: { event: eventId } });
};

// Function to fetch subscribed events for the current user
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

// Function to fetch participated events for the current user
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

// Handle the selection of a menu item and fetch the corresponding events
const handleSelect = (index: string) => {
  activeIndex.value = index;
  if (index === '3') {
    fetchSubscribedEvents();
  } else if (index === '2') {
    fetchParticipatedEvents();
  }
};

// Navigate to the event detail page when an event card is clicked
const navigateToEvent = (eventId: string) => {
  proxy.$router.push({ name: 'EventDetail', params: { id: eventId } });
};

// Load participated events when the component is mounted
onMounted(() => {
  fetchParticipatedEvents();
});
</script>

<style scoped>
.container {
  display: flex;
  height: 100vh;
}

/* Sidebar styling */
.sidebar {
  flex: 0 0 200px;
  background: #fff;
  overflow-y: auto;
}

/* Content area styling */
.content {
  flex-grow: 1;
  overflow-y: auto;
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

/* Grid layout for blog display */
.blog-display {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
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
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  position: relative;
}

/* Image styling for blog cards */
img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

/* Blog information section styling */
.blog-info {
  padding: 14px;
  text-align: left;
  width: 100%;
}

/* Author and date section styling */
.blog-author-date {
  display: flex;
  align-items: center;
  color: #666;
  font-size: 0.9rem;
  margin-bottom: 10px;
}

/* Title styling */
h5 {
  color: #333;
  font-size: 1.2rem;
  font-weight: bold;
  margin: 5px 0;
}

/* Description text styling */
p {
  color: #666;
  font-size: 1rem;
}

/* Status box styling */
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

/* Different status colors */
.status-pending {
  background-color: #f39c12;
}

.status-accepted {
  background-color: #27ae60;
}

/* Button container styling */
.button-container {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

/* Discuss button styling */
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

/* Icon styling */
.el-icon {
  color: #a9181a;
  margin-right: 5px;
}
</style>
