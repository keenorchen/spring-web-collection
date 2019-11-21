package com.keenor.resttempalate.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public  class BookReq {

    private String title;
    private String author;
    private String content;

}

