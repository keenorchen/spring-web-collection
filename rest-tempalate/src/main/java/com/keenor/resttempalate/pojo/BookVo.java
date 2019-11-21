package com.keenor.resttempalate.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Data
@Accessors(chain = true)
public  class BookVo {

    private Long id;
    private String title;
    private String author;
    private String content;

}

