package com.tetx.core.resultResponse.restful;

import com.tetx.core.resultResponse.ResultCode;
//import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//@AllArgsConstructor
public enum RestResultCode implements ResultCode {

    SUCCESS(200000, "成功"),
    UNKNOWN_EXCEPTION(500000, "系统异常"),
    INVALID_PARAMS(500003, "参数异常: %s"),
    REQUEST_METHOD_NOT_SUPPORT(500004, "请求方式错误: %s"),
    INVALID_RESULT(500005, "无效的结果"),
    REQUEST_TIMEOUT(501000, "请求超时"),
    USERNAME_OR_PASSWORD_ERROR(400001, "用户名或密码错误"),
    AUTHORIZATION_EXPIRED(400002, "授权已过期，请重新登陆"),
    NO_PERMISSION_EXCEPTION(400003, "没有该操作的权限"),
    ACCOUNT_LOCKED_EXCEPTION(400004, "账户已被锁定"),
    OTHER_AUTHORIZATION_EXCEPTION(400005, "权限校验不通过"),
    METHOD_ARGUMENT_NOT_VALID(400006, "方法的参数无效"),
    BAD_REQUEST(400000, "请求无效，服务器无法理解该请求"),
    NOT_FOUND(404000, "没有找到对应的请求"),
    ;

    private int code;

    private String msg;

    RestResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private static final Map<Integer, RestResultCode> CODE_LOOK_UP = new HashMap<>(100);

    static {
        for (RestResultCode at : RestResultCode.values()) {
            CODE_LOOK_UP.put(at.getCode(), at);
        }
    }

    public static RestResultCode findByCode(int code) {
        return CODE_LOOK_UP.get(code);
    }

    public static String getMsgByCode(int code) {
        return Objects.isNull(CODE_LOOK_UP.get(code)) ? null : CODE_LOOK_UP.get(code).getMsg();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
