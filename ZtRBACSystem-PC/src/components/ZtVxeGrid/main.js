// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ViewUI from 'view-design';
import 'view-design/dist/styles/iview.css';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// 引用API文件
import api from './api/index.js'
import XEUtils from "xe-utils";
import VXETable from "vxe-table";
import VXEUtils from "vxe-utils";
import 'vxe-table/lib/style.css'
import store from './store'
import {Enums} from './api/enums.js'

Vue.config.productionTip = false
Vue.use(ViewUI);
Vue.use(ElementUI);
// 将API方法绑定到全局
Vue.prototype.$api = api
Vue.use(VXETable);
Vue.use(VXEUtils, XEUtils);
Vue.prototype.Enums = Enums;
Vue.prototype.$XPrint = VXETable.print
Vue.prototype.$XModal = VXETable.modal
Vue.prototype.$XSaveFile = VXETable.saveFile
Vue.prototype.$XReadFile = VXETable.readFile
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {
    App
  },
  template: '<App/>'
})
