package com.codebarry.barryweb.commons.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author :cjh
 * @date : 13:05 2021/2/18
 */
@TableName("t_admin")
@Data
public class Admin {
    @TableId(value = "uid")
    private String uid;
    private String userName;
}
