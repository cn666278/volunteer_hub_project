<template>
  <div class="event-detail" v-if="event">
    <!-- Intro Section with text and single image carousel -->
    <div class="intro-section">
      <div class="image-carousel">
        <img :src="uploadedFilePath" alt="Event Image" class="carousel-image" />
      </div>
      <div class="text-content">
        <h1>{{ event.title }}</h1>
        <p>{{ event.organizationName }}</p>
      </div>
    </div>

    <!-- Introduction Sections -->
    <div class="project-introduction-middle">
      <!-- LOCATION, DATE, and REWARD POINTS Cards -->
      <div class="card-container">
        <div v-for="(item, index) in introSections" :key="index" class="custom-card">
          <div :class="['event-name', { 'red-title': item.title === 'LOCATION' }]">
            {{ item.title }}
          </div>
          <div class="divider2"></div>
          <div class="comment-content">
            <div :class="['comment-text', { 'full-text': item.title === 'DATE' }]">{{ item.value }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Description Section -->
    <div class="project-introduction-middle">
      <div class="card-container">
        <div class="custom-card wide-card">
          <div class="event-name">
            Description
          </div>
          <div class="divider2"></div>
          <div class="comment-content">
            <div class="comment-text full-text">{{ event.description }}</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Volunteer Recruitment Information Section -->
    <div class="project-introduction-middle">
      <div class="card-container">
        <div class="custom-card wide-card">
          <div class="event-name">
            Volunteer Information
          </div>
          <div class="divider2"></div>
          <div class="comment-content">
            <div v-for="(role, index) in roles" :key="index" class="comment-text full-text">
              · {{ role.roleName }} (NUMBER REQUIRED: {{ role.volunteerCount }}):
              <br />
              {{ role.roleDescription }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Action Buttons -->
    <div class="action-buttons">
      <button class="styled-button primary" @click="openApplyDialog">
        Apply to be a volunteer
      </button>
      <button
          class="styled-button success"
          :class="{'subscribed': isSubscribed}"
          @click="subscribeToEvent"
          :disabled="isSubscribed"
      >
        {{ isSubscribed ? 'Subscribed' : 'Subscribe This Event' }}
      </button>
    </div>

    <!-- Apply Modal -->
    <div v-if="applyDialogVisible" class="modal">
      <div class="modal-content">
        <h3>Apply to be a Volunteer</h3>
        <div class="apply-form">
          <div class="form-group">
            <label for="event-name">Event name</label>
            <input id="event-name" v-model="event.title" readonly />
          </div>
          <!-- 新增的日期行 -->
          <div class="form-group">
            <label for="event-dates">Event Dates</label>
            <input id="event-dates" :value="`${formattedEventStartDate} - ${formattedEventEndDate}`" readonly />
          </div>

          <!-- 修改后的Terms & Conditions 复选框 -->
          <div class="form-group terms-conditions">
            <label for="terms-checkbox">
              Please ensure that you have understood and agreed to our
              <a href="https://wsa.wales/terms-conditions/" target="_blank">Terms & Conditions</a>.
            </label>
            <input type="checkbox" id="terms-checkbox" v-model="agreedToTerms" />
          </div>
        </div>
        <div class="modal-footer">
          <button @click="applyDialogVisible = false">Cancel</button>
          <button class="primary" @click="submitApplication">Submit</button>
        </div>
      </div>
    </div>

  </div>

  <div v-else>
    <p>Loading...</p>
  </div>
</template>

<script lang="ts">
import { useRoute } from 'vue-router';
import { ref, onMounted, computed } from 'vue';
import api from '../../api/api';
import useUser from '../../store/user';
import { ElMessage } from 'element-plus';
import { useI18n } from 'vue-i18n';

export default {
  name: 'EventDetail',
  setup() {
    const route = useRoute();
    const event = ref(null);
    const roles = ref([]); // 用于存储角色信息
    const uploadedFilePath = ref(''); // 用于存储图片路径
    const { t } = useI18n(); // 国际化支持
    const userStore = useUser();
    const isSubscribed = ref(false);
    const agreedToTerms = ref(false); // 用于控制复选框是否选中

    const introSections = computed(() => [
      { title: 'LOCATION', value: event.value?.location },
      { title: 'DATE', value: `${formattedEventStartDate.value} - ${formattedEventEndDate.value}` },
      { title: 'Reward Points', value: event.value?.pointsAwarded },
    ]);

    const applyDialogVisible = ref(false);

    const loadEvent = async () => {
      const eventId = route.params.id;
      if (eventId) {
        const response = await api.getEventById({ id: eventId }).catch((error) => {
          console.error(error.message);
        });
        if (response) {
          event.value = response;

          // 加载图片
          uploadedFilePath.value = await fetchEventImage(response.eventPic);

          // 加载角色信息
          roles.value = await fetchRoles(eventId);
        }
      } else {
        console.error('Event ID is not provided');
      }
    };

    // 获取事件图片
    const fetchEventImage = async (eventPicId) => {
      try {
        const response = await api.getfiles({ id: eventPicId });
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
      } catch (error) {
        console.error('Error fetching event image:', error);
        return '';
      }
    };

    // 获取角色信息
    const fetchRoles = async (eventId) => {
      try {
        const response = await api.getRolesByEventId({ eventId });
        return response;
      } catch (error) {
        console.error('Error fetching roles:', error);
        return [];
      }
    };

    const subscribeToEvent = async () => {
      try {
        const loginId = userStore.user.id;
        if (!loginId) {
          alert('Please log in to subscribe to this event');
          return;
        }

        const eventId = event.value?.id;
        if (!eventId) {
          alert('Event ID is missing');
          return;
        }

        const response = await api.subscribeForEvent({
          eventId: eventId,
          volunteerId: loginId,
          roleId: 1,
          status: 'subscribed',
        });

        if (response.includes('Successfully subscribed to the event')) {
          ElMessage({
            message: 'Successfully subscribed to the event.',
            type: 'success',
          });
          isSubscribed.value = true;
        } else if (response.includes('already subscribed')) {
          ElMessage({
            message: 'You have already subscribed to this event',
            type: 'warning',
          });
          isSubscribed.value = true;
        }
      } catch (error) {
        console.error('Error subscribing to event:', error);
        alert('Failed to subscribe to the event');
      }
    };

    const openApplyDialog = () => {
      applyDialogVisible.value = true;
    };

    const submitApplication = async () => {
      if (agreedToTerms.value == false) {

        // Show an alert if the checkbox is not checked
        ElMessage({
          message: 'Please agree to the Terms & Conditions before submitting.',
          type: 'warning',
        });
        return; // Exit the function to prevent submission
      }

      try {
        const loginId = userStore.user.id;
        if (!loginId) {
          alert('Please log in to apply for this event');
          return;
        }

        const eventId = event.value?.id;
        if (!eventId) {
          alert('Event ID is missing');
          return;
        }

        const response = await api.registerForEvent({
          eventId: eventId,
          volunteerId: loginId,
          roleId: 1,
          status: 'pending',
        });

        if (response.includes('Application submitted successfully')) {
          ElMessage({
            message: 'Application submitted successfully.',
            type: 'success',
          });
          applyDialogVisible.value = false;
        } else {
          ElMessage({
            message: 'Failed to submit application.',
            type: 'warning',
          });
        }
      } catch (error) {
        console.error('Error submitting application:', error);
        alert('Failed to submit application');
      }
    };

    const formattedEventStartDate = computed(() => {
      if (event.value && event.value.startDate) {
        return new Date(event.value.startDate).toLocaleDateString('en-US', {
          year: 'numeric',
          month: 'short',
          day: 'numeric',
        });
      }
      return '';
    });

    const formattedEventEndDate = computed(() => {
      if (event.value && event.value.endDate) {
        return new Date(event.value.endDate).toLocaleDateString('en-US', {
          year: 'numeric',
          month: 'short',
          day: 'numeric',
        });
      }
      return '';
    });


    onMounted(() => {
      loadEvent();
    });

    return {
      event,
      roles,
      introSections,
      uploadedFilePath, // 返回图片路径
      subscribeToEvent,
      openApplyDialog,
      applyDialogVisible,
      submitApplication,
      formattedEventStartDate,
      formattedEventEndDate,
      isSubscribed,
      agreedToTerms, // 绑定复选框的状态
    };
  },
};
</script>

<style lang="scss" scoped>
.event-detail {
  .intro-section {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 20px;
    background-color: #fff;
    border-bottom: 2px solid #ececec;

    .image-carousel {
      width: 50%;
      padding-right: 20px;

      .carousel-image {
        width: 100%;
        height: auto;
        object-fit: cover;
        border-radius: 8px;
      }
    }

    .text-content {
      width: 50%;
      padding-left: 20px;

      h1 {
        font-size: 2rem;
        font-weight: bold;
        margin: 0.5rem 0;
        color: #a9181a;
      }

      p {
        font-size: 1.2rem;
        color: #a9181a;
        margin-bottom: 20px;
      }
    }

    @media (max-width: 800px) {
      flex-direction: column;
      align-items: center;

      .image-carousel,
      .text-content {
        width: 100%;
        padding: 0;
      }

      .image-carousel {
        padding-bottom: 20px;
      }

      .text-content {
        padding-bottom: 20px;
        text-align: center;
      }
    }
  }

  .project-introduction-middle {
    .card-container {
      display: flex;
      justify-content: space-around;
      padding: 40px 20px;
    }

    .custom-card {
      width: 30%;
      height: auto;
      border: 1px solid #ccc;
      background-color: #f5f5f5;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
      display: flex;
      flex-direction: column;
      align-items: center;
      border-radius: 10px;
      margin-bottom: 20px;
      padding: 20px;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      }

      .event-name {
        font-weight: bold;
        text-align: center;
        margin-top: 10px;
        color: #a9181a;
        font-size: 20px;
      }

      .divider2 {
        width: 90%;
        height: 2px;
        background-color: #ddd;
        margin: 10px 0;
      }

      .comment-content {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 90%;
        padding: 10px;
      }

      .comment-text {
        margin-right: 10px;
        text-align: center;
        font-size: 18px;
        color: #666;

        &.full-text {
          white-space: normal;
          overflow: visible;
        }
      }

      &.red-title .event-name {
        color: #a9181a;
      }
    }

    .wide-card {
      width: 90%;
    }

    @media (max-width: 800px) {
      .card-container {
        flex-direction: column;
        align-items: center;
      }

      .custom-card,
      .wide-card {
        width: 100%;
        margin-bottom: 20px;
      }
    }
  }

  .action-buttons {
    display: flex;
    justify-content: space-between;
    padding: 20px;
    background-color: #fff;
    border-top: 2px solid #ececec;
    margin-top: 20px;

    .styled-button {
      width: 45%;
      height: 50px;
      font-size: 1rem;
      background-color: #a9181a;
      color: #fff;
      font-weight: bold;
      border: none;
      outline: none;
      cursor: pointer;
      transition: all 0.3s ease;

      &:hover {
        background-color: darken(#a9181a, 10%);
      }

      &.primary {
        background-color: #a9181a;
      }

      &.success {
        background-color: #28a745;
      }
    }
  }

  .modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;

    .modal-content {
      background-color: #fff;
      padding: 20px;
      border-radius: 8px;
      width: 90%;
      max-width: 500px;
      text-align: center;

      .apply-form {
        display: flex;
        flex-direction: column;
        align-items: stretch;

        .form-group {
          margin-bottom: 15px;
          text-align: left;

          label {
            font-weight: bold;
            margin-bottom: 5px;
            display: inline-block;
          }

          input {
            width: 100%;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ccc;
            font-size: 1rem;
            background-color: #f0f0f0;
          }
        }
      }

      .modal-footer {
        display: flex;
        justify-content: space-between;
        margin-top: 20px;

        button {
          padding: 10px 20px;
          border: none;
          border-radius: 4px;
          cursor: pointer;
          transition: background-color 0.3s;

          &:hover {
            background-color: #ccc;
          }

          &.primary {
            background-color: #a9181a;
            color: #fff;
          }
        }
      }
    }
  }
}

.form-group.terms-conditions {
  display: flex;
  flex-direction: column;
  align-items: flex-start;

  label {
    margin-bottom: 5px;
    font-weight: bold;
  }

  input[type="checkbox"] {
    width: 18px;
    height: 18px;
  }
}

.form-group.terms-conditions[data-v-705ca337] {
  font-size: x-small;
}

</style>
