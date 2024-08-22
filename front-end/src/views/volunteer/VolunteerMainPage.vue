<template>
  <el-main class="main-content">
    <div class="user-profile">
      <img src="/src/assets/volunteer1.jpg" class="user-avatar" >
      <div class="username">username</div>
    </div>
    <div class="user-rating">
      <el-rate v-model="rating"></el-rate>
    </div>
    <el-row :gutter="60" class="feature-row">
      <el-col :span="8" v-for="feature in features" :key="feature.name">
        <el-card class="feature-card" @click="navigateTo(feature.route)">
          <div class="card-content">
            <img :src="feature.icon" class="feature-icon" alt="Feature Icon">
            <h3>{{ feature.name }}</h3>
            <p>{{ feature.description }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
export default {
  name: 'VolunteerMainPage',
  data() {
    return {
      rating: 4.5,
      features: [
        { name: 'Personal Information', icon: './src/assets/personal.png', route: '/profile', description: 'Manage your personal information' },
        { name: 'Credentials', icon: './src/assets/credentials.png', route: '/credentials', description: 'View your credentials' },
        { name: 'Events', icon: './src/assets/events.png', route: '/events', description: 'Participate in events' },
        { name: 'Comments', icon: './src/assets/comments.png', route: '/reviews', description: 'Review and rate experiences' },
        { name: 'Information', icon: './src/assets/message.png', route: '/messages', description: 'Check your messages' },
        { name: 'Share', icon: './src/assets/share.png', route: '/share', description: 'Share with your network' }
      ]
    };
  },
  methods: {
    navigateTo(route) {
      this.$router.push(route); // Navigate to the specified route
    }
  }
};
</script>

<style scoped>
.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  color: #333333; /* Set text color to dark gray */
}

.user-profile {
  margin-bottom: 20px;
  width: 100%; /* Ensure it takes the full width of the container */
  display: flex;
  flex-direction: column;
  align-items: center; /* Center child elements */
}

.username {
  margin-top: 10px; /* Adjust spacing as needed */
  text-align: center; /* Ensure text is centered */
  color: #6894c7;
  font-size: 30px;
}

.user-avatar {
  width: 200px; /* Set avatar width */
  height: 200px; /* Set avatar height */
  border-radius: 50%; /* Keep circular shape */
  object-fit: cover; /* Cover the entire area, cropping excess */
  border: 2px solid #b0bec5; /* Add border to the avatar */
}

.user-rating {
  margin-bottom: 30px; /* Add spacing below the rating component */
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
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border: 2px dashed #6894c7; /* Dashed border for feature cards */
  padding: 20px;
  margin-top: 0px;
  margin-bottom: 15px;
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
  margin-top: 10px; /* Add spacing above the feature name */
}

.feature-card p {
  color: #666; /* Set text color for the description */
  font-size: 14px; /* Set font size for the description */
}
</style>
