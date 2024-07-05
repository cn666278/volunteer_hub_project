<template>
  <el-footer class="footer">

  </el-footer>
</template>

<script>
export default {
  name: 'VolunteerFooter'
};
</script>

<style scoped>
.footer {
  background-color: #000000;
  color: #FFFFFF;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 1200px;
}

.footer-section {
  margin-right: 20px;
}

.footer-section h3 {
  margin-bottom: 10px;
}

.footer-section p {
  margin: 5px 0;
}

.el-link {
  color: #FFFFFF;
  margin-right: 10px;
  font-size: 24px;
}
</style>
