<template>
  <div class="container">
    <a-card class="general-card" :title="$t('menu.list.searchTable')">
      <a-row>
        <a-col :flex="1">
          <a-form :model="formModel" :label-col-props="{ span: 8 }" :wrapper-col-props="{ span: 18 }" label-align="left">
            <a-row :gutter="16">
              <a-col :span="10">
                <a-form-item field="number" :label="$t('searchTable.form.number')">
                  <a-input v-model="formModel.number" :placeholder="$t('searchTable.form.number.placeholder')" />
                </a-form-item>
              </a-col>
              <a-col :span="10">
                <a-form-item field="name" :label="$t('searchTable.form.name')">
                  <a-input v-model="formModel.name" :placeholder="$t('searchTable.form.name.placeholder')" />
                </a-form-item>
              </a-col>
              <a-col :span="10">
                <a-form-item field="eventType" :label="$t('searchTable.form.eventType')">
                  <a-select v-model="formModel.eventType" :options="eventTypeOptions" :placeholder="$t('searchTable.form.selectDefault')" />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item field="status" :label="$t('searchTable.form.status')">
                  <a-select v-model="formModel.status" :options="statusOptions" :placeholder="$t('searchTable.form.selectDefault')" />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item field="createdTime" :label="$t('searchTable.form.createdTime')">
                  <a-range-picker v-model="formModel.createdTime" style="width: 100%" />
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </a-col>
        <a-divider style="height: 84px" direction="vertical" />
        <a-col :flex="'86px'" style="text-align: right">
          <a-space direction="vertical" :size="18">
            <a-button type="primary" @click="search">
              <template #icon>
                <icon-search />
              </template>
              {{ $t('searchTable.form.search') }}
            </a-button>
            <a-button @click="reset">
              <template #icon>
                <icon-refresh />
              </template>
              {{ $t('searchTable.form.reset') }}
            </a-button>
          </a-space>
        </a-col>
      </a-row>
      <a-divider style="margin-top: 0" />
      <a-row style="margin-bottom: 16px">
        <a-col :span="12">
          <a-space>
            <a-button type="primary">
              <template #icon>
                <icon-plus />
              </template>
              {{ $t('searchTable.operation.create') }}
            </a-button>
            <a-upload action="/">
              <template #upload-button>
                <a-button>
                  {{ $t('searchTable.operation.import') }}
                </a-button>
              </template>
            </a-upload>
          </a-space>
        </a-col>
        <a-col :span="12" style="display: flex; align-items: center; justify-content: end">
          <a-button>
            <template #icon>
              <icon-download />
            </template>
            {{ $t('searchTable.operation.download') }}
          </a-button>
          <a-tooltip :content="$t('searchTable.actions.refresh')">
            <div class="action-icon" @click="search"><icon-refresh size="18" /></div>
          </a-tooltip>
          <a-dropdown @select="handleSelectDensity">
            <a-tooltip :content="$t('searchTable.actions.density')">
              <div class="action-icon"><icon-line-height size="18" /></div>
            </a-tooltip>
            <template #content>
              <a-doption v-for="item in densityList" :key="item.value" :value="item.value" :class="{ active: item.value === size }">
                <span>{{ item.name }}</span>
              </a-doption>
            </template>
          </a-dropdown>
          <a-tooltip :content="$t('searchTable.actions.columnSetting')">
            <a-popover trigger="click" position="bl" @popupVisibleChange="popupVisibleChange">
              <div class="action-icon"><icon-settings size="18" /></div>
              <template #content>
                <div id="tableSetting">
                  <div v-for="(item, index) in showColumns" :key="item.dataIndex" class="setting">
                    <div style="margin-right: 4px; cursor: move">
                      <icon-drag-arrow />
                    </div>
                    <div>
                      <a-checkbox v-model="item.checked" @change="handleChange($event, item as TableColumnData, index)">
                      </a-checkbox>
                    </div>
                    <div class="title">
                      {{ item.title === '#' ? 'Index' : item.title }}
                    </div>
                  </div>
                </div>
              </template>
            </a-popover>
          </a-tooltip>
        </a-col>
      </a-row>
      <a-table row-key="id" :loading="loading" :pagination="pagination" :columns="(cloneColumns as TableColumnData[])"
        :data="renderData" :bordered="false" :size="size" @page-change="onPageChange">
        <template #index="{ rowIndex }">
          {{ rowIndex + 1 + (pagination.current - 1) * pagination.pageSize }}
        </template>
        <template #eventType="{ record }">
          <a-space>
            <a-avatar v-if="record.eventType === 'Judo'" :size="16" shape="square">
              <img alt="avatar" src="../../../assets/kungfu.png" />
            </a-avatar>
            <a-avatar v-else-if="record.eventType === 'Badminton'" :size="16" shape="square">
              <img alt="avatar" src="../../../assets/badminton.png" />
            </a-avatar>
            <a-avatar v-else-if="record.eventType === 'Table Tennis'" :size="16" shape="square">
              <img alt="avatar" src="../../../assets/tabletennis.png" />
            </a-avatar>
            <a-avatar v-else-if="record.eventType === 'Hockey'" :size="16" shape="square">
              <img alt="avatar" src="../../../assets/hockey.png" />
            </a-avatar>
            <a-avatar v-else-if="record.eventType === 'Cycling'" :size="16" shape="square">
              <img alt="avatar" src="../../../assets/air-bike.png" />
            </a-avatar>
            <a-avatar v-else-if="record.eventType === 'Snowsports'" :size="16" shape="square">
              <img alt="avatar" src="../../../assets/skating.png" />
            </a-avatar>
            {{ record.eventType }}
          </a-space>
        </template>
        <template #startDate="{ record }">
          {{ formatDate(record.startDate) }}
        </template>
        <template #endDate="{ record }">
          {{ formatDate(record.endDate) }}
        </template>
        <template #status="{ record }">
          <span v-if="record.status === 'Awaiting review'" class="circle"></span>
          <span v-else class="circle pass"></span>
          {{ record.status }}
        </template>
        <template #operations="{ record }">
          <a-button v-permission="['admin']" type="text" size="small" style="margin-right: 10px;" @click="viewDetails(record)">
            {{ $t('searchTable.columns.operations.view') }}
          </a-button>
          <a-button v-permission="['admin']" status="success" size="small" style="margin-right: 10px;" @click="approveEvent(record)">
            {{ $t('searchTable.columns.operations.approve') }}
          </a-button>
          <a-button v-permission="['admin']" status="danger" size="small" @click="rejectEvent(record)">
            {{ $t('searchTable.columns.operations.reject') }}
          </a-button>
        </template>
      </a-table>
    </a-card>

    <!-- Event Details Dialog -->
    <el-dialog v-model="dialogVisible" title="Event Details" width="800">
      <el-descriptions
        v-if="currentEvent"
        :title="currentEvent.title"
        direction="vertical"
        :column="2"
        border
      >
        <el-descriptions-item label="ID" :span="2">{{ currentEvent.id }}</el-descriptions-item>
        <el-descriptions-item label="Organizer">{{ userInfo?.username }}</el-descriptions-item>
        <el-descriptions-item label="Organizer Email">{{ userInfo?.email }}</el-descriptions-item>
        <el-descriptions-item label="Description" :span="2">{{ currentEvent.description }}</el-descriptions-item>
        <el-descriptions-item label="Location" :span="2">{{ currentEvent.location }}</el-descriptions-item>
        <el-descriptions-item label="Start Date">{{ formatDate(currentEvent.startDate) }}</el-descriptions-item>
        <el-descriptions-item label="End Date">{{ formatDate(currentEvent.endDate) }}</el-descriptions-item>
        <el-descriptions-item label="Status">
          <el-button
            :type="currentEvent.status === 'Passed' ? 'success' : currentEvent.status === 'Awaiting review' ? 'warning' : currentEvent.status === 'Rejected' ? 'danger' : ''"
            size="small"
            round
          >
            {{ currentEvent.status }}
          </el-button>
        </el-descriptions-item>
        <el-descriptions-item label="Points Awarded">{{ currentEvent.pointsAwarded }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="success" @click="approveEvent(currentEvent)">Approve</el-button>
          <el-button type="danger" @click="rejectEvent(currentEvent)">Reject</el-button>
          <el-button @click="dialogVisible = false">Close</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref, reactive, watch, nextTick, getCurrentInstance } from 'vue';
