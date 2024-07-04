<template>
  <div class="role">
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
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { $list } from "../../api/role.ts";

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
onMounted(() => {
  loadRoles();
});
</script>

<style lang="scss"></style>
