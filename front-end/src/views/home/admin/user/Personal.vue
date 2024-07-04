<template>
  <div class="personal">
    <div class="left">
      <img :src="userStore.user.photo" alt="avatar" />
    </div>
    <div class="right">
      <el-descriptions
        class="margin-top"
        title="User Information"
        :column="2"
        :size="size"
        border
      >
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <View />
              </el-icon>
              Login ID
            </div>
          </template>
          {{ userStore.user.loginId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <message />
              </el-icon>
              Mail
            </div>
          </template>
          {{ userStore.user.email }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <tickets />
              </el-icon>
              Username
            </div>
          </template>
          {{ userStore.user.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <iphone />
              </el-icon>
              Phone
            </div>
          </template>
          {{ userStore.user.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <user />
              </el-icon>
              Role
            </div>
          </template>
          <el-tag size="small">{{ userStore.user.role.roleName }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <office-building />
              </el-icon>
              Address
            </div>
          </template>
          Cardiff, UK
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </div>
</template>

<script setup lang="ts">
import useUser from "../../../../store/user.ts";
import {
  Iphone,
  Message,
  OfficeBuilding,
  Tickets,
  User,
  View,
} from "@element-plus/icons-vue";
import type { ComponentSize } from "element-plus";
import { ref } from "vue";
let userStore = useUser();
const size = ref<ComponentSize>("large");
</script>

<style lang="scss">
.personal {
  display: flex;
  .left {
    width: 200px;
    height: 200px;
    background-color: rgba(240, 240, 240, 0.8);
    backdrop-filter: blur(10px);
    padding: 20px;
    img {
      width: 150px;
      height: 150px;
    }
  }
  .right {
    margin-left: 10px;
    background-color: #fff;
  }
}
</style>
