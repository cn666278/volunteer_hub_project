<template>
  <div class="container">
    <div class="item-list">
      <div v-for="item in items" :key="item.id" class="item-card">
        <img :src="item.itemUrl" alt="item image" class="item-image" />
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

const { proxy } = getCurrentInstance();
const items = ref([]);

const fetchItems = async () => {
  const response = await proxy.$api.getItems();
  items.value = response;
};

const redeemItem = async (itemId) => {
  const response = await proxy.$api.redeemItem({
    userId: userStore.user.id,
    itemId: itemId,
  });

  if (response.success) {
    alert('Redemption successful!');
    fetchItems(); // Refresh items
  } else {
    alert('Redemption failed!');
  }
};

onMounted(() => {
  fetchItems();
});
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
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
