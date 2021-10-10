<template>
  <div :style="{height: scrollerHeight}">
    <!-- <div height="800"> -->

    <!--
    第一种方式子类自行实现，此时@submit调用的是this.$refs.ztVxeGrid.queryEvent(this.queryData);传入的是对象
    <vxe-form :data="queryData" @submit="doQuery" @reset="resetEvent">
      <vxe-form-item title="角色" field="thisName" :item-render="{}">
          <template #default="{ data }">
          <vxe-input v-model="data.thisName" placeholder="请输入名称"></vxe-input>
        </template>
      </vxe-form-item>
      <vxe-form-item title="角色" field="thisName" :item-render="{}">
        <template #default>
          <vxe-input v-model="queryData.thisName" placeholder="请输入名称"></vxe-input>
        </template>
      </vxe-form-item>
      <vxe-form-item>
        <template #default>
          <vxe-button type="submit" status="primary">查询</vxe-button>
          <vxe-button type="reset">重置</vxe-button>
        </template>
      </vxe-form-item>
    </vxe-form>
 -->

    <!--
    <vxe-form ref="ZtVxeQueryForm" :data="queryData" :items="queryFormItems" :rules="queryFormRoles" title-align="right"
      title-width="100" @submit="queryFormEvent" @toggle-collapse="queryFormToggleEvent" @reset="resetEvent"></vxe-form>
       -->

    <!-- queryFormConfig要传全部的配置，否则有问题。通用组件里面添加查询与重制按钮，这样查询触发的就是@form-submit的queryFormEvent，入参是个事件 -->
    <vxe-grid border stripe resizable show-overflow showHeaderOverflow highlightHoverRow keep-source :show-header="true"
      height="130%" :loading="loading" ref="ZtVxeGrid" row-id="id" :data="tableData" :columns="tableColumn"
      :toolbar-config="toolbarConfig" :import-config="importConfig" :export-config="exportConfig"
      :form-config="queryFormConfig" @form-submit="queryFormEvent"
      :seq-config="{startIndex: (tablePage.currentPage - 1) * tablePage.pageSize}" :print-config="printConfig"
      @toolbar-button-click="toolbarButtonClickEvent" @form-toggle-collapse="queryFormToggleEvent">

      <!--
      这样会把toolbar的按钮挤乱，配合修改toolbarConfigProps
      <template #toolbar_buttons>
        <vxe-form ref="ZtVxeQueryForm" :data="queryData" :items="queryFormItems" :rules="queryFormRoles"
          title-align="right" title-width="100" @submit="queryFormEvent" @toggle-collapse="queryFormToggleEvent"
          @reset="resetEvent"></vxe-form>
      </template>
 -->
      <template v-slot:operate="{ row }">
        <vxe-button icon="fa fa-edit" title="编辑" v-if="showEdit" :disabled="!showEdit" @click="editButtonEvent(row)">编辑
        </vxe-button>
        <vxe-button icon="fa fa-edit" title="删除" status="danger" v-if="showRemove" :disabled="!showRemove"
          @click="removeButtonEvent(row)">删除</vxe-button>
      </template>

      <template #pager>
        <vxe-pager :layouts="['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total']"
          :current-page.sync="tablePage.currentPage" :page-size.sync="tablePage.pageSize" :total="tablePage.total"
          :page-sizes="[1, 3, 5, 10, 15, 20, 50, 100, 200, 500, 1000]" @page-change="handlePageChange">
        </vxe-pager>
      </template>

    </vxe-grid>

    <vxe-modal ref="ZtVxeModal" v-model="showSaveForm" :title="saveFormData ? '编辑&保存' : '新增&保存'" width="100%"
      min-width="600" min-height="300" resize destroy-on-close>
      <template v-slot>
        <vxe-form ref="ZtVxeForm" :data="saveFormData" :items="saveFormItems" :rules="saveFormRules" title-align="right"
          title-width="100" @submit="saveButtonEvent" @toggle-collapse="queryFormToggleEvent"></vxe-form>
      </template>
    </vxe-modal>
  </div>
</template>

