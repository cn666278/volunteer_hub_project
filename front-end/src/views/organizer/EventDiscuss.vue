<template>
  <div>
    <div v-for="msg in messages" :key="msg.id" class="message">
      <p>
        <strong>{{ msg.username }}:</strong> {{ msg.content }}
        <span class="timestamp">{{ formatTimestamp(msg.timestamp) }}</span>
        <el-button
            size="mini"
            type="danger"
            @click="deleteMessage(msg.id)"
            class="delete-button"
        >
        delete
        </el-button>
      </p>
    </div>
    <el-input
        v-model="newMessage"
        :placeholder="t('eventDiscuss.placeholder')"
        @keyup.enter="sendMessage"
    />
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
  const socket = new SockJS('http://10.72.102.12:8081/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, (frame) => {
    stompClient.subscribe('/topic/messages', (message) => {
      const receivedMessage = JSON.parse(message.body);
      console.log("receivedMessage", receivedMessage)
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

// 删除消息
const deleteMessage = async (messageId) => {
  try {
    const response = await proxy.$api.deleteMessage({ id: messageId });
    if (response) {
      messages.value = messages.value.filter(msg => msg.id !== messageId);
    } else {
    }
  } catch (error) {
  }
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
  position: relative;
}
.timestamp {
  float: right;
  color: #888;
  margin-right: 100px;
}

.delete-button {
  position: absolute;
  right: 10px;
  top: 5px;
}
</style>
