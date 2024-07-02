<template>
    <div class="login">
      <div class="box">
        <h2>Welsh Sport Volunteer Hub</h2>
        <el-form
          size="small"
          ref="formRef"
          style="max-width: 600px"
          :model="formData"
          status-icon
          :rules="rules"
          label-width="40px"
        >
          <el-form-item label="账号" prop="username">
            <el-input v-model="formData.username" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="formData.password" type="password" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(formRef)">
              Login
            </el-button>
            <el-button @click="resetForm(formRef)">Reset</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </template>
<script setup lang='ts'>
import { onMounted, reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
// form instance
const formRef = ref<FormInstance>();

// form data
const formData = reactive({
  username: "",
  password: "",
});
</script>
<style lang='scss'>
.login{
    width: 100vw;
    height: 100vh;
    background: linear-gradient(to bottom, #2866ad, #6894c7);
    display: flex;
    justify-content: center;
    align-items: center;
    .box{
        width: 400px;
        height: 200px;
        background: #fff;
        border-radius: 10px;
    }
}
</style>