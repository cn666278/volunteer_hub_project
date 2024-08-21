<template>
  <div class="index">
    <div class="left">
      <div class="title">
        <h2>{{ $t('sidebar.title') }}</h2>
      </div>
      <el-menu router active-text-color="#ffd04b" background-color="#a9181a" class="el-menu-vertical-demo"
               default-active="2" text-color="#fff">
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Briefcase /></el-icon>
            <span>{{ $t('sidebar.event') }}</span>
          </template>
          <el-menu-item index="/eventRegister">{{ $t('sidebar.eventRegister') }}</el-menu-item>
          <el-menu-item index="/myEvents">{{ $t('sidebar.myEvents') }}</el-menu-item>
<!--          <el-menu-item index="/uploadTest">{{ $t('sidebar.uploadTest') }}</el-menu-item>-->
        </el-sub-menu>
<!--        <el-sub-menu index="2">-->
<!--          <template #title>-->
<!--            <el-icon><Tools /></el-icon>-->
<!--            <span>{{ $t('sidebar.system') }}</span>-->
<!--          </template>-->
<!--          <el-menu-item index="2-1">{{ $t('sidebar.menu') }}</el-menu-item>-->
<!--          <el-menu-item index="2-2">{{ $t('sidebar.dictionary') }}</el-menu-item>-->
<!--        </el-sub-menu>-->
<!--        <el-sub-menu index="3">-->
<!--          <template #title>-->
<!--            <el-icon><Avatar /></el-icon>-->
<!--            <span>{{ $t('sidebar.account') }}</span>-->
<!--          </template>-->
<!--          <el-menu-item index="3-1">{{ $t('sidebar.role') }}</el-menu-item>-->
<!--          <el-menu-item index="3-2">{{ $t('sidebar.user') }}</el-menu-item>-->
<!--        </el-sub-menu>-->
      </el-menu>
    </div>
    <div class="right">
      <div class="top">
        <el-menu router mode="horizontal" background-color="#a9181a" text-color="#fff" active-text-color="rgb(255, 143, 15)"
                 :ellipsis="false">
          <el-menu-item index="/organizer">
            <el-icon><House /></el-icon>
            {{ $t('topbar.home') }}
          </el-menu-item>
<!--          <el-menu-item index="2">-->
<!--            <el-icon><Message /></el-icon>-->
<!--            {{ $t('topbar.mail') }}-->
<!--          </el-menu-item>-->
<!--          <el-menu-item index="3">-->
<!--            <el-icon><ChatDotRound /></el-icon>-->
<!--            {{ $t('topbar.message') }}-->
<!--          </el-menu-item>-->
          <changeLanguage />
          <el-sub-menu index="4">
            <template #title>
              <el-icon><User /></el-icon>
              {{ username }}
            </template>
<!--            <el-menu-item index="4-1">{{ $t('topbar.personal') }}</el-menu-item>-->
<!--            <el-menu-item index="4-2">{{ $t('topbar.password') }}</el-menu-item>-->
            <el-menu-item index="" @click="exit">{{ $t('topbar.exit') }}</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
      <div class="content">
        <!-- add router, render content -->
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  House,
  Briefcase,
  Tools,
  Message,
  ChatDotRound,
  Avatar,
  User,
} from "@element-plus/icons-vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import useUser from "../../store/user.ts";
import { ElMessageBox } from 'element-plus';
import changeLanguage from "../../components/changeLanguage.vue";
import { useI18n } from 'vue-i18n';

const { t } = useI18n();

let userStore = useUser();
let router = useRouter();
const username = ref(''); // 定义一个变量存储用户名

// check if the user is logged in, if not, redirect to the login page
onMounted(() => {
  console.log(userStore.user);
  if (!userStore.user.username) {
    router.push('/');
  } else {
    username.value = userStore.user.username; // 设置用户名
  }
});

// exit
const exit = () => {
  ElMessageBox.confirm(t('messages.confirmExit'), t('messages.systemNotification'), {
    confirmButtonText: t('buttons.confirm'),
    cancelButtonText: t('buttons.cancel'),
    type: 'warning'
  }).then(() => {
    userStore.clearUser();
    router.push('/');
  }).catch(() => {
    console.log(t('messages.cancelExit'));
  });
};
</script>

<style lang="scss">
.index {
  width: 100vw; // 100% of the viewport width
  height: 100vh;
  display: flex;
  .left {
    width: 200px;
    background-color: #a9181a;
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
      background-color: #a9181a;
      color: white;
      display: flex;
      justify-content: flex-end;
      .el-menu {
        // delete the bottom border
        border-bottom: none;
      }
      .el-sub-menu {
        cursor: pointer;
        color: white;
      }
    }
    .content {
      flex: 1;
      background-color: white;
      padding: 10px;
    }
  }
}
</style>
