<template>
  <div class="app-container">
    <zt-vxe-grid ref="ztVxeGrid" :apiPre="apiPre" :thisName="thisName" :tableColumnProps="tableColumn"
      :queryFormConfigProps="queryFormConfig" :saveFormConfigProps="saveFormConfig" @showEditForm="showEditForm">
    </zt-vxe-grid>
  </div>
</template>

<script>
  var _this;

  import {
    listDept,
    getDept,
    delDept,
    addDept,
    updateDept,
    listDeptExcludeChild
  } from "@/api/system/dept";
  import Item from '@/layout/components/Sidebar/Item.vue'
  // import ZtVxeGrid from '@/components/ZtVxeGrid';
  export default {
    components: {
      Item
    },
    name: "DEPT_MANAGE",
    data() {
      return {
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
            field: 'thisCode',
            resetValue: null,
            title: '部门编号',
            span: 12,
            folding: false,
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
          }, {
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
          },
        ],
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
          },
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
      // this.getDicts("sys_normal_disable").then(response => {
      //   this.statusOptions = response.data;
      // });
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
                t.itemRender.props.options = r.data;
                return
              }
            })
            this.saveFormConfig.items.forEach(t => {
              if (t.field == 'parentCode') {
                t.itemRender.props.options = r.data;
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
      /** 转换部门数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.deptId,
          label: node.deptName,
          children: node.children
        };
      },
      // 字典状态字典翻译
      statusFormat(row, column) {
        return this.selectDictLabel(this.statusOptions, row.status);
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          deptId: undefined,
          parentId: undefined,
          deptName: undefined,
          orderNum: undefined,
          leader: undefined,
          phone: undefined,
          email: undefined,
          status: "0"
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 新增按钮操作 */
      handleAdd(row) {
        this.reset();
        if (row != undefined) {
          this.form.parentId = row.deptId;
        }
        this.open = true;
        this.title = "添加部门";
        listDept().then(response => {
          this.deptOptions = this.handleTree(response.data, "deptId");
        });
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        getDept(row.deptId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改部门";
        });
        listDeptExcludeChild(row.deptId).then(response => {
          this.deptOptions = this.handleTree(response.data, "deptId");
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.deptId != undefined) {
              updateDept(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addDept(this.form).then(response => {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        this.$confirm('是否确认删除名称为"' + row.deptName + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDept(row.deptId);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },

    }
  };
</script>

<style>
  .ali {
    font-size: 20px;
  }
</style>
