<template>
  <div class="bg">
    <el-row type="flex" class="row1" justify="center">
      <el-col :span="21" class="col1">
        <div class="Button">
          <button class="butt" @click="turnback">Return Back</button>
        </div>
      </el-col>
    </el-row>
    <el-row type="flex" class="row2" justify="center">
      <el-col :span="21" class="body_L">
        <el-col :span="9">
          <div class="Nav">
            <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
              <el-menu-item index="1">定义</el-menu-item>
              <el-menu-item index="2">关系</el-menu-item>
            </el-menu>
          </div>
          <div class="left">
            <div v-if="isshow1" class="source_des">
              <p class="source_header">Source Name</p>
              <p class="sourceName" v-text="sourceName">SourceName</p>
              <p class="source_header">Source Id</p>
              <p class="sourceLabel" v-text="sourceLabel">SourceLabel</p>
              <p class="source_header">Source Label</p>
              <p class="sourceId" v-text="sourceId">SourceId</p>
            </div>
            <div v-if="isshow2" class="relation">
              <div class="cascader">
                <div class="word"> Relation</div>
                <el-cascader
                  ref="refSubCat"
                  v-model="value"
                  class="choose"
                  :options="options"
                  :props="{ expandTrigger: 'hover'}"
                  @change="handleChange"
                /></div>
              <p class="des_header">Description</p>
              <el-scrollbar style="height:280px">
                <div class="description">
                  <p v-text="d1" />
                  <p v-text="d2" />
                  <p v-text="d3" />
                  <p v-text="d4" />
                  <p v-text="d5" />
                  <p v-text="d6" />
                  <p v-text="d7" />
                  <p v-text="d8" />
                </div>
              </el-scrollbar>
            </div>
          </div>
        </el-col>
        <el-col :span="13" :offset="1">
          <div
            id="myChart1"
            class="right"
            style="width: 650px;height:500px;"
          />
        </el-col>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// :sm="{span: 14}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}"
