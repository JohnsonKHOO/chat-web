<template>
  <div class="updatePassword_container">
    <!--验证密码块-->

    <div class="updatePassword_box">
      <span class="span">更新密码</span>
      <!--表单-->
      <el-form ref="updatePasswordForm" :rules="checkPasswordRules" :model="updatePasswordForm"
        class="updatePassword_form" label-width="0">

        <!--密码输入框-->
        <el-form-item prop="password">
          <el-input type="password" v-model="updatePasswordForm.password" placeholder="请输入您的新密码" clearable
            show-password>
          </el-input>
        </el-form-item>
        <!--确认密码输入框-->
        <el-form-item prop="checkpassword">
          <el-input type="password" v-model="updatePasswordForm.checkpassword" placeholder="请再输入一遍新密码" clearable
            show-password>
          </el-input>
        </el-form-item>
        <!--按钮-->
        <el-form-item class="btns">
          <el-button class="btn" type="primary" v-on:click="updatePassword">
            验证密码
          </el-button>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>


<script>
  export default {
    name: "UpdatePassword",
    data() {
      /*判断两次输入密码是否一致*/
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.updatePasswordForm.checkpassword !== '') {
            this.$refs.updatePasswordForm.validateField('checkpassword');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.updatePasswordForm.password) {
          callback(new Error('两次输入密码不一致！'));
        } else {
          callback();
        }
      };
      return {
        //页面成员
        updatePasswordForm: {
          password: '',
          checkpassword: '',
        },

        /*Form组件提供的表单验证功能，通过rules属性传入约定的验证规则，并将Form-Item的prop属性设置为需校验的字段名*/
        checkPasswordRules: {
          password: [{
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 6,
              max: 15,
              message: '长度在6到15个字符',
              trigger: 'blur'
            },
            {
              validator: validatePass,
              trigger: 'blur'
            }
          ],
          checkpassword: [{
              required: true,
              message: '请重复输入密码',
              trigger: 'blur'
            },
            {
              min: 6,
              max: 15,
              message: '长度在6到15个字符',
              trigger: 'blur'
            },
            {
              validator: validatePass2,
              trigger: 'blur'
            }
          ],
        }
      };

    },

    methods: {

      updatePassword() {
        console.log(this.updatePassword);
        this.$refs['updatePasswordForm'].validate(valid => {
          console.log(valid);
          if (valid) {

            var fd = new FormData();
            var user = JSON.parse(sessionStorage.getItem('userInfo'));
            fd.append('id', user.id);
            fd.append('password', this.updatePasswordForm.password);
            this.$axios
              .post('/user/updatepassword', fd)
              .then(res => {
                console.log(res.data);
                if (res.data.code === 200) {
                  this.$router.replace({
                    path: '/user/info'
                  });
                  this.$message.success("更新密码成功");
                } else if (res.data.code === 400) {
                  this.$message.error("更新密码失败");
                }
              })
              .catch(err => {
                this.$message.warning("请求失败");
              })
          } else {
            this.$message.error("请输入正确信息！");
            return false;
          }
        })
      },
    }
  }

</script>

<style scoped>
  .updatePassword_container {
    height: 100%;
    overflow: hidden;
  }

  span {
    margin-left: 140px;
    font-size: 40px;
  }

  .updatePassword_box {
    width: 450px;
    height: 300px;
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

  .updatePassword_form {
    width: 100%;
    padding: 20px 20px;
    box-sizing: border-box;
  }

</style>
