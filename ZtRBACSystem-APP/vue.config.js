//https://cli.vuejs.org/zh/guide/mode-and-env.html#%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F
//只有 NODE_ENV，BASE_URL 和以 VUE_APP_ 开头的变量将通过 webpack.DefinePlugin 静态地嵌入到客户端侧的代码中
module.exports = {
	chainWebpack: config => {
		config
			.plugin('define')
			.tap(args => {
				args[0]['process.env'].VUE_APP_TEST = '"test"';
				return args
			})
	},
	devServer: {
		host: '0.0.0.0',
		port: 8081,
		open: true,
		// proxy: {
		//     detail: https://cli.vuejs.org/config/#devserver-proxy
		//     'dev': {
		//         target: `https://zt.zhangzhuorui.com:442`,
		//         changeOrigin: true,
		//         pathRewrite: {
		//             ['^' + process.env.VUE_APP_BASE_API]: ''
		//         }
		//     }
		// },
		disableHostCheck: true
	},
	// 配置路径别名
	configureWebpack: {
		devServer: {
			// 调试时允许内网穿透，让外网的人访问到本地调试的H5页面
			disableHostCheck: true
		}
	},
	productionSourceMap: false,
}
