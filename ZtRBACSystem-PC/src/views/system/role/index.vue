<template>
  <div class="app-container">
    <!--
    <vxe-grid :formConfig="queryFormConfig">

    </vxe-grid>
 -->
    <!--
    <vxe-grid border resizable show-overflow keep-source ref="xTable" height="500" :data="tmpTableData"
      :columns="tmpTableColumn">
      <template #operate_default="{ row }">
        <span>
          <vxe-button status="primary" size="mini">自定义</vxe-button>
        </span>
        <vxe-button icon="fa fa-edit" title="编辑" @click="editButtonEvent(row)">编辑
        </vxe-button>
        <vxe-button icon="fa fa-edit" title="删除" status="danger" @click="removeButtonEvent(row)">删除</vxe-button>
      </template>
    </vxe-grid>
   -->
    <!-- <i class="fa fa-camera-retro fa-lg" style="color:green"></i> fa-lg -->
    <!-- <i class='zhangtao icon-word2' style="color:red"></i>icon-word2 -->
    <!-- <i class='zhangtao icon-excel ali' ></i>excel -->

    <!-- :queryFormItemsProps="queryFormItems"  @setSaveFormItemsBak="setSaveFormItemsBak" @resetSaveFormData="resetSaveFormData"-->
    <vxe-pulldown ref="xDown1">
      <vxe-input v-model="queryFormConfig.data.thisCode" placeholder="可搜索的下拉框" @focus="focusEvent1"></vxe-input>
      <!--
      <template #default>
        <vxe-input v-model="thisData.thisCode" placeholder="可搜索的下拉框" @focus="focusEvent1"></vxe-input>
      </template>
      -->
      <template #dropdown>
        <div class="my-dropdown1">
          <div class="list-item1" v-for="item in list1" :key="item.value" @click="selectEvent1(item)">
            <i class="fa fa-user-o"></i>
            <span>{{ item.label }}</span>
          </div>
        </div>
      </template>
    </vxe-pulldown>

    <vxe-button icon="fa fa-edit" @click="testButtonEvent">click</vxe-button>

    <zt-vxe-grid ref="ztVxeGrid" :apiPre="apiPre" :thisName="thisName" :thisPermissionPre="thisPermissionPre"
      :tableColumnProps="tableColumn" :queryFormConfigProps="queryFormConfig" :saveFormDataProps="saveFormData"
      :saveFormItemsProps="saveFormItems" :saveFormRolesProps="saveFormRoles" @formResetEvent="formResetEvent">
    </zt-vxe-grid>
  </div>
</template>

