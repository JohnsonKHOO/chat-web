package com.a2208.chat.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
    public static String uploadFile(MultipartFile file) throws IOException {
        if(file !=null){
            String avatar = file.getOriginalFilename();
            InputStream in = file.getInputStream();

            File mkdir = new File("src/resources/static/img");
            if(!mkdir.exists()){
                mkdir.mkdirs();
            }
            FileOutputStream os = new FileOutputStream(mkdir.getPath()+"\\"+avatar);
            int len = 0;
            while((len = in.read()) != -1){
                os.write(len);
            }
            os.flush();
            in.close();
            os.close();

            return avatar;
        }
        return "";
    }
}