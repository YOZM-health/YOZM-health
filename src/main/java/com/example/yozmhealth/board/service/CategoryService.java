package com.example.yozmhealth.board.service;

import com.example.yozmhealth.board.mapper.CategoryMapper;
import com.example.yozmhealth.board.vo.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto.Response>categoryList(){
        return categoryMapper.categoryList();
    }

    public void insertCategory(CategoryDto.Request request){
        categoryMapper.insertCategory(request);
    }

    public void deleteCategory(Long catCode) {
        categoryMapper.deleteCategory(catCode);
    }
}
