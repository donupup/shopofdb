<template>
  <div>
    <el-page-header @back="headBack" content="商品营收占比"> </el-page-header>
    <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>输入商品ID获得商品数据</span>
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
        <el-form
          :inline="true"
          :model="listQuery"
          size="small"
          label-width="140px"
        >
          <el-form-item label="商品ID">

            <el-input
              style="width: 203px"
              v-model="listQuery.goodid"
              placeholder="商品号"
            ></el-input>
          </el-form-item>
          <el-form-item label="日期">
            <el-date-picker
              v-model="value1"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <!-- <el-table :data="chosengoodInfo" style="width: 100%">
      <el-table-column prop="totalSaleNum" label="总售出数" width="180">
      </el-table-column>
      <el-table-column prop="totalSalePrice" label="总售价" width="180">
      </el-table-column>
      <el-table-column prop="totalSaleProfit" label="总销售利润">
      </el-table-column>
      <el-table-column prop="totalInNum" label="总进货数"> </el-table-column>
      <el-table-column prop="totalInPrice" label="总进价"> </el-table-column>
    </el-table> -->
    <el-descriptions class="margin-top" :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          总售出数
        </template>
        {{ chosengoodInfo.totalSaleNum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          总售价
        </template>
        {{ chosengoodInfo.totalSalePrice }}￥
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          总销售利润
        </template>
        {{ chosengoodInfo.totalSaleProfit }}￥
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          总进货数
        </template>
        {{ chosengoodInfo.totalInNum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          总进价
        </template>
        {{ chosengoodInfo.totalInPrice }}￥ </el-descriptions-item
      ><el-descriptions-item>
        <template slot="label">
          <i class="el-icon-office-building"></i>
          总购买次数
        </template>
        {{ chosengoodInfo.saleNum }}
      </el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <barchart-per-good
            :barName="barName"
            :barNum="barNum"
            :barPrice="barPrice"
          />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <bar-sex :barNum="barNumSex" />
        </div>
      </el-col>
    </el-row>

       <el-divider></el-divider>
    <el-divider content-position="center">商品总体分析</el-divider>
    <el-divider></el-divider>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <circlewithtitle :salePineNum="catsale" title="各种类销量" />
        </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <circlewithtitle :salePineNum="catsaleprice" title="各种类销售额" />
        </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <circlewithtitle :salePineNum="categorystorage" title="各种类库存" />
        </div>
      </el-col>
    </el-row>
    <el-divider></el-divider>
    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pinewithtitle :salePineNum="goodsale" title="各商品销量" />
        </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pinewithtitle :salePineNum="goodsaleprice" title="各商品销售额" />
        </div>
      </el-col>

      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pinewithtitle :salePineNum="goodstorage" title="各商品库存" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>


<script>
import { getGoodNumSta, getGoodSta } from "@/api/sta";
import PieChart from "@/components/dashboard/PieChart";
import CircleChart from "@/components/dashboard/CircleChart";
import CircleIn from "@/components/dashboard/CircleIn";
import Pinewithtitle from "../../components/dashboard/Pinewithtitle.vue";
import Circlewithtitle from "../../components/dashboard/Circlewithtitle.vue";
import BarChart from "../../components/dashboard/BarChart.vue";
import BarSex from "../../components/dashboard/BarSex.vue";
import BarchartPerGood from "../../components/dashboard/BarchartPerGood.vue";
const defaultListQuery = {
  goodid: null,
  starttime: null,
  endtime: null,
};
export default {
  components: {
    BarChart,
    PieChart,
    CircleChart,
    CircleIn,
    Pinewithtitle,
    Circlewithtitle,
    BarSex,
    BarchartPerGood,
    BarChart,
  },
  data() {
    return {
      value1: "",
       listQuery: Object.assign({}, defaultListQuery),
      month: "",
      goodid: "",
      chosengoodInfo: [],
      monthInfo: "",
      barName: ["销售", "进货", "销售利润", "净利润"],
      barNum: [],
      barPrice: [],
      barNumSex: [],
      saleList: [],
      inList: [],
      salePineNum: [],
      inCircleNum: [],
      categorystorage: [],
      catsale: [],
      catsaleprice: [],
      goodsale: [],
      goodsaleprice: [],
      goodstorage: [],
    };
  },
  mounted() {
    this.fetchGoodSta();
  },
  methods: {
    fetchGoodSta() {
      getGoodSta().then((response) => {
        const { data } = response;
        this.categorystorage = data.categorystorage;
        this.catsale = data.catsale;
        this.catsaleprice = data.catsalePrice;
        this.goodsale = data.goodsale;
        this.goodsaleprice = data.goodsalePrice;
        this.goodstorage = data.goodstorage;
        console.log(data);
      });
    },
    handleSearchList() {
      if(this.listQuery.goodid == null)
      {
        this.$message.error("请输入商品ID");
      }
      else{
        this.barNum = []
        this.barPrice = []
        this.BarNumSex = []
        console.log(this.value1)
        if(this.value1 != null)
        {
          this.listQuery.starttime = this.value1[0];
          this.listQuery.endtime = this.value1[1];
        }
        else{
          this.listQuery.starttime = null;
          this.listQuery.endtime = null;
        }
        console.log(this.listQuery)
        getGoodNumSta(this.listQuery).then((response) => {
        const { data } = response;
        this.chosengoodInfo = data;
        this.barNum.push(data.totalSaleNum);
        this.barNum.push(data.totalInNum);
        this.barNum.push(data.saleNum);
        this.barNum.push(data.inNum);
        this.barPrice.push(data.totalSalePrice);
        this.barPrice.push(data.totalInPrice);
        this.barPrice.push(data.totalSaleProfit);
        this.barPrice.push(data.totalSaleProfit - data.totalInPrice);
        this.barNumSex.push(data.maleSale);
        this.barNumSex.push(data.femaleSale);
        console.log(this.chosengoodInfo);
      });
      } 
    },
    handleResetSearch() {
      this.value1 = ''
      this.listQuery = Object.assign({}, defaultListQuery);
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
      console.log(123);
      console.log(arrNum);
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
