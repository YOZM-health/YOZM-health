package com.example.yozmhealth.config.page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Criteria {

    private int page; // 현재 페이지 번호
    private int perPageNum; // 페이지당 보여줄 게시글의 개수
    private String searchType;// 검색어
    private String keyword;// 검색 카테고리.

    // 생성자
    public Criteria() {
        // 페이지 번호랑 보여줄 게시글의 개수 설정하기.
        this.page = 1;
        this.perPageNum = 5;
    }

    public int getPageStart() {
        return (this.page - 1) * perPageNum;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;

        } else {
            this.page = page;
        }
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    public void setPerPageNum(int perPageNum) {
        int cnt = this.perPageNum;

        if (perPageNum != cnt) {
            this.perPageNum = cnt;
        } else {
            this.perPageNum = perPageNum;
        }
    }

    @Override
    public String toString() {
        return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
    }
}
