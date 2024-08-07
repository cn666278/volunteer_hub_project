<!-- EventDetailsDialog.vue -->
<template>
  <el-dialog :visible="visible" @update:visible="updateVisible" title="Event Details" width="800">
    <el-descriptions v-if="currentEvent" :title="currentEvent.title" direction="vertical" :column="2" border>
      <el-descriptions-item label="ID" :span="2">{{ currentEvent.id }}</el-descriptions-item>
      <el-descriptions-item label="Organizer">{{ userInfo?.username }}</el-descriptions-item>
      <el-descriptions-item label="Organizer Email">{{ userInfo?.email }}</el-descriptions-item>
      <el-descriptions-item label="Description" :span="2">{{ currentEvent.description }}</el-descriptions-item>
      <el-descriptions-item label="Location" :span="2">{{ currentEvent.location }}</el-descriptions-item>
      <el-descriptions-item label="Start Date">{{ formatDate(currentEvent.startDate) }}</el-descriptions-item>
      <el-descriptions-item label="End Date">{{ formatDate(currentEvent.endDate) }}</el-descriptions-item>
      <el-descriptions-item label="Status">
        <el-button :type="currentEvent.status === 'Passed' ? 'success' : currentEvent.status === 'Awaiting Review' ? 'warning' : 'danger'" size="small" round>
          {{ currentEvent.status }}
        </el-button>
      </el-descriptions-item>
      <el-descriptions-item label="Points Awarded">{{ currentEvent.pointsAwarded }}</el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="success" @click="approveEvent">Approve</el-button>
        <el-button type="danger" @click="rejectEvent">Reject</el-button>
        <el-button @click="updateVisible(false)">Close</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, watch } from 'vue';

const props = defineProps({
  visible: Boolean,
  currentEvent: Object,
  userInfo: Object
});

const emit = defineEmits(['update:visible']);

const updateVisible = (value: boolean) => {
  emit('update:visible', value);
};

const approveEvent = () => {
  if (props.currentEvent) {
    props.currentEvent.status = 'Passed';
    updateVisible(false);
  }
};

const rejectEvent = () => {
  if (props.currentEvent) {
    // todo: implement reject event
    // props.currentEvent.status = 'Rejected';
    updateVisible(false);
  }
};

const formatDate = (dateStr: string | number | Date) => {
  const options: any = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Intl.DateTimeFormat('en-GB', options).format(new Date(dateStr));
};
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>
