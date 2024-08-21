<template>
  <div class="event-detail" v-if="event">
    <!-- Intro Section with text and single image carousel -->
    <div class="intro-section">
      <div class="image-carousel">
        <img :src="uploadedFilePath" alt="Event Image" class="carousel-image" />
      </div>
      <div class="text-content">
        <h1>{{ event.title }}</h1>
        <p>{{ event.organizerId }}</p>
      </div>
    </div>

    <!-- Introduction Sections -->
    <div class="project-introduction-middle">
      <div class="column" v-for="(item, index) in introSections" :key="index">
        <h3>{{ $t(`home.projectIntro.${item.title.toLowerCase()}Title`) }}</h3>
        <p>{{ $t(`home.projectIntro.${item.title.toLowerCase()}Text`) }}</p>
      </div>
    </div>

    <!-- Description Section -->
    <div class="description">
      <h3>{{ $t('home.descriptionTitle') }}</h3>
      <div class="section-content">
        <p>{{ event.description }}</p>
      </div>
    </div>

    <!-- Volunteer Recruitment Information Section -->
    <div class="description-role">
      <h3>{{ $t('home.volunteerInfoTitle') }}</h3>
      <div class="section-content">
        <p>{{ event.description }}</p>
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
          <div class="form-group">
            <label for="event-date">Event date</label>
            <input id="event-date" v-model="formattedEventDate" readonly />
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
import { useI18n } from 'vue-i18n';
import { useRoute } from 'vue-router';
import { ref, onMounted, computed } from 'vue';
import api from '../../api/api';
import useUser from '../../store/user';
import { ElMessage } from 'element-plus';

export default {
  name: 'EventDetail',
  setup() {
    const route = useRoute();
    const event = ref(null);
    const uploadedFilePath = ref(''); // 用于存储图片路径
    const { t } = useI18n();
    const userStore = useUser();
    const isSubscribed = ref(false); // 添加状态变量

    const introSections = ref([
      { title: 'SUPPORT' },
      { title: 'ROLE' },
      { title: 'OPPORTUNITY' },
    ]);

    const applyDialogVisible = ref(false);
    const applyForm = ref({});

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
        const byteCharacters = atob(base64Data);
        const byteNumbers = new Array(byteCharacters.length);
        for (let i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        const blob = new Blob([byteArray], { type: 'image/jpeg' });
        return URL.createObjectURL(blob);
      } catch (error) {
        console.error('Error fetching event image:', error);
        return '';
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
          isSubscribed.value = true; // 更新状态变量
        } else if (response.includes('already subscribed')) {
          ElMessage({
            message: 'You have already subscribed to this event',
            type: 'warning',
          });
          isSubscribed.value = true; // 更新状态变量
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

    const formattedEventDate = computed(() => {
      if (event.value) {
        return new Date(event.value.date).toLocaleDateString('en-US', {
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
      introSections,
      uploadedFilePath, // 返回图片路径
      subscribeToEvent,
      openApplyDialog,
      applyDialogVisible,
      applyForm,
      submitApplication,
      formattedEventDate,
      t,
      isSubscribed, // 返回状态变量
    };
  },
};
</script>

<style lang="scss" scoped>
.styled-button.subscribed {
  background-color: #6c757d !important;
  cursor: not-allowed;
}
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
    display: flex;
    justify-content: space-around;
    align-items: flex-start;
    padding: 40px 20px;
    background-color: #f5f5f5;
    margin: 20px 0;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 8px;

    .column {
      width: 30%;
      text-align: center;
      padding: 10px;

      h3 {
        margin-top: 10px;
        font-size: 1.5rem;
        color: #a9181a;
      }

      p {
        font-size: 1rem;
        color: #666;
        margin-top: 5px;
      }
    }

    @media (max-width: 800px) {
      flex-direction: column;

      .column {
        width: 100%;
      }
    }
  }

  .description,
  .description-role {
    padding: 20px;
    background-color: #f5f5f5;
    margin: 20px 0;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    text-align: center;

    h3 {
      margin-top: 10px;
      font-size: 1.5rem;
      color: #a9181a;
    }

    .section-content {
      padding: 20px;
      font-size: 1rem;
      color: #666;
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
</style>
