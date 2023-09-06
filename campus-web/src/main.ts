import { createApp } from 'vue'
import { createPinia } from 'pinia'
import 'normalize.css'
import '@/assets/css/index.less'
import App from './App.vue'
import router from './router'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
// import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import './assets/main.css'
import 'virtual:svg-icons-register'
import svgIcon from '@/components/SvgIcon.vue'
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
const pinia = createPinia()
// 注册持久化插件
pinia.use(piniaPluginPersistedstate)
app.use(pinia)
app.use(router)
app.component('SvgIcon', svgIcon)
app.mount('#app')
