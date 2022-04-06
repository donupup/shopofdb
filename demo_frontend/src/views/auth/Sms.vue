<template>
  <div class="columns py-6">
    <div class="column is-half is-offset-one-quarter">
      <el-card shadow="never">
        <div slot="header" class="has-text-centered has-text-weight-bold">
          用户登录
        </div>
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
            <el-form-item label="电话" prop="name">
              <el-input v-model="ruleForm.phoneNumber"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')"
              >提交
              </el-button
              >
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import {sendMsg} from "@/api/msg"
export default {
  name: "Login",
  data() {
    return {
      redirect: undefined,
      loading: false,
      ruleForm: {
        phoneNumber:''
      },
      rules: {
       
      },
    };
  },
  methods: {
    submitForm(formName) {
      this.dialogFormVisible = false;
      //this.$set(this.form, "userid", this.userId);
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.ruleForm);
          console.log(123)
          sendMsg(this.ruleForm)
            .then((value) => {
              const { code, message } = value;
              console.log(value);
              if (code === 200) {
                this.$message({
                  message: "修改成功",
                  type: "success",
                });
              } else {
                this.$message.error("修改失败，" + message);
              }
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
  },
};
</script>

<style scoped>
</style>
