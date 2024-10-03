$(document).ready(
    replyList()
);

// 댓글 목록 불러오기
function replyList() {
    let boardNo = document.getElementById('boardNo').value;

    $.ajax({
        url: '/api/reply/' + boardNo,
        type: 'GET',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function (resp) {
        const replyContainer = document.getElementById('replyContainer');
        replyContainer.innerHTML = ''; // 기존 댓글 초기화

        if (resp.length > 0) {
            resp.forEach(reply => {
                const replyElement = document.createElement('div');
                replyElement.className = 'mb-2';
                replyElement.innerHTML = `
                    <span class="fw-bold">${reply.replyAuthor}</span>
                    <span class="text-muted">${reply.replyCreatedTime}</span>
                    <p>${reply.replyContents}</p>
                    <button class="btn btn-link" onclick="showReplyForm(${reply.replyNo})">답글</button>
                    <button class="btn btn-link" onclick="showUpdateForm(${reply.replyNo}, '${reply.replyContents}')">수정</button>
                    <button class="btn btn-danger" onclick="replyDelete(${reply.replyNo})">삭제</button>
                    <button class="btn btn-link" id="likeBtn_${reply.replyNo}" onclick="toggleLike(${reply.replyNo}, ${reply.userNo}, 'reply')">
                        ${reply.isLiked ? '❤️' : '🤍'} 좋아요
                    </button>
                    <span id="likeCount_${reply.replyNo}">${reply.likeCount || 0}</span>
                    <div id="replyForm_${reply.replyNo}" style="display:none">
                        <input type="text" id="replyAuthor_${reply.replyNo}" class="form-control mb-1" placeholder="작성자 이름을 입력하세요">
                        <input type="text" id="replyContent_${reply.replyNo}" class="form-control mb-1" placeholder="답글 입력">
                    </div>`;

                // 대댓글이 있는 경우
                if (reply.childReplies && reply.childReplies.length > 0) {

                    const subReplyContainer = document.createElement('div');

                    subReplyContainer.className = 'ml-4'; // 대댓글 들여쓰기

                    reply.childReplies.forEach(subReply => {
                        const subReplyElement = document.createElement('div');
                        subReplyElement.className = 'mb-2';
                        subReplyElement.innerHTML = `
                                <span class="fw-bold">${subReply.replyAuthor}</span>
                                <span class="text-muted">${subReply.replyCreatedTime}</span>
                                <p>${subReply.replyContents}</p>
                                    <button class="btn btn-link" onclick="showUpdateForm(${subReply.replyNo}, '${subReply.replyContents}')">수정</button>
                                    <button class="btn btn-danger" onclick="replyDelete(${subReply.replyNo})">삭제</button>
                                    <button class="btn btn-link" id="likeBtn_${subReply.replyNo}" onclick="toggleLike(${subReply.replyNo}, ${subReply.userNo}, 'subReply')">
                                        ${subReply.isLiked ? '❤️' : '🤍'} 좋아요
                                    </button>
                                <span id="likeCount_${subReply.replyNo}">${subReply.likeCount || 0}</span>`;

                        subReplyContainer.appendChild(subReplyElement);
                    });
                    replyElement.appendChild(subReplyContainer);
                }
                replyContainer.appendChild(replyElement);
            });
        } else {
            replyContainer.innerHTML = '<p>댓글이 없습니다.</p>';
        }
    }).fail(function (error) {
        console.log(error);
    });
}

// 댓글 작성
function insertReply(parentReplyNo = null) {
    let boardNo = document.getElementById('boardNo').value;
    let author = document.getElementById('replyAuthor').value;
    let contents = parentReplyNo ? document.getElementById('replyContent_' + parentReplyNo).value : document.getElementById('commentContent').value;
    let userNo = document.getElementById('userNo').value;

    const data = {
        replyAuthor: author,
        replyContents: contents,
        userNo: userNo,
        boardNo2: boardNo,
        parentReplyNo: parentReplyNo // 대댓글이면 부모 댓글 번호 전달
    };

    $.ajax({
        url: '/api/reply/' + boardNo,
        type: 'POST',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function (resp) {
        alert('댓글이 작성되었습니다.');
        replyList();
        document.getElementById('replyAuthor').value = '';
        document.getElementById('commentContent').value = '';
    }).fail(function (error) {
        console.log(error);
        alert('댓글 작성에 실패했습니다.');
    });
}

// 댓글 삭제
function replyDelete(replyNo) {
    $.ajax({
        url: '/api/reply/' + replyNo,
        type: 'DELETE'
    }).done(function (resp) {
        alert('댓글이 삭제되었습니다.');
        replyList();
    }).fail(function (error) {
        console.log(error);
        alert('댓글 삭제에 실패했습니다.');
    });
}

// 댓글 수정 폼 표시
function showUpdateForm(replyNo, currentContent) {
    const replyElement = document.getElementById('replyForm_' + replyNo);

    if (!replyElement) {
        console.error('해당하는 replyForm이 존재하지 않습니다. replyNo:', replyNo);
        return; // replyElement가 없을 경우 함수 종료
    }
    // 폼 토글 기능 추가 (이전에 열렸던 폼을 다시 누르면 사라지게 설정)
    if (replyElement.style.display === 'block') {
        replyElement.style.display = 'none'; // 폼이 이미 열려있으면 닫기
        return; // 폼을 닫고 함수 종료
    } else {
        replyElement.style.display = 'block'; // 폼 열기

    }

    document.getElementById('replyContent_' + replyNo).value = currentContent; // 기존 댓글 내용을 수정란에 표시

    // 답글 등록 버튼 숨기기
    const replyBtn = document.querySelector(`#replyForm_${replyNo} .reply-btn`);
    if (replyBtn) replyBtn.style.display = 'none';

    // 수정 버튼 추가 (이미 추가된 경우 중복 추가 방지)
    let updateBtn = document.getElementById('updateBtn_' + replyNo);

    if (!updateBtn) {
        updateBtn = document.createElement('button');
        updateBtn.id = 'updateBtn_' + replyNo;
        updateBtn.className = 'btn btn-primary btn-sm mt-1';
        updateBtn.innerText = '댓글 수정';
        updateBtn.onclick = function () {
            updateReply(replyNo); // 수정 버튼 클릭 시 updateReply 호출
        };
        // 수정 폼에 수정 버튼 추가
        replyElement.appendChild(updateBtn);
    }
    updateBtn.style.display = 'block'; // 수정 버튼 보이기
}

// 댓글 수정
function updateReply(replyNo) {
    let updatedContent = document.getElementById('replyContent_' + replyNo).value;

    const data = {
        replyContents: updatedContent
    };

    $.ajax({
        url: '/api/reply/' + replyNo,
        type: 'PUT',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function (resp) {
        alert('댓글이 수정되었습니다.');
        replyList(); // 댓글 목록 갱신
    }).fail(function (error) {
        console.log(error);
        alert('댓글 수정에 실패했습니다.');
    });
}

// 답글 작성 폼 표시
function showReplyForm(replyNo) {
    const replyForm = document.getElementById('replyForm_' + replyNo);
    replyForm.style.display = replyForm.style.display === 'none' ? 'block' : 'none'; // 폼 토글

    // 답글 버튼을 눌렀을 때는 수정 버튼을 숨기고 답글 등록 버튼만 보이게 설정
    const updateBtn = document.getElementById('updateBtn_' + replyNo);
    if (updateBtn) updateBtn.style.display = 'none'; // 수정 버튼 숨기기

    // 내용 입력창을 빈 값으로 초기화
    document.getElementById('replyAuthor_' + replyNo).value = ''; // 작성자 입력창은 시큐리티 아이디를 넣기.
    document.getElementById('replyContent_' + replyNo).value = ''; // 내용 입력창 비우기

    //답글 등록 버튼 처리
    const replyBtn = document.querySelector(`#replyForm_${replyNo} .reply-btn`);

    if (!replyBtn) {
        const newReplyBtn = document.createElement('button');
        newReplyBtn.className = 'btn btn-primary btn-sm mt-1 reply-btn';
        newReplyBtn.innerText = '답글 등록';
        newReplyBtn.onclick = function () {
            insertReply(replyNo); // 답글 등록 버튼 클릭 시 insertReply 호출
        };
        replyForm.appendChild(newReplyBtn);
    }
    replyBtn.style.display = 'block'; // 답글 등록 버튼 보이기
}

// 좋아요 토글 함수
function toggleLike(replyNo, userNo, type) {
    const data = {
        replyNo2: replyNo,
        userNo2: userNo
    };

    $.ajax({
        url: '/api/like/toggle',
        type: 'POST',
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json; charset=utf-8'
    }).done(function (resp) {
        // 좋아요 상태 및 수 업데이트
        const likeButton = document.getElementById(`likeBtn_${replyNo}`);
        const likeCount = document.getElementById(`likeCount_${replyNo}`);

        likeButton.innerHTML = resp.isLiked ? '❤️' : '🤍' + ' 좋아요';
        likeCount.innerHTML = resp.likeCount;

        alert('좋아요 상태가 업데이트되었습니다.');
    }).fail(function (error) {
        console.log(error);
        alert('좋아요 업데이트에 실패했습니다.');
    });
}