import echarts from 'echarts'
import axios from 'axios'
// var that = this
export default {
  data() {
    return {
      // 定义图表，各种参数
      graph_categories: [],
      graph_nodes: [],
      graph_links: [],
      graph_length: 0,
      graph: {},
      lastpageLabel: '疾病',
      sourceLabel: '',
      sourceId: '',
      sourceName: '',
      activeIndex: '1',
      isshow1: true,
      isshow2: false,
      value: [],
      options: [{
        'value': '',
        'label': '',
        'children': []
      }],
      d1: 'Label: ',
      d2: 'Time: ',
      d3: 'Group: ',
      d4: '证据级别: ',
      d5: '临床证据: ',
      d6: '参考文献: ',
      d7: '临床建议: ',
      d8: '作用机制: '
    }
  },
  watch: {
  },
  mounted() {
    this.$nextTick(() => {
      this.loadData()
    })
  },
  created() {
    this.loadData()
  },
  methods: {
    // lazyLoad(node, resolve) {
    //   setTimeout(() => {
    //     resolve(this.options)
    //   }, 5000)
    // },
    handleChange(value) {
      console.log('77' + value)
      // console.log(this.$refs['refSubCat'].getCheckedNodes()[0].label)
      var m = value.toString().split(',')
      var rela = m[0]
      var child = m[1]
      for (var n = 0; n < this.options.length; n++) {
        if (this.options[n].value === rela) {
          for (var l = 0; l < this.options[n].children.length; l++) {
            if (this.options[n]['children'][l].label === child) {
              this.node_des = ''
              console.log('success')
              // var reg = RegExp(/[<br/>]/g);.replace(/[<br/>]/g, '\n')
              var xx = this.options[n].children[l].description
              var ll = xx.split('<br/>')
              this.d1 = ll[0]
              this.d2 = ll[1]
              this.d3 = ll[2]
              this.d4 = ll[3]
              this.d5 = ll[4]
              this.d6 = ll[5]
              this.d7 = ll[6]
              this.d8 = ll[7]
            }
          }
        }
      }
      console.log(rela)
      console.log(child)
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath)
      if (key === '2') {
        console.log(key)
        this.isshow2 = true
        this.isshow1 = false
        // this.drawTree()
      }
      if (key === '1') {
        this.isshow2 = false
        this.isshow1 = true
        // this.drawLine()
      }
    },
    turnback() {
      console.log('888: ' + this.lastpageLabel)
      this.$router.push({
        path: 'ForceChart',
        query: { category: this.lastpageLabel }
      })
    },
    loadData() {
      const url = 'http://localhost:10088/IdfindEdge'
      const category = this.$route.query.category
      this.sourceName = this.$route.query.name
      this.sourceId = category
      this.sourceLabel = this.$route.query.label
      console.log('999: ' + this.$route.query.label)
      this.lastpageLabel = ''
      this.lastpageLabel = this.$route.query.label
      const data =
          {
            'category': category,
            'content': category
          }
      axios.post(url, data).then((response) => {
        var receive = response.data
        console.log(receive)
        if (receive === null || receive === undefined || receive === '' || receive.length === 0) {
          var url_empty_response = 'http://localhost:10088/IdfindName'
          axios.post(url_empty_response, data).then((response_empty_response) => {
            console.log('Not find any neighbours')
            console.log('response_empty_response.data: ' + response_empty_response.data)
            var category_empty_response = {}
            category_empty_response['name'] = response_empty_response.data
            var node_empty_response = {}
            node_empty_response['name'] = response_empty_response.data
            node_empty_response['category'] = 0
            node_empty_response['label'] = 0
            node_empty_response['id'] = response_empty_response.data
            node_empty_response['symbolSize'] = 30
            this.graph_nodes[0] = node_empty_response
            this.graph_categories[0] = category_empty_response
            this.drawLine()
          })
        } else {
          this.graph_length = 0
          this.graph_length = response.data.length
          console.log('graph_length: ' + this.graph_length)
          var cate1 = {}
          cate1['name'] = response.data[0]['inV']
          console.log("response.data[0]['outVLabel']: " + response.data[0]['outVLabel'])
          this.graph_categories[0] = cate1
          var cate2 = {}
          cate2['name'] = response.data[0]['outV']
          this.graph_categories[1] = cate2
          // 添加第一条边的起始节点，这个起始节点是原点
          var node1 = {}
          node1['category'] = 0
          node1['id'] = response.data[0]['inV']
          node1['symbolSize'] = 30
          node1['name'] = response.data[0]['inVLabel']
          node1['label'] = 0
          this.graph_nodes[0] = node1
          // 添加第一条边的终止节点
          var node2 = {}
          node2['category'] = 1
          node2['id'] = response.data[0]['outV']
          node2['symbolSize'] = 30
          node2['name'] = response.data[0]['outVLabel']
          node2['label'] = 1
          this.graph_nodes[1] = node2
          // 添加第一条边
          var link1 = {}
          link1['source'] = response.data[0]['inV']
          // console.log("response.data[0]['inV']: " + response.data[0]['inV'] + response.data[0]['inVLabel'])
          // console.log("response.data[0]['outV']: " + response.data[0]['inV']+response.data[0]['inVLabel']))
          link1['target'] = response.data[0]['outV']
          link1['label'] = response.data[0]['label']
          link1['des'] = response.data[0]['description']
          this.graph_links[0] = link1
          // 添加第一个关系
          var re = { value: response.data[0]['label'], label: response.data[0]['label'], children: [] }
          re['children'].push({ value: response.data[0]['outVLabel'], label: response.data[0]['outVLabel'], description: response.data[0]['description'] })
          this.options[0] = re
          var number = 0

          for (var i = 1; i < this.graph_length; i++) {
            // 类别
            var cate = {}
            cate['name'] = response.data[i]['outV']
            this.graph_categories[i + 1] = cate
            // 节点
            var node = {}
            node['category'] = i + 1
            node['id'] = response.data[i]['outV']
            node['symbolSize'] = 30
            node['name'] = response.data[i]['outVLabel']
            node['label'] = i + 1
            // console.log('i: ' + i + ' node: ' + node.name)
            // console.log('i: ' + i + ' cate: ' + cate.name)
            this.graph_nodes[i + 1] = node
          }
          for (var j = 1; j < this.graph_length; j++) {
          // 边
            var link = {}
            link['source'] = response.data[j]['inV']
            link['target'] = response.data[j]['outV']
            link['label'] = response.data[j]['label']
            link['des'] = response.data[j]['description']
            this.graph_links[j] = link
            // 这里假设返回的边是同个边的label都是一次性全过来，因此直接与上一条边比较是否相等来防止重复
            // 以后需要修改
            if (this.options[number]['label'] === response.data[j]['label']) {
              this.options[number].children.push({ value: response.data[j]['outVLabel'], label: response.data[j]['outVLabel'], description: response.data[j]['description'] })
            } else {
              console.log('应该不会来这')
              console.log(response.data[j]['label'])
              number = number + 1
              var re1 = { value: response.data[j]['label'], label: response.data[j]['label'], children: [] }
              re1['children'].push({ value: response.data[j]['outVLabel'], label: response.data[j]['outVLabel'], description: response.data[j]['description'] })
              this.options[number] = re1
            }
            // console.log('j: ' + j + ' link.source: ' + link.source)
            // console.log('j: ' + j + ' link.target: ' + link.target)
            // console.log('j: ' + j + ' link.label: ' + link.label)
          }
          // console.log('this.graph_nodes: ' + this.graph_nodes)
          // console.log(' this.graph_links: ' + this.graph_links)
          // console.log('this.graph_categories: ' + this.graph_categories)
          // console.log('length: ' + this.options[0].children.length)
          this.drawLine()
          // this.options = this.options
        }
      })
    },
    drawLine() {
      // 1、基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(document.getElementById('myChart1'))
      // 2、构造图表数据
      // console.log('2' + this.graph_categories)
      // console.log('2' + this.graph_nodes)
      myChart.showLoading(
        {
          text: 'loading',
          color: '#c23531',
          textColor: '#000',
          maskColor: 'rgba(255, 255, 255, 0.2)',
          zlevel: 0
        }
      )
      const options = {
        title: {
          top: 'bottom',
          left: 'right'
        },
        tooltip: {
          trigger: 'item',
          formatter: function(x) {
            // console.log("x",x)
            return x.data.des
          }
        },
        legend: [{
          show: false
        }],
        animation: false,
        series: [
          {
            name: 'Les Miserables',
            type: 'graph',
            layout: 'force',
            // silent: false, // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            data: this.graph_nodes,
            links: this.graph_links,
            categories: this.graph_categories,
            roam: true,
            label: {
              position: 'bottom',
              formatter: '{b}',
              show: true
            },
            draggable: true,
            force: {
              edgeLength: 100,
              repulsion: 2000,
              gravity: 0.2
            }
          }
        ] }
      // 3、绘制图表
      myChart.hideLoading()
      myChart.setOption(options)
    }
  }
}

