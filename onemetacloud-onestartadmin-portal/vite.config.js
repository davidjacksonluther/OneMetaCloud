import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import veauryVitePlugins from 'veaury/vite/index.js'


// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    veauryVitePlugins({type:'vue'})
  ],
  resolve: {
    preserveSymlinks:false,
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server:{
    port:8084

  }
})
