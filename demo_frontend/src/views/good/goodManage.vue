<template>
  <div>
    <el-page-header @back="headBack" content="商品详情"> </el-page-header>

    <div style="text-align: right">
      <!-- <import-excel /> -->
      <el-button size="small" type="primary" @click="dialogFormVisible = true"
        >增加商品</el-button
      >
      <!-- <b-field position="is-centered">
        <b-input
          v-model="searchKey"
          class="s_input"
          width="80%"
          placeholder="搜索商品"
          rounded
          clearable
          @keyup.enter.native="search()"
        />
        <p class="control">
          <b-button class="is-info" @click="search()">检索 </b-button>
        </p>
      </b-field> -->
      <exportExcel
            :id="'export'"
            :name="'商品表'"
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
          <el-form-item label="商品名">
            <el-input
              style="width: 203px"
              v-model="listQuery.goodname"
              placeholder="名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="进价￥">
            <el-input
              style="width: 203px"
              v-model="listQuery.pricein"
              placeholder="价格"
            ></el-input>
          </el-form-item>
          <el-form-item label="售价￥">
            <el-input
              style="width: 203px"
              v-model="listQuery.pricesell"
              placeholder="价格"
            ></el-input>
          </el-form-item>
          <el-form-item label="供货商">
            <el-select
              v-model="listQuery.providerId"
              placeholder="供货商"
              clearable
            >
              <el-option
                v-for="item in providerInfo"
                :key="item.id"
                :label="item.pname"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品种类">
            <el-select
              v-model="listQuery.categoryId"
              placeholder="种类"
              clearable
            >
              <el-option
                v-for="item in categoryInfo"
                :key="item.cid"
                :label="item.cname"
                :value="item.cid"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <div hidden="hidden">
      <el-table
        ref="multipleTable"
        :data="goodInfo"
        tooltip-effect="dark"
        style="width: 100%"
        border
        id = "export"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column prop="id" label="ID" width="150"> </el-table-column>
        <el-table-column prop="goodname" label="商品名称" show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="providerNmae"
          label="供货商名称"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column prop="categoryName" label="商品种类" show-overflow-tooltip>
        </el-table-column>
        <el-table-column
          prop="pricesell"
          label="售价"
          show-overflow-tooltip
          width="50"
        >
        </el-table-column>
                <el-table-column
          prop="pricein"
          label="进价"
          show-overflow-tooltip
          width="50"
        >
        </el-table-column>
        <el-table-column
          prop="storage"
          label="库存"
          show-overflow-tooltip
          width="70"
        >
        </el-table-column>
        <el-table-column prop="salenum" label="售出数" show-overflow-tooltip>
        </el-table-column>

        <el-table-column prop="bio" label="备注" show-overflow-tooltip>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
      <el-form :model="form" ref="form" :rules="rules">
        <el-form-item label="商品名" :label-width="formLabelWidth">
          <el-input v-model="form.goodname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="进价" :label-width="formLabelWidth">
          <el-input v-model="form.pricein" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="售价" :label-width="formLabelWidth">
          <el-input v-model="form.pricesell" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="库存" :label-width="formLabelWidth">
          <el-input v-model="form.storage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="保质期" :label-width="formLabelWidth">
          <el-input v-model="form.shelflife" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="选择种类" :label-width="formLabelWidth">
          <el-select v-model="form.categoryId" placeholder="请选择种类">
            <el-option
              v-for="(item, index) in categoryInfo"
              :key="index"
              :label="item.cname"
              :value="item.cid"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择供货商" :label-width="formLabelWidth">
          <el-select v-model="form.providerId" placeholder="请选择供货商">
            <el-option
              v-for="(item, index) in providerInfo"
              :key="index"
              :label="item.pname"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品描述" :label-width="formLabelWidth">
          <el-input
            v-model="form.bio"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="请输入商品描述"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddGoods('form')">确 定</el-button>
      </div>
    </el-dialog>
    <el-divider></el-divider>
    <goodList
      v-for="(item, index) in goodInfo"
      :item="item"
      :key="index"
      :need-opera="true"
    ></goodList>
    <!-- <el-table ref="goodInfo" :data="list" style="width: 100%" border>
      <el-table-column v-for="(item, index) in goodInfo" :key="index">
        <goodList :item="item"  :need-opera="true"></goodList>
      </el-table-column>
    </el-table> -->
  </div>
</template>

<script>
import goodList from "@/components/good/goodList";
import { getGoodList, addGood, editGood, getConditionList } from "@/api/good";
import { getCategoryList } from "@/api/category";
import { getProviderList } from "@/api/provider";
import { searchByKeyword } from "@/api/search";
import ExportExcel from "@/components/ExportExcel";
import ImportExcel from "../../components/ImportExcel.vue";
const defaultListQuery = {
  goodname: null,
  providerId: null,
  categoryId: null,
  pricein: null,
  pricesell: null,
};
export default {
  name: "goodManage",
  components: { goodList, ImportExcel , ExportExcel},
  data() {
    return {
      listQuery: Object.assign({}, defaultListQuery),
      searchKey: "",
      goodInfo: {},
      multipleSelection:[],
      categoryInfo: {},
      providerInfo: {},
      form: {
        goodname: "",
        pricein: "",
        pricesell: "",
        storage: "",
        status: "",
        categoryId: "",
        providerId: "",
        shelflife: "",
        bio: "",
      },
      rules: {
        goodname: [
          { required: true, message: "请输入商品名", trigger: "blur" },
        ],
      },
      dialogFormVisible: false,
      formLabelWidth: "120px",
    };
  },
  mounted() {
    this.fetchCategoryList();
    this.fetchGoodList();
    this.fetchProviderList();
  },
  methods: {
    getList() {
      console.log(this.listQuery);
      getConditionList(this.listQuery).then((response) => {
        const { data } = response;
        this.goodInfo = data;
        console.log(this.goodInfo);
      });
    },
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
      this.fetchGoodList();
    },
    handleSearchList() {
      this.getList();
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
    messa(value) {
      console.log(1111);
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
    },
    fetchGoodList() {
      getGoodList().then((response) => {
        const { data } = response;
        this.goodInfo = data;
        console.log(111111);
        console.log(this.goodInfo);
      });
    },
    AddGoods(formName) {
      this.dialogFormVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          //console.log(this.form)
          addGood(this.form)
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
              this.fetchGoodList();
              //this.$router.go(0);
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
        handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    search() {
      console.log(this.searchKey);
      if (this.searchKey.trim() === null || this.searchKey.trim() === "") {
        this.$message.info({
          showClose: true,
          message: "请输入关键字搜索！",
          type: "warning",
        });
        return false;
      }
      this.$router.push({ path: "/search?key=" + this.searchKey });
    },
  },
};
</script>

<style scoped>
</style>
