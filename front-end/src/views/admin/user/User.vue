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
      <el-table-column prop="loginId" label="Login ID" width="150" />
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
import EditUser from "../../../components/user/EditUser.vue";
import { getCurrentInstance, onMounted, ref } from "vue";
import { ElMessageBox, ElNotification } from "element-plus";
const { proxy }: any = getCurrentInstance();
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
  let res = await proxy.$api.getRoleList();
  roleList.value = res;
  roleList.value.unshift({ roleId: 0, roleName: "All" });
};

// get user list
const getUserList = async () => {
  console.log("Load user list");
  let res = await proxy.$api.getUserList();
  let { list, count } = res;
  // 当roleId改变时，筛选出对应roleId的用户，并更新用户列表
  if (roleId.value !== 0) {
    list = list.filter((user: any) => user.roleId === roleId.value);
  }
  userList.value = list;
  total.value = count;
  isUpdate.value = !isUpdate.value; // update table
};

// Edit user
const handleEdit = async (loginId: string) => {
  let res = await proxy.$api.getUserByLoginId({loginId: loginId});
  editDrawerRef.value.handleOpen(res);
};

// Delete user
const handleDelete = (row: any) => {
  console.log("Delete user: ", row);
  ElMessageBox.confirm(
    "Are you sure delete user: " + row.username + " ?",
    "Notification",
    {
      confirmButtonText: "Confirm",
      cancelButtonText: "Cancel",
      type: "warning",
    }
  )
    .then(async () => {
      let res = await proxy.$api.deleteUser({loginId: row.loginId});
      if (res) {
        ElNotification({
          title: "Notification",
          message: res.message,
          type: "success",
        });
        // delete successfully, reload role list
        getUserList();
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
