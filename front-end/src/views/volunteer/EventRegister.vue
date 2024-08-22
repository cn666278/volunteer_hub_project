<template>
  <div class="home">
    <!-- Event registration form using Element Plus form component -->
    <el-form
        ref="ruleFormRef"
        style="margin:auto; max-width: 800px"
        :model="ruleForm"
        :rules="rules"
        label-width="auto"
        class="demo-ruleForm"
        :size="formSize"
        status-icon
    >
      <!-- Display event name (disabled input) -->
      <el-form-item :label="$t('eventRegister.eventName')" prop="name">
        <el-input v-model="ruleForm.name" disabled />
      </el-form-item>

      <!-- Display event date (disabled input) -->
      <el-form-item :label="$t('eventRegister.eventDate')" prop="date">
        <el-input v-model="ruleForm.date" disabled />
      </el-form-item>

      <!-- Volunteer role selection using checkboxes -->
      <el-form-item :label="$t('eventRegister.volunteerRole')" prop="role">
        <el-checkbox-group v-model="ruleForm.role">
          <!-- Individual roles as checkboxes -->
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

      <!-- Form submission and reset buttons -->
      <el-form-item style="display: flex;">
        <!-- Submit button triggers form validation and submission -->
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          Submit
        </el-button>
        <!-- Reset button resets the form fields -->
        <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'

// Interface defining the structure of the form data
interface RuleForm {
  name: string
  date: string
  role: string[]
}

// Reactive reference for form size, defaulting to 'default'
const formSize = ref<ComponentSize>('default')

// Reference for the form instance
const ruleFormRef = ref<FormInstance>()

// Reactive object holding the form data
const ruleForm = reactive<RuleForm>({
  name: 'Table Tennis Wales hosting the Senior Team Championships of Wales',
  date: 'Aug 24-26, 2024',
  role: [],
})

// Validation rules for the form
const rules = reactive<FormRules<RuleForm>>({
  role: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one event role',
      trigger: 'change',
    },
  ],
})

// Function to submit the form after validating it
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return

  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('Form submission successful!')
    } else {
      console.log('Form submission error!', fields)
    }
  })
}

// Function to reset the form fields to their initial state
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style>
/* Styling for the home component */
.home {
  padding-top: 20px;
  width: 95%;
  max-width: 1200px;
  margin: auto;
  margin-bottom: 120px;
  color: #333;
}
</style>
