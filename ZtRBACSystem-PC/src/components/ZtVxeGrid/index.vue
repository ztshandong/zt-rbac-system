<template>
  <div>
    <vxe-grid border resizable show-overflow showHeaderOverflow highlightHoverRow keep-source height="800"
      ref="ZtVxeGrid" row-id="id" :data="tableData" :columns="tableColumn" :toolbar-config="toolbarConfig"
      :form-config="queryFormConfig" :import-config="importConfig" :export-config="exportConfig"
      :print-config="printConfig" @toolbar-button-click="toolbarButtonClickEvent" @form-submit="queryFormEvent"
      @form-toggle-collapse="queryFormToggleEvent">
      <template v-slot:operate="{ row }">
        <vxe-button icon="fa fa-edit" title="编辑" v-if="showEdit" :disabled="!showEdit" @click="editButtonEvent(row)">编辑
        </vxe-button>
        <vxe-button icon="fa fa-edit" title="删除" status="danger" v-if="showRemove" :disabled="!showRemove"
          @click="removeButtonEvent(row)">删除</vxe-button>
      </template>
    </vxe-grid>
    <vxe-modal ref="ZtVxeModal" v-model="showSaveForm" :title="saveFormData ? '编辑&保存' : '新增&保存'" width="800"
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
  export default {
    name: 'ZtVxeGrid',
    props: {
      apiPre: {
        // api请求前缀，对应不同微服务的统一前缀
        type: String,
        default: "",
      },
      thisName: {
        // 当前界面请求路径
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
              visible: false,
              // disabled: !this.showAdd,
              code: 'add',
              name: 'app.body.button.add',
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
        }),
      },
    },
    data() {
      return {
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
        saveFormData: this.saveFormDataProps,
        saveFormRules: this.saveFormRolesProps,
        saveFormItems: this.saveFormItemsProps,
        saveFormItemsBak: this.saveFormItemsBakProps,
        queryFormConfig: this.queryFormConfigProps,
        printConfig: this.printConfigProps,
        importConfig: this.importConfigProps,
        exportConfig: this.exportConfigProps,
        toolbarConfig: this.toolbarConfigProps
      }
    },
    methods: {

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
      queryFormEvent() {
        console.log(this.queryFormConfig.data)
        this.$api.post(this.apiPre + '/' + this.thisName + '/select', this.queryFormConfig.data, r => {
          // 使用函数式加载，阻断 vue 对大数据的监听
          const xTable = this.$refs.ZtVxeGrid
          const startTime = Date.now()
          if (xTable) {
            this.$refs.ZtVxeGrid.reloadData(r.data.records).then(() => {
              _this.$XModal.message({
                message: `渲染 ${r.data.records.length} 行，用时 ${Date.now() - startTime}毫秒`,
                status: 'info'
              })
            })
          }

          this.tableData = r.data.records
        })
      },
      queryFormToggleEvent(collapse, data, $event) {},
      saveButtonEvent() {
        this.submitLoading = true
        setTimeout(() => {
          this.submitLoading = false
          this.showSaveForm = false
          if (this.saveFormData.id) {
            console.log('编辑')
            this.$api.post(this.apiPre + '/' + this.thisName + '/update', this.saveFormData, r => {})
          } else {
            console.log('新增')
            this.$api.post(this.apiPre + '/' + this.thisName + '/insert', this.saveFormData, r => {})
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
          case 'add':
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
        this.$api.post(this.apiPre + '/' + this.thisName + '/delete', row, r => {})
      }
    },
    mounted() {
      // this.$store.dispatch("SetPermi")
      console.log('mounted')
      _this.showAdd = this.hasPermi('add')
      this.showQuery = this.hasPermi('query')
      this.showEdit = this.hasPermi('edit')
      this.showRemove = this.hasPermi('remove')
      this.showApp = this.hasPermi('app')
      this.showPrint = this.hasPermi('print')
      this.showImport = this.hasPermi('import')
      this.showExport = this.hasPermi('export')

      // this.findColumn()
      this.$nextTick(() => {
        // var buttons = _this.$refs.ZtVxeGrid.toolbarConfig.buttons;
        // let add = buttons.filter(function(cur, index, arr) {
        //   return cur.code == 'add';
        // })
        // add[0].visible = this.hasPermi('add')
        // add[0].disabled = !this.hasPermi('add')
        // add[0].status = 'primary'

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
    },
    created() {
      console.log('created')
      _this = this
    }
  }
</script>
