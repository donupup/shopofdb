<template>
  <div>
    <el-page-header @back="headBack" content="商品销售"> </el-page-header>
    <el-divider></el-divider>
    <el-form :inline="true" :model="shoppingEle" class="demo-form-inline">
      <el-form-item label="选择商品">
        <el-select
          v-model="shoppingEle.good"
          placeholder="商品名称"
          value-key="id"
        >
          <el-option
            v-for="good in goodInfo"
            v-if="good.storage"
            :label="good.goodname"
            :value="good"
            :key="good.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品数量">
        <el-input-number
          v-model="shoppingEle.num"
          controls-position="right"
          :min="1"
          :max="shoppingEle.good.storage"
        ></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">加入购物车</el-button>
      </el-form-item>
    </el-form>
    <good-list
      :item="shoppingEle.good"
      :need-opera="false"
      v-if="
        !(
          Object.prototype.isPrototypeOf(shoppingEle.good) &&
          Object.keys(shoppingEle.good).length === 0
        )
      "
    ></good-list>
    <float
      :value="eleNum"
      :shopping-car="this.shoppingCar"
      @changeNum="changeValue"
    ></float>
  </div>
</template>

<script>
import float from "@/components/Layout/float";
import { getGoodList } from "@/api/good";
import goodList from "@/components/good/goodList";

export default {
  name: "goodSale",
  components: {
    float,
    goodList,
  },
  data() {
    return {
      eleNum: 0,
      goodInfo: {},
      nameInCar: [],
      shoppingCar: [],
      shoppingEle: {
        good: {},
        num: "",
      },
    };
  },
  beforeRouteLeave(to, from, next) {
    console.log(123);
    // 这里需要elementui的支持，如果使用其他界面组件自行替换即可
    this.$confirm("正在离开本页面，本页面内所有未保存数据都会丢失", "警告", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        // 正常跳转
        next();
      })
      .catch(() => {
        window.history.go(1);
      });
  },
  mounted() {
    this.fetchGoodList();
    window.onbeforeunload = function (e) {
      e = e || window.event;

      // 兼容IE8和Firefox 4之前的版本
      if (e) {
        e.returnValue = "关闭提示";
      }

      // Chrome, Safari, Firefox 4+, Opera 12+ , IE 9+
      return "关闭提示";
    };
    //window.addEventListener("beforeunload", this.beforeUnloadHandler, false);
  },
  // destroyed() {
  //   window.removeEventListener("beforeunload", this.beforeUnloadHandler, false);
  // },
  methods: {
    // beforeUnloadHandler(e) {
    //   e.returnValue = "离开此页面？"; // 此处返回任意字符串，不返回null即可，不能修改默认提示内容
    // },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
    changeValue(data) {
      this.eleNum = data;
    },
    fetchGoodList() {
      getGoodList().then((response) => {
        const { data } = response;
        this.goodInfo = data;
        console.log(this.goodInfo);
      });
    },
    onSubmit() {
      let obj = this.shoppingEle.good;
      if (
        Object.prototype.isPrototypeOf(obj) &&
        Object.keys(obj).length === 0
      ) {
        this.$message({
          message: "请选择商品",
          type: "error",
        });
        return;
      }
      if (this.nameInCar.includes(this.shoppingEle.good.goodname)) {
        this.$message({
          message: "购物车里已经有该商品了，请去购物车操作",
          type: "warning",
        });
      } else {
        if (this.shoppingEle.num > this.shoppingEle.good.storage) {
          this.$message({
            message: "库存不足",
            type: "error",
          });
        } else {
          //console.log(this.shoppingEle)
          this.nameInCar.push(this.shoppingEle.good.goodname);
          let selected = {
            goodname: "",
            max_num: "",
            num: "",
          };
          selected.goodname = this.shoppingEle.good.goodname;
          selected.num = this.shoppingEle.num;
          selected.max_num = this.shoppingEle.good.storage;
          this.shoppingCar.push(selected);
          this.eleNum = this.eleNum + this.shoppingEle.num;
          this.$message({
            message: "添加成功",
            type: "success",
          });
        }
      }
    },
  },
};
</script>

<style scoped>
</style>
