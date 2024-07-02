<template>
    <div class="login">
      <div class="login-form">
        <h2>Welsh Sport Volunteer Hub</h2>
        <el-form
          size="default"
          ref="formRef"
          style="max-width: 600px"
          :model="formData"
          status-icon
          :rules="rules"
          label-width="80px"
        >
          <el-form-item label="Username" prop="username">
            <el-input v-model="formData.username" />
          </el-form-item>
          <el-form-item label="Password" prop="password">
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

// validate username
const validateUsername = (_: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("Please enter the username"));
  } else {
    callback();
  }
};

// validate password
const validatePassword = (_: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error("Please enter the password"));
  } else {
    callback();
  }
};


// submit form rules
const rules = reactive<FormRules<typeof formData>>({
  username: [{ validator: validateUsername, trigger: "blur" }],
  password: [{ validator: validatePassword, trigger: "blur" }],
});

</script>
<style scoped lang='scss'>
.login{
    width: 100vw;
    height: 100vh;
    background: linear-gradient(to bottom, #2866ad, #6894c7);
    display: flex;
    justify-content: center;
    align-items: center;
    .login-form {
    width: 600px;
    border-radius: 10px;
    border: 1px solid #fff;
    padding: 20px;
    h2 {
      color: #fff;
      font-size: 20px;
      text-align: center;
      margin-bottom: 20px;
    }
    :deep(.el-form-item__label) {
      color: white;
    }
  }
}
</style>