<template>
  <div>
        <el-page-header @back="headBack" content="商品营收占比">
  </el-page-header>
  <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
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
          <el-form-item label="商品名">
            <el-input
              style="width: 203px"
              v-model="goodid"
              placeholder="名称"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

  </div>
</template>


<script>
import { getGoodNumSta } from "@/api/sta";
import BarChart from "@/components/dashboard/BarChart";
import PieChart from "@/components/dashboard/PieChart";
import CircleChart from "@/components/dashboard/CircleChart";
import CircleIn from "@/components/dashboard/CircleIn";
export default {
  components: { BarChart,PieChart,CircleChart,CircleIn },
  data() {
    return {
      month: "",
      goodid:'',
      monthInfo: "",
      barName: ["销售", "进货","利润"],
      barNum: [],
      barPrice: [],
      saleList:[],
      inList:[],
      salePineNum: [],
      inCircleNum:[]
    };
  },
  methods: {
    handleSearchList() {
        getGoodNumSta(this.goodid).then((response) => {
        const { data } = response;
        console.log(data);
      });
        
    },
    handleResetSearch() {
     this.goodid = ''
    },
    salePine(){
      let arrNum = new Array();
      this.salePineNum = [];
      for(let i = 0 ;i < this.saleList.length;i++)
      {
        if(this.saleList[i].goodname in arrNum)
        {
          arrNum[this.saleList[i].goodname] += this.saleList[i].num;
        }
        else{
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
      console.log(123)
      console.log(arrNum);
    },
        headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
