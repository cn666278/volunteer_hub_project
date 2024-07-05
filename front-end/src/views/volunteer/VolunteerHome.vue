<template>
  <div class="volunteerHome">
    <!-- Header 部分 -->
    <el-header>
      <Navbar />
    </el-header>

    <!-- 主内容部分 -->
    <el-main>
      <div class="dashed-box">
        <p>这是一个虚线透明框</p>
      </div>
    </el-main>

    <!-- 页脚部分 -->
    <VolunteerFooter />
  </div>
</template>

<script>
import Navbar from './VolunteerNavbar.vue';
import VolunteerFooter from './VolunteerFooter.vue';

export default {
  name: 'VolunteerHome',
  components: {
    Navbar,
    VolunteerFooter
  }
};
</script>

<style scoped>
.volunteerHome {
  padding-top: 100px;
}

.dashed-box {
  height: 400px;
  border: 2px dashed #607D8B;
  background-color: rgba(255, 255, 255, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #607D8B;
  font-size: 24px;
  margin: 20px;
}
</style>
