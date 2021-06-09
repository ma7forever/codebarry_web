package com.moxi.mogublog.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moxi.mogublog.commons.entity.*;
import com.moxi.mogublog.commons.feign.PictureFeignClient;
import com.moxi.mogublog.utils.*;
import com.moxi.mogublog.utils.ServerInfo.Sys;
import com.moxi.mogublog.xo.global.MessageConf;
import com.moxi.mogublog.xo.global.RedisConf;
import com.moxi.mogublog.xo.global.SQLConf;
import com.moxi.mogublog.xo.global.SysConf;
import com.moxi.mogublog.xo.mapper.VideoMapper;
import com.moxi.mogublog.xo.service.*;
import com.moxi.mogublog.xo.utils.WebUtil;
import com.moxi.mogublog.xo.vo.StudyVideoVO;
import com.moxi.mogublog.xo.vo.UserVO;
import com.moxi.mogublog.xo.vo.VideoVO;
import com.moxi.mougblog.base.enums.ECommentSource;
import com.moxi.mougblog.base.enums.ECommentType;
import com.moxi.mougblog.base.enums.EPublish;
import com.moxi.mougblog.base.enums.EStatus;
import com.moxi.mougblog.base.holder.RequestHolder;
import com.moxi.mougblog.base.serviceImpl.SuperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author :cjh
 * @date : 14:04 2021/5/9
 * 视频上传相关接口
 */

