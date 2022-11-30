<template>
  <div class="usersearch_container">

    <!--搜索筛选-->
    <el-form :inline="true" :model="userSearch" class="user_search">
      <el-form-item label="搜索：">
        <el-input v-model="userSearch.account" placeholder="请输入用户账号/昵称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button style="margin-left: 500px;" type="primary" icon="el-icon-search" @click="groupSearch">群组</el-button>
      </el-form-item>
    </el-form>
    <!--用户信息列表-->
    <el-main>
      <el-table :data="tableData.filter(data => data.id != user.id)" highlight-current-row style="width: 100%;"
        class="searchuser_table" height="500">
        <el-table-column label="头像" prop="user.avatar">
          <template v-slot="scope">
            <el-avatar :size="45" v-if="scope.row.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
          +scope.row.avatar)">
            </el-avatar>
            <el-avatar v-else :size="45">
              <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
            </el-avatar>
          </template>

        </el-table-column>
        <el-table-column prop="account" label="账号"></el-table-column>
        <el-table-column prop="nickname" label="昵称"></el-table-column>

        <el-table-column label="操作" width="180" align="center">

          <template v-slot="scope">
            <el-popconfirm title="是否将该用户添加为好友？" @confirm="addFriend(scope.row.id)" @onConfirm="addFriend(scope.row.id)">
              <el-button type="success" slot="reference">添加好友</el-button>
            </el-popconfirm>
          </template>

        </el-table-column>
      </el-table>


    </el-main>
  </div>
</template>

<script>
  export default {

    name: 'SearchUser',

    data() {
      return {
        tableData: [{}],
        userSearch: {
          account: '',
        },  
        user: {},
      }
    },

    created() {
      this.user = JSON.parse(sessionStorage.getItem('userInfo'));
    },

    mounted() {

    },
    methods: {
      //跳转群组搜索
      groupSearch() {
        this.$router.replace({
          path: '/group/search'
        });
      },
      //添加好友
      addFriend(id) {
        var fd = new FormData();
        fd.append('userId', this.user.id);
        fd.append('friendId', id);
        console.log(fd)
        this.$axios
          .post('/friend/add', fd)
          .then(res => {
            console.log(res.data)
            if (res.data.code === 200) {
              this.$message.success("添加好友成功！");
              this.search();
            } else if (res.data.code === 400) {
              this.$message.error("已为好友！");
            } else if (res.data.code === 600) {
              this.$message.error("添加失败！");
            }

          })
          .catch(err => {
            this.$message.warning("请求失败");
          })
      },

      search() { //搜索按钮方法
        console.log("get all data");
        var fd = new FormData();
        fd.append('account', this.userSearch.account);

        this.$axios
          .post('/user/search', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.tableData = res.data.data;

            } else {
              this.$message.error("没有数据！");
            }
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },

    }
  }

</script>

<style scoped>
  /*根节点样式*/
  .usersearch_container {
    height: 100%;
    width: 100%;
  }

  .user_search {
    margin-top: 20px;
    margin-left: 15px;
    padding: 0 10px;
  }

  .el-image {
    width: 70px;
    height: 70px;
    margin-left: 30px;
  }

</style>
