$(document).ready(function () {
    $('#content').summernote({
        // 에디터 크기 설정
        height: 800,
        // 에디터 한글 설정
        lang: 'ko-KR',
        toolbar: [
            // 글자 크기 설정
            ['fontsize', ['fontsize']],
            // 글자 [굵게, 기울임, 밑줄, 취소 선, 지우기]
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            // 글자색 설정
            ['color', ['color']],
            // 표 만들기
            ['table', ['table']],
            // 서식 [글머리 기호, 번호매기기, 문단정렬]
            ['para', ['ul', 'ol', 'paragraph']],
            // 줄간격 설정
            ['height', ['height']],
            // 이미지 첨부
            ['insert',['picture']]
        ],
        // 추가한 글꼴
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
        // 추가한 폰트사이즈
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72','96'],
        focus : true,
        // callbacks은 이미지 업로드 처리입니다.
        callbacks : {
            onImageUpload : function(files, editor, welEditable) {
                for (var i = 0; i < files.length; i++) {
                    imageUploader(files[i], this);
                }
            },
            onMediaDelete: function($target, editor, $editable) {
                if (confirm('이미지를 삭제하시겠습니까?')) {
                    var deletedImageUrl = $target.attr('src').split('/').pop()
                    // ajax 함수 호출
                    deleteSummernoteImageFile(deletedImageUrl)
                }
            }
        }
    });
    // 서버에서 받아온 내용으로 Summernote에 내용 설정
    const initialContent = /*[[${board.boardContent}]]*/ ''; // Thymeleaf로 내용을 설정
    $('#content').summernote('code', initialContent); // Summernote에 내용 설정
});

//에디터 이미지 업로드 기능
function imageUploader(file, el) {
    var formData = new FormData();
    formData.append('file', file);
    console.log(file);
    $.ajax({
        data : formData,
        type : "POST",
        url : '/api/image-upload',
        contentType : false,
        processData : false,
        enctype : 'multipart/form-data'
    }).done(function (data){
        console.log(data);
        let imageUrl = data.url; // JSON 형식으로 응답된 URL
        //서버 경로에 추가하기
        $(el).summernote('insertImage', imageUrl,
            function($image) {
                $image.css('width', "100%");
                // 이미지에 드래그로 크기를 조절할 수 있도록 설정
                $image.attr('contenteditable', false); // 이미지 수정 불가능 설정
            });
        console.log(data);
    }).fail(function(error){
        console.log(error);
    });
}
//에디터 이미지 삭제
function deleteSummernoteImageFile(imageName) {

    data = new FormData()

    data.append('file', imageName)

    $.ajax({
        data: data,
        type: 'POST',
        url: '/api/deleteSummernoteImageFile',
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
    }).done(function (response) {
        alert("이미지 삭제 성공: " + response);
        // 삭제한 이미지의 src 속성으로부터 $target을 찾아서 제거
        $('#content img[src*="' + imageName + '"]').remove(); // 삭제된 이미지 삭제
    }).fail(function (error) {
        console.error("이미지 삭제 실패: ", error);
    });
}