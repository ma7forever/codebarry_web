package com.moxi.mogublog.xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moxi.mogublog.commons.entity.Blog;
import com.moxi.mogublog.commons.entity.Video;
import com.moxi.mougblog.base.service.SuperService;

/**
 * @author :cjh
 * @date : 10:53 2021/5/9
 */
public interface VideoService  extends SuperService<Video> {
    public IPage<Video> getNewVideo(Long currentPage, Long pageSize);

}
