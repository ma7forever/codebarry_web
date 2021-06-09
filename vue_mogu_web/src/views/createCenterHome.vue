
<template>
<div class="contain">
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
      <!-- <el-menu-item index="1-2">专栏投稿</el-menu-item> -->
    </el-menu-item-group>
      <template slot="title">
      <span slot="title">管理</span>
    </template>
      
    <el-menu-item-group title="管理">
      
    <el-submenu index="1-3">

      <span slot="title">我的视频</span>
    <router-link to="publishVideo">
      <el-menu-item index="1-3-1">公开视频</el-menu-item>
    </router-link>
     <router-link to="myVideo">
     <el-menu-item index="1-3-2">仅自己可见</el-menu-item> 
       </router-link>
    <router-link to="auditing">
     <el-menu-item index="1-3-3">待审核视频</el-menu-item>
    </router-link> 
    <router-link to="auditfail">
     <el-menu-item index="1-3-4">未通过视频</el-menu-item> 
    </router-link> 
    
    </el-submenu>

    </el-menu-item-group>
    <el-submenu index="1-5">
      <span slot="title">收藏管理</span>
    <router-link to="myCollection">
      <el-menu-item index="1-5-1">我收藏的视频</el-menu-item>
    </router-link> 
    
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
</div>

  <div class="mainContain">
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
    position: relative;
    margin-top: 80px;
    min-height: 840px;
    float: left;
  }
  .mainContain{
    position: relative;
    margin-top: 180px;
    min-width: 1140px;
    float: left;
  }
  
  .Contain{
    width:1440px;
    margin: 20px;
  }
</style>

<script>
import {mapGetters, mapMutations} from 'vuex';
 
  export default {
    data() {
      return {
        isCollapse: true
      };
    },
created(){
  this.loginjy()
},
methods: {

      ...mapMutations(['setLoginMessage']),
    loginjy() {
        let isLogin = this.$store.state.user.isLogin
        if(!isLogin) {
          this.$notify.error({
            title: '警告',
            message: '登录后才可以使用创作中心~',
            offset: 100
          });
          // 未登录，自动弹出登录框
          
          this.setLoginMessage(Math.random())
          return;
        }
    },
handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
  
</script>