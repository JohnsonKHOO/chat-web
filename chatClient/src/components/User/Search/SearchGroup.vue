<template>
  <div class="groupsearch_container">
    <!--搜索筛选-->
    <el-form :inline="true" :model="groupSearch" class="group_search">
      <el-form-item label="搜索：">
        <el-input v-model="groupSearch.name" placeholder="请输入群组名字"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button style="margin-left: 500px;" type="primary" icon="el-icon-search" @click="userSearch">用户</el-button>
      </el-form-item>
    </el-form>
    <!--群组信息列表-->
    <el-main>
      <el-table :data="tableData" highlight-current-row style="width: 100%;" class="searchgroup_table" height="500">
        <el-table-column label="头像" prop="group.avatar"><template v-slot="scope">
            <el-avatar :size="45" v-if="scope.row.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
            + scope.row.avatar)">
            </el-avatar>
            <el-avatar v-else :size="45"><img
                src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" /></el-avatar>
          </template></el-table-column>
        <el-table-column prop="name" label="组名"></el-table-column>
        <el-table-column prop="user.nickname" label="群主昵称"></el-table-column>
        <el-table-column label="操作" width="180" align="center"><template v-slot="scope">
            <el-popconfirm title="是否加入该群组？" @confirm="addGroup(scope.row.id)" @onConfirm="addGroup(scope.row.id)">
              <el-button type="success" slot="reference">加入群组</el-button>
            </el-popconfirm>
          </template></el-table-column>
      </el-table>
    </el-main>
  </div>
</template>
<script>
  export default {

    name: 'SearchGroup',

    data() {
      return {
        tableData: [{}

        ],
        groupSearch: {
          name: '',
        },
        user: {},
        groupUser: {
          userId: '',
          groupId: '',
          name: '',
        },

      }
    },

    created() {
      this.user = JSON.parse(sessionStorage.getItem('userInfo'));
    },

    mounted() {

    },
    methods: {
      userSearch() {
        this.$router.replace({
            path: '/user/search'
          }

        );
      },
      //加入群组
      addGroup(id) {
        var fd = new FormData();
        this.groupUser.userId = this.user.id;
        this.groupUser.groupId = id;
        this.groupUser.name = this.user.nickname;
        console.log(this.groupUser);
        fd.append('groupUser', JSON.stringify(this.groupUser));

        console.log(fd)
        this.$axios.post('/group/user/insert', fd).then(res => {
            console.log(res.data)
            if (res.data.code === 200) {
              this.$message.success("加入群组成功！");
              this.search();
            } else if (res.data.code === 400) {
              this.$message.error("已在群内！");
            } else if (res.data.code === 600) {
              this.$message.error("w加入失败!");
            }

          }

        ).catch(err => {
            this.$message.warning("请求失败");
          }

        )
      },
      //搜索按钮方法
      search() {
       
        console.log("get all data");
        var fd = new FormData();
        fd.append('name', this.groupSearch.name);

        this.$axios.post('/group/search', fd).then(res => {
            if (res.data.code === 200) {
              this.tableData = res.data.data;

            } else {
              this.$message.error("没有数据！");
            }
          }

        ).catch(err => {
            this.$message.warning("请求失败");
          }

        )
      },

    }
  }

</script>
<style scoped>
  /*根节点样式*/
  .groupsearch_container {
    height: 100%;
    width: 100%;
  }

  .group_search {
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
