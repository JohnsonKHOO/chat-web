<template>
  <div>
    <el-container>
      <el-scrollbar style="width: 42%; height: 500px;">
        <el-aside class="column-1">
          <div style="text-align: center;border-bottom: 1px solid #ccc">好友</div>
          <!-- 好友 -->
          <div class="contact_box" style="padding: 3px; margin: 1px; font-size: 15px; border: 1px dotted;"
            v-for="(friend, index) in friends" :key="friend.user.account" :tabindex="index" 
            @click="chatUser = friend.user.account ; nickname = friend.user.nickname;
             listMessage(friend.user.id); htmlText = ''">

            <el-avatar style="margin-bottom: -15px;" :size="40" v-if="friend.user.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
            + friend.user.avatar)">
            </el-avatar>
            <el-avatar v-else :size="40" style="margin-bottom: -15px;">
              <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
            </el-avatar>

            <span> {{friend.user.nickname}}</span>
            <!-- 通过后端获取的在线用户列表查找好友是否在内，是则显示在线 -->
            <span v-if="online.includes(friend.user.account)" class="el-icon-monitor" 
            style="color: limegreen">在线</span>
            <span v-else style="color: red" class="el-icon-switch-button">离线</span>
            <span style="font-size: 12px;color: limegreen; margin-left: 5px"
              v-if="friend.user.account === chatUser">chatting...</span>
          </div>

          <!-- 群组 -->
          <div class="contact_box" style="padding: 3px; margin: 1px; font-size: 15px; border: 1px dotted;"
            v-for="(group, index) in groups" :key="group.group.id" :tabindex="index" 
            @click="chatUser = group.group.id ; nickname = group.group.name; groupInit();
            htmlText=''; listGroupMessage(group.group.id)">

            <el-avatar style="margin-bottom: -15px;" :size="40" v-if="group.group.avatar" :src="require('C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
            + group.group.avatar)">
            </el-avatar>
            <el-avatar v-else :size="40" style="margin-bottom: -15px;">
              <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" />
            </el-avatar>
            <span> {{group.group.name}}</span>

            <span style="font-size: 12px;color: limegreen; margin-left: 5px"
              v-if="group.group.id === chatUser">chatting...</span>
          </div>
        </el-aside>
      </el-scrollbar>
      <!-- 聊天窗口 -->
      <div style=" background-color: white; width: 100%; height: 350px;">
        <div style="text-align: center; line-height: 50px;" v-if="nickname">
          {{nickname}}
        </div>
        <div style="text-align: center; line-height: 50px;" v-else>聊天窗口
        </div>
        <!-- 把htmlText绑定，显示消息渲染 -->
        <div style=" height: 300px; overflow:auto; border-top: 1px solid #ccc;" v-html="htmlText"></div>
        <div>
          <textarea v-model="text">

          </textarea>
          <div style="text-align: right; margin-top: -60px; margin-right: 10px;">
            <el-button type="primary" size="mini" @click="send">发送</el-button>
          </div>
        </div>
      </div>
    </el-container>
  </div>
