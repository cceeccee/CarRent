<template>
  <!-- 查询 -->
  <div>
    <el-form :inline="true" :model="queryForm" style="text-align: left;">
      <el-form-item label="订单编号">
        <el-input v-model="queryForm.id" placeholder="订单编号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 数据列表 -->
    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column prop="orderId" label="订单编号">
      </el-table-column>
      <el-table-column prop="order.customerId" label="客户编号">
      </el-table-column>
      <el-table-column prop="customer.name" label="客户名称">
      </el-table-column>
      <el-table-column prop="carNo" label="车牌号码">
      </el-table-column>
      <el-table-column prop="outTime" label="出车时间">
      </el-table-column>
      <el-table-column prop="inTime" label="回车时间">
      </el-table-column>
      <el-table-column prop="status" label="状态">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width = "200px">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="queryDetail(scope.row)">详情</el-button>
          <el-button type="primary" size="small" :disabled="showOut(scope.row)" @click="openOut(scope.row)">出车
          </el-button>
          <el-popconfirm title="确定整备吗？" @confirm="toPre(scope.row)">
            <el-button slot="reference" size="small" type="danger"  :disabled="showPre(scope.row)">整备</el-button>
          </el-popconfirm>
          <el-button type="primary" size="small" :disabled="showIn(scope.row)" @click="openPre(scope.row)">回车
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination v-show="total>0" background @size-change="query" @current-change="query"
      :current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
      layout="prev, pager, next, total, sizes, jumper" :total="total">
    </el-pagination>

    <el-dialog :title="title" :visible.sync="show" width="500px">
      <el-form :model="form1" :rules="rules1" ref="form1" label-width="60px">
        <el-form-item label="订单编号" prop="order.orderId" label-width="70px">
          <el-input v-model="form1.orderId" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="客户编号" prop="customer.customerId" label-width="70px">
          <el-input v-model="form1.customerId" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="客户名称" prop="customer.name" label-width="70px">
          <el-input v-model="form1.name" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="预定车型" prop="car.name" label-width="70px">
          <el-input v-model="form1.name" clearable :disabled="disabled"></el-input>
        </el-form-item>
      </el-form>
      <el-form :model="form2" :rules="rules2" ref="form2" label-width="60px">
        <el-form-item label="出车门店" prop="name" label-width="70px">
          <el-input v-model="form2.name" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="出车时间" prop="outTime" label-width="70px">
          <el-input v-model="form1.outTime" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="出车人员" prop="admin" label-width="70px">
          <el-input v-model="form2.admin" clearable :disabled="disabled"></el-input>
        </el-form-item>
      </el-form>
      <el-form :model="form3" :rules="rules3" ref="form3" label-width="60px">
        <el-form-item label="回车门店" prop="name" label-width="70px">
          <el-input v-model="form3.name" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="回车时间" prop="inTime" label-width="70px">
          <el-input v-model="form1.inTime" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="回车人员" prop="admin" label-width="70px">
          <el-input v-model="form3.admin" clearable :disabled="disabled"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="show=false">确定</el-button>
        <el-button @click="show = false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'Car',
    data() {
      return {
        // 查询
        queryForm: {
          name: '',
          pageNum: 1,
          pageSize: 10,
        },
        //对话默认不显示
        show: false,
        // 表格
        tableData: [],
        //总页数
        total: 0,
        //初始化表单对象
        form1: {},
        form2: {},
        form3: {},
        //对话类型
        dialogype: 'add',
        //初始化不可编辑
        disabled: true,
        //出车
        //showOut: false,
        //回车
        //showPre: false
      }
    },
    methods: {
      //查询数据
      query() {
        this.$axios({
          method: 'post',
          url: 'api/car/carwork/list',
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
      queryDetail(row) {
        //设置对话框标题
        this.title = "详情";
        //清空表单
        this.form1 = {};
        //清空表单
        this.form2 = {};
        //主键不可编辑
        this.disabled = true;
        //打开对话框
        this.show = true;
        this.form1 = Object.assign({}, row);

        //this.query1(row);
        //this.query2(row);
        //this.query3(row);
      },
      //查询数据1
      query1(row) {
        this.$axios({
          method: 'post',
          url: 'api/car/carwork/listDetail/' + row.id
        }).then(res => {
          if (res.data.success) {
            this.form1 = res.data.obj;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
      query2(row) {
        this.$axios({
          method: 'post',
          url: 'api/car/carwork/listOutDetail/' + row.id
        }).then(res => {
          if (res.data.success) {
            this.form2 = res.data.obj;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
      query3(row) {
        this.$axios({
          method: 'post',
          url: 'api/car/carwork/listInDetail/' + row.id
        }).then(res => {
          if (res.data.success) {
            this.form3 = res.data.obj;
          } else {
            this.$message.error(res.data.msg);
          }
        }).catch(err => {
          console.info(err)
        });
      },
      //判断是否出车回车整备
      showPre(row) {
        if (row.status == "已回车") {
          return false;
        } else return true;
      },

      showOut(row) {
        if (row.status == "已整备" || row.status == "已准备") {
          return false;
        } else return true;
      },
      showIn(row){
        if (row.status == "已出车") {
          return false;
        } else return true;
      },
      toPre(row){

      }
    },

    created() {
      //初始化页面时进行数据查询
      this.query();
    }

  }
</script>

<style>
</style>
