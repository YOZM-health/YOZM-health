package com.example.yozmhealth.board.controller;

import com.example.yozmhealth.board.mapper.BoardAttachMapper;
import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileController {

    @Value("${server.file.upload}")
    private String uploadDirectory;

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
            //해당 부분은 리팩토링이 필요
            String uploadPath = uploadDirectory + "summernote/";
            log.info(uploadPath);

            // 디렉토리가 존재하지 않으면 생성
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            log.info(directory);
            String originalFileName = file.getOriginalFilename();
            log.info(originalFileName);
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            String uuidFileName = UUID.randomUUID() + fileExtension;

            file.transferTo(new File(uploadPath, uuidFileName));

            // 이미지 URL 생성
            String imageUrl = "/attachFolder/summernote/" + uuidFileName;
            log.info(imageUrl);
            Map<String, String> response = new HashMap<>();
            response.put("url", imageUrl);
            log.info(response.get("url"));
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
            String uploadDirectory = "C://attachFolder//summernote";
            Path filePath = Paths.get(uploadDirectory, fileName);
            log.info(uploadDirectory);
            log.info(filePath);
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
