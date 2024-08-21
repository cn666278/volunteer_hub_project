<template>
    <el-row type="flex" justify="center" style="margin-top: 20px">
      <el-col :span="18">
        <!-- Title Row -->
        <el-row style="margin-bottom: 10px;">
          <el-col :span="24">
            <h2 style="text-align: left; margin: 0;">{{ $t('organizerDataOverview.title') }}</h2>
          </el-col>
        </el-row>
        
        <!-- Description Row -->
        <el-row style="margin-bottom: 20px;">
          <el-col :span="24">
            <p style="text-align: left; margin: 0; color: #666;">
              {{ $t('organizerDataOverview.description') }}
            </p>
          </el-col>
        </el-row>
        
        <!-- Pie Chart Card -->
        <el-card shadow="hover" style="height: 400px;">
          <div ref="organizerData" style="height: 350px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </template>
  
  <script setup>
  import { onMounted, getCurrentInstance, reactive } from "vue";
  import * as echarts from "echarts";
  
  const { proxy } = getCurrentInstance();
  
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
  
  let organizerData = reactive({
    series: [],
  });
  
  const getEchartData = async () => {
    let result = await proxy.$api.getEchartData();
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
  