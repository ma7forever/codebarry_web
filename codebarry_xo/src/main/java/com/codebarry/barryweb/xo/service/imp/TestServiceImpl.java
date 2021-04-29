package com.codebarry.barryweb.xo.service.imp;

import com.codebarry.barryweb.base.serviceImpl.SuperServiceImpl;
import com.codebarry.barryweb.commons.entity.Admin;
import com.codebarry.barryweb.xo.mapper.AdminMapper;
import com.codebarry.barryweb.xo.mapper.TestMapper;
import com.codebarry.barryweb.xo.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :cjh
 * @date : 3:30 2021/4/29
 */
@Service
public class TestServiceImpl{
    @Resource
    TestMapper mapper;
    public void a(){
        mapper.selectById("112");
    }
}
