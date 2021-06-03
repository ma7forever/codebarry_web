package com.moxi.mogublog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moxi.mogublog.commons.entity.Blog;
import com.moxi.mogublog.commons.entity.User;
import com.moxi.mogublog.commons.entity.Video;
import com.moxi.mogublog.xo.vo.StudyVideoVO;
import com.moxi.mogublog.xo.vo.UserVO;
import com.moxi.mogublog.xo.vo.VideoVO;
import com.moxi.mougblog.base.service.SuperService;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * @author :cjh
 * @date : 10:53 2021/5/9
 */
public interface VideoService  extends SuperService<Video> {

    public String addNewVideo(VideoVO videoVO);
    public String deleteVideo(List<VideoVO> videoVOList);
    public List<Video> getAllVideo(String page,String page2);
    public String getUserVideo(String page, String page2, User user);
    public IPage<Video> getPageList(VideoVO videoVO);
    public String editVideo(VideoVO VideoVO);
}
