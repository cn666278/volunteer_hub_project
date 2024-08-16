<template>
  <div class="event-detail" v-if="event">
    <!-- Intro Section with text and single image carousel -->
    <div class="intro-section">
      <div class="image-carousel">
        <img :src="event.eventPic" alt="Event Image" class="carousel-image" />
      </div>
      <div class="text-content">
        <h1>{{ event.title }}</h1>
        <p>{{ event.organizerId }}</p>
      </div>
    </div>

    <!-- Introduction Sections -->
    <div class="project-introduction-middle">
      <div class="column" v-for="(item, index) in introSections" :key="index">
        <el-icon :name="item.icon" :size="50">
          <component :is="item.component" />
        </el-icon>
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
      <el-button type="primary" class="styled-button">
        Apply to be a volunteer
      </el-button>
      <el-button type="success" class="styled-button" @click="subscribeToEvent">
        Subscribe This Event
      </el-button>
    </div>
  </div>
  <div v-else>
    <p>Loading...</p>
  </div>
</template>

<script lang="ts">
import { ElIcon, ElButton, ElMessage } from "element-plus";
import { useI18n } from 'vue-i18n';
import { useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import api from '../../api/api';
import useUser from '../../store/user';

export default {
  name: 'EventDetail',
  components: {
    ElIcon,
    ElButton,
  },
  setup() {
    const route = useRoute();
    const event = ref(null);
    const { t } = useI18n();
    const userStore = useUser();

    const introSections = ref([
      {
        icon: 'el-icon-service',
        component: 'Service',
        title: 'SUPPORT',
      },
      {
        icon: 'el-icon-user',
        component: 'User',
        title: 'ROLE',
      },
      {
        icon: 'el-icon-star',
        component: 'Star',
        title: 'OPPORTUNITY',
      },
    ]);

    const loadEvent = async () => {
      const eventId = route.params.id;
      if (eventId) {
        const response = await api.getEventById({ id: eventId }).catch(error => {
          console.error(error.message);
        });
        if (response) {
          event.value = response;
        }
      } else {
        console.error('Event ID is not provided');
      }
    };

    const subscribeToEvent = async () => {
      try {
        const loginId = userStore.user.loginId;
        if (!loginId) {
          ElMessage.error('Please log in to subscribe to this event');
          return;
        }

        const eventId = event.value?.id;
        if (!eventId) {
          ElMessage.error('Event ID is missing');
          return;
        }

        // 调用API来插入数据
        const response = await api.subscribeForEvent({
          eventId: eventId,
          volunteerId: loginId,
          roleId: 1,  // 默认设置roleId为1
          status: 'subscribed',
        });

        if (response.success) {
          ElMessage.success('Successfully subscribed to the event');
        } else if (response.message.includes("already subscribed")) {
          ElMessage.warning('You have already subscribed to this event');
        } else {
          throw new Error(response.message || 'Failed to subscribe to the event');
        }
      } catch (error) {
        console.error('Error subscribing to event:', error);
        ElMessage.error('Failed to subscribe to the event');
      }
    };

    onMounted(() => {
      loadEvent();
    });

    return {
      event,
      introSections,
      subscribeToEvent,
      t,
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

    .el-button.styled-button {
      width: 45%;
      height: 100px;
      font-size: 1rem;
      background-color: #a9181a;
      color: #fff;
      font-weight: bold;
      border: none;
      outline: none;
      transition: all 0.3s ease;

      &:hover {
        background-color: darken(#a9181a, 10%);
      }
    }
  }
}
</style>
