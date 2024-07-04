<template>
  <div class="user">
    <div class="search">
      <!-- Search -->
      <span style="color: grey; font-size: 13px">Role: </span>
      <el-select
        size="small"
        v-model="roleId"
        placeholder="Please search a role"
        style="width: 240px; margin-right: 10px"
      >
        <el-option
          v-for="item in roleList"
          :key="item.roleId"
          :label="item.roleName"
          :value="item.roleId"
        />
      </el-select>
      <el-button type="success" size="small" @click="getUserList">
        Search</el-button
      >
      <!-- Add Button -->
      <el-button
        type="primary"
        size="small"
        @click="editDrawerRef.handleOpen()"
      >
        Add</el-button
      >
    </div>
    <!-- User Table -->
    <el-table
      :data="userList"
      stripe
      style="width: 100%"
      :key="isUpdate.toString()"
    >
      <el-table-column prop="id" label="ID" width="100" />
      <el-table-column prop="loginId" label="LoginId" width="150" />
      <el-table-column prop="username" label="Username" width="150" />
      <el-table-column prop="photo" label="Photo" width="100">
        <template #default="scope">
          <el-avatar
            v-if="scope.row.photo"
            :size="40"
            :src="scope.row.photo"
            fit="cover"
          />
          <el-avatar
            v-else
            :size="40"
            src="https://s2.loli.net/2024/06/07/pngIsY8GxMOArz7.png"
            fit="cover"
          />
        </template>
      </el-table-column>

      <el-table-column prop="phone" label="Phone" width="150" />
      <el-table-column prop="email" label="Email" width="200" />
      <el-table-column prop="role.roleName" label="Role" width="150" sortable />
      <el-table-column label="Action">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row.loginId)">
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
      :total="total"
      v-model:current-page="pageIndex"
      :default-page-size="10"
      @current-change="getUserList"
    />
    <!-- Edit User Drawer-->
    <EditUser ref="editDrawerRef" @update-user-list="getUserList" />
  </div>
</template>

<script setup lang="ts">
import EditUser from "../../components/user/EditUser.vue";
import { onMounted, ref } from "vue";
import {
  $getUserList,
  $getUserByLoginId,
  $deleteUser,
} from "../../api/admin.ts";
import { $getRoleList } from "../../api/mockData/role.ts";
import { ElMessageBox, ElNotification } from "element-plus";

// user list
let userList = ref<any>([]);

// page index
let pageIndex = ref(1);

let total = ref(0);

// When the user list data changes, modify this value, bind the table key, and trigger the table update
let isUpdate = ref(false);

// role id
let roleId = ref(0);

// role list
const roleList: any = ref([]);

// page change event
const getRoleList = async () => {
  console.log("Load role list");
  let res = await $getRoleList();
  roleList.value = res;
  roleList.value.unshift({ roleId: 0, roleName: "All Roles" });
};

// get user list
const getUserList = async () => {
  console.log("Load user list");
  let { data, count } = await $getUserList({
    pageIndex: pageIndex.value,
    pageSize: 10,
    roleId: roleId.value,
  });
  userList.value = data;
  total.value = count;
  isUpdate.value = !isUpdate.value; // update table
};

// Edit user
const handleEdit = async (loginId: string) => {
  let res = await $getUserByLoginId(loginId);
  editDrawerRef.value.handleOpen(res);
};

// Delete user
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
      let res = await $deleteUser(row.roleId);
      if (res.code === 200) {
        ElNotification({
          title: "Notification",
          message: res.data.message,
          type: "success",
        });
        // delete successfully, reload role list
        getUserList();
        console.log("Delete successfully!");
      } else {
        ElNotification({
          title: "Notification",
          message: res.data.message,
          type: "error",
        });
      }
    })
    .catch(() => {
      console.log("Cancel delete");
    });
};

// drawer ref
const editDrawerRef = ref();

// load role list when the page is mounted
onMounted(() => {
  getRoleList();
  getUserList();
});
</script>

<style lang="scss">
.search {
  margin-bottom: 20px;
}
.--el-avatar-bg-color {
  color: red;
}
</style>
