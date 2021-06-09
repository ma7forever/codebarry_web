<template>
<div>
    <div class="pagebg sh"></div>
    <div>
  
  <div class="container" >
  <div class="share">
          <h1 class="t_nav" >
               <span >更多</span>
               
        <a href="/" class="n1" @click="goToMoreVideo()">动漫</a>
      </h1>
    <div style="width:800px;float:left">
     <div class="container">
      <div class="share">
        
        <ul>
          <li v-for="item in animeList" :key="item.uid">
            <div class="shareli" @click="goToVideoPage(item.videoSourceId)">
              <a href="javascript:void(0);">
                <i>
                  <img class="resImg" :src="item.photoList[0]">
                </i>
                <h2>
                  <b>简介：{{item.summary}}</b>
                </h2>
                <span>标题：{{item.title}}</span>
              </a>
            </div>
          </li>
        </ul>
      </div>
    </div>
    
    </div>
     <div class="sidebar2" style="width:300px;float:right">
       
       <h1>热播排行</h1>
      <div class="tuijian"> 
      <h2 class="hometitle"></h2>
      <ul class="tjpic"  v-if="hotVideoData[0]">
         <i><img style="cursor:pointer" v-if="hotVideoData[0].photoList" :src="hotVideoData[0].photoList[0]" @click="goToVideoPage(hotVideoData[0].videoSourceId)"></i>
        <p><a href="javascript:void(0);" @click="goToVideoPage(hotVideoData[0].videoSourceId)"> {{hotVideoData[0].title}}</a></p>

      </ul>
     <ul class="sidenews">
        <li v-for="item in sideNews" :key="item.uid">
          <i><img style="cursor:pointer"  v-if="item.photoList" :src="item.photoList[0]"  @click="goToVideoPage(item.videoSourceId)"></i>
          <p><a href="javascript:void(0);" @click="goToVideoPage(item.videoSourceId)">{{item.title}}</a></p>
          <span>点击量：{{item.playCount}}</span>
        </li>
      </ul>
    
    </div>
    </div>
    </div>
    </div>
  <div class="container" >
  <div class="share">
          <h1 class="t_nav">
        <span>好咖啡要和朋友一起品尝，好“资料”也要和同样喜欢它的人一起分享。</span>
        <a href="/" class="n1">电影</a>
      </h1>
    <div style="width:800px;float:left">
     <div class="container">
      <div class="share">
        <ul>
          <li v-for="item in movieList" :key="item.uid">
            <div class="shareli" @click="goToVideoPage(item.videoSourceId)">
              <a href="javascript:void(0);">
                <i>
                  <img class="resImg" :src="item.photoList[0]">
                </i>
                <h2>
                  <b>{{item.summary}}</b>
                </h2>
                <span>{{item.title}}</span>
              </a>
            </div>
          </li>
        </ul>
      </div>
    </div>
    
    </div>
     <div class="sidebar2" style="width:300px;float:right">
      <div class="tuijian"> 
      <h2 class="hometitle"></h2>
      <ul class="tjpic" >
        <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
        <p><a href="javascript:void(0);">标题A</a></p>
      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题B</a></p>
          <span>2021/5/9</span>
        </li>

      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题C</a></p>
          <span>2021/5/9</span>
        </li>
        
      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题D</a></p>
          <span>2021/5/9</span>
        </li>
        
      </ul>
   
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题F</a></p>
          <span>2021/5/9</span>
        </li>
      </ul>
    </div>
    </div>
    </div>
    </div>
    
  <div class="container" >
  <div class="share">
          <h1 class="t_nav">
        <span>好咖啡要和朋友一起品尝，好“资料”也要和同样喜欢它的人一起分享。</span>
        <a href="/" class="n1">电视剧</a>
      </h1>
    <div style="width:800px;float:left">
     <div class="container">
      <div class="share">
        <ul>
          <li v-for="item in dianshijuList" :key="item.uid">
            <div class="shareli" @click="goToVideoPage(item.videoSourceId)">
              <a href="javascript:void(0);">
                <i>
                  <img class="resImg" :src="item.photoList[0]">
                </i>
                <h2>
                  <b>{{item.summary}}</b>
                </h2>
                <span>{{item.title}}</span>
              </a>
            </div>
          </li>
        </ul>
      </div>
    </div>
    
    </div>
     <div class="sidebar2" style="width:300px;float:right">
      <div class="tuijian"> 
      <h2 class="hometitle"></h2>
      <ul class="tjpic" >
        <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
        <p><a href="javascript:void(0);">标题A</a></p>
      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题B</a></p>
          <span>2021/5/9</span>
        </li>

      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题C</a></p>
          <span>2021/5/9</span>
        </li>
        
      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题D</a></p>
          <span>2021/5/9</span>
        </li>
        
      </ul>
   
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题F</a></p>
          <span>2021/5/9</span>
        </li>
      </ul>
    </div>
    </div>
    </div>
    </div>
      
      
  <div class="container" >
  <div class="share">
          <h1 class="t_nav">
        <span>好咖啡要和朋友一起品尝，好“资料”也要和同样喜欢它的人一起分享。</span>
        <a href="/" class="n1">科技</a>
      </h1>
    <div style="width:800px;float:left">
     <div class="container">
      <div class="share">
        <ul>
          <li v-for="item in scienceFictionList" :key="item.uid">
            <div class="shareli" @click="goToVideoPage(item.videoSourceId)">
              <a href="javascript:void(0);">
                <i>
                  <img class="resImg" :src="item.photoList[0]">
                </i>
                <h2>
                  <b>{{item.summary}}</b>
                </h2>
                <span>{{item.title}}</span>
              </a>
            </div>
          </li>
        </ul>
      </div>
    </div>
    
    </div>
     <div class="sidebar2" style="width:300px;float:right">
      <div class="tuijian"> 
      <h2 class="hometitle"></h2>
      <ul class="tjpic" >
        <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
        <p><a href="javascript:void(0);">标题A</a></p>
      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题B</a></p>
          <span>2021/5/9</span>
        </li>

      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题C</a></p>
          <span>2021/5/9</span>
        </li>
        
      </ul>
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题D</a></p>
          <span>2021/5/9</span>
        </li>
        
      </ul>
   
      <ul class="sidenews">
        <li >
          <i><img style="cursor:pointer" src="static/images/b01.jpg"></i>
          <p><a href="javascript:void(0);">标题F</a></p>
          <span>2021/5/9</span>
        </li>
      </ul>
    </div>
    </div>
    </div>
    </div>  
  
    </div> 
