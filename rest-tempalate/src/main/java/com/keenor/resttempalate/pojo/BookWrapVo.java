package com.keenor.resttempalate.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.keenor.resttempalate.ErrorCode;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class BookWrapVo<T> implements Serializable {

    private String code;
    private String msg;
    private List<T> detail;

}




