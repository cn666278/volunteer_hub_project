<template>
  <div>
    <!-- Loop through each message and display it -->
    <div v-for="msg in messages" :key="msg.id" class="message">
      <p>
        <!-- Display the username and message content -->
        <strong>{{ msg.username }}:</strong> {{ msg.content }}
        <!-- Display the formatted timestamp for each message -->
        <span class="timestamp">{{ formatTimestamp(msg.timestamp) }}</span>
      </p>
    </div>
    <!-- Input field to type a new message; triggers sendMessage on Enter key -->
    <el-input v-model="newMessage" placeholder="Type your message..." @keyup.enter="sendMessage" />
    <!-- Button to send a new message -->
    <el-button @click="sendMessage">Send</el-button>
  </div>
</template>

<script setup>
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useRoute } from 'vue-router';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import useUser from "../../store/user"; // Import user store to get user data

const { proxy } = getCurrentInstance(); // Get the current Vue instance for accessing global properties
const route = useRoute(); // Get route information to access query parameters
const event = ref(route.query.event); // Reference to the event ID from the route query parameter
const messages = ref([]); // List of messages to display
const newMessage = ref(''); // Reference to the new message input

// Initialize user store to get user information
const userStore = useUser();
let stompClient; // Declare STOMP client for WebSocket communication

// Function to fetch messages by event ID from the backend API
const fetchMessages = async () => {
  const response = await proxy.$api.getMessagesByEventId({ eventId: event.value });
  messages.value = response;
  console.log("Messages fetched:", messages.value);
};

// Function to send a new message
const sendMessage = () => {
  if (newMessage.value.trim() === '') return; // Prevent sending empty messages
  const now = new Date();
  const offset = now.getTimezoneOffset();
  const localTime = new Date(now.getTime() - (offset * 60 * 1000)).toISOString(); // Convert to ISO string in local timezone

  const message = {
    eventId: event.value, // Event ID to associate the message with
    senderId: userStore.user.id, // Sender ID from user store
    content: newMessage.value, // Message content
    timestamp: localTime // Current timestamp in ISO format
  };

  // Send the message through the WebSocket connection
  stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
  newMessage.value = ''; // Clear the input field after sending
};

// Function to establish a WebSocket connection using SockJS and STOMP
const connect = () => {
  const socket = new SockJS('http://10.72.102.12:8081/ws'); // Create SockJS connection
  stompClient = Stomp.over(socket); // Wrap SockJS connection with STOMP
  stompClient.connect({}, (frame) => {
    // Subscribe to the topic to receive messages
    stompClient.subscribe('/topic/messages', (message) => {
      const receivedMessage = JSON.parse(message.body); // Parse incoming message
      console.log("receivedMessage", receivedMessage);
      messages.value.push(receivedMessage); // Add received message to the list
    });
  }, (error) => {
    console.error('WebSocket connection error:', error); // Log connection errors
  });
};

// Function to format the timestamp of each message for display
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

// Lifecycle hook to fetch messages and connect to WebSocket on component mount
onMounted(() => {
  fetchMessages(); // Fetch existing messages from the server
  connect(); // Establish WebSocket connection
});
</script>

<style scoped>
.message {
  padding: 10px; /* Padding around each message */
  border-bottom: 1px solid #ddd; /* Divider line between messages */
}

.timestamp {
  float: right; /* Align timestamp to the right */
  color: #888; /* Set timestamp color to a lighter shade */
}
</style>
