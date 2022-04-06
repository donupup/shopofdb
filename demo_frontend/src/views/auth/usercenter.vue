<template>
  <div>
    <el-page-header @back="headBack" content="个人中心"> </el-page-header>
    <el-divider></el-divider>
    <el-descriptions class="margin-top" :column="1" :size="size" border>
      <template slot="extra">
        <el-button type="primary" size="small" @click="dialogFormVisible = true"
          >修改信息</el-button
        >
      </template>
      <el-descriptions-item label="工号">{{
        userInfo.id
      }}</el-descriptions-item>
      <el-descriptions-item label="用户名">{{
        userInfo.username
      }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{
        userInfo.mobile
      }}</el-descriptions-item>
      <el-descriptions-item label="姓名">{{
        userInfo.alias
      }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">
        {{ userInfo.email }}
      </el-descriptions-item>
      <el-descriptions-item label="学号">{{
        userInfo.stuNo
      }}</el-descriptions-item>
      <el-descriptions-item label="个人描述">{{
        userInfo.bio
      }}</el-descriptions-item>
    </el-descriptions>

    <el-dialog title="个人信息" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="rules" label-position="right">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input
            style="width: 203px"
            v-model="form.username"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input
            v-model="form.email"
            autocomplete="off"
            placeholder="请输入邮箱"
          ></el-input>
        </el-form-item>
        <el-form-item label="学号" :label-width="formLabelWidth">
          <el-input
            v-model="form.stuNo"
            autocomplete="off"
            placeholder="请输入学号"
          ></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input
            v-model="form.alias"
            autocomplete="off"
            placeholder="请输入姓名"
          ></el-input>
        </el-form-item>
        <el-form-item label="个人描述" :label-width="formLabelWidth">
          <el-input
            v-model="form.bio"
            autocomplete="off"
            type="textarea"
            placeholder="个人描述"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="EditForm('form')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getUserById, EditUserById } from "@/api/auth";
import { getUserId } from "@/utils/auth";
export default {
  data() {
    return {
      userInfo: {},
      userId: "",
      dialogFormVisible: false,
      form: {
        userid: "",
        alias: "",
        email: "",
        stuNo: "",
        username: "",
        bio: "",
      },
      formLabelWidth: "120px",
    };
  },
  mounted() {
    this.fetchUserInfo();
  },
  methods: {
    fetchUserInfo() {
      this.userId = getUserId();
      getUserById(this.userId).then((response) => {
        const { data } = response;
        this.userInfo = data;
        console.log(this.userInfo);
      });
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
    EditForm(formName) {
      this.dialogFormVisible = false;
      this.$set(this.form, "userid", this.userId);
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          EditUserById(this.form)
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
              getUserById(this.userId).then((response) => {
                const { data } = response;
                this.userInfo = data;
                console.log(this.userInfo);
              });
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
