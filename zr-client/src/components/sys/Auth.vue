<template>
	<div style="text-align: left;">
		<el-button type="primary" @click="openEdit" plain>编辑</el-button>
		<el-button type="primary" @click="openAdd" plain>新建</el-button>
		<el-popconfirm title="确定删除吗?" @confirm="del">
			<el-button slot="reference" type="danger">删除</el-button>
		</el-popconfirm>

		<!-- 新增/修改 未做校验！！！-->
		<el-dialog :title="title" :visible.sync="show" width="500px">
			<el-input type="hidden" :name="dialogType"></el-input>
			<span slot="footer" class="dialog-footer">
				<el-form ref="form" :rules="rules" :model="form" label-width="80px">
					<el-form-item label="权限名称" prop="name">
						<el-input v-model="form.name"></el-input>
					</el-form-item>
					<el-form-item label="访问地址" prop="url">
						<el-input v-model="form.url"></el-input>
					</el-form-item>
					<el-form-item label="排序序号" prop="seq">
						<el-input v-model="form.seq"></el-input>
					</el-form-item>
					<el-form-item label="权限备注" prop="mark">
						<el-input v-model="form.mark"></el-input>
					</el-form-item>
					<el-form-item label="上级菜单">
						<el-select v-model="form.pid" placeholder="请选择上级菜单" @change="$forceUpdate()">
							<el-option v-for="item in data" :key="item.id" :label="item.name" :value="item.id">
							</el-option>
						</el-select>
					</el-form-item>
				</el-form>
				<el-button @click="show = false">取 消</el-button>
				<el-button @click="confirmAuth()">权限确定</el-button>
				<el-button type="primary" @click="submitForm()">提 交</el-button>
			</span>
		</el-dialog>
		<!-- 树形权限 -->
		<el-tree :data="data" show-checkbox default-expand-all node-key="id" ref="tree" highlight-current
			:props="defaultProps">
		</el-tree>
	</div>
</template>

<script>
	export default {
		name: 'Auth',
		data() {
			return {
				arr: [],
				form: {},
				defaultProps: {
					children: 'children',
					label: 'name'
				},
				//原始JSON存放
				originForm: {},
				//树形权限存放位置
				data: [],
				//
				treeData: [],
				//显示隐藏对话框
				show: false,
				//对话框的标题
				title: '',
				//标识是新增还是修改
				dialogType: 'add',
				//权限key值
				authKey:[],
				//权限authNode
				authNode:{},
				//规则
				rules: {
					name: [{
						required: true,
						message: '请输入权限名称',
						trigger: 'blur'
					}],
					url: [{
						required: true,
						message: '请输入权限访问地址',
						trigger: 'blur'
					}],
					seq: [{
						required: true,
						message: '请输入权限排序序号',
						trigger: 'blur'
					}],
					mark: [{
						required: true,
						message: '请输入权限备注',
						trigger: 'blur'
					}],
				},
			}
		},
		methods: {
			fetchNodeList() {
				this.$axios({
					method: 'post',
					url: 'api/sys/auth/fetchNodeList',
					data: this.form
				}).then(res => {
					//存放树形数据于data
					this.data = res.data.obj;
					this.originForm = res.data;
				}).catch(err => {
					console.info(err)
				});
			},
			openAdd() {
				this.form = {};
				this.title = "新建";
				this.show = true;
				this.dialogType = 'add';
			},
			openEdit() {
				this.title = "编辑";
				this.show = true;
				this.dialogType = 'edit';
				this.authNode = this.$refs.tree.getCheckedNodes();
				this.form = Object.assign({},this.authNode);
				console.info(this.form[0]);
				this.form = Object.assign({},this.form[0]);
			},
			//权限确定
			confirmAuth() {

				this.form.authKey = this.$refs.tree.getCheckedKeys();
				//console.info(this.form);
			},
			del() {
				this.authNode = this.$refs.tree.getCheckedNodes();
				this.form = Object.assign({},this.authNode);
				//这里多包了一层，得去掉！！！！！！！
				this.form = Object.assign({},this.form[0]);
				this.$axios({
					method: 'post',
					url: 'api/sys/auth/del',
					data: this.form
				}).then(res => {
					//刷新页面
					this.fetchNodeList();
				}).catch(err => {
					console.info(err)
				});
			},
			submitForm() {
				this.authKey = this.$refs.tree.getCheckedKeys();
				if (this.dialogType == 'add') {
					this.$axios({
						method: 'post',
						url: 'api/sys/auth/add',
						data: this.form
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.fetchNodeList();
					}).catch(err => {
						console.info(err)
					});
				} else {
					this.$axios({
						method: 'post',
						url: 'api/sys/auth/edit',
						data: this.form
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.fetchNodeList();
					}).catch(err => {
						console.info(err)
					});
				}
			}
		},
		created() {
			this.fetchNodeList();
		}
	}
</script>

<style>
	.el-select {
	  width: 100%;
	}
</style>
