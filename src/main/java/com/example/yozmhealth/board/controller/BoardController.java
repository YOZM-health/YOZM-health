package com.example.yozmhealth.board.controller;

import com.example.yozmhealth.board.service.BoardService;
import com.example.yozmhealth.board.service.CategoryService;
import com.example.yozmhealth.board.vo.dto.BoardDto;
import com.example.yozmhealth.board.vo.dto.CategoryDto;
import com.example.yozmhealth.config.page.Criteria;
import com.example.yozmhealth.config.page.Paging;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Log4j2
@Controller
@AllArgsConstructor
@RequestMapping("/page")
public class BoardController {

    private final BoardService boardService;

    private final CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView boardListPage(Criteria cri,
                                      @RequestParam(required = false, defaultValue = "T") String searchType,
                                      @RequestParam(required = false) String keyword) {
        ModelAndView mv = new ModelAndView();
        List<BoardDto.BoardResponse> list;
        Long totalCount;
        //게시글 목록
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
        //조회수 증가
        boardService.readCountUp(boardId);

        mv.addObject("nextPrevious",nextPreviousBoardTitle);
        mv.addObject("board",boardVO);

        mv.setViewName("/board/freeBoardDetail");
        return mv;
    }

    @GetMapping("/write")
    public ModelAndView boardInputPage() {

        ModelAndView mv = new ModelAndView();

        mv.setViewName("/board/freeBoardInput");

        return mv;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Long insertBoardProc(@RequestPart(value = "boardDto") BoardDto.BoardRequest request,
                                @RequestPart(value = "attachments") List<MultipartFile> attachments) {
        Long insertResult = 0L;

        try {
            request.setBoardStatus('C'); // 완전히 저장이 되었으면 C 아닌 경우에는 P 로 구분
            request.setAttachLists(attachments);//첨부 파일
            insertResult = boardService.insertBoard(request);
            return insertResult;
        } catch (Exception e) {
            e.getMessage();
        }
        return insertResult;
    }

    @PostMapping("/temp-board")
    @ResponseStatus(HttpStatus.CREATED)
    public Long insertTemporaryBoard(@RequestPart(value = "boardDto")BoardDto.BoardRequest request,
                                     @RequestPart(value = "attachments") List<MultipartFile> attachments) {
        Long insertTmpResult = 0L;

        try {
            request.setBoardStatus('P');
            request.setAttachLists(attachments);
            insertTmpResult = boardService.insertBoard(request);
            return insertTmpResult;
        } catch (Exception e){
            e.getMessage();
        }
        return insertTmpResult;
    }

    @ResponseBody
    @PutMapping("/{id}")
    public Long updateBoardProc(@PathVariable("id") Long boardNo,
                                @RequestPart(value = "boardDto")BoardDto.BoardRequest request,
                                @RequestPart(value = "attachments") List<MultipartFile> attachments) {
        Long updateResult = 0L;

        try {
            request.setBoardNo(boardNo);
            request.setAttachLists(attachments);
            updateResult = boardService.updateBoard(request);
        } catch (Exception e) {
            e.getMessage();
        }
        return updateResult;
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public String deleteBoardProc(@PathVariable("id")Long boardNo) {
        boardService.deleteBoard(boardNo);
        return "Delete O.k";
    }
}
