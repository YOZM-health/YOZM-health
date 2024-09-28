//글작성 취소
function moveToBoardList() {
    location.href='/page/list'
}

// 파일 확장자 검증 함수
function validateFile(fileInput) {
    const allowedExtensions = /(\.png|\.gif|\.jpg|\.jpeg)$/i;
    const files = fileInput.files;  // 다중 파일 입력 대응

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (!allowedExtensions.exec(file.name)) {
            alert(file.name + '은(는) 허용되지 않는 파일 형식입니다. png, gif, jpg, jpeg 파일만 업로드 가능합니다.');
            fileInput.value = ''; // 파일 초기화
            return false;
        }
    }
    return true;
}

//글작성 기능
function boardProc() {

    let formDate = new FormData();

    let title = document.getElementById('title').value;
    let nickname = document.getElementById('nickname').value;
    let content =   document.getElementById('content').value;
    let boardCode = document.getElementById('category').value;
    let userNo = document.getElementById('userNo').value;
    let editorContent = $('#content').summernote('code');

    const data  = {
        boardTitle : title,
        boardContent : editorContent,
        boardAuthor : nickname,
        boardCode : boardCode,
        userNo : userNo
    }

    formDate.append('boardDto',new Blob([JSON.stringify(data)], {type: "application/json"}));

    let files = document.querySelectorAll('input[type="file"]');
    let valid = true;

    files.forEach((fileInput, index) => {
        if (!validateFile(fileInput)) {
            valid = false;
            return; // 파일이 유효하지 않으면 중단
        }
        Array.from(fileInput.files).forEach(file => {
            console.log(file);
            formDate.append('attachments', file);
        });
    });

    if (!valid) {
        alert('파일 형식에 문제가 있습니다. 다시 확인해주세요.');
        return; // 유효하지 않으면 폼 제출 중단
    }

    $.ajax({
        url:'/page/write',
        type:'POST',
        data: formDate,
        contentType: false,
        processData: false,
        cache: false,
        enctype: 'multipart/form-data'
    }).done(function(resp){
        console.log(resp);
        alert('게시글이 작성이 되었습니다.');
        location.href='/page/list';
    }).fail(function(error){
        console.log(error);
        alert('글 작성을 하는데 문제가 생겼습니다.');
    });
}

//임시 게시글 작성
function tmpBoardProc() {
    let formDate = new FormData();

    let title = document.getElementById('title').value;
    let nickname = document.getElementById('nickname').value;
    let content = stripTags(document.getElementById('content').value);
    let boardCode = document.getElementById('boardCode').value;

    const data  = {
        boardTitle : title,
        boardContent : content,
        boardAuthor : nickname,
        boardCode : boardCode
    }

    formDate.append('boardDto',new Blob([JSON.stringify(data)], {type: "application/json"}));

    let files = document.querySelectorAll('input[type="file"]');
    let valid = true;

    files.forEach((fileInput, index) => {
        if (!validateFile(fileInput)) {
            valid = false;
            return; // 파일이 유효하지 않으면 중단
        }
        Array.from(fileInput.files).forEach(file => {
            console.log(file);
            formDate.append('attachments', file);
        });
    });

    if (!valid) {
        alert('파일 형식에 문제가 있습니다. 다시 확인해주세요.');
        return; // 유효하지 않으면 폼 제출 중단
    }

    $.ajax({
        url:'/page/temp-board',
        type:'POST',
        data: formDate,
        contentType: false,
        processData: false,
        cache: false,
        enctype: 'multipart/form-data'
    }).done(function(resp){
        console.log(resp);
        alert('임시글이 작성이 되었습니다.');
    });
}