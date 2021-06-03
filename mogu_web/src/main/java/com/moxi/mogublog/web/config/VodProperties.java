package com.moxi.mogublog.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 视频点播VOD，相关配置信息
 *
 * @author lbx
 * @date 2018/12/10 10:59
 */
@Component
@Data
@ConfigurationProperties(prefix = "vod.configs")
public class VodProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;
}

