package com.moxi.mogublog.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LBX
 * @Date: 2019/5/14 16:09
 */
public class AliyunUpload {

    //初始化客户端
    public static DefaultAcsClient initVodClient(Map<String, Object> param) {
        DefaultProfile profile = DefaultProfile.getProfile(param.get("regionId").toString(), param.get("accessKeyId").toString(), param.get("accessKeySecret").toString());
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 获取视频上传地址和凭证
     */
    public static HashMap<String, Object> createUploadVideo(Map<String, Object> param) {
        DefaultAcsClient client = initVodClient(param);

        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(param.get("title").toString());
        request.setFileName(param.get("fileName").toString());
        HashMap<String, Object> map = new HashMap<>();
        try {
            CreateUploadVideoResponse response = client.getAcsResponse(request);
            map.put("Code", "0");
            map.put("VideoId", response.getVideoId());
            map.put("UploadAddress", response.getUploadAddress());
            map.put("UploadAuth", response.getUploadAuth());
            map.put("RequestId", response.getRequestId());
        } catch (ClientException e) {
            e.printStackTrace();
            map.put("Code", "1");
            map.put("ErrorMessage", e.getLocalizedMessage());
        }

        return map;
    }

    /**
     * 刷新视频上传凭证
     */
    public static Map<String, Object> refreshUploadVideo(Map<String, Object> param) {
        DefaultAcsClient client = initVodClient(param);
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        request.setVideoId(param.get("videoId").toString());

        Map<String, Object> map = new HashMap<>();
        try {
            RefreshUploadVideoResponse response = client.getAcsResponse(request);
            map.put("Code", "0");
            map.put("VideoId", response.getVideoId());
            map.put("UploadAddress", response.getUploadAddress());
            map.put("UploadAuth", response.getUploadAuth());
        } catch (ClientException e) {
            e.printStackTrace();
            map.put("Code", "1");
            map.put("ErrorMessage", e.getLocalizedMessage());
        }
        return map;
    }

    /*获取播放信息*/
    public static Map<String, Object> getVideoPlayAuth(Map<String, Object> param) {
        DefaultAcsClient client = initVodClient(param);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(param.get("videoId").toString());
        request.setAuthInfoTimeout(Long.valueOf(param.get("authInfoTimeout").toString()));
        Map<String, Object> map = new HashMap<>();
        try {
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            map.put("Code", "0");
            map.put("PlayAuth", response.getPlayAuth());
            map.put("RequestId", response.getRequestId());
            map.put("VideoId", response.getVideoMeta().getVideoId());
            map.put("Title", response.getVideoMeta().getTitle());
            map.put("CoverUrl", response.getVideoMeta().getCoverURL());
        } catch (ClientException e) {
            e.printStackTrace();
            map.put("Code", "1");
            map.put("ErrorMessage", e.getLocalizedMessage());
        }
        return map;
    }
    /*获取播放凭证函数*/
    public static Map<String, Object> getVideoPlayInfo(Map<String, Object> param) {
        DefaultAcsClient client = initVodClient(param);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(param.get("videoId").toString());
        Map<String, Object> map = new HashMap<>();
        try {
            GetPlayInfoResponse response = client.getAcsResponse(request);
            map.put("Code", "0");
            map.put("PlayInfo", response.getPlayInfoList());
            map.put("RequestId", response.getRequestId());
            map.put("VideoId", response.getVideoBase().getVideoId());
            map.put("Title", response.getVideoBase().getTitle());
            map.put("CoverUrl", response.getVideoBase().getCoverURL());
        } catch (ClientException e) {
            e.printStackTrace();
            map.put("Code", "1");
            map.put("ErrorMessage", e.getLocalizedMessage());
        }
        return map;
    }
}

