<template>
  <div>
    <el-form :inline="true" :model="formInline" class="demo-form-inline" style="text-align: left;">
      <el-form-item label="订单编号">
        <el-input v-model="formInline.id" placeholder="订单编号"></el-input>
      </el-form-item>
      <el-form-item label="客户账号">
        <el-input v-model="formInline.customerId" placeholder="客户账号"></el-input>
      </el-form-item>
      <el-form-item label="客户">
        <el-input v-model="formInline.name" placeholder="客户"></el-input>
      </el-form-item>
      <el-form-item label="预定车型">
        <el-select v-model="formInline.carname" placeholder="全部">
          <el-option label="全部" value=""></el-option>
          <el-option label="奥迪A4L" value="奥迪A4L"></el-option>
          <el-option label="奥迪A6L" value="奥迪A6L"></el-option>
          <el-option label="奥迪Q5L" value="奥迪Q5L"></el-option>
          <el-option label="奥迪e-tron" value="奥迪e-tron"></el-option>
          <el-option label="奥迪Q3L" value="奥迪Q3L"></el-option>
          <el-option label="奥迪Q7" value="奥迪Q7"></el-option>
          <el-option label="奥迪Q8L" value="奥迪Q8L"></el-option>
          <el-option label="奥迪Q2L" value="奥迪Q2L"></el-option>
          <el-option label="奥迪R8" value="奥迪R8"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" style="width: 150%" :default-sort="{prop: 'id', order: 'ascending'}">
      <el-table-column prop="id" label="订单编号" sortable>
      </el-table-column>
      <el-table-column prop="customerId" label="客户账号">
      </el-table-column>
      <el-table-column prop="name" label="客户姓名">
      </el-table-column>
      <el-table-column prop="carname" label="预定车型">
      </el-table-column>
      <el-table-column prop="fcname" label="取车城市">
      </el-table-column>
      <el-table-column prop="fsname" label="取车门店">
      </el-table-column>
      <el-table-column prop="fromTime" label="取车时间">
      </el-table-column>
      <el-table-column prop="tcname" label="还车城市">
      </el-table-column>
      <el-table-column prop="tsname" label="还车门店">
      </el-table-column>
      <el-table-column prop="toTime" label="还车时间">
      </el-table-column>
      <el-table-column prop="status" label="状态">
      </el-table-column>
      <el-table-column prop="orderTime" label="订单日期">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="openEdit(scope.row)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
      :current-page.sync="formInline.pageNum" :page-size.sync="formInline.pageSize"
      layout="total,sizes,prev, pager, next,jumper" :total="total">
    </el-pagination>
    <!-- 新增/修改页面 -->
    <el-dialog :visible.sync="show" width="500px">
      <el-input type="hidden" :name="dialogType"></el-input>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="订单编号">
          <el-input v-model="form.id" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="客户编号">
          <el-input v-model="form.customerId" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="客户名称">
          <el-input v-model="form.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="预定车型">
          <el-input v-model="form.carname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="取车城市">
          <el-input v-model="form.fcname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="取车门店">
          <el-input v-model="form.fsname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="取车时间">
          <el-input v-model="form.fromTime" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="还车城市">
          <el-input v-model="form.tcname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="还车门店">
          <el-input v-model="form.tsname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="还车时间">
          <el-input v-model="form.toTime" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-input v-model="form.status" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-input v-model="form.type" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="订单日期">
          <el-input v-model="form.orderTime" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="合同类型">
          <el-input v-model="form.agreement" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="show = false">返回</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'Order',
    data() {
      return {
        queryForm: {
          id: '',
          customerId: '',
          carname: '',
          pageNum: 1,
          pageSize: 10
        },
        tableData: [],
        total: 0,
        //显示/隐藏对话框
        show: false,
        //初始化表单对象
        form: {},


        dialogType: 'edit',

        disabled: true,



        formInline: {
          id: '',
          customerId: '',
          name: '',
          carname: '',
          pageNum: 1,
          pageSize: 10
        },
      }

    },
    methods: {
      query() {
        this.$axios({
          method: 'post',
          url: 'api/lease/order/list',
          data: this.formInline
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

      openEdit(row) {
        this.tittle = "详情";
        this.form = {};
        this.disabled = false;
        this.show = true;
        this.dialogType = 'edit';
        this.form = Object.assign({}, row);
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
