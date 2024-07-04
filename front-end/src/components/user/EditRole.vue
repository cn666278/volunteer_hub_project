<template>
    <el-drawer
      size="30%"
      v-model="drawer"
      title="Add Role"
      direction="rtl"
      :before-close="handleClose"
    >
      <el-form
        size="small"
        ref="formRef"
        style="max-width: 600px"
        :model="formData"
        status-icon
        :rules="rules"
        label-width="70px"
      >
        <el-form-item label="Role Name" prop="roleName">
          <el-input v-model="formData.roleName" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(formRef)">
            Add
          </el-button>
          <el-button @click="resetForm(formRef)">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </template>
  
  <script setup lang="ts">
  import { FormInstance, FormRules, ElNotification  } from "element-plus";
  import { reactive, ref } from "vue";
  import { $addRole } from "../../api/role.ts";
  // expose to parent component, so that the parent component can call the drawer
  const emit = defineEmits(["update-role-list"]);
  // drawer
  const drawer = ref(false);
  // close drawer
  const handleClose = () => {
    drawer.value = false;
    formRef.value?.resetFields(); // reset form
  };
  // form ref
  const formRef = ref<FormInstance>();
  // form data
  const formData = reactive({
    roleName: "",
  });
  // validate role name
  const validateRoleName = (_: any, value: any, callback: any) => {
    if (value === "") {
      callback(new Error("Please enter role name"));
    } else {
      callback();
    }
  };
  // form rules
  const rules = reactive<FormRules<typeof formData>>({
    roleName: [{ required: true, validator: validateRoleName, trigger: "blur" }],
  });
  // submit form
  const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.validate(async (valid) => {
      if (valid) {
        const res = await $addRole(formData);
      if (res.code === 200) {
        ElNotification({
          title: "Notification",
          message: res.data.message,
          type: "success",
        });
        emit("update-role-list"); // update role list
        handleClose(); // close drawer
        console.log("success submit!");
      } else {
        ElNotification({
          title: "Notification",
          message: res.data.message,
          type: "error",
        });
        console.log("error submit!");
      }
      } else {
        console.log("error submit!");
        // return false;
      }
    });
  };
  // reset form
  const resetForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.resetFields();
  };
  // expose to parent component
  defineExpose({
    drawer,
  });
  </script>
  
  <style lang="scss"></style>