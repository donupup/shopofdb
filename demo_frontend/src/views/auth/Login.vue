<template>
  <div class="columns py-6">
    <div class="column is-half is-offset-one-quarter">
      <el-card shadow="never">
        <div slot="header" class="has-text-centered has-text-weight-bold">
          用户登录
        </div>
        <el-tabs v-model="activeName" @tab-click="handleClick" stretch>
          <el-tab-pane label="短信验证码登录" name="first">
            <div>
              <el-form :model="msgForm" :rules="rules" ref="msgForm">
                <el-form-item prop="phoneNumber">
                  <el-input
                    placeholder="请输入手机号"
                    v-model="msgForm.phoneNumber"
                  >
                    <i slot="prefix" class="el-icon-phone"></i>
                  </el-input>
                </el-form-item>
                <el-form-item prop="code">
                  <el-row :gutter="20">
                    <el-col :span="16">
                      <el-input
                        placeholder="请输入验证码"
                        v-model="msgForm.code"
                      >
                        <i slot="prefix" class="el-icon-tickets"></i>
                      </el-input>
                    </el-col>
                    <el-col :span="6">
                      <el-button
                        @click="submitFormMsg('msgForm')"
                        :disabled="disabled"
                      >
                        发送验证码
                      </el-button>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    style="width: 100%"
                    @click="submitLogin('msgForm')"
                    >登录</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
          <el-tab-pane label="密码登录" name="second">
            <div>
              <el-form
                v-loading="loading"
                :model="ruleForm"
                status-icon
                :rules="rules"
                ref="ruleForm"
                label-width="100px"
                class="demo-ruleForm"
              >
                <el-form-item label="账号" prop="name">
                  <el-input v-model="ruleForm.name"></el-input>
                </el-form-item>

                <el-form-item label="密码" prop="pass">
                  <el-input
                    type="password"
                    v-model="ruleForm.pass"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>

                <el-form-item label="记住" prop="delivery">
                  <el-switch v-model="ruleForm.rememberMe"></el-switch>
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" @click="submitForm('ruleForm')"
                    >提交
                  </el-button>
                  <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>

<script>
import { sendMsg } from "@/api/msg";
import { sendLogin } from "@/api/auth";
export default {
  name: "Login",
  data() {
    return {
      activeName: "second",
      redirect: undefined,
      loading: false,
      ruleForm: {
        name: "",
        pass: "",
        rememberMe: true,
      },
      msgForm: {
        phoneNumber: "",
        code: "",
      },
      rules: {
        name: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            min: 2,
            max: 15,
            message: "长度在 2 到 15 个字符",
            trigger: "blur",
          },
        ],
        pass: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 20,
            message: "长度在 6 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    submitLogin(formName) {
      this.dialogFormVisible = false;
      //this.$set(this.form, "userid", this.userId);
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.msgForm);
          console.log(123);
          sendLogin(this.msgForm)
            .then((value) => {
              const { code, message } = value;
              console.log(value);
              if (code === 200) {
                this.$message({
                  message: "登录成功",
                  type: "success",
                });
                this.$store.dispatch("user/loginWithMsg", value).then(() => {
                  setTimeout(() => {
                    this.loading = false;
                    this.$router.push({ path: this.redirect || "/" });
                  }, 0.1 * 100);
                  this.$router.go(0);
                });
              } else {
                this.$message.error("登录失败，" + message);
              }
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    submitFormMsg(formName) {
      this.dialogFormVisible = false;
      //this.$set(this.form, "userid", this.userId);
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.msgForm);
          console.log(123);
          sendMsg(this.msgForm)
            .then((value) => {
              const { code, message } = value;
              console.log(value);
              if (code === 200) {
                this.$message({
                  message: "发送成功",
                  type: "success",
                });
              } else {
                this.$message.error("发送失败，" + message);
              }
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch("user/login", this.ruleForm)
            .then(() => {
              this.$message({
                message: "恭喜你，登录成功",
                type: "success",
                duration: 2000,
              });
              setTimeout(() => {
                this.loading = false;

                this.$router.push({ path: this.redirect || "/" });
              }, 0.1 * 100);
              this.$router.go(0);
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
  },
};
</script>

<style scoped>
</style>
