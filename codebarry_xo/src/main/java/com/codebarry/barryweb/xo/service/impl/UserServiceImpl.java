package com.codebarry.barryweb.xo.service.impl;

import com.codebarry.barryweb.base.serviceimpl.SuperServiceImpl;
import com.codebarry.barryweb.commons.entity.User;
import com.codebarry.barryweb.xo.mapper.UserMapper;
import com.codebarry.barryweb.xo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :cjh
 * @date : 15:21 2021/3/5
 */


@Service
public class UserServiceImpl  extends SuperServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserService userService;
    @Override
    public User getUserById(String id) {
        return baseMapper.selectById("114514");
    }

}
