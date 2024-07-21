<template>
  <div>
    <el-input v-model="search.title" placeholder="Search by title" @input="fetchEvents" />
    <el-select v-model="search.status" placeholder="Filter by status" @change="fetchEvents">
      <el-option label="Pending" value="Pending" />
      <el-option label="Approved" value="Approved" />
      <el-option label="Rejected" value="Rejected" />
    </el-select>
    <el-table :data="events" style="width: 100%">
      <el-table-column prop="title" label="Title" />
      <el-table-column prop="description" label="Description" />
      <el-table-column prop="location" label="Location" />
      <el-table-column prop="startDateFormat" label="Start Date" />
      <el-table-column prop="endDateFormat" label="End Date" />
      <el-table-column prop="status" label="Status" />
      <el-table-column prop="pointsAwarded" label="Points Awarded" />
      <el-table-column label="Actions">
        <template v-slot="scope">
          <el-button @click="editEvent(scope.row)">Edit</el-button>
          <el-button @click="reviewEvent(scope.row)">Review</el-button>
          <el-button @click="discussEvent(scope.row)">Discuss</el-button>
          <el-button @click="commentEvent(scope.row)">Comment</el-button>
          <el-button @click="deleteEvent(scope.row)">Delete</el-button>
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
import { ElMessage } from 'element-plus';
import dayjs from 'dayjs';
import { useRouter } from "vue-router";
let router = useRouter();

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
    organizerId: 1,
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
    ElMessage.error('Failed to fetch events');
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
