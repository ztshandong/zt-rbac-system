<template>
	<view class="container">
		<u-form :model="item" ref="uForm">
			<u-form-item label="角色名称">
				<u-input disabled v-model="item.thisName" />
			</u-form-item>
			<u-form-item label="角色编号">
				<u-input disabled v-model="item.thisCode" />
			</u-form-item>
			<u-form-item label="角色类型">
				<u-input disabled v-model="item.roleType" />
			</u-form-item>
			<u-form-item label="是否启用">
				<u-switch slot="right" v-model="item.enableFlag" disabled></u-switch>
			</u-form-item>
		</u-form>
	</view>
</template>

<script>
	var _this;

	export default {
		data() {
			return {
				item: {}
			}
		},
		onLoad(data) {
			_this = this
			this.item = JSON.parse(decodeURIComponent(data.item));
			console.log(this.item)
		},
		methods: {

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
