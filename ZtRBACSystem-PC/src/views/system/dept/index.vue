<template>
  <div class="app-container">
    <item icon="word2" title="xx" />
    <vxe-input v-model="thisName" placeholder="请输入名称"></vxe-input>
    <vxe-form :data="saveFormData">
      <vxe-form-item title="app.body.label.name" field="name" :item-render="{}">
        <template #default="{ data }">
          <!-- <item icon="word2" title="xx" /> -->
          <svg class="icon ali" aria-hidden="true">
            <use xlink:href="#icon-word2"></use>
          </svg>
          <vxe-input v-model="data.name" placeholder="请输入名称"></vxe-input>
        </template>
      </vxe-form-item>
    </vxe-form>

    <zt-vxe-grid ref="ztVxeGrid" :apiPre="apiPre" :thisName="thisName" :tableColumnProps="tableColumn"
      :queryFormConfigProps="queryFormConfig" :saveFormDataProps="saveFormData" :saveFormItemsProps="saveFormItems"
      :saveFormRolesProps="saveFormRoles" @showEditForm="showEditForm">
    </zt-vxe-grid>

    <!-- https://www.vue-treeselect.cn/ -->
    <!-- <treeselect :multiple="true" :options="deptOptions" placeholder="xx" v-model="thisData.thisCode" /> -->
    <!--
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch">
      <el-form-item label="部门名称" prop="deptName">
        <el-input v-model="queryParams.deptName" placeholder="请输入部门名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="部门状态" clearable size="small">
          <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel"
            :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="false" :data="deptList" row-key="deptId" default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column prop="deptName" label="部门名称" width="260"></el-table-column>
      <el-table-column prop="orderNum" label="排序" width="200"></el-table-column>
      <el-table-column prop="status" label="状态" :formatter="statusFormat" width="100"></el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)">新增</el-button>
          <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
            @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
 -->
    <!--
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item label="上级部门" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer"
                placeholder="选择上级部门" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门名称" prop="deptName">
              <el-input v-model="form.deptName" placeholder="请输入部门名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="负责人" prop="leader">
              <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictValue">{{dict.dictLabel}}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    -->
  </div>
</template>

<script>
  var _this;

  import Vue from 'vue'
  import VXETable from "vxe-table";
  VXETable.renderer.add('FormItemInput', {
            // 项内容模板
            renderItemContent (h, renderOpts, params) {
              // console.log(h)
              // console.log(renderOpts)
              // console.log(params)
              const { data, property } = params
              const props = renderOpts.props || {}

              // return [<template><svg class="icon ali" aria-hidden="true"><use xlink:href="#icon-word2"></use></svg><vxe-input v-model={ data[property] } { ...{ props } }></vxe-input></template>]
              const vnodes = []
// return [<svg class="icon ali" aria-hidden="false"><use xlink:href="#icon-word2"></use></svg>]
                vnodes.push(<svg-icon icon-class="workd2"/>)

                vnodes.push(<vxe-input v-model={ data[property] } { ...{ props } }></vxe-input>)

              return vnodes
              // return [
              //   <vxe-input v-model={ data[property] } { ...{ props } }></vxe-input>
              // ]
            }
          })

  import {
    listDept,
    getDept,
    delDept,
    addDept,
    updateDept,
    listDeptExcludeChild
  } from "@/api/system/dept";
  import Item from '@/layout/components/Sidebar/Item.vue'
  // import Treeselect from "@riophae/vue-treeselect";
  // import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  // import ZtVxeGrid from '@/components/ZtVxeGrid';
  export default {
    components: {
      VXETable,
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
        thisCommonItem: [{
          title: '基础信息',
          span: 24,
          titleAlign: "left",
          titleWidth: "200px",
          titlePrefix: {
            // icon: 'fa fa-address-card-o',
            // icon: '<svg class="icon" aria-hidden="true"><use xlink:href="#icon-word2"></use></svg>'
          },
          itemRender: {
            name: 'FormItemInput',
          }
        }, {
          field: 'thisName',
          resetValue: null,
          title: '部门名称',
          span: 12,
          folding: false,
          itemRender: {
            name: 'FormItemInput',
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
        }, ],
        thisQueryItem: [],
        thisSaveItem: [],
        queryFormConfig: {
          data: {},
          items: [],
          rules: {}
        },
        saveFormData: {},
        //新增编辑界面表单元素 created()里面赋值
        saveFormItems: [],
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

      this.getList();
      // this.getDicts("sys_normal_disable").then(response => {
      //   this.statusOptions = response.data;
      // });
    },
    computed: {
      isDisable: function() {
        return true;
      },
    },
    methods: {
      showEditForm(row, items) {
        console.log(row)
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
        this.loading = true;
        this.$api.post(this.apiPre + '/' + this.thisName + '/getAllDeptTree', null)
          .then(r => {
            this.queryFormConfig.items.forEach(t => {
              if (t.field == 'parentCode') {
                t.itemRender.props.options = r.data;
                return
              }
            })
            this.saveFormItems.forEach(t => {
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
    font-size: 24px;
  }
</style>
