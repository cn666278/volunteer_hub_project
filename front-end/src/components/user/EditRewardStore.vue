<template>
  <el-drawer
      size="40%"
      v-model="drawer"
      :title="formData.id ? $t('rewardStore.editTitle') : $t('rewardStore.addTitle')"
      direction="rtl"
      :before-close="handleClose"
  >
    <el-form
        size="small"
        ref="formRef"
        style="max-width: 600px"
        :model="formData"
        status-icon
        :rules="rules"
        label-width="120px"
    >
      <el-form-item :label="$t('rewardStore.itemName')" prop="itemName">
        <el-input v-model="formData.itemName" clearable />
      </el-form-item>

      <el-form-item :label="$t('rewardStore.pointsRequired')" prop="pointsRequired">
        <el-input-number v-model="formData.pointsRequired" :min="1" />
      </el-form-item>

      <el-form-item :label="$t('rewardStore.itemDescription')" prop="itemDescription">
        <el-input v-model="formData.itemDescription" clearable />
      </el-form-item>

      <el-form-item :label="$t('rewardStore.itemImage')">
        <input type="file" @change="onFileChange" />
        <el-input v-model="filename" :placeholder="$t('rewardStore.enterFileName')" />
        <!-- 显示文件预览图 -->
        <div v-if="uploadedFilePath">
          <img :src="uploadedFilePath" :alt="$t('rewardStore.filePreview')" style="max-width: 100px; max-height: 100px; margin-top: 10px;" />
        </div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm(formRef)">
          {{ formData.id ? $t('rewardStore.edit') : $t('rewardStore.add') }}
        </el-button>
        <el-button @click="handleResetForm(formRef)">{{ $t('rewardStore.reset') }}</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script setup lang="ts">
import { FormInstance, FormRules, ElNotification } from "element-plus";
import { getCurrentInstance, ref } from "vue";
import { useI18n } from 'vue-i18n'

const { t } = useI18n();
const { proxy }: any = getCurrentInstance();
// expose to parent component, so that the parent component can call the drawer
const emit = defineEmits(["update-item-list"]);
// drawer
const drawer = ref(false);
// 修改 handleOpen 方法
const handleOpen = (row: any) => {
  drawer.value = true;
  formData.value = { ...row };

  // 重置文件上传相关的状态
  file.value = null;
  filename.value = '';
  uploadedFilePath.value = '';

  if (formData.value.itemUrl) {
    fetchAndDisplayImage(formData.value.itemUrl); // 加载并显示图片
  }
};

const handleClose = () => {
  drawer.value = false;
  resetForm(formRef.value); // 重置表单

  // 重置文件上传相关的状态
  file.value = null;
  filename.value = '';
  uploadedFilePath.value = '';
};


const handleResetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
  // reset form data
  formData.value = {
    id: formData.value.id,
    itemName: "",
    pointsRequired: 0,
    itemDescription: "",
    itemUrl: ""
  };
};
// form ref
const formRef = ref<FormInstance>();
// form data
const formData = ref({
  id: null,
  itemName: "",
  pointsRequired: 0,
  itemDescription: "",
  itemUrl: "" // 用于存储文件ID或URL
});

// 文件上传处理
const file = ref(null);
const filename = ref('');
const uploadedFilePath = ref('');

const onFileChange = async (e: Event) => {
  const files = (e.target as HTMLInputElement).files;
  if (files && files.length > 0) {
    file.value = files[0];
    filename.value = file.value.name; // 设置文件名
    await uploadFile(); // 自动上传文件
  }
};

const uploadFile = async () => {
  if (!file.value || !filename.value.trim()) {
    alert(t('rewardStore.selectFileError'));
    return;
  }

  const uploadData = new FormData();  // 改名为 uploadData
  uploadData.append('file', file.value);
  uploadData.append('filename', filename.value.trim());

  try {
    const response = await proxy.$api.uploadFile(uploadData);
    const fileId = response.match(/\d+$/)[0];

    // 确保外部 formData.value 已经初始化为对象
    if (!formData.value) {
      formData.value = {};
    }

    // 使用解构赋值来更新 formData
    formData.value = {
      ...formData.value,  // 保持已有属性不变
      itemUrl: fileId    // 更新或添加 itemUrl 属性
    };

    await fetchAndDisplayImage(fileId); // 上传后立即获取并显示图片
    console.log("File uploaded successfully:", fileId);
  } catch (error) {
    console.error("File upload failed:", error);
  }
};


// 根据文件ID获取并显示图片
const fetchAndDisplayImage = async (fileId: string | number) => {
  try {
    const response = await proxy.$api.getfiles({ id: fileId });
    if (response) {
      const base64Data = response;
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });
      uploadedFilePath.value = URL.createObjectURL(blob);
    }
  } catch (error) {
    console.error('Error fetching file:', error);
  }
};

// validate item name
const validateItemName = (_: any, value: any, callback: any) => {
  if (value === "") {
    callback(new Error(t('rewardStore.validateItemName')));
  } else {
    callback();
  }
};
// form rules
const rules = ref<FormRules<typeof formData>>({
  itemName: [{ required: true, validator: validateItemName, trigger: "blur" }],
  pointsRequired: [{ required: true, type: 'number', min: 1, message: t('rewardStore.validatePointsRequired'), trigger: 'blur' }]
});
// submit form
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      let res;
      if (formData.value.id) {
        // edit
        res = await proxy.$api.updateRewardStoreItem(formData.value);
      } else {
        // add
        res = await proxy.$api.addRewardStoreItem(formData.value);
      }
      if (res) {
        console.log("res:", res);
        ElNotification({
          title: t('rewardStore.notificationTitle'),
          message: res,
          type: "success",
        });
        emit("update-item-list"); // update item list
        handleClose(); // close drawer
        console.log("success submit!");
      } else {
        ElNotification({
          title: t('rewardStore.notificationTitle'),
          message: res,
          type: "error",
        });
        console.log("error submit!");
      }
    } else {
      console.log("error submit!");
    }
  });
};
// reset form
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.resetFields();
  // reset form data
  formData.value = {
    id: null,
    itemName: "",
    pointsRequired: 0,
    itemDescription: "",
    itemUrl: ""
  };
};
// expose to parent component
defineExpose({
  drawer,
  formData,
  handleOpen,
});
</script>

<style lang="scss"></style>
