package com.example.yozmhealth.board.controller;

import com.example.yozmhealth.board.service.BoardAttachService;
import com.example.yozmhealth.board.service.BoardService;
import com.example.yozmhealth.board.service.CategoryService;
import com.example.yozmhealth.board.vo.dto.BoardAttachDto;
import com.example.yozmhealth.board.vo.dto.BoardDto;
import com.example.yozmhealth.board.vo.dto.CategoryDto;
import com.example.yozmhealth.config.page.Criteria;
import com.example.yozmhealth.config.page.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/page")
public class BoardController {

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    private final BoardService boardService;

    private final CategoryService categoryService;

    private final BoardAttachService attachService;

    @GetMapping("/list")
    public ModelAndView boardListPage(Criteria cri,
                                      @RequestParam(required = false, defaultValue = "T") String searchType,
                                      @RequestParam(required = false,value = "keyword") String keyword,
                                      @RequestParam(required = false,value = "boardCode") Long boardCode) {

        ModelAndView mv = new ModelAndView();
        List<BoardDto.BoardResponse> list;
        Long totalCount;

        //게시글 목록
        if(boardCode != null){
            cri.setBoardCode(boardCode);//카테고리가 있는경우
        }
        list = boardService.findAll(cri);
        //게시글 총 갯수
        totalCount = boardService.boardListTotalCount();
        //카테고리 목록
        List<CategoryDto.Response> categoryDtoList = categoryService.categoryList();

        Paging paging = new Paging();

        paging.setCri(cri);
        paging.setTotalCount(totalCount);
        paging.setKeyword(keyword);
        paging.setSearchType(searchType);
        paging.setBoardCode(boardCode);

        mv.addObject("categoryList",categoryDtoList);
        mv.addObject("boardList",list);
        mv.addObject("totalCount",totalCount);
        mv.addObject("paging",paging);

        mv.setViewName("/board/freeBoardList");

        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView boardDetailPage(@PathVariable("id") Long boardId) {
        ModelAndView mv = new ModelAndView();
        //게시글 조회
        BoardDto.BoardResponse boardVO = boardService.findByBoardId(boardId);
        //게시글 이전글/다음글
        List<BoardDto.BoardResponse> nextPreviousBoardTitle = boardService.boardNextPrevious(boardId);
        //카테고리 목록
        List<CategoryDto.Response> categoryDtoList = categoryService.categoryList();
        //파일 목록
        List<BoardAttachDto.Response> attachList = attachService.attachList(boardId);
        //조회수 증가
        boardService.readCountUp(boardId);

        mv.addObject("kakaoApiKey",kakaoApiKey);
        mv.addObject("category",categoryDtoList);
        mv.addObject("nextPrevious",nextPreviousBoardTitle);
        mv.addObject("board",boardVO);
        mv.addObject("attachList",attachList);

        mv.setViewName("/board/freeBoardDetail");
        return mv;
    }

    @GetMapping("/writePage")
    public ModelAndView boardInputPage() {
        ModelAndView mv = new ModelAndView();
        //카테고리 목록
        List<CategoryDto.Response> categoryDtoList = categoryService.categoryList();
        mv.addObject("category",categoryDtoList);
        mv.setViewName("/board/freeBoardInput");
        return mv;
    }

    @ResponseBody
    @PostMapping("/write")
    @ResponseStatus(HttpStatus.CREATED)
    public Long insertBoardProc(@RequestPart(value = "boardDto") BoardDto.BoardRequest request,
                                @RequestPart(value = "attachments",required = false) List<MultipartFile> attachments) throws IOException {

            // attachments가 null이 아닌 경우에만 isEmpty()를 호출
            if (attachments != null && !attachments.isEmpty()) {
                request.setAttachLists(attachments);
            } else {
                log.warn("Attachments is null or empty.");
            }
            request.setBoardStatus('C');
            Long insertResult = boardService.insertBoard(request);
            log.info(insertResult);
            return insertResult;
    }

    @ResponseBody
    @PostMapping("/temp-board")
    @ResponseStatus(HttpStatus.CREATED)
    public Long insertTemporaryBoard(@RequestPart(value = "boardDto")BoardDto.BoardRequest request,
                                     @RequestPart(value = "attachments") List<MultipartFile> attachments) throws IOException {
        request.setBoardStatus('P');
        // attachments가 null이 아닌 경우에만 isEmpty()를 호출
        if (attachments != null && !attachments.isEmpty()) {
            request.setAttachLists(attachments);
        } else {
            log.warn("Attachments is null or empty.");
        }
        Long insertTmpResult = boardService.insertBoard(request);
        return insertTmpResult;
    }

    @GetMapping("/edite/{id}")
    public ModelAndView boardEditePage(@PathVariable("id")Long boardNo) {
        ModelAndView mv = new ModelAndView();
        //게시글 단일 조회
        BoardDto.BoardResponse boardResponse = boardService.findByBoardId(boardNo);
        //첨부파일 조회

        mv.addObject("board",boardResponse);
        mv.setViewName("/board/freeBoardEdite");

        return mv;
    }

    @ResponseBody
    @PutMapping("/{id}")
    public Long updateBoardProc(@PathVariable("id") Long boardNo,
                                @RequestPart(value = "boardDto")BoardDto.BoardRequest request,
                                @RequestPart(value = "attachments") List<MultipartFile> attachments) throws IOException {

        request.setBoardNo(boardNo);
        // attachments가 null이 아닌 경우에만 isEmpty()를 호출
        if (attachments != null && !attachments.isEmpty()) {
            request.setAttachLists(attachments);
        } else {
            log.warn("Attachments is null or empty.");
        }
        Long updateResult = boardService.updateBoard(request);

        return updateResult;
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public String deleteBoardProc(@PathVariable("id")Long boardNo) {
        boardService.deleteBoard(boardNo);
        return "Delete O.k";
    }
}
