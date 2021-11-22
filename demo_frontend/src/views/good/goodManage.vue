<template>
  <div>
    <goodList v-for="(item,index) in goodInfo" :item="item" :key="index"></goodList>
  </div>

</template>

<script>
import goodList from "@/components/goodList";
import {getGoodList} from "@/api/good";
export default {
  name: "goodManage",
  components:{goodList},
  data(){
    return{
      goodInfo:{}
    }
  },
  mounted() {
    this.fetchGoodList()
  },
  methods:{
    fetchGoodList(){
      getGoodList().then(response => {
        const {data} = response
        this.goodInfo= data
        console.log(this.goodInfo)
      })
    }

  }

}
</script>

<style scoped>

</style>
