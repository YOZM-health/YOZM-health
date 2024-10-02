package com.example.yozmhealth.board.vo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardDto {

    @Getter
    @Setter
    @ToString
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class BoardRequest {
        private Long boardNo;
        private String boardTitle;
        private String boardContent;
        private String boardAuthor;
        private Long boardCode;
        private Long userNo;
        private char boardStatus;
        //첨부파일
        private List<MultipartFile> attachLists = new ArrayList<>();
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createDate;
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
        private Long boardCode; // 게시글 카테고리 코드
        private Long readCount;
        private Long userNo; //회원 번호
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime createDate;
    }
}
