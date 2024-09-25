package com.example.yozmhealth.board.vo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardRequest {
        private Long boardNo;
        private String boardTitle;
        private String boardContent;
        private String boardAuthor;
        private char boardStatus;
        //첨부파일
        private List<MultipartFile> attachLists;
    }

    @Getter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoardResponse {
        private Long boardNo;
        private String boardTitle;
        private String boardContent;
        private String boardAuthor;
        private char boardDelFl; // 게시글 삭제 유무
        private char boardStatus;
        private Long readCount;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createDate;
    }
}
