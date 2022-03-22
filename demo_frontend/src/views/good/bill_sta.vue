<template>
  <div>
    <el-page-header @back="headBack" content="账单"> </el-page-header>
    <el-divider></el-divider>
    <div style="text-align: right">
      <el-button type="primary" @click="totalNum">点击计算</el-button>
    </div>
    💴总进价 <el-tag type="danger">{{ totalIn }}</el-tag
    >元
    <el-divider></el-divider>
    <!-- <sale-list v-for="(item,index) in info" :item="item" :key="index"></sale-list> -->
    <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
      <el-tab-pane label="进货账单" name="first">
        <el-table
          :data="
            this.inGood.slice(
              (this.page - 1) * this.size,
              this.page * this.size
            )
          "
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
          <el-table-column prop="num" label="数量" sortable width="100">
          </el-table-column>
          <el-table-column prop="inPrice" label="进价" sortable width="100">
          </el-table-column>
          <el-table-column prop="bio" label="备注" sortable width="180">
          </el-table-column>
          <el-table-column
            prop="GoodInTime"
            label="日期"
            sortable
            :formatter="formatter"
          >
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :current-page="page"
          :page-size="size"
          :page-sizes="pageSizes"
          layout="total, sizes, prev, pager, next, jumper"
          :total="this.inGood.length"
        >
        </el-pagination>
      </el-tab-pane>
      <el-tab-pane label="出货账单" name="second">
        <el-table
          :data="
            this.outGood.slice(
              (this.page - 1) * this.size,
              this.page * this.size
            )
          "
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
          <el-table-column prop="bio" label="备注" sortable width="180">
          </el-table-column>

          <el-table-column
            prop="GoodOutTime"
            label="日期"
            sortable
            :formatter="formatter_out"
          >
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :current-page="page"
          :page-size="size"
          :page-sizes="pageSizes"
          layout="total, sizes, prev, pager, next, jumper"
          :total="this.outGood.length"
        >
        </el-pagination>
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
      page: 1, //第几页
      size: 3, //一页多少条
      total: 0, //总条目数
      pageSizes: [3, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000], //可选择的一页多少条
      tableData: [], //表格绑定的数据
      info: [],
      inGood: [],
      outGood: [],
      totalIn: "",
      activeName: "first",
    };
  },
  mounted() {
    this.fetchSaleGood();
    console.log(1);
    console.log(this.tableData);
    this.getTabelData2();
    console.log(12);
    console.log(this.tableData);
    this.sizeChange(5);
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
    totalNum() {
      let len = this.inGood.length;
      let sum = 0;
      for (let i = 0; i < len; i++) {
        sum += this.inGood[i].num * this.inGood[i].inPrice;
      }
      this.totalIn = sum;
      console.log(sum);
    },

    //获取表格数据，自行分页（splice）
    getTabelData2() {
      let data = JSON.parse(JSON.stringify(this.inGood));
      console.log(data);
      this.tableData = data.splice((this.page - 1) * this.size, this.size);
      this.total = this.inGood.length;
    },
    //page改变时的回调函数，参数为当前页码
    currentChange(val) {
      console.log("翻页，当前为第几页", val);
      this.page = val;
      this.getTabelData2();
    },
    //size改变时回调的函数，参数为当前的size
    sizeChange(val) {
      console.log("改变每页多少条，当前一页多少条数据", val);
      this.size = val;
      this.page = 1;
      this.getTabelData2();
    },
  },
};
</script>

<style scoped>
</style>
