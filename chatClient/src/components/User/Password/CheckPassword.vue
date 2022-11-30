<template>
  <div class="checkPassword_container">
    <!--验证密码块-->

    <div class="checkPassword_box">
      <span class="span">验证密码</span>
      <!--表单-->
      <el-form ref="checkPasswordForm" :model="checkPasswordForm" class="checkPassword_form" label-width="0">


        <!--密码-->
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" type="password" v-model="checkPasswordForm.password" placeholder="请输入旧密码"
            show-password clearable></el-input>
        </el-form-item>

        <!--按钮-->
        <el-form-item class="btns">
          <el-button class="btn" type="primary" v-on:click="checkPassword">
            验证旧密码
          </el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>


<script>
  export default {
    name: "CheckPassword",
    data() {
      return {
        //页面成员
        checkPasswordForm: {
          password: '',
        },
      };
    },

    methods: {
      checkPassword() {
        var fd = new FormData();
        var user = JSON.parse(sessionStorage.getItem('userInfo'));
        fd.append('account', user.account);
        fd.append('password', this.checkPasswordForm.password);
        this.$axios
          .post('/user/checkpassword', fd)
          .then(res => {
            console.log(res.data);
            if (res.data.code === 200) {
              this.$message.success("验证成功!");
              this.$router.replace({
                  path: '/user/updatepassword'
                });
            } else if (res.data.code === 400) {
              this.$message.error("密码有错！");
            }
          })
          .catch(err => {
            this.$message.warning("请求失败");
          })
      },
    }
  }

</script>

<style scoped>
  .checkPassword_container {
    height: 100%;
    overflow: hidden;
  }

  span {
    margin-left: 140px;
    font-size: 40px;
  }

  .checkPassword_box {
    width: 450px;
    height: 250px;
    background-color: #fff;
    border-radius: 15px;
    margin-top: 120px;
    margin-left: 270px;
  }

  .btn {
    color: white;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    cursor: pointer;
  }

  .checkPassword_form {
    width: 100%;
    padding: 20px 20px;
    box-sizing: border-box;
  }

</style>
