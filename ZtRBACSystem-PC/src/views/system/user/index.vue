<template>
  <div class="app-container">
    <!-- @currentChange="currentChange" @cellClick="cellClick" -->
    <zt-vxe-grid ref="ztVxeGrid" :apiPre="apiPre" :thisName="thisName" :tableColumnProps="tableColumn"
      :queryFormConfigProps="queryFormConfig" :saveFormConfigProps="saveFormConfig" >
    </zt-vxe-grid>
    <el-transfer v-model="transferValue" :props="transferProps" :data="transferData" filterable
      :titles="['所有部门', '用户部门']">
    </el-transfer>
    <vxe-button icon="fa fa-edit" title="保存" @click="saveTransferValue">
      保存
    </vxe-button>
  </div>
</template>

<script>
  var _this;
  import {
    getToken,
    setToken
  } from '@/utils/auth'
  export default {
    name: "USER_MANAGE",
    data() {

      const checkPhone = (
        item
      ) => {
        console.log(item.itemValue)
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        console.log(reg.test(item.itemValue));
        if (!reg.test(item.itemValue)) {
          return new Error('手机号输入不正确')
        }
      }

      return {
        transferProps: {
          key: 'thisCode',
          label: 'thisName',
        },
        transferData: [],
        transferValue: [],
        apiPre: "",
        thisName: "ZtUserInfo",
        thisData: {
          id: null,
          userCode: null,
          username: null,
          nickname: null,
          password: null,
          mobile: null,
          avatar: null,
          adminFlag: null,
          blockFlag: null,
          defaultDeptCode: null,
          defaultPostCode: null,
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
          field: 'username',
          resetValue: null,
          title: '用户名称',
          span: 12,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              placeholder: '请输入用户名称',
              clearable: true,
            },
          }
        }, {
          index: 20,
          field: 'userCode',
          resetValue: null,
          title: '用户编号',
          span: 12,
          folding: false,
          titlePrefix: {
            icon: 'fa fa-address-card-o',
            message: '用户编号一旦保存不可修改'
          },
          itemRender: {
            name: '$input',
            props: {
              placeholder: '请输入用户编号',
              clearable: true,
              disabled: false
            },
          }
        }, {
          index: 30,
          field: 'adminFlag',
          resetValue: null,
          title: '是否管理员',
          span: 24,
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
          field: 'nickname',
          resetValue: null,
          title: '用户昵称',
          span: 8,
          folding: false,
          titlePrefix: {
            icon: 'fa fa-address-card-o',
          },
          itemRender: {
            name: '$input',
            props: {
              placeholder: '请输入用户昵称',
              clearable: true,
              disabled: false
            },
          }
        }, {
          index: 120,
          field: 'mobile',
          resetValue: null,
          title: '用户手机',
          span: 8,
          folding: false,
          titlePrefix: {
            icon: 'fa fa-address-card-o',
          },
          itemRender: {
            name: '$input',
            props: {
              placeholder: '请输入用户手机',
              clearable: true,
              disabled: false
            },
          }
        }, ],
        thisQueryItem: [],
        thisSaveItem: [{
          index: 105,
          alicon: 'excel',
          textFirst: true,
          field: 'password',
          resetValue: null,
          title: '用户密码',
          span: 12,
          folding: false,
          itemRender: {
            name: '$input',
            props: {
              type: 'password',
              placeholder: '请输入用户密码',
              clearable: true,
            },
          }
        }],
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
          mobile: [{
              required: true,
              message: '请输入手机号',
              validator: checkPhone
            },
            {
              min: 11,
              max: 11,
              message: '长度不对'
            }
          ],
        },
        tableColumn: [{
            field: 'id',
            title: 'id',
            visible: false
          },
          {
            field: 'userCode',
            title: '用户编号',
            titleHelp: "{message: '自定义图标', icon: 'fa fa-bell'}"
          },
          {
            field: 'adminFlag',
            title: '是否管理员',
            span: 24,
            folding: false,
            cellRender: {
              name: '$switch',
              props: {
                openIcon: "fa fa-bell",
                closeIcon: "fa fa-bell-slash",
                openValue: true,
                closeValue: false,
                disabled: true,
              },
            }
          },
          {
            field: 'username',
            title: '用户名称',
          },
          {
            field: 'remark',
            title: '备注'
          }
        ],
        curRow: {}
      };
    },
    watch: {

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

      this.$api.post('/ZtDeptInfo/selectSimple', null)
        .then(r => {
          this.transferData = r.data.results;
        })
    },
    mounted() {
      console.log('_this mounted')
    },
    methods: {
      formResetEvent(e) {

      },
      afterQuery() {

      },
      showEditForm(row, items){

      },
      customToolbarButton(code){

      },
      saveTransferValue() {
        // let user = this.getUserInfo()
        // console.log(this.transferValue)
        let userDeptInfo = []
        this.transferValue.forEach(e => {
          let tmp = {
            userCode: this.curRow.userCode,
            deptCode: e,
            otherParams: 'USER_MANAGE'
          }
          userDeptInfo.push(tmp)
        })
        this.$api.post('/ZtUserDeptInfo/insertBatchSimple', userDeptInfo)
          .then(r => {
            this.$refs.ztVxeGrid.$emit("queryFormEvent")
          })
      },
      currentChange(newValue) {
        // console.log(newValue.row)
        this.curRow = newValue.row
        // console.log('user')
        // console.log(this.curRow)
        let user = {
          userCode: newValue.row.userCode
        }
        // console.log(user)
        this.$api.post('/ZtUserDeptInfo/selectSimple', user)
          .then(r => {
            let deptCodes = r.data.results.map(item => {
              return item.deptCode;
            })
            this.transferValue = deptCodes
          })
      },
      cellClick(data) {
        // console.log(data.column)
        // console.log(data.column.title)
        this.tableColumn.forEach(t => {
          if (t.title == data.column.title) {
            // console.log(t.field)
            // console.log(data.row[`${t.field}`])
            return
          }
        })
      }
    }
  };
</script>
