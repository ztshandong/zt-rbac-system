<template>
  <div>
    <vxe-grid border show-overflow keep-source height="530" :data="tableData" :columns="tableColumn">
      <template v-slot:operate="{ row }">
        <vxe-button icon="fa fa-edit" title="编辑" v-if="!showEdit" circle @click="cellDBLClickEvent(row)">编辑</vxe-button>
      </template>
    </vxe-grid>

    <vxe-button status="primary" title="新增" @click="cellDBLClickEvent">新增</vxe-button>

    <vxe-modal v-model="showEdit" :title="formData ? '编辑&保存' : '新增&保存'" width="800" min-width="600" min-height="300"
      resize destroy-on-close>
      <template v-slot>
        <vxe-form :data="formData" :items="formItems" :rules="formRules" title-align="right" title-width="100" @submit="submitEvent"></vxe-form>
      </template>
    </vxe-modal>

  </div>
</template>

<script>
  var _this;
  export default {
    name: 'DynamicColumn',
    data() {
      return {
        tableColumn: [],
        tableData: [],
        // selectRow: null,
        showEdit: false,
        formData: {
          remark: null
        },
        formRules: {},
        // formRules: {
        //   remark: [{
        //       required: true,
        //       message: '请输入备注'
        //     },
        //     {
        //       min: 3,
        //       max: 5,
        //       message: '长度在 3 到 5 个字符'
        //     },
        //     {
        //       pattern: new RegExp('^1[34578][0-9]{9}$'),
        //       message: `校验不通过，请重新填写`
        //     }
        //   ]
        // },
        formItems: [],
        // formItems: [{
        //   field: 'remark',
        //   title: '备注',
        //   span: 12,
        //   itemRender: {
        //     name: '$input',
        //     props: {
        //       placeholder: '请输入备注'
        //     }
        //   }
        // }, {
        //   align: 'center',
        //   span: 24,
        //   titleAlign: 'left',
        //   itemRender: {
        //     name: '$buttons',
        //     children: [{
        //       props: {
        //         type: 'submit',
        //         content: '提交',
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
      }
    },
    created() {
      this.findColumn()
    },
    methods: {
      findColumn() {
        this.$api.post('/usercenter/ztColumnInfoConf/select', null, r => {
          // console.log(r.data.records)
          this.tableColumn = this.deepClone(r.data.records)
          // this.tableColumn.forEach(t => {
          //   const xx = t
          //   xx.itemRender = t.editRender
          //   this.formItems.push(xx)
          // })
          this.formItems = this.deepClone(r.data.records)
          this.formItems.push({
            align: 'center',
            span: 24,
            titleAlign: 'left',
            itemRender: {
              name: '$buttons',
              children: [{
                props: {
                  type: 'submit',
                  content: '提交',
                  status: 'primary'
                }
              }, {
                props: {
                  type: 'reset',
                  content: '重置'
                }
              }]
            }
          })
          
          this.$nextTick(() => {
            this.$api.get('/usercenter/ztButtonInfo/getenuminfo?enumName=ZtTestStrEnum2', null, r => {
              let ztEnum = this.formItems.filter(function(cur, index, arr) {
                return cur.field == 'ztEnum1';
              })
              // console.log(ztEnum)
              ztEnum[0].itemRender.options = r.data
              // this.formItems[1].itemRender.options = r.data
            })
          })
          
          this.tableColumn.map(item => {
            if (item.required) {
              this.formRules[item.field] = [{
                required: true,
                message: `请填写${item.title}`
              }]
            }
            if (item.validator) {
              if (this.formRules[item.field]) {
                this.formRules[item.field].push({
                  pattern: new RegExp(item.validator),
                  message: item.validMsg || `${item.title}校验不通过，请重新填写`
                })
              } else {
                this.formRules[item.field] = [{
                  pattern: new RegExp(item.validator),
                  message: item.validMsg || `${item.title}校验不通过，请重新填写`
                }]
              }
            }
          })
          this.tableColumn.push({
            title: '操作',
            width: 200,
            slots: {
              default: 'operate'
            }
          })
          // console.log(this.tableColumn)
          // console.log(this.formItems)
        })
        this.$api.post('/usercenter/ztTest/select', null, r => {
          // console.log(r.data.records)
          this.tableData = r.data.records
        })
      },
      cellDBLClickEvent(row) {
        this.editEvent(row)
      },
      editEvent(row) {
        // console.log(row)
        this.formData = row
        // this.selectRow = row
        this.showEdit = true
      },
      submitEvent() {
        this.submitLoading = true
        setTimeout(() => {
          this.submitLoading = false
          this.showEdit = false
          if (this.formData.id) {
            console.log('编辑')
            // const xx = Object.assign(this.selectRow, this.formData)
            console.log(JSON.stringify(this.formData))
          } else {
            console.log('新增')
            console.log(JSON.stringify(this.formData))
          }
          this.formData = null
        }, 500)
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
      }
    },
    mounted() {
      _this = this

    },
    created() {
      this.findColumn()
    },
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1,
  h2 {
    font-weight: normal;
  }

  ul {
    list-style-type: none;
    padding: 0;
  }

  li {
    display: inline-block;
    margin: 0 10px;
  }

  a {
    color: #42b983;
  }
</style>
