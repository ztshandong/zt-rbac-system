<template>
	<view>
		<!-- <link href="http://172.16.1.217/iconfont/iconfont-weapp-icon.css" rel="stylesheet" type="text/css"> -->
		<!-- <image class="logo" src="/static/logo.png"></image> -->
		<!-- <view class="text-area">
			<text class="title">{{ title }}</text>
		</view> -->
		<!-- <view class="t-zhangtao t-zhangtao-word2 ali"></view>aaa -->
		<!-- <text class="t-zhangtao t-zhangtao-ppt2 ali"></text> -->
		<scroll-view :bounce="false" @scrolltolower="scrolltolower" scroll-y
			style="width: 750rpx;height: 200px;background-color: #C0C0C0;">
			<view style="height: 999px;width:750rpx;background-color: #007AFF;">{{msg}}</view>
		</scroll-view>
		<view class="u-demo">
			<view class="u-demo-wrap">
				<view class="u-demo-title">最新公告</view>
				<view class="u-demo-area">
					<u-toast :type="type" ref="uToast"></u-toast>
					<u-notice-bar :autoplay="autoplay" :playState="playState" :speed="speed" :mode="mode" @click="click"
						:show="show" :type="type" :list="list" :moreIcon="moreIcon" :volumeIcon="volumeIcon"
						:duration="duration" :isCircular="isCircular">
					</u-notice-bar>
				</view>
			</view>
		</view>

		<!-- <u-button type="primary" @click="testUcloud" ></u-button> -->

		<view class="u-content">
			<u-parse :html="detail.content"></u-parse>
		</view>
		<!-- 
		<view class="container">
			<web-view :webview-styles="webviewStyles" :src="articleurl"></web-view>
		</view>
		 -->
		<!-- 
		<view class="u-demo">
			<view class="u-demo-wrap">
				<view class="u-demo-title">演示效果</view>
				<view class="u-demo-area">
					<u-toast :type="type" ref="uToast"></u-toast>
					<u-notice-bar :autoplay="autoplay" :playState="playState" :speed="speed" @getMore="getMore"
						:mode="mode" @end="end" @close="close" @click="click" :show="show" :type="type" :list="list"
						:moreIcon="moreIcon" :volumeIcon="volumeIcon" :duration="duration" :isCircular="isCircular">
					</u-notice-bar>
				</view>
			</view>

			<view class="u-config-wrap">
				<view class="u-config-title u-border-bottom">
					参数配置
				</view>
				
				<view class="u-config-item">
					<view class="u-item-title">主题</view>
					<u-subsection :current="0" :list="['primary', 'success', 'error', 'warning', 'none']"
						@change="typeChange"></u-subsection>
				</view>
				<view class="u-config-item">
					<view class="u-item-title">滚动模式</view>
					<u-subsection :current="current" :list="['水平', '垂直']" @change="modeChange"></u-subsection>
				</view>
				<view class="u-config-item">
					<view class="u-item-title">状态</view>
					<u-subsection :list="['播放', '暂停']" @change="playStateChange"></u-subsection>
				</view>
				<view class="u-config-item">
					<view class="u-item-title">速度</view>
					<u-subsection :current="1" :list="['慢', '正常', '快']" @change="speedChange"></u-subsection>
				</view>
				<view class="u-config-item">
					<view class="u-item-title">图标</view>
					<u-subsection :list="['显示', '隐藏']" @change="iconChange"></u-subsection>
				</view>
				
			</view>
			
		</view>
 -->
		<u-tabbar v-model="currentTab" :list="tabbar" :mid-button="true"></u-tabbar>
	</view>
</template>

