<template>
  <div class="home">
    <div class="left">
      <div class="title">
        <h2>WSA Volunteer Hub</h2>
      </div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#142334"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#fff"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><UserFilled /></el-icon>
            <span>Account</span>
          </template>
          <el-menu-item index="1-1">Role</el-menu-item>
          <el-menu-item index="1-2">User</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon><Tools /></el-icon>
            <span>System</span>
          </template>
          <el-menu-item index="2-1">Menu</el-menu-item>
          <el-menu-item index="2-2">Dictionary</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon><Briefcase /></el-icon>
            <span>Menu</span>
          </template>
          <el-menu-item index="3-1">Sub Menu1</el-menu-item>
          <el-menu-item index="3-2">Sub Menu2</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>
    <div class="right">
      <div class="top">2</div>
      <div class="content">3</div>
    </div>
  </div>
</template>
<script setup lang="ts">
import {
  UserFilled,
  HomeFilled,
  Briefcase,
  Tools,
} from "@element-plus/icons-vue";
</script>
<style lang="scss">
.home {
  width: 100vw; // 100% of the viewport width
  height: 100vh;
  display: flex;
  .left {
    width: 200px;
    background-color: #142334;
    color: white;
    .el-menu {
      // delete the right border
      border-right: none;
    }
    h2 {
      font-size: 16px;
      text-align: center;
      height: 60px;
      line-height: 60px;
    }
  }
  .right {
    flex: 1;
    display: flex;
    flex-direction: column;
    .top {
      height: 60px;
      background-color: #142334;
      color: white;
    }
    .content {
      flex: 1;
      background-color: white;
    }
  }
}
</style>
