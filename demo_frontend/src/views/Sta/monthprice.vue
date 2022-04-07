<template>
  <div>
    <el-page-header @back="headBack" content="月营销额查询"> </el-page-header>
    <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <i class="el-icon-search"></i>
      <span>选择月份进行统计</span>
      <div class="block">
        <span class="demonstration"></span>
        <el-date-picker v-model="month" type="month" placeholder="选择月">
        </el-date-picker>
        <el-button @click="handleSearch()" type="primary"> 查询结果 </el-button>
      </div>
    </el-card>
    <panel-year
      @handleSetLineChartData="handleSetLineChartData"
      :saleLength="totalSale"
      :inLength="totalIn"
      :outLength="totalOut"
      :storageLength="totalStorage"
    />
    <el-row style="background: #fff; padding: 16px 16px 0; margin-bottom: 32px">
      <line-chart :chart-data="this.lineChartData" :data="this.xarry" />
    </el-row>
    <el-divider></el-divider>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <bar-chart :barName="barName" :barNum="barNum" :barPrice="barPrice" />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <bar-season
            :inNum="seasonInNum"
            :inPrice="seasonInPrice"
            :salePrice="seasonSalePrice"
            :saleNum="seasonSaleNum"
            :saleProfit="seasonSaleProfit"
            :totalProfit="seasontotalProfit"
            :barName="seasonName"
          />
        </div>
      </el-col>
    </el-row>

    <el-divider></el-divider>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <circle-in :salePineNum="inCircleNum" />
        </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart :salePineNum="salePineNum" />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pinewithtitle :salePineNum="goodsalePrice" title="商品销售额" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import { getMonthSta } from "@/api/sta";
import BarChart from "@/components/dashboard/BarChart";
import PieChart from "@/components/dashboard/PieChart";
import CircleChart from "@/components/dashboard/CircleChart";
import CircleIn from "@/components/dashboard/CircleIn";
import Pinewithtitle from "../../components/dashboard/Pinewithtitle.vue";
import PanelYear from "../../components/dashboard/PanelYear.vue";
import LineChart from "../../components/dashboard/LineChart.vue";
import BarSeason from "../../components/dashboard/BarSeason.vue";

