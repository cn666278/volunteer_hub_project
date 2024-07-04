<template>
  <div class="role">
    <div class="search">
      <el-button type="primary" size="small" @click="drawer = true"
        >Add</el-button
      >
    </div>
    <el-table :data="roles" stripe style="width: 100%">
      <el-table-column prop="roleId" label="Role ID" width="100" />
      <el-table-column prop="roleName" label="RoleName" width="250" />
      <el-table-column label="edit">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row.roleId)">
            Edit
          </el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row.roleId)"
          >
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
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
            Submit
          </el-button>
          <el-button @click="resetForm(formRef)">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { FormInstance, FormRules } from "element-plus";
import { $list } from "../../../../api/role.ts";

// role list
let roles = ref([]);
// load role list
const loadRoles = async () => {
  roles.value = await $list();
};
// edit role
const handleEdit = (roleId: number) => {
  console.log(roleId);
};
// delete role
const handleDelete = (roleId: number) => {
  console.log(roleId);
};
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
    callback(new Error("Please enter the role name"));
  } else {
    callback();
  }
};
// validation rules
const rules = reactive<FormRules<typeof formData>>({
  roleName: [{ required: true, validator: validateRoleName, trigger: "blur" }],
});
// submit form
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
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
onMounted(() => {
  loadRoles();
});
</script>

<style lang="scss">
.search {
  margin-bottom: 20px;
}
</style>
