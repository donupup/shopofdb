<template>
  <div>
    <el-page-header @back="headBack" content="会员信息"> </el-page-header>
    <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>输入会员ID获得用户数据</span>
        <el-button
          style="float: right"
          @click="handleSearchList()"
          type="primary"
          size="small"
        >
          查询结果
        </el-button>
        <el-button
          style="float: right; margin-right: 15px"
          @click="handleResetSearch()"
          size="small"
        >
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-input
          style="width: 203px"
          v-model="vipid"
          placeholder="名称"
        ></el-input>
      </div>
    </el-card>
    <el-descriptions class="margin-top" :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          会员姓名
        </template>
        {{ vipInfo.vname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          性别
        </template>
        <el-tag size="small">{{ vipInfo.vsex }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          年龄
        </template>
        {{ vipInfo.vage }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          总购买商品数
        </template>
        {{ chosenvipInfo.buyNum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          购买商品总价值
        </template>
        {{ chosenvipInfo.buyPrice }}￥
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          购买次数
        </template>
        {{ chosenvipInfo.buyTimes }}
      </el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <barvip
      :barName="chosenvipInfo.cnames"
      :barNum="chosenvipInfo.catNum"
      :barPrice="chosenvipInfo.catPrice"
    />
    <el-divider></el-divider>
    <el-divider content-position="center">顾客群体分析</el-divider>
    <el-divider></el-divider>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <circlewithtitle :salePineNum="vsexNum" title="用户性别组成" />
        </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <circlewithtitle :salePineNum="agemap" title="用户年龄组成"/>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <circlewithtitle :salePineNum="vsexSaleNum" title="男女用户购买量" />
        </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <circlewithtitle
            :salePineNum="vsexSalePrice"
            title="男女用户购买金额"
          />
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>


<script>
import { getNumOfVip, getVipSta } from "@/api/sta";
import BarChart from "@/components/dashboard/BarChart";
import PieChart from "@/components/dashboard/PieChart";
import CircleChart from "@/components/dashboard/CircleChart";
import CircleIn from "@/components/dashboard/CircleIn";
import Pinewithtitle from "../../components/dashboard/Pinewithtitle.vue";
import Circlewithtitle from "../../components/dashboard/Circlewithtitle.vue";
import BarChartDay from "../../components/dashboard/BarChartDay.vue";
import Barvip from "../../components/dashboard/Barvip.vue";
export default {
  components: {
    BarChart,
    PieChart,
    CircleChart,
    CircleIn,
    Pinewithtitle,
    Circlewithtitle,
    BarChartDay,
    Barvip,
  },
  data() {
    return {
      vipid: "",
      chosenvipInfo: "",
      vipInfo: "",
      vsexNum: "",
      vsexSaleNum: "",
      vsexSalePrice: "",
      agemap:""
    };
  },
  mounted() {
    this.fetchVipSta();
  },
  methods: {
    fetchVipSta() {
      getVipSta().then((response) => {
        const { data } = response;
        this.vsexNum = data.vsexNum;
        this.vsexSaleNum = data.vsexSaleNum;
        this.vsexSalePrice = data.vsexSalePrice;
        this.agemap = data.agemap
        console.log(data);
      });
    },
    handleSearchList() {
      console.log(this.vipid);
      if (this.vipid == "") {
        this.$message.error("请输入会员卡号");
      } else {
        getNumOfVip(this.vipid).then((response) => {
          const { data } = response;
          this.chosenvipInfo = data;
          this.vipInfo = data.vip;
          console.log(this.chosenvipInfo);
        });
      }
    },
    handleResetSearch() {
      this.vipid = "";
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
