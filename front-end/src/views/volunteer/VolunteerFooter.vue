<template>
  <el-footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <el-row>
          <el-col :span="24"><h3>{{ $t('footer.aboutUs') }}</h3></el-col>
          <el-col :span="24"><p>{{ $t('footer.learnMore') }}</p></el-col>
        </el-row>
      </div>
      <div class="footer-section">
        <h3>{{ $t('footer.newsletter') }}</h3>
        <el-form>
          <el-form-item>
            <el-input :placeholder="$t('footer.yourEmail')"></el-input>
          </el-form-item>
          <el-button type="primary">{{ $t('footer.subscribe') }}</el-button>
        </el-form>
      </div>
      <div class="footer-section">
        <h3>{{ $t('footer.followUs') }}</h3>
        <el-link icon="el-icon-twitter"></el-link>
        <el-link icon="el-icon-linkedin"></el-link>
      </div>
      <div class="footer-section">
        <h3>{{ $t('footer.contactUs') }}</h3>
        <p>{{ $t('footer.address') }}<br>
          {{ $t('footer.addressLine1') }}<br>
          {{ $t('footer.addressLine2') }}<br>
          {{ $t('footer.addressLine3') }}</p>
        <p>{{ $t('footer.phone') }}</p>
        <p>{{ $t('footer.email') }}</p>
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
@media (max-width: 768px) {
  .footer {
    display: none;
  }
}
</style>
