package com.codebarry.barryweb.base.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codebarry.barryweb.base.mapper.SuperMapper;
import com.codebarry.barryweb.base.service.SuperService;

/**
 * @author :cjh
 * @date : 15:50 2021/3/5
 */

public class SuperServiceImpl<M extends SuperMapper<T>, T>  extends ServiceImpl<M, T> implements SuperService<T> {

}
