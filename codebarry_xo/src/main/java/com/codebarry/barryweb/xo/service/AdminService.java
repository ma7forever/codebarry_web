package com.codebarry.barryweb.xo.service;

import com.codebarry.barryweb.commons.entity.Admin;
import com.codebarry.barryweb.commons.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author :cjh
 * @date : 10:43 2021/3/8
 */
public interface AdminService {
    public Admin getAdminById(String id);
}
