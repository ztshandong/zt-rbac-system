<template>
  <div class="login" id="loginDiv" ref="loginDiv"
    :class="{background1: random==1, background2: random==2, background3:random==3 }">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h1 class="title">卓睿管理系统</h1>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
          @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>

      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
          @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2020-2030 zhangzhuorui.com All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
  import Cookies from "js-cookie";
  import {
    encrypt,
    decrypt
  } from '@/utils/jsencrypt'

  export default {
    name: "Login",
    data() {
      return {
        cookiepassword: "",
        loginForm: {
          username: "zhangtao",
          password: "123456",
          rememberMe: false,
          code: "",
          uuid: ""
        },
        loginRules: {
          username: [{
            required: true,
            trigger: "blur",
            message: "用户名不能为空"
          }],
          password: [{
            required: true,
            trigger: "blur",
            message: "密码不能为空"
          }]
        },
        loading: false,
        redirect: undefined,
        random: 1
      };
    },
    watch: {
      $route: {
        handler: function(route) {
          console.log('route.query:' + JSON.stringify(route.query))
          console.log('route.query.redirect:' + JSON.stringify(route.query.redirect))
          this.redirect = route.query && route.query.redirect;
        },
        immediate: true
      }
    },
    created() {
      // this.getCode();
      this.getCookie();
    },
    mounted() {
      this.random = Math.ceil(Math.random() * 3)
      if (this.random == 0) {
        this.random = 1
      }
    },
    methods: {
      getCode() {
        getCodeImg().then(res => {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        });
      },
      getCookie() {
        const username = Cookies.get("username");
        const password = Cookies.get("password");
        const rememberMe = Cookies.get('rememberMe')
        this.loginForm = {
          username: username === undefined ? this.loginForm.username : username,
          password: password === undefined ? this.loginForm.password : decrypt(password),
          rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
        };
      },
      handleLogin() {
        // console.log(this.loginForm)
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true;
            if (this.loginForm.rememberMe) {
              Cookies.set("username", this.loginForm.username, {
                expires: 30
              });
              Cookies.set("password", encrypt(this.loginForm.password), {
                expires: 30
              });
              Cookies.set('rememberMe', this.loginForm.rememberMe, {
                expires: 30
              });
            } else {
              Cookies.remove("username");
              Cookies.remove("password");
              Cookies.remove('rememberMe');
            }
            // src/store/modules/user.js
            this.$store.dispatch("Login", this.loginForm).then((res) => {
              console.log('LoginRes:' + JSON.stringify(res))
              this.$router.push({
                path: this.redirect || "/"
              }).catch((err2) => {
                console.log('LoginResErr2:' + err2)
              });
            }).catch((err) => {
              console.log('LoginResErr:' + JSON.stringify(err))
              this.loading = false;
              // this.getCode();
            });
          }
        });
      }
    }
  };
</script>

<style rel="stylesheet/scss" lang="scss">
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    // background-image: url("../assets/images/login-background.jpg");
    background-size: cover;
  }

  .background1 {
    background-image: url("https://ztshandong.oss-cn-shanghai.aliyuncs.com/kid/login-background.jpg");
  }

  .background2 {
    background-image: url("https://ztshandong.oss-cn-shanghai.aliyuncs.com/kid/login-background2.jpg");
  }

  .background3 {
    background-image: url("https://ztshandong.oss-cn-shanghai.aliyuncs.com/kid/login-background3.jpg");
  }

  .title {
    // margin: 0px 0px 0px 0px;
    margin: 0px auto 30px auto;
    text-align: center;
    color: black;
    // color: #707070;
  }

  .login-form {
    background-color: transparent;
    border-radius: 6px;
    color: white;
    // background: #ffffff;
    width: 400px;
    padding: 25px 25px 5px 25px;

    .el-input {
      height: 38px;

      input {
        height: 38px;
      }
    }

    .input-icon {
      height: 39px;
      width: 14px;
      margin-left: 2px;
    }
  }

  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }

  .login-code {
    width: 33%;
    height: 38px;
    float: right;

    img {
      cursor: pointer;
      vertical-align: middle;
    }
  }

  .el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
  }

  .login-code-img {
    height: 38px;
  }

  .el-input__inner {
    // height: 47px;
    background-color: rgba(255, 255, 255, 0.247);
  }
</style>
