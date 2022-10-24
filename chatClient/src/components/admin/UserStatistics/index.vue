<template>
  <div class="chart">
    <div id="myChart"></div>
    <div id="myLine"></div>
  </div>
</template>

<script>
  export default {
    name: 'chart',
    data() {
      return {
        dataPie: [],
        dataLine: []
      }
    },
    created(){
      this.statisticPie();
      this.statisticLine();
    },

    mounted() {
      this.Draw();
    },
    methods: {
      statisticPie() {
        this.$axios.get('/user/statistics/pie')
          .then(res => {
            console.log(res.data);
            this.dataPie = res.data.data;
            console.log(this.dataPie);
      
          }).catch(err => {
            alert("跨域操作失败！")
          })
      },
      statisticLine() {
        this.$axios.get('/user/statistics/line')
          .then(res => {
            console.log(res.data);
            this.dataLine = res.data.data;
            console.log(this.dataLine);
          }).catch(err => {
            alert("跨域操作失败！")
          })
      },

      Draw() {
        let myChart = this.$echarts.init(document.getElementById('myChart'))
        let myLine = this.$echarts.init(document.getElementById('myLine'))

        myChart.setOption({
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
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [{
                value: this.dataPie.countMale,
                name: '男性用户'
              },
              {
                value: this.dataPie.countFemale,
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
        })

        myLine.setOption({
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
            data: ['10-20', '20-30', '30-40', '40-50', '50-60', '60-70', '70-80'],
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
              data: this.dataLine.male
            },
            {
              name: '女性用户',
              type: 'bar',
              tooltip: {
                valueFormatter: function (value) {
                  return value + ' 人';
                }
              },
              data: this.dataLine.female
            },
          ]
        })
      }
    }
  }

</script>


<style scoped>
  #myChart {
    float: left;
    height: 500px;
    width: 500px;
  }

  #myLine {
    float: left;
    height: 500px;
    width: 500px;
  }

</style>
