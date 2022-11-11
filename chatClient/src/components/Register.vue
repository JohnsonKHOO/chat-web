<template>
  <div class="register_container">
    <span class="span">聊天系统注册页面</span>
    <!--注册块-->
    <div class="register_box">

      <!--表单-->
      <el-form ref="registerForm" :model="registerForm" :rules="registerRules" class="register_Form"
        label-width="100px">

        <!--姓名输入框-->
        <el-form-item label="昵称：" prop="nickname">
          <el-input v-model="registerForm.nickname" placeholder="请输入您的昵称" clearable></el-input>
        </el-form-item>
        <!--账号输入框-->
        <el-form-item label="账号：" prop="account">
          <el-input v-model="registerForm.account" placeholder="请输入您的账号" clearable></el-input>
        </el-form-item>
        <!--性别单选框-->
        <el-form-item label="性别：" prop="sex">
          <el-radio-group v-model="registerForm.sex">
            <el-radio label="0">男</el-radio>
            <el-radio label="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <!--日期选择器-->
        <el-form-item label="出生日期：" prop="birthday">
          <el-date-picker v-model="registerForm.birthday" type="date" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <!--密码输入框-->
        <el-form-item label="密码：" prop="password">
          <el-input type="password" v-model="registerForm.password" placeholder="请输入您的密码" clearable show-password>
          </el-input>
        </el-form-item>
        <!--确认密码输入框-->
        <el-form-item label="确认密码：" prop="checkpassword">
          <el-input type="password" v-model="registerForm.checkpassword" placeholder="请再输入一遍密码" clearable show-password>
          </el-input>
        </el-form-item>
        <!-- 头像上传 -->
        <el-form-item label="头像上传：">
          <el-upload ref="upfile" class="avatar-uploader" action="#" :auto-upload="false"
          :on-change="handleChange" :show-file-list="false" :limit="1" 
          accept="image/png,image/gif,image/jpg,image/jpeg">
            <img v-if="avatar" :src="avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>  
        </el-form-item>

        <!--按钮-->
        <el-form-item class="btns">
          <el-button type="primary" v-on:click="submitForm">注册</el-button>
          <el-button type="info" v-on:click="login">返回登陆页面</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Register",
    data() {
      /*判断两次输入密码是否一致*/
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.registerForm.checkpassword !== '') {
            this.$refs.registerForm.validateField('checkpassword');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.registerForm.password) {
          callback(new Error('两次输入密码不一致！'));
        } else {
          callback();
        }
      };

      var validateBirth = (rule, value, callback) => {
        if (value === '') callback(new Error('请输入用户生日日期'));
        else {
          var d = new Date();
          if (Date.parse(value) > d.getTime()) {
            callback(new Error('生日日期不符合规范'));
          } else callback();
        }

      };

      return {
        registerForm: {
          account: '',
          nickname: '',
          password: '',
          birthday: '',
          sex: '',
          checkpassword: '',
        },
        avatar: '', 
        fileList: {},

        /*Form组件提供的表单验证功能，通过rules属性传入约定的验证规则，并将Form-Item的prop属性设置为需校验的字段名*/
        registerRules: {
          nickname: [{
            required: true,
            message: '请输入您的昵称',
            trigger: 'blur'
          }],
          sex: [{
            required: true,
            message: '请选择您的性别',
            trigger: 'blur'
          }],

          account: [{
            required: true,
            message: '请输入您的账号',
            trigger: 'blur'

            },
            {
              min: 6,
              max: 15,
              message: '长度在6到15个字符',
              trigger: 'blur'
            }
          ],
          birthday: [{
              required: true,
              message: '请输入出生日期',
              trigger: 'blur'
            },
            {
              validator: validateBirth,
              trigger: 'blur'
            }
          ],

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
      }
    },
    methods: {
      handleChange(file, fileList){
        this.fileList = fileList;
        console.log("file:"+ this.fileList);
        this.avatar = URL.createObjectURL(file.raw);
        console.log("avatar:" + this.avatar);
      },

       // 文件列表移除文件时的钩子
      //  fileRemove(file, fileList){
      //     this.fileList= fileList;
      //     const list = [];
      //     this.fileList.forEach(item => {
      //         list.push({
      //           id:item.id,
      //           imageType:0
      //         });
      //     });
      //     this.registerForm.avatar = list;
      // },

      submitForm() {
        console.log(this.registerForm);
        this.$refs['registerForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();

            fd.append('user', JSON.stringify(this.registerForm));
            fd.append('file', this.fileList[0].raw);
            this.$axios
              .post('/register', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("注册成功！");
                  this.$refs.registerForm.resetFields();
                } else if (res.data.code === 400) {
                  this.$message.error("注册失败，账号已存在！");
                } else if (res.data.code === 600) {
                  this.$message.error("数据错误！");
                }
              })
              .catch(err => {
                this.$message.error("请求失败");
              })
          } else {
            console.log('请输入正确信息');  
            return false;
          }
        });
      },
      login() {
        this.$router.replace({
          path: '/login'
        })
      }
    }
  }

</script>

<style scoped>
  /*根节点样式*/
  .register_container {
    background-image: linear-gradient(to bottom right, #fc466b, #3f5efb);
    height: 100%;
    overflow: hidden;
  }

  span {
    position: absolute;
    left: 20%;
    top: 8%;
    z-index: 10;
    font-size: 30px;
  }

  /*输入框样式*/
  .register_box {
    width: 1000px;
    height: 600px;
    background-color: #fff;
    border-radius: 15px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);

  }

  /*按钮样式*/
  .btns {
    display: block;
  }

  /*表单样式*/
  .register_Form {
    position: absolute;
    bottom: 0%;
    width: 90%;
    padding: -20px 10px;
    margin: 0px 40px;
    box-sizing: border-box;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    overflow: hidden;

  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 78px;
    height: 78px;
    line-height: 78px;
    text-align: center;
  }

  .avatar {
    width: 78px;
    height: 78px;
    display: block;
  }

</style>
