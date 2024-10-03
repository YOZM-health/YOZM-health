package com.example.yozmhealth.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardReplyLikeMapper {
    //댓글 좋아요 갯수
    Long boardLikeCount(@Param("replyNo2") Long replyNo2);
    //댓글 좋아요 중북
    Long boardReplyLikeDuplicate(@Param("replyNo2")Long replyNo2,@Param("userNo2")Long userNo2);
    //좋아요 증가
    void boardLikePlus(@Param("userNo2")Long userNo,@Param("replyNo2")Long replyNo);
    //좋아요 감소
    void boardLikeMinus(@Param("userNo2")Long userNo,@Param("replyNo2")Long replyNo);
}
