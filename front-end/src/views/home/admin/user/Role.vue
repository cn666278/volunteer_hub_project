<template>
  <div class="role">
    <div class="search">
      <el-button type="primary" size="small" @click="editDrawerRef.drawer = true"
        >Add</el-button
      >
    </div>
    <el-table :data="showRoles" stripe style="width: 100%">
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
    <el-pagination
      style="margin-top: 5px"
      background
      layout="prev, pager, next"
      :total="roles.length"
      @current-change="handleCurrentChange"
    />
    <EditRole ref="editDrawerRef" @update-role-list="loadRoles"></EditRole>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from "vue";
import EditRole from "../../../../components/user/EditRole.vue";
import { $list } from "../../../../api/role.ts";

// role list
let roles = ref<any[]>([]);
// page index
let pageIndex = ref(1);
// show roles
let showRoles = computed(() => {
  return roles.value.slice((pageIndex.value - 1) * 10, pageIndex.value * 10);
});
// page change event
const handleCurrentChange = (val: number) => {
  pageIndex.value = val;
};
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
// drawer ref
// define EditRoleRef, by editDrawerRef can get the instance object exposed by the component
const editDrawerRef = ref();
onMounted(() => {
  loadRoles();
});
</script>

<style lang="scss">
.search {
  margin-bottom: 20px;
}
</style>
