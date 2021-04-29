package com.codebarry.barryweb.base.vo;

import com.baomidou.mybatisplus.core.injector.methods.Delete;
import com.codebarry.barryweb.base.validator.annotion.IdValid;
import com.codebarry.barryweb.base.validator.group.Update;
import lombok.Data;

/**
 * @author :cjh
 * @date : 11:10 2021/2/18
 */
@Data
public class BaseVO<T>  extends PageInfo<T> {
    /**
     * 唯一UID
     */
    @IdValid(groups = {Update.class, Delete.class})
    private String uid;

    private Integer status;
}
