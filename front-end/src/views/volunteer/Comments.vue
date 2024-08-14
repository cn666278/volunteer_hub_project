<template>
  <div class="Comments">
    <div class="search-section">
      <el-input
          placeholder="在此处输入搜索..."
          v-model="searchQuery">
        <template #prefix>
          <el-icon><search /></el-icon>
        </template>
      </el-input>
    </div>
    <div class="all-comments">
      <div v-for="comment in comments" :key="comment.id" class="custom-card">
        <div class="event-name">{{ comment.eventName }}</div>
        <div class="divider2"></div>
        <div class="comment-content">
          <div class="comment-text">{{ comment.text }}</div>
          <div class="divider1"></div>
          <div class="rating-section">
            <el-rate v-model="comment.rating" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" disabled />
          </div>
        </div>
        <div class="comment-time">{{ comment.time }}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import useUser from '../../store/user';
import api from '../../api/api';  // 确保引入API管理文件

// 定义 proxy 对象
const proxy = {
  $api: api,
};

const userStore = useUser();
const comments = ref([]);

const fetchVolunteerRatings = async () => {
  try {
    const volunteerId = userStore.user.loginId; // 假设 loginId 对应 volunteerId
    const response = await proxy.$api.getRatingsByVolunteerId({ volunteerId });

    if (response && Array.isArray(response)) {  // 确保响应对象存在且data为数组
      if (response.length > 0) {
        // 遍历每个rating，并获取对应的event title
        comments.value = await Promise.all(response.map(async (rating: any) => {
          // 获取每个eventId对应的事件详情
          const eventResponse = await proxy.$api.getEventById({ id: rating.eventId });

          return {
            volunteerId: rating.volunteerId,
            eventName: eventResponse?.title || 'Unknown Event',  // 使用标题替换eventId
            text: rating.comment,
            rating: rating.rating,
            time: rating.createdAt,
          };
        }));
      } else {
        console.log('No ratings found for the given volunteerId');
      }
    } else {
      console.error('获取志愿者评分失败:', response?.msg || '未知错误');
    }
  } catch (error) {
    console.error('获取志愿者评分失败:', error.message || '请求失败');
  }
};


// 在组件挂载时获取评分数据
onMounted(() => {
  fetchVolunteerRatings();
});
</script>






<style lang='scss'>
.search-section {
  padding: 20px;
  display: flex;
  justify-content: center;
  .el-input {
    width: 80%;
    --el-input-border-color: #c0c4cc;

    .el-input__inner {
      padding-left: 35px;
    }

    .el-icon {
      margin-right: 8px;
      color: #409EFF;
    }
  }
}

.all-comments {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.custom-card {
  width: 80%;
  min-height: 150px;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  margin-bottom: 20px;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
}

.event-name {
  font-weight: bold;
  text-align: center;
  margin-top: 10px;
}

.divider2 {
  width: 90%;
  height: 2px;
  background-color: #ddd;
  margin: 10px 0;
}

.comment-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 90%;
  padding: 10px;
}

.comment-text {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-right: 10px;
}

.rating-section {
  flex-shrink: 0;
  margin-left: auto;
  padding-right: 10px;
}

.divider1 {
  height: 70%;
  width: 2px;
  background-color: #ddd;
  margin-right: 20px;
}

.comment-time {
  width: 100%;
  text-align: center;
  padding: 10px;
  color: #999;
  font-size: 0.85rem;
}
</style>
