<template>
  <el-drawer
    size="30%"
    v-model="drawer"
    :title="formData.roleId ? $t('role.editTitle') : $t('role.addTitle')"
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
      <el-form-item :label="$t('role.roleName')" prop="roleName">
        <el-input v-model="formData.roleName" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(formRef)">
          {{ formData.roleId ? $t('role.edit') : $t('role.add') }}
        </el-button>
        <el-button @click="handleClose">{{ $t('role.reset') }}</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script setup lang="ts">
import { FormInstance, FormRules, ElNotification } from "element-plus";
import { getCurrentInstance, ref } from "vue";
import { useI18n } from 'vue-i18n'

const { t } = useI18n();
const { proxy }: any = getCurrentInstance();
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
    callback(new Error(t('role.validateRoleName')));
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
        res = await proxy.$api.updateRole(formData.value);
      } else {
        // add
        res = await proxy.$api.addRole({roleName: formData.value.roleName});
      }
      if (res) {
        ElNotification({
          title: t('role.notificationTitle'),
          message: res.message,
          type: "success",
        });
        emit("update-role-list"); // update role list
        handleClose(); // close drawer
        console.log("success submit!");
      } else {
        ElNotification({
          title: t('role.notificationTitle'),
          message: res.message,
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
  handleOpen,
});
</script>

<style lang="scss"></style>
