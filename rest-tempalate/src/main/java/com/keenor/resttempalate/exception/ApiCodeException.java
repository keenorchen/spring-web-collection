package com.keenor.resttempalate.exception;

public class ApiCodeException extends Exception {

    public ApiCodeException(String code) {
        super(code);
        this.code = code;
    }

    public ApiCodeException(String code, String msg) {
        super(code + " " + msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 业务错误码
     */
    String code;
    String msg;

}