import { useI18n } from 'vue-i18n';
import useLoading from '../../../hooks/loading.ts';
import { EventType } from './list.ts';
import { Pagination } from '../../../types/global';
import type { SelectOptionData } from '@arco-design/web-vue/es/select/interface';
import type { TableColumnData } from '@arco-design/web-vue/es/table/interface';
import cloneDeep from 'lodash/cloneDeep';
import Sortable from 'sortablejs';
import { ElMessage, ElMessageBox } from 'element-plus';

type SizeProps = 'mini' | 'small' | 'medium' | 'large';
type Column = TableColumnData & { checked?: true };

const { proxy } = getCurrentInstance() as any;

const generateFormModel = () => {
  return {
    number: '',
    name: '',
    eventType: '',
    filterType: '',
    createdTime: [],
    status: '',
  };
};
const { loading, setLoading } = useLoading(true);
const { t } = useI18n();
const renderData = ref<EventType[]>([]);
const fullData = ref<EventType[]>([]);
const formModel = ref(generateFormModel());
const cloneColumns = ref<Column[]>([]);
const showColumns = ref<Column[]>([]);
const dialogVisible = ref(false);
const currentEvent = ref<EventType | null>(null);
const userInfo = ref<any>(); // Add this line to store userInfo

const size = ref<SizeProps>('medium');

