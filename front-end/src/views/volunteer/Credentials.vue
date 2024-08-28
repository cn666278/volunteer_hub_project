<template>
  <div class="container">
    <!-- Button to trigger file upload -->
    <el-button type="primary" class="upload-button" @click="triggerFileInput">Upload Credentials</el-button>
    <!-- Hidden file input for uploading files -->
    <input type="file" ref="fileInput" style="display: none;" @change="onFileChange" />
    <!-- Container for displaying uploaded credentials -->
    <div class="credentials">
      <el-card v-for="item in items" :key="item.id" class="card">
        <!-- Display the credential name in the card header -->
        <template #header>{{ item.credentialName }}</template>
        <!-- Display credential image -->
        <img :src="item.credentialUrl || item.uploadedFilePath" class="card-image" />
        <!-- Action buttons for each credential -->
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
import useUser from '../../store/user'; // Import user store to access user data
import api from '../../api/api'; // Import API management file for making API calls

const items = ref([]); // List of uploaded credentials
const userStore = useUser(); // Get user store for retrieving user information
const fileInput = ref<HTMLInputElement | null>(null); // Reference to the hidden file input element

// Function to trigger the hidden file input click
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click();
  }
};

// Function to handle file input change event
const onFileChange = async (event: Event) => {
  const files = (event.target as HTMLInputElement).files; // Get the selected files from the input
  if (files && files.length > 0) {
    const file = files[0];

    // Remove the file extension for the upload
    const fileNameWithoutExtension = file.name.replace(/\.[^/.]+$/, '');

    const formData = new FormData();
    formData.append('file', file); // Append the selected file to form data
    formData.append('filename', fileNameWithoutExtension); // Append the filename without extension
    formData.append('volunteerId', userStore.user.id); // Append the volunteer ID

    try {
      const response = await api.uploadFileForVolunteer(formData); // Upload the file

      await fetchCredentials(); // Refresh the credentials list

    } catch (error) {
      console.error('File upload failed:', error);
    }
  }
};

// Function to fetch the credential list and display the files
const fetchCredentials = async () => {
  const loginId = userStore.user.id; // Retrieve the user ID from the user store

  if (loginId) {
    try {
      const credentialResponse = await api.getCredentialsByVolunteerId({ volunteerId: loginId }); // Fetch credentials by volunteer ID

      if (credentialResponse && Array.isArray(credentialResponse)) {
        items.value = await Promise.all(
            credentialResponse.map(async (credential) => {
              const fileData = await fetchFile(credential.credentialUrl); // Fetch file data using the credential URL
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

// Fetch the credentials when the component is mounted
onMounted(() => {
  fetchCredentials();
});

// Function to fetch file using credential URL
const fetchFile = async (credentialUrl: string) => {
  try {
    const response = await api.getfiles({ id: credentialUrl.split('/').pop() }); // Get file details by extracting ID from URL
    if (response) {
      const base64Data = response;
      const mimeType = response.mimeType || 'image/jpeg'; // Default to JPEG if MIME type is not provided
      const byteCharacters = atob(base64Data); // Decode base64 data
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: mimeType });
      return URL.createObjectURL(blob); // Convert to a blob URL for display
    }
  } catch (error) {
    console.error('Error fetching file:', error);
  }
};

// Function to confirm deletion of a credential
const confirmDelete = (id) => {
  ElMessageBox.confirm(
      'Are you sure you want to delete this credential?', // Confirmation message
      'Warning', // Title of the confirmation box
      {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning',
      }
  )
      .then(() => {
        deleteCredential(id); // Delete the credential if confirmed
      })
      .catch(() => {
        console.log('Delete canceled'); // Handle cancellation of delete
      });
};

// Function to delete a credential
const deleteCredential = async (id) => {
  try {
    const response = await api.deleteCredential({ id }); // Call API to delete the credential
    if (response) {
      items.value = items.value.filter((item) => item.id !== id); // Update the items list to remove the deleted item
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
