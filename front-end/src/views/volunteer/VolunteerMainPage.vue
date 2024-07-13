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
  color: #333333; /* 设置文本颜色为深灰色 */
}

.user-profile {
  margin-bottom: 20px;
  width: 100%; /* 确保占满整个容器宽度 */
  display: flex;
  flex-direction: column;
  align-items: center; /* 保证子元素居中 */
}

.username {
  margin-top: 10px; /* 根据需要调整间距 */
  text-align: center; /* 确保文本居中显示 */
  color: #6894c7;
  font-size: 30px;

}

.user-avatar {
  width: 200px; /* 设置头像宽度 */
  height: 200px; /* 设置头像高度 */
  border-radius: 50%; /* 保持圆形 */
  object-fit: cover; /* 使图片覆盖整个区域，多余的部分会被剪裁掉 */
  border: 2px solid #b0bec5; /* 为头像添加边框 */
}

.user-rating {
  margin-bottom: 30px;
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
  border: 2px dashed #6894c7;
  padding: 20px;
  margin-top: 0px;
  margin-bottom: 15px;
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
  color: #666;
  font-size: 14px;
}
</style>

