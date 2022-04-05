package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 业务说明: 实现图片上传
     * URL: http://localhost:8091/file/upload
     * 类型: post
     * 参数: MultipartFile file 字节信息
     * 返回值: SysResult.success()
     * 问题思考:
     *      1.完成图片类型校验 jpg|png|gif....
     *      2.防止恶意程序    a.exe.jpg
     *      3.将图片分目录存储
     *             3.1.按照类型分   理论可以但是得多分配几个
     *             3.2.按照时间划分. yyyy/MM/dd
     *      4.自定义文件名称. 利用UUID充当图片名称.
     */
    @PostMapping("/upload")
    public SysResult upload(MultipartFile file) throws IOException {

        ImageVO imageVO = fileService.upload(file);
        if(imageVO == null){
            return SysResult.fail();
        }
        return SysResult.success(imageVO);
    }

    /**
     * 业务说明: 文件删除操作
     * URL地址:   http://localhost:8091/file/deleteFile
     * 请求类型:   delete
     * 参数:      virtualPath 虚拟路径
     * 返回值:    SysResult对象
     */
    @DeleteMapping("/deleteFile")
    public SysResult deleteFile(String virtualPath){

        fileService.deleteFile(virtualPath);
        return SysResult.success();
    }






    /**
     * 业务说明: 实现图片上传
     * URL: http://localhost:8091/file/upload
     * 类型: post
     * 参数:  file 字节信息
     * 返回值: SysResult.success()
     * 扩展:
     *      一般情况下:
     *          一般前端向后端服务器发送字节信息.由外到内实现数据传输.
     *      采用输入流信息. InputStream file
     *          使用字节流的弊端: 1.必须手动关闭, 2.代码操作繁琐
     *          底层代码的实现.
     *       SpringMVC高级API  MultipartFile 专门处理IO流操作
     *  文件上传步骤:
     *        1.获取文件名称
     *        2.准备文件上传的目录
     *        3.判断目录是否存在  存在目录: 实现上传  没有目录:创建目录
     *        4.利用工具API方法,实现文件上传.
     *  注意事项: MultipartFile 默认支持1M的数据
     */
     /*@PostMapping("/upload")
     public SysResult upload(MultipartFile file) throws IOException {
         //1.获取文件名称
         String fileName = file.getOriginalFilename();
         //2.准备磁盘地址
         String dirPath = "E:/project3/images/";
         //3.将这个文件目录 封装为File对象
         File dirFile = new File(dirPath);
         //4.判断对象是否存在
         if(!dirFile.exists()){
            //如果文件目录不存在,则创建目录
             dirFile.mkdirs(); //表示多级目录上传.
         }
         //5.封装文件全路径 E:xxx/xxx/a.jpg
         String path = dirPath + fileName;
         File allFile = new File(path);
         //6.实现文件上传 将IO流按照指定的对象格式进行输出.
         file.transferTo(allFile);
         return SysResult.success();
     }*/
}
