<template>
  <el-row type="flex" justify="center" style="margin-top: 20px">
    <el-col :span="18">
      <!-- Title Row -->
      <el-row style="margin-bottom: 10px;">
        <el-col :span="24">
          <h2 style="text-align: left; margin: 0;">{{ $t('volunteerRegistrationOverview.title') }}</h2>
        </el-col>
      </el-row>
      
      <!-- Description Row -->
      <el-row style="margin-bottom: 20px;">
        <el-col :span="24">
          <p style="text-align: left; margin: 0; color: #666;">
            {{ $t('volunteerRegistrationOverview.description') }}
          </p>
        </el-col>
      </el-row>
      
      <!-- Chart Card -->
      <el-card shadow="hover" style="height: 400px;">
        <div ref="echart" style="height: 350px;"></div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, ref, getCurrentInstance, reactive } from "vue";
import * as echarts from "echarts";

const { proxy } = getCurrentInstance();
let eventData = reactive({
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
  color: ["#2ec7c9", "#b6a2de", "#5ab1ef", "#ffb980", "#d87a80", "#8d98b3"],
  series: [],
});

const getEchartData = async () => {
  let result = await proxy.$api.getEchartData();
  let res = result.eventData;

  let sortedData = res.date.map((date, index) => {
    return {
      date: date,
      data: res.data[index],
    };
  });

  sortedData.sort((a, b) => new Date(a.date) - new Date(b.date));
  res.date = sortedData.map((item) => item.date);
  res.data = sortedData.map((item) => item.data);

  eventData.xData = res.date;
  const series = [];
  const keySet = new Set();
  res.data.forEach((item) => {
    Object.keys(item).forEach((key) => keySet.add(key));
  });

  keySet.forEach((key) => {
    series.push({
      name: key,
      data: res.data.map((item) => item[key] || 0),
      type: "line",
    });
  });

  eventData.series = series;
  xOptions.xAxis.data = eventData.xData;
  xOptions.series = eventData.series;

  let hEcharts = echarts.init(proxy.$refs["echart"]);
  hEcharts.setOption(xOptions);
};

onMounted(() => {
  getEchartData();
});
</script>

<style scoped>
/* Styling adjustments for better appearance */
.el-card {
  padding: 20px;
}

h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}

p {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
}
</style>
