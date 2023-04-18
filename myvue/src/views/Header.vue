<template>
  <div style="display: flex;line-height: 60px">
    <div>
      <i :class="icon" style="font-size: 15px;" @click="collapse"></i>
    </div>
    <div style="flex: 1;text-align: center;font-size: 30px">
      <span>欢迎来到仓库管理系统</span>
    </div>
    <el-dropdown>
      <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item @click.native="toUser">个人中心</el-dropdown-item>
        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
      <span>{{user.name}}</span>
    </el-dropdown>

  </div>
</template>

<script>
import Router from "../router";

export default {
  name: "Header",
  data(){
    return{
      user:JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  props:{
    icon:String
  },
  methods:{
    toUser(){
      console.log('touser')
      this.$router.push("/Home")
    },
    logout(){
      console.log('tas')
      this.$confirm('你确定要退出登录吗？', {
        confirmButtonText: '确认',
        type: 'warning',
        center: true, //
      })
        .then(() => {
          this.$message({
            type:'success',
            message:'成功退出'
          })

          this.$router.push("/")
          sessionStorage.clear()
        })
        .catch(() => {
          this.$message({
            type:'info',
            message:'已取消退出登录'
          })
        })
    },
    collapse(){
      this.$emit('doCollapse')
    }
  }
}
</script>

<style scoped>

</style>
