<template>
	<view class="content">
		<!-- <u-loading :show="btnLoading"></u-loading> -->
		<view class="header">
			<image src="/static/shilu-login/logo.png"></image>
		</view>
		<view class="list">
			<view class="list-call">
				<!-- account -->
				<!-- <image class="img" src="/static/shilu-login/1.png"></image> -->
				<u-icon size="50" name="account" color="rgb(255, 165, 85)"></u-icon>
				<u-input class="sl-input" v-model="userName" type="text" maxlength="32" placeholder="输入账号" />
			</view>
			<view class="list-call">
				<!-- lock -->
				<!-- <image class="img" src="/static/shilu-login/2.png"></image> -->
				<u-icon size="50" name="lock" color="rgb(255, 165, 85)"></u-icon>
				<u-input class="sl-input" v-model="userPwd" type="password" maxlength="32" placeholder="输入密码"
					:password-icon="true" />
				<!-- <input class="sl-input" v-model="userPwd" type="text" maxlength="32" placeholder="输入密码" password="true" /> -->
			</view>

			<view class="list-call img" v-if="srcs!=''">
				<img :src="srcs" @click="refreshCode()" />
			</view>

			<view class="list-call" v-if="srcs!=''">
				<u-icon size="50" name="code" color="rgb(255, 165, 85)"></u-icon>
				<u-input class="sl-input" v-model="code" type="text" maxlength="32" placeholder="输入验证码" />
			</view>
		</view>

		<!-- <view class="button-login" hover-class="button-hover" @tap="accountLogin()"> -->
		<view class="button-login" hover-class="button-hover" @click="accountLogin()">
			<text>账号登录</text>
		</view>

		<view class="agreenment">
			<navigator url="forget" open-type="navigate">忘记密码</navigator>
			<text>|</text>
			<navigator url="reg" open-type="navigate">注册账户</navigator>
		</view>


		<view class="buttom">
			<view class="loginType">
				<view class="wechat item">

					<!-- #ifdef APP-PLUS -->
					<u-button type="primary" size="mini" @click="openWXMini4Login" :custom-style="customStyle"
						shape="circle" class="t-zhangtao t-zhangtao-weixin"></u-button>
					微信小程序登录

					<view class="icon">
						<u-icon size="70" name="phone" color="rgb(83,194,64)" @click="oneKeyLogin()">
						</u-icon>
					</view>
					手机号一键登录

					<!-- <u-button type="primary" size="mini" @click="oneKeyLogin" :custom-style="customStyle" shape="circle" class="t-zhangtao t-zhangtao-weixin"></u-button> -->

					<!-- <button type="primary" @click="openWXMini4Login" withCredentials="true">微信小程序登录</button> -->
					<!-- #endif -->

					<!-- #ifdef MP-BAIDU || MP-QQ || MP-ALIPAY || MP-TOUTIAO -->
					<!-- <button type="primary" open-type="getUserInfo" @getuserinfo="mpGetUserInfo">用户登录</button> -->
					<!-- <button type="primary" @click="mpGetUserInfo">获取用户信息</button> -->
					<!-- #endif -->

					<!-- #ifdef MP-WEIXIN-->
					<!-- <button type="primary" @click="mpGetUserInfoWx">微信登录</button> -->
					<!-- <button type="primary" @click="gotoWXMiniLogin">微信小程序登录页</button> -->
					<!-- <u-button type="primary" plain @click="mpGetUserInfoWx" :custom-style="customStyle"
						shape="circle" class="t-zhangtao t-zhangtao-weixin ali"></u-button> -->
					<view class="icon">
						<u-icon size="70" name="weixin-fill" color="rgb(83,194,64)" @click.once="mpGetUserInfoWx()">
						</u-icon>
					</view>
					微信登录
					<!-- #endif -->

					<view class="icon">
						<u-icon size="70" name="phone" color="rgb(83,194,64)" @click="uniLogin()">
						</u-icon>
					</view>
					uni云登录

					<!-- 
					<view class="icon">
						<u-icon size="70" name="weixin-fill" color="rgb(83,194,64)" @click.once="loginWX()"></u-icon>
					</view>
					 -->
				</view>
				<!-- 
				<view class="QQ item">
					<view class="icon">
						<u-icon size="70" name="qq-fill" color="rgb(17,183,233)" @click="loginQQ()"></u-icon>
					</view>
					QQ
				</view>
 -->
			</view>
		</view>

	</view>
