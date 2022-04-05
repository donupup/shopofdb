<template>
  <div>
    <el-page-header @back="headBack" content="员工信息"> </el-page-header>
    <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>输入员工用户名进行查询</span>
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
          v-model="username"
          placeholder="名称"
        ></el-input>
      </div>
    </el-card>
    <el-descriptions class="margin-top" :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          员工用户名
        </template>
        {{ userInfo.username}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          员工姓名
        </template>
        {{ userInfo.alias }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          总售出商品数
        </template>
        {{ chosenuserInfo.saleNum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          总售出商品价格
        </template>
        {{ chosenuserInfo.salePrice }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          总进货数
        </template>
        {{ chosenuserInfo.inNum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          总进货价格
        </template>
        {{ chosenuserInfo.inPrice }}
      </el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
    <baruser :barName="barName" :barNum="barNum" :barPrice="barPrice"/>
    
  </div>
</template>


<script>
import { getNumOfVip, getVipSta,getNumOfUser,getUserSta } from "@/api/sta";
import BarChart from "@/components/dashboard/BarChart";
import PieChart from "@/components/dashboard/PieChart";
import CircleChart from "@/components/dashboard/CircleChart";
import CircleIn from "@/components/dashboard/CircleIn";
import Pinewithtitle from "../../components/dashboard/Pinewithtitle.vue";
import Circlewithtitle from "../../components/dashboard/Circlewithtitle.vue";
import BarChartDay from "../../components/dashboard/BarChartDay.vue";
import Barvip from "../../components/dashboard/Barvip.vue";
import Baruser from '../../components/dashboard/Baruser.vue';
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
    Baruser,
  },
  data() {
    return {
      username: "",
      chosenuserInfo:'',
      userInfo:'',
      barName: ["销售","进货"],
      barNum:[],
      barPrice:[],

    };
  },
  mounted() {
    this.fetchUserSta();
  },
  methods: {
    fetchUserSta() {
      getUserSta().then((response) => {
        const { data } = response;
        console.log(data);
      });
    },
    handleSearchList() {
      console.log(this.username);
      if (this.username == "") {
        this.$message.error("请输入会员卡号");
      } else {
                this.barNum = []
                this.barPrice = []
        getNumOfUser(this.username).then((response) => {
          const { data } = response;
          this.userInfo = data.user;
          this.chosenuserInfo = data;
          this.barNum.push(data.saleNum)
          this.barNum.push(data.inNum)
          this.barPrice.push(data.salePrice)
          this.barPrice.push(data.inPrice)
          console.log(data);
        });
      }
    },
    handleResetSearch() {
      this.username = "";
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