// 控制分页显示的数据条数
const basePagination: Pagination = {
  current: 1,
  pageSize: 3,
};
const pagination = reactive({
  ...basePagination,
});

const densityList = computed(() => [
  {
    name: t('searchTable.size.mini'),
    value: 'mini',
  },
  {
    name: t('searchTable.size.small'),
    value: 'small',
  },
  {
    name: t('searchTable.size.medium'),
    value: 'medium',
  },
  {
    name: t('searchTable.size.large'),
    value: 'large',
  },
]);

const columns = computed<TableColumnData[]>(() => [
  {
    title: t('searchTable.columns.index'),
    dataIndex: 'id',
    slotName: 'index',
  },
  {
    title: t('searchTable.columns.name'),
    dataIndex: 'title',
  },
  {
    title: t('searchTable.columns.description'),
    dataIndex: 'description',
  },
  {
    title: t('searchTable.columns.location'),
    dataIndex: 'location',
  },
  {
    title: t('searchTable.columns.pointsAwarded'),
    dataIndex: 'pointsAwarded',
  },
  {
    title: t('searchTable.columns.startDate'),
    dataIndex: 'startDate',
    slotName: 'startDate',
  },
  {
    title: t('searchTable.columns.endDate'),
    dataIndex: 'endDate',
    slotName: 'endDate',
  },
  {
    title: t('searchTable.columns.status'),
    dataIndex: 'status',
    slotName: 'status',
  },
  {
    title: t('searchTable.columns.operations'),
    dataIndex: 'operations',
    slotName: 'operations',
  },
]);

const eventTypeOptions = computed<SelectOptionData[]>(() => [
  {
    label: t('searchTable.form.eventType.Judo'),
    value: 'Judo',
  },
  {
    label: t('searchTable.form.eventType.Badminton'),
    value: 'Badminton',
  },
  {
    label: t('searchTable.form.eventType.TableTennis'),
    value: 'Table Tennis',
  },
  {
    label: t('searchTable.form.eventType.Hockey'),
    value: 'Hockey',
  },
  {
    label: t('searchTable.form.eventType.Cycling'),
    value: 'Cycling',
  },
  {
    label: t('searchTable.form.eventType.Snowsports'),
    value: 'Snowsports',
  },
]);

const statusOptions = computed<SelectOptionData[]>(() => [
  {
    label: t('searchTable.form.status.awaitingReview'),
    value: 'Awaiting review',
  },
  {
    label: t('searchTable.form.status.passed'),
    value: 'Passed',
  },
  {
    label: t('searchTable.form.status.rejected'),
    value: 'Rejected',
  },
]);

const fetchData = async () => {
  setLoading(true);
  try {
    let res = await proxy.$api.getEventList();
    console.log(res);
    // let { list } = res;
    fullData.value = res;
    pagination.total = res.length;
    // 前端分页
    updateRenderData(); // Update render data after fetching 
  } catch (err) {
    // you can report use errorHandler or other
  } finally {
    setLoading(false);
  }
};

// 前端分页
const updateRenderData = () => {
  const start = (pagination.current - 1) * pagination.pageSize;
  const end = start + pagination.pageSize;
  renderData.value = fullData.value.slice(start, end);
};

const search = () => {
  // 将表单数据转化为小写以便更好地匹配
  const searchData = {
    number: formModel.value.number?.trim().toLowerCase(),
    name: formModel.value.name?.trim().toLowerCase(),
    eventType: formModel.value.eventType,
    status: formModel.value.status,
    createdTime: formModel.value.createdTime,
  };

  // 对数据进行筛选
  const filteredData = fullData.value.filter((item) => {
    const matchesNumber = !searchData.number || item.id.toString().includes(searchData.number);
    const matchesName = !searchData.name || item.title.toLowerCase().includes(searchData.name);
    const matchesEventType = !searchData.eventType || item.eventType === searchData.eventType;
    const matchesStatus = !searchData.status || item.status === searchData.status;
    const matchesCreatedTime =
      !searchData.createdTime.length ||
      (new Date(item.startDate) >= new Date(searchData.createdTime[0]) &&
        new Date(item.endDate) <= new Date(searchData.createdTime[1]));

    return matchesNumber && matchesName && matchesEventType && matchesStatus && matchesCreatedTime;
  });

  // 更新渲染数据
  renderData.value = filteredData.slice(0, pagination.pageSize);
  pagination.total = filteredData.length;

  // 如果需要，重置分页页码
  pagination.current = 1;
};


