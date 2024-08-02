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

    <!-- New Video Section -->
    <div class="video-section">
      <div class="video-content">
        <iframe src="https://www.youtube.com/embed/OeDycAFm0Xo" frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen class="video-frame"></iframe>
      </div>
      <div class="text-content">
        <p>{{ $t('home.videoSection.text') }}</p>
      </div>
    </div>

    <!-- Blog Posts Section -->
    <div class="blog-section">
      <h1>{{ $t('home.blogSection.title') }}</h1>
      <div class="blog-display">
        <el-card v-for="post in blogPosts" :key="post.id" class="blog-card">
          <img :src="post.image" alt="Blog Image" class="blog-image">
          <div class="blog-info">
            <div class="blog-author-date">
              <div class="author-details">
                <el-icon><User /></el-icon> <!-- Element Plus icon for author -->
                {{ post.author }}
              </div>
              <div class="date-details">
                <el-icon><Calendar /></el-icon> <!-- Element Plus icon for date -->
                {{ post.date }}
              </div>
            </div>

            <h5>{{ post.title }}</h5>
            <p>{{ post.description }}</p>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script lang='ts'>
import { ElButton, ElCard, ElCarousel, ElCarouselItem, ElIcon } from "element-plus";
import { User, Calendar } from "@element-plus/icons-vue"; // 引入需要的图标
import { useI18n } from 'vue-i18n';

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
  data() {
    return {
      introImages: [
        { id: 1, src: './src/assets/volunteer1.jpg' },
        { id: 2, src: './src/assets/event-badminto.png' },
        { id: 3, src: './src/assets/volunteer3.jpg' }
      ],
      introSections: [{
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
      ],
      activities: [
        { id: 1, title: 'Activity 1', image: './src/assets/activity1.jpg' },
        { id: 2, title: 'Activity 2', image: './src/assets/activity2.jpg' },
        { id: 3, title: 'Activity 3', image: './src/assets/activity3.jpg' },
        { id: 4, title: 'Activity 4', image: './src/assets/activity4.jpg' },
        { id: 5, title: 'Activity 5', image: './src/assets/activity5.jpg' },
        { id: 6, title: 'Activity 6', image: './src/assets/activity6.jpg' }
      ],
      blogPosts: [
        {
          id: 1,
          image: './src/assets/event-judo.png',
          title: 'The British Adaptive & VI Open',
          date: '30.07.2024',
          author: 'Welsh Judo',
          description: 'Welsh Judo is hosting the British Adaptive & VI Open in Swansea, showcasing inclusive judo for visually impaired athletes.'
        },
        {
          id: 2,
          image: './src/assets/event-badminto.png',
          title: 'Yonex Welsh International Challenge',
          date: '30.07.2024',
          author: 'Badminton Wales',
          description: 'Badminton Wales is hosting the Yonex Welsh International Challenge in Cardiff, attracting top badminton talent from around the world.'
        },
        {
          id: 3,
          image: './src/assets/event-tabletennis.png',
          title: 'The magic of marketing, the science of sales',
          date: '30.07.2024',
          author: 'Table Tennis Wales',
          description: 'Table Tennis Wales is hosting the Senior Team Championships of Wales, featuring elite team competition in table tennis.'
        }
      ],
      volunteerHubText: 'Volunteer Hub',
      displayedText: ''
    };
  },
  mounted() {
    this.animateText();
  },
  methods: {
    animateText() {
      const characters = this.volunteerHubText.split('');
      let index = 0;
      const interval = setInterval(() => {
        if (index < characters.length) {
          this.displayedText += characters[index];
          index++;
        } else {
          clearInterval(interval);
        }
      }, 300); // Adjust timing for effect
    }
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
      height: 400px; // Adjust based on design requirement
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

    h1 {
      font-size: 4rem;
      font-weight: bold;
      line-height: 1.2;
      margin: 0.5rem 0;
      span {
        color: #a9181a;
        font-weight: bold;
      }
    }

    p {
      font-size: 1.2rem;
      color: #a9181a;
      margin-bottom: 20px;
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
  border-right: .15em solid #a9181a; /* 用于模拟打字机效果的光标 */
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

.blog-section {
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
}

.blog-display {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.blog-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow: hidden;
  border: 1px solid #ddd; // 给卡片添加边框
  border-radius: 8px; // 圆角边框
  box-shadow: 0 2px 5px rgba(0,0,0,0.1); // 轻微阴影

  img {
    width: 100%;
    height: 200px; // 设置图片高度
    object-fit: cover; // 确保图片完整显示
    border-radius: 8px; // 图片四个角圆角
  }

  .blog-info {
    padding: 14px;
    text-align: left;
    width: 100%;

    .blog-author-date {
      display: flex;
      align-items: center;
      color: #666;
      font-size: 0.9rem;
      margin-bottom: 10px;

      .author-icon, .date-icon {
        color:#a9181a; // 图标颜色
        margin-right: 5px;
      }
    }

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
  }
}

@media (max-width: 800px) {
  .blog-display {
    grid-template-columns: 1fr;
  }
}

.blog-author-date {
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
      color: #a9181a; // Changed icon color to blue
      margin-right: 5px;
    }
  }
}
</style>
