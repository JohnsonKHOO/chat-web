<template>
  <div class="userlist_container">

    <!--搜索筛选-->
    <el-form :inline="true" :model="userlistForm" class="user_search">
      <el-form-item label="搜索：">
        <el-input v-model="userlistForm.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="add">新增用户</el-button>
        <el-button type="primary" icon="el-icon-refresh" @click="getlist">刷新</el-button>
      </el-form-item>
    </el-form>
    <!--用户信息列表-->
    <el-main>
      <el-table :data="tableData" highlight-current-row border style="width: 100%;" class="userlist_table" height="500">
        <el-table-column prop="id" label="id"></el-table-column>
        <el-table-column prop="avatar" label="头像"></el-table-column>
        <el-table-column prop="nickname" label="昵称"></el-table-column>
        <el-table-column prop="account" label="账号"></el-table-column>
        <el-table-column prop="createTime" :formatter="susSet" label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" :formatter="susSet" label="最近更新时间"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="edit(scope.row)">编辑</el-button>
            <el-button type="text" icon="el-icon-delete" class="deleteColor" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--新增用户信息对话框-->
      <el-dialog title="新增用户信息" :visible.sync="addFormVisible" width="30%" close-on-click-modal="false"
        close-on-press-escape="false" show-close="false">
        <!--新增用户信息表单-->
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
            <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="addForm.avatar" :src="addForm.avatar" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <!--新增用户信息对话框按钮-->
        <div slot="footer" class="dialog_footer">
          <el-button @click="addFormVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </el-dialog>

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
            <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
              <img v-if="editForm.avatar" :src="editForm.avatar" class="avatar">
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



    </el-main>
  </div>
</template>

<script>
  export default {

    name: 'UserList',

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
        tableData: [{

        }],
        addFormVisible: false,
        editFormVisible: false,
        userlistForm: {
        },
        addForm: {
          account: '',
          nickname: '',
          password: '',
          birthday: '',
          sex: '',
          avatar: '',
        },
        editForm: {
          id: '',
          account: '',
          nickname: '',
          password: '',
          birthday: '',
          sex: '',
          avatar: '',
        },
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

          }],
          birthday: [{
              required: true,
              message: '请输入出生日期',
              trigger: 'blur'
            },
            {
              validator: validateBirth,
              trigger: 'blur'
            }],
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
            }],
        }
      }
    },

    mounted() {
      this.getlist();
    },

    methods: {

      handleAvatarSuccess(res, file) {
        this.registerForm.avatar = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },

      submitForm() {
        console.log(this.addForm);
        this.$refs['addForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            this.$axios
              .post('/register', this.$qs.stringify(this.addForm))
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("注册成功！");
                } else if (res.data.code === 400) {
                  this.$message.error("注册失败，账号已存在！");
                } else if (res.data.code === 600) {
                  this.$message.error("数据错误！");
                }
              })
              .catch(err => {
                alert("操作失败！")
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
            this.$axios
              .put('/user/update', this.$qs.stringify(this.editForm))
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("更新成功！");
                } else if (res.data.code === 400) {
                  this.$message.error("更新用户数据失败！");
                } 
              })
              .catch(err => {
                alert("操作失败！")
              })
          } else {
            console.log('请输入正确信息');
            return false;
          }
        });
      },

      search() { //搜索按钮方法
        console.log("get all data");
        let param = new URLSearchParams();
        param.append("id",this.userlistForm.name);
        this.$axios
          .post('/user/getbyid', param)
          .then(res => {
            console.log(res.data);
            this.tableData = res.data.data;
          }).catch(err => {
            alert("跨域操作失败！")
          })
      },
      add() { //显示新增用户表单
        this.addForm = {};
        this.addFormVisible = true;
      },
      save() { //新增用户表单的保存
        this.$confirm('是否确定新增此用户？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => { //按下确定弹出消息
          this.submitForm();
        })
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

      edit(obj) { //编辑按钮方法
        console.log(obj);
        this.editForm = obj;
        this.editFormVisible = true;
      },

      del(id) { //删除按钮方法
        this.$confirm('是否确定删除此用户？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => { //按下确定弹出消息
          console.log(id);
          this.$axios
            .delete('/user/delete', this.$qs.parse(id)
            )
            .then(res => {
              if (res.data.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除用户成功！'
                });
                this.getdata();
              } else {
                this.$message({
                  type: 'error',
                  message: '删除用户失败'
                });
              }
            }).catch(err => {
              alert("跨域操作失败！")
            })
        })
      },
      getlist() {
        console.log("get list");
        this.$axios.get('/user/list')
          .then(res => {
            console.log(res.data);
            this.tableData = res.data.data;
            console.log(this.tableData);
          }).catch(err => {
            alert("跨域操作失败！")
          })

      },
    }
  }

</script>

<style scoped>
  /*根节点样式*/
  .userlist_container {
    height: 100%;
    width: 100%;
  }

  .user_search {
    margin-top: 20px;
    margin-left: 15px;
    padding: 0 10px;
  }

  .deleteColor {
    color: red;
  }

</style>