</div>
</template>

<script>

import {getVideoList,} from "../api/videoShow";
import {getHotVideo}  from "../api/index";
  import TagCloud from "../components/TagCloud";
  import FollowUs from "../components/FollowUs";
  import Link from "../components/Link";
  import {getBlogByLevel, getNewBlog, getNewVideo, recorderVisitPage} from "../api/index";
  import { Loading } from 'element-ui';
  import {getBlogByUid} from "../api/blogContent";
  export default {
    name: "index",
    components: {
      //注册组件
      TagCloud,
      FollowUs,
      Link,

    },
    data() {
      return {
        videoId:"",
         dataList: [],
        loadingInstance: null, // loading对象
        VUE_MOGU_WEB: process.env.VUE_MOGU_WEB,
         hotVideoData: [] //热门视频列表
   ,    
        hotTagData: [], //最新标签
        keyword: "",
        currentPage: 1,
        pageSize: 6,
        total: 0, //总数量
        isEnd: false, //是否到底底部了
        loading: false //是否正在加载
      };
    },
    mounted() {
      // 注册scroll事件并监听
      this.loading = false;
    },
    computed:{
     sideNews() {
      return this.hotVideoData.filter(blog =>
        this.hotVideoData.indexOf(blog) != 0
      )
    },
      animeList:function(){
        return this.dataList.filter(function(item){
          if(item.resourceSort.sortName=="动漫")
          return item
        })
      },
      
      movieList:function(){
        return this.dataList.filter(function(item){
          if(item.resourceSort.sortName=="电影")
          return item
        })
      },
      dianshijuList:function(){
        return this.dataList.filter(function(item){
          if(item.resourceSort.sortName=="电视剧")
          return item
        })
      },
      scienceFictionList:function(){
        return this.dataList.filter(function(item){
          if(item.resourceSort.sortName=="科技")
          return item
        });
      }
    },
    created(){
      getHotVideo().then(response => {
      if (response.code == this.$ECode.SUCCESS) {
        this.hotVideoData = response.data.records;
        console.log("!!!获取到的数据")
        console.log(this.hotVideoData[0])
      }
    });
      var secondParams = new URLSearchParams();
      secondParams.append("level", 2);
      // 是否排序
      secondParams.append("useSort", 1);
      getBlogByLevel(secondParams).then(response => {
        if(response.code == this.$ECode.SUCCESS) {
          this.secondData = response.data.records;
        }
      });
     this.subjectList()
     // 获取最新博客
   //   this.newBlogList();
      // this.newVideoList();
      // //获取最新视频
      
      var params = new URLSearchParams();
      params.append("pageName", "INDEX");
        recorderVisitPage(params).then(response => {
      });
    },
    methods: {
         subjectList() {
      var params = {};
      params.pageSize = this.pageSize
      params.currentPage = this.currentPage
      params.isPublish=1
      params.status=1
     // params.userUid=this.$store.state.user.userInfo.uid
   
      getVideoList(params).then(response => {
        if (response.code == this.$ECode.SUCCESS) {
          this.dataList = response.data.records;
          this.total = response.data.total;
          this.pageSize = response.data.size;
          this.currentPage = response.data.current;
        }
      });
    },
        handleCurrentChange(val) {
      this.currentPage = val; //改变当前所指向的页数
      this.subjectList();
    },
    showSubjectItemList(selectSubjectUid) {
      this.selectSubjectUid = selectSubjectUid
      this.visiable = !this.visiable
    },
    closeSubjectItemList(selectSubjectUid) {
      this.visiable = !this.visiable
    },
      goToVideoPage(videoId){
            // 标签uid
            let routeData = this.$router.resolve({
              path: "/videoShow",
              query: { videoId: videoId }
            });
            window.open(routeData.href, "_blank");
      },
      
      goToMoreVideo(){
            // 标签uid
       let routeData = this.$router.resolve({
              path: "/anime",
         //     query: { videoId: videoId }
            });
            window.open(routeData.href, "_blank");
      },
      //跳转到文章详情【或推广链接】
      goToInfo(blog) {
        if(blog.type == "0") {
          let routeData = this.$router.resolve({
            path: "/info",
            query: {blogOid: blog.oid}
          });
          window.open(routeData.href, '_blank');
        } else if(blog.type == "1") {
          var params = new URLSearchParams();
          params.append("uid", blog.uid);
          getBlogByUid(params).then(response => {
            // 记录一下用户点击日志
          });
          window.open(blog.outsideLink, '_blank');
        }
      },
      //跳转到搜索详情页
      goToList(uid) {
        let routeData = this.$router.push({
          path: "/list",
          query: {sortUid: uid}
        });

      },

      //跳转到搜索详情页
      goToAuthor(author) {
        let routeData = this.$router.push({
          path: "/list",
          query: {author: author}
        });
      },
 //最新视频列表
      // newVideoList() {
      //   var that = this;
      //   that.loadingInstance = Loading.service({
      //     lock: true,
      //     text: '正在努力加载中……',
      //     background: 'rgba(0, 0, 0, 0.7)'
      //   })

      //   var params = new URLSearchParams();
      //   params.append("currentPage", this.currentPage);
      //   params.append("pageSize", this.pageSize);
      //   getNewVideo(params).then(response => {
      //     if (response.code == this.$ECode.SUCCESS) {
      //       that.newVideoData = response.data.records;
      //       that.total = response.data.total;
      //       that.pageSize = response.data.size;
      //       that.currentPage = response.data.current;
      //     }
      //     that.loadingInstance.close();
      //   },function(err){
      //     that.loadingInstance.close();
      //   });
      // },
      //最新博客列表
      newBlogList() {
        var that = this;
        that.loadingInstance = Loading.service({
          lock: true,
          text: '正在努力加载中……',
          background: 'rgba(0, 0, 0, 0.7)'
        })

        var params = new URLSearchParams();
        params.append("currentPage", this.currentPage);
        params.append("pageSize", this.pageSize);
        getNewBlog(params).then(response => {
          if (response.code == this.$ECode.SUCCESS) {
            that.newBlogData = response.data.records;
            that.total = response.data.total;
            that.pageSize = response.data.size;
            that.currentPage = response.data.current;
          }
          that.loadingInstance.close();
        },function(err){
          that.loadingInstance.close();
        });
      },

      loadContent: function () {
        var that = this;
        that.loading = false;
        that.currentPage = that.currentPage + 1;
        var params = new URLSearchParams();
        params.append("currentPage", that.currentPage);
        params.append("pageSize", that.pageSize);
        getNewBlog(params).then(response => {
          if (response.code == this.$ECode.SUCCESS && response.data.records.length > 0) {
            that.isEnd = false;
            var newData = that.newBlogData.concat(response.data.records);
            that.newBlogData = newData;
            that.total = response.data.total;
            that.pageSize = response.data.size;
            that.currentPage = response.data.current;

            //全部加载完毕
            if (newData.length < that.pageSize) {
              that.isEnd = true;
            }
          } else {
            that.isEnd = true;
          }
          that.loading = false;
        });
      },
    }
  };


</script>

<style>
.resImg {
  width: 100%;
  height: 160px;
  vertical-align: middle;
}


</style>
