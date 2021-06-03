package com.moxi.mogublog.utils;

import com.aliyun.tea.*;
import com.aliyun.vod20170321.*;
import com.aliyun.vod20170321.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;
/**
 * @author :cjh
 * @date : 22:11 2021/5/21
 */
public class AliyunUtil {
    public static com.aliyun.vod20170321.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "vod.cn-shanghai.aliyuncs.com";
        return new com.aliyun.vod20170321.Client(config);
    }
}
