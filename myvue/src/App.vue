<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import Login from "./views/Login";
export default {
  name: 'App',
  components:{
    Login
  }
}
</script>

<style>

</style>
