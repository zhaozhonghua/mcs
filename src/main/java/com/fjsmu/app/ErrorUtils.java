package com.fjsmu.app;

public class ErrorUtils {

    // dz api admin user
    /**
     * 错误码：后台管理 用户错误提示
     */
    public static final ErrorCode SUCCESS = new ErrorCode(0, "成功");
    public static final ErrorCode FORBIDDEN = new ErrorCode(403, "拒绝访问");
    public static final ErrorCode NOT_FOUND = new ErrorCode(404, "资源不存在");
    public static final ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "资源动词不支持");

    public static final ErrorCode DATA_NOT_EXIST = new ErrorCode(1001, "数据不存在");
    public static final ErrorCode REQUEST_PARAMETER_ERROR = new ErrorCode(1002, "请提交需要修改的数据");

    public static final ErrorCode OPERATION_FAILED = new ErrorCode(1004, "操作失败");

    public static final ErrorCode PATIENT_ALREADY_REGISTER = new ErrorCode(2001, "请稍等, 正在处理中, 不用重复挂号");

}
