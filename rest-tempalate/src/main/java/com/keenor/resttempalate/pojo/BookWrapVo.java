package com.keenor.resttempalate.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class BookWrapVo<T> implements Serializable {

    private String code;
    private String msg;
    private List<T> detail;

}




