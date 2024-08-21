<template>
  <el-footer class="footer">
    <div class="footer-content">
      <div class="footer-section navigation">
        <h3>Navigation</h3>
        <ul>
          <li><a href="https://wsa.wales/about-the-wsa/" target="_blank">About the WSA</a></li>
          <li><a href="https://wsa.wales/our-services/" target="_blank">WSA Services</a></li>
          <li><a href="https://wsa.wales/membership/" target="_blank">WSA Membership</a></li>
          <li><a href="https://wsa.wales/events-courses/" target="_blank">Events & Courses</a></li>
          <li><a href="https://wsa.wales/partners/" target="_blank">Partners</a></li>
          <li><a href="https://wsa.wales/news/" target="_blank">News</a></li>
          <li><a href="https://wsa.wales/contact/" target="_blank">Contact the WSA</a></li>
        </ul>
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
        <!-- 添加自定义图标 -->
        <div class="custom-icons">
          <a href="https://www.linkedin.com/company/welshsportsassociation/" target="_blank">
            <img src="../../assets/linkedin.png" alt="LinkedIn" class="custom-icon" />
          </a>
          <a href="https://x.com/welshsportassoc" target="_blank">
            <img src="../../assets/twitter2.png" alt="Twitter" class="custom-icon" />
          </a>
        </div>
      </div>
      <div class="footer-section contact">
        <h3>Contact Us Today</h3>
        <p>Welsh Sports Association</p>
        <p>c/o National Centre</p>
        <p>Sophia Gardens</p>
        <p>Cardiff CF11 9SW</p>
        <p>029 2033 4972</p>
        <p>admin@wsa.wales</p>
        <p><a href="https://wsa.wales/terms-conditions/" target="_blank" class="terms-link">Terms & Conditions</a></p>
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
  padding: 40px 60px;
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
  width: 30%; /* 每个div的宽度设为30% */
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

.navigation ul li a {
  color: inherit; /* 继承父元素的字体颜色，保持白色 */
  text-decoration: none; /* 去掉下划线 */
}

.navigation ul li a:hover {
  text-decoration: underline; /* 悬停时显示下划线 */
}

.social-media {
  text-align: center; /* 社交媒体部分居中 */
}

.social-media a {
  color: #FFFFFF;
  margin-right: 10px;
  font-size: 24px;
}

.custom-icons {
  margin-top: 20px;
}

.custom-icon {
  width: 60px; /* 根据需要调整图标的大小 */
  height: 60px;
  margin-right: 10px;
  filter: brightness(0) invert(1);
}

.contact {
  text-align: right; /* 联系我们部分居右 */
}

.terms-link {
  color: inherit; /* 继承父元素的字体颜色，保持白色 */
  text-decoration: none; /* 去掉下划线 */
}

.terms-link:hover {
  text-decoration: underline; /* 悬停时显示下划线 */
}

@media (max-width: 768px) {
  .footer {
    display: none;
  }
}
</style>
