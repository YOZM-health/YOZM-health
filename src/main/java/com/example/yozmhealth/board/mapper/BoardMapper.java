package com.example.yozmhealth.board.mapper;

import com.example.yozmhealth.board.vo.dto.BoardDto;
import com.example.yozmhealth.config.page.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    List<BoardDto.BoardResponse> findAll(Criteria cri);

    Optional<BoardDto.BoardResponse> findByBoardId (Long boardNo);

    Long insertBoard(BoardDto.BoardRequest dto);

    Long updateBoard(BoardDto.BoardRequest dto);

    void deleteBoard(Long boardNo);

    void deleteBoardBatch(List<Long>boardNos);

    List<Long>findExpiredBoardNos();

    void readCountUp(Long boardNo);

    List<BoardDto.BoardResponse> boardNextPrevious (Long boardNo);

    Long boardTotalCount();
}
