<template>
  <div class="container">
    <!-- Welcome Section with User Points -->
    <div class="welcome-section">
      <h2>Welcome to the Points Mall</h2>
      <p>Your current points are {{ points }}</p>
    </div>

    <!-- Item List Section -->
    <div class="item-list">
      <!-- Display each item in a card -->
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

// Initialize user store and reactive variables
const userStore = useUser();
const points = ref(0); // User's current points
const { proxy } = getCurrentInstance();
const items = ref([]); // List of items available for redemption

// Fetch items available for redemption from the server
const fetchItems = async () => {
  const response = await proxy.$api.getItems();

  // Load item images from the server and handle errors
  const itemsWithImages = await Promise.all(response.map(async (item) => {
    try {
      const imageResponse = await proxy.$api.getfiles({ id: item.itemUrl });
      if (imageResponse) {
        const base64Data = imageResponse;
        const mimeType = imageResponse.mimeType || 'image/jpeg'; // Default to JPEG if MIME type is missing
        const byteCharacters = atob(base64Data);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: mimeType });
        item.imageSrc = URL.createObjectURL(blob); // Convert image to a URL
      } else {
        item.imageSrc = ''; // Fallback if no image found
      }
    } catch (error) {
      console.error('Failed to fetch image:', error);
      item.imageSrc = ''; // Fallback if an error occurs
    }
    return item;
  }));

  items.value = itemsWithImages; // Set the items with images
};

// Fetch the user's current points from the server
const fetchPoints = async () => {
  const userId = userStore.user.id;
  const response = await proxy.$api.getVolunteerByUserId({ userId });
  if (response && response.kudosPoints) {
    points.value = response.kudosPoints; // Update points
  } else {
    console.error("Failed to fetch points");
  }
};

// Handle item redemption by the user
const redeemItem = async (itemId) => {
  const response = await proxy.$api.redeemItem({
    userId: userStore.user.id,
    itemId: itemId,
  });

  if (response) {
    alert('Redemption successful!');
    fetchItems(); // Refresh items after redemption
    fetchPoints(); // Refresh points after redemption
  } else {
    alert('Redemption failed!');
  }
};

// Fetch items and points when the component is mounted
onMounted(() => {
  fetchItems();
  fetchPoints();
});
</script>

<style scoped>
/* Container styling for the Points Mall */
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  width: 100%;
  max-width: 1200px;
  padding: 20px;
}

/* Styling for the welcome section */
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

/* Styling for the item list */
.item-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  justify-content: center;
  width: 100%;
}

/* Styling for each item card */
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

/* Styling for item images */
.item-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
}

/* Styling for item information */
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

/* Styling for the redeem button */
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

/* Responsive styling for different screen sizes */
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
