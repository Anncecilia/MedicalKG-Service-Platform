<template>
  <div class="bg">
    <el-row type="flex" class="row1" justify="center">
      <!-- <el-col :xs="{span: 24}"> -->
      <el-col :span="21" class="col1">
        <div class="Search">
          <el-autocomplete
            v-model="state2"
            class="input"
            :fetch-suggestions="querySearch"
            placeholder="请输入内容"
            :trigger-on-focus="false"
            @select="handleSelect"
          >
            <!-- <el-select slot="prepend" v-model="value1" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select> -->
            <el-button slot="append" icon="el-icon-search" />
            <el-button slot="append" type="primary" @click="onClick">重置</el-button>
          <!-- <el-button slot="append" type="primary" style="margin-left: 16px;" @click="drawer = true">功能</el-button> -->
          <!-- </el-input> -->
          </el-autocomplete>
        </div>
      </el-col>
    </el-row>
    <el-row type="flex" class="row2" justify="center">
      <el-col :span="21" class="body_L">
        <el-col :span="7" :offset="1">
          <el-scrollbar style="height:500px">
            <div class="left">
              <ul v-for="(item,index) in newArr" :key="index" class="list-ul">
                <div @click="toggleShow(index)">
                  <h6>{{ item.title }}</h6>
                </div>
                <div v-show="item.show">
                  <li v-for="(user,index1) in item.items" :key="index1" class="div" @click="handleSelect(user)">
                    {{ user.name }}
                  </li>
                </div>
              </ul>
            </div>
          </el-scrollbar></el-col>
        <el-col :span="13" :offset="2">
          <div
            id="myChart1"
            class="right"
            style="width: 600px;height:500px;"
          />
        </el-col>
      </el-col>
    </el-row>
    <!-- <el-drawer
      title="功能查询"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
    >
      <span>我来啦!</span>
    </el-drawer> -->
  </div>
</template>