const onPageChange = (current: number) => {
  pagination.current = current;
  updateRenderData();
};

fetchData();

const reset = () => {
  formModel.value = generateFormModel();
  search();
};

const handleSelectDensity = (
  val: string | number | Record<string, any> | undefined,
  e: Event
) => {
  size.value = val as SizeProps;
};

const handleChange = (
  checked: boolean | (string | boolean | number)[],
  column: Column,
  index: number
) => {
  if (!checked) {
    cloneColumns.value = showColumns.value.filter(
      (item) => item.dataIndex !== column.dataIndex
    );
  } else {
    cloneColumns.value.splice(index, 0, column);
  }
};

const exchangeArray = <T extends Array<any>>(
  array: T,
  beforeIdx: number,
  newIdx: number,
  isDeep = false
): T => {
  const newArray = isDeep ? cloneDeep(array) : array;
  if (beforeIdx > -1 && newIdx > -1) {
    newArray.splice(
      beforeIdx,
      1,
      newArray.splice(newIdx, 1, newArray[beforeIdx]).pop()
    );
  }
  return newArray;
};

const popupVisibleChange = (val: boolean) => {
  if (val) {
    nextTick(() => {
      const el = document.getElementById('tableSetting') as HTMLElement;
      const sortable = new Sortable(el, {
        onEnd(e: any) {
          const { oldIndex, newIndex } = e;
          exchangeArray(cloneColumns.value, oldIndex, newIndex);
          exchangeArray(showColumns.value, oldIndex, newIndex);
        },
      });
    });
  }
};

watch(
  () => columns.value,
  (val) => {
    cloneColumns.value = cloneDeep(val);
    cloneColumns.value.forEach((item, index) => {
      item.checked = true;
    });
    showColumns.value = cloneDeep(cloneColumns.value);
  },
  { deep: true, immediate: true }
);

const formatDate = (dateStr: string | number | Date) => {
  const options: any = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Intl.DateTimeFormat('en-GB', options).format(new Date(dateStr));
};

const viewDetails = async (record: EventType) => {
  currentEvent.value = record;
  dialogVisible.value = true;
  if (record.organizerId) {
    await fetchUserInfo(record.organizerId);
  }
};

const fetchUserInfo = async (organizerId: number) => {
  try {
    const res = await proxy.$api.getUserInfoByOrganizerId({ organizerId });
    userInfo.value = res;
  } catch (error) {
    userInfo.value = 'Error fetching user info';
    console.error(error);
  }
};

// Approve event
const approveEvent = async (event: EventType) => {
  const { id, title } = event;
  try {
    await ElMessageBox.confirm(
      `Are you sure you want to approve the event "${title}"?`,
      'Confirm Approval',
      {
        confirmButtonText: 'Approve',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
    );
    // 如果用户点击确认，则执行以下代码
    let res = await proxy.$api.approveEvent({id});
    if (res) {
      ElMessage.success(`Event "${title}" approved successfully!`);
      fetchData(); // 更新数据
      dialogVisible.value = false; // 关闭对话框
    } else {
      ElMessage.error(`Failed to approve event "${title}".`);
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(`Error approving event "${title}": ${error}`);
    }
  }
};


// Reject event
const rejectEvent = async (event: EventType) => {
  const { id, title } = event;
  try {
    await ElMessageBox.confirm(
      `Are you sure you want to reject the event "${title}"?`,
      'Confirm Rejection',
      {
        confirmButtonText: 'Reject',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
    );

    // 如果用户点击确认，则执行以下代码
    let res = await proxy.$api.rejectEvent({id});
    if (res) {
      ElMessage.success(`Event "${title}" rejected successfully!`);
      fetchData(); // 更新数据
      dialogVisible.value = false; // 关闭对话框
    } else {
      ElMessage.error(`Failed to reject event "${title}".`);
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(`Error rejecting event "${title}": ${error}`);
    }
  }
};

</script>

<script lang="ts">
export default {
  name: 'SearchTable',
};
</script>

<style scoped lang="less">
.container {
  padding: 0 20px 20px 20px;
}

:deep(.arco-table-th) {
  &:last-child {
    .arco-table-th-item-title {
      margin-left: 16px;
    }
  }
}

.action-icon {
  margin-left: 12px;
  cursor: pointer;
}

.active {
  color: #0960bd;
  background-color: #e3f4fc;
}

.setting {
  display: flex;
  align-items: center;
  width: 200px;

  .title {
    margin-left: 12px;
    cursor: pointer;
  }
}

.dialog-footer {
  text-align: right;
}
</style>
