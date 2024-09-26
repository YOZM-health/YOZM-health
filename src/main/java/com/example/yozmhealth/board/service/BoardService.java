package com.example.yozmhealth.board.service;

import com.example.yozmhealth.board.mapper.BoardAttachMapper;
import com.example.yozmhealth.board.mapper.BoardMapper;
import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import com.example.yozmhealth.board.vo.dto.BoardDto;
import com.example.yozmhealth.config.Upload.FileUtils;
import com.example.yozmhealth.config.page.Criteria;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    private final FileUtils fileUtils;

    private final BoardAttachMapper boardAttachMapper;

    @Transactional(readOnly = true)
    public List<BoardDto.BoardResponse> findAll (Criteria criteria) {
        return Optional
                .ofNullable(boardMapper.findAll(criteria))
                .filter(boardResponseList -> !boardResponseList.isEmpty())
                .orElseThrow(() -> new RuntimeException("게시글 목록이 없습니다."));
    }

    @Transactional(readOnly = true)
    public BoardDto.BoardResponse findByBoardId (Long boardId) {
        return boardMapper.findByBoardId(boardId)
                .orElseThrow(()-> new RuntimeException("게시글이 존재하지 않습니다."));
    }

    //리팩토링
    public Long insertBoard(BoardDto.BoardRequest request) throws IOException {
        Long createResult = boardMapper.insertBoard(request);
        //첨부파일이 있는 경우
        List<BoardAttachDto.Response> list = fileUtils.fileParse(request.getAttachLists());

        if(list != null) {
            for(BoardAttachDto.Response dto : list) {
                dto.setBoardNo2(request.getBoardNo());
                boardAttachMapper.insertAttach(dto);
            }
        }
        return createResult;
    }

    //리팩토링
    public Long updateBoard(BoardDto.BoardRequest request) throws IOException {
        Long updateResult = boardMapper.updateBoard(request);
        List<BoardAttachDto.Response> list = boardAttachMapper.attachByBoardList(request.getBoardNo());
        
        //파일이 있는 경우
        if(!list.isEmpty()) {
            //저장위치 삭제
            for(int i=0; i< list.size(); i++) {
                String filePath = list.get(i).getFilePath();
                File file = new File(filePath);
                if(file.exists()){
                    file.delete();
                }
                //디비에서 삭제
                boardAttachMapper.deleteAttach(request.getBoardNo());
            }
            //파일  업로드
            list = fileUtils.fileParse(request.getAttachLists());
            //파일 디비 저장
            for(BoardAttachDto.Response dto : list) {
                dto.setBoardNo2(request.getBoardNo());
                boardAttachMapper.insertAttach(dto);
            }
        }
        return updateResult;
    }
    //리팩토링
    public void deleteBoard(Long boardNo) {
        //게시글 삭제
        boardMapper.deleteBoard(boardNo);
        //첨부파일목록
        List<BoardAttachDto.Response> attachList = boardAttachMapper.attachByBoardList(boardNo);

        if(!attachList.isEmpty()) {
            for (int i = 0; i< attachList.size(); i++) {
                String filePath = attachList.get(i).getFilePath();
                File file = new File(filePath);
                //경로가 있다면 삭제
                if(file.exists()) {
                    file.delete();
                }
                //디비에 있는 첨부파일 삭제
                boardAttachMapper.deleteAttach(boardNo);
            }
        }
    }

    public List<BoardDto.BoardResponse> boardNextPrevious (Long boardNo) {
        return boardMapper.boardNextPrevious(boardNo);
    }
    
    public Long boardListTotalCount () {
        return boardMapper.boardTotalCount();
    }

    public void readCountUp(Long boardNo) {
        boardMapper.readCountUp(boardNo);
    }
}
