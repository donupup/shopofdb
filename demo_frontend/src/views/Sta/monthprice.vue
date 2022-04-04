<template>
  <div>
    <el-card class="filter-container" shadow="never">
      <div class="block">
        <span class="demonstration"></span>
        <el-date-picker v-model="month" type="month" placeholder="选择月">
        </el-date-picker>
        <el-button @click="handleSearch()" type="primary"> 查询结果 </el-button>
      </div>
    </el-card>
  </div>
</template>


<script>
export default {
  data() {
    return {
      month: "",
    };
  },
  methods: {
      handleSearch(){
          console.log(month);
      }
  },
};
</script>