</template>

<script>
	import CryptoJS from '@/common/crypto-js/crypto-js.js'
	var _this;
	export default {
		data() {
			return {
				userName: '',
				userPwd: '',
				code: null,
				srcs: '',
				customStyle: {
					// marginTop: '20rpx', // 注意驼峰命名，并且值必须用引号包括，因为这是对象
					// color: 'red',
					// maxWidth: '500rpx',
					// minWidth: '400rpx',
					backgroundColor: '#ffffff',
					width: '100rpx',
					height: '100rpx',
				}
			};
		},
		onShow() {
			// console.log(this)
			this.getArguments()
			// console.log(this.getUUID(16, 16))
			// console.log(this.sha1Hash('123456', 3))
		},
		onLoad() {
			_this = this
		},
		methods: {
			sha1Hash(passwd, times) {
				var p = passwd.toString()
				for (var i = 0; i < times; i++) {
					p = CryptoJS.SHA1(p).toString();
					// console.log(p.toString())
					//java: DigestUtils.sha1Hex(s.getBytes("UTF-8"));
				}
				return p.toString();
			},
			accountLogin() {
				console.log('accountLogin')
				// return
				uni.showLoading({
					title: '正在登录'
				});
				// let user = {
				// 	userName: this.userName,
				// 	userPwd: this.userPwd
				// }
				let user = {
					action: 'login',
					params: {
						scene: "login",
						captcha: this.code,
						username: this.userName,
						password: this.userPwd
					}
				}
				this.$u.post('ZtIndex/uniLogin', user)
					.then(res => {
						if (res.data && res.data.uid) {
							console.log('uniLogin success')
							uni.setStorageSync('uid', res.data.uid)
							uni.setStorageSync('uni_id_token', res.data.token)
							uni.setStorageSync('uni_id_token_expired', res.data.tokenExpired)

							// uni.setStorageSync('apiToken', e.result.apiRes.data.data)
							// uni.setStorageSync('apiUserInfo', e.result.apiRes.data.user)

							_this.$u.vuex('userInfo', res.data.userInfo)
							_this.$u.vuex('isLogin', true)

							console.log(uni.getStorageSync('lifeData'))
							console.log(_this.userInfo)
							console.log(_this.token)
							console.log(_this.isLogin)

							uni.showModal({
								showCancel: false,
								content: res.data.username + '登录成功'
							})
							uni.navigateBack({})
						} else {
							if (res.data.needCaptcha) {
								uniCloud.callFunction({
									name: 'user-center',
									data: {
										action: 'createCaptcha',
										params: {
											scene: "login"
										}
									},
									success(res) {
										_this.srcs = res.result.captchaBase64
									}
								})
							}
							uni.showModal({
								showCancel: false,
								content: '登录失败，请稍后再试'
							})
						}

						// if (res.data.msg != 'fail') {
						// 	this.$u.vuex('token', res.data)
						// 	uni.setStorageSync('token', res.data)
						// 	// this.isLogin = true;
						// 	// this.hasUserInfo = true;
						// 	this.$u.vuex('userInfo', res.user.user)
						// 	// console.log(this.$u.userInfo)
						// 	this.$u.vuex('isLogin', true)
						// 	// this.userInfo = result.userInfo;
						// 	uni.navigateBack({})
						// } else {
						// 	console.log('login fail' + JSON.stringify(res));
						// }
					})
					.catch(err => {
						console.log('login err')
						console.log(err)
						// console.log(JSON.stringify(err))
					})
					.finally(t => {
						uni.hideLoading();
					})
			},
			uniLogin() {
				uniCloud.callFunction({
					name: 'user-center',
					data: {
						action: 'login',
						// apiUrl: this.$u.http.config.baseUrl + '/ZtIndex/login',
						params: {
							scene: "login",
							captcha: this.code,
							username: this.userName,
							password: this.userPwd
						}
					},
					success(e) {
						console.log(JSON.stringify(e))
						//{"success":true,"header":{"transfer-encoding":"chunked","x-fc-code-checksum":"14931192961446868056","access-control-expose-headers":"Date,x-fc-request-id,x-fc-error-type,x-fc-code-checksum,x-fc-invocation-duration,x-fc-max-memory-usage,x-fc-log-result,x-fc-invocation-code-version","x-fc-invocation-service-version":"LATEST","content-disposition":"attachment","x-serverless-request-id":"ac1423131637291564611150710","x-serverless-runtime-version":"1.2.1","date":"Fri, 19 Nov 2021 03:12:44 GMT","x-fc-request-id":"9ebd47cf-646d-4bc8-93b6-f6bc4ee762ba","x-fc-invocation-duration":"104","x-fc-max-memory-usage":"28.41","content-type":"application/json"},"result":{"code":0,"msg":"","token":"jwttoken","uid":"xxx","username":"ztshandong","type":"login","userInfo":{"_id":"xxx","username":"ztshandong","password":"ooo","role":["admin"],"register_date":1636175395497,"register_ip":"xx","token":["jwttoken"],"last_login_date":1637291564686,"last_login_ip":"xx.xx.xx.xx","login_ip_limit":[],"mobile":"13812345678","mobile_confirmed":1},"tokenExpired":1637298764686,"errCode":0,"errMsg":"","message":"","needCaptcha":false},"requestId":"ac1423131637291564611150710"}
						//jwt信息：Header{alg=HS256,typ=JWT},Claim{uid=xxxx,role=[],permission=[],iat=xx,exp=xx}
						// e.result.needCaptcha
						if (e.result.uid) {
							console.log('uniLogin success')
							uni.setStorageSync('uid', e.result.uid)
							uni.setStorageSync('uni_id_token', e.result.token)
							uni.setStorageSync('uni_id_token_expired', e.result.tokenExpired)

							// uni.setStorageSync('apiToken', e.result.apiRes.data.data)
							// uni.setStorageSync('apiUserInfo', e.result.apiRes.data.user)

							if (e.result.apiRes) {
								_this.$u.vuex('token', e.result.apiRes.data.data)
								uni.setStorageSync('token', e.result.apiRes.data.data)
								_this.$u.vuex('userInfo', e.result.apiRes.data.user)
							}
							_this.$u.vuex('isLogin', true)

							console.log(uni.getStorageSync('lifeData'))
							console.log(_this.userInfo)
							// console.log(_this.token)
							console.log(_this.isLogin)

							// { "user": {},"roles": [],"permissions": [] }

							uni.showModal({
								showCancel: false,
								content: e.result.username + '登录成功'
							})
							// uni.navigateBack({})
						} else {
							if (e.result.needCaptcha) {
								uniCloud.callFunction({
									name: 'user-center',
									data: {
										action: 'createCaptcha',
										params: {
											scene: "login"
										}
									},
									success(res) {
										_this.srcs = res.result.captchaBase64
									}
								})
							}
							uni.showModal({
								showCancel: false,
								content: '登录失败，请稍后再试'
							})
						}
					},
					fail(e) {
						console.error(e)
						uni.showModal({
							showCancel: false,
							content: '登录失败，请稍后再试'
						})
					}
				})
			},
			refreshCode() {
				uniCloud.callFunction({
					name: 'user-center',
					data: {
						action: 'refreshCaptcha',
						params: {
							scene: "login"
						}
					},
					success(res) {
						_this.srcs = res.result.captchaBase64
					}
				})
			},
			oneKeyLogin() {
				uni.login({
					provider: 'univerify',
					success: (res) => {
						console.log('准备登录:', JSON.stringify(res));
						//一键登录res
						//{"authResult":{"openid":"aaa","access_token":"bbb=="},"errMsg":"login:ok"}

						// uniCloud.callFunction({
						// 	name: 'user-center',
						// 	data: {
						// 		action: 'loginByUniverify',
						// 		params: res.authResult
						// 	},
						// 	success(res2) {
						// 		console.log('cloud一键登录成功:', JSON.stringify(res2.result));
						// 		//{"code":0,"msg":"","token":"jwttoken","uid":"aaa","type":"login","userInfo":{"_id":"aaa","mobile":"13812345678","mobile_confirmed":1,"dcloud_appid":["appid"],"register_date":1636522325845,"register_ip":"xx.xx.xx.xx","token":["jwttoken],"last_login_date":1636523727344,"last_login_ip":"xx.xx.xx.xx"},"tokenExpired":1639115727344,"mobile":"13812345678","errCode":0,"errMsg":"","message":""}
						// 		uni.hideLoading()
						// 		uni.closeAuthView();
						// 	},
						// 	fail(e) {
						// 		console.error(e)
						// 	}
						// })
						// return

						this.$u.post('ZtIndex/getPhoneNumberByAccessToken', res.authResult)
							.then(res => {
								console.log('一键登录成功:' + JSON.stringify(res));
								//{"data":{"code":"200","data":"13812345678","success":true,"message":"操作成功"},"statusCode":200,"header":{"Content-Type":"application/json","Date":"Sat, 06 Nov 2021 03:26:08 GMT","Transfer-Encoding":"chunked","Connection":"keep-alive","Keep-Alive":"timeout=60"},"errMsg":"request:ok","cookies":[]}
								uni.showModal({
									title: '结果',
									content: '手机号为:' + res.data,
									success: function(res) {
										if (res.confirm) {
											uni.hideLoading()
											uni.closeAuthView();
										} else if (res.cancel) {
											uni.hideLoading()
											uni.closeAuthView();
										}
									}
								});
							})
							.catch(err => {
								console.log('login err')
								console.log(err)
								// console.log(JSON.stringify(err))
							})
							.finally(t => {
								// uni.hideLoading();
							})

						// uni.request({
						// 	url: 'http://192.168.1.7:8080/ZtIndex/getPhoneNumberByAccessToken',
						// 	method: 'POST',
						// 	data: res.authResult,
						// 	success(res) {
						// 		console.log('一键登录成功:' + JSON.stringify(res));

						// 		uni.showModal({
						// 			title: '结果',
						// 			content: '手机号为:' + res.data.data,
						// 			success: function(res) {
						// 				if (res.confirm) {
						// 					uni.hideLoading()
						// 					uni.closeAuthView();
						// 				} else if (res.cancel) {
						// 					uni.hideLoading()
						// 					uni.closeAuthView();
						// 				}
						// 			}
						// 		});
						// 	},
						// 	fail(err) {
						// 		console.log('获取登录信息失败：', err);
						// 	},
						// 	complete: () => {
						// 		// uni.hideLoading()
						// 		// uni.closeAuthView();
						// 	}
						// })
					}
				});
			},
			gotoWXMiniLogin() {
				this.$u.route('pages/user/login/weixinminilogin')
			},
			getArguments: function() {
				// 处理第三方传入的参数
				// #ifdef APP-PLUS
				var param = plus.runtime.arguments;
				console.log('getArguments---')
				// uni.showModal({
				// 	content: JSON.stringify(param),
				// })
				if (param != '') {
					try {
						console.log("param")
						console.log(param);
						// console.log("2");
						var userInfo = JSON.parse(param); //获取小程序传输到app的数据方法

						uni.showModal({
							content: JSON.stringify(userInfo),
						})

						plus.runtime.arguments = "";
						console.log("清除plus.runtime.arguments数据成功")

					} catch (e) {
						// 若传入的参数不是JSON格式字符，需处理异常情况
						console.error(e);
						console.log('若传入的参数不是JSON格式字符，需处理异常情况。');
					}
				}
				// #endif
			},
			openWXMini4Login() {
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
										path: 'pages/user/login/weixinminilogin',
										type: 1
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
								path: 'pages/user/login/weixinminilogin',
								type: 1
							});
						}
					}, function(res) {
						// console.log(res);
					});
				}
			},
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
			mpGetUserInfo(result) {
				console.log('mpGetUserInfo:', JSON.stringify(result));
				if (result.detail.errMsg !== 'getUserInfo:ok') {
					uni.showModal({
						title: '获取用户信息失败',
						content: '错误原因' + result.detail.errMsg,
						showCancel: false
					});
					return;
				}
				this.hasUserInfo = true;
				this.userInfo = result.detail.userInfo;

				// uni.getUserInfo({
				// 	provider: 'weixin',
				// 	success: (result) => {
				// 		console.log('getUserInfo:' + JSON.stringify(result))
				// 		//{"errMsg":"getUserInfo:ok","userInfo":{"openId":"oRrdQU0Vj-0","nickName":"张涛","gender":0,"city":"","province":"","country":"","avatarUrl":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIwOQibbpkkOM73M0buZYvgqrkZgyQZpYsLt8oSkqanh6Nm3smic5iaHgsictSBQSQt9j6HzRwZhRtPkg/132","unionId":"oU5Yyt_qaKAc"}}
				// 		this.isLogin = true;
				// 		this.hasUserInfo = true;
				// 		this.userInfo = result.userInfo;
				// 		uni.navigateBack({})
				// 	},
				// 	fail: (err) => {
				// 		console.log('loginWXApp err' + JSON.stringify(err));
				// 	},
				// 	complete: () => {
				// 		uni.hideLoading();
				// 	}
				// });
			},
			mpGetUserInfoWx() {
				//https://blog.csdn.net/weixin_44986043/article/details/118706363
				uni.getUserProfile({
					desc: 'Wexin', // 这个参数是必须的
					lang: 'zh_CN',
					success: res => {
						console.log("用户信息", res)
						//{"cloudID":"50_ok3RNhIU","encryptedData":"vSVfzSkPIMaV5ovWFVA7Kw==","iv":"CPpfglun7S4r/40HjIo9vg==","signature":"2cd55a5e5c6cec9663df79e64","userInfo":{"nickName":"张涛","gender":0,"language":"zh_CN","city":"","province":"","country":"","avatarUrl":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKWIMFQhvLVe1rW7hAWQpgicvayDBIKl6Qm1oG8U1cwzk8E049zX7gRAkAWAG5oQNsGTV7vOSAf3NA/132"},"rawData":"{\"nickName\":\"张涛\",\"gender\":0,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"\",\"avatarUrl\":\"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKWIMFQhvLVe1rW7hAWQpgicvayDBIKl6Qm1oG8U1cwzk8E049zX7gRAkAWAG5oQNsGTV7vOSAf3NA/132\"}","errMsg":"getUserProfile:ok"}
						// this.isLogin = true;
						// this.hasUserInfo = true;
						// this.userInfo = res.userInfo;
						this.$u.vuex('userInfo', res.userInfo)
						// console.log(this.$u.userInfo)
						this.$u.vuex('isLogin', true)
						uni.login({
							provider: 'weixin',
							success: (res1) => {
								console.log('res1')
								//{errMsg: "login:ok", code: "021rewFa1F5R0C0WG6Ja1U1Gez0rewFk"}
								// console.log(res1)
								// return
								let data = {
									code: res1.code
								}
								this.$u.get('ZtIndex/getOpenIdInXiaoChengXu', data)
									.then(res2 => {
										console.log('getOpenIdInXiaoChengXu:');
										console.log(res2);
									})
									.catch(err => {
										console.log('login err' + err)
										// console.log(JSON.stringify(err))
									})
									.finally(t => {
										uni.hideLoading();
									})

								// uni.request({
								// 	url: 'http://172.16.1.217:8080/ZtIndex/getOpenIdInXiaoChengXu',
								// 	method: 'GET',
								// 	data: {
								// 		code: res1.code
								// 	},
								// 	success(res2) {
								// 		console.log('getOpenIdInXiaoChengXu:' + res2);
								// 		//{"data":{"unionid":"oumM8wq8sc7LAAU","openid":"ol3XN4uW77Tna3LA","session_key":"8EWyic7NjV3Q=="},"profile":{"transportRttEstimate":33,"domainLookUpStart":1635039991975,"responseEnd":1635039993523,"sendBytesCount":581,"redirectStart":0,"connectEnd":1635039992077,"protocol":"unknown","peerIP":"192.168.1.6","requestStart":1635039991974,"socketReused":false,"fetchStart":1635039991973,"SSLconnectionStart":1635039991978,"estimate_nettype":5,"httpRttEstimate":115,"throughputKbps":0,"SSLconnectionEnd":1635039991978,"downstreamThroughputKbpsEstimate":1961,"domainLookUpEnd":1635039991975,"responseStart":1635039993520,"redirectEnd":0,"rtt":33,"connectStart":1635039991975,"port":8080,"receivedBytedCount":296,"requestEnd":1635039993523},"header":{"Transfer-Encoding":"chunked","Keep-Alive":"timeout=60","Connection":"keep-alive","Date":"Sun, 24 Oct 2021 01:46:36 GMT","Content-Type":"application/json"},"statusCode":200,"cookies":[],"errMsg":"request:ok"}

								// 		// this.$refs.xiaoChengXuGetPhoneNumber.$el.click();
								// 		// if (res.data.code !== 0) {
								// 		// 	console.log('获取openid失败：', res.result.msg);
								// 		// 	return
								// 		// }
								// 		// uni.setStorageSync('openid', res.data.openid)
								// 	},
								// 	fail(err) {
								// 		console.log('获取openid失败：', err);
								// 	}
								// })
							}
						})
						uni.navigateBack({})
					},
					fail: (err) => {
						console.log('mpGetUserInfoWx err' + JSON.stringify(err));
					},
					complete: () => {
						uni.hideLoading();
					}
				})
			},
			loginWXApp() {
				uni.showToast({
					title: '暂时不支持',
					duration: 500
				});
				return
				uni.login({
					provider: 'weixin',
					// #ifdef MP-ALIPAY
					scopes: 'auth_user', //支付宝小程序需设置授权类型
					// #endif
					success: (res) => {
						console.log('loginWXApp:', JSON.stringify(res));
						//loginWXApp:, {"authResult":{"access_token":"50__InbYuEPwO1HUlzq_ZGSN_OACFwA","expires_in":7200,"refresh_token":"50_EBKrij1t_axNXUNmZCM_OWOQi-nCc4TuKDs","openid":"oRrdQt5dBvU0Vj-0","scope":"snsapi_userinfo","unionid":"oU5Yyt_gyHqaKAc"},"errMsg":"login:ok"} 
						uni.getUserProfile({
							desc: 'Wexin', // 这个参数是必须的
							success: res => {
								console.log("用户信息", res)
								//{"errMsg":"getUserProfile:ok","userInfo":{"openId":"oRrdQt5dHU0Vj-0","nickName":"张涛","gender":0,"city":"","province":"","country":"","avatarUrl":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIwOQibbpkkOM73M0buZYvgqrkZgyQZpYsLt8oSkqanh6Nm3smic5iaHgsictSBQSQt9j6HzRwZhRtPkg/132","unionId":"oU5Yyt_VNBHqaKAc"}}
								// this.isLogin = true;
								// this.hasUserInfo = true;
								// this.userInfo = res.userInfo;
								this.$u.vuex('userInfo', res.userInfo)
								// console.log(this.$u.userInfo)
								this.$u.vuex('isLogin', true)
								uni.navigateBack({})
							},
							fail: (err) => {
								console.log('mpGetUserInfoWx err' + JSON.stringify(err));
							},
							complete: () => {
								uni.hideLoading();
							}
						})
					},
					fail: (err) => {
						console.log('login fail:', err);
					},
					complete: () => {
						uni.hideLoading();
					}
				});
			},

		}
	}
