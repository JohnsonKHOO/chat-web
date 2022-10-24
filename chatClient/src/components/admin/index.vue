<template>
  <!--引入container布局-->
  <el-container class="home-container">
    <!--头部-->
    <el-header height="80px">
      <div class="title">
        <span class="span">聊天软件管理端</span>
      </div>
      <!-- @command="handleCommand" -->
      <el-dropdown trigger="hover">
        <el-avatar :size="50" src=user.avatar @error="errorHandler">
          <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
        </el-avatar>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <el-descriptions title="用户信息" >
              <el-descriptions-item label="昵称">管理员</el-descriptions-item>
              <el-descriptions-item label="账号"></el-descriptions-item>
              <el-descriptions-item label="在线状态"></el-descriptions-item>
              <el-descriptions-item label="简介"></el-descriptions-item>
            </el-descriptions>
          </el-dropdown-item>
          <el-dropdown-item>
            
          </el-dropdown-item>
          <el-dropdown-item divided>
            <el-link type="primary" icon="el-icon-edit"  v-on:click="logout">编辑</el-link>
            <el-link type="danger" icon="el-icon-switch-button" v-on:click="logout">登出</el-link>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <!--主体-->
    <el-container>
      <!--侧边栏-->
      <el-aside width="230px">
        <el-menu unique-opened :router="true"
          default-active="/admin/userstatistics">

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
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>


<script>
  export default {
    name: 'Home',
    data() {
      return {
        user: [],
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
                path: '/admin/manage',
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


    methods: {

      errorHandler() {
        return true;
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
          window.sessionStorage.clear();
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
  .el-aside{
    box-shadow: 0 1px 5px 0 rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.10);
  }
  .el-dropdown{
    left: 90%;
  }

  img {
    width: 230px;
    height: 80px;
    position: absolute;
    left: 0;
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

</style>
