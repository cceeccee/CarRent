<template>
	<div>
		<div>
			<el-form :inline="true" :model="queryForm" style="text-align: left;">
				<el-form-item label="起始时间">
					<div class="block">
						<div class="demonstration">值：{{ startDate.orderTime }}</div>
						<el-date-picker v-model="startDate.orderTime" type="date" placeholder="选择日期"
							format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
						</el-date-picker>
					</div>
				</el-form-item>
				<el-form-item label="终止时间">
					<div class="block">
						<div class="demonstration">值：{{ endDate.orderTime }}</div>
						<el-date-picker v-model="endDate.orderTime" type="date" placeholder="选择日期"
							format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
						</el-date-picker>
					</div>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="onSubmit">统计</el-button>
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
		name: 'ReportCar',
		data() {
			return {
				startDate: {
					orderTime: ''
				},
				endDate: {
					orderTime: ''
				},
				carNames: [],
				carNums: [],
				
				dateL: {"startDate":{"orderTime":"2019-07-11"},"endDate":{"orderTime":"2022-07-15"}}
			}
		},
		methods: {
			onSubmit: function() {
				this.carNames = [];
				this.carNums = [];
				this.$axios({
					method: 'post',
					url: 'api/lease/order/getCarNum',
					data: {
						startDate: this.startDate,
						endDate: this.endDate
					}
				}).then(res => {
					if (res.data.success) {
						for (let key in res.data.obj) {
							this.carNames.push(key);
							this.carNums.push(res.data.obj[key]);
							//console.log("jjjj");
						}
						this.echart();
					}
				}).catch(err => {

				})
			},
			onSubmitL: function() {
				this.carNames = [];
				this.carNums = [];
				this.$axios({
					method: 'post',
					url: 'api/lease/order/getCarNum',
					data: dateL
				}).then(res => {
					if (res.data.success) {
						for (let key in res.data.obj) {
							this.carNames.push(key);
							this.carNums.push(res.data.obj[key]);
							//console.log("jjjj");
						}
						this.echart();
					}
				}).catch(err => {
			
				})
			},
			echart: function() {
				var carEchart = this.$echarts.init(document.getElementById('main'));
				var option = {
					title: {
						text: "各车型租赁次数统计"
					},
					xAxis: {
						data: this.carNames
					},
					yAxis: {},
					series: [{
						type: 'bar',
						color: '#6699ff',
						data: this.carNums
					}]
				};
				carEchart.setOption(option);
			},
			getAndShow: function() {
				this.onsubmit();
			}
		},
		created() {
			this.onSubmitL();
		},
		mounted() {
			this.echart();
		}
	}
</script>

<style>

</style>
