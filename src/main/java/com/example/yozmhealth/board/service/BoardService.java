package com.example.yozmhealth.board.service;

import com.example.yozmhealth.board.mapper.BoardMapper;
import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import com.example.yozmhealth.board.vo.dto.BoardDto;
import com.example.yozmhealth.config.Upload.FileUtils;
import com.example.yozmhealth.config.page.Criteria;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    private final FileUtils fileUtils;

    private final BoardAttachService boardAttachService;
    
    //게시글 조회
    @Transactional(readOnly = true)
    public List<BoardDto.BoardResponse> findAll (Criteria criteria) {
        return Optional
                .ofNullable(boardMapper.findAll(criteria))
                .filter(boardResponseList -> !boardResponseList.isEmpty())
                .orElseThrow(() -> new RuntimeException("게시글 목록이 없습니다."));
    }
    
    //게시글 단일 조회
    @Transactional(readOnly = true)
    public BoardDto.BoardResponse findByBoardId (Long boardId) {
        return boardMapper.findByBoardId(boardId)
                .orElseThrow(()-> new RuntimeException("게시글이 존재하지 않습니다."));
    }

    //게시글 작성
    public Long insertBoard(BoardDto.BoardRequest request) throws IOException {
        Long createResult = boardMapper.insertBoard(request);
        //첨부 파일이 없는 경우
        if(request.getAttachLists().isEmpty()||request.getAttachLists() == null){
            return createResult;
        }
        //첨부파일이 있는 경우
        List<BoardAttachDto.Response> list = fileUtils.fileParse(request.getAttachLists());

        if(list != null) {
            list.forEach(dto ->{
                dto.setBoardNo2(request.getBoardNo());
                boardAttachService.insertAttach(dto);
            });
        }
        return createResult;
    }

    //게시글 수정
    public Long updateBoard(BoardDto.BoardRequest request) throws IOException {
        List<BoardAttachDto.Response> list;
        Long updateResult = boardMapper.updateBoard(request);

        //첨부파일이 없는 경우 게시글 수정기능
        if(request.getAttachLists().isEmpty()||request.getAttachLists() == null){
            return updateResult;
        }
        //첨부 파일 목록
        list = boardAttachService.attachList(request.getBoardNo());
        
        //파일이 있는 경우
        if(!list.isEmpty()) {
            //저장된 이미지 삭제
            list.stream()
                    .map(BoardAttachDto.Response::getFilePath)
                    .map(File::new)
                    .filter(File::exists)
                    .forEach(File::delete);
            boardAttachService.deleteAttach(request.getBoardNo());
        }

        if(!request.getAttachLists().isEmpty() && request.getAttachLists() != null) {
            //파일  업로드
            list = fileUtils.fileParse(request.getAttachLists());
            //파일 디비 저장
            list.forEach(dto -> {
                dto.setBoardNo2(request.getBoardNo());
                boardAttachService.insertAttach(dto);
            });
        }
        return updateResult;
    }
    
    //게시글 삭제(삭제 표시만 바꾸기 -> 첨부 파일 부분도 바꾸기.)
    public void deleteBoard(Long boardNo) {
        //게시글 삭제
        boardMapper.deleteBoard(boardNo);
        //첨부파일목록
        List<BoardAttachDto.Response> attachList = boardAttachService.attachList(boardNo);

        if(!attachList.isEmpty()) {
            attachList
                    .stream()
                    .map(BoardAttachDto.Response::getFilePath)
                    .map(File::new)
                    .filter(File::exists)
                    .forEach(File::delete);

            boardAttachService.deleteAttach(boardNo);
        }
    }

    //게시글 일괄삭제
    public void deleteBoardBatch(List<Long>boardNos){
        boardMapper.deleteBoardBatch(boardNos);
    }

    //삭제할 게시글이 3개월이 지나면 삭제
    @Scheduled(cron = "0 0 0 1 * ?")// 매달 1일 자정에 실행
    public void deleteExpiredBoards(){
        // 삭제할 게시글 번호를 가져오는 메소드
        List<Long> expiredBoardNos = boardMapper.findExpiredBoardNos();

        if (!expiredBoardNos.isEmpty()) {
            deleteBoardBatch(expiredBoardNos); // 기존의 deleteBoardBatch 호출
            log.info("만료된 게시글 삭제 완료: {}", expiredBoardNos);
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
