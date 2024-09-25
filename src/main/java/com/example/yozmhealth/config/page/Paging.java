package com.example.yozmhealth.config.page;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Paging {

    private Long totalCount; // 게시판 전체 데이터 개수
    private int displayPageNum = 10; // 게시판 화면에서 한번에 보여질 페이지 번호의 개수

    private int startPage; // 화면의 시작 번호
    private int endPage; // 화면의 끝 번호
    private boolean prev; // 페이징 이전 버튼 활성화 여부
    private boolean next; // 페이징 다음 버튼 활성화 여부
    private String keyword;//키워드
    private String searchType;//검색 카테고리.

    private Criteria cri;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;

        pagingData();
    }

    private void pagingData() {

        endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
        startPage = (endPage - displayPageNum) + 1;

        int tempEndPage = (int) (Math.ceil(totalCount / (double) cri.getPerPageNum()));
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }

        prev = startPage == 1 ? false : true;
        next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
    }

    @Override
    public String toString() {
        return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
                + prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
    }


}