</script>

<style scoped>
.bg{
  /* background-image: url("../../assets/Visulization/3.jpg"); */
  background-size: 100% 100%;
  z-index: -1;
}
.col1{
 background-color: #333333;
}
.Button{
  margin-top: 5px;
  margin-left: 40%;
  height: 120px;
}
.butt4{
        width: 200px;
        padding:8px;
        background-color:#ffd04b;
        border-color:#333333;
        color: #fff;
        -moz-border-radius: 10px;
        -webkit-border-radius: 10px;
        border-radius: 10px; /* future proofing */
        -khtml-border-radius: 10px; /* for old Konqueror browsers */
        text-align: center;
        vertical-align: middle;
        border: 1px solid transparent;
        font-weight: 900;
        font-size:125%
}
.butt2{
  width: 200px;
  padding:8px;
  background-color: #f7f8f9;
  color: #428bca;
  text-align: center;
  vertical-align: middle;
  font-weight: 900;
  font-size:125%
}
.butt3{
  background: gold;
        border: none;
        border-radius: 40px 10px;
        width: 200px;
        height: 100px;
         font-weight: 900;
  font-size:125%;
   text-align: center;
  vertical-align: middle;
}
.butt:hover {
    background-color: #333333;
    color: #f7f8f9;
}
.butt{
    text-align: center;
    vertical-align: middle;
    font-weight: 900;
    font-size:125%;
    width: 200px;
    padding:8px;
    border:groove 1em #ffd04b;
    border-radius:2em;}
.body_L{
   border-style: solid;
    border-color: #003366;
    border-width: 5px 5px 5px 5px;
    height: 550px;
}
.Nav{
  height: 50px;
  width: 100%;
}
.left{
   /* filter:alpha(Opacity=85);
    -moz-opacity:0.85;
    opacity: 0.85; */
    margin-top: 20px;
    margin-left: 10px;
    padding-left: 5px;
    border-style: solid;
    border-color: #003366;
    border-width: 5px 5px 5px 5px;
    z-index:1;
    height: 450px;
    width: 100%;
    margin-bottom: 20px;
}
.right{
  /* filter:alpha(Opacity=85);
    -moz-opacity:0.85;
    opacity: 0.85; */
    /* background-color:'#ffffff'; */
    /* width:1500px; */
    margin-top: 20px;
    border-style: solid;
    border-color: #003366;
    border-width: 5px 5px 5px 5px;
    height: 500px;
    z-index: 1;
    /* :style="{width: '1500px', height: '700px',background:'#ffffff'}" */
}
.source_des{
font-family: "微软雅黑";
font-weight: bold;
}
.source_header{
font-family: "微软雅黑";
font-weight: bold;
padding-left: 4px;
border-left: 4px solid  #003366;
}
.sourceName{
  /* padding-top: 100px; */
	color: #FFCC00;
	font-size: 30px;
	font-family: "微软雅黑";
	font-weight: bold;
}
.sourceLabel{
 /* border-left: 4px solid  #003366; */
 color: #336699;
}
.sourceId{
/* border-left: 4px solid  #003366; */
 color: #336699;
}
.relation{

}
.cascader{
 margin-top: 20px;
 /* margin-left: 10px; */
 width: 100%;
 height: 30%;
}
.word{
  font-family: "微软雅黑";
font-weight: bold;
display:inline-block;
margin-right: 20px;
padding-left: 4px;
border-left: 4px solid #336699;
margin-bottom: 10px;
/* float: left; */
}
.choose{
width: 300px;
display:inline-block;
}
.des_header{
  font-family: "微软雅黑";
font-weight: bold;
padding-left: 4px;
border-left: 4px solid  #336699;
}
.description{
  height:300px;
  margin-top: 20px;
  margin-left: 10px;
  margin-right:5px;
  width: 95%;
  /* border-style: solid; */
  /* border-color: #003366; */
  /* border-width: 5px 0px 0px 0px; */
}
</style>
