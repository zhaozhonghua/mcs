package com.fjsmu.app;

/**
 * Created by zzh on 16/10/19.
 */
public class ErrorUtils {

    // dz api admin user
    /**
     * 错误码：后台管理 用户错误提示
     */
    public static final ErrorCode SUCCESS = new ErrorCode(0, "成功");
    public static final ErrorCode FORBIDDEN = new ErrorCode(403, "拒绝访问");
    public static final ErrorCode NOT_FOUND = new ErrorCode(404, "资源不存在");
    public static final ErrorCode METHOD_NOT_ALLOWED = new ErrorCode(405, "资源动词不支持");

    public static final ErrorCode UNKNOW_ERROR = new ErrorCode(1001, "未知错误");
    public static final ErrorCode PASSWORD_ERROR = new ErrorCode(1002, "用户密码不正确");
    public static final ErrorCode PARAMETER_STUDENTID_EMPTY = new ErrorCode(1003, "studentId不能为空");
    public static final ErrorCode USER_ALREADY_EXIST = new ErrorCode(1004, "该用户已经存在");
    public static final ErrorCode PASSWORD_BE_EMPTY = new ErrorCode(1005, "用户密码不能为空");
    public static final ErrorCode MOBILE_ALREADY_EXIST = new ErrorCode(1006, "该手机号已经存在");
    public static final ErrorCode SYSUSER_OLD_PASSWORD_REQUIRED = new ErrorCode(1007, "请填写原密码");
    public static final ErrorCode SYSUSER_NEW_PASSWORD_REQUIRED = new ErrorCode(1008, "请填写新密码");
    public static final ErrorCode SYSUSER_TWICE_PASSWORD_SAME = new ErrorCode(1009, "新密码不能与原密码一样");
    public static final ErrorCode SYSUSER_OLD_PASSWORD_ERROR = new ErrorCode(1010, "原密码不正确");

    public static final ErrorCode NOT_FOUND_MENU = new ErrorCode(1002, "菜单不存在");
    public static final ErrorCode NOT_FOUND_USER = new ErrorCode(1002, "用户不存在");
    public static final ErrorCode NOT_FOUND_ROLE = new ErrorCode(1002, "角色不存在");
    public static final ErrorCode ROLE_ID_BE_EMPTY = new ErrorCode(1002, "用户角色ID参数不能为空");
    public static final ErrorCode OLD_PASSWD_ERROR = new ErrorCode(1002, "旧密码不正确");

    public static final ErrorCode USERNAME_PASSWORD_BE_EMPTY = new ErrorCode(1003, "对象不存在");

    public static final ErrorCode OPERATION_FAILED = new ErrorCode(1004, "操作失败");
    public static final ErrorCode PARAMETER_BE_EMPTY = new ErrorCode(1005, "参数不能为空");
    public static final ErrorCode STUDENT_NOT_EXIST = new ErrorCode(1006, "宝宝信息不存在");

    public static final ErrorCode ADD_FAILED = new ErrorCode(1101, "添加失败");
    public static final ErrorCode UPDATE_FAILED = new ErrorCode(1102, "修改失败");
    public static final ErrorCode DELETE_FAILED = new ErrorCode(1103, "删除失败");

    //课程
    public static final ErrorCode CURRICULUM_SUBSCRIBE_FULL = new ErrorCode(2001, "课程报名人数已满，不能继续报名");
    public static final ErrorCode CURRICULUM_ALREADY_SUBSCRIBE = new ErrorCode(2002, "该课程您已经报名了");
    //新东方大鱼塘
    public static final ErrorCode NOTIFY_JSON_PARAM_INVALID = new ErrorCode(2003, "传入的参数错误");
    public static final ErrorCode NOTIFY_PROCESS_DATA_ERROR = new ErrorCode(2004, "处理成交信息出错");
    public static final ErrorCode NOTIFY_DYT_REPEAT_ORDER = new ErrorCode(0, "重复的订单");

    //家园共育
    public static final ErrorCode EDU_LIVE_CLASS_NOT_EXIST = new ErrorCode(3001, "请找老师将宝宝信息录入班级, 就能体验家园共育的功能了");
    public static final ErrorCode CLASS_CIYCLE_NOT_EXIST = new ErrorCode(3002, "请找老师将宝宝信息录入班级, 就能体验班级相册及班级视频的功能了");
    public static final ErrorCode CLASS_CIYCLE_FAVORITE_NOT_EXIST = new ErrorCode(3003, "请选择要收藏的班级圈");
    public static final ErrorCode CLASS_CIYCLE_ID_NOT_EXIST = new ErrorCode(3004, "删除的班级圈不存在");
    public static final ErrorCode CLASS_CIYCLE_ALREADY_SHARED = new ErrorCode(3005, "已分享过");
    public static final ErrorCode CLASS_CIYCLE_ALREADY_EXIST = new ErrorCode(3006, "来自于班级圈的收藏，请勿分享回去");
    public static final ErrorCode CLASS_CIYCLE_SHARE_SYNC_FAILED = new ErrorCode(3007, "分享至班级空间同步失败");

