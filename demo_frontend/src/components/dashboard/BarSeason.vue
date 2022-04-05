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
    inNum: {
      type: Array,
      default: [79, 52, 200, 334, 390, 330, 220],
    },
    inPrice: {
      type: Array,
      default: [79, 52, 200, 334, 390, 330, 220],
    },
    saleNum: {
      type: Array,
      default: [79, 52, 200, 334, 390, 330, 220],
    },
    salePrice: {
      type: Array,
      default: [79, 52, 200, 334, 390, 330, 220],
    },
    saleProfit: {
      type: Array,
      default: [79, 52, 200, 334, 390, 330, 220],
    },
    totalProfit: {
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
      barName1: ["一季度", "二季度", "三季度", "四季度"],
      inNum1: [],
      inPrice1: [],
      salePrice1: [],
      saleNum1: [],
      saleProfit1: [],
      totalProfit1: [],
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
    inNum: {
      deep: true,
      handler(val) {
        this.inNum1 = val;
        console.log(val);
        this.initChart();
      },
    },
    inPrice: {
      deep: true,
      handler(val) {
        this.inPrice1 = val;
        console.log(val);
        this.initChart();
      },
    },
    saleNum: {
      deep: true,
      handler(val) {
        this.saleNum1 = val;
        console.log(val);
        this.initChart();
      },
    },
    salePrice: {
      //deep: true,
      handler(val) {
        this.salePrice1 = val;
        console.log(val);
        this.initChart();
      },
    },
    saleProfit: {
      //deep: true,
      handler(val) {
        this.saleProfit1 = val;
        console.log(val);
        this.initChart();
      },
    },
    totalProfit: {
      deep: true,
      handler(val) {
        this.totalProfit1 = val;
        console.log(val);
        this.initChart();
      },
    },
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
          text: "各季度统计",
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
            name: "销售数量",
            type: "bar",
            //stack: "vistors",
            barWidth: "5%",
            data: this.saleNum1,
            animationDuration,
          },
          {
            name: "销售价值",
            type: "bar",
            //stack: "vistors",
            barWidth: "5%",
            data: this.salePrice1,
            animationDuration,
          },
          {
            name: "进货量",
            type: "bar",
            //stack: 'vistors',
            barWidth: "5%",
            data: this.inNum1,
            animationDuration,
          },
          {
            name: "进货价",
            type: "bar",
            //stack: 'vistors',
            barWidth: "5%",
            data: this.inPrice1,
            animationDuration,
          },
          {
            name: "销售利润",
            type: "bar",
            //stack: 'vistors',
            barWidth: "5%",
            data: this.saleProfit1,
            animationDuration,
          },
          {
            name: "净利润",
            type: "bar",
            //stack: 'vistors',
            barWidth: "5%",
            data: this.totalProfit1,
            animationDuration,
          },
        ],
      });
    },
  },
};
</script>
