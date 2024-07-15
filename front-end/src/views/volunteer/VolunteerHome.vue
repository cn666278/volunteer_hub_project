<template>
  <div className="volunteerHome">
    <!-- Header -->
    <el-header>
      <Navbar/>
    </el-header>

        <!-- Main content -->
        <VolunteerMainPage />

    <!-- Footer -->
    <VolunteerFooter/>
  </div>
</template>

<script>
import Navbar from './VolunteerNavbar.vue';
import VolunteerFooter from './VolunteerFooter.vue';
import VolunteerMainPage from './VolunteerMainPage.vue';

export default {
  name: 'VolunteerHome',
  components: {
    Navbar,
    VolunteerFooter,
    VolunteerMainPage
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