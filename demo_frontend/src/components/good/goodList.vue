<template>
  <div>
    <el-descriptions
      class="margin-top"
      :title="item.goodname"
      :column="3"
      border
    >
      <template slot="extra">
        <el-button
          type="primary"
          icon="el-icon-edit"
          circle
          @click="setDefault"
          v-if="needOpera"
        ></el-button>
        <el-dialog title="用户信息" :visible.sync="dialogFormVisible">
          <el-form :model="form" ref="form" :rules="rules">
            <el-form-item
              label="商品名"
              :label-width="formLabelWidth"
              v-if="role != 5"
            >
              <el-input
                v-model="form.goodname"
                autocomplete="off"
                :placeholder="item.goodname"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="规格"
              :label-width="formLabelWidth"
              v-if="role != 5"
            >
              <el-input
                v-model="form.specification"
                autocomplete="off"
                :placeholder="item.specification"
              ></el-input>
            </el-form-item>
            <!-- <el-form-item label="进价" :label-width="formLabelWidth" v-if="role != 2">
              <el-input v-model="form.pricein" autocomplete="off" :placeholder="item.pricein"></el-input>
            </el-form-item>
            <el-form-item label="售价" :label-width="formLabelWidth" v-if="role != 2">
              <el-input v-model="form.pricesell" autocomplete="off" :placeholder="item.pricesell"></el-input>
            </el-form-item> -->
            <!-- <el-form-item label="库存" :label-width="formLabelWidth" >
              <el-input-number v-model="form.storage" controls-position="right"  :min="0" ></el-input-number>
            </el-form-item> -->
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
            <el-form-item
              label="备注"
              :label-width="formLabelWidth"
              v-if="role != 5"
            >
              <el-input
                v-model="form.bio"
                placeholder="请输入原因"
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4 }"
              ></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="editGoodInfo('form')"
              >确 定</el-button
            >
          </div>
        </el-dialog>

        <el-popconfirm
          v-if="role != 5 && needOpera"
          confirm-button-text="是"
          cancel-button-text="否"
          title="确定删除该商品吗？"
          @confirm="deleteGoodInfo"
        >
          <el-button
            slot="reference"
            type="danger"
            icon="el-icon-delete"
            circle
          ></el-button>
        </el-popconfirm>
      </template>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          序号
        </template>
        {{ item.id }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          商品名称
        </template>
        {{ item.goodname }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          进价
        </template>
        {{ item.pricein }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          售价
        </template>
        {{ item.pricesell }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-goods"></i>
          售出数
        </template>
        {{ item.salenum }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <el-badge value="lack" class="item" v-if="item.storage < 10">
            <i class="el-icon-goods"></i>
            库存
          </el-badge>
          <div v-else>
            <i class="el-icon-goods"></i>
            库存
          </div>
        </template>
        {{ item.storage }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          供货商
        </template>
        {{ item.providerNmae }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          商品种类
        </template>
        {{ item.categoryName }}
        <!-- <el-tag size="small" v-if="item.status == false">未过期</el-tag>
        <el-tag size="small" v-else-if="item.status == true">已过期</el-tag> -->
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-date"></i>
          规格
        </template>
        <!-- {{ dayjs(item.modifyTime).format("YYYY/MM/DD dddd HH:mm:ss.SSS A") }} -->
        {{ item.specification }}
      </el-descriptions-item>
    </el-descriptions>
    <el-divider></el-divider>
  </div>
</template>

<script>
import { deleteGood, editGood } from "@/api/good";
import { mapGetters } from "vuex";
import { getCategoryList } from "@/api/category";
import { getProviderList } from "@/api/provider";

export default {
  name: "goodList",
  props: {
    item: {
      type: Object,
    },
    needOpera: "",
    providerList: {
      type: Array,
    },
    categoryList: {
      type: Array,
    },
  },

  data() {
    return {
      loading: false,
      dialogFormVisible: false,
      form: {
        id: "",
        goodname: "",
        pricein: "",
        pricesell: "",
        storage: "",
        status: "",
        bio: "",
        categoryId: "",
        providerId: "",
        specification: "",
      },
      providerInfo: {},
      categoryInfo: {},
      formLabelWidth: "120px",
      rules: {
        goodname: [
          { required: true, message: "请输入商品名", trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    this.fetchCategoryList();
    this.fetchProviderList();
  },
  computed: {
    ...mapGetters(["token", "user", "role"]),
  },
  methods: {
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
    checkNum() {
      if (this.role == "2") {
        if (this.form.storage > this.item.storage) return true;
        return false;
      }
      return true;
    },
    setDefault() {
      this.dialogFormVisible = true;
      this.$set(this.form, "id", this.item.id);
      this.$set(this.form, "goodname", this.item.goodname);
      this.$set(this.form, "pricein", this.item.pricein);
      this.$set(this.form, "pricesell", this.item.pricesell);
      this.$set(this.form, "storage", this.item.storage);
      this.$set(this.form, "status", this.item.status);
    },
    deleteGoodInfo() {
      this.loading = true;
      //console.log(this.item.id)
      if (!this.checkNum()) {
        this.$message.error("货物变少了~您只有权力进货，不能出货哦~");
        return;
      }
      deleteGood(this.item.id)
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
    editGoodInfo(formName) {
      this.dialogFormVisible = false;
      if (!this.checkNum()) {
        this.$message.error("货物变少了~您只有权力进货，不能出货哦~");
        return;
      }
      //this.$set(this.form,'username',this.item.username)
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          //console.log(this.form)
          editGood(this.form)
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
.item {
  margin-top: 10px;
  margin-right: 20px;
}
</style>
