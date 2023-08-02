import { ref, reactive } from 'vue'
import { defineStore, storeToRefs } from 'pinia'

const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  function increment() {
    count.value++
  }

  return { count, increment }
})

export default useCounterStore
