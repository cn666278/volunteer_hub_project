<template>
  <div>
    <el-form :model="volunteer">
      <el-form-item label="Username">
        <el-input v-model="volunteer.username" disabled></el-input>
      </el-form-item>
<!--      <el-form-item label="Phone">-->
<!--        <el-input v-model="volunteer.phone" disabled></el-input>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="Email">-->
<!--        <el-input v-model="volunteer.email" disabled></el-input>-->
<!--      </el-form-item>-->
      <el-form-item label="Star">
        <el-rate v-model="volunteer.rating"></el-rate>
      </el-form-item>
      <el-form-item label="Comment">
        <el-input v-model="volunteer.comment" type="textarea"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitComment">Submit</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import {getCurrentInstance, ref} from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
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
    console.log("volunteer.value.volunteerId",volunteer.value.volunteerId)
    ElMessage.success('Comment submitted successfully.');
    console.log("submitComment event",event.value)
    router.push({ name: 'CommentVolunteer', query: { event: JSON.stringify(event.value) } });
  } catch (error) {
    ElMessage.error('Failed to submit comment.');
  }
};

const cancel = () => {
  router.push({ name: 'CommentVolunteer' });
};
</script>
