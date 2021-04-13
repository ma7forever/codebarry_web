package com.codebarry.barryweb.xo.service.imp;

import com.codebarry.barryweb.base.serviceimpl.SuperServiceImpl;
import com.codebarry.barryweb.commons.entity.Admin;
import com.codebarry.barryweb.commons.entity.User;
import com.codebarry.barryweb.xo.mapper.AdminMapper;
import com.codebarry.barryweb.xo.mapper.UserMapper;
import com.codebarry.barryweb.xo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
