<template>
  <div class="home">
    <el-form ref="ruleFormRef" style="margin:auto; max-width: 800px" :model="ruleForm" :rules="rules" label-width="auto"
             class="demo-ruleForm" :size="formSize" status-icon>
      <el-form-item :label="$t('eventRegister.eventName')" prop="name">
        <el-input v-model="ruleForm.name" disabled />
      </el-form-item>
      <el-form-item :label="$t('eventRegister.eventDate')" prop="date">
        <el-input v-model="ruleForm.date" disabled />
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
      <el-form-item style="display: flex;">
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          Submit
        </el-button>
        <el-button @click="resetForm(ruleFormRef)">Submit</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  name: string
  date: string
  role: string[]
}

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  name: 'Table Tennis Wales hosting the Senior Team Championships of Wales',
  date: 'Aug 24-26, 2024',
  role: [],
})

const rules = reactive<FormRules<RuleForm>>({
  role: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one event type',
      trigger: 'change',
    },
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
