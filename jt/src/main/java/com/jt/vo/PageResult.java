package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {   //封装VO对象
    private String query;
    private Integer pageNum;
    private Integer pageSize;
    private Long    total;
    private Object  rows;  //分页后的结果
}
