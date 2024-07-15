<template>
  <div class="role">
    <div class="addButton">
      <el-button type="primary" size="small" @click="editDrawerRef.handleOpen()"
        >Add</el-button
      >
    </div>
    <!-- Roles Table -->
    <el-table :data="showRoles" stripe style="width: 100%" :key="isUpdate.toString()">
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
import { onMounted, ref, computed, getCurrentInstance } from "vue";
import EditRole from "../../../components/user/EditRole.vue";
import { ElMessageBox, ElNotification } from "element-plus";

const { proxy }: any = getCurrentInstance();

// role list
let roleList = ref<any[]>([]);
// page index
let pageIndex = ref(1);
// role list update flag
let isUpdate = ref(false);
// show roles
let showRoles = computed(() => {
  isUpdate.value = !isUpdate.value; // update role list
  return roleList.value.slice((pageIndex.value - 1) * 10, pageIndex.value * 10);
});
// page change event
const handleCurrentChange = (val: number) => {
  pageIndex.value = val;
};
// load role list
const getRoleList = async () => {
  console.log("load role list");
  roleList.value = await proxy.$api.getRoleList();
  isUpdate.value = !isUpdate.value; // update role list
};
// edit role
const handleEdit = async (roleId: number) => {
  const role = roleList.value.find((role: any) => role.roleId === roleId);
  editDrawerRef.value.handleOpen(role);
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
      let res = await proxy.$api.deleteRole({roleId: row.roleId});
      if (res) {
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
.addButton {
  margin-bottom: 20px;
}
</style>
