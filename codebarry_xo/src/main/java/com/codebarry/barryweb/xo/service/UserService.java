package com.codebarry.barryweb.xo.service;

import com.codebarry.barryweb.base.service.SuperService;
import com.codebarry.barryweb.commons.entity.User;

import java.util.List;

/**
 * @author :cjh
 * @date : 15:24 2021/3/5
 */
public interface UserService extends SuperService<User> {

 //   public List<User> getUserListByIds(List<String> ids);
    public User getUserById(String id);
}
