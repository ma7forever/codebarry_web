package com.codebarry.barryweb.xo.service.imp;

import com.codebarry.barryweb.base.serviceimpl.SuperServiceImpl;
import com.codebarry.barryweb.commons.entity.Admin;
import com.codebarry.barryweb.commons.entity.User;
import com.codebarry.barryweb.xo.mapper.AdminMapper;
import com.codebarry.barryweb.xo.mapper.UserMapper;
import com.codebarry.barryweb.xo.service.AdminService;
import com.codebarry.barryweb.xo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :cjh
 * @date : 10:42 2021/3/8
 */

@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    AdminService adminService;
}
