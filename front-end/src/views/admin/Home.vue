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
                {{ formattedLastLoginTime }}
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
      <el-card shadow="hover" style="margin-top: 20px; height: 490px;">
        <p style="margin-bottom: 5px; margin-left: 10px">{{ $t('home.event') }}</p>
        <el-table :data="tableData" style="width: 100%;" height="450px">
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
      <el-card style="height: 230px">
        <p>{{ $t('home.registerVolunteer') }}</p>
        <div ref="echart" style="height: 250px; margin-top: -30px;"></div>
      </el-card>
      <div class="graph">
        <el-card style="height: 300px">
          <p>{{ $t('home.activeUsers') }}</p>
          <div ref="userEchart" style="height: 240px"></div>
        </el-card>
        <el-card style="height: 300px">
          <p>{{ $t('home.organizerData.title') }}</p>
          <div ref="organizerData" style="height: 190px; margin-top: 10px;"></div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>


<script setup>
import { onMounted, ref, getCurrentInstance, reactive, computed } from "vue";
import useUser from "../../store/user.ts";
import * as echarts from "echarts";

// proxy is a global object provided by Vue 3.0, which can be obtained using getCurrentInstance()
const { proxy } = getCurrentInstance();

let userStore = useUser();

// tableData is defined using let because it will be assigned later
let tableData = ref([]); // Two-way binding, use ref to wrap the array to achieve responsive data
let countData = ref({});

// Format the last login time
const formattedLastLoginTime = computed(() => {
  const date = new Date(userStore.user.lastLoginTime);
  const options = {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
  };
  return date.toLocaleDateString('en-GB', options);
});

// Table header for event data
const tableLable = {
  name: 'event',
  today: 'todaysActiveUsers',
  monthly: 'monthlyActiveUsers',
  total: 'totalActiveUsers',
};

// Get event table data
const getTableList = async () => {
  try {
    let res = await proxy.$api.getEventStatus();
    tableData.value = res;
  } catch (error) {
    console.error("Error fetching table data:", error);
  }
};
// Get count data for home page: event / user / register volunteer
const getCountData = async () => {
  try {
    let res = await proxy.$api.getCountData();
    countData.value = res;
  } catch (error) {
    console.error("Error fetching count data:", error);
  }
};
// Rendering part of echarts, you can refer to the official documentation of echarts
let xOptions = reactive({
  // Legend text color
  textStyle: {
    color: "#333",
  },
  grid: {
    left: "20%",
  },
  // Tooltip
  tooltip: {
    trigger: "axis",
  },
  xAxis: {
    type: "category", // Category axis
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
// Order data, user data, organizer data
let eventData = reactive({
  xData: [],
  series: [],
});

let userData = reactive({
  xData: [],
  series: [],
});

let organizerData = reactive({
  series: [],
});

// Get echarts data
const getEchartData = async () => {
  let result = await proxy.$api.getEchartData();
  let res = result.eventData;
  console.log(res);

  // Sort the date and corresponding data (ascending order by date)
  let sortedData = res.date.map((date, index) => {
    return {
      date: date,
      data: res.data[index]
    };
  });

  sortedData.sort((a, b) => new Date(a.date) - new Date(b.date));

  // Extract sorted date and data
  res.date = sortedData.map(item => item.date);
  res.data = sortedData.map(item => item.data);

  console.log(res);

  // Process data to ensure no empty objects
  eventData.xData = res.date;
  const series = [];
  
  // Get keys of all non-empty data
  const keySet = new Set();
  res.data.forEach((item) => {
    Object.keys(item).forEach((key) => keySet.add(key));
  });
  
  // Build series data
  keySet.forEach((key) => {
    series.push({
      name: key,
      data: res.data.map((item) => item[key] || 0), // Replace empty values with 0
      type: "line",
    });
  });

  eventData.series = series;
  xOptions.xAxis.data = eventData.xData;
  xOptions.series = eventData.series;

  let hEcharts = echarts.init(proxy.$refs["echart"]);
  hEcharts.setOption(xOptions);

  // Render bar chart: userData
  let userRes = result.userData;
  userData.xData = userRes.map((item) => item.date);
  userData.series = [
    {
      name: "New users",
      data: userRes.map((item) => item.newUsers || 0), // Replace empty values with 0
      type: "bar",
    },
    {
      name: "Active users",
      data: userRes.map((item) => item.activeUsers || 0), // Replace empty values with 0
      type: "bar",
    },
  ];

  xOptions.xAxis.data = userData.xData;
  xOptions.series = userData.series;

  let userEcharts = echarts.init(proxy.$refs["userEchart"]);
  userEcharts.setOption(xOptions);

  // Render pie chart: organizerData
  let organizerRes = result.organizerData;
  organizerData.series = [
    {
      data: organizerRes,
      type: "pie",
    },
  ];
  
  pieOptions.series = organizerData.series;

  let organizerEcharts = echarts.init(proxy.$refs["organizerData"]);
  organizerEcharts.setOption(pieOptions);
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
      margin-bottom: 14px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      height: 100%; /* Set card height to automatically fill parent container */
    }

    .icons {
      width: 50px;
      height: 50px;
      font-size: 20px;
      text-align: center;
      line-height: 50px;
      color: #fff;
      flex-shrink: 0; /* Fixed size */
    }

    .details {
      margin-left: 8px;
      display: flex;
      flex-direction: column;
      justify-content: space-around; /* Distribute elements evenly within the parent container */
      flex-grow: 1; /* Make details take up remaining space */

      .num {
        font-size: 16px;
        // margin-bottom: 10px;
      }

      .text {
        font-size: 13px;
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
</style>
