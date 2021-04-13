package com.codebarry.barryweb.xo.service.imp;

import com.codebarry.barryweb.commons.entity.Admin;
import com.codebarry.barryweb.xo.mapper.AdminMapper;
import com.codebarry.barryweb.xo.mapper.UserMapper;
import com.codebarry.barryweb.xo.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author :cjh
 * @date : 10:42 2021/3/8
 */

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminById(String id) {
        return adminMapper.selectById("111");
    }
}
