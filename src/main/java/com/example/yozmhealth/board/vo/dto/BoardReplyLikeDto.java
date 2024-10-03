package com.example.yozmhealth.board.vo.dto;

import lombok.*;

public class BoardReplyLikeDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long replyNo2;
        private Long userNo2;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long replyNo2;
        private Long userNo2;
        private Long likeCount;
        private boolean isLiked;//좋아요 상태

        public Response(boolean isLiked, Long likeCount){
            this.isLiked = isLiked;
            this.likeCount = likeCount;
        }
    }
}
