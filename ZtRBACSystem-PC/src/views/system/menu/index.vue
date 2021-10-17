<template>
  <div class="app-container">
    <zt-vxe-grid ref="ztVxeGrid" :apiPre="apiPre" :thisName="thisName" :tableColumnProps="tableColumn"
      :queryFormConfigProps="queryFormConfig" :saveFormConfigProps="saveFormConfig" @showEditForm="showEditForm">
    </zt-vxe-grid>
  </div>
</template>

<script>
  var _this;
  export default {
    name: "COMPONENT_MANAGE",
    data() {
      return {
        apiPre: "",
        thisName: "ZtComponentInfo",
        thisData: {
          id: null,
          thisCode: null,
          thisName: null,
          parentCode: null,
          componentSort: null,
          componentRoute: null,
          componentPath: null,
          frameFlag: null,
          enableFlag: null,
          leafFlag: null,
          cacheFlag: null,
          menuType: null,
          buttonCode: null,
          iconUrl: null,
          resourceUrl: null,
          remark: null
        },
        thisCommonItem: [{
          index: 1,
          editOnly: true,
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
        }, {
          index: 10,
          alicon: 'excel',
          textFirst: true,
          field: 'thisName',
          resetValue: null,
          title: '组件名称',
          span: 8,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              placeholder: '请输入组件名称',
              clearable: true,
            },
          }
        }, {
          index: 20,
          field: 'thisCode',
          resetValue: null,
          title: '组件编号',
          span: 8,
          folding: false,
          titlePrefix: {
            icon: 'fa fa-address-card-o',
            message: '组件编号一旦保存不可修改'
          },
          itemRender: {
            name: '$input',
            props: {
              placeholder: '请输入组件编号',
              clearable: true,
              disabled: false
            },
          }
        }, {
          index: 30,
          field: 'parentCode',
          resetValue: null,
          title: '父组件',
          span: 8,
          folding: false,
          itemRender: {
            name: '$select',
            options: [],
            props: {
              multiple: false,
              placeholder: '父组件',
              clearable: true,
              transfer: true,
              disabled: false,
            }
          }
        }, {
          index: 40,
          field: 'leafFlag',
          resetValue: null,
          title: '是否叶子节点',
          span: 12,
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
          index: 50,
          field: 'frameFlag',
          resetValue: null,
          title: '是否外链',
          span: 12,
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
          index: 100,
          editOnly: true,
          title: '其他信息',
          span: 24,
          titleAlign: "left",
          titleWidth: "200px",
          titlePrefix: {
            icon: 'zhangtao icon-word2',
            message: '请填写其他项'
          }
        }, {
          index: 110,
          field: 'menuType',
          resetValue: null,
          title: '组件类型',
          span: 8,
          folding: false,
          itemRender: {
            name: '$select',
            options: [],
            props: {
              multiple: false,
              placeholder: '组件类型',
              clearable: true,
              transfer: true,
              disabled: false,
            },
            events: {
              change: this.menuTypeChange
            }
          }
        }, {
          index: 120,
          field: 'buttonCode',
          resetValue: null,
          title: '按钮编号',
          span: 8,
          folding: false,
          titlePrefix: {
            icon: 'fa fa-address-card-o',
          },
          itemRender: {
            name: '$select',
            options: [],
            props: {
              multiple: false,
              placeholder: '按钮编号',
              clearable: true,
              transfer: true,
              disabled: false,
            }
          }
        }, ],
        thisQueryItem: [],
        thisSaveItem: [],
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
        saveFormRoles: {},
        tableColumn: [{
            field: 'id',
            title: 'id',
            visible: false
          },
          {
            field: 'thisCode',
            title: '组件编号',
            titleHelp: "{message: '自定义图标', icon: 'fa fa-bell'}"
          },
          {
            field: 'thisName',
            title: '组件名称',
          },
          {
            field: 'parentCode',
            title: '父组件编号',
            showHeaderOverflow: true,
            // filters: this.ztRoleTypeEnum,
            filterMultiple: false,
            // formatter: this.formatterRoleTypeEnum,
            sortable: true,
            editRender: {
              name: '$select',
              options: [],
              multiple: true,
              props: {
                placeholder: '请选择父组件'
              }
            }
          },
          {
            field: 'menuType',
            title: '组件类型',
            showHeaderOverflow: true,
            // filters: this.ztRoleTypeEnum,
            filterMultiple: false,
            // formatter: this.formatterRoleTypeEnum,
            sortable: true,
            editRender: {
              name: '$select',
              options: [],
              multiple: true,
              props: {
                placeholder: '请选择组件类型'
              }
            }
          },
          {
            field: 'buttonCode',
            title: '按钮',
            showHeaderOverflow: true,
            // filters: this.ztRoleTypeEnum,
            filterMultiple: false,
            // formatter: this.formatterRoleTypeEnum,
            sortable: true,
            editRender: {
              name: '$select',
              options: [],
              multiple: true,
              props: {
                placeholder: '请选择按钮编号'
              }
            }
          },
          {
            field: 'remark',
            title: '备注'
          }
        ],
      };
    },
    watch: {

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

      this.$api.get(this.apiPre + '/' + this.thisName + '/getEnumInfo?enumName=ZtComponentTypeEnum', null)
        .then(r => {

          this.queryFormConfig.items.forEach(t => {
            if (t.field == 'menuType') {
              t.itemRender.options = r.data
              return
            }
          })

          this.saveFormConfig.items.forEach(t => {
            if (t.field == 'menuType') {
              t.itemRender.options = r.data
              return
            }
          })

          this.tableColumn.forEach(t => {
            if (t.field == 'menuType') {
              t.editRender.options = r.data
              return
            }
          })

        })

      this.$api.get(this.apiPre + '/' + this.thisName + '/getEnumInfo?enumName=ZtMenuCodeEnum', null)
        .then(r => {

          this.queryFormConfig.items.forEach(t => {
            if (t.field == 'parentCode') {
              t.itemRender.options = r.data
              return
            }
          })

          this.saveFormConfig.items.forEach(t => {
            if (t.field == 'parentCode') {
              t.itemRender.options = r.data
              return
            }
          })

          this.tableColumn.forEach(t => {
            if (t.field == 'parentCode') {
              t.editRender.options = r.data
              return
            }
          })

        })

      this.$api.get(this.apiPre + '/' + this.thisName + '/getEnumInfo?enumName=ZtButtonCodeEnum', null)
        .then(r => {

          this.queryFormConfig.items.forEach(t => {
            if (t.field == 'buttonCode') {
              t.itemRender.options = r.data
              return
            }
          })

          this.saveFormConfig.items.forEach(t => {
            if (t.field == 'buttonCode') {
              t.itemRender.options = r.data
              return
            }
          })

          this.tableColumn.forEach(t => {
            if (t.field == 'buttonCode') {
              t.editRender.options = r.data
              return
            }
          })

        })

    },
    mounted() {

    },
    methods: {
      showEditForm(row, items) {
        if (row.menuType == "BUTTON") {
          items.forEach(t => {
            if (t.field == 'buttonCode') {
              t.itemRender.props.disabled = false
              return
            }
          }) 
        } else {
          this.$refs.ztVxeGrid.saveFormData.buttonCode = null
          items.forEach(t => {
            if (t.field == 'buttonCode') {
              t.itemRender.props.disabled = true
              return
            }
          })
        }
      },
      menuTypeChange(value, $event) {
        if ($event.value == "BUTTON") {
          this.saveFormConfig.items.forEach(t => {
            if (t.field == 'buttonCode') {
              t.itemRender.props.disabled = false
              return
            }
          })
        } else {
          this.$refs.ztVxeGrid.saveFormData.buttonCode = null
          this.saveFormConfig.items.forEach(t => {
            if (t.field == 'buttonCode') {
              t.itemRender.props.disabled = true
              return
            }
          })
        }
      }
    },
    computed: {

    }
  };
</script>
