<template>
  <div>
    <el-page-header @back="headBack" content="销售统计"> </el-page-header>
    <el-divider></el-divider>
    在此期间👜共卖出 <el-tag>{{ saleNum }}</el-tag
    >件 💴总进价
    <el-tag type="warning">{{ totalIn }}</el-tag
    >元 💰 总售价 <el-tag type="success">{{ totalOut }}</el-tag
    >元

    <!-- <exportExcel :id="'exportTab'" :name="'导出Table'"></exportExcel> -->
    <div hidden="hidden">
      <el-table
        :data="this.info"
        border-card
        highlight-current-row
        style="width: 100%"
        :default-sort="{ prop: 'goodSoldTime', order: 'descending' }"
        id="exportTab"
      >
        <el-table-column prop="goodname" label="商品名称" sortable width="180">
        </el-table-column>
        <el-table-column prop="goodId" label="商品Id" sortable width="180">
        </el-table-column>
        <el-table-column prop="num" label="数量" sortable width="100">
        </el-table-column>
        <el-table-column prop="priceinNow" label="进价" sortable width="100">
        </el-table-column>
        <el-table-column prop="pricesellNow" label="售价" sortable width="100">
        </el-table-column>
        <el-table-column
          prop="goodSoldTime"
          label="日期"
          sortable
          :formatter="formatter"
        >
        </el-table-column>
      </el-table>
    </div>
    <el-divider></el-divider>
    <div class="block">
      <span class="demonstration"
        ><el-button @click="choseData" type="primary"
          >筛选日期后进行统计</el-button
        ></span
      >
      <el-date-picker
        v-model="value"
        type="daterange"
        align="right"
        unlink-panels
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :picker-options="pickerOptions"
      >
      </el-date-picker>
      <exportExcel
        :id="'exportTab'"
        :name="'全日期商品销售记录'"
        :button="'导出全部记录'"
      ></exportExcel>
      <exportExcel
        :id="'exportNow'"
        :name="'当前选择商品销售记录'"
        :button="'导出当前选择日期内记录'"
      ></exportExcel>
    </div>

    <!-- <sale-list v-for="(item,index) in info" :item="item" :key="index"></sale-list> -->
    <el-table
      :data="
        this.databyday.slice((this.page - 1) * this.size, this.page * this.size)
      "
      border-card
      highlight-current-row
      style="width: 100%"
      :default-sort="{ prop: 'goodSoldTime', order: 'descending' }"
      id="exportNow"
    >
      <el-table-column prop="goodname" label="商品名称" sortable width="180">
      </el-table-column>
      <el-table-column prop="goodId" label="商品Id" sortable width="180">
      </el-table-column>
      <el-table-column prop="num" label="数量" sortable width="100">
      </el-table-column>
      <el-table-column prop="priceinNow" label="进价" sortable width="100">
      </el-table-column>
      <el-table-column prop="pricesellNow" label="售价" sortable width="100">
      </el-table-column>
      <el-table-column
        prop="goodSoldTime"
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
      :total="this.databyday.length"
    >
    </el-pagination>
  </div>
</template>

<script>
import { getSaleList } from "@/api/good";
import SaleList from "../../components/good/saleList.vue";
import ExportExcel from "@/components/ExportExcel";
export default {
  name: "sale_sta",
  components: { SaleList, ExportExcel },
  data() {
    return {
      page: 1, //第几页
      size: 3, //一页多少条
      total: 0, //总条目数
      pageSizes: [3, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000], //可选择的一页多少条
      tableData: [], //表格绑定的数据
      info: [],
      saleNum: 0,
      totalIn: 0,
      totalOut: 0,
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      value: "",
      databyday: [],
    };
  },
  mounted() {
    this.fetchSaleGood();
    //this.choseData();
    this.getTabelData2();
    this.sizeChange(5);
  },
  methods: {
    fetchSaleGood() {
      getSaleList().then((response) => {
        const { data } = response;
        this.info = data;
      });
    },
    headBack() {
      this.$router.back();
    },
    formatter(row, column) {
      //return dayjs(row.goodSoldTime).format("YYYY/MM/DD dddd HH:mm:ss.SSS A");
      return row.goodSoldTime.substr(0, 19);
    },
    totalNum() {
      let sum = 0;
      let In = 0;
      let out = 0;
      let len = this.databyday.length;
      for (let i = 0; i < len; i++) {
        sum = sum + this.databyday[i].num;
        In = In + this.databyday[i].priceinNow;
        out = out + this.databyday[i].pricesellNow;
      }
      this.saleNum = sum;
      this.totalIn = In;
      this.totalOut = out;
    },
    getTabelData() {
      //allData为全部数据
      this.tableData = this.info.slice(
        (this.page - 1) * this.size,
        this.page * this.size
      );
      this.total = this.info.length;
    },

    //获取表格数据，自行分页（splice）
    getTabelData2() {
      let data = JSON.parse(JSON.stringify(this.info));
      console.log(data);
      this.tableData = data.splice((this.page - 1) * this.size, this.size);
      this.total = this.info.length;
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

    //日期筛选
    choseData() {
      this.databyday = [];
      //console.log(this.value[0]);
      if (this.value === null || this.value === "") {
        //console.log(111111)
        this.databyday = this.info;
        this.$message({
                  message: "没有选择日期，默认为全部日期",
                  type: "info",
                  duration: 2000,
                });
        //console.log(this.databyday)
      } else {
        let j = 0;
        for (let i = 0; i < this.info.length; i++) {
          //console.log(new Date(this.info[i].goodSoldTime))
          //console.log(this.value[0])
          if (
            new Date(this.info[i].goodSoldTime).getTime() >
              this.value[0].getTime() &&
            new Date(this.info[i].goodSoldTime).getTime() <
              this.value[1].getTime()
          ) {
            this.databyday[j] = this.info[i];
            j++;
          }
        }
        this.$message({
                  message: "筛选成功！",
                  type: "success",
                  duration: 2000,
                });
        // console.log(123);
        // //console.log(this.info)
        // console.log(this.databyday);
        // console.log(456)
      }
      this.sizeChange(this.size);
      this.totalNum();
      
    },
  },
};
</script>

<style scoped>
</style>
