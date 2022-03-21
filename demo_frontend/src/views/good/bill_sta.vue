<template>
  <div>
    <el-page-header @back="headBack" content="销售统计"> </el-page-header>
    <el-divider></el-divider>
    <div style="text-align:right">
      <el-button type="primary" @click="totalNum" >点击计算</el-button>
    </div>
    💴总进价   <el-tag type="danger">{{totalIn}}</el-tag>元
    <el-divider></el-divider>
    <!-- <sale-list v-for="(item,index) in info" :item="item" :key="index"></sale-list> -->
    <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
      <el-tab-pane label="进货账单" name="first">
        <el-table
          :data="inGood"
          border
          highlight-current-row
          style="width: 100%"
          :default-sort="{ prop: 'goodname', order: 'descending' }"
        >
          <el-table-column
            prop="goodname"
            label="商品名称"
            sortable
            width="180"
          >
          </el-table-column>
          <el-table-column prop="goodId" label="商品Id" sortable width="180">
          </el-table-column>
          <el-table-column prop="num" label="数量" sortable width="180">
          </el-table-column>
          <el-table-column prop="inPrice" label="进价" sortable width="180">
          </el-table-column>
          <el-table-column
            prop="bio"
            label="备注"
            sortable
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="GoodInTime"
            label="日期"
            sortable
            :formatter="formatter"
          >
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="出货账单" name="second">
        <el-table
          :data="outGood"
          border
          highlight-current-row
          style="width: 100%"
          :default-sort="{ prop: 'goodname', order: 'descending' }"
        >
          <el-table-column
            prop="goodname"
            label="商品名称"
            sortable
            width="180"
          >
          </el-table-column>
          <el-table-column prop="goodId" label="商品Id" sortable width="180">
          </el-table-column>
          <el-table-column prop="num" label="数量" sortable width="180">
          </el-table-column>
          <el-table-column
            prop="bio"
            label="备注"
            sortable
            width="180"
          >
          </el-table-column>
        
          <el-table-column
            prop="GoodOutTime"
            label="日期"
            sortable
            :formatter="formatter_out"
          >
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getInList, getOutList } from "@/api/good";
//import SaleList from '../../components/good/saleList.vue'

export default {
  name: "bill_sta",
  components: {},
  data() {
    return {
      info: [],
      inGood: [],
      outGood: [],
      totalIn: "",
      activeName: "first",
    };
  },
  mounted() {
    this.fetchSaleGood();
  },
  methods: {
    fetchSaleGood() {
      getInList().then((response) => {
        const { data } = response;
        this.inGood = data;
        console.log(this.inGood);
      });
      getOutList().then((response) => {
        const { data } = response;
        this.outGood = data;
        console.log(this.outGood);
      });
    },
    headBack() {
      this.$router.back();
    },
    formatter(row, column) {
      //return dayjs(row.goodSoldTime).format("YYYY/MM/DD dddd HH:mm:ss.SSS A");
      return row.goodInTime.substr(0, 19);
    },
    formatter_out(row, column) {
      //return dayjs(row.goodSoldTime).format("YYYY/MM/DD dddd HH:mm:ss.SSS A");
      return row.goodOutTime;
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    totalNum(){
        let len = this.inGood.length
        let sum = 0
        for(let i = 0;i < len ;i++)
        {
            sum += this.inGood[i].num * this.inGood[i].inPrice;
        }
        this.totalIn = sum;
        console.log(sum);
    }
  },
};
</script>

<style scoped>
</style>
