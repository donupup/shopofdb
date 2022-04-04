<template>
  <div>
    <el-page-header @back="headBack" content="当日交易信息"> </el-page-header>
    <el-divider></el-divider>
    <el-descriptions class="margin-top" :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
        当日销售数
        </template>
        {{dayInfo.saleNum  }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          日销售额
        </template>
       {{dayInfo.salePrice}}￥
        
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
     <bar-chart-day :barName="this.dayInfo.cnames" :barNum="this.dayInfo.catsaleNum" :barPrice="this.dayInfo.catsalePrice" :barProfit="this.dayInfo.catsaleProfit"/>
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
import BarChartDay from '../../components/dashboard/BarChartDay.vue';
export default {
  components: {
    BarChart,
    PieChart,
    CircleChart,
    CircleIn,
    Pinewithtitle,
    Circlewithtitle,
    BarChartDay,
  },
  data() {
    return {
     dayInfo:''
    };
  },
  mounted() {
      this.fetchDaySta();
  },
  methods: {
    fetchDaySta(){
        getDaySta().then((response) => {
        const { data } = response;
        this.dayInfo = data;
        console.log(data);
      });
      },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
