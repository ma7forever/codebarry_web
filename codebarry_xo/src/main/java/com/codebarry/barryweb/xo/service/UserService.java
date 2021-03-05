package com.codebarry.barryweb.xo.service;

import com.codebarry.barryweb.entity.User;

import java.util.List;

/**
 * @author :cjh
 * @date : 15:24 2021/3/5
 */
public interface UserService {
    /**
     * 通过ids获取用户列表
     *
     * @param ids
     * @return
     */
    public List<User> getUserListByIds(List<String> ids);
}
