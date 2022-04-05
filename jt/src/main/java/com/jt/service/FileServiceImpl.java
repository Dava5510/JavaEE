package com.jt.service;

import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@PropertySource("classpath:/image.properties")
public class FileServiceImpl implements FileService{

    @Value("${image.localDirPath}")
    private String localDirPath;    // = "E:/project3/images";
    @Value("${image.preUrl}")
    private String preUrl;          // = "http://image.jt.com";

    //1.校验图片类型   xxx.jpg   校验后缀是否为jpg
    @Override
    public ImageVO upload(MultipartFile file) {

        //1.1 获取文件名称  abc.jpg
        String fileName = file.getOriginalFilename();
        //1.2 全部转化为小写字母
        fileName = fileName.toLowerCase();
        //1.3正则校验是否为图片类型
        if(!fileName.matches("^.+\\.(jpg|png|gif)$")){
            //图片类型 不匹配  程序应该终止
            return null;
        }

        //2.校验是否为恶意程序 怎么判断就是一张图 高度和宽度
        //2.1 通过图片对象进行处理
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            if(height == 0 || width == 0){
                return null;
            }
            //3.将图片分目录存储 yyyy/MM/dd
            String dateDir = new SimpleDateFormat("/yyyy/MM/dd/")
                                .format(new Date());
            String dateDirPath = localDirPath + dateDir;
            File dirFile = new File(dateDirPath);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }

            //4.防止文件重名  动态生成UUID.类型
            //4.1 动态生成UUID
            String uuid = UUID.randomUUID().toString()
                              .replace("-","");
            //4.2 获取图片类型        abc.jpg    .jpg
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            // uuid.jpg
            String newFileName = uuid + fileType;

            //5.实现文件上传 1.准备全文件路径  2. 封装对象实现上传
            String path = dateDirPath + newFileName;
            file.transferTo(new File(path));

            //6. 实现ImageVO数据的返回
            //6.1 准备虚拟路径 /2021/11/11/uuid.jpg
            String virtualPath = dateDir + newFileName;
            //6.2 准备URL地址  域名前缀 + 虚拟路径
            String url =  preUrl + virtualPath;
            System.out.println(url);
            return new ImageVO(virtualPath,url,newFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteFile(String virtualPath) {
        String filePath = localDirPath + virtualPath;
        File file = new File(filePath);
        if(file.exists()){ //如果文件存在,则删除数据
            file.delete();
        }
    }
}
