<template>
  <div class="Information">
    <div class="all-comments">
      <div class="custom-card">
        <el-form ref="shareForm" label-position="top">
          <el-form-item label="Share Your Experience">
            <el-input
                type="textarea"
                v-model="textToShare"
                placeholder="Enter content here..."
                rows="5">
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="shareToTwitter">Share to Twitter</el-button>
            <el-button type="primary" @click="shareToFacebook">Share to Facebook</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script lang='ts'>
import { ref } from 'vue';
import { ElInput, ElForm, ElFormItem, ElButton } from 'element-plus';

export default {
  components: {
    ElInput, ElForm, ElFormItem, ElButton
  },
  setup() {
    const textToShare = ref('');
    const currentUrl = window.location.href; // 当前页面的URL

    function shareToTwitter() {
      const twitterUrl = `https://twitter.com/intent/tweet?url=${encodeURIComponent(currentUrl)}&text=${encodeURIComponent(textToShare.value)}`;
      window.open(twitterUrl, '_blank');
    }

    function shareToFacebook() {
      const facebookUrl = `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(currentUrl)}&quote=${encodeURIComponent(textToShare.value)}`;
      window.open(facebookUrl, '_blank');
    }

    return {
      textToShare,
      shareToTwitter,
      shareToFacebook
    };
  }
}
</script>

<style lang='scss'>
.all-comments {
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.custom-card {
  width: 80%;
  border: 1px solid #ccc;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
