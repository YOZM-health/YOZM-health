package com.example.yozmhealth.board.mapper;

import com.example.yozmhealth.board.vo.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryDto.Response> categoryList();
    //카테고리 생성
    void insertCategory(CategoryDto.Request request);
    //카테고리 삭제
    void deleteCategory(Long catCode);
}
