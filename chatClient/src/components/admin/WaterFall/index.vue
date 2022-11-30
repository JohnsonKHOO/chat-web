<template>
  <!-- 简单瀑布流图片展示 -->
  <div id="list">
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
      <div class="list_box">
        <div class="list_item" :class="[ing % 2 == 0 ? 'even' : '']" v-for="(item, ing) of url" :key="ing">
          <el-image :src="require(
                'C://Users//Strix//Desktop//IntellijIdea//chat-web//src//resources//static//img//'
                + item)" />
        </div>
      </div>
    </van-list>
  </div>
</template>


<script>
  export default {
    data() {
      return {
        loading: false,
        finished: false,
        users: [],
        url: [],
      };
    },

    created() {
      //获取头像图片路径
      this.$axios
        .get('/user/listavatar')
        .then(res => {
            console.log(res.data)
          if (res.data.code === 200) {
            this.url = res.data.data;
          }
        }).catch(err => {
          this.$message.warning("请求失败");
        });

    },
    methods: {
      onLoad() {
        setTimeout(() => {
          // 加载状态结束
          this.loading = false;
          // 数据全部加载完成
          if (this.url.length >= 10) {
            this.finished = true;
          }
        }, 1000);
      },
    },
  };

</script>

<style scoped>
  .list_box {
    column-count: 2;
    column-gap: 1em;
    width: 100%;
    margin: 0 auto;
  }

  li {
    list-style: none;
  }

  .van-list__loading {
    clear: both;
  }

</style>