<script>
  // import ZtVxeGrid from '@/components/ZtVxeGrid';
  var _this;

  export default {
    name: 'ROLE_MANAGE',
    // components: {
    //   ZtVxeGrid
    // },

    data() {
      return {
        // tmpTableColumn: [{
        //   field: 'name',
        //   title: 'Name'
        // }, {
        //   field: 'operate',
        //   title: '操作',
        //   slots: {
        //     default: 'operate_default'
        //   }
        // }],
        // tmpTableData: [],
        //数据结构
        list1: [],
        thisData: {
          id: null,
          thisCode: null,
          parentCode: null,
          thisName: null,
          roleType: null,
          enableFlag: null,
          adminFlag: null,
          dataScopeType: null,
          dataScopeOpt: null,
          roleCustom: null,
          remark: null,
          startTime: null,
          endDate: null,
          deptCode: null,
        },
        //通用form表单元素。查询、新增、编辑都用
        thisCommonItem: [{
          field: 'deptCode',
          resetValue: null,
          title: '部门名称',
          span: 8,
          folding: false,
          itemRender: {
            name: 'ElCascader',
            showAllLevels: false,
            props: {
              props: {
                expandTrigger: 'hover',
                // checkStrictly: true,
                emitPath:false
              },
              // prefixIcon: "fa fa-user",
              // suffixIcon: "fa fa-search",
              placeholder: '请选择部门',
              clearable: true,
              options: [],
              filterable: true,
              expandTrigger: 'hover',
              showAllLevels: false,
              // checkStrictly: true
              // type: 'search',
            },
          }
        }, {
          field: 'thisName',
          resetValue: null,
          title: '角色名称',
          span: 8,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              // prefixIcon: "fa fa-user",
              // suffixIcon: "fa fa-search",
              placeholder: '请输入角色名称',
              clearable: true,
              // type: 'search',
            },
            events: {
              // focus: this.focusEvent1,
              // searchClick: this.doSearch,
              // search: this.doSearch,
              // click: this.doSearch,
              // searchclick: this.doSearch,
              // SearchClick: this.doSearch
            },
            nativeEvents: {
              // focus: this.focusEvent1,
              // searchClick: this.doSearch,
              // search: this.doSearch,
              // click: this.doSearch,
              // searchclick: this.doSearch,
              // SearchClick: this.doSearch
            },
          }
        }, {
          field: 'adminFlag',
          resetValue: null,
          title: '是否管理员',
          span: 8,
          folding: false,
          itemRender: {
            name: '$switch',
            props: {
              openIcon: "fa fa-bell",
              closeIcon: "fa fa-bell-slash",
              openValue: true,
              closeValue: false
            },
          }
        }, {
          field: 'parentCode',
          resetValue: null,
          title: '密码类型',
          span: 8,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              type: 'password',
              placeholder: '密码类型',
              clearable: true
            }
          }
        }, {
          field: 'startTime',
          resetValue: null,
          title: '开始日期',
          span: 8,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              placeholder: '日期和时间选择',
              clearable: true,
              type: 'datetime',
              transfer: true
            }
          }
        }, {
          field: 'endDate',
          resetValue: null,
          title: '结束时间',
          span: 8,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              placeholder: '日期选择',
              clearable: true,
              type: 'date',
              transfer: true
            }
          }
        }, {
          field: 'roleType',
          resetValue: null,
          title: '角色类型',
          span: 8,
          folding: false,
          itemRender: {
            name: '$select',
            options: [],
            props: {
              multiple: false,
              placeholder: '角色类型',
              clearable: true,
              transfer: true,
              disabled: false,
            }
          }
        }],
        //查询表单专用form元素
        thisQueryItem: [{
          field: 'remark',
          resetValue: null,
          title: '备注',
          span: 8,
          folding: true,
          itemRender: {
            name: '$textarea',
            props: {
              placeholder: '请输入备注',
              resize: "both"
            }
          }
        }, ],
        //新增、编辑专用form元素
        thisSaveItem: [{
          field: 'thisCode',
          resetValue: null,
          title: '角色编号',
          span: 8,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              type: 'search',
              placeholder: '请输入角色编号',
            },
            events: {
              searchClick: this.doSearch
            }
          }
        }, ],
        //查询表单校验规则，有就添加
        queryFormRoles: {

        },
        //created()里面赋值，查询表单直接传入config，在通用组件的vxe-grid里面
        queryFormConfig: {
          data: {},
          items: [],
          rules: {}
        },
        //新增编辑界面数据 created()里面赋值
        saveFormData: {},
        //新增编辑界面表单元素 created()里面赋值
        saveFormItems: [],
        //新增编辑表单校验规则
        saveFormRoles: {
          thisName: [{
              required: true,
              message: '请输入角色名称'
            },
            {
              min: 3,
              max: 5,
              message: '长度在 3 到 5 个字符'
            }
          ]
        },
        apiPre: "",
        thisName: "ZtRoleInfo",
        thisPermissionPre: 'ROLE_MANAGE',
        // saveFormItemsBak: [],
        // ztEnum2Render: [],
        tableColumn: [{
            field: 'id',
            title: 'id',
            visible: false
          },
          {
            field: 'thisCode',
            title: '角色编号',
            titleHelp: "{message: '自定义图标', icon: 'fa fa-bell'}"
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
            // formatter: this.formatterRoleTypeEnum,
            sortable: true,
            editRender: {
              name: '$select',
              options: [],
              multiple: true,
              props: {
                placeholder: '请选择角色类型'
              }
            }
          },
          {
            field: 'dataScopeType',
            title: '数据权限范围',
            showOverflow: true,
            filters: this.ztDataScopeTypeEnum,
            filterMultiple: false,
            // formatter: this.formatterDataScopeTypeEnum
            editRender: {
              name: '$select',
              options: [],
              multiple: true,
              props: {
                placeholder: '请选择数据权限范围'
              }
            }
          },
          {
            field: 'remark',
            title: '备注'
          },
        ],
        ztRoleTypeEnum: [],
        ztDataScopeTypeEnum: [],
      }
    },
    methods: {
      // resetSaveFormData(saveFormData) {
      // console.log(saveFormData)
      // saveFormData = {
      //   "ztEnum2": null
      // }
      // _this.saveFormData = {
      //   "ztEnum2": null
      // }
      // },
      // doQuery() {
      //   this.$refs.ztVxeGrid.queryEvent(this.queryData);
      // },
      // resetEvent() {
      //   console.log(this.queryData)
      // },
      // getZtEnum2RenderSource() {
      //   this.$api.get(this.apiPre + '/getenuminfo?enumName=ZtTestStrEnum2', null, r => {
      //     this.ztEnum2Render = r.data
      //   })
      // },
      // setSaveFormItemsBak(saveFormItems, callback) {
      //   let ztEnum = saveFormItems.filter(function(cur, index, arr) {
      //     return cur.field == 'ztEnum2';
      //   })
      //   if (ztEnum[0]) {
      //     ztEnum[0].itemRender.options = this.ztEnum2Render
      //   }
      //   this.saveFormItemsBak = this.deepClone(saveFormItems)
      //   callback(this.saveFormItemsBak)
      // },
      // formatterRoleTypeEnum({
      //   cellValue
      // }) {
      //   let item = this.ztRoleTypeEnum.find(item => item.value === cellValue)
      //   return item ? item.label : null
      // },
      // formatterDataScopeTypeEnum({
      //   cellValue
      // }) {
      //   let item = this.ztDataScopeTypeEnum.find(item => item.value === cellValue)
      //   return item ? item.label : null
      // },
      doSearch(value, $event) {
        console.log(value)
        console.log($event)
      },
      focusEvent1(e) {
        console.log(e)
        this.$refs.xDown1.showPanel()
      },
      selectEvent1(item) {
        this.queryFormConfig.data.thisCode = item.label
        this.$refs.xDown1.hidePanel().then(() => {
          // this.list1 = this.data1
        })
      },
      formResetEvent(e) {
        this.queryFormConfig.data.thisCode = null
      },
      testButtonEvent(e) {
        // console.log(e)
        this.queryFormConfig.data.thisCode = null
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

      this.saveFormData = this.deepClone(this.thisData)

      this.queryFormConfig.data = this.deepClone(this.thisData)
      this.thisCommonItem.forEach(t => {
        this.queryFormConfig.items.push(this.deepClone(t))
      })
      this.thisQueryItem.forEach(t => {
        this.queryFormConfig.items.push(this.deepClone(t))
      })
      this.queryFormConfig.rules = this.queryFormRoles

      this.thisCommonItem.forEach(t => {
        this.saveFormItems.push(this.deepClone(t))
      })
      this.thisSaveItem.forEach(t => {
        this.saveFormItems.push(this.deepClone(t))
      })

      this.$api.get(this.apiPre + '/' + this.thisName + '/getEnumInfo?enumName=ZtRoleTypeEnum', null)
        .then(r => {
          _this.ztRoleTypeEnum = r.data

          // let roleType = {
          //   field: 'roleType',
          //   resetValue: null,
          //   title: '角色类型',
          //   span: 8,
          //   folding: true,
          //   itemRender: {
          //     name: '$select',
          //     options: _this.ztRoleTypeEnum
          //   }
          // }
          // this.thisCommonItem.push(roleType)

          this.queryFormConfig.items.forEach(t => {
            if (t.field == 'roleType') {
              t.itemRender.options = _this.ztRoleTypeEnum
              return
            }
          })

          this.saveFormItems.forEach(t => {
            if (t.field == 'roleType') {
              t.itemRender.options = _this.ztRoleTypeEnum
              return
            }
          })

          this.tableColumn.forEach(t => {
            if (t.field == 'roleType') {
              t.editRender.options = _this.ztRoleTypeEnum
              return
            }
          })
          // const $grid = this.$refs.ztVxeGrid.$refs.ZtVxeGrid
          // const roleTypeColumn = $grid.getColumnByField('roleType')
          // roleTypeColumn.editRender.options = _this.ztRoleTypeEnum

          // for (var i = 0; i < 20; i++) {
          //   let roleTypeItem = $grid.getFormItems(i)
          //   if (roleTypeItem.field == 'roleType') {
          //     roleTypeItem.itemRender.options = _this.ztRoleTypeEnum
          //     break;
          //   }
          // }

          // console.log($grid.getFormItems(0))
          // console.log($grid.getFormItems(2))
          // console.log($grid.getFormItems(3))
          // roleTypeItem.itemRender.options = _this.ztRoleTypeEnum

        })

      this.$api.get(this.apiPre + '/' + this.thisName + '/getEnumInfo?enumName=ZtDataScopeTypeEnum', null)
        .then(r => {
          _this.ztDataScopeTypeEnum = r.data

          this.queryFormConfig.items.forEach(t => {
            if (t.field == 'dataScopeType') {
              t.itemRender.options = _this.ztDataScopeTypeEnum
              return
            }
          })

          this.saveFormItems.forEach(t => {
            if (t.field == 'dataScopeType') {
              t.itemRender.options = _this.ztDataScopeTypeEnum
              return
            }
          })

          this.tableColumn.forEach(t => {
            if (t.field == 'dataScopeType') {
              t.editRender.options = _this.ztDataScopeTypeEnum
              return
            }
          })

        })

      this.$api.post('/ZtDeptInfo/getAllDeptTree', null)
        .then(r => {
          this.queryFormConfig.items.forEach(t => {
            if (t.field == 'deptCode') {
              t.itemRender.props.options = r.data;
              return
            }
          })

          this.saveFormItems.forEach(t => {
            if (t.field == 'deptCode') {
              t.itemRender.props.options = r.data;
              return
            }
          })

          this.tableColumn.forEach(t => {
            if (t.field == 'deptCode') {
              t.editRender.props.options = r.data;
              return
            }
          })

        })

      const list1 = []
      for (let index = 0; index < 20; index++) {
        list1.push({
          label: `选项${index}`,
          value: index
        })
      }

      this.list1 = list1
    },
    mounted() {

      // const $form = this.$refs.ztVxeGrid.$refs.ZtVxeModal
      // console.log($form)

      // this.$refs.ztVxeGrid.queryEvent(this.queryData);
    }
  }
</script>

<style>
  /*  .ali{
    font-size: 36px;
  } */
</style>
