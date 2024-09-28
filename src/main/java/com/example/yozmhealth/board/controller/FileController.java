package com.example.yozmhealth.board.controller;

import com.example.yozmhealth.board.mapper.BoardAttachMapper;
import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final WebApplicationContext context;

    private final BoardAttachMapper attachMapper;

    //첨부파일 다운로드
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> fileDownLoad (@PathVariable("filename")String originFileName) throws IOException {
        BoardAttachDto.Response getFiles = attachMapper.getFileByOriginName(originFileName);
        Path path = Paths.get(getFiles.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=\"" + URLEncoder.encode(getFiles.getOriginName(), StandardCharsets.UTF_8) + "\"")
                .body(resource);
    }

    //썸머노트 이미지 업로드
    @PostMapping("/image-upload")
    public ResponseEntity<?> imageUpload(@RequestParam("file") MultipartFile file) throws IllegalStateException {
        try {
            String uploadDirectory = context.getServletContext().getRealPath("/resources/images/upload");

            String originalFileName = file.getOriginalFilename();

            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            String uuidFileName = UUID.randomUUID() + fileExtension;

            file.transferTo(new File(uploadDirectory, uuidFileName));

            System.out.println(uploadDirectory);
            // 이미지 URL 생성
            String imageUrl = "/resources/images/upload/" + uuidFileName;

            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("이미지 업로드 실패");
        }
    }
    
    //에디터 이미지 삭제
    @PostMapping("/deleteSummernoteImageFile")
    public ResponseEntity<?> deleteImage(@RequestParam("file") String fileName) {
        try {
            // 서버에 저장된 이미지 경로
            String uploadDirectory = context.getResource("classpath:resources/images/upload").getFile().getAbsolutePath();
            Path filePath = Paths.get(uploadDirectory, fileName);

            // 파일 삭제
            File file = filePath.toFile();
            if (file.exists()) {
                if (file.delete()) {
                    return ResponseEntity.ok("이미지 삭제 성공");
                } else {
                    return ResponseEntity.status(500).body("이미지 삭제 실패");
                }
            } else {
                return ResponseEntity.status(404).body("이미지가 존재하지 않습니다");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("이미지 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}
