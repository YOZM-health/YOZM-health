package com.example.yozmhealth.board.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardReplyVO {

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
}