</script>

<style lang="scss" scoped>
	page {
		background: url('https://vkceyugu.cdn.bspapp.com/VKCEYUGU-weitao/50c93d50-a594-11ea-b997-9918a5dda011.png') no-repeat center / 100% 100%;
	}

	.content {
		display: flex;
		flex-direction: column;
		justify-content: center;
	}

	.header {
		width: 161rpx;
		height: 161rpx;
		background: rgba(63, 205, 235, 1);
		box-shadow: 0rpx 12rpx 13rpx 0rpx rgba(63, 205, 235, 0.47);
		border-radius: 50%;
		margin-top: 90rpx;
		margin-left: auto;
		margin-right: auto;
	}

	.header image {
		width: 161rpx;
		height: 161rpx;
		border-radius: 50%;
	}

	.list {
		display: flex;
		flex-direction: column;
		padding-top: 50rpx;
		padding-left: 70rpx;
		padding-right: 70rpx;

		// flex-direction: row;
		// justify-content:space-between;
		// flex-wrap: wrap;
	}

	.list-call {
		// min-width: 500px; 
		// width: 50%;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		height: 100rpx;
		color: #333333;
		border-bottom: 0.5px solid #e2e2e2;
	}

	.list-call .img {
		width: 40rpx;
		height: 40rpx;
	}

	.list-call .sl-input {
		flex: 1;
		text-align: left;
		font-size: 48rpx;
		margin-left: 16rpx;
	}

	.button-login {
		color: #FFFFFF;
		font-size: 34rpx;
		width: 470rpx;
		height: 100rpx;
		background: linear-gradient(-90deg, rgba(63, 205, 235, 1), rgba(188, 226, 158, 1));
		box-shadow: 0rpx 0rpx 13rpx 0rpx rgba(164, 217, 228, 0.2);
		border-radius: 50rpx;
		line-height: 100rpx;
		text-align: center;
		margin-left: auto;
		margin-right: auto;
		margin-top: 100rpx;
	}

	.button-hover {
		background: linear-gradient(-90deg, rgba(63, 205, 235, 0.8), rgba(188, 226, 158, 0.8));
		// background: linear-gradient(-90deg, rgba(188, 226, 158, 0.8), rgba(63, 205, 235, 0.8));
	}

	.agreenment {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		font-size: 30rpx;
		margin-top: 80rpx;
		color: #FFA800;
		text-align: center;
		height: 40rpx;
		line-height: 40rpx;
	}

	.agreenment text {
		font-size: 24rpx;
		margin-left: 15rpx;
		margin-right: 15rpx;
	}


	.buttom {
		.loginType {
			display: flex;
			padding: 150rpx 150rpx 150rpx 100rpx;
			// justify-content: space-between;

			// flex-direction: row;
			// align-items: center;
			justify-content: space-around;
			// flex-wrap: wrap;
			// position: absolute;
			// top: 0;
			// left: 0;
			// width: 100%;

			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				color: $u-content-color;
				font-size: 28rpx;
			}
		}

		.hint {
			padding: 20rpx 40rpx;
			font-size: 20rpx;
			color: $u-tips-color;

			.link {
				color: $u-type-warning;
			}
		}
	}
</style>
