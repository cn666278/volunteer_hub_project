<template>
    <el-row type="flex" justify="center" style="margin-top: 20px">
      <el-col :span="18">
        <!-- Title Row -->
        <el-row style="margin-bottom: 10px;">
          <el-col :span="24">
            <h2 style="text-align: left; margin: 0;">{{ $t('dashboardOverview.title') }}</h2>
          </el-col>
        </el-row>
        
        <!-- Description Row -->
        <el-row style="margin-bottom: 20px;">
          <el-col :span="24">
            <p style="text-align: left; margin: 0; color: #666;">
              {{ $t('dashboardOverview.description') }}
            </p>
          </el-col>
        </el-row>
        
        <!-- Data Count Cards -->
        <el-row :gutter="20" class="num">
          <el-col :span="8" v-for="item in countData" :key="item.name" style="margin-bottom: 20px;">
            <el-card shadow="hover" class="data-card">
              <div class="card-content">
                <component
                  class="icons"
                  :is="item.icon"
                  :style="{ background: item.color }"
                ></component>
                <div class="details">
                  <p class="num">{{ item.value }}</p>
                  <p class="text">{{ $t(`home.${item.name}`) }}</p>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </template>
  
  <script setup>
  import { ref, getCurrentInstance } from "vue";
  
  const { proxy } = getCurrentInstance();
  let countData = ref([]);
  
  const getCountData = async () => {
    try {
      let res = await proxy.$api.getCountData();
      countData.value = res;
    } catch (error) {
      console.error("Error fetching count data:", error);
    }
  };
  
  getCountData();
  </script>
  
  <style scoped>
  .num {
    display: flex;
    flex-wrap: wrap;
  }
  
  .data-card {
    display: flex;
    align-items: center;
    padding: 10px;
    height: 100px;
  }
  
  .card-content {
    display: flex;
    align-items: center;
    width: 100%;
  }
  
  .icons {
    width: 50px;
    height: 50px;
    font-size: 24px;
    text-align: center;
    line-height: 50px;
    color: #fff;
    margin-right: 20px;
    flex-shrink: 0;
  }
  
  .details {
    display: flex;
    flex-direction: column;
    justify-content: center;
  
    .num {
      font-size: 28px;
      font-weight: bold;
    }
  
    .text {
      font-size: 14px;
      color: #666;
      margin-top: 5px;
    }
  }
  
  .el-col {
    margin-bottom: 20px; /* Adds spacing between cards */
  }
  </style>
  