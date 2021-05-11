<template>
 <div style="width:998px;margin:20px auto">
     <div>
      <video-player class="video-player vjs-custom-skin" 
            ref="videoPlayer" 
            :playsinline="true" 
            :options="playerOptions">
      </video-player>
     </div>
     
     <CommentBox
        :userInfo="userInfo"
        :commentInfo="commentInfo"
        @submit-box="submitBox"
        :showCancel="showCancel"
      ></CommentBox>

      <div class="message_infos">
        <CommentList :comments="comments" :commentInfo="commentInfo"></CommentList>
        <div class="noComment" v-if="comments.length ==0">还没有评论，快来抢沙发吧！</div>
      </div>
</div>
</template>
<script>
 import CommentList from "../components/CommentList";
    import CommentBox from "../components/CommentBox";
    import Sticky from "@/components/Sticky";
    import { addComment, getCommentList } from "../api/comment";
import { videoPlayer } from 'vue-video-player'
import 'video.js/dist/video-js.css'
import 'videojs-flash'  // 如果是直播或者是视频流，注意需要引入这个模块
import 'vue-video-player/src/custom-theme.css'

    // vuex中有mapState方法，相当于我们能够使用它的getset方法
    import { mapMutations } from "vuex";
export default {
  components: {
    videoPlayer,
            CommentList,
            CommentBox,
            Sticky
  },      data() {
        return {
          playerOptions: {
            playbackRates: [0.5, 1.0, 1.5, 2.0],  // 可选的播放速度
            autoplay: false,  // 如果为true,浏览器准备好时开始回放。
            muted: false,  // 默认情况下将会消除任何音频。
            loop: false,  // 是否视频一结束就重新开始。
            preload: 'auto',  // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
            language: 'zh-CN',
            aspectRatio: '16:9',  // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
            fluid: true,  // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
            sources: [{
              type: "video/mp4",  // 类型
              src: 'http://vjs.zencdn.net/v/oceans.mp4'  // url地址
            }],
            poster: '',  // 封面地址
            notSupportedMessage: '此视频暂无法播放，请稍后再试',  // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
            controlBar: {
              timeDivider: true,  // 当前时间和持续时间的分隔符
              durationDisplay: true,  // 显示持续时间
              remainingTimeDisplay: false,  // 是否显示剩余时间功能
              fullscreenToggle: true,  // 是否显示全屏按钮,

            }
          },
            pageMinHeight: 0,
                source: "MESSAGE_BOARD",
                showCancel: false,
                submitting: false,
                value: "",
                comments: [],
                commentInfo: {
                    // 评论来源： MESSAGE_BOARD，ABOUT，BLOG_INFO 等 代表来自某些页面的评论
                    source: "MESSAGE_BOARD"
                },
                currentPage: 1,
                pageSize: 10,
                total: 0, //总数量
                toInfo: {},
                userInfo: {}
            };
       },
        watch: {},
        computed: {},
        created() {
            this.getCommentDataList();

        },
        mounted () {
          var that = this;
          // 屏幕的高度
          this.pageMinHeight = window.innerHeight - 62
          $(window).scroll(function () {
            var docHeight = $(document).height(); // 获取整个页面的高度(不只是窗口,还包括为显示的页面)
            var winHeight = $(window).height(); // 获取当前窗体的高度(显示的高度)
            var winScrollHeight = $(window).scrollTop(); // 获取滚动条滚动的距离(移动距离)
            //还有30像素的时候,就查询
            if(docHeight == winHeight + winScrollHeight){
              if(that.comments.length >= that.total) {
                return;
              }
              let params = {};
              params.source = "MESSAGE_BOARD";
              params.currentPage = that.currentPage + 1
              params.pageSize = that.pageSize;
              getCommentList(params).then(response => {
                if (response.code == that.$ECode.SUCCESS) {
                  that.comments = that.comments.concat(response.data.records);
                  that.setCommentList(this.comments);
                  that.currentPage = response.data.current;
                  that.pageSize = response.data.size;
                  that.total = response.data.total;
                }
              });
            }
          })
        },
        methods: {
            //拿到vuex中的写的两个方法
            ...mapMutations(["setCommentList"]),
            handleCurrentChange: function(val) {
                this.currentPage = val;
                this.getCommentDataList();
            },
            submitBox(e) {
                let params = {};
                params.source = e.source;
                params.userUid = e.userUid;
                params.content = e.content;
                params.blogUid = e.blogUid;
                addComment(params).then(response => {
                    if (response.code == this.$ECode.SUCCESS) {
                        this.$notify({
                            title: "成功",
                            message: "发表成功~",
                            type: "success",
                            offset: 100
                        });
                    } else {
                        this.$notify.error({
                            title: "错误",
                            message: response.data,
                            offset: 100
                        });
                    }
                    this.getCommentDataList();
                });
            },
            getCommentDataList: function() {
                let params = {};
                params.source = "MESSAGE_BOARD";
                params.currentPage = this.currentPage;
                params.pageSize = this.pageSize;
                getCommentList(params).then(response => {
                    if (response.code == this.$ECode.SUCCESS) {
                        this.comments = response.data.records;
                        this.setCommentList(this.comments);
                        this.currentPage = response.data.current;
                        this.pageSize = response.data.size;
                        this.total = response.data.total;
                    }
                });
            },
        }
    };
</script>
<style scoped>
  .message_infos {
    width: 100%;
    min-height: 500px;
    margin-left: 10px;
  }
  .noComment {
    width: 100%;
    text-align: center;
  }
  .page {
    position: relative;
  }
  .block {
    position: relative;
    bottom: 0px;
  }
</style>