$(document).ready(function () {
  let link = document.location.href;
  let boardNum = link.split("=");
  $.ajax({
    url: "/board/" + boardNum[1],
    type: "GET",
    dataType: "json",
    success: function (data) {
      $(".text_title").text(data.title);
      $(".text_content").text(data.content);
    },
  });
});
function update() {
  let link = document.location.href;
  let boardNum = link.split("=");
  let title = $(".text_title").val();
  let content = $(".text_content").val();
  let data = {
    title: title,
    content: content,
    boardNum: boardNum[1],
  };
  $.ajax({
    type: "PUT",
    url: "/board/",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function () {
        window.location.href = "/view?boardNum=" + boardNum[1];
    },
  });
}
