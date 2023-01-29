<template>
  <!-- 查询 -->
  <div>
    <el-form :inline="true" :model="queryForm" style="text-align: left;">
      <el-form-item label="车辆名称">
        <el-input v-model="queryForm.name" placeholder="车辆名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
        <el-button @click="openAdd" plain>新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 数据列表 -->
    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column prop="name" label="名称">
      </el-table-column>
      <el-table-column prop="brand" label="品牌">
      </el-table-column>
      <el-table-column prop="model" label="型号">
      </el-table-column>
      <el-table-column prop="box" label="箱数">
      </el-table-column>
      <el-table-column prop="volume" label="排量">
      </el-table-column>
      <el-table-column prop="door" label="门数">
      </el-table-column>
      <el-table-column prop="seat" label="座数">
      </el-table-column>
      <el-table-column prop="gears" label="档位">
      </el-table-column>
      <el-table-column prop="auto" label="档位类型">
      </el-table-column>
      <el-table-column prop="price" label="价格">
      </el-table-column>
      <el-table-column prop="status" label="状态">
      </el-table-column>
      <el-table-column prop="gears" label="档位">
      </el-table-column>
      <el-table-column prop="createId" label="新增人员">
      </el-table-column>
      <el-table-column prop="createTime" label="新增时间">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="300px">
        <template slot-scope="scope">
          <el-button size="small" type="primary" @click="openImg(scope.row)">图片</el-button>
          <el-button size="small" type="primary" @click="openEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="del(scope.row)">
            <el-button slot="reference" size="small" type="danger">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination v-show="total>0" background @size-change="query" @current-change="query"
      :current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
      layout="prev, pager, next, total, sizes, jumper" :total="total">
    </el-pagination>
    <!-- 新增修改页面 -->
    <el-dialog :title="title" :visible.sync="show" width="500px">
      <el-input type="hidden" :name="dialogType"></el-input>
      <el-form :model="form" :rules="rules" ref="form" label-width="60px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" clearable :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" clearable></el-input>
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" clearable></el-input>
        </el-form-item>
        <el-form-item label="箱数">
          <el-select v-model="form.box" placeholder="请选择">
            <el-option v-for="item in boxList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        </el-form-item>
        <el-form-item label="排量" prop="volume">
          <el-input v-model="form.volume" clearable></el-input>
        </el-form-item>
        <el-form-item label="门数">
          <el-select v-model="form.door" placeholder="请选择" @change="$forceUpdate()">
            <el-option v-for="item in doorList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="座位">
          <el-select v-model="form.seat" placeholder="请选择" @change="$forceUpdate()">
            <el-option v-for="item in seatList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="档位" prop="gears">
          <el-input v-model="form.gears" clearable></el-input>
        </el-form-item>
        <el-form-item label="档位类型">
          <el-select v-model="form.auto" placeholder="请选择" @change="$forceUpdate()">
            <el-option v-for="item in autoOptions" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择" @change="$forceUpdate()">
            <el-option v-for="item in statusList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="show = false">取消</el-button>
      </span>
    </el-dialog>
    <!-- 上传图片 -->
    <el-dialog title="上传图片" :visible.sync="showImg" width="60%">
      <el-image :src="imageUrl" fit="contain">
        <div slot="error" class="image-slot">
          <i class="el-icon-picture-outline"></i>
        </div>
      </el-image>
      <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" :on-preview="handlePreview"
        :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed"
        :file-list="fileList">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
      </el-upload>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitImg">上传</el-button>
        <el-button @click="showImg = false">关闭</el-button>
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
        //图片窗口
        showImg: false,
        //状态类型
        statusList: [{
          value: '正常',
          label: '正常'
        }, {
          value: '下架',
          label: '下架'
        }],
        //新增类型
        boxList: [{
            value: '1',
            label: '1'
          }, {
            value: '2',
            label: '2'
          }, {
            value: '3',
            label: '3'
          }, {
            value: '4',
            label: '4'
          }, {
            value: '5',
            label: '5'
          },
          {
            value: '6',
            label: '6'
          },
          {
            value: '7',
            label: '7'
          },
          {
            value: '8',
            label: '8'
          },
          {
            value: '9',
            label: '9'
          },
          {
            value: '10',
            label: '10'
          }
        ],
        seatList: [{
            value: '1',
            label: '1'
          }, {
            value: '2',
            label: '2'
          }, {
            value: '3',
            label: '3'
          }, {
            value: '4',
            label: '4'
          }, {
            value: '5',
            label: '5'
          },
          {
            value: '6',
            label: '6'
          },
          {
            value: '7',
            label: '7'
          },
          {
            value: '8',
            label: '8'
          },
          {
            value: '9',
            label: '9'
          },
          {
            value: '10',
            label: '10'
          }
        ],
        doorList: [{
            value: '1',
            label: '1'
          }, {
            value: '2',
            label: '2'
          }, {
            value: '3',
            label: '3'
          }, {
            value: '4',
            label: '4'
          }, {
            value: '5',
            label: '5'
          },
          {
            value: '6',
            label: '6'
          },
          {
            value: '7',
            label: '7'
          },
          {
            value: '8',
            label: '8'
          },
          {
            value: '9',
            label: '9'
          },
          {
            value: '10',
            label: '10'
          }
        ],
        //档位类型
        autoOptions: [{
            value: '自动',
            label: '自动'
          },
          {
            value: '手动',
            label: '手动'
          },
          {
            value: '一体',
            label: '一体'
          }
        ],
        value: '',
        //总页数
        total: 0,
        // 表格
        tableData: [],
        //对话框标题
        title: '',
        //显示隐藏对话框
        show: false,
        //初始化表单对象
        form: {},
        //编辑新增表单选项
        dialogype: 'add',
        //主键是否可以被编辑
        disabled: true,

        rules: {
          name: [{
            required: true,
            message: '请输入名称',
            trigger: 'blur'
          }]
        },
      }
    },
    methods: {
      //图片操作
      openImg(row) {
        //设置对话框标题
        this.title = "上传图片";
        //打开对话框
        this.showImg = true;

      },
      //编辑操作
      openEdit(row) {
        //设置对话框标题
        this.title = "编辑";
        //清空表单
        this.form = {};
        //主键不可编辑
        this.disabled = true;
        //打开对话框
        this.show = true;
        //更改标识为新增
        this.dialogType = 'edit';
        //对表单克隆
        this.form = Object.assign({}, row);
        this.form.createTime = '';
        this.form.modifyTime = '';
        //清除表单校验结果
        this.$nextTick(function() {
          this.$refs['form'].clearValidate();
        });
      },
      //删除操作
      del(row) {
        this.$axios({
          method: 'get',
          url: 'api/car/carcar/del/' + row.id,
        }).then(res => {
          //刷新页面
          this.query()
        }).catch(err => {
          console.info(err)
        });
      },
      //新增
      openAdd() {
        //设置对话框标题
        this.title = "新增";
        //清空表单
        this.form = {};
        //主键可编辑
        this.disabled = false;
        //打开对话框
        this.show = true;
        //更改标识为新增
        this.dialogType = 'add';
      },
      //查询数据
      query() {
        this.$axios({
          method: 'post',
          url: 'api/car/carcar/list',
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
      submitForm() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'add') {
              this.$axios({
                method: 'post',
                url: 'api/car/carcar/add',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query()
              }).catch(err => {
                console.info(err)
              });
            } else {
              this.$axios({
                method: 'post',
                url: 'api/car/carcar/edit',
                data: this.form
              }).then(res => {
                //关闭对话框
                this.show = false;
                //刷新页面
                this.query();
              }).catch(err => {
                console.info(err);
              });
        
              }
          }
        });
      }
    },
    created() {
      //初始化页面时进行数据查询
      this.query();
    }

  }
</script>

<style>
  .el-select {
    width: 100%;
  }
</style>
