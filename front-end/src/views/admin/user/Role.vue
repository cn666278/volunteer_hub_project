<template>
  <div class="role">
    <div class="addButton">
      <el-button type="primary" size="small" @click="editDrawerRef.handleOpen()">{{ $t('role.add') }}</el-button>
    </div>
    <!-- Roles Table -->
    <el-table :data="showRoles" stripe style="width: 100%" :key="isUpdate.toString()">
      <el-table-column prop="roleId" :label="$t('role.roleId')" width="100" />
      <el-table-column prop="roleName" :label="$t('role.roleName')" width="250" />
      <el-table-column :label="$t('role.edit')">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row.roleId)">{{ $t('role.edit') }}</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">{{ $t('role.delete') }}</el-button>
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
import { onMounted, ref, computed, getCurrentInstance } from 'vue';
import EditRole from '../../../components/user/EditRole.vue';
import { ElMessageBox, ElNotification } from 'element-plus';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
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
  console.log(t('role.loadRoleList'));
  // 从后端获取角色列表
  let res = await proxy.$api.getRoleList();
  // 对角色列表根据 roleId 进行排序
  res.sort((a: { roleId: number; }, b: { roleId: number; }) => a.roleId - b.roleId);
  // 将排序后的列表赋值给 roleList
  roleList.value = res;
  // 触发更新
  isUpdate.value = !isUpdate.value;
};

// edit role
const handleEdit = async (roleId: number) => {
  const role = roleList.value.find((role: any) => role.roleId === roleId);
  editDrawerRef.value.handleOpen(role);
};
// delete role
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
    t('role.confirmDelete', { roleName: row.roleName }),
    t('role.notification'),
    {
      confirmButtonText: t('role.confirm'),
      cancelButtonText: t('role.cancel'),
      type: 'warning',
    }
  )
    .then(async () => {
      let res = await proxy.$api.deleteRole({ roleId: row.roleId });
      if (res) {
        ElNotification({
          title: t('role.notification'),
          message: res,
          type: 'success',
        });
        // delete successfully, reload role list
        getRoleList();
        console.log(t('role.deleteSuccess'));
      } else {
        ElNotification({
          title: t('role.notification'),
          message: res,
          type: 'error',
        });
      }
    })
    .catch(() => {
      console.log(t('role.cancelDelete'));
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
