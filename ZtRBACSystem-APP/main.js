import Vue from 'vue'
import App from './App'
import store from '@/store';
import uView from "uview-ui";
// http拦截器
import httpInterceptor from '@/common/http.interceptor.js'
// http接口API集中管理
import httpApi from '@/common/http.api.js'
// 公共函数
import globalFunc from '@/utils/common.js'
import {deepClone, getUUID} from "@/common/util.js";
// 微信SDK
// #ifdef H5
import weixin from '@/utils/jwx.js'

Vue.use(uView);
Vue.config.productionTip = false
// Vue.config.ignoredElements.push("wx-open-launch-weapp");

// vuex
const vuexStore = require("@/store/$u.mixin.js");
Vue.mixin(vuexStore);

// 引入uView对小程序分享的mixin封装
let mpShare = require('uview-ui/libs/mixin/mpShare.js');
Vue.mixin(mpShare)

App.mpType = 'app'
const app = new Vue({
    store,
    ...App
})

// import '//172.16.1.217/iconfont/iconfont-weapp-icon.css';
Vue.use(httpInterceptor, app)

Vue.use(httpApi, app)

Vue.use(globalFunc, app)

Vue.prototype.getUUID = getUUID
Vue.prototype.deepClone = deepClone

Vue.use(weixin, app)
// #endif

app.$mount()

