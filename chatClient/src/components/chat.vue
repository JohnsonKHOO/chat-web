<template>
  <div style="background-image: linear-gradient(to bottom right, #fc466b, #3f5efb); height: 100%; width: 100%;">
    <div class="main">
      <div class="column-1">
        
      </div>

      <div class="column-2">
        <div>
          <div style="text-align: center;border-bottom: 1px solid #ccc">好友</div>
          <div style="padding: 10px 0" v-for="user in users" :key="user.username">
            <span>{{ user.username }}</span>
            <i class="el-icon-chat-dot-round" style="margin-left: 10px; font-size: 16px; cursor: pointer"
              @click="chatUser = user.username"></i>
            <span style="font-size: 12px;color: limegreen; margin-left: 5px"
              v-if="user.username === chatUser">chatting...</span>
          </div>
        </div>
      </div>

      <div class="column-3">
        <div style="width: 100%; height:100% background-color: white;
                border-radius: 5px;">
          <div style="text-align: center; line-height: 50px;">
            聊天窗口
            <!-- （{{ chatUser }}） -->
          </div>
          <div style="height: 100%; overflow:auto; border-top: 1px solid #ccc" v-html="content"></div>
          <div style="height: 200px">
            <textarea v-model="text" style="margin-top: 313px; height: 160px; width: 94%; padding: 20px; border: none; border-top: 1px solid #ccc;
         border-bottom: 1px solid #ccc; outline: none"></textarea>
            <div style="position: absolute; margin-left: 660px; margin-top: -40px;">
              <el-button type="primary" size="mini" @click="send">发送</el-button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>

</template>
<script>
  let socket;
  export default {
    name: "chat",
    data() {
      return {
        // circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
        user: {},
        isCollapse: false,
        users: [{}],
        chatUser: '',
        text: "",
        messages: [{}],
        content: ''
      }
    },
    created() {
      this.init()
    },
    methods: {
      send() {
        if (!this.chatUser) {
          this.$message({
            type: 'warning',
            message: "请选择聊天对象"
          })
          return;
        }
        if (!this.text) {
          this.$message({
            type: 'warning',
            message: "请输入内容"
          })
        }
      },
      createContent(remoteUser, nowUser, text) { // 这个方法是用来将 json的聊天消息数据转换成 html的。
        let html
        // 当前用户消息
        if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
            "    <div class=\"tip left\">" + text + "</div>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-2\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            // "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "</div>";
        } else if (remoteUser) { // remoteUser表示远程用户聊天消息，蓝色的气泡
          html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
            "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
            "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
            // "    <img src=\"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\" style=\"object-fit: cover;\">\n" +
            "  </span>\n" +
            "  </div>\n" +
            "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
            "    <div class=\"tip right\">" + text + "</div>\n" +
            "  </div>\n" +
            "</div>";
        }
        console.log(html)
        this.content += html;
      },
      init() {

      }
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
    grid-template-columns: 60px 1.5fr 6.7fr;
    box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  }
  /* 测试中 */
  .column-1 {
    background-color: rgb(96, 96, 96);
  }

  .column-2 {
    background-color: rgb(245, 245, 245);
    width: 200px;
    line-height:  50px;
  }

  .column-3 {
    background-color: white;
  }

  .tip {
    color: white;
    text-align: center;
    border-radius: 10px;
    font-family: sans-serif;
    padding: 10px;
    width: auto;
    display: inline-block !important;
    display: inline;
  }

  .right {
    background-color: deepskyblue;
  }

  .left {
    background-color: forestgreen;
  }

</style>
