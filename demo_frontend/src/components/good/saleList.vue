<template>
  
  <div>{{item.goodname}}</div>
</template>

<script>

import {mapGetters} from "vuex";

export default {
  name: "saleList",
  props: {
    item: {
      type: Object
    },
  },

  data() {
    return {
    }

  },
  computed: {
    ...mapGetters(["token", "user","role"]),
  },
  methods: {
    

    }
}
</script>

<style scoped>

</style>
