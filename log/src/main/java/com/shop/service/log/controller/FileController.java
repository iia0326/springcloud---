package com.shop.service.log.controller;

import com.alibaba.fastjson.JSONObject;
import com.shop.service.log.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
public class FileController {

    @Value("${file.path}")
    private String filePath;

    @PostMapping("/file/upload")
    public Result fileTest(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value="folder",defaultValue = "face") String folder
            ){
        if(file.isEmpty()){
            return Result.end(500,"","文件不可以为空");
        }
        String fileName = file.getOriginalFilename();
        String ex = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString();
        String newFilePath = this.filePath+newFileName+ex;
        File f = new File(newFilePath);
        try {
            file.transferTo(f);
        }catch (Exception e){
            return Result.end(500,e.getMessage(),"发生异常");
        }
        JSONObject j = new JSONObject();
        j.put("url","/public/"+newFileName+ex);
        j.put("fileName",newFileName+ex);
        return Result.end(200,j,"");
    }

    @GetMapping("/file/delete")
    public Result deleteFile(@RequestParam(value = "path") String path){
        String fullPath = filePath+path.substring(path.lastIndexOf("/")+1);
        File f = new File(fullPath);
        if(f.exists()){
            f.delete();
        }
        return Result.end(200,"","删除成功");
    }
}
