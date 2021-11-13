<template>
	<view>
		<u-cell-group>
			<u-cell-item icon="fingerprint" :title="loginOrOut" @click="gotToLoginOrLogout">
				<!-- v-if="isLogin" -->
				<view class="u-flex u-row-right" v-if="isLogin2">
					<u-avatar :src="userInfo2.avatarUrl"></u-avatar>
					<view class="u-m-l-24">{{ userInfo2.nickname }}</view>
				</view>
			</u-cell-item>
			<u-cell-item icon="chat" title="消息提示" @click="showToast('我是一条消息提示', toastType[$u.random(0, 2)])">
			</u-cell-item>
			
			<u-cell-item icon="chat" title="其他" @click="gotoCenter">
			</u-cell-item>
			
			<u-cell-item icon="map" title="获取当前位置" @click="getLocation">
				<view class="u-flex u-row-right" v-if="location">
					<view>经度:{{ location.longitude.toFixed(6) }}</view>
					<view class="u-m-l-24">纬度:{{ location.latitude.toFixed(6) }}</view>
				</view>
			</u-cell-item>
			<u-cell-item icon="pushpin" title="打开当前位置(导航)" @click="chooseLocation">
				<view class="u-flex u-row-right" v-if="location2">
					<view>经度:{{ location2.longitude.toFixed(6) }}</view>
					<view class="u-m-l-24">纬度:{{ location2.latitude.toFixed(6) }}</view>
				</view>
			</u-cell-item>
			<u-cell-item icon="scan" title="扫码" @click="scan">
				<text>{{scanRes}}</text>
			</u-cell-item>
			<u-cell-item icon="phone" title="打电话" @click="makePhoneCall"></u-cell-item>
			<!-- #ifdef APP-PLUS -->
			<u-cell-item icon="weixin-fill" title="打开微信小程序" @click="goToWxMiniInApp"></u-cell-item>
			<u-cell-item icon="star" title="手机号">
				<u-input class="sl-input" v-model="mobile" type="text" maxlength="32" placeholder="输入手机号" />
			</u-cell-item>
			<u-cell-item icon="star" title="绑定手机" @click="bindMobile">
			</u-cell-item>
			<!-- #endif -->
			<!-- 
			<u-cell-item icon="photo" title="选择图片" @click="chooseImage"></u-cell-item>
			<u-cell-item icon="shopping-cart" title="选择收货地址" @click="chooseAddress"></u-cell-item>
			<u-cell-item icon="share" title="设置自定义分享" @click="share"></u-cell-item>
			<u-cell-item icon="rmb-circle" title="微信支付" @click="wxpay"></u-cell-item>
			 -->
		</u-cell-group>
		<v-toast ref="vToast"></v-toast>
		<u-tabbar v-model="current" :list="tabbar" :mid-button="true"></u-tabbar>
	</view>
</template>

