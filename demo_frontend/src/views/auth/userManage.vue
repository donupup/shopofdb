<template>
  <div>
    <el-page-header @back="headBack" content="职工详情"> </el-page-header>

    <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float: right"
          @click="handleSearchList()"
          type="primary"
          size="small"
        >
          查询结果
        </el-button>
        <el-button
          style="float: right; margin-right: 15px"
          @click="handleResetSearch()"
          size="small"
        >
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form
          :inline="true"
          :model="listQuery"
          size="small"
          label-width="140px"
        >
          <el-form-item label="职工用户名">
            <el-input
              style="width: 203px"
              v-model="listQuery.username"
              placeholder="职工用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="职工工号：">
            <el-input
              style="width: 203px"
              v-model="listQuery.stuNo"
              placeholder="工号"
            ></el-input>
          </el-form-item>
          <el-form-item label="职工角色">
            <el-select
              v-model="listQuery.role"
              placeholder="请选择角色"
              clearable
            >
              <el-option
                v-for="item in roleInfo"
                :key="item.id"
                :label="item.rolename"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-table
      ref="multipleTable"
      :data="userInfo.slice((this.page - 1) * this.size, this.page * this.size)"
      tooltip-effect="dark"
      style="width: 100%"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="职工ID" width="200"> </el-table-column>
      <el-table-column prop="username" label="职工用户名" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="alias" label="职工姓名" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="stuNo" label="职工工号" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="mobile" label="职工电话" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="bio" label="职工备注" show-overflow-tooltip>
      </el-table-column>
      <!-- <el-table-column
        prop="vbalance"
        label="会员卡余额"
        show-overflow-tooltip
      >
      </el-table-column> -->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>


          <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="page"
      :page-size="size"
      :page-sizes="pageSizes"
      layout="total, sizes, prev, pager, next, jumper"
      :total="this.userInfo.length"
    >
    </el-pagination>

    <el-dialog title="职工信息" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="formEdit" ref="formEdit" :rules="rules">
        <el-form-item label="职工用户名" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.username"
            autocomplete="off"
            :placeholder="rowItem.vname"
          ></el-input>
        </el-form-item>
        <el-form-item label="职责" :label-width="formLabelWidth">
          <el-select v-model="formEdit.role" placeholder="Select">
            <el-option
              v-for="(item, key) in roleInfo"
              :key="key"
              :label="item.rolename"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职工联系方式" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.mobile"
            autocomplete="off"
            :placeholder="rowItem.vphone"
          ></el-input>
        </el-form-item>
        <el-form-item label="职工姓名" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.alias"
            autocomplete="off"
            :placeholder="rowItem.vaddress"
          ></el-input>
        </el-form-item>
        <el-form-item label="职工工号" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.stuNo"
            autocomplete="off"
            :placeholder="rowItem.vbalance"
          ></el-input>
        </el-form-item>
        <el-form-item label="职工邮箱" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.email"
            autocomplete="off"
            :placeholder="rowItem.vbalance"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo('formEdit')"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <el-divider></el-divider>
  </div>
</template>

<script>
import { userInfo } from "@/api/auth";
import { editInfo, deleteUser, getRoleList, getConditionList } from "@/api/auth";
const defaultListQuery = {
  username: null,
  stuNo: null,
  role: null,
};
export default {
  name: "userManage",
  data() {
    return {
         page: 1, //第几页
      size: 3, //一页多少条
      total: 0, //总条目数
      pageSizes: [3, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000], //可选择的一页多少条
      tableData: [], //表格绑定的数据
      listQuery: Object.assign({}, defaultListQuery),
      searchKey: "",
      userInfo: {},
      roleInfo: {},
      formEdit: {
        role: "",
        username: "",
        stuNo: "",
        mobile: "",
        alias: "",
        email: "",
      },
      multipleSelection: [],
      rules: {
        goodname: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
      },
      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      formLabelWidth: "120px",
      rowItem: {
        id: "",
        vname: "",
        vsex: "",
        vphone: "",
        vaddress: "",
        vbalance: "",
      },
    };
  },
  mounted() {
    this.fetchUserList();
    this.fetchRole();
  },
  methods: {
    getList() {
      getConditionList(this.listQuery).then((response) => {
        const { data } = response;
        this.userInfo = data;
        console.log(this.userInfo);
      });
    },
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
      this.fetchUserList();
    },
    handleSearchList() {;
      this.getList();
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
    fetchUserList() {
      userInfo().then((response) => {
        const { data } = response;
        this.userInfo = data;
        console.log(this.userInfo);
      });
    },
    fetchRole() {
      getRoleList().then((response) => {
        const { data } = response;
        this.roleInfo = data;
        console.log(123);
        console.log(this.roleInfo);
      });
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.rowItem = row;
      //console.log(this.multipleSelection);
      //console.log(this.rowItem)
      this.$set(this.formEdit, "role", this.rowItem["roleId"]);
      this.$set(this.formEdit, "username", this.rowItem["username"]);
      this.$set(this.formEdit, "mobile", this.rowItem["mobile"]);
      this.$set(this.formEdit, "stuNo", this.rowItem["stuNo"]);
      this.$set(this.formEdit, "alias", this.rowItem["alias"]);
      this.$set(this.formEdit, "email", this.rowItem["email"]);
      this.dialogFormVisibleEdit = true;
    },
    handleDelete(index, row) {
      console.log(row);
      deleteUser(row["username"])
        .then((value) => {
          const { code, message } = value;
          console.log(value);
          if (code === 200) {
            this.$message({
              message: "删除成功",
              type: "success",
            });
          } else {
            this.$message.error("删除失败，" + message);
          }
          this.$router.go(0);
        })
        .catch(() => {
          this.loading = false;
        });
    },
    editUserInfo(formName) {
      this.dialogFormVisible = false;
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          editInfo(this.formEdit)
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
              this.$router.go(0);
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    getTabelData() {
      //allData为全部数据
      this.tableData = this.info.slice(
        (this.page - 1) * this.size,
        this.page * this.size
      );
      this.total = this.info.length;
    },

    //获取表格数据，自行分页（splice）
    getTabelData2() {
      let data = JSON.parse(JSON.stringify(this.info));
      console.log(data);
      this.tableData = data.splice((this.page - 1) * this.size, this.size);
      this.total = this.info.length;
    },
    //page改变时的回调函数，参数为当前页码
    currentChange(val) {
      console.log("翻页，当前为第几页", val);
      this.page = val;
      this.getTabelData2();
    },
    //size改变时回调的函数，参数为当前的size
    sizeChange(val) {
      console.log("改变每页多少条，当前一页多少条数据", val);
      this.size = val;
      this.page = 1;
      this.getTabelData2();
    },
  },
};
</script>

<style scoped>
</style>
