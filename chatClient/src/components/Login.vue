<template>
  <div class="login_container">
    <!--登录块-->

    <div class="login_box">
      <span class="span">聊天系统</span>
      <!--表单-->
      <el-form ref="loginForm" :model="loginForm" class="login_form" label-width="0">

        <!--用户名-->
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user" v-model="loginForm.account" placeholder="请输入您的账号" clearable></el-input>
        </el-form-item>

        <!--密码-->
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" type="password" v-model="loginForm.password" placeholder="请输入密码"
            show-password clearable></el-input>
        </el-form-item>

        <!--按钮-->
        <el-form-item class="btns">
          <el-button class="btn" type="primary" v-on:click="login" v-loading.fullscreen.lock="fullscreenLoading">登录
          </el-button>
          <el-button class="btn" type="success" v-on:click="register">注册账号</el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>


<script>
  export default {
    name: "Login",
    data() {
      return {
        //页面成员
        loginForm: {
          account: "",
          password: "",
        },
        fullscreenLoading: false
      };
    },

    methods: {
      login() {
        //loading画面
        this.fullscreenLoading = true;
        setTimeout(() => {
          this.fullscreenLoading = false;
        }, 500);
        var fd = new FormData();
        fd.append('account', this.loginForm.account);
        fd.append('password', this.loginForm.password);
        this.$axios
          .post('/login', fd)
          .then(res => {
            console.log(res.data);
            if (res.data.code === 200) {
              sessionStorage.setItem('isLogin', true);
              //session设置
              sessionStorage.setItem('userInfo', JSON.stringify(res.data.data));
              //用户登录
              if (res.data.data.roleId === 1) {
                this.$message.success("欢迎" + res.data.data.nickname + "登录");
                this.$router.replace({
                  path: '/user/home'
                });
              }
            } else if (res.data.code === 400) {
              this.$message.error("登录失败，账号或密码错误！");
            }
            else if (res.data.code === 600) {
              this.$message.error("登录失败，账号异地登录！");
            }
          })
          .catch(err => {
            this.$message.warning("请求失败");
          })
      },

      register() {
        this.$router.replace({
          path: '/register'
        })
      }
    }
  }

</script>

<style scoped>
  .login_container {
    background-image: linear-gradient(to bottom right, #fc466b, #3f5efb);
    height: 100%;
    overflow: hidden;
  }

  span {
    margin-left: 140px;
    font-size: 40px;
  }

  .login_box {
    width: 450px;
    height: 300px;
    background-color: #fff;
    border-radius: 15px;
    position: absolute;
    left: 50%;
    top: 45%;
    transform: translate(-50%, -25%);
    box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  }

  .btns {
    margin-top: 50px;
  }

  .btn {
    color: white;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    cursor: pointer;
  }

  .login_form {
    width: 100%;
    top: 10%;
    padding: 20px 20px;
    box-sizing: border-box;
  }

</style>