<script>
	var _this;
	import appUpdate from "@/uni_modules/uni-upgrade-center-app/utils/check-update.js"
	import {
		tabbars
	} from "@/common/common.js"
	import permision from "@/common/permission.js"
	export default {
		data() {
			return {
				title: '个人中心',
				current: 3,
				tabbar: tabbars,
				location: null,
				location2: null,
				toastType: ['success', 'warning', 'error'],
				isLogin2: false,
				userInfo2: {},
				scanRes: '',
				loginOrOut: '登录',
				mobile:''
			}
		},
		onLoad() {
			_this = this
			// #ifdef APP-PLUS
			this.appid = plus.runtime.appid
			this.appVersion = plus.runtime.version
			plus.runtime.getProperty(plus.runtime.appid, function(widgetInfo) {
				uniCloud.callFunction({
					name: 'check-version',
					data: {
						appid: plus.runtime.appid,
						appVersion: plus.runtime.version,
						wgtVersion: widgetInfo.version
					},
					success: (e) => {
						this.wgtVersion = widgetInfo.version
					},
					fail: (error) => {

					}
				})
			})
			// #endif
		},
		onShow() {
			console.log('onShow')
			// console.log(this)
			// console.log(this.lifeData)
			// console.log(this.userInfo)
			// console.log(uni.getStorageSync('lifeData'))
			if (uni.getStorageSync('lifeData').userInfo) {
				this.userInfo2 = uni.getStorageSync('lifeData').userInfo
			} else {
				this.userInfo2 = {}
			}
			if (uni.getStorageSync('lifeData').isLogin) {
				this.isLogin2 = uni.getStorageSync('lifeData').isLogin
				this.loginOrOut = '登出'
			} else {
				this.isLogin2 = false
				this.loginOrOut = '登录'
			}
			// console.log(this.userInfo2)
		},
		methods: {
			bindMobile() {
				uniCloud.callFunction({
					name: 'user-center',
					data: {
						action: 'bindMobile',
						params: {
							mobile: this.mobile
						}
					},
					success(res) {
						console.log('绑定成功')
						console.log(JSON.stringify(res.result))
						//{"code":60101,"errCode":"uni-id-account-already-bound","errMsg":"此手机号已绑定","msg":"此手机号已绑定","message":"此手机号已绑定"}
						//{"code":0,"msg":"","mobile":"18655654121","errCode":0,"errMsg":"","message":""}
						
						// uniCloud.logger.info(JSON.stringify(res.result))
						// uni.showModal({
						// 	showCancel: false,
						// 	content: JSON.stringify(res.result)
						// })
					},
					fail(e) {
						console.error(e)
						// uni.showModal({
						// 	showCancel: false,
						// 	content: '绑定失败，请稍后再试'
						// })
					}
				})
			},
			gotoCenter(){
				this.$u.route('pages/tabbar/center/center')
			},
			gotToLoginOrLogout() {
				if (this.isLogin2) {
					uni.setStorageSync('lifeData', {});
					uni.removeStorageSync('token')
					uni.removeStorageSync('uid')
					uni.removeStorageSync('uni_id_token')
					uni.removeStorageSync('uni_id_token_expired')
					
					uni.reLaunch({
						url: '/pages/tabbar/user/user',
					});
				} else {
					uni.navigateTo({
						url: '/pages/user/login/login'
					});
				}
			},
			makePhoneCall: function() {
				uni.makePhoneCall({
					phoneNumber: '17091648421',
					success: () => {
						console.log("成功拨打电话")
					}
				})
			},
			getLocation() {
				uni.getLocation({
					type: 'wgs84',
					success: function(res1) {
						console.log(res1)
						_this.location = res1
						// console.log('当前位置的经度：' + res1.longitude);
						// console.log('当前位置的纬度：' + res1.latitude);

					}
				});
				// this.$u.wx.getLocation(res => {
				// 	this.location = res
				// })
			},
			openLocation() {
				if (!this.location) return this.showToast('请先获取当前位置', 'warning')
				uni.chooseLocation({
					latitude: this.location.latitude,
					longitude: this.location.longitude,
					success: (res) => {
						console.log(res)
					}
				})
				// this.$u.wx.openLocation({
				// 	name: '我的位置',
				// 	address: '地址详细描述',
				// 	latitude: this.location.latitude,
				// 	longitude: this.location.longitude
				// })
			},
			chooseLocation: function() {
				uni.getLocation({
					type: 'wgs84',
					success: function(res1) {
						console.log(res1)
						// console.log('当前位置的经度：' + res1.longitude);
						// console.log('当前位置的纬度：' + res1.latitude);
						uni.chooseLocation({
							latitude: res1.latitude,
							longitude: res1.longitude,
							success: (res) => {
								console.log(res)
								_this.location2 = res
							}
						})
					}
				});

			},
			chooseImage() {
				this.$u.wx.chooseImage(res => {
					uni.showModal({
						content: JSON.stringify(res),
						showCancel: false
					})
				})
			},
			chooseAddress() {
				this.$u.wx.chooseAddress(res => {
					uni.showModal({
						content: JSON.stringify(res),
						showCancel: false
					})
				})
			},
			share() {
				if (!this.isLogin) return this.showToast('请先授权登录', 'warning')
				this.showToast('设置成功，请点击右上角转发给好友')
				this.$u.wx.share({
					title: this.userInfo.nickname + '邀请你参加XXX活动',
					desc: '缤纷好礼享不停，快来看看吧~',
					success: () => {
						uni.showModal({
							content: '分享成功,积分增加100',
							showCancel: false
						})
					}
				})
			},
			async wxpay() {
				if (!this.isLogin) return this.showToast('请先授权登录', 'warning')
				const res = await this.$u.api.createTestOrder()
				this.$u.wx.wxpay({
					data: {
						...res.data
					},
					success: () => {
						this.showToast('支付成功')
					},
					fail: () => {
						this.showToast('取消支付', 'warning')
					}
				})
			},
			async scan() {
				// #ifdef APP-PLUS
				let status = await this.checkPermission();
				if (status !== 1) {
					return;
				}
				// #endif
				uni.scanCode({
					success: (res) => {
						_this.scanRes = res.result
						// this.showToast(res.result)
					},
					fail: (err) => {
						// 需要注意的是小程序扫码不需要申请相机权限
					}
				});
			},
			// #ifdef APP-PLUS
			async checkPermission(code) {
				let status = permision.isIOS ? await permision.requestIOS('camera') :
					await permision.requestAndroid('android.permission.CAMERA');

				if (status === null || status === 1) {
					status = 1;
				} else {
					uni.showModal({
						content: "需要相机权限",
						confirmText: "设置",
						success: function(res) {
							if (res.confirm) {
								permision.gotoAppSetting();
							}
						}
					})
				}
				return status;
			},
			// #endif
			showToast(title, type = 'success') {
				this.$refs.vToast.show({
					title,
					type
				})
			},
			getVersion() {
				// #ifdef APP-PLUS
				appUpdate(); // 调用方法检查版本更新
				// #endif
			},
			goToWxMiniInApp() {
				// 如果是ios 需要先login 然后在 执行下面的代码 isIOS 这个方法根据你项目中的来定义 即可
				if (uni.getSystemInfoSync().platform == 'ios') {
					uni.login({
						provider: 'weixin',
						success: function(loginRes1) {
							plus.share.getServices(function(res) {
								var sweixin = null;
								for (var i = 0; i < res.length; i++) {
									var t = res[i];
									if (t.id == 'weixin') {
										sweixin = t;
									}
								}

								if (sweixin) {
									sweixin.launchMiniProgram({
										id: 'gh_82e48c06a20d',
										path: 'pages/main/index/index',
										type: 0
									});
								}
							}, function(res) {
								// console.log(res);
							});
						}
					});
				} else {
					plus.share.getServices(function(res) {
						var sweixin = null;
						for (var i = 0; i < res.length; i++) {
							var t = res[i];
							if (t.id == 'weixin') {
								sweixin = t;
							}
						}
						
						//可取值： 0-正式版； 1-测试版； 2-体验版。 默认值为0。
						if (sweixin) {
							sweixin.launchMiniProgram({
								id: 'gh_82e48c06a20d',
								path: 'pages/main/index/index',
								type: 0
							});
						}
					}, function(res) {
						// console.log(res);
					});
				}

				//小程序内互相跳转
				// uni.navigateToMiniProgram({
				//   appId: '',
				//   path: 'pages/index/index',
				//   extraData: {
				//     'data1': 'test'
				//   },
				//   success(res) {
				//     // 打开成功
				//   }
				// })

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
