<template>
  <div class="home">
    <!-- Intro Section with text and single image carousel -->
    <div class="intro-section">
      <div class="text-content">
        <h4>{{ $t('home.intro.weAreGathered') }}</h4>
        <h1>{{ $t('home.intro.wsa') }}</h1>
        <h1><span class="animated-text">{{ displayedText }}</span></h1>
        <p>{{ $t('home.intro.welshSportsAssociation') }}</p>
      </div>
      <div class="image-carousel">
        <el-carousel trigger="hover" interval="3000" height="400px" indicator-position="none">
          <el-carousel-item v-for="image in introImages" :key="image.id">
            <img :src="image.src" alt="carousel image" class="carousel-image">
          </el-carousel-item>
        </el-carousel>
      </div>
    </div>

    <div class="project-introduction-middle">
      <div class="column" v-for="(item, index) in introSections" :key="index">
        <el-icon :name="item.icon" :size="50">
          <component :is="item.component" />
        </el-icon>
        <h3>{{ $t(`home.projectIntro.${item.title.toLowerCase()}Title`) }}</h3>
        <p>{{ $t(`home.projectIntro.${item.title.toLowerCase()}Text`) }}</p>
      </div>
    </div>

    <!-- Recent Events Section -->
    <div class="recent-events-section">
      <h1>{{ $t('home.recentsEvents')}}</h1>
      <div class="event-display">
        <el-card v-for="event in recentEvents" :key="event.id" class="event-card">
          <img :src="event.uploadedFilePath" alt="Event Image" class="event-image">
          <div class="event-details">
            <div class="event-author-date">
              <div class="author-details">
                <el-icon><User /></el-icon> <!-- Event author icon -->
                {{ event.organizationName }} <!-- Display the organizer's name next to the icon -->
              </div>
              <div class="date-details">
                <el-icon><Calendar /></el-icon> <!-- Event date icon -->
                {{ formatDate(event.startDate) }}
              </div>
            </div>
            <h5>{{ event.title }}</h5>
            <p>{{ event.description }}</p>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script lang='ts'>
import { ElButton, ElCard, ElCarousel, ElCarouselItem, ElIcon } from "element-plus";
import { User, Calendar } from "@element-plus/icons-vue";
import { ref, onMounted, getCurrentInstance } from 'vue';
import { ElMessage } from 'element-plus';

export default {
  name: 'Index',
  components: {
    ElCarousel,
    ElCarouselItem,
    ElButton,
    ElCard,
    ElIcon,
    User,
    Calendar,
  },
  setup() {
    const recentEvents = ref([]);
    const introImages = ref([
      { id: 1, src: './src/assets/volunteer1.jpg' },
      { id: 2, src: './src/assets/event-badminto.png' },
      { id: 3, src: './src/assets/volunteer3.jpg' }
    ]);
    const introSections = ref([
      {
        icon: 'el-icon-service',
        component: 'Service',
        title: 'SUPPORT',
        text: 'The WSA Volunteer Hub provides critical support to ensure the smooth running of the event.'
      },
      {
        icon: 'el-icon-user',
        component: 'User',
        title: 'ROLE',
        text: 'The role of volunteers at events is crucial and has a major impact on the smooth running and existence of the competition.'
      },
      {
        icon: 'el-icon-star',
        component: 'Star',
        title: 'OPPORTUNITY',
        text: 'The WSA Volunteer Hub provides opportunities for volunteers and organisations to grow and develop.'
      }
    ]);
    const volunteerHubText = ref('Volunteer Hub');
    const displayedText = ref('');
    const { proxy } = getCurrentInstance();

    const fetchRecentEvents = async () => {
      try {
        const response = await proxy.$api.getLatestEvents();
        const events = response;

        // Fetch images for each event
        recentEvents.value = await Promise.all(
            events.map(async (event) => {
              const uploadedFilePath = await fetchEventImage(event.eventPic);
              return {
                ...event,
                uploadedFilePath,
              };
            })
        );
      } catch (error) {
        ElMessage.error('Failed to load recent events');
      }
    };

    const fetchEventImage = async (eventPicId) => {
      try {
        const response = await proxy.$api.getfiles({ id: eventPicId });
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

    const animateText = () => {
      const characters = volunteerHubText.value.split('');
      let index = 0;
      const interval = setInterval(() => {
        if (index < characters.length) {
          displayedText.value += characters[index];
          index++;
        } else {
          clearInterval(interval);
        }
      }, 300);
    };

    const formatDate = (dateString: string) => {
      const options: Intl.DateTimeFormatOptions = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      };
      return new Date(dateString).toLocaleDateString(undefined, options);
    };

    onMounted(() => {
      fetchRecentEvents();
      animateText();
    });

    return {
      introImages,
      introSections,
      recentEvents,
      volunteerHubText,
      displayedText,
      formatDate,
    };
  }
};
</script>

<style lang='scss'>
.home {
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
  }

  @media (max-width: 800px) {
    .project-introduction-middle {
      flex-direction: column;
      .column {
        width: 100%;
      }
    }
  }
}

