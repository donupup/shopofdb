<template>
  <div>
    <el-page-header @back="headBack" content="供应商详情"> </el-page-header>

    <div style="text-align: right">
      <!-- <import-excel /> -->
      <el-button type="primary" @click="dialogFormVisible = true"
        >增加供货商</el-button
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
          <el-form-item label="供货商名称">
            <el-input
              style="width: 203px"
              v-model="listQuery.pname"
              placeholder="用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="供货商地址：">
            <el-input
              style="width: 203px"
              v-model="listQuery.paddress"
              placeholder="地址"
            ></el-input>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input
              style="width: 203px"
              v-model="listQuery.pphone"
              placeholder="电话号"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-divider></el-divider>
    <el-table
      ref="multipleTable"
      :data="providerInfo.slice((this.page - 1) * this.size, this.page * this.size)"
      tooltip-effect="dark"
      style="width: 100%"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="ID" width="200"> </el-table-column>
      <el-table-column prop="pname" label="供货商名称" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="pphone"
        label="供货商联系方式"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column prop="paddress" label="供货商地址" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="plinkman"
        label="供货商联系人"
        show-overflow-tooltip
      >
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
      :total="this.providerInfo.length"
    >
    </el-pagination>

    <el-dialog title="供应商信息" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="供应商名" :label-width="formLabelWidth">
          <el-input v-model="form.pname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供应商电话" :label-width="formLabelWidth">
          <el-input v-model="form.pphone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供应商地址" :label-width="formLabelWidth">
          <el-input v-model="form.paddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供应商联系人" :label-width="formLabelWidth">
          <el-input v-model="form.plinkman" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddProvider('form')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="供应商信息" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="formEdit" ref="formEdit" :rules="rules">
        <el-form-item label="供应商名" :label-width="formLabelWidth">
          <el-input v-model="formEdit.pname" autocomplete="off" :placeholder="rowItem.pname"></el-input>
        </el-form-item>
        <el-form-item label="供应商电话" :label-width="formLabelWidth">
          <el-input v-model="formEdit.pphone" autocomplete="off" :placeholder="rowItem.pphone"></el-input>
        </el-form-item>
        <el-form-item label="供应商地址" :label-width="formLabelWidth">
          <el-input v-model="formEdit.paddress" autocomplete="off" :placeholder="rowItem.paddress"></el-input>
        </el-form-item>
        <el-form-item label="供应商联系人" :label-width="formLabelWidth">
          <el-input v-model="formEdit.plinkman" autocomplete="off" :placeholder="rowItem.plinkman"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="EditProvider('formEdit')">确 定</el-button>
      </div>
    </el-dialog>

    <el-divider></el-divider>
  </div>
</template>

<script>
import goodList from "@/components/good/goodList";
import { getProviderList, addProvider, deleteProvider,editProvider,getConditionList } from "@/api/provider";
const defaultListQuery = {
  pname: null,
  paddress: null,
  pphone: null,
};
export default {
  name: "providerManage",
  components: { goodList },
  data() {
    return {
       page: 1, //第几页
      size: 3, //一页多少条
      total: 0, //总条目数
      pageSizes: [3, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000], //可选择的一页多少条
      tableData: [], //表格绑定的数据
      listQuery: Object.assign({}, defaultListQuery),
      searchKey: "",
      providerInfo: {},
      form: {
        pname: "",
        pphone: "",
        paddress: "",
        plinkman: "",
      },
      formEdit: {
          id:'',
        ppname: "",
        pphone: "",
        paddress: "",
        plinkman: "",
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
      rowItem:{
          id:"",
        pname: "",
        pphone: "",
        paddress: "",
        plinkman: "",
      },
    };
  },
  mounted() {
    this.fetchProviderList();
  },
  methods: {
    getList() {
      console.log(this.listQuery)
      getConditionList(this.listQuery).then((response) => {
        const { data } = response;
        this.providerInfo = data;
        console.log(this.providerInfo);
      });
    },
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
      this.fetchProviderList()
    },
    handleSearchList() {;
      this.getList();
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
    fetchProviderList() {
      getProviderList().then((response) => {
        const { data } = response;
        this.providerInfo = data;
        console.log(this.providerInfo);
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
       this.$set(this.formEdit,'id',this.rowItem["id"])
      this.$set(this.formEdit,'pname',this.rowItem["pname"])
      this.$set(this.formEdit,'pphone',this.rowItem["pphone"])
        this.$set(this.formEdit,'paddress',this.rowItem["paddress"])
    this.$set(this.formEdit,'plinkman',this.rowItem["plinkman"])
      console.log(this.formEdit)
      this.dialogFormVisibleEdit = true;
    },
    handleDelete(index, row) {
      console.log(row);
      deleteProvider(row)
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
    AddProvider(formName) {
      this.dialogFormVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          addProvider(this.form)
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
    EditProvider(formName) {
      this.dialogFormVisibleEdit = false;
      console.log(this.$refs[formName])
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.form);
          editProvider(this.formEdit)
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
  },
};
</script>

<style scoped>
</style>
