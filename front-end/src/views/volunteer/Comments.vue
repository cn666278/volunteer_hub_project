<template>
  <div class="Comments">
    <div class="search-section">

    </div>
    <div class="all-comments">
      <div v-for="comment in comments" :key="comment.id" class="custom-card">
        <div class="event-name">{{ comment.eventName }}</div>
        <div class="divider2"></div>
        <div class="comment-content">
          <div class="comment-text">{{ comment.text }}</div>
          <div class="divider1"></div>
          <div class="rating-section">
            <el-rate v-model="comment.rating" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" disabled />
          </div>
        </div>
        <div class="comment-time">{{ comment.time }}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import useUser from '../../store/user';
import api from '../../api/api';  // Ensure the API management file is imported

// Define a proxy object to access API functions
const proxy = {
  $api: api,
};

const userStore = useUser();
const comments = ref([]);

// Function to fetch volunteer ratings and event details
const fetchVolunteerRatings = async () => {
  try {
    const volunteerId = userStore.user.id; // Assume loginId corresponds to volunteerId
    const response = await proxy.$api.getRatingsByVolunteerId({ volunteerId });

    if (response && Array.isArray(response)) {  // Ensure response object exists and data is an array
      if (response.length > 0) {
        // Iterate over each rating and fetch corresponding event title
        comments.value = await Promise.all(response.map(async (rating: any) => {
          // Fetch event details for each eventId
          const eventResponse = await proxy.$api.getEventById({ id: rating.eventId });

          return {
            volunteerId: rating.volunteerId,
            eventName: eventResponse?.title || 'Unknown Event',  // Use title instead of eventId
            text: rating.comment,
            rating: rating.rating,
            time: rating.createdAt,
          };
        }));
      } else {
        console.log('No ratings found for the given volunteerId');
      }
    } else {
      console.error('Failed to retrieve volunteer ratings:', response?.msg || 'Unknown error');
    }
  } catch (error) {
    console.error('Failed to retrieve volunteer ratings:', error.message || 'Request failed');
  }
};

// Fetch ratings data when the component is mounted
onMounted(() => {
  fetchVolunteerRatings();
});
</script>

<style lang='scss'>
.search-section {
  padding: 20px;
  display: flex;
  justify-content: center;
  .el-input {
    width: 80%;
    --el-input-border-color: #c0c4cc;

    .el-input__inner {
      padding-left: 35px;
    }

    .el-icon {
      margin-right: 8px;
      color: #409EFF;
    }
  }
}

.all-comments {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.custom-card {
  width: 80%;
  border: 1px solid #ccc;
  background-color: #f5f5f5;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  margin-bottom: 20px;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
}

.event-name {
  font-weight: bold;
  text-align: center;
  margin-top: 10px;
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
  justify-content: space-between;
  width: 90%;
  padding: 10px;
  flex-wrap: wrap; /* Allow wrapping on smaller screens */
}

.comment-text {
  flex-grow: 1;
  margin-right: 10px;
  word-wrap: break-word; /* Allow text to break into multiple lines */
  white-space: normal; /* Enable wrapping for long text */
}

.rating-section {
  flex-shrink: 0;
  margin-left: auto;
  padding-right: 5px;
}

.divider1 {
  height: 70%;
  width: 2px;
  background-color: #ddd;
  margin-right: 20px;
}

.comment-time {
  width: 100%;
  text-align: center;
  padding: 10px;
  color: #999;
  font-size: 0.85rem;
}

/* Mobile responsiveness */
@media (max-width: 600px) {
  .custom-card {
    width: 100%;
  }

  .comment-content {
    flex-direction: column;
    align-items: flex-start;
  }

  .divider1 {
    display: none; /* Hide the divider on smaller screens */
  }

  .rating-section {
    margin-left: 0;
    padding-top: 10px;
    width: 100%;
    text-align: center;
  }
}
</style>
