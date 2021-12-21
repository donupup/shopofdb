// 悬浮按钮组件
<template>
  <div>
    <div style="position:relative;">
      <div class="button-box" v-drag draggable="false">
        <div class="btn-bg-img"></div>
        <el-badge :value="value" class="item">
          <el-button icon="el-icon-shopping-cart-2"  circle @click="openBox"></el-button>
        </el-badge>
      </div>
    </div>
    <el-dialog
        title="购物车"
        :visible.sync="showCar"
        direction="rtl"
        :before-close="handleClose"
        >
      <div v-for="(ele,index) in shoppingCar">
        <i class="el-icon-shopping-bag-2" style="font-size: 15px">{{ele.goodname}}</i>
        <div style="text-align:right">
          <el-input-number v-model="ele.num" controls-position="right" :min="0"
                           :max="ele.max_num"></el-input-number>
          <el-button type="danger" plain round @click="ele.num = 0">移出</el-button>
        </div>
        <el-divider></el-divider>
      </div>
      <div slot="footer" class="dialog-footer">
<!--        <el-button @click="handleClose">取 消</el-button>-->
        <el-button type="primary" @click="GoodSale">购 买</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
import {sellGood} from "@/api/good";
export default {
  name: 'float',
  props: ['value', 'shoppingCar'],
  data() {
    return {
      text: '双击显示购物车',
      isOpen: false,
      isMove: false,
      showCar: false,
    }
  },
  methods: {
    GoodSale(){
      if(this.shoppingCar.length == 0){
        this.$message(
            {
              message: "购物车里没有商品",
              type: "error",
            })
        return
      }
      console.log(this.shoppingCar)
      console.log(JSON.stringify(this.shoppingCar))
      sellGood(JSON.stringify(this.shoppingCar)).then((value)=>{
            const {code, message} = value
            //console.log(value)
            if (code === 200) {
              this.$message({
                message: '成功卖出',
                type: 'success'
              });}
            else {
              this.$message.error('卖出失败，' + message)
            }
        this.$router.go(0)
      }).catch(() => {
        this.loading = false
      })
    },
    openBox() {
      console.log(this.shoppingCar)
      this.showCar = true
    },
    handleClose(done) {
      let numAll = 0
      for(let i = 0;i < this.shoppingCar.length; i++){
        numAll = numAll + this.shoppingCar[i].num
      }
      this.value = numAll
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      this.$emit('changeNum',this.value)
    },

    mousedowm(e) { // 鼠标按下时的鼠标所在的X，Y坐标
      this.mouseDownX = e.pageX
      this.mouseDownY = e.pageY
      // 初始位置的X，Y 坐标
      // this.initX = obj.offsetLeft;
      // this.initY = obj.offsetTop;
      console.log('e', e)
      // 表示鼠标已按下
      this.flag = true
    },
    mousemove(e) {
      if (this.flag) {
        console.log('e :', e)
      }
    }
  },
  directives: {
    drag(el) {
      let oDiv = el // 当前元素
      // let self = this // 上下文
      // 禁止选择网页上的文字
      document.onselectstart = function () {
        return true
      }
      oDiv.onmousedown = function (e) {
        // 鼠标按下，计算当前元素距离可视区的距离
        let disX = e.clientX - oDiv.offsetLeft
        let disY = e.clientY - oDiv.offsetTop
        document.onmousemove = function (e) {
          // 通过事件委托，计算移动的距离
          let l = e.clientX - disX
          let t = e.clientY - disY
          // 移动当前元素
          oDiv.style.left = l + 'px'
          oDiv.style.top = t + 'px'
        }
        document.onmouseup = function (e) {
          document.onmousemove = null
          document.onmouseup = null
        }
        // return false不加的话可能导致黏连，就是拖到一个地方时div粘在鼠标上不下来，相当于onmouseup失效
        return false
      }
    }
  }
}
</script>

<style scoped>
.button-box {
  width: 80px;
  border-radius: 50%;
  position: fixed;
  bottom: 80px;
  left: 50px;
  padding-left: 15px;
  padding-top: 8px;
  cursor: pointer;
  opacity: 0.7;
  z-index: 888;
}

.btn-bg-img {
  width: 80px;
  height: 80px;
  background-size: cover;
}

.button-box:hover {
  color: white;
  opacity: 1;
}

.font-box {
  width: 80px;
  color: #3193ef;
  text-align: center;
}
</style>
