<template>
  <div>
    <el-page-header @back="headBack" content="商品搜索详情">
    </el-page-header>
    <el-divider></el-divider>
    <el-card shadow="never">
      <div slot="header" class="clearfix">
        检索到 <code>{{ list.length }}</code>
        条关于 <code class="has-text-info">{{ query.keyword }}</code> 的商品
      </div>
    </el-card>
    <div>
      <goodList v-for="(item,index) in list" :item="item" :key="index" :need-opera="true"></goodList>
    </div>
  </div>
</template>

<script>
import goodList from "@/components/good/goodList";
import { searchByKeyword } from '@/api/search'
export default {
  name: 'search',
  components:{goodList},
  data() {
    return {
      list: [],
      query: {
        keyword: this.$route.query.key,
      }
    }
  },
  created() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      searchByKeyword(this.query).then(value => {
        const { data } = value
        this.list = data
        console.log(this.list)
      })
    },
    headBack(){
      console.log(this.$router)
      this.$router.back()
    },
  }
}
</script>

<style scoped>
</style>