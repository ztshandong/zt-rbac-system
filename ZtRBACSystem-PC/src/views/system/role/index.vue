<template>
  <div class="app-container">
    <ZtVxeGrid ref="ztVxeGrid" :thisName="thisName" :saveFormItemsBakProps="saveFormItemsBak" :apiPre="apiPre"
      :thisPermissionPre="thisPermissionPre" :saveFormDataProps="saveFormData" @resetSaveFormData="resetSaveFormData"
      :tableColumnProps="tableColumn" @setSaveFormItemsBak="setSaveFormItemsBak"></ZtVxeGrid>
  </div>
</template>

<script>
  import ZtVxeGrid from '@/components/ZtVxeGrid';
  var _this;
  export default {
    name: 'ROLE_MANAGE',
    components: {
      ZtVxeGrid
    },
    data() {
      return {
        apiPre: "",
        thisName: "ZtRoleInfo",
        thisPermissionPre: 'ROLE_MANAGE',
        ztRoleTypeEnum: [],
        ztDataScopeTypeEnum: [],
        saveFormItemsBak: [],
        saveFormData: {
          "ztEnum2": null
        },
        ztEnum2Render: [],
        tableColumn: [{
            field: 'id',
            title: 'id',
            visible: false
          },
          {
            field: 'thisName',
            title: '角色名称'
          },
          {
            field: 'roleType',
            title: '角色类型',
            showHeaderOverflow: true,
            filters: this.ztRoleTypeEnum,
            filterMultiple: false,
            formatter: this.formatterRoleTypeEnum
          },
          {
            field: 'dataScopeType',
            title: '数据权限范围',
            showOverflow: true,
            filters: this.ztDataScopeTypeEnum,
            filterMultiple: false,
            formatter: this.formatterDataScopeTypeEnum
          },
          {
            field: 'remark',
            title: '备注'
          },
        ]
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
        this.$api.get(this.apiPre + '/getenuminfo?enumName=ZtTestStrEnum2', null, r => {
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
      formatterRoleTypeEnum({
        cellValue
      }) {
        let item = this.ztRoleTypeEnum.find(item => item.value === cellValue)
        return item ? item.label : null
      },
      formatterDataScopeTypeEnum({
        cellValue
      }) {
        let item = this.ztDataScopeTypeEnum.find(item => item.value === cellValue)
        return item ? item.label : null
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
    },
    mounted() {
      var param = {
        id: 1
      }
      this.$refs.ztVxeGrid.queryEvent(param);

      this.$api.get(this.apiPre + '/' + this.thisName + '/getEnumInfo?enumName=ZtRoleTypeEnum', null)
        .then(r => {
          _this.ztRoleTypeEnum = r.data
        })

      this.$api.get(this.apiPre + '/' + this.thisName + '/getEnumInfo?enumName=ZtDataScopeTypeEnum', null)
        .then(r => {
          _this.ztDataScopeTypeEnum = r.data
        })

    }
  }
</script>

<style>
</style>
