<template>
  <div class="reward-store">
    <div class="addButton">
      <el-button type="primary" size="small" @click="editDrawerRef.handleOpen()">{{ $t('rewardStore.add') }}</el-button>
    </div>
    <!-- 奖励商店表格 -->
    <el-table :data="showItems" stripe style="width: 100%" :key="isUpdate.toString()">
      <el-table-column prop="id" :label="$t('rewardStore.itemId')" width="100" />
      <el-table-column prop="itemName" :label="$t('rewardStore.itemName')" width="250" />
      <el-table-column prop="pointsRequired" :label="$t('rewardStore.pointsRequired')" width="150" />
      <el-table-column :label="$t('rewardStore.edit')">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row.id)">{{ $t('rewardStore.edit') }}</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">{{ $t('rewardStore.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
        style="margin-top: 5px"
        background
        layout="prev, pager, next"
        :total="itemList.length"
        @current-change="handleCurrentChange"
    />
    <EditRewardStore ref="editDrawerRef" @update-item-list="getItemList"></EditRewardStore>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed, getCurrentInstance } from 'vue';
import EditRewardStore from '../../../components/user/EditRewardStore.vue';
import { ElMessageBox, ElNotification } from 'element-plus';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const { proxy }: any = getCurrentInstance();

// 商品列表
let itemList = ref<any[]>([]);
// 当前页码
let pageIndex = ref(1);
// 列表更新标志
let isUpdate = ref(false);
// 显示的商品
let showItems = computed(() => {
  isUpdate.value = !isUpdate.value; // 更新商品列表
  return itemList.value.slice((pageIndex.value - 1) * 10, pageIndex.value * 10);
});
// 页码变更事件
const handleCurrentChange = (val: number) => {
  pageIndex.value = val;
};
// 加载商品列表
const getItemList = async () => {
  console.log(t('rewardStore.loadItemList'));
  // 从后端获取商品列表
  let res = await proxy.$api.getItems();
  // 对商品列表根据 id 进行排序
  res.sort((a: { id: number; }, b: { id: number; }) => a.id - b.id);
  // 将排序后的列表赋值给 itemList
  itemList.value = res;
  // 触发更新
  isUpdate.value = !isUpdate.value;
};

// 编辑商品
const handleEdit = async (itemId: number) => {
  const item = itemList.value.find((item: any) => item.id === itemId);
  editDrawerRef.value.handleOpen(item);
};
// 删除商品
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
      t('rewardStore.confirmDelete', { itemName: row.itemName }),
      t('rewardStore.notification'),
      {
        confirmButtonText: t('rewardStore.confirm'),
        cancelButtonText: t('rewardStore.cancel'),
        type: 'warning',
      }
  )
      .then(async () => {
        let res = await proxy.$api.deleteRewardStoreItem({ id: row.id });
        if (res) {
          ElNotification({
            title: t('rewardStore.notification'),
            message: res,
            type: 'success',
          });
          // 删除成功后重新加载商品列表
          getItemList();
          console.log(t('rewardStore.deleteSuccess'));
        } else {
          ElNotification({
            title: t('rewardStore.notification'),
            message: res,
            type: 'error',
          });
        }
      })
      .catch(() => {
        console.log(t('rewardStore.cancelDelete'));
      });
};
// 抽屉引用
// 定义 EditRewardStoreRef，通过 editDrawerRef 可以获取到组件暴露的实例对象
const editDrawerRef = ref();
onMounted(() => {
  getItemList();
});
</script>

<style lang="scss">
.addButton {
  margin-bottom: 20px;
}
</style>
