<template>

  <div>
    <el-page-header @back="headBack" content="商品详情">
    </el-page-header>
    <div style="text-align:right">
      <el-button type="primary" @click="dialogFormVisible = true">增加商品</el-button>
    </div>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
      <el-form :model="form"
               ref="form"
               :rules="rules">
        <el-form-item label="商品名" :label-width="formLabelWidth">
          <el-input v-model="form.goodname" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="进价" :label-width="formLabelWidth">
          <el-input v-model="form.pricein" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="售价" :label-width="formLabelWidth">
          <el-input v-model="form.pricesell" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="库存" :label-width="formLabelWidth">
          <el-input v-model="form.storage" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="保质期" :label-width="formLabelWidth">
          <el-input v-model="form.shelflife" autocomplete="off" ></el-input>
        </el-form-item>
        <el-form-item label="商品描述" :label-width="formLabelWidth">
          <el-input v-model="form.bio"  type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入商品描述" ></el-input>
        </el-form-item>
        <el-form-item label="是否过期" :label-width="formLabelWidth">
          <el-select v-model="form.status" placeholder="请选择是否过期">
            <el-option label="已过期" :value= true></el-option>
            <el-option label="未过期" :value = false></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddGoods('form')">确 定</el-button>
      </div>
    </el-dialog>
    <el-divider></el-divider>
    <goodList v-for="(item,index) in goodInfo" :item="item" :key="index"></goodList>
  </div>

</template>

<script>
import goodList from "@/components/goodList";
import {getGoodList, addGood, editGood} from "@/api/good";
export default {
  name: "goodManage",
  components:{goodList},
  data(){
    return{
      goodInfo:{},
      form: {
        goodname: '',
        pricein: '',
        pricesell: '',
        storage: '',
        status: '',
        shelflife:'',
        bio:''
      },
      rules: {
        goodname: [
          {required: true, message: "请输入商品名", trigger: "blur"}
        ],
      },
      dialogFormVisible : false,
      formLabelWidth : '120px'
    }
  },
  mounted() {
    this.fetchGoodList()
  },
  methods:{
    fetchGoodList(){
      getGoodList().then(response => {
        const {data} = response
        this.goodInfo= data
        console.log(this.goodInfo)
      })
    },
    AddGoods(formName){
      this.dialogFormVisible = false
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          //console.log(this.form)
          addGood(this.form).then((value) => {
            const {code, message} = value
            //console.log(value)
            if (code === 200) {
              this.$message({
                message: '修改成功',
                type: 'success'
              });
            } else {
              this.$message.error('修改失败，' + message)
            }
            this.$router.go(0)
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    headBack(){
      console.log(this.$router)
      this.$router.back()
    }


  }

}
</script>

<style scoped>

</style>
