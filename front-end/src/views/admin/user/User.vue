<template>
  <div class="user">
    <div class="search">
      <!-- Search -->
      <span style="color: grey; font-size: 13px">{{ $t('user.role') }}: </span>
      <el-select
        size="small"
        v-model="roleId"
        :placeholder="$t('user.search.placeholder')"
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
        {{ $t('user.search') }}
      </el-button>
      <!-- Add Button -->
      <el-button
        type="primary"
        size="small"
        @click="editDrawerRef.handleOpen()"
      >
        {{ $t('user.add') }}
      </el-button>
    </div>
    <!-- User Table -->
    <el-table
      :data="userList"
      stripe
      style="width: 100%"
      :key="isUpdate.toString()"
    >
      <el-table-column prop="id" :label="$t('user.id')" width="100" />
      <el-table-column prop="loginId" :label="$t('user.loginId')" width="150" />
      <el-table-column prop="username" :label="$t('user.username')" width="150" />
      <el-table-column prop="photo" :label="$t('user.photo')" width="100">
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

      <el-table-column prop="phone" :label="$t('user.phone')" width="150" />
      <el-table-column prop="email" :label="$t('user.email')" width="200" />
      <el-table-column prop="role.roleName" :label="$t('user.role')" width="150" sortable />
      <el-table-column :label="$t('user.action')">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row.id)">
            {{ $t('user.edit') }}
          </el-button>
          <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.row)"
          >
            {{ $t('user.delete') }}
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
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const { proxy }: any = getCurrentInstance();
// user list
let userList = ref<any>([]);

// page index
let pageIndex = ref(1);

// page size
let pageSize = 10;

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
  roleList.value.unshift({ roleId: 0, roleName: t('user.role.all') });
};

// get user list
const getUserList = async () => {
  console.log("Load user list");
  let res = await proxy.$api.getUserList();
  let { list, count } = res;
  // 当roleId改变时，筛选出对应roleId的用户，并更新用户列表
  console.log(roleId.value);
  if (roleId.value !== 0) {
    list = list.filter((user: any) => Number(user.role.roleId) === roleId.value);
  }
  // Pagination前端分页
  const start = (pageIndex.value - 1) * pageSize;
  const end = start + pageSize;
  list = list.slice(start, end);
  userList.value = list;
  total.value = count;
  isUpdate.value = !isUpdate.value; // update table
};

// Edit user
const handleEdit = async (id: number) => {
  let res = await proxy.$api.getUserById({id: id});
  editDrawerRef.value.handleOpen(res);
};

// Delete user
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
    t('user.confirmDelete', { username: row.username }),
    t('user.notification'),
    {
      confirmButtonText: t('user.confirm'),
      cancelButtonText: t('user.cancel'),
      type: "warning",
    }
  )
    .then(async () => {
      let res = await proxy.$api.deleteUser({id: row.id});
      if (res) {
        ElNotification({
          title: t('user.notification'),
          message: res,
          type: "success",
        });
        // delete successfully, reload role list
        getUserList();
        console.log("Delete successfully!");
      } else {
        ElNotification({
          title: t('user.notification'),
          message: res,
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
