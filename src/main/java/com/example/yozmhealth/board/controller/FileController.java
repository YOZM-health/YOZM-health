package com.example.yozmhealth.board.controller;

import com.example.yozmhealth.board.mapper.BoardAttachMapper;
import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class FileController {

    private final BoardAttachMapper attachMapper;

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
}
