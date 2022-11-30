<template>
  <div class="grouplist_container">
    <!--群组信息列表-->
    <el-main>
      <span>群组列表</span>
      <el-table :data="tableData.filter(data => !search || data.group.name.toLowerCase().includes(search.toLowerCase())
            || data.name.toLowerCase().includes(search.toLowerCase()))" highlight-current-row style="width: 100%;"
        class="grouplist_table" height="500">

        <el-table-column label="头像">
          <template v-slot="scope">
            <el-avatar :size="45" v-if="scope.row.group.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
            +scope.row.group.avatar)">
            </el-avatar>
            <el-avatar v-else :size="45">
              <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
            </el-avatar>
          </template>

        </el-table-column>
        <el-table-column prop="group.name" label="群名字"></el-table-column>
        <el-table-column prop="name" label="群内昵称"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot="header" slot-scope="scope">
            <el-input v-if="scope" v-model="search" size="mini" placeholder="输入关键字搜索" />
            <el-button type="text" icon="el-icon-plus" @click="add">新增群组</el-button>
          </template>

          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="info(scope.row.group.id);">查看</el-button>
            <el-button type="text" icon="el-icon-edit" @click="edit(scope.row)">更改群内昵称</el-button>
            <el-button type="text" icon="el-icon-delete" class="deleteColor" @click="del(scope.row.id)">退群</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog title="群组信息" :visible.sync="infoVisible" width="30%" close-on-click-modal="false"
        close-on-press-escape="false" show-close="false">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="群头像">
            <el-avatar :size="60" v-if="group.avatar" fit="contain" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
            +group.avatar)">
            </el-avatar>
            <el-avatar v-else :size="60">
              <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
            </el-avatar>
          </el-descriptions-item>

          <el-descriptions-item label="群名字">
            {{group.name}}
          </el-descriptions-item>
          <el-descriptions-item label="群主">{{group.user.nickname}}</el-descriptions-item>
          <el-descriptions-item label="群签名">
            {{group.signature}}
          </el-descriptions-item>


          <div slot="footer" class="dialog_footer">
            <el-button @click="infoVisible = false">关闭</el-button>
          </div>
        </el-descriptions>
      </el-dialog>

      <!--新增群组信息对话框-->
      <el-dialog title="新增群组信息" :visible.sync="addFormVisible" width="30%" close-on-click-modal="false"
        close-on-press-escape="false" show-close="false">
        <!--新增群组信息表单-->
        <el-form label-width="100px" :model="addForm" :rules="rules" ref="addForm" class="add_Form">
          <!--组名输入框-->
          <el-form-item label="组名" prop="name">
            <el-input v-model="addForm.name" placeholder="请输入群名字" clearable></el-input>
          </el-form-item>
          <!--编辑用户签名表单-->
          <el-form-item label="群签名" prop="signature">
            <el-input type="textarea" v-model="addForm.signature" placeholder="请输入群签名"></el-input>
          </el-form-item>

          <!-- 群头像上传 -->
          <el-form-item label="群头像上传：" style="margin-left: 30px; margin-top: 20px;">
            <el-upload ref="upfile" class="avatar-uploader" action="#" :auto-upload="false" :on-change="handleChange"
              :show-file-list="false" :limit="1" accept="image/png,image/gif,image/jpg,image/jpeg">
              <img v-if="avatar" :src="avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <!--新增群组信息对话框按钮-->
        <div slot="footer" class="dialog_footer">
          <el-button @click="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </el-dialog>

      <!--编辑群组信息对话框-->
      <el-dialog title="编辑群组信息" :visible.sync="editFormVisible" width="30%" close-on-click-modal="false"
        close-on-press-escape="false" show-close="false">
        <!--编辑群组信息表单-->
        <el-form label-width="100px" :model="editForm" :rules="rules" ref="editForm" class="edit_Form">
          <!--群组备注输入框-->
          <el-form-item label="群组备注：" prop="nickname">
            <el-input v-model="editForm.name" placeholder="请输入您的昵称" clearable></el-input>
          </el-form-item>

        </el-form>
        <!--编辑群组信息对话框按钮-->
        <div slot="footer" class="dialog_footer">
          <el-button @click="editFormVisible = false">取消</el-button>
          <el-button type="primary" @click="save2">更新</el-button>
        </div>
      </el-dialog>

    </el-main>
  </div>
