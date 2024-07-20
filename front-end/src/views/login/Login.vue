<template>
  <div class="container">
    <div class="logo">
      <img alt="logo" src="../../assets/logo.jpg" />
      <div class="logo-text">WSA Volunteer Hub</div>
    </div>
    <LoginBanner />
    <div class="content">
      <div class="content-inner">
        <el-tabs type="border-card">
          <el-tab-pane label="Volunteer">
            <VolunteerLoginForm />
          </el-tab-pane>
          <el-tab-pane label="Organizer">Organizer</el-tab-pane>
          <el-tab-pane label="Admin">Admin</el-tab-pane>
          <el-tab-pane label="Register">Register</el-tab-pane>
        </el-tabs>
      </div>
      <div class="footer">
        <Footer />
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import Footer from './components/Footer.vue';
import LoginBanner from './components/banner.vue';
import VolunteerLoginForm from './components/VolunteerLoginForm.vue';

</script>
<style scoped lang="scss">
// scoped表示这个样式只在当前组件中生效, 不会影响到其他组件

.container {
  display: flex;
  height: 100vh;

  .banner {
    width: 550px;
    background: linear-gradient(163.85deg, #e0535f 0%, #ad0b0b 100%);
  }

  .content {
    position: relative;
    display: flex;
    flex: 1;
    align-items: center;
    justify-content: center;
    padding-bottom: 40px;

    :deep(.el-form-item__label) {
      color: white;
    }
  }

  .footer {
    position: absolute;
    right: 0;
    bottom: 0;
    width: 100%;
  }
}

.logo {
  position: fixed;
  top: 24px;
  left: 22px;
  z-index: 1;
  display: inline-flex;
  align-items: center;

  img {
    height: 70px;
  }

  .logo-text {
    color: white;
    font-size: 20px;
    font-weight: 500;
    margin-left: 20px;
  }

  &-text {
    margin-right: 4px;
    margin-left: 4px;
    color: var(--color-fill-1);
    font-size: 20px;
  }
}

.demo-tabs>.el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}


:deep(.el-tabs--border-card) {
  background: linear-gradient(163.85deg, #e0535f 0%, #c52323 100%);
}

:deep(.el-tabs){
  --el-color-primary: #f74150;
  --el-color-primary-light-3: #ff5d6a;
}
</style>
