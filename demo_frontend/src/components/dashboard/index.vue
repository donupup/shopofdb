<template>
  <div class="dashboard-editor-container">
    <!--<github-corner class="github-corner" />-->

    <panel-group @handleSetLineChartData="handleSetLineChartData"  />

    <el-row style="background: #fff; padding: 16px 16px 0; margin-bottom: 32px">
      <line-chart :chart-data="this.lineChartData" :data="this.xarryDay" />
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// import GithubCorner from '@/components/GithubCorner'
import PanelGroup from "./PanelGroup";
import RaddarChart from "./RaddarChart";
import PieChart from "./PieChart";
import LineChart from "./LineChart";
import BarChart from "./BarChart";
import { getInList, getOutList, getSaleList,getGoodList } from "@/api/good";
//import TransactionTable from './TransactionTable'
//import BoxCard from './BoxCard'

 let lineChartDatas = {
  saleLineSta: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145],
  },
  inLineSta: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130],
  },
  outLineSta: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130],
  },
  storageLineSta: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130],
  },
};

export default {
  name: "DashboardAdmin",
  components: {
    // GithubCorner,
    PanelGroup,
    RaddarChart,
    LineChart,
    PieChart,
    BarChart,
    //TransactionTable,
    //BoxCard
  },
  data() {
    return {
      lineChartData: lineChartDatas.shoppings,
      saleList: [],
      inList: [],
      outList: [],
      goodList:[],
      saleLineNum: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      saleLinePrice: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      inLineNum: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      inLinePrice: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      outLineNum: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      outLinePrice: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      storageLineNum: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      storageLinePrice: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
      xarryDay:['0','0','0','0','0','0','0','0','0','0'],
    };
  },
  mounted() {
    this.fetchGoodList();

  },
  methods: {
    fetchGoodList() {
      getSaleList().then((response) => {
        const { data } = response;
        this.saleList = data;
        //console.log(new Date(this.saleList[0].goodSoldTime).getDate())
        //console.log(this.saleList)
      });
      getInList().then((response) => {
        const { data } = response;
        this.inList = data;
        //console.log(this.inList);
      });
      getOutList().then((response) => {
        const { data } = response;
        this.outList = data;
        //console.log(this.outList);
      });
      getGoodList().then((response) => {
        const { data } = response;
        this.goodList = data;
        console.log(this.goodList);
      });
    },
    handleSetLineChartData(type) {
      this.lineChartData = lineChartDatas[type];
      this.saleLine();
      this.inLine();
      this.outLine();
      this.storageLine();
      //this.storageLine();
      //console.log(this.outLineNum)
    },
    dateInDay(first, second, num) {
      if (
        first.getFullYear() === second.getFullYear() &&
        first.getMonth() === second.getMonth() &&
        first.getDate() - second.getDate() === num
      )
        return true;
      else return false;
    },
    saleLine() {
      this.saleLineNum = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      this.saleLinePrice= [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      let now = new Date();
      for (let i = 0; i < 10; i++) {
        for (let j = 0; j < this.saleList.length; j++) {
          // console.log(now);
          // console.log(this.saleList[j].goodSoldTime);
          if (this.dateInDay(now, new Date(this.saleList[j].goodSoldTime), i)) {
            //console.log(123);
            this.saleLineNum[9 - i] += this.saleList[j].num;
            this.saleLinePrice[9 - i] +=
              this.saleList[j].num * this.saleList[j].pricesellNow;
              
          }
        }
        this.xarryDay[9 - i] = now.getMonth() +"."+ (now.getDate() - i);
      }
      lineChartDatas.saleLineSta.expectedData = this.saleLineNum;
      lineChartDatas.saleLineSta.actualData = this.saleLinePrice;
      // console.log(this.saleLineNum)
      // console.log(this.saleLinePrice)
    },
    inLine(){
      this.inLineNum = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
       this.inLinePrice = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      let now = new Date();
      for(let i = 0;i < 10;i ++)
      {
        for(let j = 0;j < this.inList.length;j ++)
        {
          if (this.dateInDay(now, new Date(this.inList[j].goodInTime), i)) {
            //console.log(123);
            this.inLineNum[9 - i] += this.inList[j].num;
            this.inLinePrice[9 - i] +=
              this.inList[j].num * this.inList[j].inPrice;
              
          }
        }
      }
      lineChartDatas.inLineSta.expectedData = this.inLineNum;
      lineChartDatas.inLineSta.actualData = this.inLinePrice;
    },
    outLine(){
      this.outLineNum = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      this.outLinePrice= [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      let now = new Date();
      for(let i = 0;i < 10;i ++)
      {
        for(let j = 0;j < this.outList.length;j ++)
        {
          if (this.dateInDay(now, new Date(this.outList[j].goodOutTime), i)) {
            //console.log(123);
            this.outLineNum[9 - i] += this.outList[j].num;
            this.outLinePrice[9 - i] =0;
              
          }
        }
      }
      lineChartDatas.outLineSta.expectedData = this.outLineNum;
      lineChartDatas.outLineSta.actualData = this.outLinePrice;
      //console.log(this.outLineNum)
    },
    storageLine(){
      let sum = 0;
      this.storageLineNum = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      this.storageLinePrice = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
      for(let i = 0;i < this.goodList.length; i ++)
      {
        sum += this.goodList[i].storage;
      }
      this.storageLineNum[9] = sum;
      for(let j = 8;j >=0; j --)
      {
        this.storageLineNum[j] = this.storageLineNum[j + 1] + this.saleLineNum[j] + this.outLineNum[j] - this.inLineNum[j];
      }
      lineChartDatas.storageLineSta.expectedData = this.storageLineNum;
      lineChartDatas.storageLineSta.actualData = this.storageLinePrice;
    }
  },
};
</script>


<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: #f6f6f6;
  position: relative;
  margin: -20px 0 0 -10px;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
