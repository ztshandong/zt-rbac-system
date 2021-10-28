<template>
	<view class="container">
		<!-- <link href="http://172.16.1.217/iconfont/iconfont-weapp-icon.css" rel="stylesheet" type="text/css"> -->
		<image class="logo" src="/static/logo.png"></image>
		<view class="text-area">
			<text class="title">{{ title }}</text>
		</view>
		<view class="t-zhangtao t-zhangtao-word2"></view>
		<text class="t-zhangtao t-zhangtao-ppt2 ali"></text>
		<u-tabbar v-model="current" :list="tabbar" :mid-button="true"></u-tabbar>
	</view>
</template>

<script>
	import {
		tabbars
	} from "@/common/common.js"
	export default {
		data() {
			return {
				title: '首页',
				current: 1,
				tabbar: tabbars
			}
		},

		onLoad() {
			// #ifdef H5
			// this.$u.route('../../wxDemo/wxDemo')
			// #endif
		},
		methods: {}
	}
</script>

<style lang="scss">
	// @import 'http://172.16.1.217/iconfont/iconfont-weapp-icon.wxss';
	// @import url('http://172.16.1.217/iconfont/iconfont-weapp-icon.css');
	
	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
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

	.ali {
		width: 100rpx;
		height: 100rpx;
	}
</style>
