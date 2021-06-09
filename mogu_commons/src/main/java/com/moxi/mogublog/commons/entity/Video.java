package com.moxi.mogublog.commons.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moxi.mougblog.base.entity.SuperEntity;
import lombok.Data;
import lombok.ToString;
import org.omg.CORBA.StringHolder;

import java.math.BigInteger;
import java.util.List;

/**
 * @author :cjh
 * @date : 11:27 2021/5/9
 */

@Data
@TableName("t_video")
@ToString
public class Video extends SuperEntity<Video>{
    private static final long serialVersionUID = 1L;
    private String title;
    private Integer playCount;
    private BigInteger size;
    private Float duration;
    private String videoOriginalName;
    private String videoSourceId;
    private Integer sort;
    private String userUid;
    private String sortUid;
    private String tagUid;
    @TableField(exist = false)
    private BlogSort blogSort;
    @TableField(exist = false)
    private List<Tag> tagList;
    private String fileUid;
    @TableField(exist = false)

    /**
     * 是否发布
     */
    private Integer isPublish;
    private String summary;
    @TableField(exist = false)
    private List<String> photoList;
    private Integer priseCount;//点赞数

    /**
     * 资源分类
     */
    @TableField(exist = false)
    private ResourceSort resourceSort;
}
