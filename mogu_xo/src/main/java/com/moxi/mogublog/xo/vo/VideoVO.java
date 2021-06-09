package com.moxi.mogublog.xo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moxi.mogublog.commons.entity.BlogSort;
import com.moxi.mogublog.commons.entity.Tag;
import com.moxi.mougblog.base.validator.annotion.NotBlank;
import com.moxi.mougblog.base.validator.group.Insert;
import com.moxi.mougblog.base.validator.group.Update;
import com.moxi.mougblog.base.vo.BaseVO;
import lombok.Data;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;

/**
 * @author :cjh
 * @date : 0:36 2021/5/28
 */
@ToString
@Data
public class VideoVO extends BaseVO<VideoVO> {

    private static final long serialVersionUID = 1L;

    @NotBlank(groups = {Insert.class, Update.class})
    private String title;
    private Integer playCount;
    private BigInteger size;
    private Float duration;
    private String videoOriginalName;
    private String videoSourceId;
    private Integer sort;
    private String userUid;
    private String re;
    private String tagUid;
    private BlogSort blogSort;
    private List<Tag> tagList;
    private String fileUid;
    private Integer priseCount;
    private String resourceSortUid;
    /**
     * 是否发布
     */
    private Integer isPublish;

    private String summary;
}