.video-section {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 20px 20px;
  background-color: #fff;
  border-bottom: 2px solid #ececec;

  .video-content {
    width: 50%;
    padding-right: 20px;

    .video-frame {
      width: 100%;
      height: 400px;
      border-radius: 8px;
    }
  }

  .text-content {
    width: 50%;
    padding-left: 20px;

    h4 {
      color: #666;
      font-size: 1.4rem;
      margin-bottom: 10px;
    }

    p {
      font-size: 1.2rem;
      color: #666;
      margin-bottom: 10px;
    }
  }
}

.intro-section {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 20px 20px;
  background-color: #fff;
  border-bottom: 2px solid #ececec;

  .text-content {
    width: 50%;
    padding-right: 20px;

    h4 {
      color: #a9181a;
      font-size: 1.2rem;
      font-weight: normal;
      margin-bottom: 10px;
    }

    .el-carousel__arrow el-carousel__arrow--left{
      display: flow;
    }

    h1 {
      font-size: 4rem;
      font-weight: bold;
      line-height: 1.2;
      margin: 0.5rem 0;

      /* 适配移动设备 */
      @media (max-width: 1200px) {
        font-size: 3rem;
      }

      @media (max-width: 800px) {
        font-size: 2.5rem;
      }

      @media (max-width: 600px) {
        font-size: 2rem;
        line-height: 1.1;
      }

      @media (max-width: 400px) {
        font-size: 1.5rem;
        line-height: 1;
      }

      span {
        color: #a9181a;
        font-weight: bold;
      }
    }

    p {
      font-size: 1.2rem;
      color: #a9181a;
      margin-bottom: 20px;

      @media (max-width: 600px) {
        font-size: 1rem;
      }
    }
  }

  .image-carousel {
    width: 50%;
    padding-left: 20px;

    .carousel-image {
      width: 100%;
      height: 100%;
      object-fit: cover;
      display: block;
      border-radius: 8px;
    }

    .el-carousel__arrow {
      opacity: 0.5;
      color: #333;
      &:hover {
        opacity: 0.8;
      }
    }
  }

  @media (max-width: 800px) {
    flex-direction: column;
    align-items: center;
    .text-content, .image-carousel {
      width: 100%;
      padding: 0;
    }
    .text-content {
      padding-bottom: 20px;
    }
    .image-carousel {
      height: auto;
    }
  }
}


.project-introduction, .activities-section {
  padding: 20px;
  background-color: #f5f5f5;
  margin: 20px 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

.activities-section h1, .project-introduction h1 {
  margin-bottom: 16px;
  text-align: center;
}

.activities-display {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.activity-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.activity-image {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.activities-button-container {
  text-align: right;
  width: 100%;
  margin-top: 20px;
}

.more-activities-btn {
  display: inline-block;
  border: none;
  background-color: transparent;
  color: #333;
}

@media (max-width: 800px) {
  .activities-display {
    grid-template-columns: 1fr;
  }
}

.animated-text {
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  border-right: .15em solid #a9181a;
  animation: typing 0.3s steps(30, end), blink-caret .75s step-end infinite;
  @keyframes typing {
    from { width: 0 }
    to { width: 100% }
  }
  @keyframes blink-caret {
    from, to { border-color: transparent }
    50% { border-color: #a9181a; }
  }
}

.recent-events-section {
  padding: 20px;
  background-color: #f5f5f5;
  margin: 20px 0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;

  h1 {
    margin-bottom: 16px;
    color: #a9181a;
  }

  .event-display {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
  }

  .event-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow: hidden;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);

    .event-image {
      width: 100%;
      height: 200px;
      object-fit: cover;
      border-radius: 8px;
    }

    .event-details {
      padding: 14px;
      text-align: left;
      width: 100%;

      h5 {
        color: #666;
        font-size: 1.2rem;
        font-weight: bold;
        margin: 5px 0;
      }

      p {
        color: #666;
        font-size: 1rem;
      }

      .event-author-date {
        display: flex;
        justify-content: space-between;
        align-items: center;
        color: #666;
        font-size: 0.9rem;
        margin-bottom: 10px;

        .author-details, .date-details {
          display: flex;
          align-items: center;

          .el-icon {
            color: #a9181a;
            margin-right: 5px;
          }
        }
      }
    }
  }
}

@media (max-width: 800px) {
  .event-display {
    grid-template-columns: 1fr !important;
  }
}

</style>

