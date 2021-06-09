package com.moxi.mougblog.base.enums;

/**
 * 状态枚举类
 *
 * @author xuzhixiang
 * @date 2017年10月4日16:34:00
 */
public class EStatus {
    /**
     * 删除的
     */
    public static final int DISABLED = 0;
    /**
     * 激活的
     */
    public static final int ENABLE = 1;
    /**
     * 冻结的
     */
    public static final int FREEZE = 2;
    /**
     * 置顶的
     */
    public static final int STICK = 3;
    public static  final int AUDIT =4; //待审核
    public static final int AUDITFAIL = 5;//审核未通过
}
