<template>
  <div class="container">
    <el-button type="primary" class="upload-button" @click="triggerFileInput">Upload Credentials</el-button>
    <input type="file" ref="fileInput" style="display: none;" @change="onFileChange" />
    <div class="credentials">
      <el-card v-for="item in items" :key="item.id" class="card">
        <template #header>{{ item.credentialName }}</template>
        <img :src="item.credentialUrl || item.uploadedFilePath" class="card-image" />
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
import api from '../../api/api'; // 确保引入API管理文件

const items = ref([]);
const userStore = useUser();
const fileInput = ref<HTMLInputElement | null>(null);

const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click();
  }
};

const onFileChange = async (event: Event) => {
  const files = (event.target as HTMLInputElement).files;
  if (files && files.length > 0) {
    const file = files[0];

    const fileNameWithoutExtension = file.name.replace(/\.[^/.]+$/, '');

    const formData = new FormData();
    formData.append('file', file);
    formData.append('filename', fileNameWithoutExtension);
    formData.append('volunteerId', userStore.user.id);
    try {
      const response = await api.uploadFileForVolunteer(formData);

      await fetchCredentials();

    } catch (error) {
      console.error('File upload failed:', error);
    }
  }
};

// Function to fetch the credential list and display the files
const fetchCredentials = async () => {
  const loginId = userStore.user.id;

  if (loginId) {
    try {
      const credentialResponse = await api.getCredentialsByVolunteerId({ volunteerId: loginId });

      if (credentialResponse && Array.isArray(credentialResponse)) {
        items.value = await Promise.all(
            credentialResponse.map(async (credential) => {
              const fileData = await fetchFile(credential.credentialUrl);
              return {
                id: credential.id,
                credentialName: credential.credentialName,
                uploadedFilePath: fileData,
              };
            })
        );
      } else {
        console.error('Failed to load credentials');
      }
    } catch (error) {
      console.error('Error fetching credentials:', error);
    }
  } else {
    console.error('loginId not found');
  }
};

onMounted(() => {
  fetchCredentials();
});

// Function to fetch file using credentialUrl
const fetchFile = async (credentialUrl: string) => {
  try {
    const response = await api.getfiles({ id: credentialUrl.split('/').pop() });
    if (response) {
      const base64Data = response;
      const mimeType = response.mimeType || 'image/jpeg';
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: mimeType });
      return URL.createObjectURL(blob);
    }
  } catch (error) {
    console.error('Error fetching file:', error);
  }
};

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
      items.value = items.value.filter((item) => item.id !== id);
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
