<template>
  <div>
    <el-page-header @back="headBack" content="进货详情"> </el-page-header>

    <div style="text-align: right">
      <!-- <import-excel /> -->
      <el-button type="primary" @click="dialogFormVisible = true"
        >增加进货</el-button
      >
    </div>
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
          <el-form-item label="供货商">
            <el-select v-model="listQuery.pid" placeholder="供货商" clearable>
              <el-option
                v-for="item in providerInfo"
                :key="item.id"
                :label="item.pname"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品">
            <!-- <el-select
              v-model="listQuery.goodid"
              placeholder="商品名"
              clearable
            >
              <el-option
                v-for="item in goodInfo"
                :key="item.id"
                :label="item.goodname"
                :value="item.id"
              >
              </el-option>
            </el-select> -->
             <el-input
              style="width: 203px"
              v-model="listQuery.goodid"
              placeholder="商品名"
            ></el-input>
          </el-form-item>
          <el-form-item label="用户">
            <el-select
              v-model="listQuery.userid"
              placeholder="操作员"
              clearable
            >
              <el-option
                v-for="item in userInfo"
                :key="item.id"
                :label="item.username"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="日期">
            <el-date-picker
              v-model="value1"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-divider></el-divider>
    <el-table
      ref="multipleTable"
      :data="
        inportInfo.slice((this.page - 1) * this.size, this.page * this.size)
      "
      tooltip-effect="dark"
      style="width: 100%"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="ID" width="150"> </el-table-column>
      <el-table-column prop="goodName" label="商品名称" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="providerName"
        label="供货商名称"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column prop="userName" label="操作员" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="goodPrice"
        label="单价"
        show-overflow-tooltip
        width="50"
      >
      </el-table-column>
      <el-table-column
        prop="num"
        label="数量"
        show-overflow-tooltip
        width="70"
      >
      </el-table-column>
      <el-table-column prop="goodInTime" label="时间" show-overflow-tooltip>
      </el-table-column>

      <el-table-column prop="bio" label="备注" show-overflow-tooltip>
      </el-table-column>
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
      :total="this.inportInfo.length"
    >
    </el-pagination>

    <el-dialog title="进货信息" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="供应商" :label-width="formLabelWidth">
          <el-select
            v-model="form.pid"
            placeholder="请选择供货商"
            @change="selectTrigger(form.pid)"
            clearable
          >
            <el-option
              v-for="(item, index) in providerInfo"
              :key="index"
              :label="item.pname"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品" :label-width="formLabelWidth">
          <el-select v-model="form.goodid" placeholder="请选择商品" clearable>
            <el-option
              v-for="(item, index) in goodofprovider"
              :key="index"
              :label="item.goodname"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input v-model="form.num" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input
            v-model="form.bio"
            autocomplete="off"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddInport('form')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="进货信息" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="formEdit" ref="formEdit" :rules="rules">
        <el-form-item label="进货数量" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.num"
            autocomplete="off"
            :placeholder="rowItem.num"
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.bio"
            autocomplete="off"
            :placeholder="rowItem.bio"
            type="textarea"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="EditInport('formEdit')"
          >确 定</el-button
        >
      </div>
    </el-dialog>

    <el-divider></el-divider>
  </div>
</template>

