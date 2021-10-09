<template>
  <div class="app-container">
    <ZtVxeGrid ref="ztVxeGrid" :thisName="thisName" :saveFormItemsBakProps="saveFormItemsBak"
      :thisPermissionPre="thisPermissionPre" :saveFormDataProps="saveFormData" @resetSaveFormData="resetSaveFormData"
      @setSaveFormItemsBak="setSaveFormItemsBak"></ZtVxeGrid>
  </div>
</template>

<script>
  import ZtVxeGrid from '@/components/ZtVxeGrid';
  var _this;
  export default {
    name: 'TestZtVxeGrid',
    components: {
      ZtVxeGrid
    },
    data() {
      return {
        thisName: "ZtRoleInfo",
        thisPermissionPre: 'ROLE_MANAGE',
        saveFormItemsBak: [],
        saveFormData: {
          "ztEnum2": null
        },
        ztEnum2Render: []
      }
    },
    methods: {
      resetSaveFormData(saveFormData) {
        // console.log(saveFormData)
        saveFormData = {
          "ztEnum2": null
        }
        _this.saveFormData = {
          "ztEnum2": null
        }
      },
      getZtEnum2RenderSource() {
        this.$api.get(this.apiPre + '/ztButtonInfo/getenuminfo?enumName=ZtTestStrEnum2', null, r => {
          this.ztEnum2Render = r.data
        })
      },
      setSaveFormItemsBak(saveFormItems, callback) {
        let ztEnum = saveFormItems.filter(function(cur, index, arr) {
          return cur.field == 'ztEnum2';
        })
        if (ztEnum[0]) {
          ztEnum[0].itemRender.options = this.ztEnum2Render
        }
        this.saveFormItemsBak = this.deepClone(saveFormItems)
        callback(this.saveFormItemsBak)
      },
      deepClone(target) {
        // console.log(target)
        // 定义一个变量
        let result;
        // debugger
        // 如果当前需要深拷贝的是一个对象的话
        if (typeof target === 'object') {
          // 如果是一个数组的话
          if (Array.isArray(target)) {
            result = []; // 将result赋值为一个数组，并且执行遍历
            for (let i in target) {
              // 递归克隆数组中的每一项
              result.push(_this.deepClone(target[i]))
            }
            // 判断如果当前的值是null的话；直接赋值为null
          } else if (target === null) {
            result = null;
            // 判断如果当前的值是一个RegExp对象的话，直接赋值
          } else if (target.constructor === RegExp) {
            result = target;
          } else {
            // 否则是普通对象，直接for in循环，递归赋值对象的所有值
            result = {};
            for (let i in target) {
              result[i] = _this.deepClone(target[i]);
            }
          }
          // 如果不是对象的话，就是基本数据类型，那么直接赋值
        } else {
          result = target;
        }
        // 返回最终结果
        return result;
      },
    },
    created() {
      _this = this
      // this.getZtEnum2RenderSource()
    },
    mounted() {
      this.$refs.ztVxeGrid.queryFormEvent();
    }
  }
</script>

<style>
</style>
