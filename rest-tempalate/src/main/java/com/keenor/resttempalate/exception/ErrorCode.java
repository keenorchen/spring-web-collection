package com.keenor.resttempalate.exception;

/**
 * Description:
 * -----------------------------------------------
 * Author:      chenliuchun
 * Date:        2019-11-01 17:32
 * Revision history:
 * Date         Remark
 * --------------------------------------------------
 */

public enum ErrorCode {

    SUCCESS("成功"),
    ERROR("系统错误"),
    SYSTEM_ERROR("系统内部错误"),
    UNBELIEVABLE_ERROR("计划外的错误"),

    PARAM_VERIFY_ERROR("参数数据校验失败"),
    PARAM_INVALID_ERROR("参数非法错误"),
    PARAM_REPEAT_ERROR("参数重复错误"),
    SIGNATURE_RECOVER_PUBLICADDRESS_ERROR("signature恢复公钥地址失败"),

    CONFIG_INVALID_ERROR("配置参数错误"),

    NO_PERMISSION("无权限操作"),
    NOT_LOGIN("未登录"),

    USERNAME_ERROR("用户名错误"),
    PASSWORD_ERROR("密码错误"),
    USER_IS_LOCKED("用户已被停用"),
    PASSWORD_ERROR_OVERRUN("密码错误次数超限"),
    OLD_PASSWORD_ERROR("密码错误"),
    PWD_NEWPWD_IS_SAME("两次密码相同"),
    AUTH_ERROR("控制权限校验失败"),
    REGISTER_REPEAT("重复的注册"),
    LOGIN_TIMEOUT("用户登录超时"),

    OBJECT_IS_NULL("该对象为空"),

    CAPTCHA_SEND_OVERRUN("验证次数超限"),
    CAPTCHA_INVALID_ERROR("验证码非法错误"),
    CAPTCHA_SEND_ERROR("短信验证码发送失败"),

    // 分区创建
    MIDDLEWARE_ERROR("中间件未知错误"),
    REGION_NAME_REPEAT("分区名称重复"),
    REGION_CREATE_BUSY("分区创建正忙"),
    REGION_CREATE_FAIL("分区创建失败"),
    REGION_ENABLE_FAIL("分区启用失败"),
    REGION_DISABLE_FAIL("分区暂停失败"),
    REGION_COMPLETE_FAIL("分区停用失败"),
    REGION_UPDATE_FAIL("分区名称修改失败"),
    REGION_UPDATE_ACCESS_LIST("分区更新权限列表失败"),
    
    // 合约部署
    CONTRACT_CODE_READ_ERROR("合约代码读取错误"),
    CONTRACT_DEPLOY_ERROR("合约部署错误"),
    METHOD_BIND_ERROR("方法绑定错误"),

    /************OSS***************/
    BUCKET_DOES_NOT_EXIST("bucket不存在"),
    DOWNLOAD_OSS_FILE_FAIL("OSS文件下载失败"),
    UPLOAD_OSS_FILE_FAIL("OSS文件上传失败"),
    DOWNLOAD_OSS_FILE_VERIFY_FAIL("OSS文件下载校验失败"),
    DOWNLOAD_FILE_FAIL("文件下载失败"),
    FILE_NOT_EXIST("文件不存在"),
    FILE_WRITE_ERROR("文件写入错误"),
    FILE_TO_BASE64_ERROR("文件base64编码错误"),

    /************** db *************/
    DB_QUERY_NOT_EXIST("数据库查询无此数据"),
    DB_QUERY_IS_NOT_UNIQUE("数据库查询不唯一"),

    /************** http request *************/
    HTTP_STATUS_EXCEPTION("http 状态码异常"),
    HTTP_TIMEOUT_EXCEPTION("http 连接超时"),
    HTTP_METHOD_NOT_SUPPORTED_EXCEPTION("http 请求方法不支持"),
    FAILED_TO_CALL_INTERFACE("调用接口失败"),
    HTTP_CONN_EXCEPTION("http 连接异常"),
    HTTP_PROCESS_EXCEPTION("http 结果处理异常"),
    API_STATUS_EXCEPTION("api 状态码异常"),

    /************** from Code ***************/
    ACCOUNT_ID_NOT_FOUND("accountId 不存在"),

    PASSED_IS_EXISTS("重复审核"),
    MSG_STATUS_ERROR("站內信状态不正确"),

    MSG_SENT_FREQUENTLY("站内信发送过于频繁"),

    SYNC_EXCEPTION("sync系统连接异常"),

    CREDIT_CODE_EXIST("社会信用代码已注册"),
    CREDIT_CODE_NOT_EXIST("社会信用代码不存在"),

    PARTNER_NOT_EXIST("合作方不存在"),

    KEY_NOT_EXIST("账户不存在"),

    STATUS_ERROR("状态不正确"),

    TRANSACTION_STATE_ERROR("transaction状态异常"),

    APPLY_LAW_SERVER_ERROR("申请法律服务异常"),

    JUDICIAL_CHAIN_SERVER_ERROR("司法链服务异常");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
