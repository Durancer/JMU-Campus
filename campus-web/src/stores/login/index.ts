import { ref, reactive } from 'vue'
import { defineStore, storeToRefs } from 'pinia'
import router from '@/router'
import { ElMessage } from 'element-plus'
import 'element-plus/es/components/message/style/css'

import { login } from '@/api/login/login'

export const useLoginStore = defineStore('login', () => {
    async function loginFn(data: any) {
        const res = await login(data)
        console.log(res);
    }
    return { loginFn }
})