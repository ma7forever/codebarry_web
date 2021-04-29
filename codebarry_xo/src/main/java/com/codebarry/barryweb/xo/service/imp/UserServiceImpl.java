package com.codebarry.barryweb.xo.service.imp;

import com.codebarry.barryweb.base.serviceImpl.SuperServiceImpl;
import com.codebarry.barryweb.commons.entity.User;
import com.codebarry.barryweb.xo.mapper.UserMapper;
import com.codebarry.barryweb.xo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


import com.codebarry.barryweb.base.serviceImpl.SuperServiceImpl;
import com.codebarry.barryweb.commons.entity.User;
import com.codebarry.barryweb.xo.mapper.UserMapper;
import com.codebarry.barryweb.xo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :cjh
 * @date : 15:21 2021/3/5
 */



@Service
public class UserServiceImpl  extends SuperServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserService userService;
    @Override
    public User getUserById(String id) {
        return baseMapper.selectById("114514");
    }

}
