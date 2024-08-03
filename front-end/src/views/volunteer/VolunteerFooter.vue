<template>
  <el-footer class="footer">
    <div class="footer-content">
      <div class="footer-section navigation">
        <h3>Navigation</h3>
        <ul>
          <li>About the WSA</li>
          <li>WSA Services</li>
          <li>WSA Membership</li>
          <li>Events & Courses</li>
          <li>Partners</li>
          <li>News</li>
          <li>Contact the WSA</li>
          <li>User Login</li>
        </ul>
      </div>
      <div class="footer-section newsletter">
        <h3>Sign Up To Our Newsletter</h3>
        <p><span>*</span> indicates required</p>
        <el-form>
          <el-form-item>
            <el-input placeholder="Email Address *"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input placeholder="First Name"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input placeholder="Last Name"></el-input>
          </el-form-item>
          <el-button type="primary">{{ $t('footer.subscribe') }}</el-button>
        </el-form>
      </div>
      <div class="footer-section social-media">
        <h3>Find Us On Social Media</h3>
        <a href="https://twitter.com" target="_blank">
          <font-awesome-icon :icon="['fab', 'twitter']" />
        </a>
        <a href="https://linkedin.com" target="_blank">
          <font-awesome-icon :icon="['fab', 'linkedin']" />
        </a>
        <a href="https://facebook.com" target="_blank">
          <font-awesome-icon :icon="['fab', 'facebook']" />
        </a>
        <a href="https://instagram.com" target="_blank">
          <font-awesome-icon :icon="['fab', 'instagram']" />
        </a>
        <a href="https://youtube.com" target="_blank">
          <font-awesome-icon :icon="['fab', 'youtube']" />
        </a>
      </div>
      <div class="footer-section contact">
        <h3>Contact Us Today</h3>
        <p>Welsh Sports Association</p>
        <p>c/o National Centre</p>
        <p>Sophia Gardens</p>
        <p>Cardiff CF11 9SW</p>
        <p>029 2033 4972</p>
        <p>admin@wsa.wales</p>
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
  background-color: #333333;
  color: #FFFFFF;
  padding: 40px 20px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  height: 300px;
  width: 100%;
  box-sizing: border-box;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 1200px;
  box-sizing: border-box;
}

.footer-section {
  flex: 1;
  margin-right: 20px;
}

.footer-section h3 {
  margin-bottom: 10px;
}

.footer-section p, .footer-section li {
  margin: 5px 0;
}

.navigation ul {
  list-style-type: none;
  padding: 0;
}

.navigation ul li {
  margin-bottom: 5px;
}

.newsletter p {
  font-size: 0.9em;
}

.newsletter p span {
  color: red;
}

.social-media a {
  color: #FFFFFF;
  margin-right: 10px;
  font-size: 24px;
}

@media (max-width: 768px) {
  .footer {
    display: none;
  }


}
</style>
