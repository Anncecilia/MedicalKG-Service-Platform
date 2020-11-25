<template>
  <div class="bg">
    <el-row type="flex" class="row1" justify="center">
      <el-col :span="21">
        <div class="NavMenu">
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            background-color="#333333"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="handleSelect"
          >
            <el-menu-item index="1">实体</el-menu-item>
            <el-menu-item index="2">关系</el-menu-item>
          </el-menu>
        </div>
      </el-col>
    </el-row>
    <el-row type="flex" class="row2" justify="center">
      <el-col :span="21" class="body_L">
        <el-col :span="7" :offset="1">
          <div v-if="isshow1" class="left">
            <el-input
              v-model="filterText"
              placeholder="输入关键字进行过滤"
            />
            <el-scrollbar style="height:450px">
              <el-tree
                ref="tree"
                class="filter-tree"
                :data="data"
                :props="defaultProps"
                :filter-node-method="filterNode"
                @node-click="handleNodeClick"
              /></el-scrollbar></div>
          <div v-if="isshow2" class="left">
            <el-input
              v-model="filterText"
              placeholder="输入关键字进行过滤"
            />
            <el-scrollbar style="height:450px">
              <el-tree
                ref="tree"
                class="filter-tree"
                :data="data1"
                :props="defaultProps"
                :filter-node-method="filterNode"
                @node-click="handleNodeClick"
              /></el-scrollbar></div>
        </el-col>
        <el-col :span="13" :offset="2">
          <div
            v-if="isshow1"
            class="right"
            style="width: 600px;height:500px;"
          > <relation-chart /></div>
        </el-col>
        <el-col :span="13" :offset="2">
          <div
            v-if="isshow2"
            class="right"
            style="width: 600px;height:500px;"
          > <tree-chart /></div></el-col>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import TreeChart from './components/TreeChart'
import RelationChart from './components/RelationChart'
// import router from '../../router'
// var that = this

