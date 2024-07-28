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
          <el-form-item label="Upload Images">
            <el-upload
                class="upload-demo"
                action="#"
                drag
                multiple
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :before-upload="beforeImageUpload">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">Drag files here, or <em>click to upload</em></div>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="shareToSocialMedia">Share to Social Media</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script lang='ts'>
import { ref } from 'vue';
import { ElInput, ElForm, ElFormItem, ElButton, ElUpload, ElDialog } from 'element-plus';

export default {
  components: {
    ElInput, ElForm, ElFormItem, ElButton, ElUpload, ElDialog
  },
  setup() {
    const textToShare = ref('');
    const fileList = ref([]);
    const dialogImageUrl = ref('');
    const dialogVisible = ref(false);

    function handlePictureCardPreview(file) {
      dialogImageUrl.value = file.url;
      dialogVisible.value = true;
    }

    function handleRemove(file, fileList) {
      console.log(file, fileList);
    }

    function beforeImageUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJPG) {
        this.$message.error('Only JPG or PNG files are allowed!');
      }
      return isJPG;
    }

    function shareToSocialMedia() {
      console.log(`Sharing: ${textToShare.value}`);
      console.log(`With images: ${fileList.value.length} images`);
      // Actual sharing logic should be implemented as per requirement
    }

    return { textToShare, fileList, dialogImageUrl, dialogVisible, handlePictureCardPreview, handleRemove, beforeImageUpload, shareToSocialMedia };
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
