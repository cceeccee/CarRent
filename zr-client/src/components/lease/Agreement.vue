<template>
  <div>
    <el-form :inline="true" :model="queryForm" style="text-align: left;">
      <el-form-item label="合同名称">
        <el-input v-model="queryForm.name" placeholder="合同名称"></el-input>
      </el-form-item>
      <el-button type="primary" @click="query">查询</el-button>
      <el-button type="primary" @click="openAdd">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 数据列表 -->
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="name" label="合同名称">
      </el-table-column>
      <el-table-column prop="text" label="合同内容">
      </el-table-column>
      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-value="有效" inactive-="作废" active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createId" label="新增人员">
      </el-table-column>
      <el-table-column prop="createTime" label="新增时间">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="openEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row)">
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页   -->
    <el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
      :current-page.sync="queryForm.pageNum" :page-size="queryForm.pageSize"
      layout="total,sizes,prev, pager, next,jumper" :total="total">
    </el-pagination>
    <!-- 新增和修改 -->
    <el-dialog :title="title" :visible.sync="show" width="500px">
      <el-input type="hidden" :name="dialogType"></el-input>
      <el-form :model="form" :rules="rules" ref="form" label-width="60px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" clearable></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="text">
          <el-input v-model="form.text" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-input v-model="form.status" clearable></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show = false">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name: 'Agreement',
    data() {
      return {
        queryForm: {
          name: '',
          pageNum: 1,
          pageSize: 10
        },
        tableData: [],
        total: 0,
        title: '',
        show: false,
        form: {},
        rules: {},
        dialogType: 'add'
      }
    },
    methods: {
      query() {
        this.$axios({
          method: 'post',
          url: 'api/lease/agreement/list',
          data: this.queryForm
        }).then(res => {
          if (res.data.success) {
            this.tableData = res.data.obj.list;
            this.total = res.data.obj.total;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
      openAdd() {
        this.title = "新增";
        this.form = {};
        this.show = true;
        this.dialogType = 'add';
      },
      openEdit() {
        this.title = "编辑";
        this.form = {};
        this.show = true;
        this.dialogType = 'edit';
        this.form = Object.assign({},row);
      },
      submitForm() {
        if (this.dialogType == 'add') {
          this.$axios({
            method: 'post',
            url: 'api/lease/agreement/add',
            data: this.form
          }).then(res => {
            this.show = false;
            this.query()
          }).catch(err => {
            console.info(err)
          });
        }
        del(row)
        this.$axios({
          method: 'get',
          url: 'api/lease/agreement/del/' + row.name
        }).then(res => {

          this.query()
        }).catch(err => {
          console.info(err)
        });

      }
    },
    created() {

      this.query();

    }
  }
</script>

<style>
</style>