<script>
  var _this;
  import store from '@/store'
  import {
    PermissionDefine
  } from "@/utils/permissiondefine"

  export default {
    name: 'ZtVxeGrid',
    props: {
      apiPre: {
        // api请求前缀，对应不同微服务的统一前缀。如果没有微服务或不需要就留个空白
        type: String,
        default: "",
      },
      thisName: {
        // 当前界面请求的api路径 就是后台controller的@RequestMapping。例如 /ZtDeptInfo
        type: String,
        default: "",
      },
      thisPermissionPre: {
        //应该用数组，因为后台是数组。暂时先用单个的
        // 当前界面权限前缀，对应后台controller的@ZtPreAuthorize。例如ZtMenuCodeEnum.ROLE_DEPT
        // 也就是zt_component_info中menu_type = BUTTON 的 parent_code。与button_code共同拼接权限
        type: String,
        default: "",
      },
      tableColumnProps: {
        // 列
        type: Array,
        default: () => [],
      },
      tableDataProps: {
        // 数据
        type: Array,
        default: () => [],
      },
      queryFormConfigProps: {
        // 查询条件配置
        type: Object,
        default: () => ({
          data: {},
          titleWidth: 100,
          titleAlign: 'right',
          items: []
        }),
      },
      queryFormItemsProps: {
        // 查询表单各个字段
        type: Array,
        default: () => [],
      },
      queryFormRolesProps: {
        // 校验规则
        type: Object,
        default: () => ({}),
      },
      queryDataProps: {
        // 查询条件具体数据
        type: Object,
        default: () => ({}),
      },
      tablePageProps: {
        //默认分页大小
        type: Object,
        default: () => ({
          total: 0,
          currentPage: 1,
          pageSize: 10
        }),
      },

      saveFormDataProps: {
        // 保存的实体类
        type: Object,
        default: () => ({}),
      },
      saveFormRolesProps: {
        // 校验规则
        type: Object,
        default: () => ({}),
      },
      saveFormItemsProps: {
        // 弹框界面各个字段
        type: Array,
        default: () => [],
      },
      saveFormItemsBakProps: {
        // 弹框界面各个字段
        type: Array,
        default: () => [],
      },

      printConfigProps: {
        // 打印配置
        type: Object,
        default: () => ({}),
      },
      importConfigProps: {
        // 导入配置
        type: Object,
        default: () => ({
          modes: ['insert']
        }),
      },
      exportConfigProps: {
        // 导出配置
        type: Object,
        default: () => ({
          modes: ['current', 'selected', 'all']
        }),
      },
      toolbarConfigProps: {
        // 工具栏按钮配置
        type: Object,
        default: () => ({
          buttons: [{
              visible: true,
              // disabled: !this.showAdd,
              code: PermissionDefine.BUTTON_ADD,
              name: '新增',
              status: 'primary'
            }, {
              code: 'myInsert',
              name: 'grid新增',
              status: 'info'
            },
            {
              code: 'saveImport',
              name: 'app.body.button.save',
              status: 'success'
            },
            {
              name: '其他',
              disabled: false,
              status: 'danger',
              dropdowns: [{
                  code: 'other1',
                  name: '自定义1',
                  status: 'primary',
                  disabled: false
                },
                {
                  code: 'other2',
                  name: '自定义2',
                  type: 'text',
                  status: 'info',
                  disabled: true
                },
                {
                  code: 'other3',
                  name: '自定义3',
                  type: 'text',
                  disabled: false
                }
              ]
            }
          ],
          perfect: true,
          import: false,
          export: false,
          print: false,
          zoom: true,
          custom: true,
          // slots: {
          //   buttons: 'toolbar_buttons'
          // }
        }),
      },
    },
    data() {
      return {
        loading: false,
        // tableHeight: 600,
        showAdd: false,
        showQuery: false,
        showEdit: false,
        showRemove: false,
        showApp: false,
        showPrint: false,
        showImport: false,
        showExport: false,
        showSaveForm: false,
        tableData: this.tableDataProps,
        tableColumn: this.tableColumnProps,
        tablePage: this.tablePageProps,

        queryFormConfig: this.queryFormConfigProps,
        queryFormItems: this.queryFormItemsProps,
        queryFormRoles: this.queryFormRolesProps,
        queryData: this.queryDataProps,
        queryDataBak: {},

        saveFormData: this.saveFormDataProps,
        saveFormRules: this.saveFormRolesProps,
        saveFormItems: this.saveFormItemsProps,
        saveFormItemsBak: this.saveFormItemsBakProps,
        printConfig: this.printConfigProps,
        importConfig: this.importConfigProps,
        exportConfig: this.exportConfigProps,
        toolbarConfig: this.toolbarConfigProps,
      }
    },
    methods: {
      queryFormEvent(e) {
        // console.log(e)
        this.queryData = this.queryFormConfig.data
        console.log(this.queryData)
        // console.log(this.queryFormConfig)
        this.loading = true
        if (!this.queryData.start) {
          this.queryData.start = this.tablePage.currentPage
        }
        if (!this.queryData.limit) {
          this.queryData.limit = this.tablePage.pageSize
        }
        this.$api.post(this.apiPre + '/' + this.thisName + '/selectSimple', this.queryData)
          .then(r => {
            var res = r.data.results;
            // console.log('r:' + JSON.stringify(r))
            this.tablePage.total = r.data.total
            // console.log('res:' + JSON.stringify(res))
            // 使用函数式加载，阻断 vue 对大数据的监听
            const xTable = this.$refs.ZtVxeGrid
            // console.log('res:')
            // console.log(res)
            const startTime = Date.now()
            if (xTable) {
              this.$refs.ZtVxeGrid.reloadData(res).then(() => {
                _this.$XModal.message({
                  message: `渲染 ${res.length} 行，用时 ${Date.now() - startTime}毫秒`,
                  status: 'info'
                })
              })
            }

            this.tableData = r.data.results
            // console.log('tableData:' + JSON.stringify(this.tableData))
          })
          .finally(r => {
            this.loading = false
          });
      },
      resetEvent() {
        // this.queryData={}
        // this.queryData=this.deepClone(this.queryDataBak)
        // const $grid = this.$refs.ZtVxeGrid
        // $grid.reloadData(this.tableData)
        // console.log(this.$refs.ZtVxeQueryForm.data)
      },
      queryFormToggleEvent(collapse, data, $event) {},
      handlePageChange({
        currentPage,
        pageSize
      }) {
        this.tablePage.currentPage = currentPage
        this.tablePage.pageSize = pageSize
        this.queryData.start = this.tablePage.currentPage
        this.queryData.limit = this.tablePage.pageSize
        this.queryFormEvent()
      },

      queryEvent(queryData) {
        this.loading = true
        this.queryData = queryData
        if (!this.queryData.start) {
          this.queryData.start = this.tablePage.currentPage
        }
        if (!this.queryData.limit) {
          this.queryData.limit = this.tablePage.pageSize
        }
        console.log('queryEvent:' + JSON.stringify(this.queryData))
        this.$api.post(this.apiPre + '/' + this.thisName + '/selectSimple', this.queryData)
          .then(r => {
            var res = r.data.results;
            // console.log('r:' + JSON.stringify(r))
            this.tablePage.total = r.data.total
            // console.log('res:' + JSON.stringify(res))
            // 使用函数式加载，阻断 vue 对大数据的监听
            const xTable = this.$refs.ZtVxeGrid
            // console.log('res:')
            // console.log(res)
            const startTime = Date.now()
            if (xTable) {
              this.$refs.ZtVxeGrid.reloadData(res).then(() => {
                _this.$XModal.message({
                  message: `渲染 ${res.length} 行，用时 ${Date.now() - startTime}毫秒`,
                  status: 'info'
                })
              })
            }

            this.tableData = r.data.results
            // console.log('tableData:' + JSON.stringify(this.tableData))
          })
          .finally(r => {
            this.loading = false
          });
        // this.$api.post(this.apiPre + '/' + this.thisName + '/selectSimple', queryData, r => {
        //   console.log('r:'+JSON.stringify(r))
        //   // 使用函数式加载，阻断 vue 对大数据的监听
        //   const xTable = this.$refs.ZtVxeGrid
        //   const startTime = Date.now()
        //   if (xTable) {
        //     this.$refs.ZtVxeGrid.reloadData(r.data).then(() => {
        //       _this.$XModal.message({
        //         message: `渲染 ${r.data.records.length} 行，用时 ${Date.now() - startTime}毫秒`,
        //         status: 'info'
        //       })
        //     })
        //   }

        //   this.tableData = r.data
        //   console.log('tableData:'+this.tableData)
        // })
      },

      // findColumn() {
      //   var column = {}
      //   column.table = this.thisName
      //   this.$api.post(this.apiPre + '/ztColumnInfoConf/select', column, r => {
      //     // this.tableColumn = this.deepClone(r.data.records)
      //     this.saveFormItems = this.deepClone(r.data.records)

      //     _this.$emit('setSaveFormItemsBak', this.saveFormItems, (res) => {
      //       if (res) {
      //         this.saveFormItems = this.deepClone(res)
      //       }
      //       this.saveFormItems.push({
      //         align: 'center',
      //         span: 24,
      //         titleAlign: 'left',
      //         collapseNode: true,
      //         itemRender: {
      //           name: '$buttons',
      //           children: [{
      //             props: {
      //               type: 'submit',
      //               content: '保存',
      //               status: 'primary'
      //             }
      //           }, {
      //             props: {
      //               disabled: true,
      //               type: 'reset',
      //               content: '重置'
      //             }
      //           }]
      //         }
      //       })

      //       this.saveFormItemsBak = this.deepClone(this.saveFormItems)
      //       this.tableColumn = this.deepClone(this.saveFormItemsBak)
      //     })

      //     // this.$nextTick(() => {
      //     //   this.$api.get(this.apiPre + '/ztButtonInfo/getenuminfo?enumName=ZtTestStrEnum2', null, r => {
      //     //     let ztEnum = this.saveFormItems.filter(function(cur, index, arr) {
      //     //       return cur.field == 'ztEnum1';
      //     //     })
      //     //     ztEnum[0].itemRender.options = r.data
      //     //     this.saveFormItemsBak = this.deepClone(this.saveFormItems)
      //     //   })
      //     // })

      //     var querydata = {}
      //     this.tableColumn.map(item => {
      //       if (item.queryField) {
      //         querydata[`${item.field}`] = null
      //         this.queryFormConfig.items.push(item)
      //       }
      //       if (item.required) {
      //         this.saveFormRules[item.field] = [{
      //           required: true,
      //           message: `请填写${item.title}`
      //         }]
      //       }
      //       if (item.validator) {
      //         if (this.saveFormRules[item.field]) {
      //           this.saveFormRules[item.field].push({
      //             pattern: new RegExp(item.validator),
      //             message: item.validMsg || `${item.title}校验不通过，请重新填写`
      //           })
      //         } else {
      //           this.saveFormRules[item.field] = [{
      //             pattern: new RegExp(item.validator),
      //             message: item.validMsg || `${item.title}校验不通过，请重新填写`
      //           }]
      //         }
      //       }
      //     })
      //     this.tableColumn.push({
      //       align: 'center',
      //       title: '操作',
      //       width: 200,
      //       slots: {
      //         default: 'operate'
      //       }
      //     })
      //     this.queryFormConfig.items.push({
      //       span: 24,
      //       align: 'center',
      //       collapseNode: true,
      //       itemRender: {
      //         name: '$buttons',
      //         children: [{
      //           props: {
      //             type: 'submit',
      //             content: 'app.body.label.search',
      //             status: 'primary'
      //           }
      //         }, {
      //           props: {
      //             type: 'reset',
      //             content: 'app.body.label.reset'
      //           }
      //         }]
      //       }
      //     })
      //     this.$nextTick(() => {
      //       this.queryFormConfig.data = querydata
      //       this.queryFormEvent()
      //     })
      //   })

      // },

      editButtonEvent(row) {
        this.editEvent(row)
      },
      editEvent(row) {
        this.saveFormItems = this.deepClone(this.saveFormItemsBak)
        // console.log(this.saveFormItems)
        this.saveFormItems.forEach(t => {
          t.folding = false
        })

        if (row.id) {
          this.saveFormData = row
          let editDisabled = this.saveFormItems.filter(function(cur, index, arr) {
            return cur.editDisabled == true;
          })
          editDisabled.forEach(t => {
            if (!t.itemRender) {
              t.itemRender = {}
            }
            if (!t.itemRender.props) {
              t.itemRender.props = {}
            }
            t.itemRender.props.disabled = true
          })

          let editReadonly = this.saveFormItems.filter(function(cur, index, arr) {
            return cur.editReadonly == true;
          })
          editReadonly.forEach(t => {
            if (!t.itemRender) {
              t.itemRender = {}
            }
            if (!t.itemRender.props) {
              t.itemRender.props = {}
            }
            t.itemRender.props.readonly = true
          })
        } else {
          _this.$emit('resetSaveFormData', this.saveFormData)
          this.saveFormData = this.saveFormDataProps
          let addDisabled = this.saveFormItems.filter(function(cur, index, arr) {
            return cur.addDisabled == true;
          })
          addDisabled.forEach(t => {
            if (!t.itemRender) {
              t.itemRender = {}
            }
            if (!t.itemRender.props) {
              t.itemRender.props = {}
            }
            t.itemRender.props.disabled = true
          })

          let addReadonly = this.saveFormItems.filter(function(cur, index, arr) {
            return cur.addReadonly == true;
          })
          addReadonly.forEach(t => {
            if (!t.itemRender) {
              t.itemRender = {}
            }
            if (!t.itemRender.props) {
              t.itemRender.props = {}
            }
            t.itemRender.props.readonly = true
          })
        }
        this.showSaveForm = true
      },

      saveButtonEvent() {
        this.submitLoading = true
        setTimeout(() => {
          this.submitLoading = false
          this.showSaveForm = false
          if (this.saveFormData.id) {
            console.log('编辑')
            this.$api.post(this.apiPre + '/' + this.thisName + '/updateSimple', this.saveFormData, r => {})
          } else {
            console.log('新增')
            this.$api.post(this.apiPre + '/' + this.thisName + '/insertSimple', this.saveFormData, r => {})
          }
          console.log(this.saveFormData)
          _this.$emit('resetSaveFormData', this.saveFormData)
          this.saveFormData = this.saveFormDataProps
        }, 500)
      },
      toolbarButtonClickEvent({
        code
      }) {
        switch (code) {
          case 'myInsert':
            this.$refs.ZtVxeGrid.insert({
              remark: 'xxx'
            })
            break
          case 'other1':
            this.$XModal.message({
              message: `other1`,
              status: 'success'
            })
            break
          case 'other2':
            this.$XModal.message({
              message: `other2`,
              status: 'success'
            })
            break
          case 'other3':
            this.$XModal.message({
              message: `other3`,
              status: 'success'
            })
            break
          case 'ADD':
            this.editButtonEvent({})
            break
          case 'saveImport':
            setTimeout(() => {
              const {
                insertRecords,
                removeRecords,
                updateRecords
              } = this.$refs.ZtVxeGrid.getRecordset()
              this.$XModal.message({
                message: `新增 ${insertRecords.length} 条，删除 ${removeRecords.length} 条，更新 ${updateRecords.length} 条`,
                status: 'success'
              })
            }, 100)
            break
          case 'myExport':
            this.$refs.ZtVxeGrid.openExport()
            // this.$refs.ZtVxeGrid.exportData({
            //   type: 'csv'
            // })
            break
        }
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
      hasPermi(per) {
        // console.log('per:' + per)
        const permissions = store.getters && store.getters.permissions
        // console.log('permissions:' + permissions)
        return permissions.includes(per)
      },
      removeButtonEvent(row) {
        this.$api.post(this.apiPre + '/' + this.thisName + '/deleteSimple', row, r => {})
      }
    },
    mounted() {
      // this.$store.dispatch("SetPermi")
      console.log('mounted')
      this.showAdd = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_ADD)
      this.showQuery = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_QUERY)
      this.showEdit = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_EDIT)
      this.showRemove = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_DEL)
      this.showApp = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_APP)
      this.showPrint = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_PRINT)
      this.showImport = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_IMPORT)
      this.showExport = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_EXPORT)

      // this.findColumn()
      this.$nextTick(() => {
        var buttons = _this.$refs.ZtVxeGrid.toolbarConfig.buttons;
        let add = buttons.filter(function(cur, index, arr) {
          return cur.code == PermissionDefine.BUTTON_ADD;
        })
        add[0].visible = this.showAdd
        add[0].disabled = !this.showAdd
        add[0].status = 'primary'

        // let saveImport = buttons.filter(function(cur, index, arr) {
        //   return cur.code == 'saveImport';
        // })
        // saveImport[0].visible = this.showImport
        // saveImport[0].disabled = !this.showImport

        // _this.$refs.ZtVxeGrid.toolbarConfig.import = this.showImport
        // _this.$refs.ZtVxeGrid.toolbarConfig.export = this.showExport
        // _this.$refs.ZtVxeGrid.toolbarConfig.print = this.showPrint
      })

      // this.$forceUpdate();
      // this.queryFormEvent()
      // _this.tableHeight = document.body.clientHeight
      // console.log(document.body.clientWidth)
      // console.log(document.body.clientHeight)
      // console.log(_this.$refs.ZtVxeGrid.height)
      // console.log(this.queryData)
      // _this.$refs.ZtVxeGrid.height = document.body.clientHeight

      // var item = [{
      //   span: 24,
      //   align: 'center',
      //   collapseNode: true,
      //   itemRender: {
      //     name: '$buttons',
      //     children: [{
      //       props: {
      //         type: 'submit',
      //         content: '查询',
      //         status: 'primary'
      //       }
      //     }, {
      //       props: {
      //         type: 'reset',
      //         content: '重置'
      //       }
      //     }]
      //   }
      // }]

      // item.forEach(t => {
      //   this.queryFormItems.push(t)
      // })

      var commonButton = {
        span: 24,
        align: 'center',
        collapseNode: true,
        itemRender: {
          name: '$buttons',
          children: [{
            props: {
              type: 'submit',
              content: '查询',
              status: 'primary'
            }
          }, {
            props: {
              type: 'reset',
              content: '重置'
            }
          }]
        }
      }

      this.queryFormConfig.items.push(commonButton)

      // this.queryDataBak = this.deepClone(this.queryData)
    },
    created() {
      console.log('created')
      _this = this
      // console.log(PermissionDefine)

    },
    computed: {
      scrollerHeight: function() {
        return (document.body.clientHeight - 150) + 'px';
      }
    }
  }
</script>