export default {
  components: {
    TreeChart,
    RelationChart
  },
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
      graph_length: 0,
      graph: {},
      activeIndex: '1',
      filterText: '',
      isshow1: true,
      isshow2: false,
      data: [{
        id: 1,
        label: 'G',
        children: [{
          id: 5,
          label: '高血压用药'
        }]
      }, {
        id: 2,
        label: 'J',
        children: [{
          id: 6,
          label: '疾病'
        }, {
          id: 7,
          label: '疾病大类'
        }, {
          id: 8,
          label: '疾病类型'
        }]
      }, {
        id: 3,
        label: 'R',
        children: [{
          id: 9,
          label: '人群'
        }]
      }, {
        id: 4,
        label: 'X',
        children: [{
          id: 10,
          label: '系统疾病'
        }]
      }, {
        id: 5,
        label: 'Y',
        children: [{
          id: 11,
          label: '药品分类'
        }, {
          id: 12,
          label: '药品化学名'
        }, {
          id: 13,
          label: '药品商品名'
        }, {
          id: 14,
          label: '用药目的'
        }]
      }],
      data1: [
        {
          id: 1,
          label: 'G',
          children: [{
            id: 5,
            label: '高血压用药',
            children: [{
              label: '高血压用药To人群-人群'
            }, {
              label: '高血压用药To用药目的-用药目的'
            }, {
              label: '高血压用药To药品化学名-药品化学名'
            }, {
              label: '高血压用药To药品分类-药品分类'
            }, {
              label: '高血压用药To药品商品名-药品商品名'
            }, {
              label: '高血压用药To疾病类型-疾病类型'
            }, {
              label: '血压用药To疾病-疾病'
            }]
          }]
        }, {
          id: 2,
          label: 'J',
          children: [{
            id: 6,
            label: '疾病',
            children: [
              {
                label: '疾病别名-疾病'
              }, {
                label: '是疾病类型的子类-疾病类型'
              }, {
                label: '并发症-疾病'
              }
            ]
          }, {
            id: 7,
            label: '疾病大类',
            children: [
              {
                label: '疾病大类分类-疾病类型'
              }
            ]
          }, {
            id: 8,
            label: '疾病类型',
            children: [
              {
                label: '疾病类型别名-疾病类型'
              }, {
                label: '疾病类型分类-疾病类型'
              }, {
                label: '疾病-疾病'
              }
            ]
          }]
        }, {
          id: 3,
          label: 'R',
          children: [{
            id: 9,
            label: '人群'
          }]
        }, {
          id: 4,
          label: 'X',
          children: [{
            id: 10,
            label: '系统疾病',
            children: [
              {
                label: '系统疾病分类-疾病大类'
              }
            ]
          }]
        }, {
          id: 5,
          label: 'Y',
          children: [{
            id: 11,
            label: '药品分类',
            children: [
              {
                label: '适应证-疾病'
              }, {
                label: '适应证-疾病类型'
              }, {
                label: '用药目的（药品分类）-用药目的'
              }, {
                label: '药品类型分类-药品分类'
              }, {
                label: '适用人群（药品分类）-人群'
              }, {
                label: '药品分类别名-药品分类'
              }, {
                label: '相互作用（药品分类）-药品分类'
              }, {
                label: '禁忌证-疾病类型'
              }, {
                label: '禁忌人群（药品分类）-人群'
              }, {
                label: '相互作用-药品商品名'
              }, {
                label: '相互作用-药品化学名'
              }
            ]
          }, {
            id: 12,
            label: '药品化学名',
            children: [
              {
                label: '适应证-疾病'
              }, {
                label: '用药目的（化学名）-用药目的'
              }, {
                label: '是药品分类的化学名子类-药品分类'
              }, {
                label: '化学名别名-药品化学名'
              }, {
                label: '相互作用（化学名）-药品化学名'
              }, {
                label: '成份-药品商品名'
              }, {
                label: '禁忌证-疾病'
              }, {
                label: '禁忌证-疾病类型'
              }, {
                label: '适用人群（化学名）-人群'
              }, {
                label: '禁忌人群（化学名）-人群'
              }, {
                label: '相互作用-药品化学名'
              }, {
                label: '相互作用-药品商品名'
              }
            ]
          }, {
            id: 13,
            label: '药品商品名',
            children: [
              {
                label: '商品名别名-药品商品名'
              }, {
                label: '相互作用（商品名）-药品商品名'
              }, {
                label: '用药目的（商品名）-用药目的'
              }, {
                label: '适应证-疾病'
              }, {
                label: '是药品分类的商品名子类-药品分类'
              }, {
                label: '适应证-疾病类型'
              }, {
                label: '禁忌证-疾病类型'
              }, {
                label: '禁忌证-疾病'
              }, {
                label: '禁忌人群（商品名）-人群'
              }, {
                label: '适用人群（商品名）-人群'
              }
            ]
          }, {
            id: 14,
            label: '用药目的'
          }
          ]
        }],
      data2: [{
        id: 1,
        label: 'G',
        children: [{
          id: 5,
          label: '高血压用药'
        }]
      }, {
        id: 2,
        label: 'J',
        children: [{
          id: 6,
          label: '疾病'
        }, {
          id: 7,
          label: '疾病大类'
        }, {
          id: 8,
          label: '疾病类型'
        }]
      }, {
        id: 3,
        label: 'R',
        children: [{
          id: 9,
          label: '人群'
        }]
      }, {
        id: 4,
        label: 'X',
        children: [{
          id: 10,
          label: '系统疾病'
        }]
      }, {
        id: 5,
        label: 'Y',
        children: [{
          id: 11,
          label: '药品分类'
        }, {
          id: 12,
          label: '药品化学名'
        }, {
          id: 13,
          label: '药品商品名'
        }, {
          id: 14,
          label: '用药目的'
        }]
      }],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },

  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  mounted() {
  },
  created() {
  },
  methods: {
    transData() {
      // eslint-disable-next-line no-unused-vars
      var num1 = 0
      var leaf_list = []
      leaf_list.push(this.compDataConfig.data[0]) // 把第一个根节点存进去
      // eslint-disable-next-line no-unused-vars
      var all_data = []
      // var obj = this.compDataConfig.data[0]
      while (leaf_list.length !== 0) { // 当数组里没有节点时，退出循环
        // eslint-disable-next-line no-prototype-builtins
        while (leaf_list[0].hasOwnProperty('children')) { // 当该节点没有子节点，即叶子节点，退出循环
          for (var i = 0; i < leaf_list[0].children.length; i++) {
            leaf_list[0].children[i].num = i
            leaf_list.push(leaf_list[0].children[i])
          }

          leaf_list.splice(0, 1)
        }
        this.leaf.push(leaf_list[0])
        leaf_list.splice(0, 1)
      }
      for (var s = 0; s < this.leaf.length; s++) {
        if (this.leaf[s].num % 2 !== 0) {
          this.real_leaf.push(this.leaf[s])
        }
      }
    },
    handleSelect(key, keyPath) {
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
    handleNodeClick(data) {
      this.$router.push({
        path: 'ForceChart',
        query: { category: data.label }
      })
      // console.log(data.label)
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    toForeChart() {
      this.$router.push({ name: 'LesMisable' })
      // console.log('11111')
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
.NavMenu{
  margin-top: 20px;
}
.body_L{
   border-style: solid;
    border-color: #003366;
    border-width: 5px 5px 5px 5px;
}
.left{
   /* filter:alpha(Opacity=85);
    -moz-opacity:0.85;
    opacity: 0.85; */
    margin-top: 20px;
    border-style: solid;
    border-color: #003366;
    border-width: 5px 5px 5px 5px;
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
