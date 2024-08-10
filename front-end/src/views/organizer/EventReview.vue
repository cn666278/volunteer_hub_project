<template>
  <div>
    <el-table :data="volunteers" stripe>
      <el-table-column prop="username" label="Username"></el-table-column>
<!--      <el-table-column label="Contact">-->
<!--        <template v-slot="scope">-->
<!--          <div>{{ scope.row.phone }}</div>-->
<!--          <div>{{ scope.row.email }}</div>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="experience" label="Experience"></el-table-column>
      <el-table-column prop="eventCount" label="Event Count"></el-table-column>
      <el-table-column prop="roleName" label="Role"></el-table-column>
      <el-table-column label="Credentials">
        <template v-slot="scope">
          <div v-for="credential in scope.row.credentialList" :key="credential.credentialName">
            <el-link type="primary" @click="viewCredential(credential.credentialUrl)">
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
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';

const route = useRoute();
const { proxy } = getCurrentInstance();

const volunteers = ref([]);
const totalVolunteers = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const event = JSON.parse(route.query.event);

const viewCredential = async (url) => {
  try {
    console.log("getfiles start");
    let id = Number(url.split("/").pop());
    const response = await proxy.$api.getfiles({ id: id });

    console.log("response", response);

    if (response) {
      // 将Base64字符串转换为Blob
      const base64Data = response;
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });
      console.log("blob", blob);

      // 创建一个URL对象并显示图片
      const imgURL = URL.createObjectURL(blob);

      ElMessageBox.alert(
          `<img src="${imgURL}" alt="Credential Image" class="img-fluid">`,
          'Credential Image',
          {
            dangerouslyUseHTMLString: true,
            customClass: 'custom-el-messagebox',
            showConfirmButton: false,
            center: true,
          }
      );
    }
  } catch (error) {
    console.error("Error fetching file", error);
  }
};
const fetchVolunteers = async () => {
  const response = await proxy.$api.getVolunteersByEventId({
    eventId: event.eventId,
    status: 'pending',
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
.custom-el-messagebox .el-message-box__content img {
  /*max-width: 100%;*/
  height: auto;
  display: block;
  margin: 0 auto;
}
</style>
