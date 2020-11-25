<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import axios from 'axios'
export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '480px'
    },
    autoResize: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      graph_categories: [],
      graph_nodes: [],
      graph_links: [],
      graph_length: 0,
      graph: {},
      chart: null
    }
  },
  watch: {
    // chartData: {
    //   deep: true,
    //   handler(val) {
    //     this.setOptions(val)
    //   }
    // }
  },
  mounted() {
    this.$nextTick(() => {
      this.loadData()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    loadData() {
      axios.post('http://localhost:10088//MedicalCategory/get').then((response) => {
      // console.log(response.data)
        this.graph_length = 0
        this.graph_length = response.data.length
        var num = 0

        for (var i = 0; i < this.graph_length; i++) {
        // 类别legend
          var cate = {}
          cate['name'] = response.data[i].name
          // 节点
          var node = {}
          node['name'] = response.data[i].name
          node['category'] = i
          node['symbolSize'] = 50
          this.graph_categories[i] = cate
          this.graph_nodes[i] = node
          for (var j = 0; j < response.data[i].link.length; j++) {
          // eslint-disable-next-line no-unused-vars
            var link = {}
            link['source'] = response.data[i].name
            link['target'] = response.data[i].link[j][0]
            this.graph_links[num] = link
            num++
          }
        }

        // for (var k = 0; k < this.graph_length; k++) {
        //   var datason = {}
        //   datason['id'] = k + 1
        //   datason['label'] = response.data[k].name
        //   datason['children'] = []
        //   console.log('66666' + datason['label'])
        // this.data1[k] = datason
        // }
        this.initChart()
      // console.log(response.data)
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        backgroundColor: '#ffffff',
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        legend: [{ show: true }],
        series: [
          {
            name: 'Les Miserables',
            type: 'graph',
            layout: 'circular',
            circular: {
              rotateLabel: true
            },
            data: this.graph_nodes,
            links: this.graph_links,
            categories: this.graph_categories,
            roam: true,
            label: {
              position: 'right',
              formatter: '{b}',
              show: true,
              textStyle: { fontSize: 16 }
            },
            lineStyle: {
              color: 'source',
              curveness: 0.6,
              width: 3
            }

          }
        ]
      })
      this.chart.on(
        'click',
        (param) => {
          // 可以使用下面的方式进行路由的切换
          // alert(param.name)
          this.$router.push({
            path: 'ForceChart',
            query: { category: param.name }
          })
        }
      )
    }
  }
}
</script>
