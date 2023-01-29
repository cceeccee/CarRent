import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import NoAuth from '@/components/NoAuth'
import SysUser from '@/components/sys/User'
import SysRole from '@/components/sys/Role'
import SysAuth from '@/components/sys/Auth'
import ShopCity from '@/components/shop/City'
import ShopShop from '@/components/shop/Shop'

// czy
import CarCar from '@/components/car/Car'
import CarWork from '@/components/car/Work'

//hyl
import LeaseCustomer from '@/components/lease/Customer'
import LeaseOrder from '@/components/lease/Order'

//jjj
import ReportCar from '@/components/car/ReportCar'
import ReportMoney from '@/components/lease/ReportMoney'

//zy
import LeaseAgreement from '@/components/lease/Agreement'

import SysProfile from '@/components/sys/Profile'

Vue.use(Router)

export default new Router({
	routes: [{
			path: '/',
			redirect: 'Login'
		},
		{
			path: '/Login',
			name: 'Login',
			component: Login
		},
		{
			path: '/Home',
			name: 'Home',
			component: Home,
			children: [{
					path: '/NoAuth',
					name: 'NoAuth',
					component: NoAuth
				},
				{
					path: '/SysUser',
					name: 'SysUser',
					component: SysUser
				},
				{
					path: '/SysProfile',
					name: 'SysProfile',
					component: SysProfile
				},
				{
					path: '/SysRole',
					name: 'SysRole',
					component: SysRole
				},
				{
					path: '/SysAuth',
					name: 'SysAuth',
					component: SysAuth
				},
				{
					path: '/ShopCity',
					name: 'ShopCity',
					component: ShopCity
				},
				{
					path: '/ShopShop',
					name: 'ShopShop',
					component: ShopShop
				},
				// czy

				{
					path: '/CarWork',
				 name: 'CarWork',
					component: CarWork
				},
				{
					path: '/CarCar',
					name: 'CarCar',
					component: CarCar
				},
				//hyl
				{
					path: '/LeaseCustomer',
					name: 'LeaseCustomer',
					component: LeaseCustomer
				},
				{
					path: '/LeaseOrder',
					name: 'LeaseOrder',
					component: LeaseOrder
				},
				//jjj
				{
					path: '/ReportCar',
					name: 'ReportCar',
					component: ReportCar
				},
				{
					path: '/ReportMoney',
					name: 'ReportMoney',
					component: ReportMoney
				},
				///zy
				{
					path: '/LeaseAgreement',
					name: 'LeaseAgreement',
					component: LeaseAgreement
				}
			]
		}
	]
})
