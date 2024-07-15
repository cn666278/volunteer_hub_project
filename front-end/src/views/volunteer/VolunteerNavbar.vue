<template>
  <div>
    <div class="navbar-top">
      <div class="contact-info">
        <el-icon class="icon">
          <i class="el-icon-phone"></i>
        </el-icon>
        <span>123-456-7890</span>
      </div>
      <div class="contact-info right">
        <el-icon class="icon">
          <i class="el-icon-message"></i>
        </el-icon>
        <span>contact@example.com</span>
      </div>
    </div>
    <el-menu
        router
        :default-active="activeIndex"
        class="el-menu-demo navbar"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
    >
      <div class="weblogo">
        <img src="../../assets/logo.png" class="logo">
      </div>
      <el-menu-item index="/volunteer">Home</el-menu-item>
      <el-menu-item index="/volunteer/events">Events</el-menu-item>
      <el-menu-item index="3">News</el-menu-item>
      <el-menu-item index="4">Search</el-menu-item>
      <el-menu-item index="5">Login</el-menu-item>
      <el-menu-item index="/volunteer/personal">
        <el-tooltip content="View Notifications" placement="bottom">
          <el-icon class="icon">
            <i class="el-icon-bell"></i>
          </el-icon>
        </el-tooltip>
        <el-avatar :src="userStore.user.photo" class="menu-avatar"></el-avatar>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import useUser from "../../store/user";
// user store
const userStore = useUser();

const activeIndex = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<style scoped>
.navbar-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
  background-color: #FFFFFF;
  padding: 10px 10px;
  box-sizing: border-box;
  height: 50px;
}

.navbar-container, .el-menu-demo {
  display: flex;
  justify-content: flex-end;
  background-color: #FFFFFF;
  overflow-x: auto;
  position: fixed;
  top: 50px;
  padding-right: 50px;
  width: 100%;
  z-index: 1000;
}


.el-menu-item {
  padding: 10px 15px;
}

.flex-grow {
  flex-grow: 1;
}

.menu-avatar {
  margin-left: 10px;
  vertical-align: middle;
}

 .contact-info {
   max-width: 150px;
 }

.contact-info span {
  font-size: 14px;
}


 .navbar-top {
   margin-bottom: 0; /* 减少底部外边距 */
 }

.weblogo {
  padding-left: 20px; /* 根据需要调整，确保图片不会贴近边界 */
  padding-right:900px;
  flex: none; /* 防止 logo 拉伸或压缩 */
  display: flex;
  justify-content: center; /* 居中图片 */
  z-index: 1000;
}

/*.logo {*/
/*  width: 25px; !* 设置图片宽度 *!*/
/*  height: 25px; !* 保持图片的宽高比 *!*/
/*}*/




</style>
