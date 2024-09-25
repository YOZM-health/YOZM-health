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
public class BoardVO {

    private Long boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardAuthor;
    private char boardDelFl; // 게시글 삭제 유무
    private Long readCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDate;
}
