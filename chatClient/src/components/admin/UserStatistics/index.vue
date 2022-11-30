<template>
  <div class="chart">
    <!-- 数据可视化 -->
    <div id="myPie" class="column-1"></div>
    <div id="onlineCount" class="column-2">
      <span style="padding: 25px auto; font-size: 25px;">在线人数:</span>
      <br><br><br>
      <i style="  font-size: 40px;">{{this.online}}</i>
      <br><br><br> <br><br><br>
      <span style="padding: 25px auto; font-size: 25px;">总用户数量:</span>
      <br><br><br>
      <i style="  font-size: 40px;">{{this.count}}</i>
    </div>
    <div id="myLine" class="column-3"></div>
  </div>
</template>

<script>
  export default {
    name: 'chart',
    data() {
      return {
        online: '',
        count: '',
      }
    },

    mounted() {
      this.statisticPie();
      this.statisticLine();
      this.onlineCount();
      this.countUser();
    },
    methods: {
      //pie图请求数据
      statisticPie() {
        this.$axios.get('/user/statistics/pie')
          .then(res => {
            //渲染pie图
            this.DrawPie(res.data.data);
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },
      //条状图请求数据
      statisticLine() {
        this.$axios.get('/user/statistics/line')
          .then(res => {
            //渲染条形图
            this.DrawLine(res.data.data);
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },
      //请求在线人数
      onlineCount() {
        console.log("----online----")
        this.$axios.get('/user/online')
          .then(res => {
            this.online = res.data.data;
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },
      //请求总用户数量
      countUser(){
        this.$axios.get('/user/statistics/count')
          .then(res => {
            this.count = res.data.data;
          }).catch(err => {
            this.$message.warning("请求失败");
          })
      },

      DrawPie(pie) {

        let optionPie = {
          title: {
            text: '全用户男女比例',
            subtext: 'pie图',
            left: 'center',
            top: '10%'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [{
            name: '全用户男女比例',
            type: 'pie',
            radius: '50%',
            data: [{
                value: '',
                name: '男性用户'
              },
              {
                value: '',
                name: '女性用户'
              },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }]
        }
        //根据id初始化echart
        let myPie = this.$echarts.init(document.getElementById('myPie'));
        //传入数据
        optionPie.series[0].data[0].value = pie.countMale;
        optionPie.series[0].data[1].value = pie.countFemale;

        myPie.setOption(optionPie);

      },

      DrawLine(line) {
        let optionLine = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            }
          },
          toolbox: {
            feature: {
              dataView: {
                show: true,
                readOnly: false
              },
              magicType: {
                show: true,
                type: ['line', 'bar']
              },
              saveAsImage: {
                show: true
              }
            }
          },
          legend: {
            data: ['男性用户', '女性用户']
          },
          xAxis: [{
            type: 'category',
            name: '年龄',
            data: ['10-20', '20-30', '30-40', '40-50', '50-60', '60-70', '70-80', '>80'],
            axisPointer: {
              type: 'shadow'
            },
            axisLabel: {
              formatter: '{value} 岁'
            }
          }],
          yAxis: [{
              type: 'value',
              name: '人数',
              min: 0,
              max: 100,
              interval: 10,
              axisLabel: {
                formatter: '{value} 人'
              }
            },
            {
              type: 'value',
              name: '',
              min: 0,
              max: 0,
              interval: 0,

            }
          ],
          series: [{
              name: '男性用户',
              type: 'bar',
              tooltip: {
                valueFormatter: function (value) {
                  return value + ' 人';
                }
              },
              data: '',
            },
            {
              name: '女性用户',
              type: 'bar',
              tooltip: {
                valueFormatter: function (value) {
                  return value + ' 人';
                }
              },
              data: '',
            },
          ]
        }
        //根据id初始化echart
        let myLine = this.$echarts.init(document.getElementById('myLine'));
        //传入数据
        optionLine.series[0].data = line.male;
        optionLine.series[1].data = line.female;
        myLine.setOption(optionLine);
      }
    }
  }

</script>


<style scoped>
  .chart {
    display: grid;
    grid-template-columns: 400px 0.6fr 0.95fr;
  }

  .column-1 {
    width: auto !important;
    height: 550px;
  }

  .column-2 {
    width: auto !important;
    margin-top: 50px;
  }

  .column-3 {
    width: auto !important;
    height: 500px;
  }

</style>
