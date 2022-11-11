<template>
  <div style="background-image: linear-gradient(to bottom right, #fc466b, #3f5efb); height: 100%; width: 100%;">
    <el-container class="main">
      <el-aside class="column-1">

        <el-menu style="background-color: rgb(96, 96, 96);" unique-opened :router="true"
          default-active="/admin/userstatistics">
          <el-menu-item-group>
            <el-menu-item :index="i.path + ''" v-for="i in menuList" :key="i.id">
              <i :class="i.icon" style="margin-left: -3px;"></i>
            </el-menu-item>
          </el-menu-item-group>
        </el-menu>

        <el-dropdown trigger="hover">
          <div>
            <el-avatar :size="45" v-if="user.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
          +user.avatar)">
            </el-avatar>
            <el-avatar v-else :size="45">
              <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
            </el-avatar>
          </div>

          <el-dropdown-menu slot="dropdown" style="width: 300px; margin-right: -75px">
            <el-dropdown-item>
              <el-descriptions title="用户信息" style="padding: 20px; font-size: medium;">
                <el-descriptions-item label="昵称">{{user.nickname}}</el-descriptions-item>
                <el-descriptions-item label="账号">{{user.account}}</el-descriptions-item>
              </el-descriptions>
            </el-dropdown-item>

            <el-dropdown-item divided>
              <el-link type="primary" icon="el-icon-edit" v-on:click="edit">编辑个人信息</el-link>
              <el-link type="danger" icon="el-icon-switch-button" v-on:click="logout">登出</el-link>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-aside>

      <el-aside class="column-2">
        <div style="text-align: center;border-bottom: 1px solid #ccc">好友</div>
        <div style="padding: 10px 0" v-for="user in users" :key="user.username">
          <span>{{ user.username }}</span>
          <i class="el-icon-chat-dot-round" style="margin-left: 10px; font-size: 16px; cursor: pointer"
            @click="chatUser = user.username"></i>
          <span style="font-size: 12px;color: limegreen; margin-left: 5px"
            v-if="user.username === chatUser">chatting...</span>
        </div>

      </el-aside>

      <el-main class="column-3" style="overflow:hidden; padding: 0px;">
        <!--编辑用户信息对话框-->
        <el-dialog title="编辑用户信息" :visible.sync="editFormVisible" width="30%" close-on-click-modal="false"
          close-on-press-escape="false" show-close="false">
          <!--编辑用户信息表单-->
          <el-form label-width="100px" :model="editForm" :rules="rules" ref="editForm" class="edit_Form">
            <!--姓名输入框-->
            <el-form-item label="昵称：" prop="nickname">
              <el-input v-model="editForm.nickname" placeholder="请输入您的昵称" clearable></el-input>
            </el-form-item>
            <!--账号输入框-->
            <el-form-item label="账号：" prop="account">
              <el-input v-model="editForm.account" placeholder="请输入您的账号" clearable></el-input>
            </el-form-item>
            <!--性别单选框-->
            <el-form-item label="性别：" prop="sex">
              <el-radio-group v-model="editForm.sex">
                <el-radio label="0">男</el-radio>
                <el-radio label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <!--日期选择器-->
            <el-form-item label="出生日期：" prop="birthday">
              <el-date-picker v-model="editForm.birthday" type="date" placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <!--密码输入框-->
            <el-form-item label="密码：" prop="password">
              <el-input type="password" v-model="editForm.password" placeholder="请输入您的密码" clearable show-password>
              </el-input>
            </el-form-item>

            <!-- 头像上传 -->
            <el-form-item label="头像上传：">
              <el-upload ref="upfile" class="avatar-uploader" action="#" :auto-upload="false" :on-change="handleChange"
                :show-file-list="false" :limit="1" accept="image/png,image/gif,image/jpg,image/jpeg">
                <img v-if="avatar" :src="avatar" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-form>
          <!--编辑用户信息对话框按钮-->
          <div slot="footer" class="dialog_footer">
            <el-button @click="editFormVisible = false">取消</el-button>
            <el-button type="primary" @click="save2">更新</el-button>
          </div>
        </el-dialog>


        <router-view></router-view>
      </el-main>

    </el-container>
  </div>

