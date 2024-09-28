//검색기능
function searchProc() {
    let searchType = document.getElementById('searchCriteria').value;
    let searchVal = document.getElementById('keyword').value;
    let boardCode = document.querySelector('input[name="boardCode"]:checked')?.value; // 카테고리 선택

    if (searchVal.trim() === '') {
        alert('검색어를 입력하세요.');
        return;
    }

    let searchUrl = `/page/list?keyword=${encodeURIComponent(searchVal)}&searchType=${encodeURIComponent(searchType)}`;

    if (boardCode) {
        searchUrl += `&boardCode=${encodeURIComponent(boardCode)}`; // boardCode 추가
    }

    location.href = searchUrl;
}

//게시글 작성 페이지
function moveToBoardWritePage(){
    location.href='/page/writePage'
}