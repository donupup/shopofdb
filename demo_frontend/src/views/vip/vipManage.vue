<template>
  <div>
    <el-page-header @back="headBack" content="会员详情"> </el-page-header>

    <div style="text-align: right">
      <!-- <import-excel /> -->
      <el-button type="primary" @click="dialogFormVisible = true"
        >增加会员</el-button
      >
    </div>
    <el-divider></el-divider>
    <el-table
      ref="multipleTable"
      :data="vipInfo"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column prop="id" label="会员卡号" width="120"> </el-table-column>
      <el-table-column prop="vname" label="会员姓名" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="vsex" label="会员性别" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="vphone"
        label="会员联系方式"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column prop="vaddress" label="会员注册地址" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        prop="vbalance"
        label="会员卡余额"
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

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddVip('form')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="供应商信息" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="formEdit" ref="formEdit" :rules="rules">
        <el-form-item label="会员姓名" :label-width="formLabelWidth">
          <el-input v-model="formEdit.vname" autocomplete="off" :placeholder="rowItem.vname"></el-input>
        </el-form-item>
        <el-form-item label="会员性别" :label-width="formLabelWidth">
          <el-select v-model="formEdit.vsex" placeholder="请选择性别">
            <el-option label="男" :value="'男'"></el-option>
            <el-option label="女" :value="'女'"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会员联系方式" :label-width="formLabelWidth">
          <el-input v-model="formEdit.vphone" autocomplete="off" :placeholder="rowItem.vphone"></el-input>
        </el-form-item>
        <el-form-item label="会员注册地址" :label-width="formLabelWidth">
          <el-input v-model="formEdit.vaddress" autocomplete="off" :placeholder="rowItem.vaddress"></el-input>
        </el-form-item>
        <el-form-item label="会员卡余额" :label-width="formLabelWidth">
          <el-input v-model="formEdit.vbalance" autocomplete="off" :placeholder="rowItem.vbalance"></el-input>
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
import { getVipList, addVip, deleteVip,editVip } from "@/api/vip";
export default {
  name: "providerManage",
  components: { goodList },
  data() {
    return {
      searchKey: "",
      vipInfo: {},
      form: {
        vname:"",
        vsex:"",
        vphone:'',
        vaddress:'',
        vbalance:''
      },
      formEdit: {
        id:"",
        vname:"",
        vsex:"",
        vphone:'',
        vaddress:'',
        vbalance:''
         
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
      rowItem:{
          id:"",
        vname:"",
        vsex:"",
        vphone:'',
        vaddress:'',
        vbalance:''
      },
    };
  },
  mounted() {
    this.fetchVipList();
  },
  methods: {
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
       this.$set(this.formEdit,'id',this.rowItem["id"])
      this.$set(this.formEdit,'vname',this.rowItem["vname"])
      this.$set(this.formEdit,'vphone',this.rowItem["vphone"])
    this.$set(this.formEdit,'vaddress',this.rowItem["vaddress"])
    this.$set(this.formEdit,'vsex',this.rowItem["vsex"])
    this.$set(this.formEdit,'vbalance',this.rowItem["vbalance"])
      console.log(this.formEdit)
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
          this.$router.go(0);
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
              this.$router.go(0);
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    EditVip(formName) {
      this.dialogFormVisibleEdit = false;
      console.log(this.$refs[formName])
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
