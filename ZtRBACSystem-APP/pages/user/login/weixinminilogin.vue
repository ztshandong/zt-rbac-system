<template>
	<view>
		<button v-if="!hiddenLaunchButton" class="bottom" type="primary" open-type="launchApp" bindlaunchapp="launchAppSuccess"
			:appParameter="userInfoJson" :app-parameter="userInfoJson" @error="launchAppError">返回APP</button>
		<button v-if="hiddenLaunchButton" class="bottom" type="primary" @click="mpGetUserInfoWx">授权登录</button>
	</view>
</template>

<script>
	var _this;
	export default {
		data() {
			return {
				hiddenLaunchButton: true,
				userInfoJson: 'dddd'
			}
		},
		onLoad() {
			_this = this
			// this.mpGetUserInfoWx()
		},
		//https://developers.weixin.qq.com/miniprogram/dev/framework/app-service/scene.html
		onShow: function(options) {
			console.log("[onShow] 本次场景值:")
			console.log(uni.getLaunchOptionsSync())
		},
		methods: {
			launchAppSuccess(e) {
				console.log(e)
				uni.showModal({
					content: JSON.stringify(e),
				})
			},
			mpGetUserInfoWx() {
				//https://blog.csdn.net/weixin_44986043/article/details/118706363
				uni.getUserProfile({
					desc: 'Wexin', // 这个参数是必须的
					lang: 'zh_CN',
					success: res => {
						console.log("用户信息")
						console.log(res)
						_this.userInfoJson = JSON.stringify(res)
						_this.hiddenLaunchButton = false
					},
					fail: (err) => {
						uni.showModal({
							content: JSON.stringify(err),
						})
						console.log('mpGetUserInfoWx err' + JSON.stringify(err));
					},
					complete: () => {
						uni.hideLoading();
					}
				})
			},
			launchAppError(e) {
				uni.showModal({
					content: JSON.stringify(e),
				})
				console.log(e)
			}
		}
	}
</script>

<style>
</style>
