<template>
  <div>
    <el-descriptions class="margin-top" :title="item.goodname" :column="3" border>
      <template slot="extra">
        <el-button type="primary" icon="el-icon-edit" circle @click="setDefault"></el-button>
        <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
          <el-form :model="form"
                   ref="form"
                   :rules="rules">
            <el-form-item label="商品名" :label-width="formLabelWidth" v-if="role != 2">
              <el-input v-model="form.goodname" autocomplete="off" :placeholder="item.goodname"></el-input>
            </el-form-item>
            <el-form-item label="进价" :label-width="formLabelWidth" v-if="role != 2">
              <el-input v-model="form.pricein" autocomplete="off" :placeholder="item.pricein"></el-input>
            </el-form-item>
            <el-form-item label="售价" :label-width="formLabelWidth" v-if="role != 2">
              <el-input v-model="form.pricesell" autocomplete="off" :placeholder="item.pricesell"></el-input>
            </el-form-item>
            <el-form-item label="库存" :label-width="formLabelWidth" >
              <el-input v-model="form.storage" autocomplete="off" :placeholder="item.storage"></el-input>
            </el-form-item>
            <el-form-item label="是否过期" :label-width="formLabelWidth" v-if="role != 2">
              <el-select v-model="form.status" placeholder="请选择是否过期">
                <el-option label="已过期" :value= true></el-option>
                <el-option label="未过期" :value = false></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth"v-if="role != 2">
              <el-input v-model="form.bio"  placeholder="请输入原因"  type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="editGoodInfo('form')">确 定</el-button>
          </div>
        </el-dialog>

        <el-popconfirm v-if="role != 2"
            confirm-button-text='是'
            cancel-button-text='否'
            title="确定删除该商品吗？"
            @confirm="deleteGoodInfo"
        >
          <el-button slot="reference" type="danger" icon="el-icon-delete" circle></el-button>
        </el-popconfirm>

      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          商品名
        </template>
        {{ item.goodname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          进价
        </template>
        {{ item.pricein }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          售价
        </template>
        {{ item.pricesell }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          售出数
        </template>
        {{ item.salenum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          库存
        </template>
        {{ item.storage }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          保质期
        </template>
        {{ item.shelflife }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          是否过期
        </template>
        <!--      {{item.roleId}}-->
        <el-tag size="small" v-if="item.status == false">未过期</el-tag>
        <el-tag size="small" v-else-if="item.status == true">已过期</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          最近一次修改时间
        </template>
        {{ dayjs(item.modifyTime).format("YYYY/MM/DD dddd HH:mm:ss.SSS A") }}
      </el-descriptions-item>

    </el-descriptions>
    <el-divider></el-divider>
  </div>
</template>

<script>
import {deleteGood,editGood} from "@/api/good";
import {mapGetters} from "vuex";

export default {
  name: "goodList",
  props: {
    item: {
      type: Object
    },
  },

  data() {
    return {
      loading: false,
      dialogFormVisible: false,
      form: {
        id:'',
        goodname: '',
        pricein: '',
        pricesell: '',
        storage: '',
        status: '',
        bio:''
      },
      formLabelWidth: '120px',
      rules: {
        goodname: [
          {required: true, message: "请输入商品名", trigger: "blur"}
        ],
      }
    }

  },
  computed: {
    ...mapGetters(["token", "user","role"]),
  },
  methods: {
    checkNum(){
      if(this.role == 2){
        if(this.form.storage > this.item.storage)
          return true
      }
      return false
    },
    setDefault(){
      this.dialogFormVisible = true
      this.$set(this.form,'id',this.item.id)
      this.$set(this.form,'goodname',this.item.goodname)
      this.$set(this.form,'pricein',this.item.pricein)
      this.$set(this.form,'pricesell',this.item.pricesell)
      this.$set(this.form,'storage',this.item.storage)
      this.$set(this.form,'status',this.item.status)
    },
    deleteGoodInfo() {
      this.loading = true
      //console.log(this.item.id)
      deleteGood(this.item.id).then((value) => {
        const {code, message} = value
        //console.log(value)
        if (code === 200) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
        } else {
          this.$message.error('删除失败，' + message)
        }
        this.$router.go(0)
      }).catch(() => {
        this.loading = false
      })
    },
    editGoodInfo(formName) {
      this.dialogFormVisible = false
      if(!this.checkNum()){
        this.$message.error("货物变少了~您只有权力进货，不能出货哦~")
        return
      }
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          //console.log(this.form)
          editGood(this.form).then((value) => {
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
    }
  }


}
</script>

<style scoped>

</style>
