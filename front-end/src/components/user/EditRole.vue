<template>
    <el-drawer
      size="30%"
      v-model="drawer"
      :title="formData.roleId ? 'Edit Role' : 'Add Role'"
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
        label-width="90px"
      >
        <el-form-item label="Role Name" prop="roleName">
          <el-input v-model="formData.roleName" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm(formRef)">
            {{ formData.roleId ? "Edit" : "Add" }}
          </el-button>
          <el-button @click="handleClose">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </template>
  
  <script setup lang="ts">
  import { FormInstance, FormRules, ElNotification  } from "element-plus";
  import { reactive, ref } from "vue";
  import { $addRole, $updateRole } from "../../api/mockData/role.ts";
  // expose to parent component, so that the parent component can call the drawer
  const emit = defineEmits(["update-role-list"]);
  // drawer
  const drawer = ref(false);
  // open drawer
  const handleOpen = (row: any) => {
    drawer.value = true;
    formData.value = { ...row };
  };
  // close drawer
  const handleClose = () => {
    drawer.value = false;
    resetForm(formRef.value); // reset form
  };
  // form ref
  const formRef = ref<FormInstance>();
  // form data
  const formData = ref({
    roleId: null,
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
  const rules = ref<FormRules<typeof formData>>({
    roleName: [{ required: true, validator: validateRoleName, trigger: "blur" }],
  });
  // submit form
  const submitForm = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.validate(async (valid) => {
      if (valid) {
        let res;
      if (formData.value.roleId) {
        // edit
        res = await $updateRole(formData.value);
      } else {
        // add
        res = await $addRole(formData.value);
      }
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
      // reset form data
      formData.value = {
          roleId: null,
          roleName: "",
      };
  };
  // expose to parent component
  defineExpose({
    drawer,
    formData,
    handleOpen
  });
  </script>
  
  <style lang="scss"></style>