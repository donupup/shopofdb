<template>
  <div>
    <el-page-header @back="headBack" content="当日交易信息"> </el-page-header>
    <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <i class="el-icon-search"></i>
      <span>选择日期进行统计</span>
      <div class="block">
        <span class="demonstration"></span>
        <el-date-picker v-model="day" type="date" placeholder="选择日期">
        </el-date-picker>
        <el-button @click="handleSearch()" type="primary"> 查询结果 </el-button>
      </div>
    </el-card>
    <el-divider></el-divider>
    <el-descriptions class="margin-top" :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          当日销售数
        </template>
        {{ dayInfo.saleNum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          日销售额
        </template>
        {{ dayInfo.salePrice }}￥
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          日进货数
        </template>
        {{ dayInfo.inNum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          日进货总价
        </template>
        {{ dayInfo.inPrice }}￥
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          销售利润
        </template>
        {{ dayInfo.saleProfit }}￥
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          净利润
        </template>
        {{ dayInfo.totalProfit }}￥
      </el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <barday-1 :barName="barName" :barNum="barNum" :barPrice="barPrice" />
    <el-divider></el-divider>
    <bar-chart-day
      :barName="this.dayInfo.cnames"
      :barNum="this.dayInfo.catsaleNum"
      :barPrice="this.dayInfo.catsalePrice"
      :barProfit="this.dayInfo.catsaleProfit"
    />
  </div>
</template>


<script>
import { getDaySta } from "@/api/sta";
import BarChart from "@/components/dashboard/BarChart";
import PieChart from "@/components/dashboard/PieChart";
import CircleChart from "@/components/dashboard/CircleChart";
import CircleIn from "@/components/dashboard/CircleIn";
import Pinewithtitle from "../../components/dashboard/Pinewithtitle.vue";
import Circlewithtitle from "../../components/dashboard/Circlewithtitle.vue";
import BarChartDay from "../../components/dashboard/BarChartDay.vue";
import Barday1 from "../../components/dashboard/Barday1.vue";
export default {
  components: {
    BarChart,
    PieChart,
    CircleChart,
    CircleIn,
    Pinewithtitle,
    Circlewithtitle,
    BarChartDay,
    Barday1,
  },
  data() {
    return {
      dayInfo: "",
      day: "",
      barName: ["销售", "进货", "利润", "净利润"],
      barNum: [],
      barPrice: [],
    };
  },
  mounted() {
    this.fetchDaySta();
  },
  methods: {
    handleSearch() {
      if (this.day == "" || this.day == null) {
        this.$message.error("请选择日期");
      } else {
        this.barNum = [];
        this.barPrice = [];
        getDaySta(this.day).then((response) => {
          const { data } = response;
          this.dayInfo = data;
          console.log(data);
          this.barNum.push(data.saleNum);
          this.barNum.push(data.inNum);
          this.barNum.push(data.saleNum);
          this.barNum.push(data.inNum);
          this.barPrice.push(data.salePrice);
          this.barPrice.push(data.inPrice);
          this.barPrice.push(data.saleProfit);
          this.barPrice.push(data.totalProfit);
        });
      }
    },
    fetchDaySta() {
      this.barNum = [];
      this.barPrice = [];
      //console.log(new Date())
      getDaySta(new Date()).then((response) => {
        const { data } = response;
        this.dayInfo = data;
        console.log(data);
        this.barNum.push(data.saleNum);
        this.barNum.push(data.inNum);
        this.barNum.push(data.saleNum);
        this.barNum.push(data.inNum);
        this.barPrice.push(data.salePrice);
        this.barPrice.push(data.inPrice);
        this.barPrice.push(data.saleProfit);
        this.barPrice.push(data.totalProfit);
        console.log(1111);
        console.log(this.barNum);
        console.log(this.barPrice);
      });
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
