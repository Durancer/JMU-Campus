<template>
  <div class="editor">
    <el-input placeholder="请输入帖子标题" class="edit-input" v-model="post.title" />
    <!--
    <div
      class="content"
      contenteditable="true"
      @input="handleInput"
      @blur="handleInput"
      ref="contentRef"
    >
      <div class="placeholder" style="pointer-events: none" :class="{ hasContent: hasContent }">
        此刻你想和大家分享什么
      </div>
    </div>
    -->
    <div style="border: 1px solid #ccc">
      <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
      />
      <Editor
        style="height: 500px; overflow-y: hidden"
        v-model="post.content"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
      />
    </div>
  </div>
  <div class="wrapper" v-show="isShow">
    <Vote ref="voteRef"></Vote>
  </div>
  <div class="wrapper">
    <div>
      是否增加投票
      <el-switch v-model="isShow" />
    </div>
    <el-button type="success" @click="publish">发布</el-button>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, reactive, shallowRef, onMounted, onBeforeUnmount } from 'vue'
import { addPost } from '@/api/posts/index.ts'
import { sucMessage, failMessage } from '@/utils/common.ts'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import Vote from '@/components/Vote.vue'
// import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

const contentRef = ref(null)
const post = reactive({
  title: '',
  content: ''
})
const isShow = ref(false)
const voteRef = ref(null)
// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
const mode = 'defalut'

// 内容 HTML
// const valueHtml = ref('')

// 模拟 ajax 异步获取内容
// onMounted(() => {
// setTimeout(() => {
//   valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
// }, 1500)
// failMessage('failMessage')
// })

const toolbarConfig = {}
const editorConfig = { placeholder: '请输入帖子内容...' }

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}
const handleInput = (event) => {
  post.content = event.target.innerText
}
// const titleInput = (e) => {
//   console.log(e.target.value, post.title)
// }
const hasContent = computed(() => post.content.length > 0)
const valid = () => {
  if (!post.title) {
    failMessage('请输入标题')
    return false
  }
  if (!post.content) {
    sucMessage('请输入内容')
    return false
  }
  return true
}
const router = useRouter()
const publish = () => {
  // post.content = contentRef.value?.innerText
  if (valid()) {
    let data = {}
    if (isShow.value) {
      // 增加投票选项
      const voteContent = JSON.parse(JSON.stringify(voteRef.value.voteForm)) //TODO 之后得优化
      data = { ...voteContent, ...post }
    } else {
      data = { ...post }
    }
    let formData = new FormData()
    for (let key in data) {
      formData.append(key, data[key])
    }
    addPost(formData).then((res) => {
      if (res.status) {
        sucMessage('发布成功')
        router.push({ name: 'index' })
      } else {
        failMessage(res.message)
      }
    })
  }
}
const totast = () => {
  ElMessage({
    message: '发布成功',
    type: 'success'
  })
}
</script>

<style lang="less">
@import url('@wangeditor/editor/dist/css/style.css');
.editor {
  box-sizing: border-box;
  width: 930px;
  padding: 20px;
  margin: auto;
  border-radius: 12px;
  background-color: rgba(255, 255, 255, 1);
  overflow: auto;
  .edit-input {
    // background-color: transparent;
    // border: none;
    // border-bottom: 1px solid rgba(240, 240, 240, 1);
    color: rgba(51, 51, 51, 1);
    font-size: 16px;
    font-weight: 300;
    height: 40px;
    line-height: 24px;
    outline: 2px solid transparent;
    outline-offset: 2px;
    width: 100%;
    margin-bottom: 20px;
  }
  .content {
    margin-top: 10px;
    position: relative;
    min-height: 240px;
    .placeholder {
      position: absolute;
      left: 0;
      right: 0;
      color: rgba(51, 51, 51, 1);
      font-size: 14px;
      font-weight: 300;
    }
    &:focus {
      outline: none;
    }
    &:focus .placeholder {
      display: none;
    }
    .hasContent {
      display: none;
    }
  }
}
.wrapper {
  width: 930px;
  margin: 20px auto;
}
</style>
