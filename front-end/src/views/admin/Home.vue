<template>
  <el-row class="home" :gutter="20">
    <el-col :span="10" style="margin-top: 20px">
      <el-card shadow="hover">
        <div class="user">
          <img src="../../assets/user.png" alt="user" />
          <div class="user-info">
            <el-descriptions
              class="margin-top"
              :title="$t('home.userInformation')"
              :column="1"
              size="default"
              border
            >
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon>
                      <tickets />
                    </el-icon>
                    {{ $t('home.username') }}
                  </div>
                </template>
                {{ userStore.user.username }}
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon>
                      <user />
                    </el-icon>
                    {{ $t('home.role') }}
                  </div>
                </template>
                <el-tag size="small">{{ userStore.user.role.roleName }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon>
                      <office-building />
                    </el-icon>
                    {{ $t('home.lastLoginTime') }}
                  </div>
                </template>
                2024-03-24 12:00:00
              </el-descriptions-item>
              <el-descriptions-item>
                <template #label>
                  <div class="cell-item">
                    <el-icon>
                      <iphone />
                    </el-icon>
                    {{ $t('home.lastLoginLocation') }}
                  </div>
                </template>
                Cardiff
              </el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </el-card>
      <el-card shadow="hover" style="margin-top: 20px" height="450px">
        <el-table :data="tableData">
          <el-table-column
            v-for="(val, key) in tableLable"
            :key="key"
            :prop="key"
            :label="$t(`home.${val}`)"
          ></el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="14" style="margin-top: 20px">
      <div class="num">
        <el-card
          :body-style="{ display: 'flex', padding: 0 }"
          v-for="item in countData"
          :key="item.name"
        >
          <component
            class="icons"
            :is="item.icon"
            :style="{ background: item.color }"
          ></component>
          <div class="details">
            <p class="num">{{ item.value }}</p>
            <p class="text">{{ $t(`home.${item.name}`) }}</p>
          </div>
        </el-card>
      </div>
      <el-card style="height: 300px">
        <p>{{ $t('home.registerUser') }}</p>
        <div ref="echart" style="height: 280px"></div>
      </el-card>
      <div class="graph">
        <el-card style="height: 300px">
          <p>{{ $t('home.activeUsers') }}</p>
          <div ref="userEchart" style="height: 240px"></div>
        </el-card>
        <el-card style="height: 300px">
          <p>{{ $t('home.events') }}</p>
          <div ref="videoEchart" style="height: 240px"></div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, ref, getCurrentInstance, reactive } from "vue";
import useUser from "../../store/user.ts";
import * as echarts from "echarts";

// proxy是vue3.0提供的一个全局对象，可以通过getCurrentInstance()获取到当前实例，
const { proxy } = getCurrentInstance();

let userStore = useUser();

// tableData使用let定义，因为后续会对其进行赋值, tableData is defined using let because it will be assigned later
let tableData = ref([]); // 双向绑定，使用ref包裹数组，实现响应式数据, Two-way binding, use ref to wrap the array to achieve responsive data
let countData = ref({});
// 今日购买，本月购买，总购买 Today's purchases, Monthly purchases, Total purchases
const tableLable = {
  name: 'event',
  todayPurchase: 'todaysActiveUsers',
  monthlyPurchase: 'monthlyActiveUsers',
  totalPurchase: 'totalActiveUsers',
};

// 获取表格数据
const getTableList = async () => {
  let res = await proxy.$api.getTableData();
  tableData.value = res;
};
// 获取首页count统计数据
const getCountData = async () => {
  let res = await proxy.$api.getCountData();
  countData.value = res;
};
// 关于echarts的渲染部分，可以参考echarts官网的文档
let xOptions = reactive({
  // 图例文字颜色
  textStyle: {
    color: "#333",
  },
  grid: {
    left: "20%",
  },
  // 提示框
  tooltip: {
    trigger: "axis",
  },
  xAxis: {
    type: "category", // 类目轴
    data: [],
    axisLine: {
      lineStyle: {
        color: "#17b3a3",
      },
    },
    axisLabel: {
      interval: 0,
      color: "#333",
    },
  },
  yAxis: [
    {
      type: "value",
      axisLine: {
        lineStyle: {
          color: "#17b3a3",
        },
      },
    },
  ],
  color: ["#2ec7c9", "#b6a2de", "#5ab1ef", "#ffb980", "#d87a80", "#8d98b3"],
  series: [],
});
let pieOptions = reactive({
  tooltip: {
    trigger: "item",
  },
  color: [
    "#0f78f4",
    "#dd536b",
    "#9462e5",
    "#a6a6a6",
    "#e1bb22",
    "#39c362",
    "#3ed1cf",
  ],
  series: [],
});
// order data, user data, video data
let orderData = reactive({
  xData: [],
  series: [],
});
let userData = reactive({
  xData: [],
  series: [],
});
let videoData = reactive({
  series: [],
});

// get Echart Data
const getEchartData = async () => {
  let result = await proxy.$api.getEchartData();
  let res = result.orderData;
  let userRes = result.userData;
  let videoRes = result.videoData;
  orderData.xData = res.date;
  const keyArray = Object.keys(res.data[0]);
  const series = [];
  keyArray.forEach((key) => {
    series.push({
      name: key,
      data: res.data.map((item) => item[key]),
      type: "line",
    });
  });
  orderData.series = series;
  xOptions.xAxis.data = orderData.xData;
  xOptions.series = orderData.series;
  // 渲染echarts: orderData
  let hEcharts = echarts.init(proxy.$refs["echart"]); // 获取dom节点 ref="echart"
  hEcharts.setOption(xOptions);

  // 渲染柱状图: userData
  userData.xData = userRes.map((item) => item.date);
  userData.series = [
    {
      name: "New users",
      data: userRes.map((item) => item.new),
      type: "bar",
    },
    {
      name: "Active users",
      data: userRes.map((item) => item.active),
      type: "bar",
    },
  ];
  xOptions.xAxis.data = userData.xData;
  xOptions.series = userData.series;
  let userEcharts = echarts.init(proxy.$refs["userEchart"]); // 获取dom节点 ref="userEchart"
  userEcharts.setOption(xOptions);

  // 渲染饼图: videoData
  videoData.series = [
    {
      data: videoRes,
      type: "pie",
    },
  ];
  pieOptions.series = videoData.series;
  let videoEcharts = echarts.init(proxy.$refs["videoEchart"]); // 获取dom节点 ref="videoEchart"
  videoEcharts.setOption(pieOptions);
};

onMounted(() => {
  getTableList();
  getCountData();
  getEchartData();
});
</script>

<style lang="scss" scoped>
.home {
  .user {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 1px solid #ccc;
    margin-bottom: 10px;
    img {
      width: 70px;
      height: 70px;
      border-radius: 50%;
      margin-right: 20px;
    }
  }
  .num {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .el-card {
      width: 32%;
      margin-bottom: 20px;
    }
    .icons {
      width: 80px;
      height: 80px;
      font-size: 30px;
      text-align: center;
      line-height: 80px;
      color: #fff;
    }
    .details {
      margin-left: 8px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      .num {
        font-size: 20px;
        margin-bottom: 10px;
      }
      .text {
        font-size: 13px;
        // text-align: center;
        color: #999;
      }
    }
  }
  .graph {
    margin-top: 20px;
    display: flex;
    justify-content: space-between;
    .el-card {
      width: 48%;
    }
  }
}

.el-table .cell {
  padding: 0 3px;
}
</style>
