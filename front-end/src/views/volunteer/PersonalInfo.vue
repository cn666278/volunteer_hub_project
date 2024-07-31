<template>
  <div class="PersonalInfo">
    <el-form :model="form" label-width="auto" style="max-width: 600px">
      <!-- User Profile Display -->
      <div class="user-profile">
        <img :src="userStore.user.photo" class="user-avatar">
        <div class="username">{{ userStore.user.username }}</div>
      </div>

      <!-- Multiple Activity Name Fields -->
      <el-form-item :label="$t('personalInfo.name')">
        <el-input v-model="form.name1" />
      </el-form-item>
      <el-form-item :label="$t('personalInfo.gender')">
        <el-input v-model="form.name2" />
      </el-form-item>
      <el-form-item :label="$t('personalInfo.phone')">
        <el-input v-model="form.name3" />
      </el-form-item>
      <el-form-item :label="$t('personalInfo.email')">
        <el-input v-model="form.name4" />
      </el-form-item>

      <!-- Simplified Activity Time Field -->
      <el-form-item :label="$t('personalInfo.dateOfBirth')">
        <el-date-picker
            v-model="form.date"
            type="date"
            :placeholder="$t('personalInfo.pickDate')"
            style="width: 100%"
        />
      </el-form-item>

      <!-- Activity Type with Checkboxes -->
      <el-form-item :label="$t('personalInfo.credentials')">
        <el-checkbox-group v-model="form.type">
          <el-checkbox :label="$t('personalInfo.onlineActivities')" value="Credentials1">
            {{ $t('personalInfo.onlineActivities') }}
          </el-checkbox>
          <el-checkbox :label="$t('personalInfo.promotionActivities')" value="Credentials2">
            {{ $t('personalInfo.promotionActivities') }}
          </el-checkbox>
          <el-checkbox :label="$t('personalInfo.offlineActivities')" value="Credentials3">
            {{ $t('personalInfo.offlineActivities') }}
          </el-checkbox>
          <el-checkbox :label="$t('personalInfo.simpleBrandExposure')" value="Credentials4">
            {{ $t('personalInfo.simpleBrandExposure') }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <!-- Activity Form Text Area -->
      <el-form-item :label="$t('personalInfo.personalStatement')">
        <el-input v-model="form.desc" type="textarea" />
      </el-form-item>

      <!-- Form Submission Buttons -->
      <el-form-item>
        <el-button type="primary" @click="onSubmit">{{ $t('personalInfo.create') }}</el-button>
        <el-button>{{ $t('personalInfo.cancel') }}</el-button>
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
