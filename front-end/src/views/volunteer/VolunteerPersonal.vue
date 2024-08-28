<template>
  <div class="volunteerPersonal">
    <!-- Main content area -->
    <el-main class="main-content">
      <!-- User Profile Section -->
      <div class="user-profile">
        <img :src="uploadedPhotoUrl || userStore.user.photo" class="user-avatar">
        <div class="username">{{ userStore.user.username }}</div>
      </div>

      <!-- Features Section with Cards -->
      <el-row :gutter="20" class="feature-row">
        <el-col :span="24" :md="8" v-for="feature in features" :key="feature.name">
          <el-card class="feature-card" @click="navigateTo(feature.route)">
            <div class="card-content">
              <img :src="feature.icon" class="feature-icon" alt="Feature Icon">
              <h3>{{ $t(feature.name) }}</h3>
              <p>{{ feature.description }}</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import useUser from '../../store/user';
import api from '../../api/api';
import { useRouter } from 'vue-router';

const userStore = useUser();
const uploadedPhotoUrl = ref<string | null>(null); // Reactive variable for uploaded photo URL
const router = useRouter(); // Initialize router instance

// Function to fetch file from server
const fetchFile = async (fileId: string) => {
  try {
    const response = await api.getfiles({ id: fileId });
    if (response) {
      const base64Data = response;
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });
      return URL.createObjectURL(blob); // Convert image to URL
    }
  } catch (error) {
    console.error('Error fetching file:', error);
  }
  return null;
};

// Load user profile picture
const loadUserProfilePicture = async () => {
  if (userStore.user.photo) {
    const photoId = userStore.user.photo.split('/').pop(); // Extract the file ID from the photo URL
    if (photoId) {
      uploadedPhotoUrl.value = await fetchFile(photoId);
    }
  }
};

// Call the function when the component is mounted
onMounted(async () => {
  await loadUserProfilePicture();
});

// Define features for the volunteer dashboard
const features = [
  {
    name: 'personal.personalInformation',
    icon: './src/assets/personal.png',
    route: '/volunteer/personalInfo',
  },
  {
    name: 'personal.credentials',
    icon: './src/assets/credentials.png',
    route: '/volunteer/credentials',
  },
  {
    name: 'personal.events',
    icon: './src/assets/events.png',
    route: '/volunteer/events_volunteer',
  },
  {
    name: 'personal.comments',
    icon: './src/assets/comments.png',
    route: '/volunteer/comments',
  },
  {
    name: 'personal.information',
    icon: './src/assets/message.png',
    route: '/volunteer/information',
  },
  {
    name: 'personal.share',
    icon: './src/assets/share.png',
    route: '/volunteer/share',
  }
];

// Navigate to different sections based on user selection
const navigateTo = (route: string) => {
  router.push(route);
};
</script>

<style scoped>
/* Main content styling */
.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  color: #a9181a;
}

/* User profile styling */
.user-profile {
  margin-bottom: 10px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.username {
  margin-top: 10px;
  text-align: center;
  color: #a9181a;
  font-size: 20px;
  margin-bottom: 10px;
}

.user-avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #a9181a;
}

/* Feature row and card styling */
.feature-row {
  width: 100%;
}

.feature-card {
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
  margin-top: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  border-radius: 10px;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.feature-icon {
  width: 40px;
  height: 40px;
  margin-bottom: 10px;
}

.card-content {
  text-align: center;
}

.feature-card h3 {
  margin-top: 10px;
}

.feature-card p {
  color: #a9181a;
  font-size: 14px;
}

/* Responsive design for smaller screens */
@media (max-width: 600px) {
  .main-content {
    padding: 10px;
  }

  .username {
    font-size: 18px;
    padding: 0 10px;
    margin-bottom: 10px;
  }

  .user-avatar {
    width: 80px;
    height: 80px;
  }

  .feature-row {
    margin: 0;
  }

  .feature-card {
    border: 1px solid #ccc;
    padding: 20px;
    margin-top: 10px;
    margin-bottom: 15px;
    width: 100%;
    box-sizing: border-box;
    background-color: #f5f5f5;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    border-radius: 10px;
  }

  .feature-icon {
    width: 40px;
    height: 40px;
  }

  .feature-card h3 {
    font-size: 14px;
    margin: 10px 0;
  }

  .feature-card p {
    font-size: 14px;
    margin: 0;
    text-align: center;
    word-wrap: break-word;
  }
}
</style>