let lineChartDatas = {
  saleYearSta: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145],
  },
  inYearSta: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130],
  },
  profitYearSta: {
    expectedData: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    actualData: [120, 90, 100, 138, 142, 130, 130],
  },
  totalProfitYearSta: {
    expectedData: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    actualData: [120, 82, 91, 154, 162, 140, 130],
  },
};
export default {
  components: {
    BarChart,
    PieChart,
    CircleChart,
    CircleIn,
    Pinewithtitle,
    PanelYear,
    LineChart,
    BarSeason,
  },
  data() {
    return {
      lineChartData: lineChartDatas.saleYearSta,
      xarry: [
        "Jan",
        "Feb",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "Sept",
        "Oct",
        "Nov",
        "Dec",
      ],
      month: "",
      monthInfo: "",
      dateres: {},
      barName: ["销售", "进货", "利润"],
      barNum: [],
      barPrice: [],
      saleList: [],
      inList: [],
      salePineNum: [],

      seasonName: ["1", "2", "3", "4"],
      seasonInNum: [0, 0, 0, 0],
      seasonInPrice: [0, 0, 0, 0],
      seasonSaleNum: [0, 0, 0, 0],
      seasonSalePrice: [0, 0, 0, 0],
      seasonSaleProfit: [0, 0, 0, 0],
      seasontotalProfit: [0, 0, 0, 0],

      inCircleNum: [],
      goodsalePrice: [],
    };
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartDatas[type];
      console.log(this.lineChartData);
      //console.log(this.outLineNum)
    },
    handleSearch() {
      this.barNum = [];
      this.barPrice = [];
      if (this.month == "") {
        this.$message.error("请选择日期");
      } else {
        getMonthSta(this.month).then((response) => {
          const { data } = response;
          console.log(data);
          this.barNum.push(data.saleSum);
          this.barNum.push(data.inSum);
          this.barNum.push(data.saleSum);
          this.barNum.push(data.saleSum);
          this.barPrice.push(data.salePrice);
          this.barPrice.push(data.inPrice);
          this.barPrice.push(data.saleProfit);
          this.barPrice.push(data.totalProfit);
          lineChartDatas.saleYearSta.expectedData = data.yearsaleNum;
          lineChartDatas.saleYearSta.actualData = data.yearsalePrice;
          lineChartDatas.inYearSta.expectedData = data.yearinNum;
          lineChartDatas.inYearSta.actualData = data.yearinPrice;
          lineChartDatas.profitYearSta.actualData = data.yearsaleProfit;
          lineChartDatas.totalProfitYearSta.actualData = data.yeartotalProfit;
          console.log(this.lineChartDatas);
          this.saleList = data.saleList;
          this.inList = data.inList;
          this.goodsalePrice = data.goodsalePrice;
          this.salePine();
          this.inCircle();
          this.seasonData(data);
        });
      }
    },
    seasonData(data) {
      //console.log(this.dateres)
      let thisseasonInNum = [0, 0, 0, 0];
      let thisseasonInPrice = [0, 0, 0, 0];
      let thisseasonSaleNum = [0, 0, 0, 0];
      let thisseasonSalePrice = [0, 0, 0, 0];
      let thisseasonSaleProfit = [0, 0, 0, 0];
      let thisseasontotalProfit = [0, 0, 0, 0];
      for (let i = 0; i < 4; i++) {
        let sum1 = 0;
        let sum2 = 0;
        let sum3 = 0;
        let sum4 = 0;
        let sum5 = 0;
        let sum6 = 0;
        for (let j = 3 * i; j < 3 * i + 3; j++) {
          //console.log(j)
          sum1 += data.yearinNum[j];
          //console.log(this.dateres["yearinNum"][j])
          sum2 += data.yearinPrice[j];
          sum3 += data.yearsaleNum[j];
          sum4 += data.yearsalePrice[j];
          sum5 += data.yearsaleProfit[j];
          sum6 += data.yeartotalProfit[j];
        }
        thisseasonInNum[i] = sum1;
        thisseasonInPrice[i] = sum2;
        thisseasonSaleNum[i] = sum3;
        thisseasonSalePrice[i] = sum4;
        thisseasonSaleProfit[i] = sum5;
        thisseasontotalProfit[i] = sum6;
      }
      this.seasonInNum = thisseasonInNum;
      this.seasonInPrice = thisseasonInPrice;
      this.seasonSaleNum = thisseasonSaleNum;
      this.seasonSalePrice = thisseasonSalePrice;
      this.seasonSaleProfit = thisseasonSaleProfit;
      this.seasontotalProfit = thisseasontotalProfit;
      //console.log(this.seasonInNum)
      //console.log(this.seasonInPrice)
    },
    salePine() {
      let arrNum = new Array();
      this.salePineNum = [];
      for (let i = 0; i < this.saleList.length; i++) {
        if (this.saleList[i].goodname in arrNum) {
          arrNum[this.saleList[i].goodname] += this.saleList[i].num;
        } else {
          arrNum[this.saleList[i].goodname] = this.saleList[i].num;
        }
      }
      this.salePineNum = arrNum;
      console.log(arrNum);
    },
    inCircle() {
      let arrNum = new Array();
      this.inCircleNum = [];
      for (let i = 0; i < this.inList.length; i++) {
        if (this.inList[i].goodName in arrNum) {
          arrNum[this.inList[i].goodName] += this.inList[i].num;
        } else {
          arrNum[this.inList[i].goodName] = this.inList[i].num;
        }
      }
      this.inCircleNum = arrNum;
      //console.log(123)
      console.log(arrNum);
    },
    headBack() {
      this.$router.back();
    },
  },
};
</script>