<script>
// :sm="{span: 14}" :md="{span: 24}" :lg="{span: 12}" :xl="{span: 12}"
import echarts from 'echarts'
import axios from 'axios'
import vPinyin from './vue-py.js'
// var that = this
export default {
  data() {
    return {
      // 定义图表，各种参数
      text: 'loading',
      color: '#c23531',
      textColor: '#000',
      maskColor: 'rgba(255, 255, 255, 0.8)',
      zlevel: 0,
      graph_categories: [],
      graph_nodes: [],
      graph_links: [],
      nodes: [],
      links: [],
      categories: [],
      graph_length: 0,
      graph: {},
      options: [
        {
          value: '选项1',
          label: 'name'
        }
        // {
        //   value: '选项2',
        //   label: 'ICD-11'
        // }
      ],
      value1: [],
      value2: [],
      activeNames: ['1'],
      drawer: false,
      direction: 'btt',
      input: '',
      state2: '',
      timeout: null,
      newArr: [],
      example: [],
      chooseLabel: ''
    }
  },
  watch: {},
  mounted() {
    // this.nodes = this.loadAll()
    // this.nodes = this.graph_nodes
    // console.log(this.nodes)
    this.$nextTick(() => {
      this.loadData()
      this.getGuideData()
    })
  },
  created() {
    // this.loadData()
    // this.getGuideData()
  },
  methods: {
    toggleShow: function(index) {
      const newItem = this.newArr[index]
      newItem.show = !this.newArr[index].show
      this.$set(this.newArr, index, newItem)
      // 一定要注意vue不能检测的几种数据变化情况包括数组和对象
    },
    getGuideData() {
      const url = 'http://localhost:10088/LabeltoFindV'
      const category = this.$route.query.category
      const data =
          {
            'category': category,
            'content': category
          }
      axios.post(url, data).then((response) => {
        this.example = []
        // 获取数据
        console.log('111')
        for (var i = 0; i < this.graph_length; i++) {
          this.example.push({ name: response.data[i]['propertie'].name, pinyin: null, index: null, id: response.data[i]['id'] })
        }
        // 转成拼音
        console.log('222')
        for (var j = 0; j < this.example.length; j++) {
          var receive = this.example[j].name
          if (receive === null || receive === undefined || receive === '' || receive.length === 0) {
            continue
          } else {
            this.example[j].pinyin = vPinyin.chineseToPinYin(this.example[j].name)
            // console.log(this.example[j].pinyin)
            this.example[j].index = this.example[j].pinyin.charAt(0)
          }
        }
        console.log('333')
        // 进行字典排序
        this.example.sort(function(a, b) {
          return (a.pinyin + '').localeCompare(b.pinyin + '')
        })
        // 分组
        console.log('444')
        const map = {}
        for (let i = 0; i < 26; i++) {
          const key = String.fromCharCode(65 + i) // A-Z赋给key当作键
          map[key] =
							{ title: key, items: [], show: true }
          this.example.map((v, k) => { // 遍历数组
            // eslint-disable-next-line no-empty
            if (v.pinyin === null || v.pinyin === undefined || v.pinyin === '' || v.pinyin === 0) {

            } else {
              const firstIndex = v.pinyin.charAt(0).toUpperCase()// 首字母
              if (firstIndex.toUpperCase() === String.fromCharCode(65 + i)) { // 统一转成大写进行逐个判断
              // console.log(v.id)
                map[key].items.push({ name: v.name, id: v.id })// push进相对应的数组里头
              }
            }
          })// //如果当前的数组里头为空，则跳过。
          if (map[key].items === undefined || map[key].items.length === 0) { continue } else { this.newArr.push(map[key]) }// 将分类好的每个对象 合并在一个数组里面}
        }
      })
    },
    loadData() {
      const url = 'http://localhost:10088/LabeltoFindV'
      const category = this.$route.query.category
      this.chooseLabel = category
      const data =
          {
            'category': category,
            'content': category
          }
      axios.post(url, data).then((response) => {
        this.graph_length = 0
        this.graph_length = response.data.length
        // this.graph = response.data
        for (var i = 0; i < this.graph_length; i++) {
          var cate = {}
          cate['name'] = response.data[i]['propertie'].name
          var node = {}
          // var query_node = {}
          // query_node['value'] = response.data[i]['propertie'].name
          node['category'] = i
          node['des'] = response.data[i]['description']
          node['id'] = response.data[i]['id']
          node['symbolSize'] = 30
          node['label'] = i
          node['name'] = response.data[i]['propertie'].name
          node['value'] = response.data[i]['propertie'].name
          this.graph_categories[i] = cate
          this.graph_nodes[i] = node
        // this.nodes[i] = query_node
        }
        this.nodes = []
        this.categories = []
        this.links = []
        this.nodes = this.graph_nodes
        this.categories = this.graph_categories
        this.drawLine()
      })
    },
    onClick() {
      this.nodes = []
      this.categories = []
      this.links = []
      this.nodes = this.graph_nodes
      this.categories = this.graph_categories
      this.drawLine()
    },
    querySearch(queryString, cb) {
      // var nodes = this.nodes
      var nodes = this.graph_nodes
      var results = queryString ? nodes.filter(this.createFilter(queryString)) : nodes
      // 调用 callback 返回建议列表的数据
      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        cb(results)
      }, 3000 * Math.random())
    },
    createFilter(queryString) {
      return (node) => {
        // console.log(queryString)
        return (node.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
        // return (node.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    // loadAll() {
    //   // return this.graph_nodes
    // },
    handleSelect(item) {
      var the_choose_node_category = {}
      the_choose_node_category['name'] = item.name
      var the_choose_node = {}
      the_choose_node['name'] = item.name
      the_choose_node['category'] = 0
      the_choose_node['label'] = 0
      the_choose_node['id'] = item.id
      the_choose_node['symbolSize'] = 30
      this.nodes = []
      this.categories = []
      this.links = []
      this.nodes[0] = the_choose_node
      this.categories[0] = the_choose_node_category
      this.drawLine()
      // console.log('item: ' + item.name)
    },
    drawLine() {
      // 1、基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(document.getElementById('myChart1'))
      // 2、构造图表数据
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
          // text: 'Les Miserables',
          // subtext: 'Default layout',
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
          // selectedMode: 'single',
          // data: categories.map(function(a) {
          //   return a.name
          // })
        }],
        animation: false,
        series: [
          {
            name: 'Les Miserables',
            type: 'graph',
            layout: 'force',
            // silent: false, // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            data: this.nodes,
            links: this.links,
            categories: this.categories,
            roam: true,
            label: {
              position: 'bottom',
              formatter: '{b}',
              show: true
            },
            draggable: true,
            force: {
              repulsion: 250
            }
          }
        ] }
      // 3、绘制图表
      myChart.hideLoading()
      myChart.on(
        'click',
        (param) => {
          // 可以使用下面的方式进行路由的切换
          // alert(param.data.id)
          this.$router.push({
            path: 'ForceChart2',
            query: { category: param.data.id, label: this.chooseLabel, name: param.data.name }
          })
        }
      )
      myChart.setOption(options)
    }
  }

}
</script>

<style scoped>
.input{
  width: 500px;
  margin-bottom: 20px;
  /* border-style: solid; */
    /* border-color: #003366; */
    /* border-width: 2px 2px 2px 2px; */
}
.bg{
  /* background-image: url("../../assets/Visulization/3.jpg"); */
  background-size: 100% 100%;
  z-index: -1;
}
.col1{
 background-color: #333333;
}
.Search{
  margin-top: 20px;
  margin-left: 30%;
}
.body_L{
   border-style: solid;
    border-color: #003366;
    border-width: 5px 5px 5px 5px;
    height: 550px;
}
.left{
   /* filter:alpha(Opacity=85);
    -moz-opacity:0.85;
    opacity: 0.85; */
    margin-top: 20px;
    /* border-style: solid; */
    /* border-color: #003366; */
    /* border-width: 5px 5px 0px 5px; */
    z-index:1;
    height: 500px;
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
</style>
