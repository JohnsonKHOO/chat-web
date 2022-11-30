<template>
  <div class="friendlist_container">
    <!--好友信息列表-->
    <el-main>
      <span>好友列表</span>
      <el-table
        :data="tableData.filter(data => !search || data.user.account.toLowerCase().includes(search.toLowerCase())
        ||data.user.nickname.toLowerCase().includes(search.toLowerCase()) || data.name.toLowerCase().includes(search.toLowerCase()))"
        highlight-current-row style="width: 100%;" class="friendlist_table" height="500" >

        <el-table-column label="头像">
          <template v-slot="scope">
            <el-avatar :size="45" v-if="scope.row.user.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
          +scope.row.user.avatar)">
            </el-avatar>
            <el-avatar v-else :size="45">
              <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
            </el-avatar>
          </template>

        </el-table-column>
        <el-table-column prop="user.nickname" label="好友昵称"></el-table-column>
        <el-table-column prop="name" label="好友备注"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot="header" slot-scope="scope">
            <el-input v-if="scope" v-model="search" size="mini" placeholder="输入关键字搜索" />
          </template>

          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="info(scope.row.user.id)">查看</el-button>
            <el-button type="text" icon="el-icon-edit" @click="edit(scope.row)">更改备注</el-button>
            <el-button type="text" icon="el-icon-delete" class="deleteColor" @click="del(scope.row)">删除好友</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog title="好友信息" :visible.sync="infoVisible" width="30%" close-on-click-modal="false"
        close-on-press-escape="false" show-close="false">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="头像">
            <el-avatar :size="60" v-if="friend.avatar" fit="contain" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
          +friend.avatar)">
            </el-avatar>
            <el-avatar v-else :size="60">
              <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
            </el-avatar>
          </el-descriptions-item>

          <el-descriptions-item label="账号">
            {{friend.account}}
          </el-descriptions-item>
          <el-descriptions-item label="昵称">
            {{friend.nickname}}
          </el-descriptions-item>
          <el-descriptions-item label="性别">
            <span v-if="friend.sex == 0">男</span>
            <span v-if="friend.sex == 1">女</span>
          </el-descriptions-item>
          <el-descriptions-item label="年龄">{{friend.age}}</el-descriptions-item>
          <el-descriptions-item label="个人签名">
            {{friend.signature}}
          </el-descriptions-item>
    

          <div slot="footer" class="dialog_footer">
            <el-button @click="infoVisible = false">关闭</el-button>
          </div>
        </el-descriptions>
      </el-dialog>

      <!--编辑好友信息对话框-->
      <el-dialog title="编辑好友信息" :visible.sync="editFormVisible" width="30%" close-on-click-modal="false"
        close-on-press-escape="false" show-close="false">
        <!--编辑好友信息表单-->
        <el-form label-width="100px" :model="editForm" :rules="rules" ref="editForm" class="edit_Form">
          <!--好友备注输入框-->
          <el-form-item label="好友备注：" prop="nickname">
            <el-input v-model="editForm.name" placeholder="请输入您的昵称" clearable></el-input>
          </el-form-item>

        </el-form>
        <!--编辑好友信息对话框按钮-->
        <div slot="footer" class="dialog_footer">
          <el-button @click="editFormVisible = false">取消</el-button>
          <el-button type="primary" @click="save">更新</el-button>
        </div>
      </el-dialog>

    </el-main>
  </div>
</template>

<script>
  export default {
    name: 'Contact',

    data() {
      return {
        user: {},
        tableData: [{}],
        infoVisible: false,
        friend: {},
        search: '',
        editFormVisible: false,
        editForm: {
        },
      }
    },

    created() {
      this.user = JSON.parse(sessionStorage.getItem('userInfo'));
      this.list();
    },

    mounted() {},

    methods: {

      SubmitEditForm() {
        console.log(this.editForm);
        this.$refs['editForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('friend', JSON.stringify(this.editForm));
            this.$axios
              .post('/friend/update', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("更新成功！");
                  this.list();
                } else {
                  this.$message.error(res.data.msg);
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

      list() { //搜索按钮方法
        var fd = new FormData();
        fd.append('userId', this.user.id);
        fd.append('account', "");
        this.$axios
          .post('/friend/search', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.tableData = res.data.data;
            } 
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },

      save() { //编辑好友信息表单的保存
        this.$confirm('是否确定编辑此好友信息？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => {
          this.SubmitEditForm();
        })
      },

      edit(obj) { //编辑按钮方法
        this.editForm = obj;
        this.editFormVisible = true;
      },

      info(id) { //查看按钮方法id
        this.infoVisible = true;
        var fd = new FormData();
        fd.append('id', id);
        this.$axios
          .post('/user/info', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.friend = res.data.data;
            } else {
              this.$message.error("没有数据！");
            }
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },

      del(obj) { //删除按钮方法
        this.$confirm('是否确定删除此好友？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => { //按下确定弹出消息
          var fd = new FormData();
          fd.append('friend', JSON.stringify(obj));
          this.$axios
            .post('/friend/delete', fd)
            .then(res => {
              if (res.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除好友成功！'
                });
                this.list();
              } else {
                this.$message({
                  type: 'error',
                  message: '删除好友失败'
                });
              }
            }).catch(err => {
              this.$message.warning("请求失败");
            })
        })
      },

    }
  }

</script>

<style scoped>
  /*根节点样式*/
  .friendlist_container {
    height: 100%;
    width: 100%;
  }

  .friend_search {
    margin-top: 20px;
    margin-left: 15px;
    padding: 0 10px;
  }

  .deleteColor {
    color: red;
  }

  .el-image {
    width: 70px;
    height: 70px;
    margin-left: 30px;
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
