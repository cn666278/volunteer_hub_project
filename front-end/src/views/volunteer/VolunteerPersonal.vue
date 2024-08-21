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
import api from '../../api/api';  // Ensure the correct API import

const userStore = useUser();
const uploadedPhotoUrl = ref<string | null>(null);

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

const loadUserProfilePicture = async () => {
  if (userStore.user.photo) {
    const photoId = userStore.user.photo.split('/').pop(); // Extract the 'id' part
    if (photoId) {
      uploadedPhotoUrl.value = await fetchFile(photoId);
    }
  }
};

onMounted(async () => {
  await loadUserProfilePicture();
});

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

const navigateTo = (route: string) => {
  this.$router.push(route);
};
</script>



<style scoped>
.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  color: #a9181a; /* 设置文本颜色为深灰色 */
}

.user-profile {
  margin-bottom: 10px;
  width: 100%; /* 确保占满整个容器宽度 */
  display: flex;
  flex-direction: column;
  align-items: center; /* 保证子元素居中 */
}

.username {
  margin-top: 10px; /* 根据需要调整间距 */
  text-align: center; /* 确保文本居中显示 */
  color: #a9181a;
  font-size: 20px;
  margin-bottom: 10px;
}

.user-avatar {
  width: 100px; /* 设置头像宽度 */
  height: 100px; /* 设置头像高度 */
  border-radius: 50%; /* 保持圆形 */
  object-fit: cover; /* 使图片覆盖整个区域，多余的部分会被剪裁掉 */
  border: 2px solid #a9181a; /* 为头像添加边框 */
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

/* Remove or adjust mobile-specific styles */
@media (max-width: 600px) {
  .main-content {
    padding: 10px; /* 调整内边距为10px */
  }

  .username {
    font-size: 18px; /* 将字体大小调整为18px */
    padding: 0 10px; /* 增加左右的内边距 */
    margin-bottom: 10px; /* 增加与下方元素的间距 */
  }

  .user-avatar {
    width: 80px; /* 将头像宽度调整为80px */
    height: 80px; /* 将头像高度调整为80px */
  }

  .feature-row {
    margin: 0; /* Remove the extra margins */
  }

  .feature-card {
    border: 1px solid #ccc; /* Ensure border style is consistent */
    padding: 20px; /* Match padding to the desktop version */
    margin-top: 10px;
    margin-bottom: 15px;
    width: 100%; /* Make sure the width is consistent */
    box-sizing: border-box; /* Include padding and border in the width calculation */
    background-color: #f5f5f5; /* Ensure background color matches */
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Consistent shadow */
    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
    border-radius: 10px;
  }

  .feature-icon {
    width: 40px; /* Consistent icon size */
    height: 40px;
  }

  .feature-card h3 {
    font-size: 14px; /* Ensure consistency in font size */
    margin: 10px 0;
  }

  .feature-card p {
    font-size: 14px; /* Consistent paragraph text size */
    margin: 0;
    text-align: center;
    word-wrap: break-word;
  }
}
</style>

