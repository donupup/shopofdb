
<!--暂时放弃使用-->

<template>
  <div class="content">
    <!-- 面包屑 -->
    <div class="breadcrumb">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item v-for="(item,index) in breadcrumb" :key="index" :to="{ path: item.path}">{{item.meta.title}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 路由出口 -->
  </div>

</template>

<script>
export default {
  name: "bread",
  data() {
    return {

    }
  },
  computed: {
    breadcrumb() {
      // 根据路由配置meta中的showInbreadcrumb字段过滤
      let matchedAddr;
      matchedAddr
      = this.$route.matched
      //因为首页比较特殊，必须一直显示在面包屑第一个，如果没有首页路由信息，手动添加到最前面
      // if(matchedAddr[0].meta.title !== '首页'){
      //     matchedAddr.unshift(
      //         {
      //           path:'/',
      //           meta:{
      //            title:"首页",
      //             showInbreadcrumb:true
      //
      //           }
      //         }
      //     )
      //   }
      return matchedAddr;
      },
    }
  }
</script>

<style scoped>

</style>
