<template>
  <!--引入container布局-->
  <el-container class="home-container">
    <!--头部-->
    <el-header style="height: 80px;">
      <div class="title">
        <span class="span">聊天软件管理端</span>
      </div>

      <el-dropdown trigger="hover">
        <div>
          <el-avatar :size="45" v-if="user.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
          +user.avatar)">
          </el-avatar>
          <el-avatar v-else :size="45">
            <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
          </el-avatar>
        </div>
             <!-- 鼠标放在头像上时显示管理员基本信息与功能 -->
        <el-dropdown-menu slot="dropdown" style="width: 300px; margin-right: -75px">
          <el-dropdown-item>
            <span style="font-size: large;">管理员信息</span>
            <br>
            <span>昵称：{{user.nickname}}</span>
            <br>
            <span>账号：{{user.account}}</span>
          </el-dropdown-item>

          <el-dropdown-item divided>
            <el-link type="primary" icon="el-icon-edit" v-on:click="edit">更改个人信息</el-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <el-link type="danger" icon="el-icon-switch-button" v-on:click="logout">登出</el-link>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

    </el-header>
    <!--主体-->
    <el-container>
      <!--侧边栏-->
      <el-aside width="230px">
        <el-menu unique-opened :router="true" default-active="/admin/userstatistics">

          <el-submenu :index="item.id + ''" v-for="item in menuList" :key="item.id">
            <template slot="title">
              <i :class="item.icon"></i>
              <span>{{ item.title }}</span>
            </template>
            <el-menu-item-group>
              <el-menu-item :index="i.path + ''" v-for="i in item.sList" :key="i.id">
                <i :class="i.icon"></i>
                <span>{{ i.title }}</span>
              </el-menu-item>
            </el-menu-item-group>

          </el-submenu>
        </el-menu>
      </el-aside>
      <!--内容-->
      <el-main>
        <!--编辑管理员信息对话框-->
        <el-dialog title="编辑管理员信息" :visible.sync="editFormVisible" width="30%" close-on-click-modal="false"
          close-on-press-escape="false" show-close="false">
          <!--编辑管理员信息表单-->
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
  </el-container>
</template>


<script>
  export default {
    name: 'Home',
    data() {
      var validateBirth = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入管理员生日日期'));
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
        user: {},
        editFormVisible: false,
        editForm: {
          id: '',
          account: '',
          nickname: '',
          birthday: '',
          sex: '',
        },
        avatar: '',
        fileList: {},
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
        menuList: [{
            id: 1,
            title: '首页',
            icon: 'el-icon-s-home',
            sList: [{
              id: 10,
              title: '用户统计',
              path: '/admin/userstatistics',
              icon: 'el-icon-s-data',
            }, ]
          },
          {
            id: 2,
            title: '管理',
            icon: 'el-icon-s-management',
            sList: [{
                id: 20,
                title: '用户管理',
                path: '/admin/userlist',
                icon: 'el-icon-user-solid',
              },
              {
                id: 21,
                title: '群组管理',
                icon: 'el-icon-user',
                path: '/admin/grouplist',
              },
              {
                id: 22,
                title: '管理员管理',
                icon: 'el-icon-s-custom',
                path: '/admin/list',
              },
            ]
          },
          {
            id: 3,
            title: '资源',
            icon: 'el-icon-folder',
            sList: [{
              id: 30,
              title: '头像',
              icon: 'el-icon-picture',
              path: '/assests/avatar',
            }, ]
          }
        ],
      }
    },

    mounted() {
      this.getSessionStorage();
    },

    methods: {
      //上传头像
      handleChange(file, fileList) {
        this.fileList = fileList;
        console.log("file:" + this.fileList);
        this.avatar = URL.createObjectURL(file.raw);
        console.log("avatar:" + this.avatar);
      },
      //
      getSessionStorage() {
        this.user = JSON.parse(sessionStorage.getItem('userInfo'));
        if (this.user == null) {
          console.log("session错误！");
          this.$message.error("session错误！");
          this.$router.replace({
            path: '/login'
          });
        }
      },
      //提交编辑用户信息表单
      SubmitEditForm() {
        console.log(this.editForm);
        this.$refs['editForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('user', JSON.stringify(this.editForm));
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
                this.$message.warning("请求失败");
              })
          } else {
            this.$message.warning("数据错误!");
            return false;
          }
        });
      },
      save2() { //编辑用户信息表单的保存
        this.$confirm('是否确定编辑此管理员信息？', '提示', {
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
          this.$message({
            type: 'success',
            message: '登出成功!'
          });
          sessionStorage.clear();
          this.$router.push("/login");
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消登出操作！'
          });
        });
      },

    }
  }

</script>

<style scoped>
  .home-container {
    height: 100%;
  }

  .el-header {
    display: flex;
    padding-left: 250px;
    align-items: center;
    font-size: 25px;
    box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.10);
  }

  .el-aside {
    box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.10);
  }

  .el-dropdown {
    left: 80%;
  }


  .toggle-button {
    background-color: #4a5064;
    font-size: 10px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
  }

  .span {
    margin-left: -230px;
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
