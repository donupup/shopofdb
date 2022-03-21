<template>
  <div>
    <el-page-header @back="headBack" content="销售统计">
    </el-page-header>
    <el-divider></el-divider>
    <div style="text-align:right">
      <el-button type="primary" @click="totalNum" >点击计算</el-button>
    </div>
     👜共卖出   <el-tag>{{saleNum}}</el-tag>件   
     <el-divider></el-divider>💴总进价   <el-tag type="warning">{{totalIn}}</el-tag>元  💰 总售价   <el-tag type="success" >{{totalOut}}</el-tag>元
     
     <el-divider></el-divider>
    <!-- <sale-list v-for="(item,index) in info" :item="item" :key="index"></sale-list> -->
    <el-table
    :data="info"
    border
    highlight-current-row
    style="width: 100%"
    :default-sort = "{prop: 'goodname', order: 'descending'}"
    >
    <el-table-column
      prop="goodname"
      label="商品名称"
      sortable
      width="180">
    </el-table-column>
    <el-table-column
      prop="goodId"
      label="商品Id"
      sortable
      width="180">
    </el-table-column>
    <el-table-column
      prop="num"
      label="数量"
      sortable
      width="180">
    </el-table-column>
    <el-table-column
      prop="priceinNow"
      label="当时进价"
      sortable
      width="180">
    </el-table-column>
    <el-table-column
      prop="pricesellNow"
      label="当时售价"
      sortable
      width="180">
    </el-table-column>
    <el-table-column
      prop="goodSoldTime"
      label="日期"
      sortable
      :formatter="formatter">
    </el-table-column>
  </el-table>


  </div>
</template>

<script>

import {getSaleList} from '@/api/good'
import SaleList from '../../components/good/saleList.vue'

export default {
  name: "sale_sta",
  components:{SaleList},
  data() {
    return {
      info: [],
      saleNum:'',
      totalIn:'',
      totalOut:''
    }
  },
  mounted() {
    this.fetchSaleGood();
  },
  methods: {
    fetchSaleGood(){
        getSaleList().then(response => {
            const{data} = response;
            this.info = data;
            console.log(this.info);
        })
    },
    headBack(){
      this.$router.back()
    },
    formatter(row, column) {
        //return dayjs(row.goodSoldTime).format("YYYY/MM/DD dddd HH:mm:ss.SSS A");
        return row.goodSoldTime.substr(0,19);
    },
    totalNum(){
      let sum = 0;
      let In = 0;
      let out = 0;
      let len = this.info.length;
      for(let i = 0;i < len;i ++)
      {
        sum = sum + this.info[i].num;
        In = In + this.info[i].priceinNow;
        out = out + this.info[i].pricesellNow;
      } 
      this.saleNum = sum;
      this.totalIn = In;
      this.totalOut = out;
    }
  }
}
</script>

<style scoped>

</style>
