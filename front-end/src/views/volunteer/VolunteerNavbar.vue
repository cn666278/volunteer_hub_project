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
    <div class="navbar-container">
      <el-menu mode="horizontal" class="navbar">
        <el-menu-item index="1">Home</el-menu-item>
        <el-menu-item index="2">Events</el-menu-item>
        <el-menu-item index="3">News</el-menu-item>
        <el-menu-item index="4">Search</el-menu-item>
        <el-menu-item index="5">Login</el-menu-item>
        <el-menu-item index="6">
          <el-avatar src="user-avatar.jpg" class="menu-avatar"></el-avatar>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Navbar'
};
</script>

<style scoped>
.navbar-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #FFFFFF;
  padding: 10px 20px;
  border-bottom: 1px solid #ddd;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  z-index: 1000;
}

.contact-info {
  display: flex;
  align-items: center;
}

.contact-info .icon {
  margin-right: 5px;
}

.contact-info.right {
  justify-content: flex-end;
}

.navbar-container {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  margin-top: 20px;
  position: fixed;
  top: 60px;
  left: 0;
  background-color: #FFFFFF;
  z-index: 1000;
}

.navbar {
  background-color: #FFFFFF;
}

.el-menu-item {
  font-size: 24px;
  line-height: 100px;
  color: #607D8B;
  padding: 0 20px;
  overflow: visible;
  white-space: nowrap;
}

.el-menu-item:not(.is-disabled):hover {
  background-color: #f5f5f5;
}

.el-menu-item.is-active {
  color: #607D8B;
  border-bottom: 4px solid #607D8B;
}

.menu-avatar {
  --el-avatar-size: 40px;
  vertical-align: middle;
}
</style>
