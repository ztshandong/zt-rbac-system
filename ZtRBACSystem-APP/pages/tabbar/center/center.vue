<template>
	<view>
		<u-button type="primary" size="mini" @click="login" :custom-style="customStyle" shape="circle" class="t-zhangtao t-zhangtao-word2"></u-button>
		<!-- 
		<view class="rowcontainer">
			<u-button type="primary" @click="login" :custom-style="customStyle">登录2</u-button>
		</view> 
		-->

		<view class="container">
			<!-- <u-button type="primary" @click="login">登录</u-button> -->
			<u-button @click="query">查询</u-button>
			<u-tabbar v-model="current" :list="tabbar" :mid-button="true"></u-tabbar>
		</view>
	</view>
</template>

<script>
	// import resize from '@/common/resize.js'
	import {
		tabbars
	} from "@/common/common.js"
	export default {
		// mixins: [resize],
		data() {
			return {
				title: '中心',
				current: 2,
				tabbar: tabbars,
				customStyle: {
					marginTop: '20rpx', // 注意驼峰命名，并且值必须用引号包括，因为这是对象
					// color: 'red',
					// maxWidth: '500rpx',
					// minWidth: '400rpx',
					backgroundColor: '#ffffff',
					width: '100rpx',
					height: '100rpx',
				}
			}
		},
		onLoad() {},
		methods: {
			query() {
				// console.log(this.$api)
				this.$u.post('/ZtRoleInfo/selectSimple', null)
					.then(r => {
						// console.log(r)
					})
					.catch(err => {
						console.log(err)
					})
					.finally(t => {
						// console.log(t)
					})
			},
		}
	}
</script>

<style lang="scss">
	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		// flex-direction: row;
		// justify-content:space-between;
		// flex-wrap: wrap;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
