<template>
  <div class="VolunteerInfo">
    <!-- Search Section -->
    <div class="search-section">
      <el-input
          placeholder="Type here to search..."
          v-model="searchQuery">
        <template #prefix>
          <el-icon><search /></el-icon> <!-- Search icon inside the input field -->
        </template>
      </el-input>
    </div>

    <!-- Comments Section -->
    <div class="all-comments">
      <div v-for="comment in comments" :key="comment.id" class="custom-card">
        <div class="event-name">{{ comment.eventName }}</div> <!-- Display event name -->
        <div class="divider2"></div>
        <div class="comment-content">
          <div class="comment-text">{{ comment.infoTitle }}</div> <!-- Display comment title -->
        </div>
        <div class="comment-time">{{ comment.sendTime }}</div> <!-- Display time when the comment was sent -->
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import useUser from '../../store/user';
import api from '../../api/api';  // Import API management file

const userStore = useUser();  // Access user data from the store
const comments = ref([]);  // Initialize comments array

// Fetch the event name based on event ID
const fetchEventName = async (id) => {
  try {
    const response = await api.getEventById({ id });
    if (response) {
      return response.title; // Return the event title if response is valid
    } else {
      console.error('Failed to fetch event title');
      return 'Unknown Event'; // Fallback if event title is not available
    }
  } catch (error) {
    console.error('Failed to fetch event title:', error.message || 'Request failed');
    return 'Unknown Event'; // Fallback in case of an error
  }
};

// Fetch volunteer information including event names
const fetchVolunteerInfo = async () => {
  try {
    const volunteerId = userStore.user.id; // Get volunteer ID from the user store
    const response = await api.getVolunteerInfoByVolunteerId({ volunteerId });

    if (response && Array.isArray(response)) {
      if (response.length > 0) {
        const commentsWithEventNames = await Promise.all(
            response.map(async (info) => {
              const eventName = await fetchEventName(info.eventId); // Fetch event name for each comment
              return {
                id: info.informationId,
                eventName: eventName, // Add event name to the comment object
                infoTitle: info.infoTitle,
                sendTime: info.sendTime,
              };
            })
        );
        comments.value = commentsWithEventNames; // Update comments array with event names
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
      padding-left: 35px; /* Space for the search icon */
    }

    .el-icon {
      margin-right: 8px;
      color: #409EFF; /* Search icon color */
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
    transform: translateY(-5px); /* Card lift effect on hover */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Shadow on hover */
  }
}

.event-name {
  font-weight: bold;
  text-align: center;
  margin-top: 10px; /* Space above event name */
}

.divider2 {
  width: 90%;
  height: 2px;
  background-color: #ddd; /* Divider color */
  margin: 10px 0; /* Space around divider */
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
  text-overflow: ellipsis; /* Truncate long text */
  margin-right: 10px;
}

.comment-time {
  width: 100%;
  text-align: center;
  padding: 10px;
  color: #999; /* Time text color */
  font-size: 0.85rem;
}
</style>
