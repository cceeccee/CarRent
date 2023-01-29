<template>
	<div>
		<el-collapse v-model="activePanel" @change="queryShop">
			<el-collapse-item title="门店列表" name="1">
				<div>
					<!-- 查询条件 -->
					<el-form :inline="true" :model="queryForm" style="text-align: left;">
						<el-form-item label="门店名称">
							<el-input v-model="queryForm.name" placeholder="城市名称"></el-input>
						</el-form-item>
						<el-form-item>
							<el-button type="primary" @click="queryShop">查询</el-button>
							<el-button type="primary" @click="openAdd" plain>新增</el-button>
						</el-form-item>
						<!-- 数据列表 -->
						<el-table :data="tableData" style="width: 100%"
							:header-cell-style="{background:'#f7f7f7',color:'#666666'}">
							<el-table-column prop="cityList" label="城市名称">
								<template slot-scope="scope">
									<el-tag v-for="item in scope.row.cityList" :key="item.name">{{item.name}}
									</el-tag>
								</template>
							</el-table-column>
							<el-table-column prop="cityList" label="省份名称">
								<template slot-scope="scope">
									<el-tag v-for="item in scope.row.cityList" :key="item.pname">{{item.pname}}
									</el-tag>
								</template>
							</el-table-column>
							<el-table-column prop="name" label="门店名称">
							</el-table-column>
							<el-table-column prop="address" label="门店地址">
							</el-table-column>
							<el-table-column prop="tel" label="电话">
							</el-table-column>
							<el-table-column prop="admin" label="负责人">
							</el-table-column>
							<el-table-column prop="status" label="状态">
							</el-table-column>
							<el-table-column prop="modifyId" label="新增人员">
							</el-table-column>
							<el-table-column prop="modifyTime" label="新增时间">
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
						<el-pagination v-show="total > 0" background @size-change="queryShop"
							@current-change="queryShop" :current-page.sync="queryForm.pageNum"
							:page-size.sync="queryForm.pageSize" layout="total,sizes,prev, pager, next,jumper"
							:total="total">
						</el-pagination>

						<!-- 新增/修改页面 -->
						<el-dialog :title="title" :visible.sync="show" width="500px">
							<el-input type="hidden" :name="dialogType"></el-input>
							<el-form :model="form" :rules="rules" ref="form" label-width="60px">
								<el-form-item label="省份">
									<el-select v-model="form.provinceId" placeholder="请选择" @change="$forceUpdate()">
										<el-option v-for="item in pList" :key="item.id" :label="item.name"
											:value="item.id">
										</el-option>
									</el-select>
								</el-form-item>
								<el-form-item label="城市" prop="cname">
									<el-input v-model="form.cname" clearable :disabled="disabled"></el-input>
								</el-form-item>
								<el-form-item label="门店" prop="name">
									<el-input v-model="form.name" clearable :disabled="disabled"></el-input>
								</el-form-item>
								<el-form-item label="地址" prop="address">
									<el-input v-model="form.address" clearable :disabled="disabled"></el-input>
								</el-form-item>
								<el-form-item label="电话" prop="tel">
									<el-input v-model="form.tel" clearable :disabled="disabled"></el-input>
								</el-form-item>
								<el-form-item label="负责人" prop="admin">
									<el-input v-model="form.admin" clearable :disabled="disabled"></el-input>
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
		name: 'Shop',
		data() {
			return {
				queryForm: {
					name: '',
					pageNum: 1,
					pageSize: 5
				},
				tableData: [],
				form: {},
				rules: {},
				total: 0,
				activePanel: '1',
				title: '',
				//标识新增或修改
				dialogType: 'add',
				//显示隐藏对话框
				show: false,
				//省份列表
				pForm: {},
				pList: [],
				//城市列表
				cityList: []
			}
		},
		methods: {
			queryShop() {
				this.$axios({
					method: 'post',
					url: 'api/shop/shop/queryShop',
					data: this.queryForm
				}).then(res => {
					if (res.data.success) {
						this.tableData = res.data.obj.list;
						this.total = res.data.obj.total;
						console.info(res.data.obj.list)
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
				this.cityList = [];
				this.show = true;
				this.dialogType = "add";
				this.listProvince();

			},
			openEdit(row) {
				this.title = "编辑";
				this.show = true;
				this.dialogType = "edit";
				this.form = Object.assign({}, row);
				
				//this.cityList = row.cityList;
				//form.cname = this.cityList[0].name;
				console.info(form.cname);
			},
			listProvince() {
				this.$axios({
					method: 'post',
					url: 'api/shop/province/listProvince',
					data: this.form
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
			}
		},
		created() {
			this.queryShop();
		}
	}
</script>

<style>

</style>