</template>

<script>
  export default {
    name: 'Group',

    data() {
      return {
        userInfo: {},
        tableData: [{}],
        infoVisible: false,
        group: {
          user: {}
        },
        search: '',
        editFormVisible: false,
        editForm: {},
        addFormVisible: false,
        addForm: {},
        avatar: '',
        fileList: {},
        /*Form组件提供的表单验证功能，通过rules属性传入约定的验证规则，并将Form-Item的prop属性设置为需校验的字段名*/
        /*新增群组信息表单验证*/
        rules: {
          name: [{
            required: true,
            message: '请输入群名字',
            trigger: 'blur'
          }],

          account: [{
            required: true,
            message: '请选择群主账号',
            trigger: 'blur'
          }],
        }
      }
    },

    created() {
      this.userInfo = JSON.parse(sessionStorage.getItem('userInfo'));
      this.list();
    },

    mounted() {},

    methods: {

      handleChange(file, fileList) {
        this.fileList = fileList;
        console.log("file:" + this.fileList);
        this.avatar = URL.createObjectURL(file.raw);
        console.log("avatar:" + this.avatar);
      },

      submitForm() {
        console.log(this.addForm);
        this.$refs['addForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            this.addForm.ownerId = this.userInfo.id;
            var fd = new FormData();
            fd.append('group', JSON.stringify(this.addForm));
            if (JSON.stringify(this.fileList) === '{}') {

            } else {
              fd.append('file', this.fileList[0].raw);
            }
            this.$axios
              .post('/group/insert', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("注册成功！");
                  this.list();
                } else if (res.data.code === 400) {
                  this.$message.error("新增群组失败，账号已存在！");
                } else if (res.data.code === 600) {
                  this.$message.error("数据错误！");
                }
              })
              .catch(err => {
                this.$message.warning("请求失败");
              })
          } else {
            console.log('请输入正确信息');
            return false;
          }
        });
      },

      SubmitEditForm() {
        console.log(this.editForm);
        this.$refs['editForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('groupUser', JSON.stringify(this.editForm));
            this.$axios
              .post('/group/user/update', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("更新成功！");
                  this.list();
                } else if (res.data.code === 400) {
                  this.$message.error("群组账号已存在！");
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
        fd.append('userId', this.userInfo.id);
        fd.append('account', "");
        this.$axios
          .post('/group/user/list', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.tableData = res.data.data;
            } 
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },
      add() { //显示新增群组表单
        this.addForm = {};
        this.addFormVisible = true;
      },
      save() { //新增群组表单的保存
        this.$confirm('是否确定新增此群组？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => { //按下确定弹出消息
          this.submitForm();
        })
      },

      save2() { //编辑群组信息表单的保存
        this.$confirm('是否确定编辑此群组信息？', '提示', {
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
          .post('/group/info', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.group = res.data.data;
            } else {
              this.$message.error("没有数据！");
            }
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },

      del(id) { //删除按钮方法
        this.$confirm('是否确定删除此群组？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => { //按下确定弹出消息
          var fd = new FormData();
          fd.append('id', id);
          this.$axios
            .post('/group/user/delete', fd)
            .then(res => {
              if (res.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除群组成功！'
                });
                this.list();
              } else {
                this.$message({
                  type: 'error',
                  message: '删除群组失败'
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
  .grouplist_container {
    height: 100%;
    width: 100%;
  }

  .group_search {
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
