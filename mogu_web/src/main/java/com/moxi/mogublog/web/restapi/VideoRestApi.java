package com.moxi.mogublog.web.restapi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moxi.mogublog.commons.entity.Blog;
import com.moxi.mogublog.commons.entity.StudyVideo;
import com.moxi.mogublog.commons.entity.Video;
import com.moxi.mogublog.utils.IpUtils;
import com.moxi.mogublog.utils.StringUtils;
import com.moxi.mogublog.web.config.AliyunUpload;
import com.moxi.mogublog.utils.ResultUtil;
import com.moxi.mogublog.web.config.VodProperties;
import com.moxi.mogublog.web.global.MessageConf;
import com.moxi.mogublog.web.global.SysConf;
import com.moxi.mogublog.xo.global.RedisConf;
import com.moxi.mogublog.xo.service.VideoService;
import com.moxi.mogublog.xo.vo.StudyVideoVO;
import com.moxi.mogublog.xo.vo.VideoVO;
import com.moxi.mougblog.base.enums.EPublish;
import com.moxi.mougblog.base.enums.EStatus;
import com.moxi.mougblog.base.exception.ThrowableUtils;
import com.moxi.mougblog.base.global.Constants;
import com.moxi.mougblog.base.global.ECode;
import com.moxi.mougblog.base.holder.RequestHolder;
import com.moxi.mougblog.base.validator.group.Delete;
import com.moxi.mougblog.base.validator.group.GetList;
import com.moxi.mougblog.base.validator.group.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author :cjh
 * @date : 17:08 2021/5/17
 */
@RestController

@Slf4j
@Api(value = "阿里云视频相关接口", tags = {"阿里云视频相关接口"})
@RequestMapping("/videoAL")
public class VideoRestApi {
    @Autowired
    private VodProperties vodProperties;
    @Autowired
    private VideoService videoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/createUploadVideo")
    @ApiOperation(value = "获取视频上传地址和凭证")
    public String createUploadVideo(@RequestParam Map<String, Object> params){
        params.put("regionId",vodProperties.getRegionId());
        params.put("accessKeyId",vodProperties.getAccessKeyId());
        params.put("accessKeySecret",vodProperties.getAccessKeySecret());

        Map<String, Object> resultMap = AliyunUpload.createUploadVideo(params);
        return ResultUtil.result(SysConf.SUCCESS,resultMap);
    }

    @PostMapping("/refreshUploadVideo")
    @ApiOperation(value = "刷新视频上传凭证")
    public String refreshUploadVideo(@RequestParam Map<String, Object> params){
        params.put("regionId",vodProperties.getRegionId());
        params.put("accessKeyId",vodProperties.getAccessKeyId());
        params.put("accessKeySecret",vodProperties.getAccessKeySecret());

        Map<String, Object> resultMap = AliyunUpload.refreshUploadVideo(params);
        return ResultUtil.result(SysConf.SUCCESS,resultMap);
    }

    @PostMapping("/getVideoPlayAuth")
    @ApiOperation(value = "获取播放凭证函数")
    public String  getVideoPlayAuth(@RequestParam Map<String, Object> params){

        params.put("regionId",vodProperties.getRegionId());
        params.put("accessKeyId",vodProperties.getAccessKeyId());
        params.put("accessKeySecret",vodProperties.getAccessKeySecret());
        params.put("authInfoTimeout",3000);//播放凭证过期时间。取值范围：100~3000。
      Map<String, Object> resultMap = AliyunUpload.getVideoPlayAuth(params);
       return ResultUtil.result(SysConf.SUCCESS,resultMap);
    }
    @PostMapping("/getPlayInfo")
    @ApiOperation(value = "获取播放信息")
    public String  getPlayInfo(@RequestParam Map<String, Object> params){

        params.put("regionId",vodProperties.getRegionId());
        params.put("accessKeyId",vodProperties.getAccessKeyId());
        params.put("accessKeySecret",vodProperties.getAccessKeySecret());
        Map<String, Object> resultMap = AliyunUpload.getVideoPlayInfo(params);
        return ResultUtil.result(SysConf.SUCCESS,resultMap);
    }
    @PostMapping("/addVideo")
    @ApiOperation(value = "添加视频")
    public String  addVideo(@RequestBody VideoVO videoVO){
        return ResultUtil.result(SysConf.SUCCESS, videoService.addUserVideo(videoVO));
    }
    @PostMapping("/delete")
    @ApiOperation(value = "删除视频")
    public String  deleteVideo(@Validated({Delete.class}) @RequestBody List<VideoVO> videoVOList, BindingResult result){
        return ResultUtil.result(SysConf.SUCCESS, videoService.deleteVideo(videoVOList));
    }
    @PostMapping("/edit")
    @ApiOperation(value = "编辑视频")
    public String edit(@Validated({Update.class}) @RequestBody VideoVO videoVO, BindingResult result) {

        // 参数校验b
        ThrowableUtils.checkParamArgument(result);
        log.info("编辑视频: {}", videoVO);
        return videoService.editVideo(videoVO);
    }
    @ApiOperation(value = "获取视频列表", notes = "获取视频列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody VideoVO videoVO, BindingResult result) {

        // 参数校验
        ThrowableUtils.checkParamArgument(result);
        log.info("获取视频列表: {}", videoVO);
        return ResultUtil.successWithData(videoService.getPageList(videoVO));
    }
    @ApiOperation(value = "获取视频作者信息", notes = "获取视频作者信息", response = String.class)
    @PostMapping(value = "/getVideoAuthor")
    public String getVideoAuthor( @ApiParam(name = "videoId", value = "视频ID", required = false) @RequestParam(name = "videoId", required = false)String videoId) {
        HttpServletRequest request = RequestHolder.getRequest();
        String ip = IpUtils.getIpAddr(request);
        Video video = null;
        if (StringUtils.isNotEmpty(videoId)) {
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SysConf.videoSourceId, videoId);
            queryWrapper.last(SysConf.LIMIT_ONE);
            video = videoService.getOne(queryWrapper);
        }

