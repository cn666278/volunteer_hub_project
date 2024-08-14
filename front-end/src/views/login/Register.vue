<template>
    <el-form
        size="default"
        ref="formRef"
        style="max-width: 600px"
        :model="formData"
        status-icon
        :rules="rules"
        label-width="80px"
    >
        <div class="form-header">
            <p>{{ $t('register.title') }}</p>
        </div>
        <el-form-item :label="$t('register.username')" prop="username">
            <el-input v-model="formData.username" />
        </el-form-item>
        <el-form-item :label="$t('register.password')" prop="password">
            <el-input v-model="formData.password" type="password" />
        </el-form-item>
        <el-form-item :label="$t('register.email')" prop="email">
            <el-input v-model="formData.email" type="email" />
        </el-form-item>
        <el-form-item :label="$t('register.role')" prop="role">
            <el-select v-model="formData.role" placeholder="Select Role">
                <el-option label="Volunteer" value="volunteer"></el-option>
                <el-option label="Admin" value="admin"></el-option>
                <el-option label="Organizer" value="organizer"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm(formRef)">
                {{ $t('register.register') }}
            </el-button>
            <el-button @click="resetForm(formRef)">
                {{ $t('register.reset') }}
            </el-button>
        </el-form-item>
    </el-form>
</template>

<script lang="ts" setup>
import { getCurrentInstance, onMounted, reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { useRouter } from "vue-router";
import useUser from "../../store/user.ts";
import { ElNotification } from "element-plus";

// 获取当前组件实例
const { proxy }: any = getCurrentInstance();

// 表单实例引用
const formRef = ref<FormInstance>();

// 路由实例
const router = useRouter();

// 用户状态管理
const userStore = useUser();

// 表单数据
const formData = reactive({
    username: "",
    password: "",
    email: "",
    role: "",
});

// 验证用户名
const validateUsername = (_: any, value: any, callback: any) => {
    if (value === "") {
        callback(new Error(proxy.$t('register.usernameRequired')));
    } else {
        callback();
    }
};

// 验证密码
const validatePassword = (_: any, value: any, callback: any) => {
    if (value === "") {
        callback(new Error(proxy.$t('register.passwordRequired')));
    } else {
        callback();
    }
};

// 验证邮箱
const validateEmail = (_: any, value: any, callback: any) => {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (value === "") {
        callback(new Error(proxy.$t('register.emailRequired')));
    } else if (!emailPattern.test(value)) {
        callback(new Error(proxy.$t('register.emailInvalid')));
    } else {
        callback();
    }
};

// 验证角色选择
const validateRole = (_: any, value: any, callback: any) => {
    if (value === "") {
        callback(new Error(proxy.$t('register.roleRequired')));
    } else {
        callback();
    }
};

// 表单验证规则
const rules = reactive<FormRules<typeof formData>>({
    username: [{ validator: validateUsername, trigger: "blur" }],
    password: [{ validator: validatePassword, trigger: "blur" }],
    email: [{ validator: validateEmail, trigger: "blur" }],
    role: [{ validator: validateRole, trigger: "change" }],
});

// 提交表单
const submitForm = (formRef: FormInstance | undefined) => {
    if (!formRef) return;
    formRef.validate(async (valid) => {
        if (valid) {
            try {
                // 提交注册请求
                const res = await proxy.$api.register(formData);
                if (res) {
                    ElNotification({
                        title: proxy.$t('notification.title'),
                        message: res.message,
                        type: "success",
                    });

                    // 根据角色跳转到不同页面
                    if (formData.role === "volunteer") {
                        router.push("/volunteer");
                    } else if (formData.role === "admin") {
                        router.push("/admin");
                    } else if (formData.role === "organizer") {
                        router.push("/organizer");
                    } else {
                        console.log("Role error");
                    }
                } else {
                    ElNotification({
                        title: proxy.$t('notification.title'),
                        message: proxy.$t('notification.registrationFailed'),
                        type: "error",
                    });
                }
            } catch (error) {
                console.error("Error during registration:", error);
            }
        } else {
            console.log("Form validation failed");
        }
    });
};

// 重置表单
const resetForm = (formRef: FormInstance | undefined) => {
    if (!formRef) return;
    formRef.resetFields();
};

// 组件挂载后检查用户是否已登录，如果已登录则跳转到对应页面
onMounted(() => {
    if (userStore.user.username) {
        if (userStore.user.role.roleName == "volunteer") {
            router.push("/volunteer");
        } else if (userStore.user.role.roleName == "admin") {
            router.push("/admin");
        } else if (userStore.user.role.roleName == "organizer") {
            router.push("/organizer");
        } else {
            console.log("Role error");
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
