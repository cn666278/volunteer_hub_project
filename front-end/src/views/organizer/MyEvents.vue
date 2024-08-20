<template>
  <div>
    <el-input v-model="search.title" :placeholder="t('myEvent.searchByTitle')" @input="fetchEvents" />
    <el-select v-model="search.status" :placeholder="t('myEvent.filterByStatus')" @change="fetchEvents">
      <el-option :label="t('myEvent.pending')" value="Pending" />
      <el-option :label="t('myEvent.approved')" value="Approved" />
      <el-option :label="t('myEvent.rejected')" value="Rejected" />
    </el-select>
    <el-table :data="events" style="width: 100%">
      <el-table-column prop="title" :label="t('myEvent.title')" />
      <el-table-column prop="description" :label="t('myEvent.description')" />
      <el-table-column prop="location" :label="t('myEvent.location')" />
      <el-table-column prop="startDateFormat" :label="t('myEvent.startDate')" />
      <el-table-column prop="endDateFormat" :label="t('myEvent.endDate')" />
      <el-table-column prop="status" :label="t('myEvent.status')" />
      <el-table-column prop="pointsAwarded" :label="t('myEvent.pointsAwarded')" />
      <el-table-column :label="t('myEvent.actions')">
        <template v-slot="scope">
          <el-button @click="editEvent(scope.row)">{{ t('myEvent.edit') }}</el-button>
          <el-button @click="reviewEvent(scope.row)">{{ t('myEvent.review') }}</el-button>
          <el-button @click="discussEvent(scope.row)">{{ t('myEvent.discuss') }}</el-button>
          <el-button @click="commentEvent(scope.row)">{{ t('myEvent.comment') }}</el-button>
          <el-button @click="deleteEvent(scope.row)">{{ t('myEvent.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :page-sizes="[2, 5, 10, 20]"
        layout="total, sizes, prev, pager, next"
        :total="totalEvents"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useI18n } from 'vue-i18n';
import { ElMessage } from 'element-plus';
import dayjs from 'dayjs';
import { useRouter } from "vue-router";

const { t } = useI18n();
let router = useRouter();
import useUser from "../../store/user";
// user store
const userStore = useUser();
const { proxy } = getCurrentInstance();
const convertToCustomFormat = (isoString: string) => {
  return dayjs(isoString).format('HH:mm:ss DD/MM/YYYY');
};
const events = ref([]);
const search = ref({ title: '', status: '' });
const currentPage = ref(1);
const pageSize = ref(2);
const totalEvents = ref(0);

const fetchEvents = async () => {
  const params = {
    organizerId: userStore.user.id,
    title: search.value.title,
    status: search.value.status,
    page: currentPage.value,
    size: pageSize.value,
  };
  try {
    const response = await proxy.$api.getEventsByOrganizerIdAndFilters(params);
    events.value = response.list.map((event: any) => ({
      ...event,
      startDateFormat: convertToCustomFormat(event.startDate),
      endDateFormat: convertToCustomFormat(event.endDate)
    }));
    totalEvents.value = response.total;
  } catch (error) {
    ElMessage.error(t('myEvent.fetchEventsError'));
  }
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  fetchEvents();
};

const handleSizeChange = (size: number) => {
  pageSize.value = size;
  fetchEvents();
};

onMounted(fetchEvents);

const editEvent = (event: any) => {
  console.log('Edit event:', event);
  router.push({ name: 'EventEdit', query: { event: JSON.stringify(event) } });
};


const reviewEvent = (event: any) => {
  console.log('Review event:', event);
  router.push({ name: 'EventReview', query: { event: JSON.stringify(event) } });
};

const discussEvent = (event: any) => {
  console.log('Discuss event:', event);
  router.push({ name: 'EventDiscuss', query: { event: JSON.stringify(event) } });
  // Handle discuss event
};

const commentEvent = (event: any) => {
  console.log('Comment event:', event);
  router.push({ name: 'CommentVolunteer', query: { event: JSON.stringify(event) } });
  // Handle comment event
};

const deleteEvent = (event: any) => {
  console.log('Delete event:', event);
  // Handle delete event
};
</script>

<style scoped>
/* 这里可以添加你的样式 */
</style>
