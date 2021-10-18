<template>
  <!-- :style="{'min-height':tableHeight+'px','max-height':tableHeight+'px','width':'100%'}" -->
  <!-- <div :style="{height: scrollerHeight+'px'}"> -->
  <el-scrollbar :height="scrollerHeight">
    <!-- <div height="800"> -->

    <!--
    第一种方式子类自行实现，此时@submit调用的是this.$refs.ztVxeGrid.queryEvent(this.queryData);传入的是对象
    <vxe-form :data="queryData" @submit="doQuery" @reset="resetEvent">  @submit.prevent="onSubmit"
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
      title-width="100" @submit="queryFormEvent" @toggle-collapse="queryFormToggleEvent" @reset="queryResetEvent"></vxe-form>
       -->


    <!-- queryFormConfig传全部的配置。通用组件里面添加查询与重制按钮，这样查询触发的就是@submit的queryFormEvent，入参是个事件，分页也要用这个 -->
    <!--
    :form-config="queryFormConfig" @form-submit="queryFormEvent" @form-reset="queryResetEvent"
    @form-toggle-collapse="queryFormToggleEvent"
    -->
    <!-- custom-layout 可以内置div，但是会导致彩色图标失效 -->
    <!-- <div :style="{'min-height':tableHeight+'px','max-height':tableHeight+'px','width':'100%'}"> -->
    <!-- ,'border': '1px dashed red' -->
    <!-- v-if="!config.editOnly" -->
    <vxe-form id="ZtVxeQueryForm" ref="ZtVxeQueryForm" :data="queryFormConfig.data" :rules="queryFormConfig.rules"
      @submit="queryFormEvent" @toggle-collapse="queryFormToggleEvent" @reset="queryResetEvent">
      <vxe-form-item v-for="config in queryFormConfig.items" v-if="!config.editOnly" :field="config.field"
        :key="config.field" :title="config.title" :span="config.span" :align="config.align"
        :titleAlign="config.titleAlign" :titleWidth="config.titleWidth" :titleOverflow="config.titleOverflow"
        :className="config.className" :visible="config.visible" :folding="config.folding"
        :collapseNode="config.collapseNode" :titlePrefix="config.titlePrefix" :titleSuffix="config.titleSuffix"
        :resetValue="config.resetValue" :itemRender="config.itemRender" :visibleMethod="config.visibleMethod"
        :children="config.children">
        <!--
        <div v-if="config.isDiv" :style="config.style">
          <vxe-form-item v-for="config2 in config.items" :field="config2.field" :title="config2.title"
            :span="config2.span" :align="config2.align" :titleAlign="config2.titleAlign"
            :titleWidth="config2.titleWidth" :titleOverflow="config2.titleOverflow" :className="config2.className"
            :visible="config2.visible" :folding="config2.folding" :collapseNode="config2.collapseNode"
            :titlePrefix="config2.titlePrefix" :titleSuffix="config2.titleSuffix" :resetValue="config2.resetValue"
            :itemRender="config2.itemRender" :visibleMethod="config2.visibleMethod">
            <template slot="title" v-if="config2.iconFirst">
                <svg class="icon ali" aria-hidden="true" v-if="config2.alicon">
                  <use v-bind:xlink:href="iconName(config2.alicon)"></use>
                </svg>
                <span slot='title'>{{config2.title}}</span>
            </template>

            <template slot="title" v-else-if="config2.textFirst">
                <span slot='title'>{{config2.title}}</span>
                <svg class="icon ali" aria-hidden="true" v-if="config2.alicon">
                  <use v-bind:xlink:href="iconName(config2.alicon)"></use>
                </svg>
            </template>
          </vxe-form-item>
        </div>
 -->
        <!-- 方式一 -->
        <!--
        <template slot="title">
          <item  icon="word2" :title="config.title" />
        </template>
        -->

        <!-- 方式二 -->
        <template slot="title" v-if="config.iconFirst">
          <!-- <svg-icon icon-class="excel"/> 这个也可以-->
          <svg class="icon ali" aria-hidden="true" v-if="config.alicon">
            <use v-bind:xlink:href="iconName(config.alicon)"></use>
          </svg>
          <span slot='title'>{{config.title}}</span>
        </template>

        <template slot="title" v-else-if="config.textFirst">
          <span slot='title'>{{config.title}}</span>
          <!-- <svg-icon icon-class="excel"/> 这个也可以-->
          <svg class="icon ali" aria-hidden="true" v-if="config.alicon">
            <use v-bind:xlink:href="iconName(config.alicon)"></use>
          </svg>
        </template>

      </vxe-form-item>
    </vxe-form>
    <!-- </div> -->

    <!-- <div :style="{height: tableHeight+'px'}"> -->
    <!-- <div :style="{'min-height':tableHeight+'px','max-height':tableHeight+'px','width':'100%'}"> -->
    <vxe-grid border stripe resizable show-overflow showHeaderOverflow highlightCurrentRow highlightHoverRow keep-source
      :show-header="true" :height="tableHeight" :loading="loading" id="ZtVxeGrid" ref="ZtVxeGrid" row-id="id"
      :data="tableData" :columns="tableColumn" :keyboard-config="keyboardConfig" :toolbar-config="toolbarConfig"
      :import-config="importConfig" :export-config="exportConfig"
      :seq-config="{startIndex: (tablePage.currentPage - 1) * tablePage.pageSize}" :print-config="printConfig"
      :tree-config="treeConfigProps" :checkbox-config="checkBoxConfigProps"
      @toolbar-button-click="toolbarButtonClickEvent" @toolbar-tool-click="toolbarButtonClickEvent"
      @current-change="currentChange" @cell-click="cellClick">

      <template #operate_default="{ row }">
        <vxe-button icon="fa fa-edit" title="编辑" @click="editButtonEvent(row,false)">
          查看
        </vxe-button>
        <vxe-button icon="fa fa-edit" title="编辑" v-if="showEdit" :disabled="!showEdit"
          @click="editButtonEvent(row,true)">
          编辑
        </vxe-button>
        <vxe-button icon="fa fa-edit" title="删除" status="danger" v-if="showRemove" :disabled="!showRemove"
          @click="removeButtonEvent(row)">删除</vxe-button>
        <vxe-button v-for="button in customGridSlotButton" :key="button.title" :icon="button.icon" :title="button.title"
          :status="button.status" v-if="button.show" @click="button.click(row)">{{button.title}}</vxe-button>
      </template>

      <!--
      这样会把toolbar的按钮挤乱，还要配合修改toolbarConfigProps
      <template #toolbar_buttons>
        <vxe-form ref="ZtVxeQueryForm" :data="queryData" :items="queryFormItems" :rules="queryFormRoles"
          title-align="right" title-width="100" @submit="queryFormEvent" @toggle-collapse="queryFormToggleEvent"
          @reset="resetEvent"></vxe-form>
      </template>
 -->
      <!--
      <template v-slot:operate="{ row }">
        <vxe-button icon="fa fa-edit" title="编辑" v-if="showEdit" :disabled="!showEdit" @click="editButtonEvent(row)">编辑
        </vxe-button>
        <vxe-button icon="fa fa-edit" title="删除" status="danger" v-if="showRemove" :disabled="!showRemove"
          @click="removeButtonEvent(row)">删除</vxe-button>
      </template>
      -->
      <!--
      <vxe-column title="操作" width="240">
        <template #default="{ row }">
          <vxe-button status="warning" content="临时删除" @click="removeRowEvent(row)"></vxe-button>
          <vxe-button status="danger" content="直接删除" @click="deleteRowEvent(row)"></vxe-button>
        </template>
      </vxe-column>
      -->
      <!--
      <template #edit="{ row }">
        <vxe-input type="text" v-model="row.role"></vxe-input>
      </template>
      -->
      <!--
      <template #default="{ row }">
        <vxe-button status="warning" content="临时删除" @click="removeRowEvent(row)"></vxe-button>
        <vxe-button status="danger" content="直接删除" @click="deleteRowEvent(row)"></vxe-button>
      </template>
 -->
      <template #pager>
        <vxe-pager :layouts="['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total']"
          :current-page.sync="tablePage.currentPage" :page-size.sync="tablePage.pageSize" :total="tablePage.total"
          :page-sizes="[1, 3, 5, 10, 15, 20, 50, 100, 200, 500, 1000]" @page-change="handlePageChange">
        </vxe-pager>
      </template>
    </vxe-grid>
    <!-- </div> -->

    <vxe-modal id="ZtVxeModal" ref="ZtVxeModal" v-model="showSaveForm" :title="saveFormData.id ? '编辑' : '新增'"
      width="100%" min-width="600" min-height="300" resize destroy-on-close :show-close="false">
      <!-- <vxe-form ref="ZtVxeSaveForm" :loading="submitLoading" :data="saveFormData" :items="saveFormItems"
        :rules="saveFormRules" title-align="right" title-width="100" @submit="saveButtonEvent"
        @toggle-collapse="queryFormToggleEvent"></vxe-form> -->

      <vxe-form id="ZtVxeSaveForm" ref="ZtVxeSaveForm" :data="saveFormData" :rules="saveFormConfig.rules"
        @submit="saveButtonEvent">
        <vxe-form-item v-for="config in saveFormConfig.items" v-if="!config.queryOnly" :field="config.field"
          :key="config.field" :title="config.title" :span="config.span" :align="config.align"
          :titleAlign="config.titleAlign" :titleWidth="config.titleWidth" :titleOverflow="config.titleOverflow"
          :className="config.className" :visible="config.visible" :folding="config.folding"
          :collapseNode="config.collapseNode" :titlePrefix="config.titlePrefix" :titleSuffix="config.titleSuffix"
          :resetValue="config.resetValue" :itemRender="config.itemRender" :visibleMethod="config.visibleMethod"
          :children="config.children">
          <template slot="title" v-if="config.iconFirst">
            <svg class="icon ali" aria-hidden="true" v-if="config.alicon">
              <use v-bind:xlink:href="iconName(config.alicon)"></use>
            </svg>
            <span slot='title'>{{config.title}}</span>
          </template>

          <template slot="title" v-else-if="config.textFirst">
            <span slot='title'>{{config.title}}</span>
            <svg class="icon ali" aria-hidden="true" v-if="config.alicon">
              <use v-bind:xlink:href="iconName(config.alicon)"></use>
            </svg>
          </template>
        </vxe-form-item>
      </vxe-form>

    </vxe-modal>
    <!-- </div> -->
  </el-scrollbar>
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
      customGridSlotButton: {
        type: Array,
        default: () => [],
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
      // queryFormItemsProps: {
      //   // 查询表单各个字段
      //   type: Array,
      //   default: () => [],
      // },
      // queryFormRolesProps: {
      //   // 校验规则
      //   type: Object,
      //   default: () => ({}),
      // },
      // queryDataProps: {
      //   // 查询条件具体数据
      //   type: Object,
      //   default: () => ({}),
      // },

      saveFormConfigProps: {
        // 查询条件配置
        type: Object,
        default: () => ({
          data: {},
          titleWidth: 100,
          titleAlign: 'right',
          items: []
        }),
      },
      // saveFormDataProps: {
      //   type: Object,
      //   default: () => ({}),
      // },
      // saveFormRolesProps: {
      //   type: Object,
      //   default: () => ({}),
      // },
      // saveFormItemsProps: {
      //   type: Array,
      //   default: () => [],
      // },
      // saveFormItemsBakProps: {
      //   // 弹框界面各个字段
      //   type: Array,
      //   default: () => [],
      // },
      tablePageProps: {
        //默认分页大小
        type: Object,
        default: () => ({
          total: 0,
          currentPage: 1,
          pageSize: 10
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
          type: 'xlsx',
          types: ['xlsx', 'pdf', 'csv', 'html', 'xml', 'txt'],
          modes: ['current', 'selected']
        }),
      },
      toolbarCustomButtonConfig: {
        type: Array,
        default: () => [],
      },
      treeConfigProps: {
        type: Object,
        default: () => null,
      },
      checkBoxConfigProps: {
        type: Object,
        default: () => ({}),
      },
      keyboardConfigProps: {
        type: Object,
        default: () => ({
          isArrow: true,
          isChecked: true,
          isEnter: true
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
          ],
          perfect: true,
          import: false,
          export: false,
          print: false,
          zoom: true,
          custom: true,
          tools: [{
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
              },
              {
                code: 'myExport',
                name: '导出3',
                type: 'text',
                disabled: false
              },

            ]
          }],
          // refresh: {
          //   // query: this.queryFormEvent(),
          //   icon: 'fa fa-refresh',
          //   iconLoading: 'fa fa-spinner fa-spin'
          // },
          // slots: {
          //   buttons: 'toolbar_buttons'
          // }
        }),
      },
    },
    data() {
      return {
        tableHeight: 100,
        loading: false,
        submitLoading: false,
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
        // queryFormItems: this.queryFormItemsProps,
        // queryFormRoles: this.queryFormRolesProps,
        queryData: {},
        // queryDataBak: {},

        initSaveFormData: {}, //初始化空对象
        saveFormConfig: this.saveFormConfigProps,
        disableSave: false,
        curRow: {},
        //对比，只传修改过的字段
        saveFormDataBak: {},
        saveFormData: {},
        // saveFormRules: this.saveFormRolesProps,
        // saveFormItems: this.saveFormItemsProps,
        // saveFormItemsBak: this.saveFormItemsBakProps,

        printConfig: this.printConfigProps,
        importConfig: this.importConfigProps,
        exportConfig: this.exportConfigProps,
        toolbarConfig: this.toolbarConfigProps,
        keyboardConfig: this.keyboardConfigProps,
      }
    },
    methods: {
      //父类要有的方法
      // formResetEvent(e) {},
      // afterQuery() {},
      // showEditForm(row, items){},
      // cellClick(data){},
      // currentChange(data){},
      // customToolbarButton(code){},

      cellClick(data, rowIndex, $rowIndex, column, columnIndex, $columnIndex, triggerRadio, triggerCheckbox,
        triggerTreeNode, triggerExpandNode, $event) {
        this.$parent.cellClick(data, rowIndex, $rowIndex, column, columnIndex, $columnIndex, triggerRadio,
          triggerCheckbox,
          triggerTreeNode, triggerExpandNode, $event)
      },
      currentChange(newValue, oldValue, row, rowIndex, $rowIndex, column, columnIndex, $columnIndex, $event) {
        // console.log(this.$parent)
        this.$parent.currentChange(newValue, oldValue, row, rowIndex, $rowIndex, column, columnIndex, $columnIndex,
          $event)
        // this.$emit('currentChange', newValue, oldValue, row, rowIndex, $rowIndex, column, columnIndex, $columnIndex, $event)
      },
      queryResetEvent(data, event) {
        this.$parent.formResetEvent(data)
      },
      //表单提交，分页也是这个
      queryFormEvent(data, event) {
        // console.log(data)
        // console.log(event)
        this.queryData = this.queryFormConfig.data
        // console.log(this.queryData)
        this.loading = true
        if (!this.queryData.start) {
          this.queryData.start = this.tablePage.currentPage
        }
        if (!this.queryData.limit) {
          this.queryData.limit = this.tablePage.pageSize
        }
        this.doQuery(this.queryData);
      },
      //其他组件传入查询参数
      queryEvent(queryData) {
        this.loading = true
        this.queryData = queryData
        if (!this.queryData.start) {
          this.queryData.start = this.tablePage.currentPage
        }
        if (!this.queryData.limit) {
          this.queryData.limit = this.tablePage.pageSize
        }
        // console.log('queryEvent:' + JSON.stringify(this.queryData))
        this.doQuery(this.queryData);
      },
      doQuery(queryData) {
        // console.log(queryData)
        if (!this.queryFormConfig.useSpecialQuery) {
          this.$api.post(this.apiPre + '/' + this.thisName + '/selectSimple', queryData)
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
              this.$nextTick(() => {
                // let data = _this.$refs.ZtVxeGrid.getTableData()
                // console.log(data)
                this.$parent.afterQuery()
              })
              // console.log('tableData:' + JSON.stringify(this.tableData))
            })
            .finally(r => {
              this.loading = false
            });
        } else {
          this.$parent.specialQuery(queryData, r => {
            // console.log('callback')
            // console.log(r)
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

                this.$nextTick(() => {
                  // let data = _this.$refs.ZtVxeGrid.getTableData()
                  // console.log(data)
                  this.$parent.afterQuery()
                })

              }).finally(t => {
                this.tableData = r.data.results
                this.loading = false
              })
            }

          })
        }
      },
      // resetEvent() {
      // },
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
      queryFormToggleEvent(collapse, data, $event) {
        // console.log(collapse)
        // console.log(data)
        // console.log($event)
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
          case PermissionDefine.BUTTON_ADD:
            this.editButtonEvent(this.deepClone(this.initSaveFormData), true)
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
          default:
            // console.log(code)
            this.$parent.customToolbarButton(code)
        }
      },
      editButtonEvent(row, canEdit) {
        this.editEvent(row, canEdit)
      },
      async removeButtonEvent(row) {
        const type = await this.$XModal.confirm('您确定要删除选中的数据?')
        if (type !== 'confirm') {
          return
        }
        console.log(row)
        // this.$api.post(this.apiPre + '/' + this.thisName + '/deleteSimple', row, r => {})
      },
      editEvent(row, canEdit) {
        if (canEdit) {
          this.saveFormConfig.items.forEach(t => {
            if (t.itemRender) {
              if (t.itemRender.props) {
                t.itemRender.props.disabled = false
              }
            }
          })
          this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[0].props.disabled = false
        } else {
          this.saveFormConfig.items.forEach(t => {
            if (t.itemRender) {
              if (t.itemRender.props) {
                t.itemRender.props.disabled = true
              }
            }
          })
          this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[0].props.disabled = true
        }
        // console.log(row)
        // console.log(canEdit)
        // console.log(this.initSaveFormData)
        // console.log(this.saveFormItems.length)

        this.$parent.showEditForm(row, this.saveFormConfig.items, canEdit)
        // console.log(this.saveFormItems)
        this.curRow = row
        // console.log(this.initSaveFormData)
        this.saveFormDataBak = this.deepClone(row)
        this.saveFormData = this.deepClone(row)
        this.showSaveForm = true
      },
      saveButtonEvent(e) {
        this.lockForm()
        // setTimeout(() => {
        if (this.saveFormData.id) {
          console.log('编辑')
          let saveData = {}
          let keys = Object.keys(this.saveFormData);
          keys.forEach(key => {
            let oriData = this.saveFormDataBak[`${key}`]
            let curData = this.saveFormData[`${key}`]
            if (JSON.stringify(curData) != JSON.stringify(oriData)) {
              saveData[`${key}`] = curData
            }
          })
          let keyLength = Object.keys(saveData).length
          if (keyLength <= 0) {
            this.$XModal.message({
              message: '没有修改数据，不需要保存',
              status: 'warn'
            })
            this.releaseForm()
            return
          }
          saveData.id = this.saveFormData.id
          // console.log(saveData)
          this.$api.post(this.apiPre + '/' + this.thisName + '/updateSimple', saveData).then(t => {
            this.queryFormEvent()
          }).finally(r => {
            this.releaseForm()
          });
        } else {
          console.log('新增')
          this.$api.post(this.apiPre + '/' + this.thisName + '/insertSimple', this.saveFormData).then(t => {
            this.queryFormEvent()
          }).finally(r => {
            this.releaseForm()
          });
        }
        // }, 2000)
        // _this.$emit('resetSaveFormData', this.saveFormData)
        // this.saveFormData = this.saveFormDataProps
      },
      lockForm() {
        this.submitLoading = true
        this.disableSave = true
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[0].props.disabled = true
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[0].props.loading = true
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[1].props.disabled = true
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[1].props.loading = true
      },
      releaseForm() {
        this.showSaveForm = false
        this.submitLoading = false
        this.disableSave = false
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[0].props.disabled = false
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[0].props.loading = false
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[1].props.disabled = false
        this.saveFormConfig.items[this.saveFormConfig.items.length - 1].itemRender.children[1].props.loading = false

        this.saveFormData = this.deepClone(this.initSaveFormData)
        this.saveFormDataBak = this.deepClone(this.initSaveFormData)
      },
      hasPermi(per) {
        // console.log('per:' + per)
        const permissions = store.getters && store.getters.permissions
        // console.log('permissions:' + permissions)
        return permissions.includes(per)
      },
      close(e) {
        this.showSaveForm = false
      },
      iconName(iconName) {
        let name = "#icon-"
        name = name + iconName
        return `${name}`
      },
      getTableHeight() {
        this.$nextTick(function() {
          // console.log(this.$refs.ZtVxeQueryForm)
          // let contentHeight = document.getElementsByClassName("vxemodal")[0].clientHeight; // tabs高度/
          let contentHeight = document.getElementById("ZtVxeQueryForm").clientHeight; // tabs高度 不稳定
          // console.log('contentHeight')
          // console.log(contentHeight)
          // console.log(this.scrollerHeight)

          // this.tableHeight = contentHeight == '0' ? '440' : (contentHeight - 55);
          // console.log(contentHeight+" -- contentHeight --  tableHeight --         "+this.tableHeight);
          this.tableHeight = this.scrollerHeight - contentHeight
          // console.log(this.tableHeight)
          // this.$refs.ZtVxeGrid.height = this.tableHeight
          // console.log(document.getElementById("ZtVxeGrid")) // tabs高度 不稳定
          // console.log(document.getElementById("ZtVxeModal")) // tabs高度 不稳定
          // console.log(document.getElementById("ZtVxeSaveForm")) // tabs高度 不稳定
          let grid = document.getElementsByClassName("vxe-grid")[0]; // tabs高度/
          // console.log(grid)
          // this.$refs.ZtVxeGrid.toolbarConfig.refresh.query = this.queryFormEvent()
        })
      },
      compareEx(property) {
        return function(a, b) {
          var value1 = a[property];
          var value2 = b[property];
          return value1 - value2;
        }
      }

    },
    mounted() {
      // this.$store.dispatch("SetPermi")
      console.log('common mounted')
      // console.log(this.queryFormConfig)
      this.showAdd = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_ADD) || this
        .thisPermissionPre == ""
      this.showQuery = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_QUERY) || this
        .thisPermissionPre == ""
      this.showEdit = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_EDIT) || this
        .thisPermissionPre == ""
      // console.log('this.showEdit' + this.showEdit)
      this.showRemove = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_DEL) || this
        .thisPermissionPre == ""
      this.showApp = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_APP) || this
        .thisPermissionPre == ""
      this.showPrint = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_PRINT) || this
        .thisPermissionPre == ""
      this.showImport = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_IMPORT) || this
        .thisPermissionPre == ""
      this.showExport = this.hasPermi(this.thisPermissionPre + ':' + PermissionDefine.BUTTON_EXPORT) || this
        .thisPermissionPre == ""

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

        _this.$refs.ZtVxeGrid.toolbarConfig.import = this.showImport
        _this.$refs.ZtVxeGrid.toolbarConfig.export = this.showExport
        _this.$refs.ZtVxeGrid.toolbarConfig.print = this.showPrint

        const remark = {
          index: 99999,
          field: 'remark',
          resetValue: null,
          title: '备注',
          span: 24,
          folding: true,
          itemRender: {
            name: '$textarea',
            props: {
              placeholder: '请输入备注',
              resize: "both"
            }
          }
        }

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
              },
            }, {
              props: {
                type: 'reset',
                content: '重置'
              }
            }]
          }
        }

        this.queryFormConfig.items.push(this.deepClone(remark))
        this.queryFormConfig.items.push(commonButton)

        // console.log(this.saveFormItems)

        // {
        //   props: {
        //     type: 'submit',
        //     content: '保存',
        //     status: 'primary'
        //   }
        // }

        this.saveFormConfig.items.push(this.deepClone(remark))

        if (!this.saveFormConfig.useSpecialSave) {
          this.saveFormConfig.items.push({
            align: 'center',
            span: 24,
            titleAlign: 'left',
            collapseNode: true,
            itemRender: {
              name: '$buttons',
              children: [{
                props: {
                  type: 'submit',
                  content: '保存',
                  status: 'primary',
                  disabled: this.disableSave,
                  loading: this.disableSave
                }
              }, {
                props: {
                  content: '取消'
                },
                events: {
                  click: this.close
                }
              }]
            }
          })
        }

        // this.queryDataBak = this.deepClone(this.queryData)
        this.tableColumn.push({
          field: 'operate',
          title: '操作',
          minWidth: 120,
          slots: {
            default: 'operate_default'
          }
        })
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

      this.initSaveFormData = this.deepClone(this.saveFormConfig.data)

      // console.log('this.$refs.ZtVxeQueryForm')
      // console.log(this.$refs)
      // console.log(this.$refs.ZtVxeQueryForm)

      this.getTableHeight()
      // setInterval(this.getTableHeight, 1000);

    },
    created() {
      console.log('common created')
      _this = this
      this.toolbarCustomButtonConfig.forEach(t => {
        this.toolbarConfigProps.buttons.push(t)
        // this.toolbarConfigProps.buttons.unshift(t)
      })

      this.queryFormConfig.items.sort(this.compareEx("index"))
      this.saveFormConfig.items.sort(this.compareEx("index"))
      // this.saveFormConfig.items

      // console.log(this.queryFormConfig)
      // console.log(PermissionDefine)

    },
    activated() {
      console.log('activated')
      console.log(this.thisName)
    },
    deactivated() {
      console.log('deactivated')
      console.log(this.thisName)
    },
    computed: {
      scrollerHeight: function() {
        console.log(document.body.clientHeight)
        return (document.body.clientHeight - 180); // + 'px'
      },
      // tableHeight: function(){
      //   let contentHeight = document.getElementById("ZtVxeQueryForm").clientHeight; // tabs高度 不稳定
      //   console.log(contentHeight)
      //   // this.tableHeight = contentHeight == '0' ? '440' : (contentHeight - 55);
      //   // console.log(contentHeight+" -- contentHeight --  tableHeight --         "+this.tableHeight);
      //   return ((document.body.clientHeight - 170) - contentHeight)+ 'px';
      // }

      // 方式二
      // iconName: function() {
      //   return function(iconName) {
      //     let name = "#icon-"
      //     name = name + iconName
      //     console.log(name)
      //     return `${name}`
      //   }
      // }
    }
  }
</script>
<style lang="scss">
  @import '@/assets/styles/zttable.scss';
</style>
