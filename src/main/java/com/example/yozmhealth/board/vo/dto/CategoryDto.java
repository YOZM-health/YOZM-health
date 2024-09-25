package com.example.yozmhealth.board.vo.dto;

import lombok.*;

public class CategoryDto {

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long catCode;
        private String catName;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long catCode;//카테고리 코드
        private Long postCount;//카테고리별 게시글 갯수
        private String catName;//카테고리명
    }
}
