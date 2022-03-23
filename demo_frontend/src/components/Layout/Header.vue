<template>
  <header class="header has-background-white has-text-black">
    <b-navbar class="container" :fixed-top="true" type="is-white">
      <!--        在调整格式时一直在-->
      <template slot="brand">
        <b-navbar-item tag="div">
          <img :src="logoUrl" alt="logo" />
        </b-navbar-item>
        <b-navbar-item
          class="is-hidden-desktop"
          tag="router-link"
          :to="{ path: '/' }"
        >
          主页
        </b-navbar-item>
      </template>

      <template slot="start">
        <b-navbar-item tag="router-link" :to="{ path: '/' }" >
          主页
        </b-navbar-item>
      </template>


      <template v-if="role === '0'">
        <template slot="start">
            <b-navbar-dropdown label = "人员管理">
              <b-navbar-item
                  tag="router-link"
                  :to="{ path: `/employ/manage` }"
              >
                人员管理
              </b-navbar-item>
              <hr class="dropdown-divider" />
              <b-navbar-item tag="router-link"
                             :to="{ path: `/employ/register` }">
                注册员工
              </b-navbar-item>
            </b-navbar-dropdown>
        </template>
          <template slot="start">
            <b-navbar-item tag="router-link" :to="{ path: '/good/manage' }">
              商品库存
            </b-navbar-item>
          </template>
        <template slot="start">
          <b-navbar-item tag="router-link" :to="{ path: '/good/sale_sta' }">
            销售统计
          </b-navbar-item>
        </template>
        <template slot="start">
          <b-navbar-item tag="router-link" :to="{ path: '/good/bill_sta' }">
            账单
          </b-navbar-item>
        </template>
        <template slot="start">
          <b-navbar-item tag="router-link" :to="{ path: '/sta' }">
            图表
          </b-navbar-item>
        </template>
      </template>

      <template v-if="role === '1'">
        <template slot="start">
          <b-navbar-item tag="router-link" :to="{ path: '/good/sale' }">
            商品销售
          </b-navbar-item>
        </template>
        <template slot="start">
          <b-navbar-item tag="router-link" :to="{ path: '/good/manage' }">
            商品库存
          </b-navbar-item>
        </template>
        <template slot="start" >
          <b-navbar-item tag="router-link" :to="{ path: '/good/sale_sta' }">
            销售统计
          </b-navbar-item>
        </template>
      </template>

      <template v-if="role === '2'">
        <template slot="start">
          <b-navbar-item tag="router-link" :to="{ path: '/good/manage' }">
            商品库存
          </b-navbar-item>
        </template>
        <template slot="start">
          <b-navbar-item tag="router-link" :to="{ path: '/good/bill_sta' }">
            账单
          </b-navbar-item>
        </template>
      </template>





      <template slot="end">
        <b-navbar-item tag="div" >
          <b-switch v-model="darkMode" passive-type="is-warning" type="is-dark">
            {{ darkMode ? "🌙" : "🌞" }}
          </b-switch>
        </b-navbar-item>

        <b-navbar-item v-if="token == null || token === ''" tag="div">
          <div class="buttons">
            <b-button
              class="is-light"
              tag="router-link"
              :to="{ path: '/login' }"
            >
              登录
            </b-button>
          </div>
        </b-navbar-item>

          <b-navbar-item v-else tag="a" @click="logout"> 退出登录 </b-navbar-item>

      </template>
    </b-navbar>
  </header>
</template>

<script>
import {
  disable as disableDarkMode,
  enable as enableDarkMode,
} from "darkreader";
import { getDarkMode, setDarkMode } from "@/utils/auth";
import { mapGetters } from "vuex";
export default {
  name: "Header",
  data() {
    return {
      logoUrl: require("@/assets/shop.svg"),
      searchKey: "",
      darkMode: false,
    };
  },
  computed: {
    ...mapGetters(["token", "user","role"]),
  },
  watch: {
    // 监听Theme模式
    darkMode(val) {
      if (val) {
        enableDarkMode({});
      } else {
        disableDarkMode();
      }
      setDarkMode(this.darkMode);
    },
  },
  created() {
    // 获取cookie中的夜间还是白天模式
    this.darkMode = getDarkMode();
    if (this.darkMode) {
      enableDarkMode({});
    } else {
      disableDarkMode();
    }
  },
  methods: {
    async logout() {
      this.$store.dispatch("user/logout").then(() => {
        this.$message.info("退出登录成功");
        setTimeout(() => {
          this.$router.push({ path: this.redirect || "/" });
        }, 500);
      });
    },
  },
};
</script>

<style scoped>
input {
  width: 80%;
  height: 86%;
}
</style>
