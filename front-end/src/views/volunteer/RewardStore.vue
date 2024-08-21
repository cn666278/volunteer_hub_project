<template>
  <div class="container">
    <div class="welcome-section">
      <h2>Welcome to the Points Mall</h2>
      <p>Your current points are {{ points }}</p>
    </div>
    <div class="item-list">
      <el-card v-for="item in items" :key="item.id" class="item-card">
        <img :src="item.imageSrc" alt="item image" class="item-image" />
        <div class="item-info">
          <h3 class="item-title">{{ item.itemName }}</h3>
          <p class="item-description">{{ item.itemDescription }}</p>
          <p>Points Required: {{ item.pointsRequired }}</p>
          <el-button class="redeem-button" @click="redeemItem(item.id)">REDEEM</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>



<script setup>
import { ref, onMounted, getCurrentInstance } from 'vue';
import useUser from "../../store/user";
// user store
const userStore = useUser();
const points = ref(0);

const { proxy } = getCurrentInstance();
const items = ref([]);

const fetchItems = async () => {
  const response = await proxy.$api.getItems();

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
        item.imageSrc = URL.createObjectURL(blob);
      } else {
        item.imageSrc = '';
      }
    } catch (error) {
      console.error('Failed to fetch image:', error);
      item.imageSrc = '';
    }
    return item;
  }));

  items.value = itemsWithImages;
};

const fetchPoints = async () => {
  const userId = userStore.user.id;
  const response = await proxy.$api.getVolunteerByUserId({ userId });
  if (response && response.kudosPoints) {
    points.value = response.kudosPoints;
  } else {
    console.error("Failed to fetch points");
  }
};

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
  fetchPoints(); 
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  width: 100%;
  max-width: 1200px;
  padding:20px;
}

.welcome-section {
  text-align: center;
  margin-bottom: 30px;

h2 {
  font-size: 2rem;
  font-weight: bold;
  color: #a9181a;
  margin: 0;
}

p {
  font-size: 1.2rem;
  color: #666;
  margin-top: 10px;
}
}

.item-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  justify-content: center;
  width: 100%;
}

.item-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  padding: 20px;
  text-align: center;
  border-radius: 10px;

&:hover {
   transform: translateY(-5px);
   box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
 }
}

.item-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

.item-info {
  padding: 14px;
  width: 100%;
}

.item-title {
  font-size: 2rem;
  font-weight: bold;
  margin: 10px 0;
  color: #a9181a;
}

.item-description {
  font-size: 1rem;
  color: #666;
  margin: 10px 0;
}

.redeem-button {
  background-color: #a9181a;
  color: #fff;
  font-weight: bold;
  width: 100%;
  height: 50px;
  font-size: 1rem;
  border-radius: 5px;
  transition: background-color 0.3s;

&:hover {
   background-color: darken(#a9181a, 10%);
 }
}

@media screen and (max-width: 768px) {
  .item-list {
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  }
}

@media screen and (max-width: 480px) {
  .item-list {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  }

  .item-image {
    height: 150px;
  }
}
</style>


