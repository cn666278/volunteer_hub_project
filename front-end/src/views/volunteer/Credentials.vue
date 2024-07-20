<template>
  <div class="container">
    <el-button type="primary" class="upload-button">Upload Credentials</el-button> <!-- 新增上传按钮 -->
    <div class="credentials">
      <el-card v-for="item in items" :key="item.id" class="card">
        <template #header>{{ item.title }}</template>
        <img :src="item.imgSrc" class="card-image" />
        <div class="actions">
          <el-button type="info">Edit</el-button>
          <el-button type="primary">Awaiting Approval</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>





<script lang="ts" setup>
import { ref } from 'vue';

const items = ref([
  { id: 1, title: 'Credential1', imgSrc: 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png' },
  { id: 2, title: 'Credential2', imgSrc: 'https://shadow.elemecdn.com/app/element/salad.png'},
  { id: 3, title: 'Credential3', imgSrc: 'https://shadow.elemecdn.com/app/element/pizza.png'}
]);
</script>


<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

.upload-button {
  margin-top: 20px;
  margin-bottom: 20px; /* 确保按钮与卡片之间有适当的间距 */
}

.credentials {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px;
}

.card {
  width: 480px;
  height: 320px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-image {
  width: 100%;
  height: 180px;
  object-fit: cover;
}

.actions {
  padding: 10px;
  display: flex;
  justify-content: space-between;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}
</style>