        if (video == null || video.getStatus() == EStatus.DISABLED || EPublish.NO_PUBLISH.equals(video.getIsPublish())) {
            return ResultUtil.result(ECode.ERROR, MessageConf.BLOG_IS_DELETE);
        }

        String jsonResult = stringRedisTemplate.opsForValue().get("BLOG_CLICK:" + ip + "#" + video.getUid());
        if (StringUtils.isEmpty(jsonResult)) {

            //给博客播放数增加
            Integer clickCount = video.getPlayCount() + 1;
            video.setPlayCount(clickCount);
            video.updateById();

            //将该用户点击记录存储到redis中, 24小时后过期
            stringRedisTemplate.opsForValue().set(RedisConf.BLOG_CLICK + Constants.SYMBOL_COLON + ip + Constants.SYMBOL_WELL + video.getUid(), video.getPlayCount().toString(),
                    24, TimeUnit.HOURS);
        }
        log.info("获取视频videoId: {}", videoId);
        return ResultUtil.successWithData(videoService.getVideoAuthor(videoId));
    }
    @ApiOperation(value = "获取视频在数据库的信息", notes = "获取视频在数据库信息", response = String.class)
    @PostMapping(value = "/getVideoInfo")
    public String getVideoInfo( @ApiParam(name = "videoId", value = "视频ID", required = false) @RequestParam(name = "videoId", required = false)String videoId) {

        log.info("获取视频videoId: {}", videoId);
        return ResultUtil.successWithData(videoService.getVideoInfo(videoId));
    }
    @ApiOperation(value = "点赞视频", notes = "点赞视频", response = String.class)
    @PostMapping(value = "/priseVideoByUid")
    public String priseVideoByUid( @ApiParam(name = "videoId", value = "视频ID", required = false) @RequestParam(name = "videoId", required = false)String videoId) {
        if (StringUtils.isEmpty(videoId)) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.PARAM_INCORRECT);
        }
        log.info("获取视频videoId: {}", videoId);
        return videoService.priseVideoByUid(videoId);
    }
    @ApiOperation(value = "获取视频点赞数", notes = "获取视频点赞数", response = String.class)
    @PostMapping(value = "/getVideoPriseCountByUid")
    public String getVideoPriseCountByUid( @ApiParam(name = "videoId", value = "视频ID", required = false) @RequestParam(name = "videoId", required = false)String videoId) {
        log.info("获取视频videoId: {}", videoId);
        return ResultUtil.result(SysConf.SUCCESS, videoService.getVideoPriseCountByUid(videoId));
    }
    @ApiOperation(value = "获取视频点赞数", notes = "获取视频点赞数", response = String.class)
    @PostMapping(value = "/getVideoPriseCountByUid")
    public String getVideoPriseCountByUid( @ApiParam(name = "videoId", value = "视频ID", required = false) @RequestParam(name = "videoId", required = false)String videoId) {
        log.info("获取视频videoId: {}", videoId);
        return ResultUtil.result(SysConf.SUCCESS, videoService.getVideoPriseCountByUid(videoId));
    }
}
