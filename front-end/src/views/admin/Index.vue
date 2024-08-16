<template>
  <div class="index">
    <div class="left">
      <div class="title">
        <h2>{{ $t('appName') }}</h2>
      </div>
      <el-menu router active-text-color="#ffd04b" background-color="#a9181a" class="el-menu-vertical-demo"
        default-active="2" text-color="#fff">
        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <Briefcase />
            </el-icon>
            <span>{{ $t('menu.manage') }}</span>
          </template>
          <el-menu-item index="/admin/role">{{ $t('menu.role') }}</el-menu-item>
          <el-menu-item index="/admin/user">{{ $t('menu.user') }}</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <Tools />
            </el-icon>
            <span>{{ $t('menu.approve') }}</span>
          </template>
          <el-menu-item index="/admin/approve/event">{{ $t('menu.event') }}</el-menu-item>
          <el-menu-item index="/admin/approve/organizer">{{ $t('menu.organizer') }}</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <Avatar />
            </el-icon>
            <span>{{ $t('menu.system') }}</span>
          </template>
          <el-menu-item index="/admin/menu">{{ $t('menu.menu') }}</el-menu-item>
          <el-menu-item index="/admin/dict">{{ $t('menu.dict') }}</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>
    <div class="right">
      <div class="top">
        <el-menu router mode="horizontal" background-color="#a9181a" text-color="#fff" active-text-color="rgb(255, 143, 15)"
          :ellipsis="false">
          <el-menu-item index="/admin">
            <el-icon>
              <House />
            </el-icon>
            {{ $t('menu.home') }}
          </el-menu-item>
          <el-menu-item index="/admin/mail">
            <el-icon>
              <Message />
            </el-icon>
            {{ $t('menu.mail') }}
          </el-menu-item>
          <el-menu-item index="3">
            <el-icon>
              <ChatDotRound />
            </el-icon>
            {{ $t('menu.message') }}
          </el-menu-item>
          <changeLanguage />
          <el-sub-menu index="5">
            <template #title>
              <el-icon>
                <User />
              </el-icon>
              {{ userStore.user.username }}
            </template>
            <el-menu-item index="/admin/personal">{{ $t('menu.personal') }}</el-menu-item>
            <el-menu-item index="/admin/password">{{ $t('menu.password') }}</el-menu-item>
            <el-menu-item index="" @click="exit">{{ $t('menu.exit') }}</el-menu-item>
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
  import { onMounted } from "vue";
  import { useRouter } from "vue-router";
  import useUser from "../../store/user.ts";
  import { ElMessageBox } from "element-plus";
  import changeLanguage from '../../components/changeLanguage.vue';
  import { useI18n } from 'vue-i18n';
  const { t } = useI18n();
  
  let userStore = useUser();
  let router = useRouter();
  // check if the user is logged in, if not, redirect to the login page
  onMounted(() => {
    console.log(userStore.user);
    if (!userStore.user.username) {
      router.push("/");
    }
  });
  
  // exit
  const exit = () => {
    ElMessageBox.confirm(t('logout.message'), t('logout.title'), {
    confirmButtonText: t('logout.confirm'),
    cancelButtonText: t('logout.cancel'),
      type: "warning",
    })
      .then(() => {
        userStore.clearUser();
        router.push("/");
      })
      .catch(() => {
        console.log("Cancel exit");
      });
  };
  </script>
  
  <style lang="scss">
  .index {
    width: 100vw; // 100% of the viewport width
    height: 100vh; // 100% of the viewport height
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
      }
      .content {
        flex: 1;
        background-color: white;
        padding: 10px;
      }
    }
  }
  </style>
  