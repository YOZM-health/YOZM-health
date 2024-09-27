//카카오 톡 공유기능
Kakao.init(`[[${kakaoApiKey}]]`);

function shareMessage() {
    console.log(Kakao.isInitialized());
    const defaultImageUrl = 'https://example.com/default-image.jpg';

    Kakao.Share.sendDefault({
        objectType: 'feed',
        content: {
            title: '[[${board.boardTitle}]]', // 여기서 게시글 제목을 넣어주세요
            description: '[[${board.boardContent}]]', // 게시글 설명을 넣어주세요
            imageUrl: defaultImageUrl, // 게시글 이미지 URL
            link: {
                mobileWebUrl: 'http://localhost:8091/page/${board.boardNo}', // 모바일 웹 URL
                webUrl: 'http://localhost:8091/page/${board.boardNo}' // 웹 URL
            }
        },
        buttons: [
            {
                title: '웹에서 보기',
                link: {
                    mobileWebUrl: 'http://localhost:8091/page/${board.boardNo}',
                    webUrl: 'http://localhost:8091/page/${board.boardNo}'
                }
            }
        ]
    });
}

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
//글 목록 이동
function moveToBoardList(){
    location.href='/page/list'
}
//글 수정 페이지 이동
function moveToUpdateBoardPage() {
    location.href='/page/'
}