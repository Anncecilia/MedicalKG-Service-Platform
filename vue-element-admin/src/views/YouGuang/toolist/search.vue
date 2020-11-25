<template>
  <div style="margin-top: 15px;">
    <el-input v-model="input3" placeholder="请输入内容" class="input-with-select">
      <el-select slot="prepend" v-model="select" placeholder="请选择">
        <el-option label="疾病" value="1" />
        <el-option label="药品" value="2" />
        <el-option label="症状" value="3" />
      </el-select>
      <el-button slot="append" icon="el-icon-search" />
    </el-input>
  </div>
</template>

<script>
import resize from './mixins/resize'

// eslint-disable-next-line no-unused-vars
const animationDuration = 6000

export default {
  mixins: [resize]
}
</script>
