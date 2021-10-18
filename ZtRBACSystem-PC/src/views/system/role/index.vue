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
    <!--
    <vxe-pulldown ref="xDown1">
      <vxe-input v-model="queryFormConfig.data.thisCode" placeholder="可搜索的下拉框" @focus="focusEvent1"></vxe-input>

      <template #dropdown>
        <div class="my-dropdown1">
          <div class="list-item1" v-for="item in list1" :key="item.value" @click="selectEvent1(item)">
            <i class="fa fa-user-o"></i>
            <span>{{ item.label }}</span>
          </div>
        </div>
      </template>
    </vxe-pulldown>
 -->
    <!-- <vxe-button icon="fa fa-edit" @click="testButtonEvent">click</vxe-button> -->
    <!-- @formResetEvent="formResetEvent" @afterQuery="afterQuery" -->
    <zt-vxe-grid ref="ztVxeGrid" :apiPre="apiPre" :thisName="thisName" :thisPermissionPre="thisPermissionPre"
      :tableColumnProps="tableColumn" :queryFormConfigProps="queryFormConfig" :saveFormConfigProps="saveFormConfig">
    </zt-vxe-grid>

    <el-tabs v-model="activeName" @tab-click="tabClick">
      <el-tab-pane label="菜单" name="menu">
        <el-transfer v-model="menuValue" :props="menuProps" :data="menuData" filterable :titles="['所有菜单', '角色菜单']">
        </el-transfer>
        <vxe-button icon="fa fa-edit" title="保存" @click="saveMenuRole">
          保存菜单角色
        </vxe-button>
      </el-tab-pane>
      <el-tab-pane label="部门" name="dept">
        <el-transfer v-model="deptValue" :props="deptProps" :data="deptData" filterable :titles="['所有部门', '角色部门']">
        </el-transfer>
        <vxe-button icon="fa fa-edit" title="保存" @click="saveDeptRole">
          保存部门角色
        </vxe-button>
      </el-tab-pane>
      <el-tab-pane label="人员" name="user">
        <el-transfer v-model="userValue" :props="userProps" :data="userData" filterable :titles="['所有人员', '角色人员']">
        </el-transfer>
        <vxe-button icon="fa fa-edit" title="保存" @click="saveUserRole">
          保存人员角色
        </vxe-button>
      </el-tab-pane>
      <el-tab-pane label="职位" name="post">
        <el-transfer v-model="postValue" :props="postProps" :data="postData" filterable :titles="['所有职位', '角色职位']">
        </el-transfer>
        <vxe-button icon="fa fa-edit" title="保存" @click="savePostRole">
          保存职位角色
        </vxe-button>
      </el-tab-pane>
    </el-tabs>
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
        activeName: 'menu',
        menuProps: {
          key: 'thisCode',
          label: 'thisName',
        },
        menuData: [],
        menuValue: [],
        deptProps: {
          key: 'thisCode',
          label: 'thisName',
        },
        deptData: [],
        deptValue: [],
        userProps: {
          key: 'userCode',
          label: 'userName',
        },
        userData: [],
        userValue: [],
        postProps: {
          key: 'thisCode',
          label: 'thisName',
        },
        postData: [],
        postValue: [],
        curRow: {},
        //数据结构
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
                emitPath: false
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
        thisQueryItem: [],
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
        saveFormConfig: {
          data: {},
          items: [],
          rules: {}
        },
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
            title: '角色名称',
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
      saveMenuRole() {
        let ztRoleComponentInfo = []
        this.menuValue.forEach(e => {
          let tmp = {
            componentCode: e,
            roleCode: this.curRow.thisCode,
            otherParams: 'ROLE_MANAGE'
          }
          ztRoleComponentInfo.push(tmp)
        })
        this.$api.post('/ZtRoleComponentInfo/insertBatchSimple', ztRoleComponentInfo)
          .then(r => {
            this.$refs.ztVxeGrid.$emit("queryFormEvent")
          })
      },
      saveDeptRole() {
        let ztRoleDeptInfo = []
        this.deptValue.forEach(e => {
          let tmp = {
            deptCode: e,
            roleCode: this.curRow.thisCode,
            otherParams: 'ROLE_MANAGE'
          }
          ztRoleDeptInfo.push(tmp)
        })
        this.$api.post('/ZtRoleDeptInfo/insertBatchSimple', ztRoleDeptInfo)
          .then(r => {
            this.$refs.ztVxeGrid.$emit("queryFormEvent")
          })
      },
      saveUserRole() {
        let ztRoleUserInfo = []
        this.userValue.forEach(e => {
          let tmp = {
            userCode: e,
            roleCode: this.curRow.thisCode,
            otherParams: 'ROLE_MANAGE'
          }
          ztRoleUserInfo.push(tmp)
        })
        this.$api.post('/ZtRoleUserInfo/insertBatchSimple', ztRoleUserInfo)
          .then(r => {
            this.$refs.ztVxeGrid.$emit("queryFormEvent")
          })
      },
      savePostRole() {
        let ztRolePostInfo = []
        this.postValue.forEach(e => {
          let tmp = {
            postCode: e,
            roleCode: this.curRow.thisCode,
            otherParams: 'ROLE_MANAGE'
          }
          ztRolePostInfo.push(tmp)
        })
        this.$api.post('/ZtRolePostInfo/insertBatchSimple', ztRolePostInfo)
          .then(r => {
            this.$refs.ztVxeGrid.$emit("queryFormEvent")
          })
      },
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
      afterQuery() {

      },
      showEditForm(row, items) {

      },
      cellClick(data) {

      },
      currentChange(data) {
        this.curRow = data.row
        let role = {
          roleCode: data.row.thisCode,
          limit: 1000
        }
        // console.log(role)
        this.$api.post('/ZtRoleComponentInfo/selectSimple', role)
          .then(r => {
            let menuCodes = r.data.results.map(item => {
              return item.componentCode;
            })
            this.menuValue = menuCodes
          })

        this.$api.post('/ZtRoleDeptInfo/selectSimple', role)
          .then(r => {
            let deptCodes = r.data.results.map(item => {
              return item.deptCode;
            })
            this.deptValue = deptCodes
          })

        this.$api.post('/ZtRoleUserInfo/selectSimple', role)
          .then(r => {
            let userCodes = r.data.results.map(item => {
              return item.userCode;
            })
            this.userValue = userCodes
          })

        this.$api.post('/ZtRolePostInfo/selectSimple', role)
          .then(r => {
            let postCodes = r.data.results.map(item => {
              return item.postCode;
            })
            this.postValue = postCodes
          })

      },
      customToolbarButton(code) {

      },
      tabClick(tab, event) {
        // console.log(tab, event)
      },
    },
    created() {
      _this = this

      this.thisCommonItem.forEach(t => {
        this.queryFormConfig.items.push(this.deepClone(t))
        this.saveFormConfig.items.push(this.deepClone(t))
      })

      this.queryFormConfig.data = this.deepClone(this.thisData)
      this.thisQueryItem.forEach(t => {
        this.queryFormConfig.items.push(this.deepClone(t))
      })
      this.queryFormConfig.rules = this.queryFormRoles

      this.saveFormConfig.data = this.deepClone(this.thisData)
      this.thisSaveItem.forEach(t => {
        this.saveFormConfig.items.push(this.deepClone(t))
      })
      this.saveFormConfig.rules = this.saveFormRoles

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

          this.saveFormConfig.items.forEach(t => {
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

          this.saveFormConfig.items.forEach(t => {
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
              t.itemRender.props.options = r.data.results;
              return
            }
          })

          this.saveFormConfig.items.forEach(t => {
            if (t.field == 'deptCode') {
              t.itemRender.props.options = r.data.results;
              return
            }
          })

          this.tableColumn.forEach(t => {
            if (t.field == 'deptCode') {
              t.editRender.props.options = r.data.results;
              return
            }
          })

        })

      this.$api.post('/ZtComponentInfo/selectSimple', null)
        .then(r => {
          let menuCodes = r.data.results.filter(t => t.leafFlag)
          this.menuData = menuCodes;
        })

      this.$api.post('/ZtDeptInfo/selectSimple', null)
        .then(r => {
          this.deptData = r.data.results;
        })

      this.$api.post('/ZtUserInfo/selectSimple', null)
        .then(r => {
          this.userData = r.data.results;
        })

      this.$api.post('/ZtPostInfo/selectSimple', null)
        .then(r => {
          this.postData = r.data.results;
        })
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
