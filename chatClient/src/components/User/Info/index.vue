<template>
  <div>
    <!-- 显示用户个人所有信息 -->
    <el-descriptions title="个人主页" :column="1" border>
      <el-descriptions-item label="头像">
        <el-avatar :size="60" v-if="user.avatar" fit="contain" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
          +user.avatar)">
        </el-avatar>
        <el-avatar v-else :size="60">
          <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
        </el-avatar>

        <el-link type="primary" icon="el-icon-edit" v-on:click="editAvatar" style="margin-left: 20px;">修改头像</el-link>
      </el-descriptions-item>

      <el-descriptions-item label="账号">
        {{user.account}}
      </el-descriptions-item>
      <el-descriptions-item label="昵称">
        {{user.nickname}}
      </el-descriptions-item>
      <el-descriptions-item label="性别">
        <span v-if="user.sex == 0">男</span>
        <span v-if="user.sex == 1">女</span>
      </el-descriptions-item>
      <el-descriptions-item label="年龄">{{user.age}}</el-descriptions-item>
      <el-descriptions-item label="个人签名">
        {{user.signature}}
      </el-descriptions-item>
      <el-descriptions-item label="创建时间">
        {{user.createTime}}
        <el-link type="primary" icon="el-icon-edit" v-on:click="editSignature" style="margin-left: 20px;">修改签名</el-link>
        <el-link type="primary" icon="el-icon-edit" v-on:click="edit" style="margin-left: 20px;">修改用户信息</el-link>
      </el-descriptions-item>

    </el-descriptions>
    <!--编辑用户头像对话框-->
    <el-dialog title="更新用户头像" :visible.sync="editAvatarFormVisible" width="30%" close-on-click-modal="false"
      close-on-press-escape="false" show-close="false">
      <!--编辑用户头像表单-->
      <el-form label-width="100px" :model="editAvatarForm" ref="editAvatarForm" class="editAvatar_Form">
        <!-- 头像上传 -->
        <el-form-item label="头像上传：">
          <el-upload ref="upfile" class="avatar-uploader" action="#" :auto-upload="false" :on-change="handleChange"
            :show-file-list="false" :limit="1" accept="image/png,image/gif,image/jpg,image/jpeg">
            <img v-if="editAvatarForm.avatar" :src="editAvatarForm.avatar" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <!--编辑用户信息对话框按钮-->
      <div slot="footer" class="dialog_footer">
        <el-button @click="editAvatarFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save1">更新</el-button>
      </div>
    </el-dialog>

    <!--编辑用户签名对话框-->
    <el-dialog title="更新用户签名" :visible.sync="editSignatureFormVisible" width="30%" close-on-click-modal="false"
      close-on-press-escape="false" show-close="false">
      <!--编辑用户签名表单-->
      <el-form label-width="100px" :model="editSignatureForm" ref="editSignatureForm" class="editSignature_Form">
        <el-form-item label="个人签名" prop="signature">
          <el-input type="textarea" v-model="editSignatureForm.signature" placeholder="请输入签名"></el-input>
        </el-form-item>

      </el-form>
      <!--编辑用户信息对话框按钮-->
      <div slot="footer" class="dialog_footer">
        <el-button @click="editSignatureFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save2">更新</el-button>
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
      </el-form>
      <!--编辑用户信息对话框按钮-->
      <div slot="footer" class="dialog_footer">
        <el-button @click="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save3">更新</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Info",
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
        user: {},
        editFormVisible: false,
        editForm: {
          id: '',
          account: '',
          nickname: '',
          password: '',
          birthday: '',
          sex: '',
        },
        editAvatarForm: {
          avatar: '',
          fileList: {},
        },
        editAvatarFormVisible: false,

        editSignatureForm: {
          signature: '',
        },
        editSignatureFormVisible: false,

        /*Form组件提供的表单验证功能，通过rules属性传入约定的验证规则，并将Form-Item的prop属性设置为需校验的字段名*/
        /*新增用户信息表单验证*/
        rules: {
          nickname: [{
            required: true,
            message: '请输入您的昵称',
            trigger: 'blur'
          },
          {
              min: 6,
              max: 15,
              message: '长度在6到15个字符',
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
        }

      }
    },
    mounted() {
      this.getSessionStorage();
    },
    methods: {
      //更改数据后传回给父组件，更新用户最新状态
      toParent() {
        this.$emit("toParent", this.user);
      },
      //上传头像
      handleChange(file, fileList) {
        this.editAvatarForm.fileList = fileList;
        this.editAvatarForm.avatar = URL.createObjectURL(file.raw);
      },
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
      //更改头像表单提交
      EditAvatarForm() {
        console.log(this.editAvatarForm);
        this.$refs['editAvatarForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('id', this.user.id);
            if (JSON.stringify(this.editAvatarForm.fileList) === '{}') {
              console.log(this.fileList);
            } else {
              console.log(this.fileList);
              fd.append('file', this.editAvatarForm.fileList[0].raw);
            }
            this.$axios
              .post('/user/updateavatar', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("更新头像成功！");
                  sessionStorage.setItem('userInfo', JSON.stringify(res.data.data))
                  //更新sessionStorage与传输数据给父组件
                  this.getSessionStorage();
                  this.toParent();
                } else if (res.data.code === 400) {
                  this.$message.error("头像为null！");
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
      save1() { //编辑用户头像表单的保存
        this.$confirm('是否确定编辑此用户信息？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => {
          this.EditAvatarForm();
        })
      },

      editAvatar() { //编辑按钮方法
        this.editAvatarFormVisible = true;
      },
      //编辑个人签名表单提交
      EditSignatureForm() {
        console.log(this.editSignature);
        this.$refs['editSignatureForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('id', this.user.id);
            fd.append('signature', this.editSignatureForm.signature);
            this.$axios
              .post('/user/updatesignature', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("更新签名成功！");
                  sessionStorage.setItem('userInfo', JSON.stringify(res.data.data))
                  //更新sessionStorage与传输数据给父组件
                  this.getSessionStorage();
                  this.toParent();
                } else if (res.data.code === 400) {
                  this.$message.error("更新签名失败");
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
      save2() { //编辑个人签名表单的保存
        this.$confirm('是否确定编辑此用户信息？', '提示', {
          confirmButtonText: '确定', //弹出框的确定提交按钮
          cancelButtonText: '取消', //弹出框的取消提交按钮
          type: 'warning', //弹出框类型
          center: true
        }).then(() => {
          this.EditSignatureForm();
        })
      },

      editSignature() { //编辑个人签名按钮方法
        this.editSignatureFormVisible = true;
      },
      //编辑用户基本信息表单提交
      SubmitEditForm() {
        console.log(this.editForm);
        this.$refs['editForm'].validate(valid => {
          /*若表单验证成功则注册成功，否则注册失败*/
          console.log(valid);
          if (valid) {
            var fd = new FormData();
            fd.append('user', JSON.stringify(this.editForm));
            // fd.append('file', this.fileList[0].raw);
            this.$axios
              .post('/user/update', fd)
              .then(res => {
                console.log(res.data)
                if (res.data.code === 200) {
                  this.$message.success("更新成功！");
                  sessionStorage.setItem('userInfo', JSON.stringify(res.data.data))
                  //更新sessionStorage与传输数据给父组件
                  this.getSessionStorage();
                  this.toParent();
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
      save3() { //编辑用户信息表单的保存
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

    }
  }

</script>

<style scoped>
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
