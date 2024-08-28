<template>
  <div class="PersonalInfo">
    <div class="user-profile">
      <!-- Display user's avatar, clicking triggers file input for photo change -->
      <img :src="uploadedPhotoUrl || userStore.user.photo" class="user-avatar" @click="triggerFileInput" />
      <input type="file" ref="fileInput" style="display: none;" @change="onFileChange" />
    </div>

    <!-- Information Display -->
    <div class="info-display">
      <div class="info-item custom-card">
        <div class="info-label">Username:</div>
        <div class="info-value">{{ userStore.user.username }}</div>
      </div>
      <div class="info-item custom-card">
        <div class="info-label">Phone:</div>
        <div class="info-value">{{ userStore.user.phone }}</div>
      </div>
      <div class="info-item custom-card">
        <div class="info-label">Email:</div>
        <div class="info-value">{{ userStore.user.email }}</div>
      </div>
    </div>

    <!-- Buttons -->
    <div class="action-buttons">
      <button class="edit-button" @click="showEditModal = true">Edit Info</button>
      <button class="change-password-button" @click="showChangePasswordModal = true">Change Password</button>
    </div>

    <!-- Edit Info Modal -->
    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <div class="modal-card custom-card">
          <span class="close" @click="showEditModal = false">&times;</span>
          <h2>Edit Info</h2>
          <div class="modal-body">
            <!-- Fields for editing user information -->
            <div class="info-item custom-card">
              <label for="username" class="info-label">Username:</label>
              <input type="text" v-model="editUsername" id="username" class="info-input" />
            </div>
            <div class="info-item custom-card">
              <label for="phone" class="info-label">Phone:</label>
              <input type="text" v-model="editPhone" id="phone" class="info-input" />
            </div>
            <div class="info-item custom-card">
              <label for="email" class="info-label">Email:</label>
              <input type="email" v-model="editEmail" id="email" class="info-input" />
            </div>
            <button @click="updateUserProfile" class="submit-button">Submit</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Change Password Modal -->
    <div v-if="showChangePasswordModal" class="modal">
      <div class="modal-content">
        <div class="modal-card custom-card">
          <span class="close" @click="showChangePasswordModal = false">&times;</span>
          <h2>Change Password</h2>
          <div class="modal-body">
            <!-- Fields for changing password -->
            <div class="info-item custom-card">
              <label for="currentPassword" class="info-label">Current Password:</label>
              <input type="password" v-model="currentPassword" id="currentPassword" class="info-input" />
            </div>
            <div class="info-item custom-card">
              <label for="newPassword" class="info-label">New Password:</label>
              <input type="password" v-model="newPassword" id="newPassword" class="info-input" />
            </div>
            <div class="info-item custom-card">
              <label for="confirmPassword" class="info-label">Confirm Password:</label>
              <input type="password" v-model="confirmPassword" id="confirmPassword" class="info-input" />
            </div>
            <button @click="changePassword" class="submit-button">Submit</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import useUser from '../../store/user';
import api from '../../api/api'; // Ensure the API management file is properly imported

const userStore = useUser();
const showEditModal = ref(false); // Controls the visibility of the edit info modal
const showChangePasswordModal = ref(false); // Controls the visibility of the change password modal
const editUsername = ref(userStore.user.username);
const editPhone = ref(userStore.user.phone);
const editEmail = ref(userStore.user.email);
const uploadedPhotoUrl = ref<string | null>(null); // Tracks the uploaded photo URL
const currentPassword = ref('');
const newPassword = ref('');
const confirmPassword = ref('');
const fileInput = ref<HTMLInputElement | null>(null);

// Trigger the file input dialog for photo upload
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click();
  }
};

// Handle the file change event when a new photo is selected
const onFileChange = async (event: Event) => {
  const files = (event.target as HTMLInputElement).files;
  if (files && files.length > 0) {
    const file = files[0];

    const formData = new FormData();
    formData.append('file', file);
    formData.append('filename', userStore.user.username);
    formData.append('volunteerId', userStore.user.id);

    try {
      const response = await api.uploadAvatorForVolunteer(formData);
      const photoId = response.match(/\d+$/)[0];

      // Update user's photo field and display
      await updateUserProfilePhoto(photoId);
      uploadedPhotoUrl.value = await fetchFile(photoId);

    } catch (error) {
      console.error('File upload failed:', error);
    }
  }
};

