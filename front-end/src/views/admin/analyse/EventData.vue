<template>
    <el-row type="flex" justify="center" style="margin-top: 20px">
      <el-col :span="14">
        <!-- Title Row -->
        <el-row style="margin-bottom: 5px;">
          <el-col :span="24">
            <h2 style="text-align: left; margin: 0;">{{ $t('eventDataOverview.title') }}</h2>
          </el-col>
        </el-row>
        
        <!-- Description Row -->
        <el-row style="margin-bottom: 20px;">
          <el-col :span="24">
            <p style="text-align: left; margin: 0; color: #666;">
              {{ $t('eventDataOverview.description') }}
            </p>
          </el-col>
        </el-row>
        
        <!-- Event Data Table -->
        <el-card shadow="hover" style="margin-top: 10px; height: 100%;">
          <el-table :data="tableData" style="width: 100%;" height="400px">
            <el-table-column
              v-for="(val, key) in tableLable"
              :key="key"
              :prop="key"
              :label="$t(`home.${val}`)"
            ></el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </template>
  
  <script setup>
  import { ref, getCurrentInstance } from "vue";
  
  const { proxy } = getCurrentInstance();
  let tableData = ref([]);
  const tableLable = {
    name: 'event',
    today: 'todaysActiveUsers',
    monthly: 'monthlyActiveUsers',
    total: 'totalActiveUsers',
  };
  
  const getTableList = async () => {
    try {
      let res = await proxy.$api.getEventStatus();
      tableData.value = res;
    } catch (error) {
      console.error("Error fetching table data:", error);
    }
  };
  
  getTableList();
  </script>
  
  <style scoped>
  .el-row {
    margin-top: 20px;
  }
  
  .el-card {
    margin-top: 10px;
    height: 100%;
  }
  
  .el-table th, .el-table td {
    text-align: center;
  }
  
  .el-table {
    font-size: 14px;
  }
  </style>
  