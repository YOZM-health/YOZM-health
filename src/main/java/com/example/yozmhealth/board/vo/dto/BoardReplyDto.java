package com.example.yozmhealth.board.vo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardReplyDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long replyNo;
        private Long boardNo2; // 게시글 번호
        private Long userNo; //회원 번호
        private String replyAuthor;
        @NotBlank(message = "댓글 내용을 작성해주세요.")
        private String replyContents;
        private Long parentReplyNo; // 대댓글 번호
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long replyNo;
        private String replyAuthor;
        private String replyContents;
        private Long parentReplyNo;
        private Long boardNo2;
        private Long userNo;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime replyCreatedTime;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime replyUpdatedTime;
        //대댓글목록
        List<Response> childReplies = new ArrayList<>();
    }
}
