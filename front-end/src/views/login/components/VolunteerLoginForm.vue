<template>
    <el-form size="default" ref="formRef" style="max-width: 600px" :model="formData" status-icon :rules="rules"
        label-width="80px">
        <div class="form-header">
            <p>Volunteer Login</p>
        </div>
        <el-form-item label="Username" prop="username">
            <el-input v-model="formData.username" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
            <el-input v-model="formData.password" type="password" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm(formRef)">
                Login
            </el-button>
            <el-button @click="resetForm(formRef)">Reset</el-button>
            <el-button type="" text="plain" style="margin-left: 80px;">Forget Password?</el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import { getCurrentInstance, onMounted, reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { useRouter } from "vue-router";
import useUser from "../../../store/user.ts";
import { ElNotification } from "element-plus";

// proxy是一个特殊的对象，它包含了当前组件的所有属性和方法
const { proxy }: any = getCurrentInstance();

// form instance
const formRef = ref<FormInstance>();

// router instance
const router = useRouter();

// user store
const userStore = useUser();

// form data
const formData = reactive({
    username: "",
    password: "",
});

// validate username
const validateUsername = (_: any, value: any, callback: any) => {
    if (value === "") {
        callback(new Error("Please enter the username"));
    } else {
        callback();
    }
};

// validate password
const validatePassword = (_: any, value: any, callback: any) => {
    if (value === "") {
        callback(new Error("Please enter the password"));
    } else {
        callback();
    }
};

// submit form rules
const rules = reactive<FormRules<typeof formData>>({
    username: [{ validator: validateUsername, trigger: "blur" }],
    password: [{ validator: validatePassword, trigger: "blur" }],
});

// submit form
const submitForm = (formRef: FormInstance | undefined) => {
    if (!formRef) return;
    formRef.validate(async (valid) => {
        try {
            console.log("Validation result:", valid);
            if (valid) {
                try {
                    console.log("Validation passed, submitting form data:", formData);
                    // let res = await proxy.$api.login({ id: 3, formData }); // muti-roles
                    let res = await proxy.$api.login(formData);
                    console.log("Login response:", res);

                    if (res) {
                        ElNotification({
                            title: "Notification",
                            message: res.message,
                            type: "success",
                        });

                        console.log("Login token:", res.token);
                        sessionStorage.setItem("token", res.token);

                        let user = await proxy.$api.getLoginUserInfo({ username: formData.username });
                        console.log("User info:", user);

                        userStore.setUser(user);
                        console.log("Login success");

                        if (user.role.roleName === "volunteer") {
                            // jump to Main home page
                            router.push("/volunteer");
                        } else if (user.role.roleName === "admin") {
                            // jump to Admin home page
                            router.push("/admin");
                        } else if (user.role.roleName === "organizer") {
                            // jump to Organizer home page
                            router.push("/organizer");
                        } else {
                            console.log("Role error");
                        }
                    } else {
                        ElNotification({
                            title: "Notification",
                            message: res.message,
                            type: "error",
                        });
                        console.log("Login failed");
                    }
                } catch (error) {
                    console.error("Error during login:", error);
                }
            } else {
                console.log("Form validation failed");
            }
        } catch (error) {
            console.error("Unexpected error during form validation:", error);
        }
    });

};

// reset form
const resetForm = (formRef: FormInstance | undefined) => {
    if (!formRef) return;
    formRef.resetFields();
};

// check if the user is logged in, if not, redirect to the home page
onMounted(() => {
    if (userStore.user.username) {
        if (userStore.user.role.roleName == "volunteer") {
            router.push("/volunteer");
        } else if (userStore.user.role.roleName == "admin") {
            router.push("/admin");
        } else if (userStore.user.role.roleName == "organizer") {
            router.push("/organizer");
        } else {
            console.log("role error");
        }
    }
});
</script>

<style lang="less" scoped>
.form-header {
    color: white;
    font-size: 16px;
    font-weight: 800;
    margin-bottom: 30px;
}

:deep(.el-button.is-text) {
    color: white;
}

:deep(.el-form-item__error) {
    color: white;
}

.el-form-item--default {
    margin-bottom: 30px;
}
</style>