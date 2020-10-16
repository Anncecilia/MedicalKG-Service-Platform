<template>
  <div class="home">
    <div class="panel">
      <div class="option-panel">
        <el-form :inline="true">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="用户名：">
                <el-input v-model="query.uname" placeholder="用户名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="性别：">
                <el-select placeholder="性别" v-model="query.gender">
                  <el-option label="不限" :value="-1"></el-option>
                  <el-option label="男" :value="0"></el-option>
                  <el-option label="女" :value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="状态：">
                <el-select placeholder="状态" v-model="query.state">
                  <el-option label="不限" :value="-1"></el-option>
                  <el-option label="启用" :value="1"></el-option>
                  <el-option label="停用" :value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="2" :offset="2">
              <el-button type="primary" icon="el-icon-search" style="float:right" @click="search">搜索</el-button>
            </el-col>
            <el-col :span="2">
              <el-button type="primary" icon="el-icon-plus" style="float:right" @click="modalState.addUser=true">新建</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="line"></div>

      <el-table :data="users" stripe border height="550" style="width:100%;" v-loading="loading" element-loading-text="正在拼命加载中" :eader-row-style="{'background':'#efefef'}">
        <el-table-column prop="uname" label="用户名" align="center"></el-table-column>
        <el-table-column prop="phone" label="手机号" width="180" align="center"></el-table-column>
        <el-table-column prop="gender" label="性别" align="center" :formatter="genderFormatter"></el-table-column>
        <el-table-column prop="state" label="状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.state==1" type="success">已启用</el-tag>
            <el-tag v-if="scope.row.state==0" type="danger">未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="update_time" label="最近更新" align="center"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="()=>{editUser={...scope.row};modalState.editUser=true}">编辑</el-button>
            <el-button @click="deleteUser(scope.row.id)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination layout="prev, pager, next" :page-count="pageInfo.total" :current-page.sync="pageInfo.current" @current-change="pageChange"></el-pagination>
      </div>
    </div>
    <el-dialog title="新增用户" :visible.sync="modalState.addUser" width="40%">
      <el-form style="width:80%;margin:0 auto" label-position="right">
        <el-form-item label="用户名" label-width="60px">
          <el-input v-model="newUser.uname" autocomplete="off" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="60px">
          <el-input v-model="newUser.phone" autocomplete="off" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item label="性别" label-width="60px" style="text-align:left">
          <el-radio v-model="newUser.gender" :label="0" border size="medium">男</el-radio>
          <el-radio v-model="newUser.gender" :label="1" border size="medium">女</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="modalState.addUser=false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="编辑用户" :visible.sync="modalState.editUser" width="40%">
      <el-form style="width:80%;margin:0 auto" label-position="right">
        <el-form-item label="用户名:" label-width="60px">
          <el-input v-model="editUser.uname" autocomplete="off" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" label-width="60px">
          <el-input v-model="editUser.phone" autocomplete="off" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item label="性别:" label-width="60px" style="text-align:left">
          <el-radio v-model="editUser.gender" :label="0" border size="medium">男</el-radio>
          <el-radio v-model="editUser.gender" :label="1" border size="medium">女</el-radio>
        </el-form-item>
        <el-form-item label="状态:" label-width="60px" style="text-align:left">
          <el-switch v-model="editUser.state" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="0">}</el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="modalState.editUser=false">取 消</el-button>
        <el-button type="primary" @click="updateUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src
const gender_dict = {
  0: '男',
  1: '女'
}
export default {
  name: 'Home',
  components: {
  },
  data() {
    return {
      users: [],
      query: { uname: '', gender: -1, state: -1 },
      loading: true,
      modalState: {
        addUser: false,
        editUser: false
      },
      pageInfo: {
        total: 1,
        current: 2,
        size: 7
      },
      newUser: {
        uname: '',
        phone: '',
        gender: 0
      },
      editUser: {
        uname: '',
        phone: '',
        gender: '',
        state: 0
      }
    }
  },
  mounted() {
    this.search()
  },
  methods: {
    getUsers(query) {
      this.loading = true;
      console.log(query)
      this.$http.get("/user/list", { params: query }).then(res => {
        if (res.data.code == 0) {
          this.users = res.data.data.list
          this.pageInfo.total = res.data.data.pages
          this.pageInfo.current = res.data.data.page
          this.loading = false;
        }
      }).catch(err => {
        this.loading = false;
        console.log(err)
      })
    },
    search() {
      this.query.page = 1
      this.query.size = this.pageInfo.size
      this.getUsers(this.query)
    },
    genderFormatter(row, column) {
      return gender_dict[row.gender]
    },
    addUser() {
      this.$http.post("/user", JSON.stringify(this.newUser)).then(res => {
        if (res.data.code == 0) {
          this.modalState.addUser = false;
          this.search()
          this.newUser.uname = ''
          this.newUser.phone = ''
          this.newUser.gender = 0
          this.$message({
            type: 'success',
            message: '新增成功'
          });
        } else {
          this.$message({
            type: 'error',
            message: res.data.msg
          });
        }
      }).catch(err => {
        this.modalState.addUser = false;
      })
    },
    deleteUser(id) {
      console.log(id)
      this.$confirm('此操作将删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete("/user/" + id).then(res => {
          if (res.data.code == 0) {
            this.$message({
              type: 'success',
              message: '删除成功'
            });
            this.getUsers(this.query)
          } else {
            this.$message({
              type: 'error',
              message: '删除失败'
            });
          }

        }).catch(err => {
          this.$message({
            type: 'error',
            message: '删除失败'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    updateUser() {
      this.$http.put("/user/" + this.editUser.id, this.editUser).then(res => {
        if (res.data.code == 0) {
          this.modalState.editUser = false;
          this.$message({
            type: 'success',
            message: '更新成功'
          });
          this.getUsers(this.query)
        } else {
          this.$message({
            type: 'error',
            message: '更新失败'
          });
        }
      }).catch(err => {
      })
    },
    pageChange(page) {
      this.query.page = page
      console.log(this.query.page)
      this.getUsers(this.query);
    }
  }
}
</script>
<style scoped>
.home {
  width: 100%;
  height: 100%;
}
.panel {
  margin: 0 auto;
  margin-top: 50px;
  width: 1200px;
  height: 750px;
  background-color: #fff;
  padding: 10px;
  padding-top: 20px;
  box-shadow: 0 1px 2px 0 rgba(31, 45, 61, 0.1);
  border-radius: 4px;
  box-sizing: border-box;
  position: relative;
}
.pagination {
  margin-top: 50px;
}
.option-panel {
  padding: 5px 0;
  box-sizing: border-box;
  width: 100%;
}
.line {
  width: 100%;
  border-bottom: 1px solid rgba(31, 45, 61, 0.2);
  position: absolute;
  left: 0;
  /* top: 80px; */
}
</style>
