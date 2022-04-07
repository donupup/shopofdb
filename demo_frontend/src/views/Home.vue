<template>
  <div class="home">
    <el-page-header @back="headBack" content="主页"> </el-page-header>
    <el-divider></el-divider>
    <div style="float: right">
      <iframe
        scrolling="no"
        src="https://tianqiapi.com/api.php?style=ya&skin=baidu"
        frameborder="0"
        width="320"
        height="200"
        allowtransparency="true"
      ></iframe>
    </div>

    <h1 v-if="role == 1">欢迎登陆！</h1>
    <div v-if="role == 2">欢迎登陆！</div>
    <div v-if="role == 3">欢迎登陆！</div>
    <div v-if="role == 4">欢迎登陆！</div>
    <el-skeleton :rows="6" animated />
  </div>
</template>

<script>
// @ is an alias to /src
import { mapGetters } from "vuex";

export default {
  name: "Home",
  components: {},
  computed: {
    ...mapGetters(["token", "user", "role", "userId"]),
  },
  methods: {
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
