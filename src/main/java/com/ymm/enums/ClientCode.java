/**
 * Created with IntelliJ IDEA.
 * User: walker
 * Date: 15/8/5
 * Time: 23:0
 * To change this template use File | Settings | File Templates.
 */

package com.ymm.enums;

public enum ClientCode {
    SUCCESS(1, "成功"),

    INVALID_USER_ID(-2001, "无效的用户"),
    INVALID_ORDER_ID(-2002, "无效的订单"),
    INVALID_ORDER_STATE(-2003, "无效的订单状态"),
    CREATE_CARGO_FAIL(-2004, "创建货源失败，请稍后重试"),
    CREATE_ORDER_FAIL(-2005, "创建订单失败，请稍后重试"),
    NO_BID(-2006, "不存在此竞价信息"),
    INVALID_BID(-2007, "无效的竞价信息"),
    FAIL_DEAL(-2008, "成交失败，请稍后重试"),
    FAIL_UPDATE_BID(-2009, "更新竞价信息失败，请稍后重试"),
    NO_DRIVER(-2010, "找不到此司机信息"),
    FAIL_SAVE_ORDER_DRIVER(-2011, "保存司机信息失败，请稍后重试"),
    FAIL_CHANGE_ORDER_ASSIGNED(-2012, "切换订单状态到已分配失败，请稍后重试"),
    ASSIGN_NO_DRIVERS(-2013, "分配的司机为空"),
    FAIL_SAVE_DRIVERS(-2014, "保存司机信息失败，请稍后重试"),
    FAIL_CHANGE_ORDER_ACCOMPLISHED(-2015, "切换订单状态到已完成失败，请稍后重试"),
    INVALID_CANCEL_REASON(-2016, "无效的取消原因"),
    INVALID_CANCEL_REMARK(-2017, "无效的取消备注"),


    SYSTEM_WRONG(-200, "系统繁忙，请稍后重试"),
    OBJECT_NOT_FOUND(-201,"未找到请求的数据"),
    BAD_REQUEST(-203,"无效的请求"),
    UPLOAD_FILE_ERROR(-204,"文件上传错误"),

    USER_NOT_LOGIN(-1000,"用户未登录"),
    FORBIDDEN(403,"权限不足"),
    ;

    private int code;

    private String msg;

    private ClientCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
