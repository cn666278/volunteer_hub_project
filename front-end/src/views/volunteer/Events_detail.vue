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
        <p>{{ event.description }}</p> <!-- 修改为 description -->
      </div>
    </div>
  </div>
  <div v-else>
    <p>Loading...</p>
  </div>
</template>

<script lang="ts">
import { ElIcon, ElMessage } from "element-plus";
import { useI18n } from 'vue-i18n';
import { useRoute } from 'vue-router';
import { ref, onMounted } from 'vue';
import api from '../../api/api';

export default {
  name: 'EventDetail',
  components: {
    ElIcon,
  },
  setup() {
    const route = useRoute();
    const event = ref(null);
    const { t } = useI18n();

    const introSections = ref([
      {
        icon: 'el-icon-service',
        component: 'Service',
        title: 'SUPPORT',
        text: t('home.projectIntro.supportText'),
      },
      {
        icon: 'el-icon-user',
        component: 'User',
        title: 'ROLE',
        text: t('home.projectIntro.roleText'),
      },
      {
        icon: 'el-icon-star',
        component: 'Star',
        title: 'OPPORTUNITY',
        text: t('home.projectIntro.opportunityText'),
      },
    ]);

    const loadEvent = async () => {
      const eventId = route.params.id;
      if (eventId) {
        const response = await api.getEventById({ id: eventId })
            .catch(error => {
              ElMessage.error(error.message);
            });
        if (response) {
          event.value = response; // 确保你从API得到的数据结构正确
        }
      } else {
        ElMessage.error('Event ID is not provided');
      }
    };

    onMounted(() => {
      loadEvent();
    });

    return {
      event,
      introSections,
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
}
</style>
