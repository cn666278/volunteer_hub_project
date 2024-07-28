<template>
  <div class="Information">
    <div class="search-section">
      <el-input
          placeholder="Type here to search..."
          v-model="searchQuery">
        <template #prefix>
          <el-icon><search /></el-icon>
        </template>
      </el-input>
    </div>
    <div class="all-comments">
      <div v-for="comment in comments" :key="comment.id" class="custom-card">
        <div class="comment-content">
          <div class="avatar-name">
            <div class="comment-avatar">
              <el-avatar :src="comment.avatar"></el-avatar>
              <div class="comment-name">{{ comment.name }}</div>
            </div>
          </div>
          <div class="divider1"></div>
          <div class="comment-text">{{ comment.text }}</div>
        </div>
      </div>
    </div>
  </div>
</template>



<script lang='ts'>
import { ref } from 'vue';
import { ElAvatar, ElInput } from 'element-plus';

export default {
  components: {
    ElAvatar,
    ElInput
  },
  setup() {
    const comments = ref([
      { id: 1, avatar: 'url-to-avatar1', name: 'Organizer1', text: 'Very Good！' },
      { id: 2, avatar: 'url-to-avatar2', name: 'Organizer2', text: 'Satisfied!' },
      { id: 3, avatar: 'url-to-avatar3', name: 'Organizer3', text: 'Not so good.' },
    ]);

    return { comments };
  }
}
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
  min-height: 120px;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-radius: 10px;
  margin-bottom: 20px; // Added margin for separation between cards

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
}

.comment-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 10px;
}

.avatar-name{
  padding: 20px;
}
.comment-avatar {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-right: 10px;
  flex: 0 0 auto;
}

.comment-name {
  margin-top: 5px;
}
.comment-text {
  flex-grow: 1;
  overflow: hidden; // 防止内容溢出
  white-space: nowrap;
  text-overflow: ellipsis; // 添加省略号以避免过长的文本
  margin-right: 10px;
}

.rating-section {
  flex-shrink: 0;
  margin-left: auto; // 推向右侧
  padding-right: 10px; // 保证距离边缘有一定空间
}

.divider1 {
  height: 70%;
  width: 2px;
  background-color: #ddd;
  margin-right: 20px;
}
</style>
