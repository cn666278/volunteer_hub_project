<template>
  <div>
    <!-- Loop through each message and display it -->
    <div v-for="msg in messages" :key="msg.id" class="message">
      <p>
        <!-- Display the username and message content -->
        <strong>{{ msg.username }}:</strong> {{ msg.content }}
        <!-- Display the formatted timestamp of the message -->
        <span class="timestamp">{{ formatTimestamp(msg.timestamp) }}</span>
      </p>
    </div>

    <!-- Input field for typing a new message -->
    <el-input v-model="newMessage" placeholder="Type your message..." @keyup.enter="sendMessage" />
    <!-- Button to send the new message -->
    <el-button @click="sendMessage">Send</el-button>
  </div>
</template>

<script setup>
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useRoute } from 'vue-router';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import useUser from "../../store/user"; // Import the user store

// Get the current Vue instance to access global properties like API calls
const { proxy } = getCurrentInstance();
// Access the current route to retrieve query parameters
const route = useRoute();
// Reference to the event ID from the route query parameters
const event = ref(route.query.event);
// Reactive variable to store the list of messages
const messages = ref([]);
// Reactive variable to store the new message being typed
const newMessage = ref('');

// Access the user store to get the current user's information
const userStore = useUser();
// Variable to store the STOMP client instance for WebSocket connection
let stompClient;

// Function to fetch existing messages for the current event
const fetchMessages = async () => {
  // API call to get messages by event ID
  const response = await proxy.$api.getMessagesByEventId({ eventId: event.value });
  messages.value = response; // Store the fetched messages in the reactive variable
  console.log("Messages fetched:", messages.value);
};

// Function to send a new message
const sendMessage = () => {
  if (newMessage.value.trim() === '') return; // Do not send empty messages

  // Get the current timestamp and convert it to ISO format considering the timezone offset
  const now = new Date();
  const offset = now.getTimezoneOffset();
  const localTime = new Date(now.getTime() - (offset * 60 * 1000)).toISOString();

  // Construct the message object with necessary details
  const message = {
    eventId: event.value,
    senderId: userStore.user.id,
    content: newMessage.value,
    timestamp: localTime
  };

  // Send the message to the server using STOMP client
  stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
  newMessage.value = ''; // Clear the input field after sending the message
};

// Function to establish a WebSocket connection and subscribe to messages
const connect = () => {
  // Initialize a SockJS connection with the backend server
  const socket = new SockJS('http://10.72.102.12:8081/ws'); // Use the backend server address and port
  stompClient = Stomp.over(socket);

  // Connect to the WebSocket and subscribe to the messages topic
  stompClient.connect({}, (frame) => {
    stompClient.subscribe('/topic/messages', (message) => {
      const receivedMessage = JSON.parse(message.body);
      console.log("receivedMessage", receivedMessage)
      messages.value.push(receivedMessage);
    });
  }, (error) => {
    console.error('WebSocket connection error:', error); // Handle connection errors
  });
};

// Function to format the timestamp for display
const formatTimestamp = (timestamp) => {
  const date = new Date(timestamp);
  return date.toLocaleString('en-GB', {
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  });
};

// Lifecycle hook that runs when the component is mounted
onMounted(() => {
  fetchMessages(); // Fetch messages for the current event
  connect(); // Establish WebSocket connection
});
</script>

<style scoped>
/* Styling for each message container */
.message {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

/* Styling for the timestamp displayed in each message */
.timestamp {
  float: right;
  color: #888;
}
</style>
