<template>
  <el-main class="main-content">
    <div class="user-profile">
    <el-avatar src="path_to_user_avatar.jpg" size="large"></el-avatar>
    <div class="username">username</div>
  </div>
    <div class="user-rating">
      <el-rate v-model="rating" show-score></el-rate>
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
      this.$router.push(route);
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
}

.user-profile {
  margin-bottom: 20px;
}

.user-rating {
  margin-bottom: 30px;
  font-size: 24px;
}

.feature-row {
  width: 100%;
}

.feature-card {
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border: 2px dashed #b0bec5;
  padding: 20px;
  margin-top: 15px; /* 新增上边距 */
  margin-bottom: 15px; /* 新增下边距 */
}

.feature-icon {
  width: 40px; /* Adjust width as necessary */
  height: 40px; /* Adjust height as necessary */
  margin-bottom: 10px;
}

.card-content {
  text-align: center;
}

.feature-card h3 {
  margin-top: 10px;
}


.feature-card p {
  color: #666;
  font-size: 14px;
}
</style>
