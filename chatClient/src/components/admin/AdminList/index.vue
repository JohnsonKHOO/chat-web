<template>
  <div class="adminlist_container">

    <!--管理员信息列表-->
    <el-main>
      <el-table
        :data="tableData.filter(data => !search || data.account.toLowerCase().includes(search.toLowerCase())
        || data.nickname.toLowerCase().includes(search.toLowerCase()))"
        highlight-current-row border style="width: 100%;" class="adminlist_table" height="500">
        <el-table-column prop="id" label="管理员id"></el-table-column>
        <el-table-column label="头像">
          <template v-slot="scope">
            <el-image v-if="scope.row.avatar"
              :src="require(
                'C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
                +scope.row.avatar)" />

          </template>
        </el-table-column>
        <el-table-column prop="account" label="账号"></el-table-column>
        <el-table-column prop="nickname" label="昵称"></el-table-column>
        <el-table-column prop="createTime" :formatter="susSet" label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" :formatter="susSet" label="最近更新时间"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot="header" slot-scope="scope">
            <el-input v-if="scope" v-model="search" size="mini" placeholder="输入关键字搜索" />
            <el-button type="text" icon="el-icon-plus" @click="add">新增管理员</el-button>
          </template>
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="edit(scope.row)">编辑</el-button>
            <el-button type="text" icon="el-icon-delete" class="deleteColor" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--新增管理员信息对话框-->
      <el-dialog title="新增管理员信息" :visible.sync="addFormVisible" width="30%" close-on-click-modal="false"
        close-on-press-escape="false" show-close="false">
        <!--新增管理员信息表单-->
        <el-form label-width="100px" :model="addForm" :rules="rules" ref="addForm" class="add_Form">
          <!--姓名输入框-->
          <el-form-item label="昵称：" prop="nickname">
            <el-input v-model="addForm.nickname" placeholder="请输入您的昵称" clearable></el-input>
          </el-form-item>
          <!--账号输入框-->
          <el-form-item label="账号：" prop="account">
            <el-input v-model="addForm.account" placeholder="请输入您的账号" clearable></el-input>
          </el-form-item>
          <!--性别单选框-->
          <el-form-item label="性别：" prop="sex">
            <el-radio-group v-model="addForm.sex">
              <el-radio label="0">男</el-radio>
              <el-radio label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <!--日期选择器-->
          <el-form-item label="出生日期：" prop="birthday">
            <el-date-picker v-model="addForm.birthday" type="date" placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <!--密码输入框-->
          <el-form-item label="密码：" prop="password">
            <el-input type="password" v-model="addForm.password" placeholder="请输入您的密码" clearable show-password>
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
        <!--新增管理员信息对话框按钮-->
        <div slot="footer" class="dialog_footer">
          <el-button @click="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </el-dialog>

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
        <!--编辑管理员信息对话框按钮-->
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

    name: 'AdminList',

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
        tableData: [{}],
        addFormVisible: false,
        editFormVisible: false,
        search: '',
        addForm: {

        },
        editForm: {
   
        },
        avatar: '',
        fileList: {},
        /*Form组件提供的表单验证功能，通过rules属性传入约定的验证规则，并将Form-Item的prop属性设置为需校验的字段名*/
        /*新增管理员信息表单验证*/
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

          }],
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
        }
      }
    },

    mounted() {
      this.list();
    },

    methods: {
      //上传头像
      handleChange(file, fileList){
        this.fileList = fileList;
        console.log("file:"+ this.fileList);
        this.avatar = URL.createObjectURL(file.raw);
        console.log("avatar:" + this.avatar);
      },
      //提交新增表单
      submitForm() {
        console.log(this.addForm);
        this.$refs['addForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('user', JSON.stringify(this.addForm));
            fd.append('roleId', 0);
            if(JSON.stringify(this.fileList) === '{}'){
              console.log(this.fileList);
            }
            else{
              console.log(this.fileList);
              fd.append('file', this.fileList[0].raw);
            }
            this.$axios
              .post('/register', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("注册成功！");
                  this.list();
                } else if (res.data.code === 400) {
                  this.$message.error("新增管理员失败，账号已存在！");
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
      //提交编辑表单
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
                  this.list();
                } else if (res.data.code === 400) {
                  this.$message.error("管理员账号已存在！");
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
        fd.append('account', "");
        fd.append('roleId', 0)
        this.$axios
          .post('/user/search', fd)
          .then(res => {
            if(res.data.code === 200){
              this.tableData = res.data.data;
            }else{
              this.$message.error("没有数据！");
            }
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },
      add() { //显示新增管理员表单
        this.addForm = {};
        this.addFormVisible = true;
      },
      save() { //新增管理员表单的保存
        this.$confirm('是否确定新增此管理员？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => { //按下确定弹出消息
          this.submitForm();
        })
      },
      save2() { //编辑管理员信息表单的保存
        this.$confirm('是否确定编辑此管理员信息？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => {
          this.SubmitEditForm();
        })
      },

      edit(obj) { //编辑按钮方法
        console.log(obj);
        this.editForm = obj;
        this.editFormVisible = true;
      },

      del(id) { //删除按钮方法
        this.$confirm('是否确定删除此管理员？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => { //按下确定弹出消息
          console.log(id);
          var fd = new FormData();
          fd.append('id', id);
          this.$axios
            .post('/user/delete', fd)
            .then(res => {
              if (res.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除管理员成功！'
                });
                this.list();
              } else {
                this.$message({
                  type: 'error',
                  message: '删除管理员失败'
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
  .adminlist_container {
    height: 100%;
    width: 100%;
  }

  .admin_search {
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
