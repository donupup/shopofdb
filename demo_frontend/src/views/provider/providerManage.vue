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
    <el-table
      ref="multipleTable"
      :data="providerInfo"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="ID" width="120"> </el-table-column>
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
import { getProviderList, addProvider, deleteProvider,editProvider } from "@/api/provider";
export default {
  name: "providerManage",
  components: { goodList },
  data() {
    return {
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
  },
};
</script>

<style scoped>
</style>
