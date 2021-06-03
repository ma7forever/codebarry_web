<template>
<div class="sideNav">
    <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
  <el-radio-button :label="false">展开</el-radio-button>
  <el-radio-button :label="true">收起</el-radio-button>
</el-radio-group>
<el-menu default-active="1-4-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
  <el-submenu index="1">
    <template slot="title">
      <i class="el-icon-location"></i>
      <span slot="title">投稿</span>
    </template>
      
      
    <el-menu-item-group title="投稿">
      
      <router-link to="upload">
      <el-menu-item index="1-1">视频投稿</el-menu-item>
      </router-link>
      <el-menu-item index="1-2">专栏投稿</el-menu-item>
    </el-menu-item-group>
      <template slot="title">
      <span slot="title">管理</span>
    </template>
      
    <el-menu-item-group title="管理">
      <el-menu-item index="1-3">视频管理</el-menu-item>
      <el-menu-item index="1-4">专栏管理</el-menu-item>
    </el-menu-item-group>
    <el-submenu index="1-5">
      <span slot="title">互动管理</span>
      <el-menu-item index="1-4-1">评论管理</el-menu-item>
    </el-submenu>
  </el-submenu>
  <router-link to="/">
  <el-menu-item index="4">
    <i class="el-icon-s-promotion"></i>
    <span slot="title">返回主站</span>
  </el-menu-item>
  </router-link>
   <router-link to="/createCenter">
  <el-menu-item index="5">
    <i class="el-icon-s-home"></i>
    <span slot="title">首页</span>
  </el-menu-item>
  </router-link> 
</el-menu>

  <div style="width:998px;margin:20px auto;">
    <router-view/>
  </div>
</div>

</template>
<style>
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  .sideNav{
    width: 300px;
    float: left;
  }
</style>

<script>
  export default {
    data() {
      return {
        isCollapse: true
      };
    },
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
</script>