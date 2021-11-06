<script>
	export default {
		onLaunch: function() {
			// #ifdef H5
			// this.$u.wx.wxOauth()
			// #endif
			// #ifdef MP-WEIXIN
			this.checkUpdataWxapp()
			// #endif

			// #ifdef APP-PLUS
			uni.preLogin({
				provider: 'univerify',
				success: (res) => {
					console.log("preLogin success: ", res);
				},
				fail: (err) => {
					console.log("preLogin fail: ", err);
				}
			})

			//监听push推送通知
			plus.push.addEventListener('receive', ({
				type,
				title,
				content,
				payload
			}) => { //console.log(type,title,content,payload);
				console.log('plus.push.addEventListener receive');
				console.log(type);
				console.log(title);
				console.log(content);
				console.log(payload);
				if (type == 'receive' || uni.getSystemInfoSync().platform !=
					"ios") { //如果type!='receive'是自己本地插件的push消息栏，“拦截”避免死循环'，安卓系统没有这个问题
					if (typeof payload != 'object') {
						payload = JSON.parse(payload)
					} //判断是否为object，不是的话手动转一下。hbuilderx 3.0以上版本已经修复此问题可省略
					plus.push.createMessage(content, JSON.stringify(payload), {
						title: payload.title,
						subtitle: payload.content
					});
				}
			});
			//监听点击通知栏
			plus.push.addEventListener('click', function({
				payload
			}) {
				console.log('plus.push.addEventListener click');
				console.log(payload);
				if (typeof payload != 'object') {
					payload = JSON.parse(payload)
				}

				let pages = getCurrentPages();
				let currentWebview = pages[pages.length - 1].$getAppWebview();
				if (currentWebview.__uniapp_route != 'pages/tabbar/msg-center/msg-center') {
					uni.switchTab({
						url: '/pages/tabbar/msg-center/msg-center'
					})
				}
				uni.$emit('readMsg', payload)
			});

			// 监听在线消息事件
			// plus.push.addEventListener(
			// 	'receive',
			// 	(msg) => {
			// 		// 这是接收的内容
			// 		uni.showModal({
			// 			content: JSON.stringify(msg),
			// 		})
			// 		console.log('recevice:' + JSON.stringify(msg))
			// 		// 这里获取后端传来的参数
			// 		console.log(JSON.parse(msg.content))
			// 		// 这里可以写跳转逻辑代码
			// 	},
			// 	false
			// )

			/* 5+  push 消息推送 ps:使用:H5+的方式监听，实现推送*/
			// plus.push.addEventListener(
			// 	'click',
			// 	(msg) => {
			// 		uni.showModal({
			// 			content: JSON.stringify(msg),
			// 		})
			// 		console.log('click:' + JSON.stringify(msg))
			// 		/**
			// 		 *  推送的标题/名字和内容
			// 		 *  payload:{"title":"碎碎z","content":"聊天内容"}
			// 		 */
			// 		console.log(msg.payload)
			// 	},
			// 	false
			// )

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
