<template>
  <div class="container">
    <h2>Welcome to the Points Mall. Your current points are {{ points }}</h2>
    <div class="item-list">
      <div v-for="item in items" :key="item.id" class="item-card">
        <img :src="item.imageSrc" alt="item image" class="item-image" />
        <h3>{{ item.itemName }}</h3>
        <p>{{ item.itemDescription }}</p>
        <p>Points Required: {{ item.pointsRequired }}</p>
        <el-button @click="redeemItem(item.id)">Redeem</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, getCurrentInstance } from 'vue';
import useUser from "../../store/user";
// user store
const userStore = useUser();
const points = ref(0); // 用于存储当前用户的积分

const { proxy } = getCurrentInstance();
const items = ref([]);

// 获取商品列表
const fetchItems = async () => {
  const response = await proxy.$api.getItems();

  // 对每个商品调用 getfiles 接口获取图片
  const itemsWithImages = await Promise.all(response.map(async (item) => {
    try {
      const imageResponse = await proxy.$api.getfiles({ id: item.itemUrl });
      if (imageResponse) {
        const base64Data = imageResponse;
        const mimeType = imageResponse.mimeType || 'image/jpeg';
        const byteCharacters = atob(base64Data);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: mimeType });
        item.imageSrc = URL.createObjectURL(blob); // 将 blob URL 存储在 item 中
      } else {
        item.imageSrc = ''; // 如果获取失败，则留空
      }
    } catch (error) {
      console.error('Failed to fetch image:', error);
      item.imageSrc = ''; // 如果发生错误，也留空
    }
    return item;
  }));

  items.value = itemsWithImages;
};

// 获取当前用户的积分
const fetchPoints = async () => {
  const userId = userStore.user.id; // 获取当前登录用户的ID
  const response = await proxy.$api.getVolunteerByUserId({ userId });
  if (response && response.kudosPoints) {
    points.value = response.kudosPoints; // 更新积分
  } else {
    console.error("Failed to fetch points");
  }
};

// 兑换商品
const redeemItem = async (itemId) => {
  const response = await proxy.$api.redeemItem({
    userId: userStore.user.id,
    itemId: itemId,
  });

  if (response) {
    alert('Redemption successful!');
    fetchItems(); // Refresh items
    fetchPoints(); // Refresh points after redemption
  } else {
    alert('Redemption failed!');
  }
};

onMounted(() => {
  fetchItems();
  fetchPoints(); // 页面加载时获取当前用户的积分
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

.item-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  max-width: 1200px;
}

.item-card {
  border: 1px solid #ddd;
  padding: 10px;
  margin: 10px;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: calc(33.333% - 20px); /* Subtracting margin */
  box-sizing: border-box;
}

.item-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

h3 {
  margin: 10px 0;
}

p {
  margin: 5px 0;
}
</style>
