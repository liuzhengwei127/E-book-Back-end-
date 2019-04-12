package cn.liuzhengwei.ebook.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@RestController
public class SrcController {
    //图片写入路径
    //private String filePath = "C:\\Users\\75667\\vueProject\\E-book\\public\\images\\";
    private String pathRoot = "/var/www/html/ebook/images/";
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {

        //传入图片文件名
        String fileName = file.getOriginalFilename();

        //处理文件，将文件写入指定位置
        try {
            byte[] file_b = file.getBytes();
            File targetFile = new File(pathRoot);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(pathRoot+fileName);
            out.write(file_b);
            out.flush();
            out.close();
        } catch (Exception e) {
            return e.getMessage();
        }

        // 返回图片的存放路径
        return fileName;
    }

    @RequestMapping(value="/delete", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@RequestParam("filename") String filename) {
        String result;
        String path = pathRoot+filename;
        File file = new File(path);

        //删除图片
        if (file.exists()) {
            if (file.delete()) {
                result =  "删除成功";
            } else {
                result =  "删除失败";
            }
        } else {
            result = "文件不存在";
        }

        //返回字符串形式结果
        return result;
    }
}