<template>
  <div>
    <el-page-header @back="headBack" content="销售统计">
    </el-page-header>
    <el-divider></el-divider>
    <div class="Echarts">
      <div id="main" style="width: 600px;height:400px;"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'statistic',
  methods:{
    headBack(){
      console.log(this.$router)
      this.$router.back()
    },
    myEcharts(){
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('main'));

      // 指定图表的配置项和数据
      let option = {
        title: {
          text: '商品销售表'
        },
        tooltip: {},
        legend: {
          data:['销量']
        },
        xAxis: {
          data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20]
        }]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
  },
  mounted() {
    this.myEcharts();
  }
}
</script>

<style>

</style>
