<template>
  <div class="home">
    <el-form ref="ruleFormRef" style="margin:auto; max-width: 800px" :model="ruleForm" :rules="rules" label-width="auto"
      class="demo-ruleForm" :size="formSize" status-icon>
      <div class="image" style="display: flex; justify-content: center; margin-bottom: 20px;">
        <img src="../../assets/volunteer2.png" alt="volunteer" style="width: 800px;">
      </div>
      <el-form-item label="Event name" prop="name">
        <el-input v-model="ruleForm.name" disabled />
      </el-form-item>
      <el-form-item label="Event date" prop="date">
        <el-input v-model="ruleForm.date" disabled />
      </el-form-item>
      <el-form-item label="Volunteer date" prop="region">
        <el-select v-model="ruleForm.region" placeholder="Volunteer Date">
          <el-option label="Day one - 24 Aug" value="day1" />
          <el-option label="Day two - 25 Aug" value="day2" />
          <el-option label="Day three - 26 Aug" value="day3" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="Event count" prop="count">
        <el-select-v2 v-model="ruleForm.count" placeholder="Event count" :options="options" />
      </el-form-item> -->
      <el-form-item label="DBS" prop="abs" required>
        <el-radio-group v-model="ruleForm.resource">
          <el-radio value="yes">Yes</el-radio>
          <el-radio value="no">No</el-radio>
        </el-radio-group>
      </el-form-item>
      <!-- <el-form-item label="DBS" prop="dbs" required>
        <el-switch v-model="ruleForm.delivery" />
      </el-form-item> -->
      <el-form-item label="Qualifications" prop="qualifications">
        <el-checkbox-group v-model="ruleForm.qualifications">
          <el-checkbox value="coach qualifications" name="qualifications">
            coach qualifications
          </el-checkbox>
          <el-checkbox value="first aid" name="qualifications">
            first aid
          </el-checkbox>
          <el-checkbox value="previous experience" name="qualifications">
            previous experience
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <!-- <el-form-item label="Event location" prop="location">
        <el-segmented v-model="ruleForm.location" :options="locationOptions" />
      </el-form-item> -->
      <el-form-item label="Volunteer role" prop="role">
        <el-checkbox-group v-model="ruleForm.role">
          <el-checkbox value="Event Coordinator" name="role">
        Event Coordinator
          </el-checkbox>
          <el-checkbox value="Event Welcome Desk" name="role">
        Event Welcome Desk
          </el-checkbox>
          <el-checkbox value="Athlete Registration Desk" name="role">
        Athlete Registration Desk
          </el-checkbox>
          <el-checkbox value="Transport Operations" name="role">
        Transport Operations
          </el-checkbox>
          <el-checkbox value="Event Greeter / Fan Experience" name="role">
        Event Greeter / Fan Experience
          </el-checkbox>
          <el-checkbox value="Entertainment Coordinator" name="role">
        Entertainment Coordinator
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="Note" prop="note">
        <el-input v-model="ruleForm.note" type="textarea" />
      </el-form-item>
      <el-form-item style="display: flex;">
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          Create
        </el-button>
        <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  name: string
  region: string
  count: string
  date1: string
  date2: string
  dbs: boolean
  location: string
  qualifications: string[]
  role: string[]
  resource: string
  note: string
}

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  name: 'Table Tennis Wales hosting the Senior Team Championships of Wales',
  region: '',
  count: '',
  date: 'Aug 24-26, 2024',
  date1: '',
  date2: '',
  dbs: false,
  location: '',
  qualifications: [],
  role: [],
  resource: '',
  note: '',
})

const locationOptions = ['Home', 'Company', 'School']

const rules = reactive<FormRules<RuleForm>>({
  region: [
    {
      required: true,
      message: 'Please select Event zone',
      trigger: 'change',
    },
  ],
  count: [
    {
      required: true,
      message: 'Please select Event count',
      trigger: 'change',
    },
  ],
  location: [
    {
      required: true,
      message: 'Please select a location',
      trigger: 'change',
    },
  ],
  qualifications: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one qualification',
      trigger: 'change',
    },
  ],
  role: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one event type',
      trigger: 'change',
    },
  ],
  resource: [
    {
      required: true,
      message: 'Please select event resource',
      trigger: 'change',
    },
  ],
  note: [
    { required: false, message: 'Please input note', trigger: 'blur' },
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const options = Array.from({ length: 10000 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
</script>

<style>
.home {
  padding-top: 20px;
  width: 95%;
  max-width: 1200px;
  margin: auto;
  margin-bottom: 50px;
  color: #333;
}
</style>