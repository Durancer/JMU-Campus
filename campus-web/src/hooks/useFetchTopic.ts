import { getAllTopic } from '@/api/posts/index.ts'
import { onMounted, ref } from 'vue'

export const useFetchTopic = () => {
  const topicList = ref([])
  async function getAllTopicFn() {
    const res = await getAllTopic()
    if (res.data && res.data.length > 10) {
      topicList.value = res.data.records.slice(0, 10)
    } else {
      topicList.value = res.data.records // TODO：话题太多怎么展示话题
    }
  }
  onMounted(() => getAllTopicFn())
  return topicList
}
