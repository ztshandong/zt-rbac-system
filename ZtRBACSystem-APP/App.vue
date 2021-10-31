<script>
	export default {
		onLaunch: function() {
			// #ifdef H5
			// this.$u.wx.wxOauth()
			// #endif
			// #ifdef MP-WEIXIN
			this.checkUpdataWxapp()
			// #endif
		},
		onShow: function() {},
		onHide: function() {},
		methods: {
			// #ifdef MP-WEIXIN
			checkUpdataWxapp() {
				const updateManager = uni.getUpdateManager()
				updateManager.onCheckForUpdate(function(res) {
					// 请求完新版本信息的回调
				})
				updateManager.onUpdateReady(function(res) {
					uni.showModal({
						title: '更新提示',
						content: '新版本已经准备好，重启应用以更新',
						success(res) {
							if (res.cancel) return
							// 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
							updateManager.applyUpdate()
						}
					})
				})
				updateManager.onUpdateFailed(function(res) {
					// 新的版本下载失败
				})
			}
			// #endif
		}
	}
</script>

<style lang="scss">
	@import 'uview-ui/index.scss';
	/*每个页面公共css */
	@import '@/common/common.style.scss';
	/*toast字体图标*/
	@import '@/components/iconfont.css';
	/*iconfont*/
	@import './static/iconfont/iconfont-weapp-icon.css';
	
	@import "common/demo.scss";
	
	.rowcontainer {
		display: flex;
		// flex-direction: column;
		align-items: center;
		justify-content: center;
	
		flex-direction: row;
		// justify-content:space-between;
		flex-wrap: wrap;
	}
	
	.ali {
		width: 100rpx;
		height: 100rpx;
	}
</style>
