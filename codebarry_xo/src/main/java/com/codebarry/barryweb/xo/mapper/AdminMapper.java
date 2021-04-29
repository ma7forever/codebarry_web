package com.codebarry.barryweb.xo.mapper;
import com.codebarry.barryweb.base.mapper.SuperMapper;
import com.codebarry.barryweb.commons.entity.Admin;
import org.apache.ibatis.annotations.Param;

/**
 * @author :cjh
 * @date : 10:40 2021/3/8
 */
public interface AdminMapper extends SuperMapper<Admin> {

    /**
     * 通过uid获取管理员
     *
     * @return
     */
    public Admin getAdminByUid(@Param("uid") String uid);
    
}
