<template>
  <div>
    <vue-cal
        v-model="calendarDate"
        :events="events"
        @cell-click="handleCellClick"
    ></vue-cal>
  </div>
</template>

<script setup lang='ts'>
import {ref, onMounted, getCurrentInstance, watch} from 'vue';
import VueCal from 'vue-cal';
import 'vue-cal/dist/vuecal.css';
import { useI18n } from 'vue-i18n';

const { proxy } = getCurrentInstance();
const { t } = useI18n();

interface Event {
  id: number;
  organizerId: number;
  title: string;
  description: string;
  location: string;
  startDate: string;
  endDate: string;
  status: string;
  notifications: string;
  pointsAwarded: number;
}

const calendarDate = ref(new Date());
const events = ref([
  {
    id: 1,
    title: t('home.event1Title'),
    start: '2024-07-12 10:00',
    end: '2024-07-12 12:00'
  },
  {
    id: 2,
    title: t('home.event2Title'),
    start: '2024-07-13 14:00',
    end: '2024-07-13 16:00'
  }
]);

function convertToCustomFormat(isoString : any) {
  const date = new Date(isoString);

  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}`;
}

const fetchEvents = async (month: number, year: number) => {
  try {
    const response = await proxy.$api.getEventsByDate({ month, year });
    events.value = response.map(event => ({
      id: event.id,
      start: convertToCustomFormat(event.startDate),
      end: convertToCustomFormat(event.endDate),
      title: event.title
    }));
    console.log("fetchEvents : events :", events);
  } catch (error) {
    console.error("Error fetching events:", error);
  }
};

const handlePanelChange = (date: Date) => {
  console.log("handlePanelChange date:", date);
  const month = date.getMonth() + 1;
  const year = date.getFullYear();
  // fetchEvents(month, year);
};

const handleCellClick = (date) => {
  console.log(t('home.cellClicked'), date);
};

onMounted(() => {
  console.log("onMounted")
  console.log(t('home.initialEvents'), events);

  const month = calendarDate.value.getMonth() + 1;
  const year = calendarDate.value.getFullYear();
  fetchEvents(month, year);
});

watch(calendarDate, (newDate) => {
  console.log(t('home.watchCalendarDate'))
  const month = newDate.getMonth() + 1;
  const year = newDate.getFullYear();
  // fetchEvents(month, year);
});
</script>

<style>
.vuecal__event {
  background-color: #409EFF;
  color: #ffffff;
  border-radius: 4px;
  padding: 4px;
  margin: 2px 0;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
