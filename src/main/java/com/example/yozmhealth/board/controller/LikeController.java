package com.example.yozmhealth.board.controller;

import com.example.yozmhealth.board.service.BoardReplyLikeService;
import com.example.yozmhealth.board.vo.dto.BoardReplyLikeDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/like")
@AllArgsConstructor
public class LikeController {

    private final BoardReplyLikeService boardReplyLikeService;

    // 좋아요 토글 (추가/삭제)
    @PostMapping("/toggle")
    public ResponseEntity<BoardReplyLikeDto.Response> replyLikeToggle(@RequestBody BoardReplyLikeDto.Request replyLikeDto) {
        BoardReplyLikeDto.Response responseDto = boardReplyLikeService.toggleBoardReplyLike(replyLikeDto);
        return ResponseEntity.ok(responseDto);
    }

    // 좋아요 갯수 조회
    @GetMapping("/count/{replyNo}")
    public ResponseEntity<Long> replyLikeCount(@PathVariable("replyNo") Long replyNo) {
        Long likeCount = boardReplyLikeService.boardReplyLikeCount(replyNo);
        return ResponseEntity.ok(likeCount);
    }
}
