<template>
  <div class="editor">
    <el-input placeholder="请输入帖子标题" class="edit-input" v-model="post.title" />
    <div style="border: 1px solid #ccc">
      <div id="editor—wrapper">
        <div id="toolbar-container"><!-- 工具栏 --></div>
        <div id="editor-container"><!-- 编辑器 --></div>
      </div>
    </div>
  </div>

  <div class="wrapper">
    <div>
      是否增加话题
      <el-switch v-model="isAddTopic"></el-switch>
    </div>
    <div v-show="isAddTopic">
      <div>
        <span style="margin-right: 10px">请输入增加的话题(最多三个)</span>
        <el-select v-model="topic" multiple filterable :multiple-limit="3" allow-create default-first-option
          :reserve-keyword="false" placeholder="请输入话题" size="large">
          <el-option v-for="topic in topicList" :key="topic.id" :label="topic.name" :value="topic.name" />
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
import Vote from '@/components/Vote.vue'
import { useFetchTopic } from '@/hooks/useFetchTopic.ts'

const post = reactive({
  title: '',
  content: ''
})
const { createEditor, createToolbar } = window.wangEditor
// 富文本相关
let editor: any = null
let toolbarConfig = {}
let toolbar = null
onMounted(() => {
  editor = createEditor({
    selector: '#editor-container',
    html: '<p><br></p>',
    config: editorConfig,
    mode: 'default' // or 'simple'
  })
  toolbar = createToolbar({
    editor,
    selector: '#toolbar-container',
    config: toolbarConfig,
    mode: 'default' // or 'simple'
  })


})
let editorConfig = {
  placeholder: 'Type here...',
  onChange(editor: any) {
    post.content = editor.getText() ? editor.getHtml() : ''
  }
}
const isAddTopic = ref(false)
const isAddVote = ref(false)
const voteRef = ref(null)
const topic = ref<string[]>([])
let topicList: any = useFetchTopic()

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef()
const mode = 'defalut'

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  if (editor == null) return
  editor.destroy()
})

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
</script>

<style lang="less">
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
