<template>
	<div>
		<div>
			<el-form :inline="true"  style="text-align: left;">
				<el-form-item label="起始时间">
					<div class="block">
						<div class="demonstration">值：{{ startDate.payTime }}</div>
						<el-date-picker v-model="startDate.payTime" type="date" placeholder="选择日期"
							format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
						</el-date-picker>
					</div>
				</el-form-item>
				<el-form-item label="终止时间">
					<div class="block">
						<div class="demonstration">值：{{ endDate.payTime }}</div>
						<el-date-picker v-model="endDate.payTime" type="date" placeholder="选择日期"
							format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
						</el-date-picker>
					</div>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="getAndShow">统计</el-button>
				</el-form-item>
			</el-form>
		</div>
		<div class="Echarts">
			<div id="main" style="width: 1200px;height:500px;"></div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'ReportMoney',
		data() {
			return {
				startDate: {
					payTime: ''
				},
				endDate: {
					payTime: ''
				},
				msg: '总收入',
				sum: '',
				incomes: [],
				days: []
			}
		},
		methods: {
			onSubmit: function() {
				this.incomes = [];
				this.sum = '';
				this.days = [];
				console.info("ffffff");
				this.$axios({
					method: 'post',
					url: 'api/lease/order/getIncomeSum',
					data: {
						startDate: this.startDate,
						endDate: this.endDate
					}
				}).then(res => {
					if (res.data.success) {
						this.sum = res.data.obj;
						console.log("kkkkkk");
					}
				}).catch(err => {

				});
				this.$axios({
					method: 'post',
					url: 'api/lease/order/getDailySum',
					data: {
						startDate: this.startDate,
						endDate: this.endDate
					}
				}).then(res => {
					if (res.data.success) {
						for (let key in res.data.obj) {
							this.days.push(key);
							this.incomes.push(res.data.obj[key]);
						}
						//console.log("555555");
						this.echart();
					}
				}).catch(err => {

				});
			},
			echart: function() {
				var moneyEchart = this.$echarts.init(document.getElementById('main'));
				var option = {
					title: {
						text: this.msg + this.sum
					},
					xAxis: {
						data: this.days
					},
					yAxis: {},
					series: [{
						type: 'line',
						color: '#6699ff',
						data: this.incomes
					}]
				};
				moneyEchart.setOption(option);
			},
			getAndShow: function() {
				this.onSubmit();
			}
		},
		mounted() {
			this.echart();
		}
	}
</script>

<style>
</style>
