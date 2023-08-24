<template>
  <div class="editor">
    <el-input placeholder="请输入帖子标题" class="edit-input" v-model="post.title" />
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

  <div class="wrapper">
    <div>
      是否增加话题
      <el-switch v-model="isAddTopic"></el-switch>
    </div>
    <div v-show="isAddTopic">
      <div>
        <span>请输入增加的话题(最多三个)</span>
        <el-select
          v-model="topic"
          multiple
          filterable
          allow-create
          default-first-option
          :reserve-keyword="false"
          placeholder="请输入话题"
          size="large"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </div>
    </div>
    <div>
      是否增加投票
      <el-switch v-model="isAddVote" />
    </div>
    <div class="wrapper" v-show="isAddVote">
      <Vote ref="voteRef"></Vote>
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
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

const contentRef = ref(null)
const post = reactive({
  title: '',
  content: ''
})
const isAddTopic = ref(false)
const isAddVote = ref(false)
const voteRef = ref(null)
const topic = ref<string[]>([])
// 临时的数据
const options = [
  {
    value: 'HTML',
    label: 'HTML'
  },
  {
    value: 'CSS',
    label: 'CSS'
  },
  {
    value: 'JavaScript',
    label: 'JavaScript'
  }
]
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
// const handleInput = (event) => {
//   post.content = event.target.innerText
// }

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
  if (valid()) {
    let data = { ...post }
    if (isAddVote.value) {
      // 增加投票选项
      const voteContent = JSON.parse(JSON.stringify(voteRef.value.voteForm)) //TODO 之后得优化
      data = { ...voteContent, ...post }
    }
    // 如果增加话题选项
    if (isAddTopic.value) {
      data = { ...data, names: topic.value }
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
  max-width: 930px;
  margin: 20px auto;
  .edit-input {
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
}
.wrapper {
  max-width: 930px;
  margin: 20px auto;
}
.el-select {
  width: 320px;
}
</style>
