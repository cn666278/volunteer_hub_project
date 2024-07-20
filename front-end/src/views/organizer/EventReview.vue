<template>
  <div>
    <el-table :data="volunteers" stripe>
      <el-table-column prop="username" label="Username"></el-table-column>
      <el-table-column label="Contact">
        <template v-slot="scope">
          <div>{{ scope.row.phone }}</div>
          <div>{{ scope.row.email }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="experience" label="Experience"></el-table-column>
      <el-table-column prop="eventCount" label="Event Count"></el-table-column>
      <el-table-column prop="roleName" label="Role"></el-table-column>
      <el-table-column label="Credentials">
        <template v-slot="scope">
          <div v-for="credential in scope.row.credentialList" :key="credential.credentialName">
            <el-link type="primary" @click="viewCredential1(credential.credentialUrl)">
              {{ credential.credentialName }}
            </el-link>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="Actions">
        <template v-slot="scope">
          <el-button @click="acceptVolunteer(scope.row.id)">Accept</el-button>
          <el-button @click="rejectVolunteer(scope.row.id)">Reject</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" title="Credential Image">
      <img :src="currentCredentialUrl" alt="Credential Image" class="img-fluid"/>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessage,ElMessageBox } from 'element-plus'
const route = useRoute();
const { proxy } = getCurrentInstance();

const volunteers = ref([]);
const totalVolunteers = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const event = JSON.parse(route.query.event);
const dialogVisible = ref(false);
const currentCredentialUrl = ref('');
const viewCredential1 = (url) => {
  console.log("url",url)
  ElMessageBox.alert(
      `<img src="${url}"/>`,
      {
        dangerouslyUseHTMLString: true,
      }
  )
}
const fetchVolunteers = async () => {
  const response = await proxy.$api.getVolunteersByEventId({
    eventId: event.eventId,
    pageNum: currentPage.value,
    pageSize: pageSize.value,
  });
  volunteers.value = response.list;
  totalVolunteers.value = response.total;
};

const handlePageChange = (page) => {
  currentPage.value = page;
  fetchVolunteers();
};

const viewCredential = (url) => {
  console.log("url:", url);
  currentCredentialUrl.value = url;
  dialogVisible.value = true;
  console.log("dialogVisible.value", dialogVisible.value);
  console.log("currentCredentialUrl.value", currentCredentialUrl.value);
};

const acceptVolunteer = async (id) => {
  await updateVolunteerStatus(id, 'accepted');
  fetchVolunteers();
};

const rejectVolunteer = async (id) => {
  await updateVolunteerStatus(id, 'rejected');
  fetchVolunteers();
};

const updateVolunteerStatus = async (id, status) => {
  try {
    await proxy.$api.updateVolunteerStatus({ id, status });
    ElMessage.success(`Volunteer has been ${status}.`);
  } catch (error) {
    ElMessage.error('Failed to update volunteer status.');
  }
};
onMounted(() => {
  fetchVolunteers();
});
</script>

<style scoped>
/* Add your styles here */
</style>
