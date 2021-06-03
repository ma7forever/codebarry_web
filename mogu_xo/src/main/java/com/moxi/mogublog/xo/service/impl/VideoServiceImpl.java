package com.moxi.mogublog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.mogublog.commons.entity.*;
import com.moxi.mogublog.commons.feign.PictureFeignClient;
import com.moxi.mogublog.utils.ResultUtil;
import com.moxi.mogublog.utils.StringUtils;
import com.moxi.mogublog.xo.global.MessageConf;
import com.moxi.mogublog.xo.global.SQLConf;
import com.moxi.mogublog.xo.global.SysConf;
import com.moxi.mogublog.xo.mapper.VideoMapper;
import com.moxi.mogublog.xo.service.*;
import com.moxi.mogublog.xo.utils.WebUtil;
import com.moxi.mogublog.xo.vo.StudyVideoVO;
import com.moxi.mogublog.xo.vo.VideoVO;
import com.moxi.mougblog.base.enums.EStatus;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author :cjh
 * @date : 14:04 2021/5/9
 * 视频上传相关接口
 */
@Service
public class VideoServiceImpl extends SuperServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    VideoService videoService;

    @Autowired
    private WebUtil webUtil;
    @Autowired
    private ResourceSortService resourceSortService;

    @Resource
    private PictureFeignClient pictureFeignClient;

    //管理员使用
    @Override
    public String addNewVideo(VideoVO videoVO) {
        //添加视频
       Video video = new Video();
       video.setTagList(video.getTagList());
       video.setTitle(videoVO.getTitle());
       video.setUpdateTime(new Date());
       video.setVideoSourceId(videoVO.getVideoSourceId());
       video.setPlayCount(videoVO.getPlayCount());
       video.setIsPublish(EStatus.ENABLE);
       video.setSummary(videoVO.getSummary());
       video.setSortUid(videoVO.getResourceSortUid());
        video.setFileUid(videoVO.getFileUid());
        video.setUserUid(videoVO.getUserUid());
        //插入数据库
        video.insert();
       return ResultUtil.successWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public String deleteVideo(List<VideoVO> videoVOList) {
        if (videoVOList.size() <= 0) {
            return ResultUtil.errorWithMessage(MessageConf.PARAM_INCORRECT);
        }
        List<String> uids = new ArrayList<>();
        videoVOList.forEach(item -> {
            uids.add(item.getUid());
        });
        Collection<Video> blogSortList = videoService.listByIds(uids);
        //全部删除
        blogSortList.forEach(item -> {
            item.setUpdateTime(new Date());
            item.setStatus(EStatus.DISABLED);
        });

        Boolean save = videoService.updateBatchById(blogSortList);

        if (save) {
            return ResultUtil.successWithMessage(MessageConf.DELETE_SUCCESS);
        } else {
            return ResultUtil.errorWithMessage(MessageConf.DELETE_FAIL);
        }
    }

    @Override
    public List<Video> getAllVideo(String page, String page2) {
        return null;
    }

    @Override
    public String getUserVideo(String page, String page2, User user) {
        return null;
    }

    @Override
    public IPage<Video> getPageList(VideoVO videoVO) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(videoVO.getKeyword()) && !StringUtils.isEmpty(videoVO.getKeyword().trim())) {
            queryWrapper.like("title", videoVO.getKeyword().trim());
        }

        Page<Video> page = new Page<>();
        page.setCurrent(videoVO.getCurrentPage());
        page.setSize(videoVO.getPageSize());

        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
        IPage<Video> pageList = videoService.page(page, queryWrapper);

        List<Video> list = pageList.getRecords();

        final StringBuffer fileUids = new StringBuffer();
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getFileUid())) {
                fileUids.append(item.getFileUid() + SysConf.FILE_SEGMENTATION);
            } //获取图片
        });

        String pictureResult = null;
        Map<String, String> pictureMap = new HashMap<>();
        //获取图片
        if (fileUids != null) {
            pictureResult = this.pictureFeignClient.getPicture(fileUids.toString(), SysConf.FILE_SEGMENTATION);
        }
        List<Map<String, Object>> picList = webUtil.getPictureMap(pictureResult);

        picList.forEach(item -> {
            pictureMap.put(item.get(SysConf.UID).toString(), item.get(SysConf.URL).toString());
        });

        for (Video item : list) {
            //获取分类资源
            if (StringUtils.isNotEmpty(item.getFileUid())) {
                List<String> pictureUidsTemp = StringUtils.changeStringToString(item.getFileUid(), SysConf.FILE_SEGMENTATION);
                List<String> pictureListTemp = new ArrayList<>();
                pictureUidsTemp.forEach(picture -> {
                    //添加标题图片
                    pictureListTemp.add(pictureMap.get(picture));
                });
                item.setPhotoList(pictureListTemp);
            }

            if (StringUtils.isNotEmpty(item.getSortUid())) {
                ResourceSort resourceSort = resourceSortService.getById(item.getSortUid());
                //获取资源分类
                item.setResourceSort(resourceSort);
            }
        }
        pageList.setRecords(list);

        return pageList;
    }

    @Override
    public String editVideo(VideoVO videoVO) {
        Video video = new Video();
        video.setTagList(video.getTagList());
        video.setTitle(videoVO.getTitle());
        video.setUpdateTime(new Date());
        video.setVideoSourceId(videoVO.getVideoSourceId());
        video.setPlayCount(videoVO.getPlayCount());
        video.setIsPublish(EStatus.ENABLE);
        video.setSummary(videoVO.getSummary());
        video.setSortUid(videoVO.getResourceSortUid());
        video.setFileUid(videoVO.getFileUid());
        video.updateById();
        return ResultUtil.successWithMessage(MessageConf.UPDATE_SUCCESS);
    }
}
