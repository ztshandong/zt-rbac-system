<template>
	<view>
		<web-view :webview-styles="webviewStyles" :src="articleurl"></web-view>
	</view>
	<!-- 
	<view class="u-content">
		广告不是我加的
		<u-parse :html="articleurl"></u-parse>
	</view>
	 -->
</template>

<script>
	export default {
		data() {
			return {
				articleurl: '',
				webviewStyles: {
					progress: {
						color: '#FF7200'
					}
				}
			};
		},
		onLoad(options) {
			//https://v.youku.com/v_show/id_XMjc0NzcxODkyNA==.html?firsttime=0
			//<iframe height=250px width=350px src='https://player.youku.com/embed/XMjc0NzcxODkyNA==' frameborder=0 'allowfullscreen'></iframe>
			this.articleurl = `https://v.youku.com/v_show/id_XMjc0NzcxODkyNA==.html?firsttime=0`;
		}
	}
</script>

<style lang="scss" scoped>
	.u-content {
		padding: 24rpx;
		font-size: 32rpx;
		color: $u-content-color;
		line-height: 1.6;
	}
</style>
