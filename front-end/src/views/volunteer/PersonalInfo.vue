<template>
  <div class="PersonlInfo">
    <!-- Header -->
    <el-header>
      <Navbar/>
    </el-header>

    <!-- Main content -->





    <!-- Footer -->
    <VolunteerFooter/>
  </div>
</template>


<script>
import Navbar from './VolunteerNavbar.vue';
import VolunteerFooter from './VolunteerFooter.vue';

export default {
  name: 'PersonalInfo',
  components: {
    Navbar,
    VolunteerFooter,
  },

};
</script>

<style scoped>


</style>
