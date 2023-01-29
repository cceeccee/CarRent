<template>
	<div>
		<el-form :inline="true" :model="queryForm" style="text-align: left;">
			<el-form-item label="名称">
				<el-input v-model="queryForm.name" placeholder="名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="query">查询</el-button>
				<el-button type="primary" @click="openAdd" plain>新增</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="tableData" style="width: 150%">
			<el-table-column prop="customerId" label="客户账号">
			</el-table-column>
			<el-table-column prop="name" label="客户姓名">
			</el-table-column>
			<el-table-column prop="idcard" label="身份证号">
			</el-table-column>
			<el-table-column prop="contact" label="紧急联系人">
			</el-table-column>
			<el-table-column prop="ctel" label="紧急联系人电话">
			</el-table-column>
			<el-table-column prop="status" label="状态">
			</el-table-column>
			<el-table-column prop="createId" label="新增人员">
			</el-table-column>
			<el-table-column prop="createTime" label="新增时间">
			</el-table-column>
			<el-table-column prop="modifyId" label="修改人员">
			</el-table-column>
			<el-table-column prop="modifyTime" label="修改时间">
			</el-table-column>
			<el-table-column fixed="right" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="openEdit(scope.row)">编辑</el-button>
					<el-popconfirm title="确定删除吗?" @confirm="del(scope.row)">
						<el-button slot="reference" type="text" size="small">删除</el-button>
					</el-popconfirm>
				</template>
			</el-table-column>
		</el-table>
		<!-- 分页 -->
		<el-pagination v-show="total > 0" background @size-change="query" @current-change="query"
			:current-page.sync="queryForm.pageNum" :page-size.sync="queryForm.pageSize"
			layout="total,sizes,prev, pager, next,jumper" :total="total">
		</el-pagination>
		<!-- 新增/修改页面 -->
		<el-dialog :title="title" :visible.sync="show" width="500px">
			<el-input type="hidden" :name="dialogType"></el-input>
			<el-form :model="form" :rules="rules" ref="form" label-width="100px">
				<el-form-item label="账号" prop="customerId">
					<el-input v-model="form.customerId" clearable :disabled="disabled"></el-input>
				</el-form-item>
				<el-form-item label="姓名" prop="name">
					<el-input v-model="form.name" clearable></el-input>
				</el-form-item>
				<el-form-item label="密码" prop="password">
					<el-input v-model="form.password" type="password" clearable></el-input>
				</el-form-item>
				<el-form-item label="重复" prop="repPassword">
					<el-input v-model="form.repPassword" type="password" clearable></el-input>
				</el-form-item>
				<el-form-item label="身份证号" prop="idcard">
					<el-input v-model="form.idcard" type="idcard" clearable></el-input>
				</el-form-item>
				<el-form-item label="紧急联系人" prop="contact">
					<el-input v-model="form.contact" type="contact" clearable></el-input>
				</el-form-item>
				<el-form-item label="联系人电话" prop="ctel">
					<el-input v-model="form.ctel" type="ctel" clearable></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button type="primary" @click="submitForm">提交</el-button>
				<el-button @click="show = false">取消</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	export default {
		name: 'Customer',
		data() {
			let validatePass = (rule, value, callback) => {
				if (this.form.password != '' && value === undefined) {
					callback(new Error('请再次输入密码'));
				} else if (this.form.password != '' && value != this.form.password) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
			return {
				//查询条件对象
				queryForm: {
					name: '',
					pageNum: 1,
					pageSize: 10
				},
				//数据表格数组
				tableData: [],
				//一共多少条数据
				total: 10,
				//对话框标题
				title: '',
				//显示/隐藏对话框
				show: false,
				//初始化表单对象
				form: {},
				//初始化校验对象
				rules: {
					customerId: [{
						required: true,
						message: '请输入账号',
						trigger: 'blur'
					}],
					name: [{
						required: true,
						message: '请输入姓名',
						trigger: 'blur'
					}],
					password: [{
						required: true,
						message: '请输入密码',
						trigger: 'blur'
					}],
					repPassword: [{
						required: true,
						validator: validatePass,
						trigger: 'blur'
					}]
				},
				//标识是新增还是修改
				dialogType: 'add',
				//主键是否可以被编辑:默认可以编辑
				disabled: false
			}
		},
		methods: {
			//查询数据
			query() {
				this.$axios({
					method: 'post',
					url: 'api/lease/customer/list',
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
				//设置校验规则为可校验密码
				this.rules.password[0].required = true;
				this.rules.repPassword[0].required = true;
				//获取所有角色数组
				//this.queryRoleList();
				//清除表单校验结果
				this.$nextTick(function() {
					this.$refs['form'].clearValidate();
				});
			},
			openEdit(row) {
				//设置对话框标题
				this.title = "编辑";
				//清空表单
				this.form = {};
				//主键不可以编辑
				this.disabled = true;
				//设置校验规则为不可校验密码
				this.rules.password[0].required = false;
				this.rules.repPassword[0].required = false;
				//打开对话框
				this.show = true;
				//更改标识为新增
				this.dialogType = 'edit';
				//获取所有角色数组
				//this.queryRoleList();
				//对列表行对象进行克隆，解决双向绑定问题 注意：select 需要 @change="$forceUpdate()"强制刷新
				this.form = Object.assign({}, row);
				//清空密码
				this.form.password = "";
				//清除表单校验结果
				this.$nextTick(function() {
					this.$refs['form'].clearValidate();
				});
			},
			change(row) {
				this.$confirm('确认要更改账号状态吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$axios({
						method: 'post',
						url: 'api/lease/customer/change',
						data: row
					}).then(res => {
						this.getList();
					}).catch(err => {
						console.log(err)
					})
				}).catch(() => {
					if (row.status == "0") {
						row.status = "1"
					} else {
						row.status = "0"
					}
					this.$message({
						type: 'info',
						message: '已取消操作'
					});
				});
			},
			submitForm() {
				this.$refs['form'].validate((valid) => {
					if (valid) {
						if (this.dialogType == 'add') {
							this.$axios({
								method: 'post',
								url: 'api/lease/customer/add',
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
								url: 'api/lease/customer/edit',
								data: this.form
							}).then(res => {
								//关闭对话框
								this.show = false;
								//刷新页面
								this.query()
							}).catch(err => {
								console.info(err)
							});
						}
					}
				});
			},
			del(row) {
				this.$axios({
					method: 'get',
					url: 'api/lease/customer/del/' + row.id
				}).then(res => {
					//刷新页面
					this.query()
				}).catch(err => {
					console.info(err)
				});
			},
			/* queryRoleList() {
			  this.$axios({
			    method: 'post',
			    url: 'api/sys/role/selectList'
			  }).then(res => {
			    this.roleList = res.data.obj;
			  }).catch(err => {
			    console.info(err)
			  });
			} */
		},
		created() {
			//初始化页面时进行数据查询
			this.query();
		}
	}
</script>

<style>
</style>
