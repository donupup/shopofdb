<template>
  <div class="app">
    <el-container style="height: 20%">
      <el-header>
        <Header></Header>
      </el-header>
      <el-container>
        <el-aside width="190px">
          <side></side>
        </el-aside>
        <el-container>
          <el-main>
            <router-view :key="this.$route.fullPath"></router-view>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Header from "@/components/Layout/Header";
import Footer from "@/components/Layout/Footer";
import bread from "@/components/bread";
import Side from "./components/Layout/side.vue";

export default {
  name: "App",
  components: { Header, Footer, bread, Side, Footer },
  data() {
    return {
      Height: 0,
    };
  },
  mounted() {
    //动态设置内容高度 让footer始终居底   header+footer的高度是100
    this.Height = document.documentElement.clientHeight - 300;
    //监听浏览器窗口变化
    window.onresize = () => {
      this.Height = document.documentElement.clientHeight - 300;
    };
  },
};
</script>
<style>
.el-aside {
  display: block;
  position: absolute;
  left: 0;
  top: 60px;
  bottom: 0;
}
.el-header {
  position: relative;
  width: 100%;
  height: 60px;
}
.el-main {
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  overflow-y: scroll;
}
.el-footer {
  position: relative;
  width: 100%;
  height: 60px;
}
</style>
