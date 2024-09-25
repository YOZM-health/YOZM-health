package com.example.yozmhealth.board.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardAttachVO {

    private Long attachNo;
    private Long boardNo2;
    private String originName;
    private String storeName;
    private Long fileSize;
    private String filePath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fileCreated;
}
