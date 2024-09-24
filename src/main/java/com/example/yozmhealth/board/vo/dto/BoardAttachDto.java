package com.example.yozmhealth.board.vo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

public class BoardAttachDto {

    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long boardNo2;
        private String originName;
        private String storeName;
        private Long fileSize;
        private String filePath;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long attachNo;
        private Long boardNo2;
        private String originName;
        private String storeName;
        private Long fileSize;
        private String filePath;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime fileCreated;
    }
}
