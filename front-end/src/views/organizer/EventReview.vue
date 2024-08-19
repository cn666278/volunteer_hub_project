<template>
  <div>
    <el-table :data="volunteers" stripe>
      <el-table-column prop="username" :label="t('eventReview.username')"></el-table-column>
      <div style="display: none">
        <el-table-column :label="t('eventReview.contact')">
          <template v-slot="scope">
            <div>{{ scope.row.phone }}</div>
            <div>{{ scope.row.email }}</div>
          </template>
        </el-table-column>
      </div>
      <el-table-column prop="experience" :label="t('eventReview.experience')"></el-table-column>
      <el-table-column prop="eventCount" :label="t('eventReview.eventCount')"></el-table-column>
      <el-table-column prop="roleName" :label="t('eventReview.role')"></el-table-column>
      <el-table-column :label="t('eventReview.credentials')">
        <template v-slot="scope">
          <div v-for="credential in scope.row.credentialList" :key="credential.credentialName">
            <el-link type="primary" @click="viewCredential(credential.credentialUrl)">
              {{ credential.credentialName }}
            </el-link>
          </div>
        </template>
      </el-table-column>
      <el-table-column :label="t('eventReview.actions')">
        <template v-slot="scope">
          <el-button @click="acceptVolunteer(scope.row.id,scope.row.email)">{{ t('eventReview.accept') }}</el-button>
          <el-button @click="rejectVolunteer(scope.row.id,scope.row.email)">{{ t('eventReview.reject') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, getCurrentInstance } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
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
          t('eventReview.credentialImage'),
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

const acceptVolunteer = async (id,email) => {
  await updateVolunteerStatus(id,email, 'accepted');
  fetchVolunteers();
};

const rejectVolunteer = async (id,email) => {
  await updateVolunteerStatus(id,email, 'rejected');
  fetchVolunteers();
};

const updateVolunteerStatus = async (id, email, status) => {
  try {
    let eventId = event.eventId;
    await proxy.$api.updateVolunteerStatus({ id, email, eventId, status });
    ElMessage.success(t('eventReview.volunteerStatusUpdated', { status }));
  } catch (error) {
    ElMessage.error(t('eventReview.updateStatusFailed'));
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
