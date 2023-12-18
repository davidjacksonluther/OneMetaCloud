<script>
import { applyPureReactInVue} from 'veaury'
import { Button, Flex } from 'antd';
import {ref} from 'vue'
  export default {
    computed :{
      username(){
        console.log(this.$route.params.username);
        return this.$route.params.username
      }
    },components:{
      Button:applyPureReactInVue(Button),
      Flex:applyPureReactInVue(Flex)
    }
  }

</script>

<template>
  <div>
    <Flex gap="small" wrap="wrap">
      <Button type="primary">Primary Button</Button>
      <Button>Default Button</Button>
      <Button type="dashed">Dashed Button</Button>
      <Button type="text">Text Button</Button>
      <Button type="link">Link Button</Button>
    </Flex>
    Hello David
    {{username}}
  </div>

</template>

<style scoped>

</style>