package com.example.yozmhealth.board.controller;

import com.example.yozmhealth.board.service.BoardReplyService;
import com.example.yozmhealth.board.vo.dto.BoardReplyDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {

    private final BoardReplyService boardReplyService;

    @GetMapping("/{boardNo}")
    public ResponseEntity<List<BoardReplyDto.Response>>replyList (@PathVariable("boardNo") Long boardNo) {
        List<BoardReplyDto.Response> replyList = boardReplyService.boardReplyList(boardNo);
        return new ResponseEntity<>(replyList, HttpStatus.OK);
    }

    @PostMapping("/{boardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>insertBoardReply(@PathVariable("boardId")Long boardNo,@Valid @RequestBody BoardReplyDto.Request request) throws Exception{
        request.setBoardNo2(boardNo);
        Long insertResult = boardReplyService.insertBoardReply(request);
        return ResponseEntity.ok(insertResult);
    }

    @PutMapping("/{replyNo}")
    public ResponseEntity<?>updateBoardReply(@PathVariable("replyNo")Long replyNo,@Valid @RequestBody BoardReplyDto.Request request) throws Exception{
        request.setReplyNo(replyNo);
        Long updateResult = boardReplyService.updateBoardReply(request);
        return ResponseEntity.ok(updateResult);
    }

    @DeleteMapping("/{replyNo}")
    public ResponseEntity<String>deleteBoardReply(@PathVariable("replyNo")Long replyNo) {
        boardReplyService.deleteBoardReply(replyNo);
        return new ResponseEntity<>("Delete O.k",HttpStatus.OK);
    }
}
