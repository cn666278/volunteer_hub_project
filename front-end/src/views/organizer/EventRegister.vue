<template>
  <div>
    <el-form :model="form" ref="formRef" label-width="120px">
      <el-form-item label="Title" prop="title">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="Description" prop="description">
        <el-input v-model="form.description"></el-input>
      </el-form-item>
      <el-form-item label="Location" prop="location">
        <el-input id="autocomplete" v-model="form.location" @focus="openMap"></el-input>
      </el-form-item>
      <el-form-item label="Points Awarded" prop="pointsAwarded">
        <el-input-number v-model="form.pointsAwarded"></el-input-number>
      </el-form-item>
      <el-form-item label="Start Date" prop="startDate">
        <el-date-picker
            v-model="form.startDate"
            type="datetime"
            placeholder="choose start date">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="End Date" prop="endDate">
        <el-date-picker
            v-model="form.endDate"
            type="datetime"
            placeholder="choose end date">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="Role" prop="roles" required>
        <div v-for="role in availableRoles" :key="role" style="display: flex; align-items: center; margin-bottom: 8px;">
          <el-checkbox :label="role" v-model="form.roles">{{ role }}</el-checkbox>
          <el-input-number v-if="form.roles.includes(role)" v-model="form.rolesQuantities[role]" :min="1" style="margin-left: 8px;"></el-input-number>
        </div>
      </el-form-item>
      <el-form-item label="Nearby Facilities" prop="nearbyFacilities">
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
        <el-button type="primary" @click="submitForm">Register</el-button>
        <el-button @click="resetForm">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang='ts'>
import { ref, reactive, getCurrentInstance, onMounted } from 'vue';
import { ElMessage, ElForm } from 'element-plus';

const { proxy } = getCurrentInstance();

const formRef = ref<InstanceType<typeof ElForm>>();
const form = reactive({
  title: '',
  description: '',
  location: '',
  pointsAwarded: 0,
  startDate: '',
  endDate: '',
  roles: [],
  rolesQuantities: {} as Record<string, number>,
  nearbyFacilities: []
});

const availableRoles = ['role1', 'role2', 'role3', 'role4', 'role5', 'role6'];
const nearbyFacilities = ref<any[]>([]);
//
const submitForm = () => {
  const roles = form.roles.map(role => ({
    role,
    quantity: form.rolesQuantities[role] || 1
  }));

  const payload = {
    organizerId: 1,
    title: form.title,
    description: form.description,
    location: form.location,
    pointsAwarded: form.pointsAwarded,
    startDate: form.startDate,
    endDate: form.endDate,
    roles: roles,
    nearbyFacilities: form.nearbyFacilities
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

onMounted(() => {
  if (window.google) {
    openMap();
  } else {
    window.addEventListener('load', openMap);
  }
});
</script>

<style scoped>
</style>
