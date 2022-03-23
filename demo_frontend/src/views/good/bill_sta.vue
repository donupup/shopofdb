<template>
  <div>
    <el-page-header @back="headBack" content="账单"> </el-page-header>
    <el-divider></el-divider>
    <!-- <div style="text-align: right">
      <el-button type="primary" @click="totalNum">点击计算</el-button>
    </div> -->
    在选中时间内💴总进价 <el-tag type="danger">{{ totalIn }}</el-tag
    >元
    <el-divider></el-divider>

    <div hidden="hidden">
      <el-table
        :data="this.inGood"
        border
        highlight-current-row
        style="width: 100%"
        :default-sort="{ prop: 'GoodInTime', order: 'descending' }"
        id="exportIn"
      >
        <el-table-column prop="goodname" label="商品名称" sortable width="180">
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
      <el-table
        :data="this.outGood"
        border
        highlight-current-row
        style="width: 100%"
        :default-sort="{ prop: 'GoodOutTime', order: 'descending' }"
        id="exportOut"
      >
        <el-table-column prop="goodname" label="商品名称" sortable width="180">
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
    </div>
    <!-- <sale-list v-for="(item,index) in info" :item="item" :key="index"></sale-list> -->
    <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card">
      <el-tab-pane label="进货账单" name="first">
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
            :id="'exportIn'"
            :name="'全部进货记录'"
            :button="'导出全部记录'"
          ></exportExcel>
          <exportExcel
            :id="'exportNowIn'"
            :name="'当前选择进货记录'"
            :button="'导出当前选择日期内记录'"
          ></exportExcel>
        </div>
        <el-table
          :data="
            this.inGoodByday.slice(
              (this.page - 1) * this.size,
              this.page * this.size
            )
          "
          border
          highlight-current-row
          style="width: 100%"
          :default-sort="{ prop: 'GoodInTime', order: 'descending' }"
          id="exportNowIn"
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
          :total="this.inGoodByday.length"
        >
        </el-pagination>
      </el-tab-pane>
      <el-tab-pane label="出货账单" name="second">
        <div class="block">
          <span class="demonstration"
            ><el-button @click="choseData2" type="primary"
              >筛选日期后进行统计</el-button
            ></span
          >
          <el-date-picker
            v-model="value2"
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
            :id="'exportOut'"
            :name="'全部出货记录'"
            :button="'导出全部记录'"
          ></exportExcel>
          <exportExcel
            :id="'exportNowOut'"
            :name="'当前选择出货记录'"
            :button="'导出当前选择日期内记录'"
          ></exportExcel>
        </div>
        <el-table
          :data="
            this.outGoodByday.slice(
              (this.page - 1) * this.size,
              this.page * this.size
            )
          "
          border
          highlight-current-row
          style="width: 100%"
          :default-sort="{ prop: 'GoodOutTime', order: 'descending' }"
          id="exportNowOut"
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
          :total="this.outGoodByday.length"
        >
        </el-pagination>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getInList, getOutList } from "@/api/good";
//import SaleList from '../../components/good/saleList.vue'
import ExportExcel from "@/components/ExportExcel";
export default {
  name: "bill_sta",
  components: { ExportExcel },
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
      totalIn: 0,
      activeName: "first",
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
      value2: "",
      inGoodByday: [],
      outGoodByday: [],
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
      let len = this.inGoodByday.length;
      let sum = 0;
      for (let i = 0; i < len; i++) {
        sum += this.inGoodByday[i].num * this.inGoodByday[i].inPrice;
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
    choseData() {
      this.inGoodByday = [];
      //console.log(this.value[0]);
      if (this.value === null || this.value === "") {
        //console.log(111111)
        this.inGoodByday = this.inGood;
        this.$message({
                  message: "没有选择日期，默认为全部日期",
                  type: "info",
                  duration: 2000,
                });
        //console.log(this.databyday)
      } else {
        let j = 0;
        for (let i = 0; i < this.inGood.length; i++) {
          //console.log(new Date(this.info[i].goodSoldTime))
          //console.log(this.value[0])
          if (
            new Date(this.inGood[i].goodInTime).getTime() >
              this.value[0].getTime() &&
            new Date(this.inGood[i].goodInTime).getTime() <
              this.value[1].getTime()
          ) {
            this.inGoodByday[j] = this.inGood[i];
            j++;
          }
        }
        this.$message({
                  message: "筛选成功！",
                  type: "success",
                  duration: 2000,
                });
      }
      // console.log(123);
      // //console.log(this.info)
      // console.log(this.inGoodByday);
      // console.log(456)
      this.sizeChange(this.size);
      this.totalNum();
      
    },
    choseData2() {
      this.outGoodByday = [];
      //console.log(this.value[0]);
      if (this.value2 === null || this.value2 === "") {
        //console.log(111111)
        this.outGoodByday = this.outGood;
        this.$message({
                  message: "没有选择日期，默认为全部日期",
                  type: "info",
                  duration: 2000,
                });
        //console.log(this.databyday)
      } else {
        let j = 0;
        for (let i = 0; i < this.outGood.length; i++) {
          //console.log(new Date(this.info[i].goodSoldTime))
          //console.log(this.value[0])
          if (
            new Date(this.outGood[i].goodOutTime).getTime() >
              this.value2[0].getTime() &&
            new Date(this.outGood[i].goodOutTime).getTime() <
              this.value2[1].getTime()
          ) {
            this.outGoodByday[j] = this.outGood[i];
            j++;
          }
        }
        this.$message({
                  message: "筛选成功！",
                  type: "success",
                  duration: 2000,
                });
      }
      // console.log(123);
      // //console.log(this.info)
      // console.log(this.inGoodByday);
      // console.log(456)
      this.sizeChange(this.size);
      
    },
  },
};
</script>

<style scoped>
</style>
