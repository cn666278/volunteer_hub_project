<template>
  <div>
    <el-form :model="form" ref="formRef" label-width="180px">
      <el-form-item :label="t('editEvent.title')" prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item :label="t('editEvent.description')" prop="description">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item :label="t('editEvent.location')" prop="location">
        <el-input id="autocomplete" v-model="form.location" @focus="openMap"></el-input>
      </el-form-item>
      <el-form-item :label="t('editEvent.file')" prop="file">
        <input type="file" @change="onFileChange" />
        <el-input v-model="filename" :placeholder="t('editEvent.enterFileName')" />
        <div v-if="uploadedFilePath">
          <img :src="imagePreviewUrl" :alt="t('editEvent.filePreview')" style="max-width: 200px; margin-top: 10px;" />
        </div>
      </el-form-item>
      <el-form-item :label="t('editEvent.pointsAwarded')" prop="pointsAwarded">
        <el-input-number v-model="form.pointsAwarded"></el-input-number>
      </el-form-item>
      <el-form-item :label="t('editEvent.startDate')" prop="startDate">
        <el-date-picker
          v-model="form.startDate"
          type="datetime"
          :placeholder="t('editEvent.chooseStartDate')"
        ></el-date-picker>
      </el-form-item>
      <el-form-item :label="t('editEvent.endDate')" prop="endDate">
        <el-date-picker
          v-model="form.endDate"
          type="datetime"
          :placeholder="t('editEvent.chooseEndDate')"
        ></el-date-picker>
      </el-form-item>
      <el-form-item :label="t('editEvent.roles')" prop="roles">
        <div v-for="role in availableRoles" :key="role" style="display: flex; align-items: center; margin-bottom: 8px;">
          <el-checkbox :label="role" v-model="form.roles">{{ role }}</el-checkbox>
          <el-input-number v-if="form.roles.includes(role)" v-model="form.rolesQuantities[role]" :min="1" style="margin-left: 8px;"></el-input-number>
        </div>
      </el-form-item>
      <el-form-item :label="t('editEvent.nearbyFacilities')" prop="nearbyFacilities">
        <el-select v-model="form.nearbyFacilities" multiple>
          <el-option
            v-for="facility in nearbyFacilities"
            :key="facility.place_id"
            :label="facility.name"
            :value="facility.name"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">{{ t('editEvent.save') }}</el-button>
        <el-button @click="resetForm">{{ t('editEvent.reset') }}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, getCurrentInstance, onMounted } from 'vue';
import { useI18n } from 'vue-i18n';
import { ElMessage, ElForm } from 'element-plus';
import { translateText } from '../../api/translate';
import { useRoute } from 'vue-router';
import { nextTick } from 'vue';
const { t } = useI18n();
const { proxy } = getCurrentInstance();
const route = useRoute();
import useUser from "../../store/user";
// user store
const userStore = useUser();
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
  fileIds: [] as number[]
});

const availableRoles = ['Default', 'Event Coordinator', 'Event Welcome Desk', 'Athlete Registration Desk', 'Transport Operations', 'Event Greeter / Fan Experience', 'Entertainment Coordinator'];
const nearbyFacilities = ref<any[]>([]);

const translatedLabels = reactive({
  title: t('editEvent.title'),
  description: t('editEvent.description'),
  location: t('editEvent.location'),
  file: t('editEvent.file'),
  pointsAwarded: t('editEvent.pointsAwarded'),
  startDate: t('editEvent.startDate'),
  endDate: t('editEvent.endDate'),
  roles: t('editEvent.roles'),
  nearbyFacilities: t('editEvent.nearbyFacilities'),
  save: t('editEvent.save'),
  reset: t('editEvent.reset')
});

const file = ref(null);
const filename = ref('');
const uploadedFilePath = ref('');
const imagePreviewUrl = ref('');

const onFileChange = (e) => {
  file.value = e.target.files[0];
  uploadFile(); // Automatically upload the file on change
};

const uploadFile = async () => {
  if (!file.value) {
    return;
  }

  const formData = new FormData();
  formData.append('file', file.value);
  formData.append('filename', filename.value.trim());

  try {
    const response = await proxy.$api.uploadFile(formData);
    const fileId = response.match(/\d+$/)[0];
    form.fileIds.push(fileId);
    uploadedFilePath.value = `/files/${fileId}`;
    console.log("Before assignment:", imagePreviewUrl.value);
    imagePreviewUrl.value = await fetchAndDisplayImage(fileId);
    console.log("After assignment:", imagePreviewUrl.value);
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
      const byteCharacters = atob(base64Data);
      const byteNumbers = new Array(byteCharacters.length);
      for (let i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      const byteArray = new Uint8Array(byteNumbers);
      const blob = new Blob([byteArray], { type: 'image/jpeg' });
      console.log("blob", blob);
      return URL.createObjectURL(blob); // 确保这里返回的是URL
    } else {
      console.error("Failed to fetch the image data.");
      return '';
    }
  } catch (error) {
    console.error("Error fetching file", error);
    return '';
  }
};

const submitForm = () => {
  const roles = form.roles.map(role => ({
    role,
    quantity: form.rolesQuantities[role] || 1
  }));

  const payload = {
    eventId: eventId,
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
  console.log("submitForm payload:", payload)
  proxy.$api.editEventById(payload)
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
  imagePreviewUrl.value = '';
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

// Function to translate labels
const translateLabels = async (language: string) => {
  const labels = Object.keys(translatedLabels);
  const translations = await Promise.all(
      labels.map(label => translateText(translatedLabels[label], language))
  );
  labels.forEach((label, index) => {
    translatedLabels[label] = translations[index];
  });
};
let eventId = 0;
const loadEventData = async (eventData) => {
  eventId = eventData.eventId;
  form.title = eventData.title;
  form.description = eventData.description;
  form.location = eventData.location;
  form.pointsAwarded = eventData.pointsAwarded;
  form.startDate = eventData.startDate;
  form.endDate = eventData.endDate;
  if (eventData.roles > 0) {}
  form.roles = eventData.roles.map(role => role.role);
  form.rolesQuantities = eventData.roles.reduce((acc, role) => {
    acc[role.role] = role.quantity;
    return acc;
  }, {});
  form.nearbyFacilities = eventData.nearbyFacilities;
  eventData.fileIds = [Number(eventData.eventPic)]
  form.fileIds = eventData.fileIds;
  if (form.fileIds.length > 0) {
    uploadedFilePath.value = `/files/${Number(eventData.eventPic)}`;
    console.log("Before assignment:", imagePreviewUrl.value);
    imagePreviewUrl.value = await fetchAndDisplayImage(form.fileIds[0]);
    console.log("After assignment:", imagePreviewUrl.value);
  }
};

onMounted(async () => {
  const event = JSON.parse(route.query.event);
  await loadEventData(event);

  if (window.google) {
    openMap();
  } else {
    window.addEventListener('load', openMap);
  }

  const selectedLanguage = localStorage.getItem('selectedLanguage') || 'en';
  await translateLabels(selectedLanguage);
});
</script>

<style scoped>
</style>
