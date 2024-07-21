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
          <el-button @click="commentVolunteer(scope.row)">Comment</el-button>
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
import { useRoute, useRouter } from 'vue-router';
import { ElMessageBox } from 'element-plus';

const route = useRoute();
const router = useRouter();
const { proxy } = getCurrentInstance();

const volunteers = ref([]);
const totalVolunteers = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const event = JSON.parse(route.query.event);
const dialogVisible = ref(false);
const currentCredentialUrl = ref('');

const viewCredential1 = (url) => {
  ElMessageBox.alert(
      `<img src="${url}"/>`,
      {
        dangerouslyUseHTMLString: true,
      }
  )
}

const fetchVolunteers = async () => {
  console.log("getVolunteersByEventId event",event)
  const response = await proxy.$api.getVolunteersByEventId({
    eventId: event.eventId,
    status: 'accepted',
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

const commentVolunteer = (volunteer) => {
  console.log("commentVolunteer event",event)
  router.push({
    name: 'CommentDetail',
    query: {
      volunteer: JSON.stringify({
        ...volunteer,
        eventId: event.eventId,
        organizerId: event.organizerId
      }),
      event: JSON.stringify(event)
    },
  });
};


onMounted(() => {
  fetchVolunteers();
});
</script>

<style scoped>
/* Add your styles here */
</style>
