package com.example.board.controller;

import com.example.board.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/image/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        return ResponseEntity.ok()
                // 응답에 Resource와 MIME 타입을 포함하여 반환
                .contentType(MediaType.IMAGE_JPEG)
                // 이미지 파일의 MIME 타입을 설정 (예: 이미지 파일의 경우 "image/jpeg"로 설정)
                .body(imageService.readImage(imageName));
    }

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("업로드할 이미지를 선택해주세요.");
        return ResponseEntity.ok(imageService.temp(imageFile));
    }
}
