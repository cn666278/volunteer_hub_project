<template>
  <div class="container">
    <el-button type="primary" class="upload-button">Upload Credentials</el-button>
    <div class="credentials">
      <el-card v-for="item in items" :key="item.id" class="card">
        <template #header>{{ item.credentialName }}</template>
        <img :src="item.credentialUrl" class="card-image" />
        <div class="actions">
          <el-button type="danger" @click="confirmDelete(item.id)">Delete</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { ElMessageBox } from 'element-plus';
import useUser from '../../store/user';
import api from '../../api/api';  // 确保引入API管理文件

const items = ref([]);
const userStore = useUser();

onMounted(async () => {
  const loginId = userStore.user.id;

  if (loginId) {
    try {
      const response = await api.getCredentialsByVolunteerId({ volunteerId: loginId });
      if (response) {
        items.value = response;
      } else {
        console.error('Failed to load credentials');
      }
    } catch (error) {
      console.error('Error fetching credentials:', error);
    }
  } else {
    console.error('loginId not found');
  }
});

// Function to confirm delete a credential
const confirmDelete = (id) => {
  ElMessageBox.confirm(
      'Are you sure you want to delete this credential?',
      'Warning',
      {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning',
      }
  )
      .then(() => {
        deleteCredential(id);
      })
      .catch(() => {
        console.log('Delete canceled');
      });
};

// Function to delete a credential
const deleteCredential = async (id) => {
  try {
    const response = await api.deleteCredential({ id });
    if (response) {
      items.value = items.value.filter(item => item.id !== id);
    } else {
      console.error('Failed to delete credential');
    }
  } catch (error) {
    console.error('Error deleting credential:', error);
  }
};
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
  margin-bottom: 20px; /* Ensures proper spacing between the button and cards */
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
  justify-content: center; /* Centering the delete button */
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.el-button--primary {
  background-color: #a9181a;
}
</style>
