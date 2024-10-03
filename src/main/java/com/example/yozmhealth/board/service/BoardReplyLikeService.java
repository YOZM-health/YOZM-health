package com.example.yozmhealth.board.service;

import com.example.yozmhealth.board.mapper.BoardReplyLikeMapper;
import com.example.yozmhealth.board.vo.dto.BoardReplyLikeDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class BoardReplyLikeService {

    private final BoardReplyLikeMapper boardReplyLikeMapper;

    public Long boardReplyLikeCount(Long replyNo2) {
        return boardReplyLikeMapper.boardLikeCount(replyNo2);
    }

    // 좋아요 토글 로직
    public BoardReplyLikeDto.Response toggleBoardReplyLike(BoardReplyLikeDto.Request request) {
        // 중복 여부 확인 (사용자가 이미 좋아요를 눌렀는지 확인)
        Long duplicateCount = boardReplyLikeMapper.boardReplyLikeDuplicate(request.getReplyNo2(), request.getUserNo2());
        boolean isDuplicated = (duplicateCount != null && duplicateCount > 0);
        log.info(isDuplicated);
        if (isDuplicated) {
            // 이미 좋아요를 눌렀다면 좋아요 삭제
            boardReplyLikeMapper.boardLikeMinus(request.getUserNo2(), request.getReplyNo2());
            return new BoardReplyLikeDto.Response(false, boardReplyLikeCount(request.getReplyNo2()));  // 좋아요 취소 상태 반환
        } else {
            // 좋아요가 없다면 좋아요 추가
            boardReplyLikeMapper.boardLikePlus(request.getUserNo2(), request.getReplyNo2());
            return new BoardReplyLikeDto.Response(true, boardReplyLikeCount(request.getReplyNo2()));  // 좋아요 추가 상태 반환
        }
    }
}
