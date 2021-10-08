<template>
  <div>

    <el-select v-model="enumValue" placeholder="枚举">
      <el-option v-for="item in Enums.ZtTest" :key="item.value" :value="item.value" :label="item.label">
      </el-option>
    </el-select>

    <el-button type="primary" plain @click="onSubmit">保存</el-button>
  </div>
</template>

<script>
  var _this;
  export default {
    name: 'ZtTestEnum',
    data() {
      return {
        enumValue: ''
      }
    },
    mounted() {
      _this = this
    },
    methods: {
      onSubmit: function() {
        var test = {};
        test.ztEnum1 = this.enumValue;
        test.ztEnum2 = this.enumValue;
        test.ztEnum3 = this.enumValue;
        console.log(JSON.stringify(test))
        this.$api.post('zttest/insert', test, r => {

        })
      }
    }
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
