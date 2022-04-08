<template>
  <!-- <el-row class="tac" gutter="2"> -->
  <el-col :span="24">
    <el-menu
      v-if="this.roleId == 1"
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :default-openeds="openeds"
      router="true"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>基础信息管理</span>
        </template>
        <el-menu-item index="/good/manage"> 商品信息管理 </el-menu-item>
        <el-menu-item index="/vip/manage"> 会员信息管理 </el-menu-item>
        <el-menu-item index="/provider/manage"> 供货商信息管理 </el-menu-item>
        <el-submenu index="1-4">
          <template slot="title">职工信息管理</template>
          <el-menu-item index="/employ/manage"> 职工信息详情 </el-menu-item>
          <el-menu-item index="/employ/register"> 职工注册 </el-menu-item>
        </el-submenu>
      </el-submenu>
      <el-menu-item index="/inport/manage">
        <i class="el-icon-menu"></i>
        进货管理
      </el-menu-item>
      <el-menu-item index="/sale/manage">
        <i class="el-icon-document"></i>
        销售管理
      </el-menu-item>
      <el-menu-item index="/good/cart">
        <i class="el-icon-money"></i>
        商品结算
      </el-menu-item>
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>统计信息</span>
        </template>
        <el-menu-item index="/sta/staday">日统计 </el-menu-item>
        <el-menu-item index="/sta/weekline"> 十日进销存 </el-menu-item>
        <el-menu-item index="/sta/monthprice"> 月份营收额统计 </el-menu-item>
        <el-menu-item index="/sta/stagood"> 商品分析 </el-menu-item>
        <el-menu-item index="/sta/stavip"> 客户分析 </el-menu-item>
        <el-menu-item index="/sta/stauser"> 员工分析</el-menu-item>
        <!-- <el-menu-item index="4-6">排行榜</el-menu-item> -->
      </el-submenu>
    </el-menu>

    <el-menu
      v-if="this.roleId == 2"
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :default-openeds="openeds"
      router="true"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>基础信息管理</span>
        </template>
        <el-menu-item index="/good/manage"> 商品信息管理 </el-menu-item>
        <el-menu-item index="/vip/manage"> 会员信息管理 </el-menu-item>
        <el-menu-item index="/provider/manage"> 供货商信息管理 </el-menu-item>
      </el-submenu>
      <el-menu-item index="/sale/manage">
        <i class="el-icon-document"></i>
        销售管理
      </el-menu-item>
      <el-menu-item index="/good/cart">
        <i class="el-icon-document"></i>
        商品结算
      </el-menu-item>
    </el-menu>

    <el-menu
      v-if="this.roleId == 3"
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :default-openeds="openeds"
      router="true"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>基础信息管理</span>
        </template>
        <el-menu-item index="/good/manage"> 商品信息管理 </el-menu-item>
        <el-menu-item index="/vip/manage"> 会员信息管理 </el-menu-item>
        <el-menu-item index="/provider/manage"> 供货商信息管理 </el-menu-item>
      </el-submenu>
      <el-menu-item index="/inport/manage">
        <i class="el-icon-menu"></i>
        进货管理
      </el-menu-item>
    </el-menu>

    <el-menu
      v-if="this.roleId == 4"
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :default-openeds="openeds"
      router="true"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>基础信息管理</span>
        </template>
        <el-menu-item index="/good/manage"> 商品信息管理 </el-menu-item>
        <el-menu-item index="/vip/manage"> 会员信息管理 </el-menu-item>
        <el-menu-item index="/provider/manage"> 供货商信息管理 </el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>统计信息</span>
        </template>
        <el-menu-item index="/sta/staday">日统计 </el-menu-item>
        <el-menu-item index="/sta/weekline"> 十日进销存 </el-menu-item>
        <el-menu-item index="/sta/monthprice"> 月份营收额统计 </el-menu-item>
        <el-menu-item index="/sta/stagood"> 商品分析 </el-menu-item>
        <el-menu-item index="/sta/stavip"> 客户分析 </el-menu-item>
        <el-menu-item index="/sta/stauser"> 员工分析</el-menu-item>
        <!-- <el-menu-item index="4-6">排行榜</el-menu-item> -->
      </el-submenu>
    </el-menu>
  </el-col>
  <!-- </el-row> -->
</template>
<script>
import { getRole } from "@/utils/auth";
export default {
  data() {
    return {
      openeds: ["1","4"],
      roleId: "",
      uniqueOpened: false,
    };
  },
  mounted() {
    this.roleId = getRole();
    console.log(this.roleId);
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>