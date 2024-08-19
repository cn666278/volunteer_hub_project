<template>
  <div>
    <el-form :model="volunteer">
      <el-form-item :label="t('commentDetail.username')">
        <el-input v-model="volunteer.username" disabled></el-input>
      </el-form-item>
<!--      <el-form-item :label="t('commentDetail.phone')">-->
<!--        <el-input v-model="volunteer.phone" disabled></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item :label="t('commentDetail.email')">-->
<!--        <el-input v-model="volunteer.email" disabled></el-input>-->
<!--      </el-form-item>-->
      <el-form-item :label="t('commentDetail.star')">
        <el-rate v-model="volunteer.rating"></el-rate>
      </el-form-item>
      <el-form-item :label="t('commentDetail.comment')">
        <el-input v-model="volunteer.comment" type="textarea"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitComment">{{ t('commentDetail.submit') }}</el-button>
        <el-button @click="cancel">{{ t('commentDetail.cancel') }}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import {getCurrentInstance, ref} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const { proxy } = getCurrentInstance();
const route = useRoute();
const router = useRouter();
const volunteer = ref(JSON.parse(route.query.volunteer));
const event = ref(JSON.parse(route.query.event));

const submitComment = async () => {
  try {
    await proxy.$api.submitComment({
      eventId: volunteer.value.eventId,
      organizerId: volunteer.value.organizerId,
      volunteerId: volunteer.value.volunteerId,
      rating: volunteer.value.rating,
      comment: volunteer.value.comment,
    });
    ElMessage.success(t('commentDetail.commentSuccess'));
    router.push({ name: 'CommentVolunteer', query: { event: JSON.stringify(event.value) } });
  } catch (error) {
    ElMessage.error(t('commentDetail.commentFailure'));
  }
};

const cancel = () => {
  router.push({ name: 'CommentVolunteer' });
};
</script>
