<template>
  <div>
    <div class="navbar-top">
      <img src="../../assets/logo.png" class="top-logo">
      <change-language />
    </div>

    <!-- Navigation Menu -->
    <el-menu
        router
        :default-active="activeIndex"
        class="el-menu-demo navbar"
        :mode="'horizontal'"
        :ellipsis="false"
        @select="handleSelect"
        text-color="#a9181a"
        active-text-color="#a9181a"
    >
      <el-menu-item index="/">
        <el-icon><House /></el-icon><span class="menu-text">{{ $t('navbar.home') }}</span>
      </el-menu-item>
      <el-menu-item index="/volunteer/events">
        <el-icon><Search /></el-icon><span class="menu-text">{{ $t('navbar.events') }}</span>
      </el-menu-item>
      <el-menu-item index="/volunteer/rewardStore">
        <el-icon><Star /></el-icon><span class="menu-text">{{ $t('navbar.rewardStore') }}</span>
      </el-menu-item>
      <el-dropdown trigger="click" @command="handleCommand">
        <el-menu-item>
          <el-icon><User /></el-icon><span class="menu-text">{{ $t('navbar.profile') }}</span>
        </el-menu-item>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">{{ $t('navbar.profile') }}</el-dropdown-item>
            <el-dropdown-item command="logout">{{ $t('navbar.logout') }}</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-menu>

    <!-- Mobile Menu Icon -->
    <div class="mobile-menu-icon" @click="toggleMobileMenu">
      <i class="el-icon-menu"></i>
    </div>

    <!-- Mobile Navigation Menu -->
    <el-menu
        v-if="mobileMenuVisible"
        router
        :default-active="activeIndex"
        class="el-menu-demo mobile-navbar"
        :mode="'vertical'"
        :ellipsis="false"
        @select="handleSelect"
        text-color="#a9181a"
        active-text-color="#a9181a"
    >
      <el-menu-item index="/">
        <el-icon><House /></el-icon>{{ $t('navbar.home') }}
      </el-menu-item>
      <el-menu-item index="/volunteer/events">
        <el-icon><Search /></el-icon>{{ $t('navbar.events') }}
      </el-menu-item>
      <el-menu-item index="/volunteer/rewardStore">
        <el-icon><Star /></el-icon>{{ $t('navbar.rewardStore') }}
      </el-menu-item>
      <el-dropdown trigger="click" @command="handleCommand">
        <el-menu-item>
          <el-icon><User /></el-icon>{{ $t('Profile') }}
        </el-menu-item>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">{{ $t('Profile') }}</el-dropdown-item>
            <el-dropdown-item command="logout">{{ $t('navbar.logout') }}</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-menu>
  </div>
</template>


<script lang="ts" setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import useUser from '../../store/user';
import changeLanguage from '../../components/changeLanguage.vue';
import { ElMessageBox } from 'element-plus';
import { useI18n } from 'vue-i18n';
import { House, Search, Star, User } from '@element-plus/icons-vue';

const { t } = useI18n();

// user store
const userStore = useUser();
let router = useRouter();

const activeIndex = ref('1');
const mobileMenuVisible = ref(false);

const handleSelect = (key: string, keyPath: string[]) => {
  // 判断用户是否登录
  if (!userStore.user.username) {
    // 用户未登录，跳转到登录页面
    router.push('/login');
  } else {
    // 用户已登录，导航到相应页面
    router.push(key);
  }
};

const toggleMobileMenu = () => {
  mobileMenuVisible.value = !mobileMenuVisible.value;
};

const navigateToProfile = () => {
  router.push('/volunteer/personal');
};

const handleCommand = (command: string) => {
  // 判断用户是否登录
  if (!userStore.user.username) {
    // 用户未登录，跳转到登录页面
    router.push('/login');
  } else {
    // 用户已登录，导航到相应页面
    if (command === 'profile') {
      navigateToProfile();
    } else if (command === 'logout') {
      exit();
    }
  }

};

// exit
const exit = () => {
  ElMessageBox.confirm(t('logout.message'), t('logout.title'), {
    confirmButtonText: t('logout.confirm'),
    cancelButtonText: t('logout.cancel'),
    type: 'warning',
  })
      .then(() => {
        userStore.clearUser();
        router.push('/login');
      })
      .catch(() => {
        console.log('Cancel exit');
      });
};
</script>


<style scoped>
.navbar-top {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ffffff;
  padding: 20px;
  z-index: 1010;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 60px;
}

.top-logo {
  width: 50px;
}

.language-switcher i {
  margin-right: 8px;
}

.language-switcher .el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.navbar-container,
.el-menu-demo {
  margin-top: 60px; /* 在navbar-top之下，调整这个值以适应navbar-top的高度 */
  display: flex;
  justify-content: center;
  background-color: #ffffff;
  /*padding-right: 50px;*/
  width: 100%;
  z-index: 1000;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

body {
  padding-top: 80px; /* 调整这个值以确保足够的空间给固定的导航栏 */
}

:root {
  --el-menu-active-color: #a9181a;
  --el-menu-hover-text-color: #a9181a;
}

.el-menu-item {
  padding: 10px 15px;
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
  margin-bottom: 0;
}

.weblogo {
  padding-left: 20px;
  flex: none;
  display: flex;
  justify-content: center;
  z-index: 1000;
}

.custom-icon {
  width: 20px;
  height: 20px;
}

.logout-menu-item {
  margin-left: auto;
  display: flex;
  align-items: center;
  justify-content: center;
}

.mobile-menu-icon {
  display: none;
  font-size: 24px;
  cursor: pointer;
}

/* Media queries for responsive design */
@media (max-width: 768px) {
  .navbar-top {
    justify-content: space-between;
    padding: 10px;
  }

  .navbar-container,
  .el-menu-demo {
    padding-left:0px;
    justify-content: center; /* 居中 */
  }

  .mobile-menu-icon {
    display: block;
  }

  .mobile-navbar {
    display: flex;
    flex-direction: column;
    width: 100%;
  }

  .mobile-navbar .el-menu-item {
    padding: 10px 15px;
  }

  .el-menu-item .menu-text {
    display: none; /* 隐藏文本 */
  }
}
</style>
