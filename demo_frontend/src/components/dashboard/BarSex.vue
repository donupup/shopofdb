<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from "echarts";
import resize from "./mixins/resize";
require("echarts/theme/macarons"); // echarts theme

const animationDuration = 6000;

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
    },
    barName: {
      type: Array,
      default: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
    },
    barNum: {
      type: Array,
      default: [79, 52, 200, 334, 390, 330, 220],
    },
    // barPrice: {
    //   type: Array,
    //   default: [80, 52, 200, 334, 390, 330, 220],
    // },
  },
  data() {
    return {
      chart: null,
      barName1: ["男会员","女会员"],
      barNum1: [79, 52, 200, 334, 390, 330, 220],
      barPrice1: [80, 52, 200, 334, 390, 330, 220],
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  watch: {
    barName: {
      deep: true,
      handler(val) {
        this.barName1 = val;
        this.initChart();
      },
    },
    barNum: {
      deep: true,
      handler(val) {
        this.barNum1 = val;
        this.initChart();
      },
    },
    // barPrice: {
    //   deep: true,
    //   handler(val) {
    //     this.barPrice1 = val;
    //     this.initChart();
    //   },
    // },
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");

      this.chart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        toolbox: {
          feature: {
            saveAsImage: {
              title: "保存为图片", //标题可自行调整
              type: "png", //下载为png格式
            },
          },
        },
        // dataZoom: [
        //   {
        //     type: "slider", //类型
        //     show: true, //显示
        //     yAxisIndex: [0], //使用y轴的index,默认值为0
        //     left: "93%", //距离左边位置百分比
        //     start: 0, //数据窗口范围的起始百分比
        //     end: 40, //数据窗口范围的结束百分比
        //   },
        //   {
        //     type: "inside", //类型
        //     yAxisIndex: [0], //使用y轴的index,默认值为0
        //     start: 0, //数据窗口范围的起始百分比
        //     end: 36, //数据窗口范围的结束百分比
        //   },
        // ],

        grid: {
          top: 10,
          left: "2%",
          right: "2%",
          bottom: "3%",
          containLabel: true,
        },
                title: {
          text: "男女购买量",
          left: "center",
          textStyle: {
            //文字颜色
            color: "#ccc",
          },
        },
        xAxis: [
          {
            type: "category",
            data: this.barName1,
            axisTick: {
              alignWithLabel: true,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisTick: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: "数量",
            type: "bar",
            //stack: "vistors",
            barWidth: "20%",
            data: this.barNum1,
            animationDuration,
          },
        //   {
        //     name: "总价值",
        //     type: "bar",
        //     //stack: "vistors",
        //     barWidth: "20%",
        //     data: this.barPrice1,
        //     animationDuration,
        //   },
          // {
          //   name: 'pageC',
          //   type: 'bar',
          //   stack: 'vistors',
          //   barWidth: '60%',
          //   data: [30, 52, 200, 334, 390, 330, 220],
          //   animationDuration
          // }
        ],
      });
    },
  },
};
</script>
