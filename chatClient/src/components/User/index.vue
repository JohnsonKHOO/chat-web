<template>
  <div style="background-image: linear-gradient(to bottom right, #fc466b, #3f5efb); height: 100%; width: 100%;">
    <el-container class="main">
      <el-aside class="column-1">
        <!-- 侧边导航栏 -->
        <el-menu style="background-color: rgb(96, 96, 96);" unique-opened :router="true"
          default-active="/admin/userstatistics">
          <el-menu-item-group>
            <el-menu-item :index="i.path + '' " v-for="i in menuList" :key="i.id">
              <i :class="i.icon" style="margin-left: -3px;"></i>
            </el-menu-item>
          </el-menu-item-group>
        </el-menu>
        <!-- 鼠标放在头像上时显示用户基本信息与功能 -->
        <el-dropdown trigger="hover">
          <div>
            <el-avatar :size="45" v-if="user.avatar"  
            :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
          +user.avatar)">
            </el-avatar>
            <el-avatar v-else :size="45" >
              <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
            </el-avatar>
          </div>

          <el-dropdown-menu slot="dropdown" style="width: 300px; margin-right: -75px">
            <el-dropdown-item>
              <span style="font-size: large;"> 用户信息</span>
              <br>
              <span>昵称：{{user.nickname}}</span>
              <br>
              <span>账号：{{user.account}}</span>
            </el-dropdown-item>

            <el-dropdown-item divided>
              <el-link type="primary" icon="el-icon-edit" v-on:click="info">个人主页</el-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-link type="primary" icon="el-icon-edit" v-on:click="checkPassword">更改密码</el-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <el-link type="danger" icon="el-icon-switch-button" v-on:click="logout">登出</el-link>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-aside>

      <el-main class="column-2" style="overflow:hidden; padding: 0px;" >

        <router-view @toParent="getInfo"></router-view>
      </el-main>

    </el-container>
  </div>

</template>
<script>
  export default {
    name: "chat",
    data() {

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
        friends: [{}],
        account: "",
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
            title: '群组',
            icon: 'el-icon-s-custom',
            path: '/user/group',
          },
          {
            id: 4,
            title: '添加好友',
            icon: 'el-icon-search',
            path: '/user/search',
          },
        ],

      }
    },
    created() {
    },
    mounted() {
      this.getSessionStorage();
    },
    methods: {
      //子组件传回信息
      getInfo(msg) {
        this.user = msg;
      },
      //获取sessionStorage
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
      //从后端获取页面session，还有问题，未完成
      getSession() {
        this.$axios
          .get('/getsession')
          .then(res => {
            if (res.data.code === 200) {
              return;
            }
            if (res.data.code === 600) {
              this.$message.error(res.data.data);
              this.$router.push("/login");
            }
          })
          .catch(err => {
            this.$message.warning("请求失败");
          });
      },
      //跳转个人主页
      info() {
        this.$router.push("/user/info");
      },
      //跳转验证与更新密码页面
      checkPassword() {
        this.$router.push("/user/checkpassword");
      },

      logout() {
        this.$confirm('是否确定登出？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '放弃',
          type: 'warning'
        }).then(() => {
          this.$router.push("/login");
          this.$message.success("登出成功！");
          sessionStorage.clear();
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
  .main {
    display: grid;
    height: 80%;
    width: 70%;
    margin-left: 200px;
    margin-top: 60px;
    position: absolute;
    grid-template-columns: 60px 8.5fr;
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
