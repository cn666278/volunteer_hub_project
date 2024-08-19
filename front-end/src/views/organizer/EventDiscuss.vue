<template>
  <div>
    <div v-for="msg in messages" :key="msg.id" class="message">
      <p>
        <strong>{{ msg.username }}:</strong> {{ msg.content }}
        <span class="timestamp">{{ formatTimestamp(msg.timestamp) }}</span>
      </p>
    </div>
    <el-input v-model="newMessage" :placeholder="t('eventDiscuss.placeholder')" @keyup.enter="sendMessage" />
    <el-button @click="sendMessage">{{ t('eventDiscuss.send') }}</el-button>
  </div>
</template>

<script setup>
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useRoute } from 'vue-router';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { useI18n } from 'vue-i18n';
import useUser from "../../store/user";

const { t } = useI18n();
const { proxy } = getCurrentInstance();
const route = useRoute();
const event = JSON.parse(route.query.event);
const messages = ref([]);
const newMessage = ref('');
// user store
const userStore = useUser();
let stompClient;

const fetchMessages = async () => {
  const response = await proxy.$api.getMessagesByEventId({ eventId: event.eventId });
  messages.value = response;
  console.log("Messages fetched:", messages.value);
};

const sendMessage = () => {
  if (newMessage.value.trim() === '') return;
  const now = new Date();
  const offset = now.getTimezoneOffset();
  const localTime = new Date(now.getTime() - (offset * 60 * 1000)).toISOString();
  const message = {
    eventId: event.eventId,
    senderId: userStore.user.id,
    content: newMessage.value,
    timestamp: localTime
  };

  stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
  newMessage.value = '';
};

const connect = () => {
  const socket = new SockJS('http://localhost:8081/ws'); // 使用后端服务器地址和端口
  stompClient = Stomp.over(socket);
  stompClient.connect({}, (frame) => {
    stompClient.subscribe('/topic/messages', (message) => {
      const receivedMessage = JSON.parse(message.body);
      console.log("receivedMessage", receivedMessage)
      // receivedMessage.username = userStore.user.username; // 确保收到的消息包含username
      messages.value.push(receivedMessage);
    });
  }, (error) => {
    console.error('WebSocket connection error:', error);
  });
};

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

onMounted(() => {
  fetchMessages();
  connect();
});
</script>

<style scoped>
.message {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}
.timestamp {
  float: right;
  color: #888;
}
</style>
