package com.example.yozmhealth.config.Upload;

import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {

    @Value("${server.file.upload}")
    private String filePaths;

    public List<BoardAttachDto.Response> fileParse( List<MultipartFile>files) throws IOException {
        List<BoardAttachDto.Response> attachments = new ArrayList<>();
        //파일 첨부
        if(!files.isEmpty()&& files != null) {
            for(MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String storedFilename = UUID.randomUUID().toString() + "_" + originalFilename;
                String filePath = filePaths;

                // 파일 저장
                File dest = new File(filePath);
                file.transferTo(dest);

                //첨부파일 디비 저장
                BoardAttachDto.Response attachDto = BoardAttachDto
                        .Response
                        .builder()
                        .filePath(filePath)
                        .fileSize(file.getSize())
                        .originName(originalFilename)
                        .storeName(storedFilename)
                        .fileCreated(LocalDateTime.now())
                        .build();

                attachments.add(attachDto);
            }
        }
        return attachments;
    }
}
