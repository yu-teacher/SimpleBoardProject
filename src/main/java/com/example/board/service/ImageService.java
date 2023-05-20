package com.example.board.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
public class ImageService {

    @Value("${path.rootMyCom.window}")
    private String winPath;
    @Value("${path.rootMyCom.linux}")
    private String linuxPath;
    @Value("${path.rootMyCom.mac}")
    private String macPath;

    public String temp(MultipartFile imageFile) throws IOException {
        String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());

        // 확장자 추출
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = System.currentTimeMillis()+"_"+ UUID.randomUUID().toString()+fileExtension;
        String os = System.getProperty("os.name").toLowerCase();

        String path = "";
        if(os.contains("win")) path = winPath;
        else if(os.contains("lin")) path = linuxPath;
        else if(os.contains("mac")) path = macPath;

        File folder = new File(path);
        if (!folder.exists()) folder.mkdirs();

        File file = new File(path, filename);
        imageFile.transferTo(file);
        return filename;
    }

    public Resource readImage(String imageName) throws IOException {
        String imagePath = "";
        String os = System.getProperty("os.name").toLowerCase();
        // 운영체체 별로 경로 정함
        if(os.contains("win")) imagePath = winPath+imageName;
        else if(os.contains("lin")) imagePath = linuxPath+imageName;
        else if(os.contains("mac")) imagePath = macPath+imageName;

        // 이미지 파일을 바이트 배열로 읽어옴
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

        // 바이트 배열을 Resource 객체로 변환
        return new ByteArrayResource(imageBytes);
    }

}
