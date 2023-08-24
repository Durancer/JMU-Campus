import { onUnmounted, ref } from 'vue'

export function useCountDown(callback) {
  let timer = null
  let timeCount = ref(0)
  function clearTimer() {
    timer && clearInterval(timer)
  }
  // 向外面暴露一个start函数
  let start = (seconds: number) => {
    timeCount.value = seconds
    timer = setInterval(() => {
      timeCount.value--
      // 时间到了自动清除,并且执行回调函数
      if (timeCount.value <= 0) {
        clearTimer()
        callback && callback()
      }
    }, 1000)
  }
  // 组件被卸载了自动清除
  onUnmounted(() => {
    clearTimer
  })
  return {
    timeCount,
    start
  }
}
