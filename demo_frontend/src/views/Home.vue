<template>
  <div class="home">
    <el-page-header @back="headBack" content="主页"> </el-page-header>
    <el-divider></el-divider>
    <div v-if="role == 0">尊敬的店长，欢迎登陆！</div>
    <div v-if="role == 1">尊敬的管理员，欢迎登陆！</div>
    <div v-if="role == 2">尊敬的采购员，欢迎登陆！</div>
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
    ...mapGetters(["token", "user", "role"]),
  },
  methods: {
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
  },
};
</script>
