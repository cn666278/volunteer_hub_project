<template>
  <div>
    <input type="file" @change="onFileChange" />
    <el-input v-model="filename" placeholder="Enter file name" />
    <el-button @click="uploadFile">Upload</el-button>
    <p v-if="uploadedFilePath">Uploaded File Path: {{ uploadedFilePath }}</p>
  </div>
</template>

<script setup>
import { ref, getCurrentInstance } from 'vue';
const { proxy } = getCurrentInstance();

const file = ref(null);
const filename = ref('');
const uploadedFilePath = ref('');

const onFileChange = (e) => {
  file.value = e.target.files[0];
};

const uploadFile = async () => {
  if (!file.value || !filename.value.trim()) {
    alert("Please select a file and enter a filename.");
    return;
  }

  const formData = new FormData();
  formData.append('file', file.value);
  formData.append('filename', filename.value.trim());

  try {
    const response = await proxy.$api.uploadFile(formData);
    // Assuming the response structure is { code: 200, data: "File uploaded successfully with ID: {file_id}", msg: "success" }
    const fileId = response.match(/\d+$/)[0]; // Extract the file ID from the response data
    uploadedFilePath.value = `/files/${fileId}`;
    console.log("File uploaded successfully:", response);
  } catch (error) {
    console.error("File upload failed:", error);
  }
};
</script>

<style scoped>
</style>
