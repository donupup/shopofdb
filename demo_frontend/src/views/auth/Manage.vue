<template>
  <div>
    <list v-for="(item,index) in info" :item="item" :key="index"></list>
  </div>
</template>

<script>

import List from '@/components/List'
import {userInfo} from '@/api/auth'

export default {
  name: "Manage",
  components: {List},
  data() {
    return {
      info: {}
    }
  },
  mounted() {
    this.fetchInfo()
  },
  methods: {
    fetchInfo() {
      userInfo().then(response => {
        const {data} = response
        this.info = data
        console.log(this.info)
      })
    }
  }
}
</script>

<style scoped>

</style>
