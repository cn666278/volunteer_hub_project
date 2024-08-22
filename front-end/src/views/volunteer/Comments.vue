<template>
  <div class="Comments">
    <!-- Search input section -->
    <div class="search-section">
      <!-- Input field for search functionality -->
      <el-input
          placeholder="Enter your search here..."
          v-model="searchQuery">
        <!-- Search icon displayed inside the input field -->
        <template #prefix>
          <el-icon><search /></el-icon>
        </template>
      </el-input>
    </div>
    <!-- Section to display all comments -->
    <div class="all-comments">
      <!-- Loop through each comment and display it in a custom card -->
      <div v-for="comment in comments" :key="comment.id" class="custom-card">
        <!-- Display the event name associated with the comment -->
        <div class="event-name">{{ comment.eventName }}</div>
        <!-- Horizontal divider line -->
        <div class="divider2"></div>
        <!-- Section containing the comment text and rating -->
        <div class="comment-content">
          <!-- Display the text of the comment -->
          <div class="comment-text">{{ comment.text }}</div>
          <!-- Vertical divider line -->
          <div class="divider1"></div>
          <!-- Display the rating associated with the comment -->
          <div class="rating-section">
            <el-rate v-model="comment.rating" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" disabled />
          </div>
        </div>
        <!-- Display the time when the comment was created -->
        <div class="comment-time">{{ comment.time }}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import useUser from '../../store/user';
import api from '../../api/api';  // Ensure to import the API management file to handle API calls

// Define a proxy object to handle API calls
const proxy = {
  $api: api,
};

// Get the user store to access user-related information
const userStore = useUser();
const comments = ref([]);  // Reactive variable to store comments

// Function to fetch volunteer ratings from the server
const fetchVolunteerRatings = async () => {
  try {
    const volunteerId = userStore.user.id; // Retrieve the ID of the logged-in user, assuming it corresponds to the volunteer ID
    // API call to get ratings associated with the volunteer ID
    const response = await proxy.$api.getRatingsByVolunteerId({ volunteerId });

    // Check if the response is valid and contains an array of ratings
    if (response && Array.isArray(response)) {
      if (response.length > 0) {
        // Iterate over each rating and fetch the corresponding event details
        comments.value = await Promise.all(response.map(async (rating: any) => {
          // API call to get the event details by event ID
          const eventResponse = await proxy.$api.getEventById({ id: rating.eventId });

          // Return a structured comment object containing event details, rating, and comment text
          return {
            volunteerId: rating.volunteerId,  // Store the volunteer ID
            eventName: eventResponse?.title || 'Unknown Event',  // Use the event title; if not available, default to 'Unknown Event'
            text: rating.comment,  // Store the text of the comment
            rating: rating.rating,  // Store the rating score
            time: rating.createdAt,  // Store the time the comment was created
          };
        }));
      } else {
        // If no ratings are found for the volunteer ID, log a message
        console.log('No ratings found for the given volunteerId');
      }
    } else {
      // Log an error message if the API call fails or the response format is unexpected
      console.error('Failed to retrieve volunteer ratings:', response?.msg || 'Unknown error');
    }
  } catch (error) {
    // Log an error message if there is an issue with the API request
    console.error('Failed to retrieve volunteer ratings:', error.message || 'Request failed');
  }
};

// Fetch the ratings data when the component is mounted
onMounted(() => {
  fetchVolunteerRatings();
});
</script>

<style lang='scss'>
/* Styling for the search input section */
.search-section {
  padding: 20px;
  display: flex;
  justify-content: center;

  /* Style for the element input field */
  .el-input {
    width: 80%;
    --el-input-border-color: #c0c4cc;

    /* Adjust padding inside the input field */
    .el-input__inner {
      padding-left: 35px;
    }

    /* Style for the search icon */
    .el-icon {
      margin-right: 8px;
      color: #409EFF;  // Set the color of the search icon
    }
  }
}

/* Styling for the section containing all comments */
.all-comments {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* Styling for each custom card displaying a comment */
.custom-card {
  width: 80%;
  border: 1px solid #ccc;  // Set border color
  background-color: #f5f5f5;  // Set background color
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);  // Add a subtle shadow
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;  // Transition effect for hover state
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;  // Round the corners of the card
  margin-bottom: 20px;

  /* Hover effect to slightly lift the card and add shadow */
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
}

/* Styling for the event name displayed in the comment card */
.event-name {
  font-weight: bold;
  text-align: center;
  margin-top: 10px;
}

/* Styling for the horizontal divider line */
.divider2 {
  width: 90%;
  height: 2px;
  background-color: #ddd;
  margin: 10px 0;
}

/* Styling for the section containing comment text and rating */
.comment-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 90%;
  padding: 10px;
  flex-wrap: wrap; /* Allow wrapping in smaller screens */
}

/* Styling for the comment text */
.comment-text {
  flex-grow: 1;
  margin-right: 10px;
  word-wrap: break-word; /* Allow text to break into multiple lines */
  white-space: normal; /* Enable wrapping for long text */
}

/* Styling for the rating section */
.rating-section {
  flex-shrink: 0;  // Prevent shrinking
  margin-left: auto;
  padding-right: 5px;
}

/* Styling for the vertical divider line */
.divider1 {
  height: 70%;
  width: 2px;
  background-color: #ddd;
  margin-right: 20px;
}

/* Styling for the timestamp of the comment */
.comment-time {
  width: 100%;
  text-align: center;
  padding: 10px;
  color: #999;  // Set the color of the timestamp text
  font-size: 0.85rem;  // Set a smaller font size for the timestamp
}

/* Responsive styling for mobile devices */
@media (max-width: 600px) {
  /* Make the custom card take the full width on small screens */
  .custom-card {
    width: 100%;
  }

  /* Stack the comment content vertically on small screens */
  .comment-content {
    flex-direction: column;
    align-items: flex-start;
  }

  /* Hide the vertical divider on small screens */
  .divider1 {
    display: none;
  }

  /* Center the rating section and add padding on small screens */
  .rating-section {
    margin-left: 0;
    padding-top: 10px;
    width: 100%;
    text-align: center;
  }
}
</style>
