<template>
	<view>
		<!-- 
		<view class="rowcontainer">
			<u-button type="primary" @click="login" :custom-style="customStyle">登录2</u-button>
		</view> 
		-->

		<!-- shape="circle" type="primary" -->
		<!-- <u-button :custom-style="customStyle" plain shape="circle" class="t-zhangtao t-zhangtao-word2 ali">
		</u-button> -->
		<!-- 
		<text class="t-zhangtao t-zhangtao-word2 ali">
			<u-button :plain="true" :custom-style="customStyle" shape="circle" @click="test"></u-button>
		</text>
		 -->
		<view>
			<!-- <text class="t-zhangtao t-zhangtao-ppt2 ali"></text> -->
			<!-- <u-button type="primary" @click="login">登录</u-button> -->
			<view class="container">
				<u-button type="primary" size="mini" @click="query">查询角色信息</u-button>
			</view>
			<view class="list-wrap">
				<u-cell-group title-bg-color="rgb(243, 244, 246)" title="角色信息">
					<u-cell-item :titleStyle="{fontWeight: 500}" @click="gotoDetail(item1)"
						:title="getFieldTitle(item1)" v-for="(item1, index1) in list" :key="index1">
						<!-- <image slot="icon" class="u-cell-icon" :src="getIcon(item1.icon)" mode="widthFix"></image> -->
					</u-cell-item>
				</u-cell-group>
			</view>
			<u-gap height="70"></u-gap>
			<u-tabbar v-model="current" :list="tabbar" :mid-button="true"></u-tabbar>
		</view>
	</view>
</template>

<script>
	var _this;
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
					// marginTop: '20rpx', // 注意驼峰命名，并且值必须用引号包括，因为这是对象
					// color: 'red',
					// maxWidth: '500rpx',
					// minWidth: '400rpx',
					backgroundColor: '#ffffff',
					width: '100rpx',
					height: '100rpx',
				},
				list: []
			}
		},
		onLoad() {
			_this = this
		},
		onShow() {
			// #ifdef APP-PLUS
			console.log('-----------center plus.push.getClientInfo().clientid')
			console.log(
				plus.push.getClientInfo().clientid 
			);
			console.log(
				plus.push.getClientInfo().token
			);
			// #endif
		},
		methods: {
			test() {
				console.log('click')
			},
			gotoDetail(item) {
				// console.log(item)
				uni.navigateTo({
					url: 'detail?item=' + encodeURIComponent(JSON.stringify(item))
				});
			},
			getFieldTitle(item) {
				return item.thisName
			},
			query() {
				// console.log(this.$api)
				this.$u.post('/ZtRoleInfo/selectSimple', null)
					.then(r => {
						// console.log(r)
						_this.list = r.data.results
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
