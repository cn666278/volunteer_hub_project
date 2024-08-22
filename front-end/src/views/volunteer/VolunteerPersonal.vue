<template>
  <div class="volunteerPersonal">
    <!-- Main content -->
    <el-main class="main-content">
      <div class="user-profile">
        <img :src="uploadedPhotoUrl || userStore.user.photo" class="user-avatar">
        <div class="username">{{ userStore.user.username }}</div>
      </div>
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
import { useRouter } from 'vue-router';  // Import useRouter to navigate between pages

const userStore = useUser();
const uploadedPhotoUrl = ref<string | null>(null);
const router = useRouter();  // Get the router instance

// Fetch the user's profile picture from the server
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
      return URL.createObjectURL(blob);
    }
  } catch (error) {
    console.error('Error fetching file:', error);
  }
  return null;
};

// Load the user's profile picture on component mount
const loadUserProfilePicture = async () => {
  if (userStore.user.photo) {
    const photoId = userStore.user.photo.split('/').pop(); // Extract the 'id' part from the photo URL
    if (photoId) {
      uploadedPhotoUrl.value = await fetchFile(photoId);
    }
  }
};

onMounted(async () => {
  await loadUserProfilePicture(); // Load profile picture when component is mounted
});

// Define the feature items to be displayed on the page
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

// Navigate to the route associated with the selected feature
const navigateTo = (route: string) => {
  router.push(route);  // Use the router instance to navigate to the selected route
};
</script>

<style scoped>
.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  color: #a9181a; /* Set text color to deep red */
}

.user-profile {
  margin-bottom: 10px;
  width: 100%; /* Ensure full width of the container */
  display: flex;
  flex-direction: column;
  align-items: center; /* Center child elements */
}

.username {
  margin-top: 10px; /* Adjust spacing as needed */
  text-align: center; /* Center the text */
  color: #a9181a;
  font-size: 20px;
  margin-bottom: 10px;
}

.user-avatar {
  width: 100px; /* Set avatar width */
  height: 100px; /* Set avatar height */
  border-radius: 50%; /* Keep circular shape */
  object-fit: cover; /* Cover the entire area, cropping excess */
  border: 2px solid #a9181a; /* Add border to the avatar */
}

.feature-row {
  width: 100%; /* Full width for the row containing feature cards */
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
  transform: translateY(-5px); /* Lift card on hover */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Enhance shadow on hover */
}

.feature-icon {
  width: 40px;
  height: 40px;
  margin-bottom: 10px; /* Spacing below the icon */
}

.card-content {
  text-align: center; /* Center align the text content */
}

.feature-card h3 {
  margin-top: 10px;
}

.feature-card p {
  color: #a9181a; /* Set paragraph text color to deep red */
  font-size: 14px;
}

/* Responsive styles for mobile devices */
@media (max-width: 600px) {
  .main-content {
    padding: 10px; /* Adjust padding for smaller screens */
  }

  .username {
    font-size: 18px; /* Adjust font size */
    padding: 0 10px; /* Add left and right padding */
    margin-bottom: 10px; /* Increase space below the username */
  }

  .user-avatar {
    width: 80px; /* Adjust avatar width */
    height: 80px; /* Adjust avatar height */
  }

  .feature-row {
    margin: 0; /* Remove extra margins */
  }

  .feature-card {
    border: 1px solid #ccc; /* Ensure consistent border style */
    padding: 20px; /* Match padding to the desktop version */
    margin-top: 10px;
    margin-bottom: 15px;
    width: 100%; /* Ensure full width on smaller screens */
    box-sizing: border-box; /* Include padding and border in the width calculation */
    background-color: #f5f5f5; /* Ensure consistent background color */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Consistent shadow */
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    border-radius: 10px;
  }

  .feature-icon {
    width: 40px; /* Consistent icon size */
    height: 40px;
  }

  .feature-card h3 {
    font-size: 14px; /* Adjust font size */
    margin: 10px 0;
  }

  .feature-card p {
    font-size: 14px; /* Adjust paragraph text size */
    margin: 0;
    text-align: center;
    word-wrap: break-word; /* Ensure text wraps properly */
  }
}
</style>
