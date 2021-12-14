<template>
  <div>
    <el-form :inline="true" :model="shoppingEle" class="demo-form-inline">
      <el-form-item label="选择商品">
        <el-select v-model="shoppingEle.good" placeholder="商品名称" value-key="id">
          <el-option v-for="good in goodInfo" v-if="good.storage" :label="good.goodname" :value="good"
                     :key="good.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商品数量">
        <el-input-number v-model="shoppingEle.num" controls-position="right" :min="1"
                         :max="shoppingEle.good.storage"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">加入购物车</el-button>
      </el-form-item>
    </el-form>
    <float :value="eleNum" :shopping-car="this.shoppingCar"></float>
  </div>


</template>

<script>
import float from "@/components/Layout/float";
import {getGoodList} from "@/api/good";

export default {
  name: "goodSale",
  components: {
    float,
  },
  data() {
    return {
      eleNum: 0,
      goodInfo: {},
      nameInCar: [],
      shoppingCar: [],
      shoppingEle: {
        good: {},
        num: '',
      },
    }
  },
  mounted() {
    this.fetchGoodList()
  },
  methods: {
    fetchGoodList() {
      getGoodList().then(response => {
        const {data} = response
        this.goodInfo = data
        console.log(this.goodInfo)
      })
    },
    onSubmit() {
      if (this.nameInCar.includes(this.shoppingEle.good.goodname)) {
        this.$message(
            {
              message: "购物车里已经有该商品了，请去购物车操作",
              type: "warning",
            })
      } else {
        if (this.shoppingEle.num > this.shoppingEle.good.storage) {
          this.$message(
              {
                message: "库存不足",
                type: "error",
              })

        } else {
          console.log(this.shoppingEle)
          this.nameInCar.push(this.shoppingEle.good.goodname)
          this.shoppingCar.push(this.shoppingEle)
          this.eleNum = this.eleNum + this.shoppingEle.num
          this.$message(
              {
                message: "添加成功",
                type: "success",
              }
          )
        }

      }
    }
  }
}
</script>

<style scoped>

</style>
