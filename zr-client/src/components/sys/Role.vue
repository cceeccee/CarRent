<template>
	<div>
		<!-- 查询条件 -->
		<el-form :inline="true" :model="queryRoleForm" style="text-align: left;">
			<el-form-item label="角色名称">
				<el-input v-model="queryRoleForm.name" placeholder="角色名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="queryRole">查询</el-button>
				<el-button type="primary" @click="openAdd" plain>新增</el-button>
			</el-form-item>
		</el-form>

		<!-- 数据列表 -->
		<el-table :data="tableRoleData" style="width: 100%" :header-cell-style="{background:'#f7f7f7',color:'#666666'}">
			<el-table-column prop="name" label="角色名称">
			</el-table-column>
			<el-table-column prop="mark" label="角色备注">
			</el-table-column>
			<el-table-column prop="authList" label="拥有权限">
				<template slot-scope="scope">
					<el-tag v-for="item in scope.row.authList" :key="item.id">{{item.name}}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="createId" label="新增人员">
			</el-table-column>
			<el-table-column prop="createTime" label="新增时间">
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

		<!-- //分页 -->
		<el-pagination v-show="total > 0" background @size-change="queryRole" :current-page.sync="queryRoleForm.pageNum"
			:page-size.sync="queryRoleForm.pageSize" layout="total,sizes,prev, pager, next,jumper" :total="total">
		</el-pagination>

		<!-- 新增/修改 未做校验！！！-->
		<el-dialog :title="title" :visible.sync="show" width="500px">
			<el-input type="hidden" :name="dialogType"></el-input>
			<span slot="footer" class="dialog-footer">
				<el-form ref="form" :rules="rules" :model="form" label-width="80px">
					<el-form-item label="角色名称" prop="name">
						<el-input v-model="form.name"></el-input>
					</el-form-item>
					<el-form-item label="角色备注" prop="mark">
						<el-input v-model="form.mark"></el-input>
					</el-form-item>

					<!-- 权限 -->
					<el-tree :data="authTree" show-checkbox default-expand-all node-key="id" ref="tree"
						highlight-current :props="defaultProps">
					</el-tree>

				</el-form>
				<el-button @click="show = false">取 消</el-button>
				<el-button @click="confirmAuth()">权限确定</el-button>
				<el-button type="primary" @click="submitForm()">提 交</el-button>
			</span>
		</el-dialog>

	</div>
</template>

<script>
	export default {
		name: 'Role',

		data() {
			return {
				//数据查询对象
				queryRoleForm: {
					name: '',
					pageNum: 1,
					pageSize: 10
				},
				//数据表格对象
				tableRoleData: [],
				//一共多少条数据
				total: 0,
				//对话框的标题
				title: '',
				//显示隐藏对话框
				show: false,
				// 初始化form提交对象
				form: {},
				rules: {
					name: [{
						required: true,
						message: '请输入角色名称',
						trigger: 'blur'
					}],
					mark: [{
						required: true,
						message: '请输入角色备注',
						trigger: 'blur'
					}],
				},
				//标识新增或修改
				dialogType: 'add',
				//权限树
				authTree: [],
				//权限key值
				authKey: [],
				defaultProps: {
					children: 'children', //指定孩子结点的标签
					label: 'name' //指定孩子结点的变量名
				}
			};
		},


		methods: {
			queryRole() {
				this.$axios({
					method: 'post',
					url: 'api/sys/role/roleList',
					data: this.queryRoleForm
				}).then(res => {
					if (res.data.success) {
						this.tableRoleData = res.data.obj.list;
						this.total = res.data.obj.total;

					} else {
						this.$message.error(res.data.msg);
					}
				}).catch(err => {
					console.info(err)
				});
			},
			fetchNodeList() {
				this.$axios({
					method: 'post',
					url: 'api/sys/auth/fetchNodeList',
					data: this.authTree
				}).then(res => {
					this.authTree = res.data.obj
				}).catch(err => {
					console.info(err)
				});
			},
			openAdd() {
				this.authKey = [];
				this.title = "新增";
				this.form = {};
				this.authTree = [];
				this.show = true;
				this.dialogType = "add";

				this.fetchNodeList();
				this.$nextTick(function() {
					this.$refs['form'].clearValidate();
				});
			},
			confirmAuth() {

				this.form.authKey = this.$refs.tree.getCheckedKeys();
				//console.info(this.form);
			},
			openEdit(row) {
				this.title = "编辑";
				this.show = true;
				this.dialogType = "edit";
				//队列表行对象 克隆、解绑、双向绑定
				this.form = Object.assign({}, row);
				this.fetchNodeList();
				for (let i = 0; i < row.authList.length; i++) {
					this.authKey[i] = row.authList[i].id;

				}
				//console.info(row.authList);
				//console.info(this.authKey);
				this.$refs.tree.setCheckedKeys(this.authKey);
			},
			del(row) {
				this.$axios({
					method: 'get',
					url: 'api/sys/role/del/' + row.id
				}).then(res => {
					//刷新页面
					this.queryRole()
				}).catch(err => {
					console.info(err)
				});
			},
			submitForm() {
				if (this.dialogType == 'add') {
					this.$axios({
						method: 'post',
						url: 'api/sys/role/add',
						data: this.form
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.queryRole()
					}).catch(err => {
						console.info(err)
					});
				} else {
					this.$axios({
						method: 'post',
						url: 'api/sys/role/edit',
						data: this.form
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.queryRole()
					}).catch(err => {
						console.info(err)
					});
				}
			},

		},
		created() {
			//获取节点权限

			//初始化页面时进行数据查询
			this.queryRole();
		}
	}
</script>

<style>
</style>
