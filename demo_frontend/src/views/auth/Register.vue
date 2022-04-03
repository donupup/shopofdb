<template>
  <div>
  <el-page-header @back="headBack" content="注册员工">
  </el-page-header>
  <el-divider></el-divider>
  <div class="columns py-6">
    <div class="column is-half is-offset-one-quarter">
      <el-card shadow="never">
        <div slot="header" class="has-text-centered has-text-weight-bold">
          新用户入驻
        </div>
        <div>
          <el-form
              ref="ruleForm"
              v-loading="loading"
              :model="ruleForm"
              status-icon
              :rules="rules"
              label-width="100px"
              class="demo-ruleForm"
          >
            <el-form-item label="账号" prop="name">
              <el-input v-model="ruleForm.name"/>
            </el-form-item>

            <el-form-item label="密码" prop="pass">
              <el-input
                  v-model="ruleForm.pass"
                  type="password"
                  autocomplete="off"
              />
            </el-form-item>

            <el-form-item label="确认密码" prop="checkPass">
              <el-input
                  v-model="ruleForm.checkPass"
                  type="password"
                  autocomplete="off"
              />
            </el-form-item>

            <el-form-item label="学号" prop="stuNo">
              <el-input v-model="ruleForm.stuNo" autocomplete="off"/>
            </el-form-item>

            <el-form-item label="职责" prop="role">
              <el-select v-model="ruleForm.role" placeholder="Select">
                <el-option
                    v-for="(item,key) in roleInfo"
                    :key="key"
                    :label="item.rolename"
                    :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>


            <el-form-item>
              <el-button
                  type="primary"
                  @click="submitForm('ruleForm')"
              >立即注册
              </el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
  </div>
</template>

<script>
import {userRegister,getRoleList} from '@/api/auth'

export default {
  name: 'Register',
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      loading: false,
      roleInfo:{},
      options: [
        {
          value: '1',
          label: '管理员',
        },
        {
          value: '2',
          label: '采购员',
        }
      ],
      ruleForm: {
        name: '',
        pass: '',
        checkPass: '',
        stuNo: '',
        role: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {
            min: 2,
            max: 10,
            message: '长度在 2 到 10 个字符',
            trigger: 'blur'
          }
        ],
        pass: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {
            min: 6,
            max: 20,
            message: '长度在 6 到 20 个字符',
            trigger: 'blur'
          }
        ],
        checkPass: [
          {required: true, message: '请再次输入密码', trigger: 'blur'},
          {validator: validatePass, trigger: 'blur'}
        ],
        stuNo: [
          {required: true, message: '请输入学号', trigger: 'blur'},
          {
            message: '请输入正确的学号',
            trigger: ['blur', 'change']
          }
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
  mounted() {
    this.fetchRole();
    
  },
  methods: {
    fetchRole(){
      getRoleList().then((response) => {
        const { data } = response;
        this.roleInfo = data;
        console.log(123)
        console.log(this.roleInfo);
      });
      
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          console.log(this.ruleForm)
          userRegister(this.ruleForm)
              .then((value) => {
                const {code, message} = value
                if (code === 200) {
                  this.$message({
                    message: '账号注册成功',
                    type: 'success'
                  })
                  setTimeout(() => {
                    this.loading = false
                    this.$router.push({path: this.redirect || '/employ/manage'})
                  }, 0.1 * 1000)
                } else {
                  this.$message.error('注册失败，' + message)
                }
              })
              .catch(() => {
                this.loading = false
              })
        } else {
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    headBack(){
      this.$router.back()
    }
  }
}
</script>

<style scoped>
</style>
