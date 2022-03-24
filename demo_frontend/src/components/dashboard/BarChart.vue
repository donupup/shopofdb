<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts'
import resize from './mixins/resize'
require('echarts/theme/macarons') // echarts theme

const animationDuration = 6000

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    barName:{
      type:Array,
      default:['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    barNum:{
      type:Array,
      default:[79, 52, 200, 334, 390, 330, 220]
    },
    barPrice:{
      type:Array,
      default:[80, 52, 200, 334, 390, 330, 220]
    }
  },
  data () {
    return {
      chart: null,
      barName1:['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
      barNum1:[79, 52, 200, 334, 390, 330, 220],
      barPrice1:[80, 52, 200, 334, 390, 330, 220],
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy () {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  watch: {
    barName: {
      deep: true,
      handler (val) {
        this.barName1 = val;
        this.initChart();
      }
    },
    barNum: {
      deep: true,
      handler (val) {
        this.barNum1 = val;
        this.initChart();
      }
    },
    barPrice: {
      deep: true,
      handler (val) {
        this.barPrice1 = val;
        this.initChart();
      }
    }
  },
  methods: {
    initChart () {
      this.chart = echarts.init(this.$el, 'macarons')

      this.chart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: this.barName1,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [{
          name: '数量',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: this.barNum1,
          animationDuration
        }, 
        {
          name: '总价值',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: this.barPrice1,
          animationDuration
        }, 
        // {
        //   name: 'pageC',
        //   type: 'bar',
        //   stack: 'vistors',
        //   barWidth: '60%',
        //   data: [30, 52, 200, 334, 390, 330, 220],
        //   animationDuration
        // }
        ]
      })
    }
  }
}
</script>
