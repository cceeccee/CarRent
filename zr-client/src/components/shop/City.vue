<template>
	<div>
		<el-collapse v-model="activePanel" @change="queryCity">
			<el-collapse-item title="城市列表" name="1">
				<div>
					<!-- 查询条件 -->
					<el-form :inline="true" :model="queryForm" style="text-align: left;">
						<el-form-item label="城市名称">
							<el-input v-model="queryForm.name" placeholder="城市名称"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="queryCity">查询</el-button>
							<el-button type="primary" @click="openAdd" plain>新增</el-button>
						</el-form-item>
						<!-- 数据列表 -->
						<el-table :data="tableData" style="width: 100%"
							:header-cell-style="{background:'#f7f7f7',color:'#666666'}">
							<el-table-column prop="name" label="城市名称">
							</el-table-column>
							<el-table-column prop="provinceList" label="省份名称">
								<template slot-scope="scope">
									<el-tag v-for="item in scope.row.provinceList" :key="item.name">{{item.name}}
									</el-tag>
								</template>
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
						<!-- 分页 -->
						<el-pagination v-show="total > 0" background @size-change="queryCity"
							@current-change="queryCity" :current-page.sync="queryForm.pageNum"
							:page-size.sync="queryForm.pageSize" layout="total,sizes,prev, pager, next,jumper"
							:total="total">
						</el-pagination>

						<!-- 新增/修改页面 -->
						<el-dialog :title="title" :visible.sync="show" width="500px">
							<el-input type="hidden" :name="dialogType"></el-input>
							<el-form :model="form" :rules="rules" ref="form" label-width="60px">
								<el-form-item label="省份">
									<el-select v-model="form.provinceList" placeholder="请选择" @change="$forceUpdate()">
										<el-option v-for="item in pList" :key="item.id" :label="item.name"
											:value="item.id">
										</el-option>
									</el-select>
								</el-form-item>
								<el-form-item label="城市" prop="userId">
									<el-input v-model="form.name" clearable></el-input>
								</el-form-item>
							</el-form>
							<span slot="footer" class="dialog-footer">
								<el-button type="primary" @click="submitForm">提交</el-button>
								<el-button @click="show = false">取消</el-button>
							</span>
						</el-dialog>

					</el-form>
				</div>
			</el-collapse-item>
		</el-collapse>
	</div>
</template>

<script>
	export default {
		name: 'City',
		data() {
			return {
				queryForm: {
					name: '',
					pageNum: 1,
					pageSize: 10
				},
				tableData: [],
				//提交对象
				form: {},
				rules: {},
				//分页
				total: 0,
				//默认展开
				activePanel: '1',
				// 对话框标题
				title: '',
				//标识新增或修改
				dialogType: 'add',
				//显示隐藏对话框
				show: false,
				//省份列表
				pForm: {},
				pList: []
			}
		},
		methods: {
			queryCity() {
				this.$axios({
					method: 'post',
					url: 'api/shop/city/queryCity',
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
				this.dialogType = "add";
				//获取省份列表
				this.listProvince();
			},
			openEdit(row) {
				this.title = "编辑";
				this.show = true;
				this.dialogType = "edit";
				this.form = Object.assign({}, row);
				//this.form = row;
				console.info(row);
				this.form.provinceList = [];
				for (let i = 0; i < row.provinceList.length; i++) {
					let p = row.provinceList[i].name;
					//console.info(p);
					this.form.provinceList.push(p);
				}
			},
			submitForm() {
				if (this.dialogType == 'add') {
					this.$axios({
						method: 'post',
						url: 'api/shop/city/add',
						data: this.form
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.queryCity()
					}).catch(err => {
						console.info(err)
					});
				} else {
					this.$axios({
						method: 'post',
						url: 'api/shop/city/edit',
						data: this.formm
					}).then(res => {
						//关闭对话框
						this.show = false;
						//刷新页面
						this.queryCity()
					}).catch(err => {
						console.info(err)
					});
				}
			},
			listProvince() {
				this.$axios({
					method: 'post',
					url: 'api/shop/province/listProvince',
					data: this.pForm
				}).then(res => {
					if (res.data.success) {
						this.pList = res.data.obj;
						console.info(this.pList);
					} else {
						this.$message.error(res.data.msg);
					}
				}).catch(err => {
					console.info(err)
				});
			},
			del(row) {
				
			}

		},
		created() {
			this.queryCity();
		}

	}
</script>

<style>
</style>
