<template>
  <div class="home">
    <!-- Event Registration Form -->
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
      <!-- Event Name - Displayed but not editable -->
      <el-form-item :label="$t('eventRegister.eventName')" prop="name">
        <el-input v-model="ruleForm.name" disabled />
      </el-form-item>

      <!-- Event Date - Displayed but not editable -->
      <el-form-item :label="$t('eventRegister.eventDate')" prop="date">
        <el-input v-model="ruleForm.date" disabled />
      </el-form-item>

      <!-- Volunteer Role Selection -->
      <el-form-item :label="$t('eventRegister.volunteerRole')" prop="role">
        <!-- Checkbox group for selecting volunteer roles -->
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

      <!-- Form Action Buttons -->
      <el-form-item style="display: flex;">
        <!-- Button to submit the form -->
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          Submit
        </el-button>
        <!-- Button to reset the form -->
        <el-button @click="resetForm(ruleFormRef)">Reset</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'

// Interface to define the structure of form data
interface RuleForm {
  name: string
  date: string
  role: string[]
}

const formSize = ref<ComponentSize>('default') // Form size, default is 'default'
const ruleFormRef = ref<FormInstance>() // Reference to the form instance for validation and reset
const ruleForm = reactive<RuleForm>({
  name: 'Table Tennis Wales hosting the Senior Team Championships of Wales', // Event name
  date: 'Aug 24-26, 2024', // Event date
  role: [], // Selected roles for volunteers
})

// Validation rules for the form
const rules = reactive<FormRules<RuleForm>>({
  role: [
    {
      type: 'array', // Ensures the 'role' field is an array
      required: true, // Field is required
      message: 'Please select at least one event type', // Error message if validation fails
      trigger: 'change', // Validation is triggered on change
    },
  ],
})

// Function to handle form submission
const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!') // Log success if form is valid
    } else {
      console.log('error submit!', fields) // Log validation errors
    }
  })
}

// Function to reset the form fields
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields() // Reset the form fields to their initial values
}
</script>

<style>
.home {
  padding-top: 20px; /* Top padding for the form container */
  width: 95%; /* Width of the container */
  max-width: 1200px; /* Maximum width to ensure responsiveness */
  margin: auto; /* Center the form container horizontally */
  margin-bottom: 120px; /* Bottom margin for spacing */
  color: #333; /* Text color */
}
</style>