@Slf4j
@Service
public class VideoServiceImpl extends SuperServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    CommentService commentService;
    @Autowired
    VideoService videoService;
    @Autowired
    UserService userService;
    @Autowired
    SysParamsService sysParamsService;

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
        if (StringUtils.isNotEmpty(videoVO.getUserUid()) && !StringUtils.isEmpty(videoVO.getUserUid().trim())) {
            queryWrapper.eq("user_uid", videoVO.getUserUid().trim());
        }
        if (videoVO.getStatus() != null) {
            queryWrapper.eq(SQLConf.STATUS, videoVO.getStatus());
        } else {
            queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        }
        if (videoVO.getIsPublish() != null) {
            queryWrapper.eq("is_publish", videoVO.getIsPublish());
        }

        Page<Video> page = new Page<>();
        page.setCurrent(videoVO.getCurrentPage());
        page.setSize(videoVO.getPageSize());

        queryWrapper.orderByDesc(SQLConf.CREATE_TIME);
        IPage<Video> pageList = videoService.page(page, queryWrapper);

        List<Video> list = pageList.getRecords();


        String pictureResult = null;
        Map<String, String> pictureMap = new HashMap<>();

        for (Video item : list) {
            //fileuid为空的情况下
            if (StringUtils.isNotEmpty(item.getVideoSourceId())) {
                Map<String, Object> params = new HashMap<>();
                params.put("regionId", "cn-shanghai");
                params.put("accessKeyId", "LTAI5tLoXXEkZBUSjh5mXkmF");
                params.put("accessKeySecret", "yqdej9OTbvaZlz4uhW1QbGMbkppmyh");//通过阿里云获得封面图片
                params.put("videoId", item.getVideoSourceId());
                Map<String, Object> resultMap = AliyunUpload.getVideoPlayInfo(params);
                List<String> pictureListTemp = new ArrayList<>();
                pictureListTemp.add(resultMap.get("CoverUrl").toString());
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
        video.setUid(videoVO.getUid());
        video.setTagList(video.getTagList());
        video.setStatus(videoVO.getStatus());
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


    @Override
    public User getVideoAuthor(String videoId) {

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_source_id", videoId);
        Video video = videoService.getBaseMapper().selectOne(queryWrapper);
        log.info(video.toString());
        User user = userService.getById(video.getUserUid());
        log.info(user.toString());
        return user;
    }

    @Override
    public String addUserVideo(VideoVO videoVO) {
        //添加视频
        Video video = new Video();

        video.setTagList(video.getTagList());
        video.setTitle(videoVO.getTitle());
        video.setUpdateTime(new Date());
        video.setVideoSourceId(videoVO.getVideoSourceId());
        video.setPlayCount(videoVO.getPlayCount());
        video.setIsPublish(EStatus.AUDIT);
        video.setStatus(EStatus.AUDIT);
        video.setSummary(videoVO.getSummary());
        video.setSortUid(videoVO.getResourceSortUid());
        video.setFileUid(videoVO.getFileUid());
        video.setUserUid(videoVO.getUserUid());
        //插入数据库
        video.insert();
        return ResultUtil.successWithMessage(MessageConf.INSERT_SUCCESS);
    }

    @Override
    public Video getVideoInfo(String videoId) {

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_source_id", videoId);
        Video video = videoService.getBaseMapper().selectOne(queryWrapper);
        log.info(video.toString());
        return video;
    }

    @Override
    public String priseVideoByUid(String videoId) {
        if (StringUtils.isEmpty(videoId)) {
            return ResultUtil.errorWithMessage(MessageConf.PARAM_INCORRECT);
        }

        HttpServletRequest request = RequestHolder.getRequest();
        // 如果用户登录了
        if (request.getAttribute(SysConf.USER_UID) != null) {
            String userUid = request.getAttribute(SysConf.USER_UID).toString();
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(SQLConf.USER_UID, userUid);
            queryWrapper.eq(SQLConf.BLOG_UID, videoId);
            queryWrapper.eq(SQLConf.TYPE, ECommentType.PRAISE);
            queryWrapper.last(SysConf.LIMIT_ONE);
            Comment praise = commentService.getOne(queryWrapper);
            if (praise != null) {
                return ResultUtil.errorWithMessage(MessageConf.YOU_HAVE_BEEN_PRISE);
            }
        } else {
            return ResultUtil.errorWithMessage(MessageConf.PLEASE_LOGIN_TO_PRISE);
        }
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_source_id",videoId);
        Video video = videoService.getOne(queryWrapper);
        log.info(video.toString());

        String pariseJsonResult = redisUtil.get(RedisConf.BLOG_PRAISE + RedisConf.SEGMENTATION + videoId);
        if (StringUtils.isEmpty(pariseJsonResult)) {
            //给该博客点赞数
            redisUtil.set(RedisConf.BLOG_PRAISE + RedisConf.SEGMENTATION + videoId, "1");
            video.setPriseCount(1);
            video.updateById();

        } else {
            Integer count = video.getPriseCount() + 1;
            //给该博客点赞 +1
            redisUtil.set(RedisConf.BLOG_PRAISE + RedisConf.SEGMENTATION + videoId, count.toString());
            video.setPriseCount(count);
            video.updateById();
        }
        // 已登录用户，向评论表添加点赞数据
        if (request.getAttribute(SysConf.USER_UID) != null) {
            String userUid = request.getAttribute(SysConf.USER_UID).toString();
            Comment comment = new Comment();
            comment.setUserUid(userUid);
            comment.setBlogUid(videoId);
            comment.setSource(ECommentSource.VIDEO_INFO.getCode());
            comment.setType(ECommentType.PRAISE);
            comment.insert();
        }
        return ResultUtil.successWithData(video.getPriseCount());
    }

    @Override
    public Integer getVideoPriseCountByUid(String videoId) {
        Integer pariseCount = 0;
        if (StringUtils.isEmpty(videoId)) {
            log.error("传入的videoUID为空");
            return pariseCount;
        }
        //从Redis取出用户点赞数据
        String pariseJsonResult = redisUtil.get(RedisConf.BLOG_PRAISE + RedisConf.SEGMENTATION + videoId);
        if (!StringUtils.isEmpty(pariseJsonResult)) {
            pariseCount = Integer.parseInt(pariseJsonResult);
        }
        return pariseCount;
    }

    @Override
    public IPage<Video> getHotVideo() {
        //从Redis中获取内容
        String jsonResult = redisUtil.get(RedisConf.HOT_BLOG);
        //判断redis中是否有文章
//        if (StringUtils.isNotEmpty(jsonResult)) {
//            List jsonResult2List = JsonUtils.jsonArrayToArrayList(jsonResult);
//            IPage pageList = new Page();
//            pageList.setRecords(jsonResult2List);
//            return pageList;
//        }
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        Page<Video> page = new Page<>();
        page.setCurrent(0);

        String blogHotCount = sysParamsService.getSysParamsValueByKey(SysConf.BLOG_HOT_COUNT);

        if (StringUtils.isEmpty(blogHotCount)) {
            log.error(MessageConf.PLEASE_CONFIGURE_SYSTEM_PARAMS);
        } else {
            page.setSize(Long.valueOf(blogHotCount));
        }
        queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
        queryWrapper.eq(SQLConf.IS_PUBLISH, EPublish.PUBLISH);
        queryWrapper.orderByDesc(SQLConf.playCount);
        //因为首页并不需要显示内容，所以需要排除掉内容字段
        IPage<Video> pageList = videoService.page(page, queryWrapper);
        List<Video> list = pageList.getRecords();
        for (Video item : list) {
            //fileuid为空的情况下
            if (StringUtils.isNotEmpty(item.getVideoSourceId())) {
                Map<String, Object> params = new HashMap<>();
                params.put("regionId", "cn-shanghai");
                params.put("accessKeyId", "LTAI5tLoXXEkZBUSjh5mXkmF");
                params.put("accessKeySecret", "yqdej9OTbvaZlz4uhW1QbGMbkppmyh");//通过阿里云获得封面图片
                params.put("videoId", item.getVideoSourceId());
                Map<String, Object> resultMap = AliyunUpload.getVideoPlayInfo(params);
                List<String> pictureListTemp = new ArrayList<>();
                pictureListTemp.add(resultMap.get("CoverUrl").toString());
                item.setPhotoList(pictureListTemp);
            }

            if (StringUtils.isNotEmpty(item.getSortUid())) {
                ResourceSort resourceSort = resourceSortService.getById(item.getSortUid());
                //获取资源分类
                item.setResourceSort(resourceSort);
            }
        }

        pageList.setRecords(list);
        // 将从数据库查询的数据缓存到redis中[避免list中没有数据而保存至redis的情况]
//        if (list.size() > 0) {
//            redisUtil.setEx(RedisConf.HOT_BLOG, JsonUtils.objectToJson(list), 1, TimeUnit.HOURS);
//        }
        return pageList;
    }

}
