<template>
  <div class="home">
    <div class="left">
      <div class="title">
        <h2>WSA Volunteer Hub</h2>
      </div>
      <el-menu
        router
        active-text-color="#ffd04b"
        background-color="#142334"
        class="el-menu-vertical-demo"
        default-active="2"
        text-color="#fff"
      >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><Briefcase /></el-icon>
            <span>Manage</span>
          </template>
          <el-menu-item index="/role">Role</el-menu-item>
          <el-menu-item index="/eventManage">User</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="2">
          <template #title>
            <el-icon><Tools /></el-icon>
            <span>View</span>
          </template>
          <el-menu-item index="2-1">Event</el-menu-item>
          <el-menu-item index="2-2">Organizer</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="3">
          <template #title>
            <el-icon><Avatar /></el-icon>
            <span>System</span>
          </template>
          <el-menu-item index="3-1">Menu</el-menu-item>
          <el-menu-item index="3-2">Dictionary</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </div>
    <div class="right">
      <div class="top">
        <el-menu
          router
          mode="horizontal"
          background-color="#142334"
          text-color="#fff"
          active-text-color="#ffd04b"
          :ellipsis="false"
        >
          <el-menu-item index="1">
            <el-icon><House /></el-icon>
            Home
          </el-menu-item>
          <el-menu-item index="2">
            <el-icon><Message /></el-icon>
            Mail
          </el-menu-item>
          <el-menu-item index="3">
            <el-icon><ChatDotRound /></el-icon>
            Message
          </el-menu-item>
          <el-sub-menu index="4">
            <template #title>
              <el-icon><User /></el-icon>
              Admin
            </template>
            <el-menu-item index="2-1">Personal</el-menu-item>
            <el-menu-item index="2-2">Password</el-menu-item>
            <el-menu-item index="" @click="exit">Exit</el-menu-item>
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
import useUser from "../../../store/user.ts";
import { ElMessageBox } from "element-plus";

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
  ElMessageBox.confirm("Do you confirm to exit?", "System Notification", {
    confirmButtonText: "Confirm",
    cancelButtonText: "Cancel",
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
