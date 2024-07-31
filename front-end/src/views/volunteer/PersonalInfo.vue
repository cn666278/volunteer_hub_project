<template>
  <div class="PersonalInfo">
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <!-- User Profile Display -->
      <div class="user-profile">
        <img :src="userStore.user.photo" class="user-avatar">
        <div class="username">{{ userStore.user.username }}</div>
      </div>

      <!-- Multiple Activity Name Fields -->
      <el-form-item label="Name">
        <el-input v-model="form.name1" />
      </el-form-item>
      <el-form-item label="Gender">
        <el-input v-model="form.name2" />
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="form.name3" />
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="form.name4" />
      </el-form-item>

      <!-- Simplified Activity Time Field -->
      <el-form-item label="Date Of Birth">
        <el-date-picker
            v-model="form.date"
            type="date"
            placeholder="Pick a date"
            style="width: 100%"
        />
      </el-form-item>

      <!-- Activity Type with Checkboxes -->
      <el-form-item label="Credentials">
        <el-checkbox-group v-model="form.type">
          <el-checkbox value="Credentials1" name="type">Online activities</el-checkbox>
          <el-checkbox value="Credentials2" name="type">Promotion activities</el-checkbox>
          <el-checkbox value="Credentials3" name="type">Offline activities</el-checkbox>
          <el-checkbox value="Credentials4" name="type">Simple brand exposure</el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <!-- Activity Form Text Area -->
      <el-form-item label="personal statement">
        <el-input v-model="form.desc" type="textarea" />
      </el-form-item>

      <!-- Form Submission Buttons -->
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Create</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue';
import useUser from "../../store/user";

// user store
const userStore = useUser();

const form = reactive({
  name1: '',
  name2: '',
  name3: '',
  name4: '',
  date: '',
  type: [],
  desc: ''
});

const onSubmit = () => {
  console.log('submit!', form);
}
</script>

<style lang="scss" scoped>
.PersonalInfo {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;  // Ensure the form is centered vertically and horizontally
}

.user-profile {
  margin-bottom: 20px;
  width: 100%; /* 确保占满整个容器宽度 */
  display: flex;
  flex-direction: column;
  align-items: center; /* 保证子元素居中 */
}

.username {
  margin-top: 10px; /* 根据需要调整间距 */
  text-align: center; /* 确保文本居中显示 */
  color: #a9181a;
  font-size: 10px;
}

.user-avatar {
  width: 50px; /* 设置头像宽度 */
  height: 50px; /* 设置头像高度 */
  border-radius: 50%; /* 保持圆形 */
  object-fit: cover; /* 使图片覆盖整个区域，多余的部分会被剪裁掉 */
  border: 2px solid  #a9181a; /* 为头像添加边框 */
}
</style>