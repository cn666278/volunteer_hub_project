<template>
  <div class="Information">
    <div class="all-comments">
      <div class="custom-card">
        <!-- Form to Share Experience -->
        <el-form ref="shareForm" label-position="top">
          <el-form-item label="Share Your Experience">
            <!-- Textarea for entering content to share -->
            <el-input
                type="textarea"
                v-model="textToShare"
                placeholder="Enter content here..."
                rows="5">
            </el-input>
          </el-form-item>
          <el-form-item>
            <!-- Buttons to share content on social media -->
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
    const textToShare = ref(''); // Reactive variable to store text input
    const currentUrl = window.location.href; // Get the current page URL

    // Function to share content on Twitter
    function shareToTwitter() {
      const twitterUrl = `https://twitter.com/intent/tweet?url=${encodeURIComponent(currentUrl)}&text=${encodeURIComponent(textToShare.value)}`;
      window.open(twitterUrl, '_blank');
    }

    // Function to share content on Facebook
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
  transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
}

/* Ensure form items are displayed comfortably on smaller screens */
.el-form-item {
  width: 100%;
  margin-bottom: 20px;
}

/* Responsive design for smaller screens */
@media (max-width: 768px) {
  .custom-card {
    width: 100%; /* Card takes full width on small screens */
    padding: 15px;
  }

  .el-form-item {
    margin-bottom: 15px;
  }

  .el-button {
    width: 100%; /* Buttons take full width on small screens */
    margin-bottom: 10px;
  }
}

@media (max-width: 480px) {
  .custom-card {
    padding: 10px;
  }

  .el-input__inner {
    font-size: 14px; /* Adjust input font size for smaller screens */
  }
}
</style>
