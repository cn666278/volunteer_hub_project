<template>
  <div class="personal">
    <div class="left">
      <img :src="userStore.user.photo" alt="avatar" />
    </div>
    <div class="right">
      <el-descriptions
        class="margin-top"
        :title="$t('user.personal.title')"
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
              {{ $t('user.personal.loginId') }}
            </div>
          </template>
          {{ userStore.user.loginId }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <Message />
              </el-icon>
              {{ $t('user.personal.mail') }}
            </div>
          </template>
          {{ userStore.user.email }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <Tickets />
              </el-icon>
              {{ $t('user.personal.username') }}
            </div>
          </template>
          {{ userStore.user.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <Iphone />
              </el-icon>
              {{ $t('user.personal.phone') }}
            </div>
          </template>
          {{ userStore.user.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <User />
              </el-icon>
              {{ $t('user.personal.role') }}
            </div>
          </template>
          <el-tag size="small">{{ userStore.user.role.roleName }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <OfficeBuilding />
              </el-icon>
              {{ $t('user.personal.address') }}
            </div>
          </template>
          Cardiff, UK
        </el-descriptions-item>
      </el-descriptions>
    </div>
  </div>
</template>

<script setup lang="ts">
import useUser from "../../../store/user.ts";
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