    //手机端扫码操作
    public static final ErrorCode MOBILE_SCAN_NOT_CLASS = new ErrorCode(4001, "您的宝宝还没有加入班级");
    public static final ErrorCode MOBILE_SCAN_NOTEXIST_PARENT = new ErrorCode(4002, "家长信息不存在");
    public static final ErrorCode MOBILE_SCAN_NOTEXIST_STUDENT = new ErrorCode(4003, "宝宝信息不存在");
    public static final ErrorCode MOBILE_RESCAN = new ErrorCode(4004, "请重新扫描二维码");
    public static final ErrorCode MOBILE_SCAN_NOTEXIST_DEVICE = new ErrorCode(4005, "设备信息不存在");
    public static final ErrorCode MOBILE_PLEASE_CHOOSE_PICTURE_VIDEO = new ErrorCode(4006, "请选择图片或视频");
    public static final ErrorCode MOBILE_DISABLE_PARENT = new ErrorCode(4007, "无效用户");

    //student宝宝列表
    public static final ErrorCode STUDENT_MOBILE_IS_NULL = new ErrorCode(4011, "请输入手机号");
    public static final ErrorCode STUDENT_MOBILE_NOTEXIST_PARENT = new ErrorCode(4012, "该手机号不是唷唷兔平台的注册用户哦");
    public static final ErrorCode STUDENT_MOBILE_SAME_AS_SELF = new ErrorCode(4013, "这是您自己的手机号，添加好友需要输入唷唷兔平台注册用户的手机号");
    public static final ErrorCode STUDENT_PARENT_NO_STUDENT = new ErrorCode(4014, "抱歉，该手机号用户还没添加宝宝信息");
    public static final ErrorCode STUDENT_ALREADY_LOGIN = new ErrorCode(4014, "当前宝宝已在另一台设备登录");

    //个人中心
    //我的消息
    public static final ErrorCode STUDENT_MSG_NOT_EXIST = new ErrorCode(5001, "暂时没有信息");
    public static final ErrorCode STUDENT_STATUS_DELETE_NOT_EXIST = new ErrorCode(5002, "图片或视频不存在");
    public static final ErrorCode STUDENT_STATUS_DELETE_ALREADY_DELETE = new ErrorCode(5003, "已删除过");

    //宝贝圈
    public static final ErrorCode FRIEND_ALREADY_ACCEPT = new ErrorCode(6001, "已经是您的好友，不能重复添加哦");

    // 会员中心
    public static final ErrorCode MEMBER_TYPE_NAME_ALREADY_EXIST = new ErrorCode(7001, "会员类型名称已经存在");
    public static final ErrorCode MEMBER_TYPE_PRICE_ZERO_ERROR = new ErrorCode(7002, "会员价格不能为0元");
    public static final ErrorCode MEMBER_TYPE_MONTHS_ZERO_ERROR = new ErrorCode(7003, "会员期限不能为0个月");
    public static final ErrorCode TRADE_PAY_FAILED = new ErrorCode(7004, "下单失败");
    public static final ErrorCode MEMBER_RECOM_CODE_EMPTY = new ErrorCode(7005, "推荐码不能为空");
    public static final ErrorCode MEMBER_RECOM_NOT_EXIST = new ErrorCode(7006, "无效的推荐码");
    public static final ErrorCode MEMBER_RECOM_CODE_LENGTH = new ErrorCode(7007, "推荐码为6位数字");
    public static final ErrorCode MEMBER_SALES_NOT_EXIST = new ErrorCode(7008, "推广人员不存在");
    public static final ErrorCode MEMBER_SALES_STATUS_ALREADY_PASS = new ErrorCode(7009, "推广人员已经认证通过");
    public static final ErrorCode MEMBER_SALES_ALREADY_SUBMIT_INFO = new ErrorCode(7010, "正在审核中，请勿重新提交");
    public static final ErrorCode MEMBER_TYPE_NOT_EXIST = new ErrorCode(7011, "无法识别您购买的会员套餐");
    public static final ErrorCode MEMBER_PAY_TOO_SMALL = new ErrorCode(7012, "支付金额少于1分");
    public static final ErrorCode MEMBER_TYPE_RECOM_PRICE_TOO_BIG = new ErrorCode(7013, "推荐优惠金额不能大于套餐总金额");
    public static final ErrorCode MEMBER_TYPE_MOBILE_ALREADY_AUTH = new ErrorCode(7014, "该手机号已存在认证信息");
    public static final ErrorCode MEMBER_TYPE_AUTH_STATUS_UNUSE = new ErrorCode(7015, "认证状态无效");

    //app端公用
    public static final ErrorCode REQUEST_PARAMETER_ERROR = new ErrorCode(9001, "请确认要修改的参数");
    public static final ErrorCode SYSTEM_ERROR = new ErrorCode(9999, "系统错误");

}
