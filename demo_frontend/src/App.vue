<template>
  <div>
    <div class="mb-5">
      <Header></Header>
    </div>
    <div class="container context" v-bind:style="{minHeight: Height+'px'}">
        <router-view :key="this.$route.fullPath"></router-view>
    </div>

    <div>
      <Footer></Footer>
    </div>
  </div>


</template>

<script>

import Header from "@/components/Layout/Header"
import Footer from "@/components/Layout/Footer"
import bread from "@/components/bread";

export default {
  name: "App",
  components: {Header,Footer,bread},
  data() {
    return {
      Height: 0
    }
  },
  mounted(){
    //动态设置内容高度 让footer始终居底   header+footer的高度是100
    this.Height = document.documentElement.clientHeight - 300;
    //监听浏览器窗口变化　
    window.onresize = ()=> {this.Height = document.documentElement.clientHeight -300}
  }
};
</script>
<style>
</style>
