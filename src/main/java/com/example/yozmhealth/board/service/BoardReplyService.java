package com.example.yozmhealth.board.service;

import com.example.yozmhealth.board.mapper.BoardReplyMapper;
import com.example.yozmhealth.board.vo.dto.BoardReplyDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BoardReplyService {

    private final BoardReplyMapper boardReplyMapper;

    // 댓글 & 대댓글 목록
    public List<BoardReplyDto.Response> boardReplyList (Long boardNo) {
        List<BoardReplyDto.Response> boardReplies = boardReplyMapper.findAllByBoard(boardNo);

        if(boardReplies!=null){
            for (BoardReplyDto.Response parentReply : boardReplies) {
                List<BoardReplyDto.Response> childReplies = boardReplyMapper.findAllByParentReply(parentReply.getReplyNo());
                parentReply.setChildReplies(childReplies); // 대댓글을 부모 댓글에 추가
            }
        }
        return boardReplies;
    }

    public Long insertBoardReply (BoardReplyDto.Request request) {
        return boardReplyMapper.insertReply(request);
    }

    public Long updateBoardReply (BoardReplyDto.Request request) {
        return boardReplyMapper.updateReply(request);
    }

    public void deleteBoardReply (Long replyNo) {
        boardReplyMapper.deleteReply(replyNo);
    }
}
