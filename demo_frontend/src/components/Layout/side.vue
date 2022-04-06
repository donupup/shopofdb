<template>
  <!-- <el-row class="tac" gutter="2"> -->
  <el-col :span="24">
    <el-menu v-if="this.roleId == 1"
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :default-openeds="openeds"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>基础信息管理</span>
        </template>
        <el-menu-item index="1-1">
          <router-link to="/good/manage"> 商品信息管理 </router-link>
        </el-menu-item>
        <el-menu-item index="1-2">
          <router-link to="/vip/manage"> 会员信息管理 </router-link>
        </el-menu-item>
        <el-menu-item index="1-3">
          <router-link to="/provider/manage">
            供货商信息管理
          </router-link></el-menu-item
        >
        <el-submenu index="1-4">
          <template slot="title">职工信息管理</template>
          <el-menu-item index="1-4-1">
            <router-link to="/employ/manage">
              职工信息详情
            </router-link></el-menu-item
          >
          <el-menu-item index="1-4-2">
            <router-link to="/employ/register">
              职工注册
            </router-link></el-menu-item
          >
        </el-submenu>
      </el-submenu>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <router-link to="/inport/manage"> 进货管理 </router-link>
      </el-menu-item>
      <el-menu-item index="3">
        <i class="el-icon-document"></i>
        <router-link to="/sale/manage"> 销售管理 </router-link>
      </el-menu-item>
       <el-menu-item index="5">
        <i class="el-icon-document"></i>
        <router-link to="/good/cart"> 商品结算 </router-link>
      </el-menu-item>
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>统计信息</span>
        </template>
        <el-menu-item index="4-8"
          ><router-link to="/sta/staday">
            日统计
          </router-link></el-menu-item
        >
        <el-menu-item index="4-7"
          ><router-link to="/sta/weekline">
            十日进销存
          </router-link></el-menu-item
        >
        <el-menu-item index="4-1"><router-link to="/sta/monthprice">
            月份营收额统计
          </router-link></el-menu-item>
        <el-menu-item index="4-2"><router-link to="/sta/stagood">
            商品分析
          </router-link></el-menu-item>
        <el-menu-item index="4-3"><router-link to="/sta/stavip">
            客户分析
          </router-link></el-menu-item>
        <el-menu-item index="4-5"><router-link to="/sta/stauser">
            员工分析
          </router-link></el-menu-item>
        <!-- <el-menu-item index="4-6">排行榜</el-menu-item> -->
      </el-submenu>
    </el-menu>
     <el-menu v-else
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :default-openeds="openeds"
    >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>基础信息管理</span>
        </template>
        <el-menu-item index="1-1">
          <router-link to="/good/manage"> 商品信息管理 </router-link>
        </el-menu-item>
        <el-menu-item index="1-2">
          <router-link to="/vip/manage"> 会员信息管理 </router-link>
        </el-menu-item>
        <el-menu-item index="1-3">
          <router-link to="/provider/manage">
            供货商信息管理
          </router-link></el-menu-item
        >
      </el-submenu>
      <el-menu-item index="2">
        <i class="el-icon-menu"></i>
        <router-link to="/inport/manage"> 进货管理 </router-link>
      </el-menu-item>
      <el-menu-item index="3">
        <i class="el-icon-document"></i>
        <router-link to="/sale/manage"> 销售管理 </router-link>
      </el-menu-item>
       <el-menu-item index="5">
        <i class="el-icon-document"></i>
        <router-link to="/good/cart"> 商品结算 </router-link>
      </el-menu-item>
      
    </el-menu>
    
  </el-col>
  <!-- </el-row> -->
</template>
<script>
import { getRole } from "@/utils/auth";
export default {
  data () {
    return {
      openeds: ['1','4'],
      roleId: "",
      uniqueOpened: false
    }
},
mounted(){
  this.roleId = getRole()
  console.log(this.roleId)
  
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