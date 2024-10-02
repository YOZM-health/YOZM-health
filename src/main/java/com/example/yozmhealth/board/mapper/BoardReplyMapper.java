package com.example.yozmhealth.board.mapper;

import com.example.yozmhealth.board.vo.dto.BoardReplyDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BoardReplyMapper {
    //댓글 목록
    List<BoardReplyDto.Response> findAllByBoard (Long boardNo);
    //대댓글 목록
    List<BoardReplyDto.Response> findAllByParentReply(Long parentReplyNo);

    BoardReplyDto.Response findByReplyId(Long replyNo);

    Long insertReply (BoardReplyDto.Request dto);

    Long updateReply (BoardReplyDto.Request dto);

    void deleteReply (Long replyNo);
}
