<template>
  <div>
    <div class="mb-5">
      <Header></Header>
    </div>
    <bread></bread>
    <div class="container context">
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
};
</script>
<style>
</style>
