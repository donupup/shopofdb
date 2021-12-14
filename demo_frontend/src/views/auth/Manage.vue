<template>
  <div>
    <el-page-header @back="headBack" content="人员管理">
    </el-page-header>
    <el-divider></el-divider>
    <list v-for="(item,index) in info" :item="item" :key="index"></list>
  </div>
</template>

<script>

import List from '@/components/auth/List'
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
    },
    headBack(){
      this.$router.back()
    }
  }
}
</script>

<style scoped>

</style>
