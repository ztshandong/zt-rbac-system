<template>
	<view>
		<unicloud-db ref="lists" v-slot:default="{data, loading, error, options,pagination}" 
			collection="msg" :getcount="true" orderby="_id desc"
		>
			<list :style="{'height':wHeight+'px'}">
				<cell v-for="(item,index) in data" :key="item._id">
					<j-msg ref="j-msg" :active="activeIndex===index" :item="item" @click.native="tapItem(item,index)" ></j-msg>
				</cell>
				<cell @appear="loadMore">
					<text class="loading" v-if="loading">加载中...</text>
				</cell>
			</list>
		</unicloud-db>
		<u-tabbar v-model="currentTab" :list="tabbar" :mid-button="true" @change="onTabItemTap2"></u-tabbar>
	</view>
</template>

<script>
	import {
		tabbars
	} from "@/common/common.js"
	import {
		jMsg
	} from "@/components/j-msg/j-msg"
	export default {
		async onReady() {
		// #ifdef APP-PLUS
			console.log( plus.push.getClientInfo().clientid );
			plus.push.addEventListener('receive',async e=>{
				this.refreshFn()
			})
		// #endif
			await this.upUnReadNum()
		},
		data() {
			return {
				title: '消息',
				currentTab: 4,
				tabbar: tabbars,
				unReadNum:0,
				wHeight:0,
				activeIndex:false,
			} 
		},
		async onNavigationBarButtonTap({index}) {
			console.log(index);
			uni.showModal({
				title: '确认清空所有未读消息吗？',
				cancelText: '取消',
				confirmText: '确认',
				success: async ({cancel}) => {
					if(cancel) return false
					const db = uniCloud.database();
					let msgDb = db.collection("msg");
					await msgDb.update({read: true});
					this.$refs.lists.dataList.forEach( (item,index)=>{
						this.$refs.lists.dataList[index].read = true
					})
					await this.upUnReadNum()
					this.setBadgeNumber(0)
				}
			});
		},
		onPullDownRefresh() {
			this.refreshFn(()=>{
				uni.stopPullDownRefresh()
			})
		},
		onLoad(e) {
			const domModule = uni.requireNativePlugin('dom')
			setTimeout(()=>{
				this.activeIndex = 1
			},1000)
			
			console.log(e);
			this.wHeight = uni.getSystemInfoSync().windowHeight;
			uni.$on('readMsg',e=>{
				console.log(e);
				console.log(this.$refs.lists.dataList);
				let {msgId} = e;
				this.$refs.lists.dataList.forEach(({_id},index)=>{
					if(_id==msgId){
						this.activeIndex = index
						//console.log(this.$refs['j-msg']);
						let el = this.$refs['j-msg'][index]
						domModule.scrollToElement(el, {})
					}
				})
			})
		},
		methods: {
			onTabItemTap2(e) {
				console.log(e)
			},
			loadMore() {
			  this.$refs.lists.loadMore()
			},
			async tapItem(item,index){
				uni.showToast({
					title: '点击了第'+index+'项',
					icon: 'none'
				});
				const db = uniCloud.database();
				let msgDb = db.collection("msg");
				let res = await msgDb.doc(item._id).update({"read": true});
				console.log(res.result.updated);
				this.$refs.lists.dataList[index].read = true;
				await this.upUnReadNum()
			},
			async refreshFn(callback){
				this.$refs.lists.loadData({
				  clear: true
				}, async() => {
					await this.upUnReadNum()
					callback()
				})
			},
			async upUnReadNum(){
				const db = uniCloud.database();
				let msgDb = db.collection("msg");
				let {result:{total:unReadNum}} = await msgDb.where({"read": false}).skip(0).limit(5).count();
				//console.log(unReadNum);
				this.unReadNum = unReadNum;				
				const AppWebview = this.$scope.$getAppWebview();
				this.setBadgeNumber(unReadNum)
				if(unReadNum===0){
					AppWebview.setTitleNViewButtonStyle(0, {  
					    text: ' ',  
					});
					uni.removeTabBarBadge({index:1})
					plus.push.clear();//清空系统消息栏
				}else{
					uni.setTabBarBadge({
						index:1,
						text:''+unReadNum,
						complete(e) {
							//console.log(e);
						}
					})
					AppWebview.setTitleNViewButtonStyle(0, {  
					    text: '清除未读',  
					});
				}
			},
			setBadgeNumber(n){
				//console.log('setBadgeNumber',n);
				//HX3.0起内置了本地设置角标不同步到云端的问题，如果你是hx3.0以下版本需要如下 GeTuiSdk.setBadge(n)同步到云端
				/* if (uni.getSystemInfoSync().platform == "ios") {
					var GeTuiSdk = plus.ios.importClass('GeTuiSdk');
					GeTuiSdk.setBadge(n);
					console.log('GeTuiSdk.setBadge(n)',n);
				} */
				if(uni.getSystemInfoSync().brand!='Xiaomi'){//屏蔽小米手机 小米本身会处理，如果调用会在系统消息中心显示一条通知， “您有x条未读消息"”，其中x未设置的角标数字值。
					plus.runtime.setBadgeNumber(n);
				}
			}
		}
	}
</script>

<style>
.loading{
	width: 750rpx;
	text-align: center;
	height: 66rpx;
	line-height: 66rpx;
	font-size: 36rpx;
}
</style>
