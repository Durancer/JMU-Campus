import { fileURLToPath, URL } from 'node:url'
import path from 'path'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import { visualizer } from 'rollup-plugin-visualizer'
import viteCompression from 'vite-plugin-compression'
import { splitVendorChunkPlugin } from 'vite'
// https://vitejs.dev/config/
export default defineConfig({
  base: './',
  server: {
    proxy: {
      '/Apis': {
        target: 'http://60.204.139.75/api',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/Apis/, '')
      }
    }
  },
  plugins: [
    vue(),
    // ...
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      resolvers: [ElementPlusResolver()]
    }),
    createSvgIconsPlugin({
      // 指定需要缓存的图标文件夹
      iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
      // 指定symbolId格式
      symbolId: 'icon-[dir]-[name]'
    }),
    visualizer({
      gzipSize: true, // 搜集gzip压缩包的大小到图表
      brotliSize: true, // 搜索brotli压缩包的大小到图表
      emitFile: false, // 设置为true，分析文件在dist目录下
      filename: 'build_chunk.html', //分析图生成的文件名
      open: true //如果存在本地服务端口，将在打包后自动展示
    }),
    viteCompression({
      algorithm: 'gzip',
      threshold: 10240,
      verbose: false,
      deleteOriginFile: true
    }),
    splitVendorChunkPlugin() // 产物分块策略
  ],
  build: {
    minify: 'terser',
    terserOptions: {
      compress: {
        drop_console: true, // 删除console语句
        drop_debugger: true // 删除debugger
      },
      format: {
        comments: false // 移除注释
      }
    },
    //关闭生成map文件 可以达到缩小打包体积
    sourcemap: false // 这个生产环境一定要关闭，不然打包的产物会很大
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