</template>
<script>
  let socket;
  export default {
    name: "chat",
    data() {
      var validateBirth = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户生日日期'));
        } else {

          var d = new Date();
          if (Date.parse(value) > d.getTime()) {
            callback(new Error('生日日期不符合规范'));
          } else {
            callback();
          }
        }
      };
      return {
        editFormVisible: false,
        editForm: {
          id: '',
          account: '',
          nickname: '',
          password: '',
          birthday: '',
          sex: '',
        },
        avatar: '',
        fileList: {},
        // circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
        user: {},
        isCollapse: false,
        users: [{}],
        chatUser: '',
        text: "",
        messages: [{}],
        content: '',
        menuList: [{
            id: 1,
            title: '聊天',
            icon: 'el-icon-chat-line-round',
            path: '/user/chat',
          },
          {
            id: 2,
            title: '通讯录',
            icon: 'el-icon-phone',
            path: '/user/contact',
          },
          {
            id: 3,
            title: '添加好友',
            icon: 'el-icon-user-solid',
            path: '/user/addfriend',
          },
          {
            id: 4,
            title: '添加群组',
            icon: 'el-icon-user',
            path: '/user/addgroup',
          },
        ],
        /*Form组件提供的表单验证功能，通过rules属性传入约定的验证规则，并将Form-Item的prop属性设置为需校验的字段名*/
        /*新增用户信息表单验证*/
        rules: {
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
            }
          ],
        },

      }
    },
    created() {
      this.init();
    },
    mounted() {
      this.getSession();
    },
    methods: {

      handleChange(file, fileList) {
        this.fileList = fileList;
        console.log("file:" + this.fileList);
        this.avatar = URL.createObjectURL(file.raw);
        console.log("avatar:" + this.avatar);
      },

      getSession() {
        this.user = JSON.parse(sessionStorage.getItem('userInfo'));
        if (this.user == null) {
          console.log("session错误！");
          this.$message.error("session错误！");
          this.$router.replace({
            path: '/login'
          });
        }
      },
      SubmitEditForm() {
        console.log(this.editForm);
        this.$refs['editForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('user', JSON.stringify(this.editForm));
            fd.append('file', this.fileList[0].raw);
            this.$axios
              .post('/user/update', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("更新成功！");
                  sessionStorage.setItem('userInfo', JSON.stringify(res.data.data))
                  this.getSession();
                } else if (res.data.code === 400) {
                  this.$message.error("用户账号已存在！");
                }
              })
              .catch(err => {
                this.$message.error("请求失败");
              })
          } else {
            this.$message.error("数据错误!");
            return false;
          }
        });
      },
      save2() { //编辑用户信息表单的保存
        this.$confirm('是否确定编辑此用户信息？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => {
          this.SubmitEditForm();
        })
      },

      edit() { //编辑按钮方法
        this.editForm = this.user;
        this.editFormVisible = true;
      },

      logout() {
        this.$confirm('是否确定登出？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '放弃',
          type: 'warning'
        }).then(() => {
          this.$axios
            .get('/logout')
            .then(res => {
              console.log(res.data)
              if (res.data.code === 200) {
                this.$message.success("登出成功");
                sessionStorage.clear();
                this.$router.push("/login");
              } else if (res.data.code === 400) {
                this.$message.error("登出失败");
              }
            })
            .catch(err => {
              this.$message.error("请求失败");
            })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消登出操作！'
          });
        });
      },
      send() {
        if (!this.chatUser) {
          this.$message({
            type: 'warning',
            message: "请选择聊天对象"
          })
          return;
        }
      },
      init() {

      }
    }
  }

</script>
<style scoped>
  .main {
    display: grid;
    height: 80%;
    width: 70%;
    margin-left: 200px;
    margin-top: 60px;
    position: absolute;
    grid-template-columns: 60px 2fr 6.5fr;
    box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  }

  .el-dropdown {
    left: 10%;
    top: 45%;
  }

  .column-1 {
    background-color: rgb(96, 96, 96);
    width: auto !important;
  }

  .column-2 {
    background-color: rgb(245, 245, 245);
    width: auto !important;
    line-height: 50px;
  }

  .column-3 {
    background-color: white;
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
