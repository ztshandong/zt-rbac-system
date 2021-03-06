//https://cli.vuejs.org/zh/guide/mode-and-env.html#%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F
//只有 NODE_ENV，BASE_URL 和以 VUE_APP_ 开头的变量将通过 webpack.DefinePlugin 静态地嵌入到客户端侧的代码中

//环境判断
//https://uniapp.dcloud.io/frame?id=%e5%bc%80%e5%8f%91%e7%8e%af%e5%a2%83%e5%92%8c%e7%94%9f%e4%ba%a7%e7%8e%af%e5%a2%83

//uniapp框架介绍
//https://uniapp.dcloud.io/frame?id=npm%e6%94%af%e6%8c%81

//配置
//https://uniapp.dcloud.io/collocation/vue-config
module.exports = {
	chainWebpack: config => {
		config
			.plugin('define')
			.tap(args => {
				args[0]['process.env'].VUE_APP_TEST = '"test"';
				return args
			})
	},
	// devServer: {
	// 	host: '0.0.0.0',
	// 	port: 8081,
	// 	open: true,
	// 	proxy: {
	// 		[process.env.NODE_ENV]: {
	// 			target: `http://localhost:8088`,
	// 			changeOrigin: true,
	// 			pathRewrite: {
	// 				['^' + process.env.NODE_ENV]: ''
	// 			}
	// 		}
	// 	},
	// 	disableHostCheck: true
	// },
	// 配置路径别名
	configureWebpack: {
		devServer: {
			host: '0.0.0.0',
			port: 8081,
			open: true,
			proxy: {
				//detail: 'https://cli.vuejs.org/config/#devserver-proxy'
				// '/development': {
				// 	target: `http://localhost:8080`,
				// 	changeOrigin: true,
				// 	pathRewrite: {
				// 		'^/development': '/'
				// 	}
				// },
				['/' + process.env.NODE_ENV]: {
					target: `http://localhost:8080`,
					// target: `https://zt.zhangzhuorui.com:442/dev`,
					changeOrigin: true,
					pathRewrite: {
						['^/' + process.env.NODE_ENV]: '/'
					}
				}
			},
			// 调试时允许内网穿透，让外网的人访问到本地调试的H5页面
			disableHostCheck: true
		}
	},
	productionSourceMap: false,
}
