<template>
  <div class="VolunteerInfo">
    <div class="search-section">
      <el-input
          placeholder="Type here to search..."
          v-model="searchQuery">
        <template #prefix>
          <el-icon><search /></el-icon>
        </template>
      </el-input>
    </div>
    <div class="all-comments">
      <div v-for="comment in comments" :key="comment.id" class="custom-card">
        <div class="event-name">{{ comment.eventName }}</div> <!-- Display event name -->
        <div class="divider2"></div>
        <div class="comment-content">
          <div class="comment-text">{{ comment.infoTitle }}</div>
        </div>
        <div class="comment-time">{{ comment.sendTime }}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import useUser from '../../store/user';
import api from '../../api/api';  // Ensure the correct path to your API management file

const userStore = useUser();
const comments = ref([]);

const fetchEventName = async (id) => {
  try {
    const response = await api.getEventById({ id });
    if (response) {
      return response.title; // Assuming the response has the event title under 'title'
    } else {
      console.error('Failed to fetch event title');
      return 'Unknown Event';
    }
  } catch (error) {
    console.error('Failed to fetch event title:', error.message || 'Request failed');
    return 'Unknown Event';
  }
};

const fetchVolunteerInfo = async () => {
  try {
    const volunteerId = userStore.user.loginId; // Assuming loginId corresponds to volunteerId
    const response = await api.getVolunteerInfoByVolunteerId({ volunteerId });

    if (response && Array.isArray(response)) {
      if (response.length > 0) {
        const commentsWithEventNames = await Promise.all(
            response.map(async (info) => {
              const eventName = await fetchEventName(info.eventId);
              return {
                id: info.informationId,
                eventName: eventName, // Use eventName from the fetched data
                infoTitle: info.infoTitle,
                sendTime: info.sendTime,
              };
            })
        );
        comments.value = commentsWithEventNames;
      } else {
        console.log('No information found for the given volunteerId');
      }
    } else {
      console.error('Failed to fetch volunteer information:', response?.msg || 'Unknown error');
    }
  } catch (error) {
    console.error('Failed to fetch volunteer information:', error.message || 'Request failed');
  }
};

// Fetch volunteer information when the component is mounted
onMounted(() => {
  fetchVolunteerInfo();
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
  min-height: 150px;
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
}

.comment-text {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  margin-right: 10px;
}

.comment-time {
  width: 100%;
  text-align: center;
  padding: 10px;
  color: #999;
  font-size: 0.85rem;
}
</style>
