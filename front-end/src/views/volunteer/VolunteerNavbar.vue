<template>
  <div>
    <div class="navbar-top">
      <!-- Logo and Language Switcher -->
      <img src="../../assets/logo.png" class="top-logo">
      <change-language />
    </div>

    <!-- Desktop Navigation Menu -->
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
      <!-- Profile Dropdown Menu -->
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
      <!-- Profile Dropdown for Mobile -->
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

// Initialize user store and router
const userStore = useUser();
let router = useRouter();

const activeIndex = ref('1'); // Active menu item index
const mobileMenuVisible = ref(false); // Mobile menu visibility toggle

// Handle menu selection
const handleSelect = (key: string, keyPath: string[]) => {
  // Check if the user is logged in
  if (!userStore.user.username) {
    // If not logged in, redirect to login page
    router.push('/login');
  } else {
    // If logged in, navigate to the selected page
    router.push(key);
  }
};

// Toggle mobile menu visibility
const toggleMobileMenu = () => {
  mobileMenuVisible.value = !mobileMenuVisible.value;
};

// Navigate to user profile
const navigateToProfile = () => {
  router.push('/volunteer/personal');
};

// Handle dropdown menu commands
const handleCommand = (command: string) => {
  // Check if the user is logged in
  if (!userStore.user.username) {
    // If not logged in, redirect to login page
    router.push('/login');
  } else {
    // If logged in, handle command
    if (command === 'profile') {
      navigateToProfile();
    } else if (command === 'logout') {
      exit();
    }
  }
};

// Handle logout process
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

/* Adjust styles for navbar and menu */
.navbar-container,
.el-menu-demo {
  margin-top: 60px; /* Ensure it appears below the fixed navbar */
  display: flex;
  justify-content: center;
  background-color: #ffffff;
  width: 100%;
  z-index: 1000;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

body {
  padding-top: 80px; /* Ensure enough space for the fixed navbar */
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
    padding-left: 0;
    justify-content: center;
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
    display: none; /* Hide text on smaller screens */
  }
}
</style>
