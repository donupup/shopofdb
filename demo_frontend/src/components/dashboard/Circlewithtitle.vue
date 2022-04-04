<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import * as echarts from "echarts";
import resize from "./mixins/resize";
require("echarts/theme/macarons"); // echarts theme

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
    salePineNum: {},
    title: {
      type: String,
    },
  },
  data() {
    return {
      chart: null,
      salePineNum1: [],
      data: [],
      key2: [],
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
    salePineNum: {
      deep: true,
      handler(val) {
        this.salePineNum1 = val;
        this.pushData();
      },
    },
  },
  methods: {
    pushData() {
      let data = [];
      let obj = {};
      let key1 = [];
      for (let key in this.salePineNum1) {
        obj = {};
        obj["name"] = key;
        obj["value"] = this.salePineNum1[key];
        data.push(obj);
        key1.push[key];
      }
      this.data = data;
      this.key2 = key1;
      //console.log(this.key)
      this.initChart();
    },
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");

      this.chart.setOption({
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        toolbox: {
          feature: {
            saveAsImage: {
              title: "保存为图片", //标题可自行调整
              type: "png", //下载为png格式
            },
          },
        },
        title: {
          text: this.title,
          left: "left",
          textStyle: {
            //文字颜色
            color: "#ccc",
          },
        },
        legend: {
          left: "center",
          bottom: "10",
          data: this.key,
        },
        series: [
          {
            name: this.title,
            type: "pie",
            //roseType: 'radius',
            radius: ["15%", "30%"],
            center: ["50%", "38%"],
            data: this.data,
            animationEasing: "cubicInOut",
            animationDuration: 2600,
          },
        ],
      });
    },
  },
};
</script>
