import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/zhuorui.scss' // zhuorui css
import App from './App'
import store from './store'
import router from './router'
import permission from './directive/permission'
import {download} from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control
import {getDicts} from "@/api/system/dict/data";
import {getConfigKey} from "@/api/system/config";
import {
  addDateRange,
  deepClone,
  getUserInfo,
  handleTree,
  parseTime,
  resetForm,
  selectDictLabel,
  selectDictLabels
} from "@/utils/zhuorui";
import Pagination from "@/components/Pagination";
// 自定义表格工具扩展
import RightToolbar from "@/components/RightToolbar"
// 引用API文件
import api from './api/index.js'
import XEUtils from "xe-utils";
import VXETable from "vxe-table";
import VXEUtils from "vxe-utils";
import 'vxe-table/lib/style.css'

import VXETablePluginElement from 'vxe-table-plugin-element'
import 'vxe-table-plugin-element/dist/style.css'

import ZtVxeGrid from '@/components/ZtVxeGrid';
import EditDownTable from '@/components/EditDownTable'
import VXETablePluginExportXLSX from 'vxe-table-plugin-export-xlsx'
import VXETablePluginExportPDF from 'vxe-table-plugin-export-pdf'

Vue.component(EditDownTable.name, EditDownTable)

VXETable.use(VXETablePluginElement)

VXETable.use(VXETablePluginExportXLSX)

VXETable.use(VXETablePluginExportPDF)

// 将API方法绑定到全局
Vue.prototype.$api = api
Vue.use(VXETable);
Vue.use(ZtVxeGrid);
Vue.component(ZtVxeGrid.name, ZtVxeGrid)
Vue.use(VXEUtils, XEUtils);
Vue.prototype.$XPrint = VXETable.print
Vue.prototype.$XModal = VXETable.modal
Vue.prototype.$XSaveFile = VXETable.saveFile
Vue.prototype.$XReadFile = VXETable.readFile

// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree
Vue.prototype.deepClone = deepClone
Vue.prototype.getUserInfo = getUserInfo

Vue.prototype.msgSuccess = function (msg) {
  this.$message({
    showClose: true,
    message: msg,
    type: "success"
  });
}

Vue.prototype.msgError = function (msg) {
  this.$message({
    showClose: true,
    message: msg,
    type: "error"
  });
}

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
}

// 全局组件挂载
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)

Vue.use(permission)

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