</template>
<script>
  var socket = null;
  var groupSocket = null;

  export default {

    name: "Chat",
    data() {
      return {
        userInfo: {},
        isCollapse: false,
        friends: [{

        }],
        groups: [],
        text: '',
        //在线用户列表
        online: [],
        nickname: '',
        chatUser: '',
        messages: [],
        htmlText: '',
      }
    },
    created() {
      this.init();
      this.getFriend();
  
    },
    methods: {
      //获取好友列表
      getFriend() {
        var fd = new FormData();
        fd.append('userId', this.userInfo.id);
        fd.append('account', "");
        this.$axios
          .post('/friend/search', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.friends = res.data.data;
              console.log(this.friends);
            }
          })
          .catch(err => {
            this.$message.warning("请求失败");
          });
      },
      //获取该用户所在群信息
      getGroup() {
        var fd = new FormData();
        fd.append('userId', this.userInfo.id);
        fd.append('account', "");
        this.$axios
          .post('/group/user/list', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.groups = res.data.data;
              console.log(this.friends);
            }
          })
          .catch(err => {
            this.$message.warning("请求失败");
          });
      },
      //根据用户id，好友id获取聊天记录
      listMessage(id) {
        var fd = new FormData();
        fd.append('fromUserId', this.userInfo.id);
        fd.append('toUserId', id);
        fd.append('content', "");
        this.$axios
          .post('/message/list', fd)
          .then(res => {
            if (res.data.code === 200) {
              this.messages = res.data.data;
              for(var index in this.messages) {
                console.log(this.messages[index])
                if (this.messages[index].fromUserId === this.userInfo.id) {
                  this.createContent(null, this.userInfo.nickname, this.messages[index].content, this.messages[index].createTime);
                } else {
                  this.createContent(this.nickname, null, this.messages[index].content, this.messages[index].createTime);
                }
              }
            }
          })
          .catch(err => {
            this.$message.warning("请求失败");
          });
      },
      //显示当前群id的消息记录
      listGroupMessage(id) {
        var fd = new FormData();
        fd.append('groupId', id);
        fd.append('content', "");
        this.$axios
          .post('/group/message/list', fd)
          .then(res => {
            if (res.data.code === 200) {

            }
          })
          .catch(err => {
            this.$message.warning("请求失败");
          });
      },

      send() {
        if (!this.chatUser) {
          this.$message({
            type: 'warning',
            message: "请选择聊天对象"
          })
          return;
        }
        console.log(this.text);
        if (!this.text) {
          this.$message({
            type: 'warning',
            message: "请输入内容"
          })
        } else {
          if (typeof (WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
          } else {
            console.log("您的浏览器支持WebSocket");
            // 组装待发送的消息 json
            // {"from": "zhang", "to": "admin", "text": "聊天文本"}
            var message = {
              from: this.userInfo.account,
              to: this.chatUser,
              text: this.text
            }
            socket.send(JSON.stringify(message)); // 将组装好的json发送给服务端，由服务端进行转发

            // 构建消息内容，本人消息
            this.createContent(null, this.userInfo.nickname, this.text, null)
            this.text = '';
          }
        }
      },

      createContent(remoteUser, nowUser, text, time) {
        // 这个方法是用来将 json的聊天消息数据转换成 html的。
        var html // 当前用户消息
        console.log(time)
        if (nowUser) {
          // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" + 
            "  <div style=\"font-size: 10px;text-align: center \">" + time + "</div>\n" +
            "  <div class=\"el-col el-col-22\" style=\"font-size: 15px; text-align: right; padding-right: 10px\">\n" +
            "    <div class=\"tip left\">" + text + "</div>\n" +
            "  </div>\n" +
  
            "  <div class=\"el-col el-col-2\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            nowUser +
            " style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "</div>";
        } else if (remoteUser) { // remoteUser表示远程用户聊天消息，蓝色的气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" + 
            "  <div style=\"font-size: 10px;text-align: center; margin: 0px; padding: 0px\">" + time + "</div>\n" +
            "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            remoteUser +
            "  </span>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-22\" style=\"font-size: 15px;text-align: left; padding-left: 10px\">\n" +
            "    <div class=\"tip right\">" + text + "</div>\n" +
            "  </div>\n" +
            "</div>";
        }

        console.log(html);
        this.htmlText += html;
      },
      init() {
        this.userInfo = sessionStorage.getItem("userInfo") ? JSON.parse(sessionStorage.getItem("userInfo")) : {}
        let account = this.userInfo.account;
        let _this = this;
        if (typeof (WebSocket) == "undefined") {
          console.log("您的浏览器不支持WebSocket");
        } else {
          console.log("您的浏览器支持WebSocket");
          let socketUrl = "ws://localhost:9000/chat/" + account
          if (socket != null) {
            socket.close();
            socket = null;
          }
          // 开启一个websocket服务
          socket = new WebSocket(socketUrl);
          //打开事件
          socket.onopen = function () {
            console.log("websocket已打开");
          };
          //  浏览器端收消息，获得从服务端发送过来的文本消息
          socket.onmessage = function (msg) {
            console.log("收到数据====" + msg.data)
            var data = JSON.parse(msg
              .data) // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}
            if (data.users) { // 获取在线人员信息
              // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
              _this.online = JSON.stringify(data.users.filter(user => user.account !== account));
              console.log(_this.online);
            } else {
              // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
              //  // {"from": "zhang", "text": "hello"}
              if (data.from === _this.chatUser) {
                // 构建消息内容
                _this.createContent(data.from, null, data.text, null)
              }
            }
          };
          //关闭事件
          socket.onclose = function () {
            console.log("websocket已关闭");
          };
          //发生了错误事件
          socket.onerror = function () {
            console.log("websocket发生了错误");
          }
        }
      },

      // 未完成部分，群内websocket服务报错
      groupInit() {
        this.userInfo = sessionStorage.getItem("userInfo") ? JSON.parse(sessionStorage.getItem("userInfo")) : {}
        let account = this.userInfo.account;
        let _this = this;
        if (typeof (WebSocket) == "undefined") {
          console.log("您的浏览器不支持WebSocket");
        } else {
          console.log("您的浏览器支持WebSocket");
          let socketUrl = "ws://localhost:9000/group/chat/" + account + "/" + this.chatUser
          if (groupSocket != null) {
            groupSocket.close();
            groupSocket = null;
          }
          // 开启一个websocket服务
          groupSocket = new WebSocket(socketUrl);
          //打开事件
          groupSocket.onopen = function () {
            console.log("groupsocket已打开");
          };
          //  浏览器端收消息，获得从服务端发送过来的文本消息
          groupSocket.onmessage = function (msg) {
            console.log("收到数据====" + msg.data)
            var data = JSON.parse(msg
              .data) // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}
            if (data.groupUsers) { // 获取在线人员信息
              // 获取当前连接的所有用户信息，并且排除自身，自己不会出现在自己的聊天列表里
              _this.online = JSON.stringify(data.groupUsers);
              console.log(_this.online);
            } else {
              // 如果服务器端发送过来的json数据 不包含 users 这个key，那么发送过来的就是聊天文本json数据
              //  // {"from": "zhang", "text": "hello"}
              if (data.from === _this.chatUser) {
                // 构建消息内容
                _this.createContent(data.from, null, data.text, null)
              }
            }
          };
          //关闭事件
          groupSocket.onclose = function () {
            console.log("groupsocket已关闭");
          };
          //发生了错误事件
          groupSocket.onerror = function () {
            console.log("groupsocket发生了错误");
          }
        }
      },
      //向群发送消息
      sendGroup() {
        if (!this.chatUser) {
          this.$message({
            type: 'warning',
            message: "请选择聊天对象"
          })
          return;
        }
        console.log(this.text);
        if (!this.text) {
          this.$message({
            type: 'warning',
            message: "请输入内容"
          })
        } else {
          if (typeof (WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
          } else {
            console.log("您的浏览器支持WebSocket");
            // 组装待发送的消息 json
            // {"from": "zhang", "to": "admin", "text": "聊天文本"}
            var message = {
              from: this.userInfo.account,
              to: this.chatUser,
              text: this.text
            }
            groupSocket.send(JSON.stringify(message)); // 将组装好的json发送给服务端，由服务端进行转发

            // 构建消息内容，本人消息
            this.createContent(null, this.userInfo.nickname, this.text, null)
            this.text = '';
          }
        }
      },
    }
  }

</script>
<style>
  .contact_box {
    background-color: white;
    cursor: pointer;
  }

  .contact_box:hover {
    background-color: rgb(228, 228, 228);
  }

  .contact_box:focus {
    background-color: rgb(196, 196, 196);
  }



  .tip {
    color: white;
    text-align: center;
    border-radius: 10px;
    font-family: sans-serif;

    padding: 0px 10px;
    width: auto;
    display: inline-block !important;

  }

  .right {
    background-color: deepskyblue;
  }

  .left {
    background-color: rgb(82, 201, 82);
  }

  textarea {
    width: 100%;
    height: 170px;
    border: none;
    border-top: 1px solid #ccc;
    border-bottom: 1px solid white;
    outline: none;
    resize: none;
  }

</style>
