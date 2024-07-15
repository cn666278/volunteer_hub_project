<template>
  <el-footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <el-row>
          <el-col :span="24"><h3>About Us</h3></el-col>
          <el-col :span="24"><p>Learn more</p></el-col>
        </el-row>
      </div>
      <div class="footer-section">
        <h3>Newsletter</h3>
        <el-form>
          <el-form-item>
            <el-input placeholder="Your email"></el-input>
          </el-form-item>
          <el-button type="primary">Subscribe</el-button>
        </el-form>
      </div>
      <div class="footer-section">
        <h3>Follow Us</h3>
        <el-link icon="el-icon-twitter"></el-link>
        <el-link icon="el-icon-linkedin"></el-link>
      </div>
      <div class="footer-section">
        <h3>Contact Us</h3>
        <p>Address: Your Address</p>
        <p>Phone: Your Phone Number</p>
        <p>Email: contact@example.com</p>
      </div>
    </div>
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
  padding: 100px;
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
