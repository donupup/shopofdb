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

        <div v-if="role == 0">尊敬的店长，欢迎登陆！</div>
    <div v-if="role == 1">尊敬的管理员，欢迎登陆！</div>
    <div v-if="role == 2">尊敬的采购员，欢迎登陆！</div>
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
