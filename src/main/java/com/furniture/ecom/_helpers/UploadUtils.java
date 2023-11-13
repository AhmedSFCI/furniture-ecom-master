/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author HP
 */
public class UploadUtils {

    final String ROOT_PATH ;

    public UploadUtils(String rootPath) {
       ROOT_PATH = rootPath;
    }

    public void uploadFile(String type, MultipartFile file) throws IOException, Exception {
 //       String relativePath =  "https://drive.google.com/drive/folders/19p6M-w1sCzZwAroYczwPBkbvin5VaLwd?usp=sharing"+"/resources/"+ type + "/";
       String relativePath = "fastdo.co/My_Images1/";
        String filePath = "";
        try {
            String fileNm =  System.currentTimeMillis()+file.getOriginalFilename();
            filePath = relativePath + fileNm;
            Path path = Paths.get(filePath);
             File convFile = new File(file.getOriginalFilename());
            convFile.toURI().toURL();
                    
        //    file.transferTo(new File(filePath));
            System.out.println("filePath : " + filePath);
        } catch (IOException ex) {
            throw ex;
        } catch (IllegalStateException ex) {
            throw ex;
        }

    }
}
