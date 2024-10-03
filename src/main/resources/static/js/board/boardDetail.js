//URL 복사
function copyURL() {
    // 현재 페이지 URL을 가져옵니다.
    const url = window.location.href;
    // 클립보드에 복사합니다.
    navigator.clipboard.writeText(url).then(function() {
        alert('URL이 복사되었습니다!'); // 복사가 성공하면 알림 표시
    }).catch(function(err) {
        alert('URL 복사에 실패했습니다: ', err); // 오류 발생 시 알림 표시
    });
}

//글 수정 페이지 이동
function moveToUpdateBoardPage() {
    const boardNo = document.getElementById("boardNo").value;
    location.href='/page/edite/'+boardNo;
}

function moveToBoardList() {
    location.href='/page/list'
}