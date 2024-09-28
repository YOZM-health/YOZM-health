package com.example.yozmhealth.board.service;

import com.example.yozmhealth.board.mapper.BoardAttachMapper;
import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BoardAttachService {

    private final BoardAttachMapper attachMapper;

    public List<BoardAttachDto.Response> attachList(Long boardNo) {
        return attachMapper.attachByBoardList(boardNo);
    }

    public BoardAttachDto.Response findByOriginFileName(String originFileName) {
        return attachMapper.getFileByOriginName(originFileName);
    }

    public void insertAttach(BoardAttachDto.Response request){
        attachMapper.insertAttach(request);
    }

    public void deleteAttach(Long boardNo){
        attachMapper.deleteAttach(boardNo);
    }
}
