<template>
  <el-drawer
    size="30%"
    v-model="drawer"
    :title="formData.id ? $t('user.drawerTitleEdit') : $t('user.drawerTitleAdd')"
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
      label-width="70px"
    >
      <el-form-item v-if="!formData.id" :label="$t('user.form.loginId')" prop="loginId">
        <el-input v-model="formData.loginId" />
      </el-form-item>
      <el-form-item v-if="!formData.id" :label="$t('user.form.password')" prop="password">
        <el-input type="password" v-model="formData.password" />
      </el-form-item>
      <el-form-item :label="$t('user.form.username')" prop="username">
        <el-input v-model="formData.username" clearable />
      </el-form-item>
      <el-form-item :label="$t('user.form.photo')" prop="photo">
        <el-upload
          class="avatar-uploader"
          :action="config.mockApi + 'user/uploadImg'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img v-if="formData.photo" :src="formData.photo" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item :label="$t('user.form.roleId')" prop="roleId">
        <el-select
          v-model="formData.roleName"
          :placeholder="$t('user.form.selectRole')"
          style="width: 240px"
        >
          <el-option
            v-for="item in roleList"
            :key="item.roleId"
            :label="item.roleName"
            :value="item.roleId"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('user.form.phone')" prop="phone">
        <el-input v-model="formData.phone" clearable />
      </el-form-item>
      <el-form-item :label="$t('user.form.email')" prop="email">
        <el-input v-model="formData.email" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(formRef)">
          {{ formData.id ? $t('user.form.submitEdit') : $t('user.form.submitAdd') }}
        </el-button>
        <el-button @click="handleClose">{{ $t('user.form.cancel') }}</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script setup lang="ts">
import {
  FormInstance,
  FormRules,
  ElNotification,
  UploadProps,
} from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { getCurrentInstance, onMounted, ref } from "vue";
import config from "../../config/index";
import { useI18n } from 'vue-i18n'

const { t } = useI18n();
const { proxy }: any = getCurrentInstance();

// expose to parent component, so that the parent component can call the drawer
const emit = defineEmits(["update-user-list"]);

// drawer
const drawer = ref(false);

// open drawer
const handleOpen = (row: any) => {
  drawer.value = true;
  formData.value = { ...row };
  console.log(formData.value);
  formData.value.roleName = row.role.roleName;
  formData.value.roleId = row.role.roleId;
};

// close drawer
const handleClose = () => {
  drawer.value = false;
  resetForm(formRef.value);
};

// form ref
const formRef = ref<FormInstance>();

// form data
const formData = ref({
  id: null,
  loginId: null,
  roleName: "",
  roleId: null,
  username: "",
  password: "",
  photo: "",
  phone: "",
  email: "",
});

// role list
const roleList: any = ref([]);

// get role list
const getRoleList = async () => {
  let res = await proxy.$api.getRoleList();
  console.log("Load role list");
  roleList.value = res;
};

// validate loginId
const validateLoginId = (_: any, value: any, callback: any) => {
  if (value === "" || value === undefined) {
    callback(new Error(t('user.validate.loginId')));
  } else {
    callback();
  }
};

// validate password
const validatePassword = (_: any, value: any, callback: any) => {
  if (value === "" || value === undefined) {
    callback(new Error(t('user.validate.password')));
  } else if (value.length < 6) {
    callback(new Error(t('user.validate.passwordLengthMin')));
  } else if (value.length > 20) {
    callback(new Error(t('user.validate.passwordLengthMax')));
  } else {
    callback();
  }
};

// validate email
const validateEmail = (_: any, value: any, callback: any) => {
  if (value === "" || value === undefined) {
    callback(new Error(t('user.validate.emailEmpty')));
  } else if (
    !/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value)
  ) {
    callback(new Error(t('user.validate.emailFormat')));
  } else {
    callback();
  }
};

// form rules
const rules = ref<FormRules<typeof formData>>({
  loginId: [{ validator: validateLoginId, required: true, trigger: "blur" }],
  password: [{ validator: validatePassword, required: true, trigger: "blur" }],
  username: [{ required: true, message: t('user.validate.username'), trigger: "blur" }],
  roleName: [{ required: true, message: t('user.validate.roleId'), trigger: "change" }],
  phone: [{ required: true, message: t('user.validate.phone'), trigger: "blur" }],
  email: [{ validator: validateEmail, required: true, trigger: "blur" }],
});

// submit form
const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      let res: any = {};
      if (formData.value.id) {
        // edit
        res = await proxy.$api.updateUser(formData.value);
      } else {
        // add
        res = await proxy.$api.addUser(formData.value);
      }
      if (res) {
        ElNotification({
          title: t('user.notification.title'),
          message: res,
          type: "success",
        });
        emit("update-user-list"); // update user list
        handleClose(); // close drawer
        console.log("success submit!");
      } else {
        ElNotification({
          title: t('user.notification.title'),
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
  formData.value = {
    id: null,
    loginId: null,
    username: "",
    password: "",
    photo: "",
    phone: "",
    email: "",
    roleName: "",
    roleId: null,
  };
};

// upload avatar success
const handleAvatarSuccess: UploadProps["onSuccess"] = (
  response,
  uploadFile
) => {
  if (response) {
    ElNotification({
      title: t('user.notification.title'),
      message: t('user.notification.uploadPhotoSuccess'),
      type: "success",
    });
    // get the uploaded file name
    // formData.value.photo = data.filename;

    // mock photo url
    formData.value.photo =
      "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";
  }
  // formData.value.photo = URL.createObjectURL(uploadFile.raw!);
};

// before upload avatar
const beforeAvatarUpload: UploadProps["beforeUpload"] = (rawFile) => {
  // image type
  let imgTypes = ["image/jpeg", "image/jpg", "image/png", "image/gif"];
  if (!imgTypes.includes(rawFile.type)) {
    ElNotification({
      title: t('user.notification.title'),
      message: t('user.notification.uploadPhotoErrorType'),
      type: "error",
    });
    return false;
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElNotification({
      title: t('user.notification.title'),
      message: t('user.notification.uploadPhotoErrorSize'),
      type: "error",
    });
    return false;
  }
  return true;
};

// load role list
onMounted(() => {
  getRoleList();
});

// expose to parent component
defineExpose({
  drawer,
  formData,
  handleOpen,
});
</script>

<style lang="scss">
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 150px;
  height: 150px;
  text-align: center;
}
</style>
