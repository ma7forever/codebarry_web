package com.moxi.mogublog.admin.restapi;

import com.moxi.mogublog.admin.annotion.AuthorityVerify.AuthorityVerify;
import com.moxi.mogublog.admin.annotion.OperationLogger.OperationLogger;
import com.moxi.mogublog.admin.global.SysConf;
import com.moxi.mogublog.commons.entity.Video;
import com.moxi.mogublog.utils.ResultUtil;
import com.moxi.mogublog.xo.service.VideoService;
import com.moxi.mogublog.xo.vo.UserVO;
import com.moxi.mogublog.xo.vo.VideoVO;
import com.moxi.mougblog.base.exception.ThrowableUtils;
import com.moxi.mougblog.base.validator.group.Delete;
import com.moxi.mougblog.base.validator.group.GetList;
import com.moxi.mougblog.base.validator.group.Insert;
import com.moxi.mougblog.base.validator.group.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author :cjh
 * @date : 20:48 2021/6/1
 */
@RestController

@Api(value = "视频相关接口", tags = {"视频相关接口"})
@RequestMapping("/video")
@Slf4j
public class VideoRestApi {
    @Autowired
    VideoService videoService;
    @PostMapping("/addVideo")
    @ApiOperation(value = "添加视频")
    public String  addVideo(@RequestBody VideoVO videoVO){
        //凭证
        return ResultUtil.result(SysConf.SUCCESS, videoService.addNewVideo(videoVO));

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
        log.info("编辑学习视频: {}", videoVO);
        return videoService.editVideo(videoVO);
    }
    @ApiOperation(value = "获取视频列表", notes = "获取视频列表", response = String.class)
    @PostMapping(value = "/getList")
    public String getList(@Validated({GetList.class}) @RequestBody VideoVO videoVO, BindingResult result) {

        // 参数校验
        ThrowableUtils.checkParamArgument(result);
        log.info("获取视频列表: {}", videoVO);
        return ResultUtil.successWithData(videoService.getPageList(videoVO));
    }}
