<template>
  <div class="columns py-6">
    <div class="column is-half is-offset-one-quarter">
      <el-steps :active="active" finish-status="success" align-center>
        <el-step title="验证身份"></el-step>
        <el-step title="修改密码"></el-step>
      </el-steps>
      <el-tabs v-model="activeName" @tab-click="handleClick" stretch>
        <el-tab-pane name="first">
          <el-card shadow="never">
            <div slot="header" class="has-text-centered has-text-weight-bold">
              用户密码修改
            </div>
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
                      <el-button @click="submitFormCheckMsg('msgForm')">
                        发送验证码
                      </el-button>
                    </el-col>
                  </el-row>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    style="width: 100%"
                    @click="submitCheck('msgForm')"
                    >验证身份</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane name="second">
          <el-card shadow="never">
            <div slot="header" class="has-text-centered has-text-weight-bold">
              重置密码
            </div>
            <div>
              <el-form
                ref="ruleForm"
                v-loading="secretLoading"
                :model="ruleForm"
                status-icon
                :rules="rules"
                label-width="100px"
                class="demo-ruleForm"
              >
                <el-form-item label="新密码" prop="pass">
                  <el-input
                    v-model="ruleForm.pass"
                    type="password"
                    autocomplete="off"
                  />
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass">
                  <el-input
                    v-model="ruleForm.checkpass"
                    type="password"
                    autocomplete="off"
                  />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitForm('ruleForm')"
                    >修改密码
                  </el-button>
                  <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { changeSecretCheck } from "@/api/msg";
import { getUserId } from "@/utils/auth";
import { sendCheck, sendChange } from "@/api/auth";
export default {
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      active: 1,
      loading: false,
      secretLoading: true,
      activeName: "first",
      ruleForm: {
        pass: "",
        checkpass: "",
        userid: "",
      },
      msgForm: {
        phoneNumber: "",
        code: "",
        userid: "",
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
        checkpass: [
          { required: true, message: "请再次输入密码", trigger: "blur" },
          { validator: validatePass, trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    this.msgForm.userid = getUserId();
    this.ruleForm.userid = getUserId();
  },
  methods: {
    submitFormCheckMsg(formName) {
      //this.$set(this.form, "userid", this.userId);
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.msgForm);
          console.log(123);
          changeSecretCheck(this.msgForm)
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
    submitCheck(formName) {
      //this.$set(this.form, "userid", this.userId);
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.msgForm);
          console.log(123);
          sendCheck(this.msgForm)
            .then((value) => {
              const { code, message } = value;
              console.log(value);
              if (code === 200) {
                this.$message({
                  message: "验证成功",
                  type: "success",
                });
                this.active = 2;
                this.activeName = "second";
                this.secretLoading = false;
              } else {
                this.$message.error("验证失败，" + message);
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
          console.log(this.ruleForm);
          sendChange(this.ruleForm)
            .then((value) => {
              const { code, message } = value;
              if (code === 200) {
                this.$message({
                  message: "密码修改成功,请重新登录",
                  type: "success",
                });
                setTimeout(() => {
                  this.$store.dispatch("user/logout").then(() => {
                    this.$message.info("退出登录成功");
                    this.$router.go(0);
                    setTimeout(() => {
                      this.$router.push({ path: this.redirect || "/" });
                    }, 500);
                  });
                }, 2000);
              } else {
                this.$message.error("注册失败，" + message);
              }
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
  },
};
</script>
<style>
</style>