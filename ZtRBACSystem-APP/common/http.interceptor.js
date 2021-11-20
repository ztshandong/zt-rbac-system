import {
    codeName,
    contentType,
    devUrl,
    invalidCode,
    loadingText,
    loadingTime,
    prodUrl,
    showLoading,
} from '@/common/settings'


// 这里的vm，就是我们在vue文件里面的this，所以我们能在这里获取vuex的变量，比如存放在里面的token变量
const install = (Vue, vm) => {
    // console.log(process.env)
    /*
    BASE_URL: "/"
    NODE_ENV: "development"
    VUE_APP_INDEX_CSS_HASH: "5e7e3b56"
    VUE_APP_NAME: "张涛的app"
    VUE_APP_PLATFORM: "h5"
    VUE_APP_TEST: "test"
    */
    let realDevUrl = devUrl;
    if (process.env.VUE_APP_PLATFORM != 'h5') {
        // realDevUrl = 'http://192.168.1.2:8080'
        realDevUrl = 'https://zt.zhangzhuorui.com:442/dev'
    }
    // 此为自定义配置参数，具体参数见上方说明
    //this.$u.http.config.baseUrl
    Vue.prototype.$u.http.setConfig({
        baseUrl: process.env.NODE_ENV === 'development' ? realDevUrl : prodUrl, // 请求的本域名
        method: 'POST',
        // 设置为json，返回后会对数据进行一次JSON.parse()
        dataType: 'json',
        showLoading, // 是否显示请求中的loading
        loadingText, // 请求loading中的文字提示
        loadingTime, // 在此时间内，请求还没回来的话，就显示加载中动画，单位ms
        originalData: false, // 是否在拦截器中返回服务端的原始数据
        loadingMask: true, // 展示loading的时候，是否给一个透明的蒙层，防止触摸穿透
        // 配置请求头信息
        header: {
            'content-type': contentType
        },
    });

    // 请求拦截，配置Token等参数
    Vue.prototype.$u.http.interceptor.request = (config) => {
        console.log('config')
        console.log(config)
        // 引用token
        // 由于使用了vuex统一管理，此处可以用vm.直接取vuex中的值
        // config.header[headerTokenName] = vm.userInfo?. [userTokenName] || '';
        const token = uni.getStorageSync('token');
        config.header.token = token;

        const clientInfo = uni.getStorageSync('clientInfo')
        config.data.clientInfo = clientInfo

        const uniIdToken = uni.getStorageSync('uni_id_token')
        if (uniIdToken) {
            config.data.uniIdToken = clientInfo
        }

        // 可以对某个url进行特别处理，此url参数为this.$u.get(url)中的url值
        // if(config.url == '/user/login') config.header.noToken = true;
        // 最后需要将config进行return
        return config;
        // 如果return一个false值，则会取消本次请求
        // if(config.url == '/user/rest') return false; // 取消某次请求
    }

    // 响应拦截，判断状态码是否通过
    Vue.prototype.$u.http.interceptor.response = (res) => {
        console.log('res')
        console.log(res)
        if (res.result[codeName] == 0 || res.result[codeName] == '0') {
            // res为服务端返回值，可能有code，result等字段
            // 这里对res进行返回，将会在this.$u.post(url).then(res => {})的then回调中的res的到
            // 如果配置了originalData为true，请留意这里的返回值
            return res.result
        } else if (res.result[codeName] == invalidCode) {
            // token失效，这里跳转登录
            vm.$u.func.showToast({
                title: '验证失败，请重新登录',
                success: () => {
                    uni.setStorageSync('lifeData', {});
                    uni.removeStorageSync('token')
                    // 此为uView的方法，详见路由相关文档
                    vm.$u.route('/pages/user/login/login')
                }
            })
            return false
        } else {
            // 如果返回false，则会调用Promise的reject回调，
            // 并将进入this.$u.post(url).then().catch(res=>{})的catch回调中，res为服务端的返回值
            vm.$u.toast(res.msg)
            return false;
        }
    }
}

export default {
    install
}
