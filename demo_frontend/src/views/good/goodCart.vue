<template>
  <div>
    <el-page-header @back="headBack" content="结算界面"> </el-page-header>
    <el-divider></el-divider>
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>输入会员ID获得用户购物车</span>
        <el-button
          style="float: right"
          @click="handleSearchListVip()"
          type="primary"
          size="small"
        >
          确认用户
        </el-button>
        <el-button
          style="float: right; margin-right: 15px"
          @click="handleResetSearchVip()"
          size="small"
        >
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-input
          style="width: 203px"
          v-model="vipid"
          placeholder="名称"
        ></el-input>
      </div>
    </el-card>
    <el-divider></el-divider>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="商品总览" name="first">
        <div>
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
          <div>
            <el-table
              ref="multipleTable"
              :data="
                goodInfo.slice(
                  (this.page - 1) * this.size,
                  this.page * this.size
                )
              "
              tooltip-effect="dark"
              style="width: 100%"
              border
              id="export"
              @selection-change="handleSelectionChange"
            >
              <el-table-column type="selection" width="55"> </el-table-column>
              <el-table-column prop="id" label="ID" width="150">
              </el-table-column>
              <el-table-column
                prop="goodname"
                label="商品名称"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="providerNmae"
                label="供货商名称"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="categoryName"
                label="商品种类"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="pricesell"
                label="售价"
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

              <el-table-column prop="bio" label="备注" show-overflow-tooltip>
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    size="mini"
                    @click="handleAdd(scope.$index, scope.row)"
                    >加入购物车</el-button
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
              :total="this.goodInfo.length"
            >
            </el-pagination>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="购物车" name="second">
        <el-table
          ref="multipleTableCart"
          :data="cartList"
          tooltip-effect="dark"
          style="width: 100%"
          border
          id="export"
          @selection-change="handleSelectionChangeCart"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column prop="id" label="ID" width="150"> </el-table-column>
          <el-table-column
            prop="goodName"
            label="商品名称"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column prop="userName" label="操作员" show-overflow-tooltip>
          </el-table-column>
          <el-table-column
            prop="price"
            label="总价"
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

          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="primary"
                size="mini"
                @click="handleEditCartItem(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                @click="handleDeleteCartItem(scope.$index, scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <!-- <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :current-page="page"
          :page-size="size"
          :page-sizes="pageSizes"
          layout="total, sizes, prev, pager, next, jumper"
          :total="this.cartList.length"
        >
        </el-pagination> -->
        <el-divider></el-divider>
        <el-button size="small" type="primary" @click="checkMulti()"
          >批量结算</el-button
        >
        <el-button size="small" type="primary" @click="deleteMulti()"
          >批量删除</el-button
        >
      </el-tab-pane>
    </el-tabs>
    <el-dialog title="加入购物车的数量" :visible.sync="dialogFormVisible">
      <el-form :model="addform" ref="addform" :rules="rules">
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input-number
            v-model="addform.num"
            @change="handleChange"
            :min="1"
            label="描述文字"
          ></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="AddToCart('addform')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <el-dialog title="编辑商品数量" :visible.sync="dialogFormVisibleEdit">
      <el-form :model="editform" ref="editform" :rules="rules">
        <el-form-item label="数量" :label-width="formLabelWidth">
          <el-input-number
            v-model="editform.num"
            @change="handleChange"
            :min="1"
            label="描述文字"
          ></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleEdit = false">取 消</el-button>
        <el-button type="primary" @click="EditCartItem('editform')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <el-divider></el-divider>
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
import { getNumOfVip } from "@/api/sta";
import {
  getCartList,
  addCart,
  deleteCartOne,
  editCartOne,
  deleteCartMulti,
  checkCart,
} from "@/api/cart";
import { getUserId } from "@/utils/auth";
export default {
  name: "goodManage",
  components: { goodList, ImportExcel, ExportExcel },
  data() {
    return {
      cartList: [],
      userId: "",
      vipid: "",
      chosenvipInfo: "",
      vipInfo: "",
      activeName: "second",
      listQuery: Object.assign({}, defaultListQuery),
      page: 1, //第几页
      size: 3, //一页多少条
      total: 0, //总条目数
      pageSizes: [3, 5, 10, 20, 50, 100, 200, 300, 400, 500, 1000], //可选择的一页多少条
      tableData: [], //表格绑定的数据
      searchKey: "",
      goodInfo: {},
      multipleSelection: [],
      multipleTableCart: [],
      categoryInfo: {},
      providerInfo: {},
      rules: {
        goodname: [
          { required: true, message: "请输入商品名", trigger: "blur" },
        ],
      },
      dialogFormVisible: false,
      dialogFormVisibleEdit: false,
      formLabelWidth: "120px",
      addform: {
        goodid: "",
        userid: "",
        vipid: "",
        num: 0,
      },
      editform: {
        id: "",
        num: 0,
      },
    };
  },
  mounted() {
    this.fetchCategoryList();
    this.fetchGoodList();
    this.fetchProviderList();
  },
  methods: {
    deleteMulti() {
      //这里面存着选中行的信息
      if (this.multipleTableCart.length == 0) {
        this.$message("请选择商品");
      } else {
        console.log(this.multipleTableCart);
        deleteCartMulti(this.multipleTableCart)
          .then((value) => {
            const { code, message } = value;
            getCartList(this.vipid).then((response) => {
              const { data } = response;
              this.cartList = data;
              console.log(this.cartList);
            });
            //console.log(value)
            if (code === 200) {
              this.$message({
                message: "删除成功",
                type: "success",
              });
            } else {
              this.$message.error("删除失败，" + message);
            }
          })
          .catch(() => {
            this.loading = false;
          });
      }
    },
    checkMulti() {
      console.log(this.multipleTableCart);

      if (this.multipleTableCart.length == 0) {
        this.$message("请选择商品");
      } else {
        //json打印
        //   this.$print({
        //   printable: this.multipleTableCart,
        //   properties: [
        //     { field: "goodName", displayName: "商品名" },
        //     { field: "num", displayName: "数量" },
        //     { field: "price", displayName: "金额" },
        //   ],
        //   header: '<h3 class="custom-h3">欢迎来到Don超市</h3><h5>================================</h5><h4>收款时间</h4><span>' + new Date() + '</span><h5>================================</h5>',
        //   type: "json",
        // });

        checkCart(this.multipleTableCart)
          .then((value) => {
            const { code, message } = value;
            getCartList(this.vipid).then((response) => {
              const { data } = response;
              this.cartList = data;
              console.log(this.cartList);
            });
            //console.log(value)
            if (code === 200) {
              this.$message({
                message: "购买成功",
                type: "success",
              });
            } else {
              this.$message.error("购买失败，" + message);
            }
          })
          .catch(() => {
            this.loading = false;
          });
      }
    },
    AddToCart(formName) {
      this.dialogFormVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.addform);
          addCart(this.addform)
            .then((value) => {
              const { code, message } = value;
              getCartList(this.vipid).then((response) => {
                const { data } = response;
                this.cartList = data;
                console.log(this.cartList);
              });
              //console.log(value)
              if (code === 200) {
                this.$message({
                  message: "添加成功",
                  type: "success",
                });
              } else {
                this.$message.error("添加失败，" + message);
              }
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    handleAdd(index, row) {
      if (this.vipInfo == "") {
        this.$message.error("输入会员卡号");
      } else {
        console.log(index, row);
        console.log(this.multipleSelection);
        this.$set(this.addform, "goodid", row["id"]);
        this.$set(this.addform, "vipid", this.vipInfo.id);
        this.getUser();
        this.$set(this.addform, "userid", this.userId);
        this.dialogFormVisible = true;
        console.log(this.dialogFormVisible);
      }
    },
    getUser() {
      this.userId = getUserId();
      console.log(this.userId);
    },
    handleEditCartItem(index, row) {
      console.log(index, row);
      this.$set(this.editform, "id", row.id);
      this.dialogFormVisibleEdit = true;
    },
    EditCartItem(formName) {
      this.dialogFormVisibleEdit = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          console.log(this.editform);
          editCartOne(this.editform)
            .then((value) => {
              const { code, message } = value;
              getCartList(this.vipid).then((response) => {
                const { data } = response;
                this.cartList = data;
                console.log(this.cartList);
              });
              //console.log(value)
              if (code === 200) {
                this.$message({
                  message: "编辑成功",
                  type: "success",
                });
              } else {
                this.$message.error("编辑失败，" + message);
              }
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
    handleDeleteCartItem(index, row) {
      console.log(index, row);
      console.log(row.id);
      deleteCartOne(row.id)
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
          getCartList(this.vipid).then((response) => {
            const { data } = response;
            this.cartList = data;
            //console.log(this.cartList);
          });
        })
        .catch(() => {
          this.loading = false;
        });
    },
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
    handleSearchListVip() {
      console.log(this.vipid);
      if (this.vipid == "") {
        this.$message.error("请输入会员卡号");
      } else {
        console.log(this.vipid);
        getNumOfVip(this.vipid).then((response) => {
          const { data } = response;
          this.chosenvipInfo = data;
          this.vipInfo = data.vip;
          console.log(this.chosenvipInfo);
        });
        getCartList(this.vipid).then((response) => {
          const { data } = response;
          this.cartList = data;
          this.$message({
            message: "购物车获取成功",
            type: "success",
          });

          console.log(this.cartList);
        });
      }
    },
    handleResetSearchVip() {
      this.vipid = "";
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

    headBack() {
      console.log(this.$router);
      this.$router.back();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleSelectionChangeCart(val) {
      this.multipleTableCart = val;
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
    handleClick(tab, event) {
      console.log(tab, event);
    },
    handleChange(value) {
      console.log(value);
    },
  },
};
</script>

<style scoped>
</style>
