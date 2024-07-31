<template>
  <div class="home">
    <el-form ref="ruleFormRef" style="margin:auto; max-width: 800px" :model="ruleForm" :rules="rules" label-width="auto"
      class="demo-ruleForm" :size="formSize" status-icon>
      <div class="image" style="display: flex; justify-content: center; margin-bottom: 20px;">
        <img src="../../assets/volunteer2.png" alt="volunteer" style="width: 800px;">
      </div>
      <el-form-item :label="$t('eventRegister.eventName')" prop="name">
        <el-input v-model="ruleForm.name" disabled />
      </el-form-item>
      <el-form-item :label="$t('eventRegister.eventDate')" prop="date">
        <el-input v-model="ruleForm.date" disabled />
      </el-form-item>
      <el-form-item :label="$t('eventRegister.volunteerDate')" prop="region">
        <el-select v-model="ruleForm.region" :placeholder="$t('eventRegister.volunteerDatePlaceholder')">
          <el-option :label="$t('eventRegister.dayOne')" value="day1" />
          <el-option :label="$t('eventRegister.dayTwo')" value="day2" />
          <el-option :label="$t('eventRegister.dayThree')" value="day3" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('eventRegister.dbs')" prop="abs" required>
        <el-radio-group v-model="ruleForm.resource">
          <el-radio :label="$t('eventRegister.yes')" value="yes">{{ $t('eventRegister.yes') }}</el-radio>
          <el-radio :label="$t('eventRegister.no')" value="no">{{ $t('eventRegister.no') }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="$t('eventRegister.qualifications')" prop="qualifications">
        <el-checkbox-group v-model="ruleForm.qualifications">
          <el-checkbox :label="$t('eventRegister.coachQualifications')" value="coach qualifications">
            {{ $t('eventRegister.coachQualifications') }}
          </el-checkbox>
          <el-checkbox :label="$t('eventRegister.firstAid')" value="first aid">
            {{ $t('eventRegister.firstAid') }}
          </el-checkbox>
          <el-checkbox :label="$t('eventRegister.previousExperience')" value="previous experience">
            {{ $t('eventRegister.previousExperience') }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="$t('eventRegister.volunteerRole')" prop="role">
        <el-checkbox-group v-model="ruleForm.role">
          <el-checkbox :label="$t('eventRegister.eventCoordinator')" value="Event Coordinator">
            {{ $t('eventRegister.eventCoordinator') }}
          </el-checkbox>
          <el-checkbox :label="$t('eventRegister.eventWelcomeDesk')" value="Event Welcome Desk">
            {{ $t('eventRegister.eventWelcomeDesk') }}
          </el-checkbox>
          <el-checkbox :label="$t('eventRegister.athleteRegistrationDesk')" value="Athlete Registration Desk">
            {{ $t('eventRegister.athleteRegistrationDesk') }}
          </el-checkbox>
          <el-checkbox :label="$t('eventRegister.transportOperations')" value="Transport Operations">
            {{ $t('eventRegister.transportOperations') }}
          </el-checkbox>
          <el-checkbox :label="$t('eventRegister.eventGreeter')" value="Event Greeter / Fan Experience">
            {{ $t('eventRegister.eventGreeter') }}
          </el-checkbox>
          <el-checkbox :label="$t('eventRegister.entertainmentCoordinator')" value="Entertainment Coordinator">
            {{ $t('eventRegister.entertainmentCoordinator') }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item :label="$t('eventRegister.note')" prop="note">
        <el-input v-model="ruleForm.note" type="textarea" />
      </el-form-item>
      <el-form-item style="display: flex;">
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          {{ $t('eventRegister.create') }}
        </el-button>
        <el-button @click="resetForm(ruleFormRef)">{{ $t('eventRegister.reset') }}</el-button>
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
  margin-bottom: 120px;
  color: #333;
}
</style>
