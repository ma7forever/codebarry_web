package com.codebarry.barrayweb.admin.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :cjh
 * @date : 13:07 2021/2/18
 */
@RestController
@Data
public class LoginController {
    private String userName;
    private String password;
    private String nickname;
    private String gender;
    private String avatar;
    private String email;
    private String mobile;
    private String weChat;
    private String occupation;

    /**
     * 自我简介最多150字
     */
    private String summary;


    /**
     * 资料来源
     */
    private String source;

    /**
     * 平台uuid
     */
    private String uuid;

    /**
     * 评论状态，0 禁言， 1 正常
     */
    private Integer commentStatus;

    /**
     * 开启邮件通知：  0：关闭， 1：开启
     */
    private Integer startEmailNotification;

    /**
     * 用户标签  0：普通，1：管理员，2：博主
     */
    private Integer userTag;

    /**
     * 用户头像
     */
    private String photoUrl;

}
