package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO {

    private String virtualPath; //图片虚拟路径 动态的路径
    private String urlPath;  //图片回显的URL地址
    private String fileName; //文件上传后的文件名称
}
