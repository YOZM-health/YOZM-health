//파일 검증
function validateFile(fileInput) {
    const allowedExtensions = ['png', 'gif', 'jpg', 'jpeg']; // 허용하는 파일 확장자 목록
    let valid = true;

    Array.from(fileInput.files).forEach(file => {
        const fileName = file.name;
        const fileExtension = fileName.split('.').pop().toLowerCase();

        if (!allowedExtensions.includes(fileExtension)) {
            alert(`${fileName} 파일은 허용되지 않는 형식입니다.`);
            valid = false; // 허용되지 않는 파일 형식일 경우 valid를 false로 설정
        }
    });

    return valid;
}
//게시글 삭제
function boardDeleteProc() {
    let boardNo = document.getElementById('boardNo').value;

    $.ajax({
        url:'/page/'+boardNo,
        type:'DELETE'
    }).done(function(resp){
        console.log(resp);
        alert('게시글이 삭제 되었습니다.');
        location.href='/page/list'
    })
}

//게시글 수정
function boardUpdateProc() {
    let formDate = new FormData();
    let boardNo = document.getElementById('boardNo').value;
    let title = document.getElementById('title').value;
    let author = document.getElementById('nickname').value;
    let boardCode = document.getElementById('category').value;
    let userNo = document.getElementById('userNo').value;
    let contents = $('#content').summernote('code');;

    const data  = {
        boardTitle : title,
        boardContent : contents,
        boardAuthor : author,
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
        url:'/page/'+boardNo,
        type:'PUT',
        data:formDate,
        contentType: false,
        processData: false,
        cache: false,
        enctype: 'multipart/form-data'
    }).done(function(resp){
        alert('게시글이 수정되었습니다.');
        location.href = '/page/list';
    }).fail(function (error){
        console.error('게시글 수정 실패:', error);
        alert('게시글 수정에 실패했습니다.');
    });
}