<script>
	import {
		tabbars
	} from "@/common/common.js"
	export default {
		data() {
			return {
				//this.$u.http.config.baseUrl
				msg: [],
				title: '首页',
				currentTab: 1,
				tabbar: tabbars,
				show: true,
				autoplay: false,
				type: 'primary',
				list: [
					'卓睿-管理系统移动端',
					'我的介绍，点击查看'
				],
				mode: 'vertical',
				playState: 'play',
				speed: 160,
				duration: 2000,
				moreIcon: true,
				volumeIcon: true,
				isCircular: false,
				current: 1,
				articleurl: 'https://gitee.com/ztshandong/zt-rbac-system',
				webviewStyles: {
					progress: {
						color: '#FF7200'
					}
				},
				detail: {
					content: `<h1>基于卓睿-极速开发框架的RBAC权限管理系统</h1>
								<h3>卓越超凡，睿智通达</h3>
								<p><a href="https://v.youku.com/v_show/id_XMjc0NzcxODkyNA==.html?firsttime=0"><img alt="" src="https://ztshandong.oss-cn-shanghai.aliyuncs.com/kid/login-background.jpg"/></a></p>
								<h1></h1>
								<h1>权限分为三种</h1>
								<ul><li># 菜单权限-哪些人能看哪些界面 (模块，菜单，按钮)</li><li># 数据权限-哪些人能看哪些人的数据 (部门上下级，职位上下级，用户上下级)</li><li># 字段权限-哪些人能看哪些敏感的信息 (岗位级别，敏感字段)</li></ul>
								<h1></h1>
								<h1>角色才有权限</h1>
								<h1></h1>
								<h1>部门，职位，用户只可以分配角色</h1>`
				}
			}
		},
		onReady() {
			console.log('this')
			console.log(this)
			console.log('this.$u')
			console.log(this.$u)
			// #ifdef APP-PLUS
			console.log(
				plus.push.getClientInfo().clientid
			);
			console.log(
				plus.push.getClientInfo().token
			);
			plus.push.addEventListener('receive', e => {
				this.msg.push(e)
				uni.showToast({
					title: JSON.stringify(e),
					icon: 'none'
				})
			})


			// var height = 0; //定义动态的高度变量，如高度为定值，可以直接写
			// uni.getSystemInfo({
			// 	//成功获取的回调函数，返回值为系统信息
			// 	success: (sysinfo) => {
			// 		height = sysinfo.windowHeight - 47; //自行修改，自己需要的高度
			// 		console.log(height);
			// 	},
			// 	complete: () => {}
			// });
			// var currentWebview = this.$scope.$getAppWebview(); //获取当前web-view
			// setTimeout(function() {
			// 	var wv = currentWebview.children()[0];
			// 	console.log(wv);
			// 	wv.setStyle({ //设置web-view距离顶部的距离以及自己的高度，单位为px
			// 		top: 68,
			// 		height: height
			// 	})
			// }, 1000); //如页面初始化调用需要写延迟
			// #endif
		},
		methods: {
			typeChange(index) {
				let type = ['primary', 'success', 'error', 'warning', 'none'];
				this.type = type[index];
			},
			modeChange(index) {
				this.current = index;
				this.mode = index == 0 ? 'horizontal' : 'vertical';
			},
			playStateChange(index) {
				this.playState = index == 0 ? 'play' : 'paused';
			},
			speedChange(index) {
				if (index == 0) {
					this.$nextTick(() => {
						this.speed = 50;
						this.duration = 6000;
					})
				} else if (index == 1) {
					this.$nextTick(() => {
						this.speed = 160;
						this.duration = 2000;
					})
				} else {
					this.$nextTick(() => {
						this.speed = 350;
						this.duration = 400;
					})
				}
			},
			iconChange(index) {
				if (index == 0) {
					this.moreIcon = true;
					this.volumeIcon = true;
				} else {
					this.moreIcon = false;
					this.volumeIcon = false;
				}
			},
			close() {
				this.toast(`点击了关闭图标`);
			},
			click(index) {
				// if (this.mode == 'horizontal' && this.isCircular) {
				// 	this.toast('此模式无法获取Index值');
				// } else {
				// 	this.toast(this.list[index]);
				// 	this.toast(`点击了第${index + 1}条消息`);
				// }
				if (index == 0) {
					this.$u.route('pages/tabbar/index/framework')
				} else {
					this.$u.route('pages/tabbar/index/youku')
				}
			}
		},
		getMore(e) {
			console.log(e)
			this.toast(`点击了更多图标`);
		},
		toast(title) {
			this.$refs.uToast.show({
				title: title,
				type: 'warning'
			})
		},
		end() {
			// console.log('end');
		},
		scrolltolower(e) {
			console.log(e);
		}
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

	.u-content {
		padding: 24rpx;
		font-size: 32rpx;
		color: $u-content-color;
		line-height: 1.6;
	}
</style>
