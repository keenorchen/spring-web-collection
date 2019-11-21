package com.keenor.resttempalate.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.keenor.resttempalate.exception.ErrorCode;

import java.io.Serializable;

/**
 * Author:      chenliuchun
 * Date:        2018/6/5
 * Description: 通用接口返回参数类
 * Modification History:
 * Date       Author       Version     Description
 * -----------------------------------------------------
 *
 * @author zccp
 */

//序列化json的时候, 如果是null的对象, key也会消失
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private String code;
    private String msg;
    private T detail;

    public Result() {
    }

    public Result(String code) {
        this.code = code;
    }

    private Result(String code, T detail) {
        this.code = code;
        this.detail = detail;
    }

    private Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(String code, String msg, T detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    @JsonIgnore //使之不在json序列化结果当中
    public boolean isSuccess() {
        return ErrorCode.SUCCESS.name().equals(this.code);
    }

    public String getCode() {
        return code;
    }

    public T getDetail() {
        return detail;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public static <T> Result<T> ofSuccess() {
        return new Result<T>(ErrorCode.SUCCESS.name());
    }

    public static <T> Result<T> ofSuccessMsg(String msg) {
        return new Result<T>(ErrorCode.SUCCESS.name(), msg);
    }

    public static <T> Result<T> ofSuccess(T data) {
        return new Result<T>(ErrorCode.SUCCESS.name(), data);
    }

    public static <T> Result<T> ofSuccess(String msg, T data) {
        return new Result<T>(ErrorCode.SUCCESS.name(), msg, data);
    }

    public static <T> Result<T> ofError() {
        return new Result<T>(ErrorCode.ERROR.name(), ErrorCode.ERROR.getMessage());
    }

    public static <T> Result<T> ofError(String errorMessage) {
        return new Result<T>(ErrorCode.ERROR.name(), errorMessage);
    }

    public static <T> Result<T> ofError(String errorCode, String errorMessage) {
        return new Result<T>(errorCode, errorMessage);
    }

    public static <T> Result<T> ofError(ErrorCode errorCode) {
        return new Result<T>(errorCode.name(), errorCode.getMessage());
    }

    public static <T> Result<T> ofError(ErrorCode errorCode, String errorMessage) {
        return new Result<T>(errorCode.name(), errorMessage);
    }


    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg=" + msg +
                ", detail=" + detail +
                '}';
    }

}




