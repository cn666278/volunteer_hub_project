<template>
    <el-row type="flex" justify="center" style="margin-top: 20px">
      <el-col :span="18">
        <!-- Title Row -->
        <el-row style="margin-bottom: 10px;">
          <el-col :span="24">
            <h2 style="text-align: left; margin: 0;">{{ $t('activeUsersOverview.title') }}</h2>
          </el-col>
        </el-row>
        
        <!-- Description Row -->
        <el-row style="margin-bottom: 20px;">
          <el-col :span="24">
            <p style="text-align: left; margin: 0; color: #666;">
              {{ $t('activeUsersOverview.description') }}
            </p>
          </el-col>
        </el-row>
        
        <!-- Chart Card -->
        <el-card shadow="hover" style="height: 400px;">
          <div ref="userEchart" style="height: 350px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </template>
  
  <script setup>
  import { onMounted, ref, getCurrentInstance, reactive } from "vue";
  import * as echarts from "echarts";
  
  const { proxy } = getCurrentInstance();
  
  let userData = reactive({
    xData: [],
    series: [],
  });
  
  let xOptions = reactive({
    textStyle: {
      color: "#333",
    },
    grid: {
      left: "10%",
      right: "10%",
      bottom: "15%",
    },
    tooltip: {
      trigger: "axis",
    },
    xAxis: {
      type: "category",
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
    color: ["#2ec7c9", "#b6a2de", "#5ab1ef"],
    series: [],
  });
  
  const getEchartData = async () => {
    let result = await proxy.$api.getEchartData();
    let userRes = result.userData;
  
    userData.xData = userRes.map((item) => item.date);
    userData.series = [
      {
        name: "New users",
        data: userRes.map((item) => item.newUsers || 0),
        type: "bar",
      },
      {
        name: "Active users",
        data: userRes.map((item) => item.activeUsers || 0),
        type: "bar",
      },
    ];
  
    xOptions.xAxis.data = userData.xData;
    xOptions.series = userData.series;
  
    let userEcharts = echarts.init(proxy.$refs["userEchart"]);
    userEcharts.setOption(xOptions);
  };
  
  onMounted(() => {
    getEchartData();
  });
  </script>
  
  <style scoped>
  .el-card {
    padding: 20px;
  }
  
  h2 {
    font-size: 24px;
    color: #333;
  }
  
  p {
    font-size: 14px;
    color: #666;
  }
  </style>
  