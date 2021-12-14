<template>
  <div>
    <el-descriptions class="margin-top" :title="item.username" :column="3" border>
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
          用户名
        </template>
        {{ item.username }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-collection"></i>
          学号
        </template>
        {{ item.stuNo }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          职责
        </template>
        <!--      {{item.roleId}}-->
        <el-tag size="small" v-if="item.roleId == '0'">店长</el-tag>
        <el-tag size="small" v-else-if="item.roleId == '1'">管理员</el-tag>
        <el-tag size="small" v-else="item.roleId == '2'">采购员</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          姓名
        </template>
        {{ item.alias }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          联系方式
        </template>
        {{ item.mobile }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-message"></i>
          邮箱
        </template>
        {{ item.email }}
      </el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
  </div>
</template>

<script>
import {editInfo,deleteUser} from "@/api/auth";

export default {
  name: "List",
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
        email: '',

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
    deleteUser(formName) {
      this.loading = true
      deleteUser(this.item.username).then((value) => {
        const {code, message} = value
        console.log(value)
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
              this.$message.error('修改失败，' + message    )
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
