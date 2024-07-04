<template>
  <div class="role">
    <div class="search">
      <el-button type="primary" size="small" @click="editDrawerRef.handleOpen()"
        >Add</el-button
      >
    </div>
    <!-- Roles Table -->
    <el-table :data="showRoles" stripe style="width: 100%">
      <el-table-column prop="roleId" label="Role ID" width="100" />
      <el-table-column prop="roleName" label="RoleName" width="250" />
      <el-table-column label="edit">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">
            Edit
          </el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
          >
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- Pagination -->
    <el-pagination
      style="margin-top: 5px"
      background
      layout="prev, pager, next"
      :total="roleList.length"
      @current-change="handleCurrentChange"
    />
    <EditRole ref="editDrawerRef" @update-role-list="getRoleList"></EditRole>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from "vue";
import EditRole from "../../../../components/user/EditRole.vue";
import {
  $getRoleList,
  $deleteRole,
  $getSingleRole,
} from "../../../../api/mockData/role.ts";
import { ElMessageBox, ElNotification } from "element-plus";

// role list
let roleList = ref<any[]>([]);
// page index
let pageIndex = ref(1);
// show roles
let showRoles = computed(() => {
  return roleList.value.slice((pageIndex.value - 1) * 10, pageIndex.value * 10);
});
// page change event
const handleCurrentChange = (val: number) => {
  pageIndex.value = val;
};
// load role list
const getRoleList = async () => {
  console.log("load role list");
  roleList.value = await $getRoleList();
};
// edit role
const handleEdit = async (row: any) => {
  let res = await $getSingleRole(row.roleId);
  editDrawerRef.value.handleOpen(res);
};
// delete role
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
    "Are you sure delete role: " + row.roleName + " ?",
    "Notification",
    {
      confirmButtonText: "Confirm",
      cancelButtonText: "Cancel",
      type: "warning",
    }
  )
    .then(async () => {
      let res = await $deleteRole(row.roleId);
      if (res.code === 200) {
        ElNotification({
          title: "Notification",
          message: res.message,
          type: "success",
        });
        // delete successfully, reload role list
        getRoleList();
        console.log("Delete successfully!");
      } else {
        ElNotification({
          title: "Notification",
          message: res.message,
          type: "error",
        });
      }
    })
    .catch(() => {
      console.log("Cancel delete!");
    });
};
// drawer ref
// define EditRoleRef, by editDrawerRef can get the instance object exposed by the component
const editDrawerRef = ref();
onMounted(() => {
  getRoleList();
});
</script>

<style lang="scss">
.search {
  margin-bottom: 20px;
}
</style>
