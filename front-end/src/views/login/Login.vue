<template>
  <div class="container">
    <div class="logo">
      <img alt="logo" src="../../assets/logo.jpg" />
      <div class="logo-text">{{ $t('appName') }}</div>
    </div>
    <div class="top-right">
      <changeLanguage />
    </div>
    <LoginBanner />
    <div class="content">
      <div class="content-inner">
        <el-tabs type="border-card" class="responsive-tabs">
          <el-tab-pane :label="$t('login.login')">
            <VolunteerLoginForm />
          </el-tab-pane>
          <!-- <el-tab-pane :label="$t('login.organizer')">{{ $t('login.organizer') }}</el-tab-pane> -->
          <!-- <el-tab-pane :label="$t('login.admin')">{{ $t('login.admin') }}</el-tab-pane> -->
          <el-tab-pane :label="$t('login.register')">
            <RegisterForm />
          </el-tab-pane>
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
import RegisterForm from './Register.vue';
import changeLanguage from '../../components/changeLanguage.vue';
</script>

<style scoped lang="scss">
.container {
  display: flex;
  height: 100vh;
  position: relative;

  .top-right {
    position: absolute;
    top: 20px;
    right: 20px;
    z-index: 10;  // 确保语言切换按钮在上层
  }

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
    z-index: 1;  // 确保 content 不会覆盖语言切换按钮

    .content-inner {
      width: 100%;
      max-width: 550px; /* 确保内容的最大宽度与Tab一致 */
      padding: 20px;
      box-sizing: border-box;
    }

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

:deep(.el-tabs) {
  --el-color-primary: #f74150;
  --el-color-primary-light-3: #ff5d6a;
}

.responsive-tabs {
  width: 100%;
  max-width: 550px; /* 确保Tab内容在大屏幕时最大宽度为500px */
  margin: 0 auto; /* 居中对齐 */
  box-sizing: border-box; /* 确保内边距影响到整个宽度 */
}

/* 针对小屏幕的调整 */
@media (max-width: 768px) {
  .banner {
    display: none; /* 当屏幕宽度小于768px时隐藏横幅 */
  }

  .responsive-tabs {
    width: 100%; /* 小屏幕时表单宽度占100% */
    padding: 10px; /* 为表单添加一些内边距 */
  }

  .logo {
    img {
      height: 50px; /* 减少Logo的高度 */
    }

    .logo-text {
      font-size: 16px; /* 减少Logo文本的字体大小 */
    }
  }

  .top-right {
    top: 10px;
    right: 10px; /* 缩小右上角按钮的边距 */
  }

  .footer {
    font-size: 12px; /* 减少页脚字体大小 */
  }
}
</style>
