package com.example.yozmhealth.board.service;

import com.example.yozmhealth.board.mapper.BoardReplyLikeMapper;
import com.example.yozmhealth.board.vo.dto.BoardReplyDto;
import com.example.yozmhealth.board.vo.dto.BoardReplyLikeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class BoardReplyLikeService {

    private final BoardReplyLikeMapper boardReplyLikeMapper;

    public Long boardReplyLikeCount(Long replyNo2) {
        return boardReplyLikeMapper.boardLikeCount(replyNo2);
    }

    public void insertBoardReplyLike(BoardReplyLikeDto.Request request){
        boardReplyLikeMapper.boardLikePlus(request.getUserNo2(),request.getReplyNo2());
    }

    public void deleteBoardReplyLike(BoardReplyDto.Request request) {
        boardReplyLikeMapper.boardLikeMinus(request.getBoardNo2(),request.getReplyNo());
    }
}