// Fetch and display the uploaded photo
const fetchFile = async (fileId: string) => {
  try {
    const response = await api.getfiles({ id: fileId });
    if (response) {
      const base64Data = response;
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });
      return URL.createObjectURL(blob);
    }
  } catch (error) {
    console.error('Error fetching file:', error);
  }
  return null;
};

// Update user's profile photo in the store
const updateUserProfilePhoto = async (photoId: string) => {
  try {
    userStore.setUser({
      ...userStore.user,
      photo: photoId,
    });
  } catch (error) {
    console.error('Error updating user photo:', error);
  }
};

// Update user profile information
const updateUserProfile = async () => {
  try {
    const response = await api.updateUserProfile({
      loginId: userStore.user.id,
      username: editUsername.value,
      phone: editPhone.value,
      email: editEmail.value,
    });

    if (response) {
      userStore.setUser({
        ...userStore.user,
        username: editUsername.value,
        phone: editPhone.value,
        email: editEmail.value,
      });
      showEditModal.value = false;
      alert('User info updated successfully');
    } else {
      throw new Error('Failed to update user info');
    }
  } catch (error) {
    console.error('Error updating user info:', error);
    alert('Failed to update user info');
  }
};

// Change user password
const changePassword = async () => {
  if (newPassword.value !== confirmPassword.value) {
    alert("New passwords do not match");
    return;
  }

  try {
    const response = await api.changeUserPassword({
      loginId: userStore.user.id,
      currentPassword: currentPassword.value,
      newPassword: newPassword.value,
    });

    if (response) {
      alert("Password changed successfully");
      showChangePasswordModal.value = false;
    } else {
      alert(response || "Failed to change password");
    }
  } catch (error) {
    console.error("Error changing password:", error);
    alert("Failed to change password");
  }
};

// Load the user's profile picture on component mount
const loadUserProfilePicture = async () => {
  if (userStore.user.photo) {
    const photoId = userStore.user.photo.split('/').pop();
    if (photoId) {
      uploadedPhotoUrl.value = await fetchFile(photoId);
    }
  }
};

onMounted(async () => {
  if (!userStore.user.id) {
    const loginId = sessionStorage.getItem('loginId');
    if (loginId) {
      try {
        const response = await fetch(`/api/users/${loginId}`);
        if (!response.ok) {
          throw new Error('Failed to fetch user details');
        }
        const data = await response.json();
        console.log('Fetched user data:', data);
        userStore.setUser(data);
      } catch (error) {
        console.error('Failed to fetch user details:', error);
      }
    }
  }
  await loadUserProfilePicture();
});
</script>

<style lang="scss" scoped>
.PersonalInfo {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding-bottom: 100px;
}

.user-profile {
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #a9181a;
  cursor: pointer; /* Indicates the area is clickable */
}

.info-display {
  width: 100%;
  max-width: 600px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 10px 20px;
  border-radius: 10px;
  background-color: #f5f5f5;
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
}

.info-label {
  font-weight: bold;
  color: #333;
  width: 30%;
  text-align: right;
  padding-right: 10px;
}

.info-value {
  color: #555;
  text-align: left;
  width: 70%;
}

.info-input {
  width: 70%;
  padding: 8px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

.info-input:focus {
  border-color: #409eff;
  box-shadow: 0 0 5px rgba(64, 158, 255, 0.5);
  outline: none;
}

.submit-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #409eff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.submit-button:hover {
  background-color: #66b1ff;
}

.action-buttons {
  margin-top: 20px;
  display: flex;
  gap: 15px; /* Space between buttons */
}

.edit-button,
.change-password-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.edit-button {
  background-color: #409eff;
  color: white;
}

.change-password-button {
  background-color: #f56c6c;
  color: white;
}

.edit-button:hover {
  background-color: #66b1ff;
}

.change-password-button:hover {
  background-color: #ff7f7f;
}

.modal-card {
  padding: 20px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  position: relative;
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 30px; /* Make the close button larger */
  cursor: pointer;
  color: #999;

  &:hover {
    color: #333;
  }
}
</style>
