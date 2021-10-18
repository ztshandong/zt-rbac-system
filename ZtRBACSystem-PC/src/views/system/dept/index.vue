<template>
  <div class="app-container">
    <!-- @showEditForm="showEditForm" @customToolbarButton="customToolbarButton" @specialQuery="specialQuery" @afterQuery="afterQuery" @currentChange="currentChange" @cellClick="cellClick" -->
    <zt-vxe-grid ref="ztVxeGrid" :apiPre="apiPre" :thisName="thisName" :tableColumnProps="tableColumn"
      :treeConfigProps="treeConfig" :toolbarCustomButtonConfig="otherButtons" :queryFormConfigProps="queryFormConfig"
      :customGridSlotButton="customGridSlotButton" :saveFormConfigProps="saveFormConfig" >
    </zt-vxe-grid>
    <el-transfer v-model="transferValue" :props="transferProps" :data="transferData" filterable
      :titles="['所有用户', '部门用户']">
    </el-transfer>
    <vxe-button icon="fa fa-edit" title="保存" @click="saveTransferValue">
      保存
    </vxe-button>
  </div>
</template>

<script>
  var _this;
  export default {
    name: "DEPT_MANAGE",
    data() {
      return {
        transferProps: {
          key: 'userCode',
          label: 'userName',
        },
        transferData: [],
        transferValue: [],
        curRow: {},
        treeConfig: {
          children: 'children',
          expandAll: true
        },
        customGridSlotButton: [{
          icon: "fa fa-edit",
          title: "title1",
          status: "primary",
          show: this.title1Show(),
          click: this.title1Click
        }, {
          icon: "fa fa-edit",
          title: "title2",
          status: "primary",
          show: this.title2Show(),
          click: this.title2Click
        }],
        otherButtons: [{
          code: 'customButton',
          name: '部门专用',
          status: 'info',
          icon: 'fa fa-plus'
        }, {
          name: '树操作',
          disabled: false,
          status: 'primary',
          dropdowns: [{
              code: 'getCheckBox',
              name: '获取已选择复选框',
              status: 'primary',
              disabled: false
            },
            {
              code: 'expandAll',
              name: '展开全部',
              type: 'text',
              status: 'info',
              disabled: false
            },
            {
              code: 'closeAll',
              name: '折叠全部',
              type: 'text',
              disabled: false
            }
          ]
        }],
        apiPre: "",
        thisName: "ZtDeptInfo",
        thisData: {
          id: null,
          thisCode: null,
          thisName: null,
          parentCode: null,
          rootCode: null,
          enableFlag: null,
          deptLeaderCode: null,
          remark: null
        },
        thisCommonItem: [
          // 内嵌div
          // {
          //   isDiv: true,
          //   style: 'border: 1px dashed red;padding: 10px;',
          //   items: [{
          //     alicon: 'word2',
          //     iconFirst: true,
          //     title: '基础信息222',
          //     span: 24,
          //     titleAlign: "left",
          //     titleWidth: "200px",
          //     titlePrefix: {
          //       icon: 'fa fa-address-card-o',
          //       message: '请填写基础信息222'
          //     },
          //   }, {
          //     alicon: 'excel',
          //     textFirst: true,
          //     field: 'thisName',
          //     resetValue: null,
          //     title: '部门名称222',
          //     span: 12,
          //     folding: false,
          //     itemRender: {
          //       name: '$input',
          //       props: {
          //         placeholder: '请输入部门名称222',
          //         clearable: true,
          //       },
          //     }
          //   }]
          // },
          {
            index: 3,
            queryOnly: true,
            alicon: 'word2',
            iconFirst: true,
            title: '基础信息',
            span: 24,
            titleAlign: "left",
            titleWidth: "200px",
            titlePrefix: {
              icon: 'fa fa-address-card-o',
              message: '请填写基础信息'
            },
            visibleMethod: this.testVisible
          }, {
            index: 2,
            editOnly: true,
            alicon: 'excel',
            textFirst: true,
            field: 'thisName',
            resetValue: null,
            title: '部门名称',
            span: 12,
            folding: false,
            itemRender: {
              name: '$input',
              props: {
                placeholder: '请输入部门名称',
                clearable: true,
              },
            }
          }, {
            index: 1,
            field: 'thisCode',
            resetValue: null,
            title: '部门编号',
            span: 12,
            folding: false,
            titlePrefix: {
              icon: 'fa fa-address-card-o',
              message: '部门编号不可修改'
            },
            itemRender: {
              name: '$input',
              props: {
                placeholder: '请输入部门编号',
                clearable: true,
                disabled: false
              },
            }
          }, {
            title: '其他信息',
            span: 24,
            titleAlign: "left",
            titleWidth: "200px",
            titlePrefix: {
              icon: 'zhangtao icon-word2',
              message: '请填写其他项'
            }
          }, {
            index: 4,
            field: 'parentCode',
            resetValue: null,
            title: '上级部门',
            span: 12,
            folding: false,
            itemRender: {
              name: 'ElCascader',
              showAllLevels: false,
              props: {
                props: {
                  expandTrigger: 'hover',
                  checkStrictly: true,
                  emitPath: false
                },
                // prefixIcon: "fa fa-user",
                // suffixIcon: "fa fa-search",
                placeholder: '请选择上级部门',
                clearable: true,
                options: [],
                filterable: true,
                expandTrigger: 'hover',
                showAllLevels: false,
                checkStrictly: true
                // type: 'search',
              },
            }
          },
        ],
        thisQueryItem: [],
        thisSaveItem: [],
        queryFormConfig: {
          useSpecialQuery: true,
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
              message: '请输入部门名称'
            },
            {
              min: 2,
              max: 50,
              message: '长度在 2 到 50 个字符'
            }
          ],
          thisCode: [{
              required: true,
              message: '请输入部门编号'
            },
            {
              min: 5,
              max: 50,
              message: '长度在 5 到 50 个字符'
            }
          ]
        },
        tableColumn: [{
            type: 'checkbox',
            treeNode: true,
          }, {
            field: 'id',
            title: 'id',
            visible: false
          },
          {
            field: 'thisCode',
            title: '部门编号',
            titleHelp: "{message: '自定义图标', icon: 'fa fa-bell'}"
          },
          {
            field: 'thisName',
            title: '部门名称',
          },
          {
            field: 'parentCode',
            title: '上级部门编号',
            showHeaderOverflow: true,
            // filters: this.ztRoleTypeEnum,
            filterMultiple: false,
            // formatter: this.formatterRoleTypeEnum,
            sortable: true,
            // editRender: {
            //   name: 'ElCascader',
            //   showAllLevels: false,
            //   props: {
            //     props: {
            //       expandTrigger: 'hover',
            //       emitPath: false
            //     },
            //     placeholder: '请选择上级部门',
            //     clearable: true,
            //     options: [],
            //     filterable: true,
            //     expandTrigger: 'hover',
            //     showAllLevels: false,
            //   },
            // }
          },
          {
            field: 'remark',
            title: '备注'
          }
        ],
        // 遮罩层
        loading: true,
        // 显示搜索条件
        showSearch: true,
        // 表格树数据
        deptList: [],
        // 部门树选项
        deptOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 状态数据字典
        statusOptions: [],
        // 查询参数
        queryParams: {
          deptName: undefined,
          status: undefined
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          parentId: [{
            required: true,
            message: "上级部门不能为空",
            trigger: "blur"
          }],
          deptName: [{
            required: true,
            message: "部门名称不能为空",
            trigger: "blur"
          }],
          orderNum: [{
            required: true,
            message: "菜单顺序不能为空",
            trigger: "blur"
          }],
          email: [{
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }],
          phone: [{
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }]
        }
      };
    },
    created() {
      _this = this
      console.log('_this = this')

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
      this.getList();

      this.$api.post('/ZtUserInfo/selectSimple', null)
        .then(r => {
          this.transferData = r.data.results;
        })

      // this.getDicts("sys_normal_disable").then(response => {
      //   this.statusOptions = response.data;
      // });
    },
    mounted() {
      // setCheckboxRow
      console.log('_this mounted')
    },
    computed: {
      isDisable: function() {
        return true;
      },
      // 方式二
      // iconName: function() {
      //   return function(iconName) {
      //     let name = "#icon-"
      //     name = name + iconName
      //     console.log(name)
      //     return `${name}`
      //   }
      // }
    },
    methods: {
      formResetEvent(e) {

      },
      saveTransferValue() {
        // let user = this.getUserInfo()
        // console.log(this.transferValue)
        let userDeptInfo = []
        this.transferValue.forEach(e => {
          let tmp = {
            userCode: e,
            deptCode: this.curRow.thisCode,
            otherParams: 'DEPT_MANAGE'
          }
          userDeptInfo.push(tmp)
        })
        this.$api.post('/ZtUserDeptInfo/insertBatchSimple', userDeptInfo)
          .then(r => {
            this.$refs.ztVxeGrid.$emit("queryFormEvent")
          })
      },
      currentChange(newValue, oldValue, row, rowIndex, $rowIndex, column, columnIndex, $columnIndex, $event) {
        // console.log(newValue.row)
        this.curRow = newValue.row
        // console.log('dept')
        // console.log(this.curRow)
        let dept = {
          deptCode: newValue.row.thisCode
        }
        // console.log(dept)
        this.$api.post('/ZtUserDeptInfo/selectSimple', dept)
          .then(r => {
            let userCodes = r.data.results.map(item => {
              return item.userCode;
            })
            this.transferValue = userCodes
          })
      },
      cellClick(data, rowIndex, $rowIndex, column, columnIndex, $columnIndex, triggerRadio, triggerCheckbox,
        triggerTreeNode, triggerExpandNode, $event) {
        // console.log(data)
      },
      specialQuery(param, callback) {
        // console.log('specialQuery')
        this.$api.post('/ZtDeptInfo/getAllDeptTree', null)
          .then(r => {
            callback(r)
            this.$nextTick(() => {
              // let data = _this.$refs.ztVxeGrid.$refs.ZtVxeGrid.getTableData().tableData
              // _this.$refs.ztVxeGrid.$refs.ZtVxeGrid.setAllCheckboxRow(true)
              // let checkedIds = [4, 6]
              // checkedIds.forEach(id => {
              //   let checkedRow = _this.$refs.ztVxeGrid.$refs.ZtVxeGrid.getRowById(id)
              //   _this.$refs.ztVxeGrid.$refs.ZtVxeGrid.setCheckboxRow(checkedRow, true)
              // })
            })
          })
      },
      afterQuery() {
        // console.log('afterQuery')
        this.$nextTick(() => {
          let checkedIds = [4, 6]
          checkedIds.forEach(id => {
            let checkedRow = this.$refs.ztVxeGrid.$refs.ZtVxeGrid.getRowById(id)
            // console.log(this.$refs.ztVxeGrid.$refs.ZtVxeGrid)
            // console.log(checkedRow)
            this.$refs.ztVxeGrid.$refs.ZtVxeGrid.setCheckboxRow(checkedRow, true)
          })
        })
      },
      title1Show() {
        return false
      },
      title2Show() {
        return true
      },
      title1Click(row) {
        console.log('title1Click')
        console.log(row)
      },
      title2Click(row) {
        console.log('title2Click')
        console.log(row)
      },
      customToolbarButton(code) {
        console.log(code)
        switch (code) {
          case 'getCheckBox':
            let selectRow = this.$refs.ztVxeGrid.$refs.ZtVxeGrid.getCheckboxRecords(true)
            console.log(selectRow)
            break
          case 'expandAll':
            this.$refs.ztVxeGrid.$refs.ZtVxeGrid.setAllTreeExpand(true)
            break
          case 'closeAll':
            this.$refs.ztVxeGrid.$refs.ZtVxeGrid.clearTreeExpand()
            break
          case 'customButton':
            this.queryFormConfig.useSpecialQuery = !this.queryFormConfig.useSpecialQuery
            if (this.queryFormConfig.useSpecialQuery) {
              this.treeConfig = {
                children: 'children',
                expandAll: true
              }
            } else {
              this.treeConfig = null
            }

        }
      },
      testVisible(e) {
        // console.log(e)
        return true
      },
      iconName(iconName) {
        let name = "#icon-"
        name = name + iconName
        return `${name}`
      },
      showEditForm(row, items) {
        // console.log(row)
        // console.log(items)
        if (row.id) {
          items.forEach(t => {
            if (t.field == 'thisCode') {
              t.itemRender.props.disabled = true;
              return
            }
          })
        } else {
          items.forEach(t => {
            if (t.field == 'thisCode') {
              t.itemRender.props.disabled = false;
              return
            }
          })
        }
      },
      /** 查询部门列表 */
      getList() {
        // this.loading = true;
        this.$api.post(this.apiPre + '/' + this.thisName + '/getAllDeptTree', null)
          .then(r => {
            this.queryFormConfig.items.forEach(t => {
              if (t.field == 'parentCode') {
                t.itemRender.props.options = r.data.results;
                return
              }
            })
            this.saveFormConfig.items.forEach(t => {
              if (t.field == 'parentCode') {
                t.itemRender.props.options = r.data.results;
                return
              }
            })
            // this.tableColumn.forEach(t => {
            //   if (t.field == 'parentCode') {
            //     t.editRender.props.options = r.data;
            //     return
            //   }
            // })
          })
        // listDept(this.queryParams).then(response => {
        //   this.deptList = this.handleTree(response.data, "deptId");
        //   this.loading = false;
        // });
      },
    }
  };
</script>

<style>
  .ali {
    font-size: 20px;
  }
</style>
