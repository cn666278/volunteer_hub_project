<template>
  <div>
    <div class="navbar-top">
      <img src="../../assets/logo.png" class="top-logo">
      <el-dropdown class="language-switcher">
  <span class="el-dropdown-link">
    <img src="../../assets/language.png" class="custom-icon"> <!-- 更换为自定义图标 -->
  </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>English</el-dropdown-item>
          <el-dropdown-item>中文</el-dropdown-item>
          <el-dropdown-item>Español</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

    </div>

    <el-menu
        router
        :default-active="activeIndex"
        class="el-menu-demo navbar"
        mode="horizontal"
        :ellipsis="false"
        @select="handleSelect"
        text-color="#a9181a"
        active-text-color="#a9181a"
    >
      <div class="weblogo">
        <img src="../../assets/logo.png" class="logo">
      </div>
      <el-menu-item index="/volunteer">Home</el-menu-item>
      <el-menu-item index="/volunteer/events">Events</el-menu-item>
      <el-menu-item index="3">News</el-menu-item>

      <el-menu-item index="/volunteer/rewardStore">RewardStore</el-menu-item>
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
  position: fixed; /* 固定位置 */
  top: 0; /* 顶部对齐 */
  left: 0; /* 左边对齐 */
  right: 0; /* 右边对齐 */
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #FFFFFF;
  padding:20px;
  z-index: 1010; /* 高层次以保持在顶部 */
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  width: 100%;
  height: 20px;

}

.top-logo {
  width: 50px; /* Adjust the size as necessary */
}

.language-switcher i {
  margin-right: 8px;
}

.language-switcher .el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.navbar-container, .el-menu-demo {
  position: fixed; /* 固定位置 */
  top: 40px; /* 在navbar-top之下，调整这个值以适应navbar-top的高度 */
  left: 0; /* 左边对齐 */
  right: 0; /* 右边对齐 */
  display: flex;
  justify-content: flex-end;
  background-color: #FFFFFF;
  overflow-x: auto;
  padding-right: 50px;
  padding-left: 50px;
  width: 100%;
  z-index: 1000; /* 确保在下层 */
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

body {
  padding-top: 120px; /* 调整这个值以确保足够的空间给固定的导航栏 */

}

:root {
  --el-menu-active-color: #a9181a; /* 例如，将活跃菜单项的颜色修改为红色 */
  --el-menu-hover-text-color: #a9181a; /* 例如，将悬停菜单项的颜色修改为蓝色 */
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

.custom-icon {
  width: 20px; /* 或者其他适当的尺寸 */
  height: 20px; /* 保持宽高比 */
}





</style>
