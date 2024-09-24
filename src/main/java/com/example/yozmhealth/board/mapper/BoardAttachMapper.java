package com.example.yozmhealth.board.mapper;

import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardAttachMapper {

    List<BoardAttachDto.Response> attachByBoardList(Long boardNo2);

    void insertAttach(BoardAttachDto.Response dto);

    void deleteAttach(Long boardNo);

    BoardAttachDto.Response getFileByOriginName(String originName);
}
