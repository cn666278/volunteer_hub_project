<template>
  <div class="Comments">
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
          <div class="comment-info">
            <div class="event-name">{{ comment.eventName }}</div>
            <div class="comment-text">{{ comment.text }}</div>
          </div>
          <div class="divider1"></div>
          <div class="rating-section">
            <el-rate v-model="comment.rating" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang='ts'>
import { ref } from 'vue';
import { ElRate, ElInput } from 'element-plus';

export default {
  components: {
    ElRate,
    ElInput
  },
  setup() {
    const comments = ref([
      { id: 1, eventName: 'Event 1', text: 'Very Good！', rating: 5 },
      { id: 2, eventName: 'Event 2', text: 'Satisfied!', rating: 4 },
      { id: 3, eventName: 'Event 3', text: 'Not so good.', rating: 3 },
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
  margin-bottom: 20px;

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

.comment-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.event-name {
  font-weight: bold;
  margin-bottom: 5px;
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
</style>
