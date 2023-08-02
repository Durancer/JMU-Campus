import { ref, reactive } from 'vue'
import { defineStore, storeToRefs } from 'pinia'
import router from '@/router'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'

import { register, requestCode } from '@/api/register/register'

export const useRegisterStore = defineStore('register', () => {
    async function requestCodeFn(data: any) {
        const res = await requestCode(data)
        console.log(res);
    }
    async function registerFn(data: any) {
        const res = await register(data)
        console.log(res);

        if (res.data.status) {
            ElMessage({
                message: '登录成功',
                type: 'success'
            })
        }
    }

    return { registerFn, requestCodeFn }
})