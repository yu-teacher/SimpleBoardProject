function register() {
  let title = $(".text_title").val();
  let content = $(".text_content").val();

  if (title === "" || content === "") {  // 제목 또는 내용이 비어있을 경우
    if (title === "") {
      $(".text_title").focus();  // 제목 필드에 포커스 이동
    } else {
      $(".text_content").focus();  // 내용 필드에 포커스 이동
    }
    return;  // 함수 종료
  }

  let data = {
    title: title,
    content: content,
  };
  $.ajax({
    type: "POST",
    url: "/board",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function () {
      window.location.href = "/list";
    },
  });
}