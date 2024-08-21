<template>
  <div>
    <el-form :model="form" ref="formRef" label-width="180px">
      <el-form-item :label="$t('eventRegistration.title')" prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.description')" prop="description">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.location')" prop="location">
        <el-input id="autocomplete" v-model="form.location" @focus="openMap"></el-input>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.file')">
        <input type="file" @change="onFileChange" />
        <el-input v-model="filename" :placeholder="$t('eventRegistration.enterFileName')" />
        <!-- 显示文件预览图 -->
        <div v-if="uploadedFilePath">
          <img :src="uploadedFilePath" :alt="$t('eventRegistration.filePreview')" style="max-width: 100px; max-height: 100px; margin-top: 10px;" />
        </div>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.pointsAwarded')" prop="pointsAwarded">
        <el-input-number v-model="form.pointsAwarded"></el-input-number>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.startDate')" prop="startDate">
        <el-date-picker
            v-model="form.startDate"
            type="datetime"
            :placeholder="$t('eventRegistration.startDate')">
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.endDate')" prop="endDate">
        <el-date-picker
            v-model="form.endDate"
            type="datetime"
            :placeholder="$t('eventRegistration.endDate')">
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.roles')" prop="roles">
        <div v-for="role in availableRoles" :key="role" style="display: flex; align-items: center; margin-bottom: 8px;">
          <el-checkbox :label="role" v-model="form.roles">{{ role }}</el-checkbox>
          <el-input-number v-if="form.roles.includes(role)" v-model="form.rolesQuantities[role]" :min="1" style="margin-left: 8px;"></el-input-number>
        </div>
      </el-form-item>
      <el-form-item :label="$t('eventRegistration.nearbyFacilities')" prop="nearbyFacilities">
        <el-select v-model="form.nearbyFacilities" multiple>
          <el-option
              v-for="facility in nearbyFacilities"
              :key="facility.place_id"
              :label="facility.name"
              :value="facility.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">{{ $t('eventRegistration.register') }}</el-button>
        <el-button @click="resetForm">{{ $t('eventRegistration.reset') }}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang='ts'>
import { ref, reactive, getCurrentInstance, onMounted } from 'vue';
import { useI18n } from 'vue-i18n';
import { ElMessage, ElForm } from 'element-plus';
import useUser from "../../store/user";
let userStore = useUser();
const { proxy } = getCurrentInstance();
const { t } = useI18n();

const formRef = ref<InstanceType<typeof ElForm>>();
const form = reactive({
  title: '',
  description: '',
  location: '',
  pointsAwarded: 0,
  startDate: '',
  endDate: '',
  roles: [] as string[],
  rolesQuantities: {} as Record<string, number>,
  nearbyFacilities: [],
  fileIds: [] as number[] // 存储文件ID
});

const availableRoles = ['Default', 'Event Coordinator', 'Event Welcome Desk', 'Athlete Registration Desk', 'Transport Operations', 'Event Greeter / Fan Experience', 'Entertainment Coordinator'];
const nearbyFacilities = ref<any[]>([]);

const file = ref(null);
const filename = ref('');
const uploadedFilePath = ref('');

const onFileChange = async (e) => {
  file.value = e.target.files[0];
  filename.value = file.value.name; // 设置文件名
  await uploadFile(); // 自动上传文件
};

const uploadFile = async () => {
  if (!file.value || !filename.value.trim()) {
    alert(t('eventRegistration.selectFileError'));
    return;
  }

  const formData = new FormData();
  formData.append('file', file.value);
  formData.append('filename', filename.value.trim());

  try {
    const response = await proxy.$api.uploadFile(formData);
    const fileId = response.match(/\d+$/)[0];
    form.fileIds.push(fileId);
    await fetchAndDisplayImage(fileId); // 上传后立即获取并显示图片
    console.log("File uploaded successfully:", response);
  } catch (error) {
    console.error("File upload failed:", error);
  }
};

const fetchAndDisplayImage = async (fileId: number) => {
  try {
    const response = await proxy.$api.getfiles({ id: fileId });
    if (response) {
      const base64Data = response;
      // 假设从服务器返回的是包含图像数据的字段
      const mimeType = response.mimeType || 'image/jpeg'; // 从响应中获取MIME类型，默认值为'jpeg'
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: mimeType }); // 使用动态MIME类型
      uploadedFilePath.value = URL.createObjectURL(blob);
    }
  } catch (error) {
    console.error("Error fetching file", error);
  }
};

const submitForm = () => {
  const roles = form.roles.map(role => ({
    role,
    quantity: form.rolesQuantities[role] || 1
  }));

  const payload = {
    organizerId: userStore.user.id,
    title: form.title,
    description: form.description,
    location: form.location,
    pointsAwarded: form.pointsAwarded,
    startDate: form.startDate,
    endDate: form.endDate,
    roles: roles,
    nearbyFacilities: form.nearbyFacilities,
    fileIds: form.fileIds
  };
  console.log("submitForm payload:",payload)
  proxy.$api.registerEvent(payload)
      .then(response => {
        ElMessage.success(response);
        resetForm();
      })
      .catch(error => {
        ElMessage.error(error.message);
      });
};

const resetForm = () => {
  form.title = '';
  form.description = '';
  form.location = '';
  form.pointsAwarded = 0;
  form.startDate = '';
  form.endDate = '';
  form.roles = [];
  form.rolesQuantities = {};
  form.nearbyFacilities = [];
  form.fileIds = [];
  uploadedFilePath.value = '';
};

const openMap = () => {
  if (!window.google) {
    console.error('Google Maps API not loaded');
    return;
  }

  const input = document.getElementById('autocomplete') as HTMLInputElement;
  const autocomplete = new google.maps.places.Autocomplete(input, { types: ['geocode'] });

  autocomplete.addListener('place_changed', () => {
    const place = autocomplete.getPlace();
    if (place.geometry) {
      form.location = place.formatted_address || '';
      fetchNearbyFacilities(place.geometry.location);
    } else {
      form.location = '';
      nearbyFacilities.value = [];
    }
  });
};

const fetchNearbyFacilities = (location) => {
  const service = new google.maps.places.PlacesService(document.createElement('div'));

  const request = {
    location: location,
    radius: '1500',
    types: ['hospital', 'restaurant']
  };

  service.nearbySearch(request, (results, status) => {
    if (status === google.maps.places.PlacesServiceStatus.OK) {
      nearbyFacilities.value = results.map(place => ({
        place_id: place.place_id,
        name: place.name
      }));
      form.nearbyFacilities = nearbyFacilities.value.map(facility => facility.name);
    } else {
      console.error('PlacesService failed:', status);
      nearbyFacilities.value = [];
      form.nearbyFacilities = [];
    }
  });
};

onMounted(async () => {
  if (window.google) {
    openMap();
  } else {
    window.addEventListener('load', openMap);
  }
});
</script>

<style scoped>
</style>
