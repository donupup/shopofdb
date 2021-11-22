<template>
  <div>
    <el-descriptions class="margin-top" :title="item.goodname" :column="3" border>
      <template slot="extra">
        <el-button type="primary" icon="el-icon-edit" circle @click="setDefault"></el-button>
        <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
          <el-form :model="form"
                   ref="form"
                   :rules="rules">
            <el-form-item label="用户名" :label-width="formLabelWidth">
              {{ item.username }}
            </el-form-item>
            <el-form-item label="学号" :label-width="formLabelWidth">
              <el-input v-model="form.stuNo" autocomplete="off" :placeholder="item.stuNo"></el-input>
            </el-form-item>
            <el-form-item label="职责" :label-width="formLabelWidth">
              <el-select v-model="form.role" placeholder="请选择职责">
                <el-option label="店长" value="0"></el-option>
                <el-option label="管理员" value="1"></el-option>
                <el-option label="采购员" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="姓名" :label-width="formLabelWidth">
              <el-input v-model="form.alias" autocomplete="off" :placeholder="item.alias"></el-input>
            </el-form-item>
            <el-form-item label="联系方式" :label-width="formLabelWidth">
              <el-input v-model="form.mobile" autocomplete="off" :placeholder="item.mobile"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" :label-width="formLabelWidth">
              <el-input v-model="form.email" autocomplete="off" :placeholder="item.email"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="editUserInfo('form')">确 定</el-button>
          </div>
        </el-dialog>

        <el-popconfirm
            confirm-button-text='是'
            cancel-button-text='否'
            title="确定删除该用户吗？"
            @confirm="deleteUser"
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
          最近一次进货时间
        </template>
        {{ item.modifyTime }}
      </el-descriptions-item>

    </el-descriptions>
    <el-divider></el-divider>
  </div>
</template>

<script>
import {editInfo} from "@/api/auth";

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
        username: '',
        stuNo: '',
        role: '',
        alias: '',
        mobile: '',
        email: ''
      },
      formLabelWidth: '120px',
      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"}
        ],
        role: [
          {required: true, message: '请选择职责', trigger: 'blur'},
          {
            message: "请选择职责",
            trigger: 'blur'
          }
        ]
      }
    }

  },

  methods: {
    setDefault(){
      this.dialogFormVisible = true
      this.$set(this.form,'username',this.item.username)
      this.$set(this.form,'stuNo',this.item.stuNo)
      this.$set(this.form,'role',this.item.roleId)
      this.$set(this.form,'alias',this.item.alias)
      this.$set(this.form,'mobile',this.item.mobile)
      this.$set(this.form,'email',this.item.email)
    },
    deleteUser() {
      console.log("shanchule")
    },
    editUserInfo(formName) {
      this.dialogFormVisible = false
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          console.log(this.form)
          editInfo(this.form).then((value) => {
            const {code, message} = value
            console.log(value)
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
