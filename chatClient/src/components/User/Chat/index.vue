<template>
  <div>
    <div style=" background-color: white;">
      <div style="text-align: center; line-height: 50px;">
        聊天窗口
        <!-- （{{ chatUser }}） -->
      </div>
      <div style="height: 100%; overflow:auto; border-top: 1px solid #ccc" v-html="content">
      </div>

      <textarea v-model="text" style="margin-top: 350px; height: 120px; width: 94%; padding: 20px; border: none; border-top: 1px solid #ccc;
       border-bottom: 1px solid white; outline: none;"></textarea>
      <div style=" margin-left: 650px; margin-top: -40px;">
        <el-button type="primary" size="mini" @click="send">发送</el-button>
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
        content: '',
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
