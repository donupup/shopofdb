<template>
  <div>
    <el-page-header @back="headBack" content="会员详情"> </el-page-header>
    <div style="text-align: right">
      <!-- <import-excel /> -->
      <el-button type="primary" size="small" @click="dialogFormVisible = true"
        >增加会员</el-button
      >
      <exportExcel
            :id="'export'"
            :name="'注册会员表'"
            :button="'导出'"
          ></exportExcel>
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
          <el-form-item label="会员用户名">
            <el-input
              style="width: 203px"
              v-model="listQuery.vname"
              placeholder="用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="会员卡号：">
            <el-input
              style="width: 203px"
              v-model="listQuery.id"
              placeholder="卡号"
            ></el-input>
          </el-form-item>
          <el-form-item label="会员电话：">
            <el-input
              style="width: 203px"
              v-model="listQuery.vphone"
              placeholder="电话号"
            ></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-select
              v-model="listQuery.vsex"
              placeholder="请选择角色"
              clearable
            >
              <el-option :label="'男'" :value="'男'"> </el-option>
              <el-option :label="'女'" :value="'女'"> </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <div hidden="hidden">
      <el-table
      ref="multipleTable"
      :data="vipInfo"
      tooltip-effect="dark"
      style="width: 100%"
      border
      id="export"
      @selection-change="handleSelectionChange"
    >
     
      <el-table-column prop="id" label="会员卡号" width="200">
      </el-table-column>
      <el-table-column prop="vname" label="会员姓名" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="vsex" label="性别" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="vage" label="年龄" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="vphone" label="联系方式" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="vaddress"
        label="注册地址"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column prop="vbalance" label="会员卡余额" show-overflow-tooltip>
      </el-table-column>
    </el-table>
    </div>

    <el-divider></el-divider>
    <el-table
      ref="multipleTable"
      :data="vipInfo.slice((this.page - 1) * this.size, this.page * this.size)"
      tooltip-effect="dark"
      style="width: 100%"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="会员卡号" width="200">
      </el-table-column>
      <el-table-column prop="vname" label="会员姓名" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="vsex" label="性别" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="vage" label="年龄" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="vphone" label="联系方式" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="vaddress"
        label="注册地址"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column prop="vbalance" label="会员卡余额" show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            
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
      :total="this.vipInfo.length"
    >
    </el-pagination>

    <el-dialog title="会员信息" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="会员姓名" :label-width="formLabelWidth">
          <el-input v-model="form.vname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="会员性别" :label-width="formLabelWidth">
          <el-select v-model="form.vsex" placeholder="请选择性别">
            <el-option label="男" :value="'男'"></el-option>
            <el-option label="女" :value="'女'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会员联系方式" :label-width="formLabelWidth">
          <el-input v-model="form.vphone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="会员注册地址" :label-width="formLabelWidth">
          <el-input v-model="form.vaddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="会员卡余额" :label-width="formLabelWidth">
          <el-input v-model="form.vbalance" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="会员年龄" :label-width="formLabelWidth">
          <el-input v-model="form.vage" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddVip('form')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="会员信息" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="formEdit" ref="formEdit" :rules="rules">
        <el-form-item label="会员姓名" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.vname"
            autocomplete="off"
            :placeholder="rowItem.vname"
          ></el-input>
        </el-form-item>
        <el-form-item label="会员性别" :label-width="formLabelWidth">
          <el-select v-model="formEdit.vsex" placeholder="请选择性别">
            <el-option label="男" :value="'男'"></el-option>
            <el-option label="女" :value="'女'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会员联系方式" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.vphone"
            autocomplete="off"
            :placeholder="rowItem.vphone"
          ></el-input>
        </el-form-item>
        <el-form-item label="会员注册地址" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.vaddress"
            autocomplete="off"
            :placeholder="rowItem.vaddress"
          ></el-input>
        </el-form-item>
        <el-form-item label="会员卡余额" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.vbalance"
            autocomplete="off"
            :placeholder="rowItem.vbalance"
          ></el-input>
        </el-form-item>
        <el-form-item label="年龄" :label-width="formLabelWidth">
          <el-input
            v-model="formEdit.vage"
            autocomplete="off"
            :placeholder="rowItem.vage"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="EditVip('formEdit')">确 定</el-button>
      </div>
    </el-dialog>

    <el-divider></el-divider>
  </div>
</template>

<script>
import goodList from "@/components/good/goodList";
import { getVipList, addVip, deleteVip, editVip,getConditionList } from "@/api/vip";
import ExportExcel from "@/components/ExportExcel";
const defaultListQuery = {
  id: null,
  vname: null,
  vsex: null,
  vphone: null,
};
export default {
  name: "vipManage",
  components: { goodList,ExportExcel },
  data() {
    return {
       page: 1, //第几页
      size: 3, //一页多少条
      total: 0, //总条目数
      pageSizes: [3, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000], //可选择的一页多少条
      tableData: [], //表格绑定的数据
      listQuery: Object.assign({}, defaultListQuery),
      searchKey: "",
      vipInfo: {},
      form: {
        vname: "",
        vsex: "",
        vphone: "",
        vaddress: "",
        vbalance: "",
        vage:'',
      },
      formEdit: {
        id: "",
        vname: "",
        vsex: "",
        vphone: "",
        vaddress: "",
        vbalance: "",
        vage:'',
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
    this.fetchVipList();
  },
  methods: {
    getList() {
      console.log(this.listQuery)
      getConditionList(this.listQuery).then((response) => {
        const { data } = response;
        this.vipInfo = data;
        console.log(this.vipInfo);
      });
    },
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
      this.fetchVipList();
    },
    handleSearchList() {
      this.getList();
    },
    fetchVipList() {
      getVipList().then((response) => {
        const { data } = response;
        this.vipInfo = data;
        console.log(this.vipInfo);
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
      //console.log(this.multipleSelection);
      //console.log(this.rowItem)
      this.$set(this.formEdit, "id", this.rowItem["id"]);
      this.$set(this.formEdit, "vname", this.rowItem["vname"]);
      this.$set(this.formEdit, "vphone", this.rowItem["vphone"]);
      this.$set(this.formEdit, "vaddress", this.rowItem["vaddress"]);
      this.$set(this.formEdit, "vsex", this.rowItem["vsex"]);
      this.$set(this.formEdit, "vbalance", this.rowItem["vbalance"]);
      this.$set(this.formEdit, "vage", this.rowItem["vage"]);
      console.log(this.formEdit);
      this.dialogFormVisibleEdit = true;
    },
    handleDelete(index, row) {
      console.log(row);
      deleteVip(row)
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
          this.getList()
        })
        .catch(() => {
          this.loading = false;
        });
    },
    AddVip(formName) {
      this.dialogFormVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          addVip(this.form)
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
              this.getList()
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    EditVip(formName) {
      this.dialogFormVisibleEdit = false;
      console.log(this.$refs[formName]);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          editVip(this.formEdit)
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
              this.getList()
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