<script>
import {
  getInportList,
  addInport,
  deleteInport,
  editInport,
  getConditionList,
} from "@/api/inport";
import { getGoodList } from "@/api/good";
import { getCategoryList } from "@/api/category";
import { getProviderList } from "@/api/provider";
import { getUserId } from "@/utils/auth";
import { userInfo } from "@/api/auth";
const defaultListQuery = {
  goodid: null,
  pid: null,
  userid: null,
  starttime: null,
  endtime: null,
};
export default {
  name: "inportManage",
  data() {
    return {
      value1: "",
      userId: "",
      page: 1, //第几页
      size: 3, //一页多少条
      total: 0, //总条目数
      pageSizes: [3, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000], //可选择的一页多少条
      tableData: [], //表格绑定的数据
      listQuery: Object.assign({}, defaultListQuery),
      searchKey: "",
      inportInfo: {},
      goodInfo: [],
      goodofprovider: [],
      providerInfo: [],
      categoryInfo: {},
      userInfo: [],
      form: {
        pid: "",
        goodid: "",
        userid: "",
        num: "",
        bio: "",
      },
      formEdit: {
        id: "",
        num: "",
        bio: "",
      },
      multipleSelection: [],
      rules: {
        goodname: [
          { required: true, message: "请输入供货商名", trigger: "blur" },
        ],
      },
      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      formLabelWidth: "120px",
      rowItem: {
        id: "",
        pname: "",
        pphone: "",
        paddress: "",
        plinkman: "",
      },
    };
  },
  mounted() {
    this.fetchInportList();
    this.fetchCategoryList();
    this.fetchGoodList();
    this.fetchProviderList();
    this.fetchUserList();
    this.getUser();
  },
  methods: {
    fetchUserList() {
      userInfo().then((response) => {
        const { data } = response;
        this.userInfo = data;
        console.log(this.userInfo);
      });
    },
    selectTrigger(val) {
      console.log(val);
      this.goodofprovider = [];
      this.form.goodid = "";
      let flag = 0;
      for (let i = 0; i < this.providerInfo.length; i++) {
        if (this.providerInfo[i].id == val) {
          for (let j = 0; j < this.goodInfo.length; j++) {
            if (this.goodInfo[j].providerNmae == this.providerInfo[i].pname) {
              flag = 1;
              this.goodofprovider.push(this.goodInfo[j]);
            }
          }
        }
      }
      if (flag == 0) {
        this.goodofprovider = [];
      }
    },
    getUser() {
      this.userId = getUserId();
      console.log(this.userId);
      this.form.userid = this.userId;
    },
    getList() {
      console.log(this.listQuery);
      getConditionList(this.listQuery).then((response) => {
        const { data } = response;
        this.inportInfo = data;
        console.log(this.inportInfo);
      });
    },
    handleResetSearch() {
      this.fetchInportList();
      this.listQuery = Object.assign({}, defaultListQuery);
    },
    handleSearchList() {
      this.listQuery.starttime = this.value1[0];
      this.listQuery.endtime = this.value1[1];
      this.getList();
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
    fetchInportList() {
      getInportList().then((response) => {
        const { data } = response;
        this.inportInfo = data;
        console.log(this.inportInfo);
      });
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
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
      this.$set(this.formEdit, "id", this.rowItem["id"]);
      this.$set(this.formEdit, "bio", this.rowItem["bio"]);
      this.$set(this.formEdit, "num", this.rowItem["num"]);
      console.log(this.formEdit);
      this.dialogFormVisibleEdit = true;
    },
    handleDelete(index, row) {
      console.log(row);
      deleteInport(row)
        .then((value) => {
          const { code, message } = value;
          //console.log(value)
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
    AddInport(formName) {
      this.dialogFormVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          addInport(this.form)
            .then((value) => {
              const { code, message } = value;
              //console.log(value)
              if (code === 200) {
                this.$message({
                  message: "添加成功",
                  type: "success",
                });
              } else {
                this.$message.error("添加失败，" + message);
              }
              this.$router.go(0);
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    EditInport(formName) {
      this.dialogFormVisibleEdit = false;
      console.log(this.$refs[formName]);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          editInport(this.formEdit)
            .then((value) => {
              const { code, message } = value;
              //console.log(value)
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
    fetchCategoryList() {
      getCategoryList().then((response) => {
        const { data } = response;
        this.categoryInfo = data;
        console.log(123);
        console.log(this.categoryInfo);
      });
    },
    fetchProviderList() {
      getProviderList().then((response) => {
        const { data } = response;
        this.providerInfo = data;
        console.log(this.providerInfo);
      });
    },
    fetchGoodList() {
      getGoodList().then((response) => {
        const { data } = response;
        this.goodInfo = data;
        console.log(this.goodInfo);
      });
    },
  },
};
</script>

<style scoped>
</style>
