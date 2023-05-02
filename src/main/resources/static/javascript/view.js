const link = document.location.href;
const temp = link.split("=");
const boardNum = temp[1];
$(document).ready(function () {
  let writer = "라온";
  $.ajax({
    type: "GET",
    url: "/board/" + boardNum,
    contentType: "charset=utf-8",
    success: function (data) {
      $(".title h1").text(data.title);
      $(".info dd").eq(0).text(data.boardNum);
      $(".info dd").eq(1).text(writer);
      $(".info dd").eq(2).text(data.regDate);
      $(".info dd").eq(3).text(data.viewCnt);
      $(".content").text(data.content);
      $(".white").click(function () {
        window.location.href = "/update?boardNum=" + boardNum;
      });
    },
  });

  $.ajax({
    type: "GET",
    url: "/comment/?boardNum=" + boardNum,
    contentType: "charset=utf-8",
    success: function (data) {
      for (let i = 0; i < data.length; i++) {
        let comment = data[i].content;
        let writer = "라온";
        let date = data[i].regDate;
        let commentNum = data[i].commentNum;
        let comment_item =
          "<div class='comment_item'>" +
          "<div class='info'><dl><dt>작성자</dt><dd>" +
          writer +
          "</dd></dl><dl><dt>작성일</dt><dd>" +
          date +
          "</dd></dl></div>" +
          "<div class='comment_content'>" +
          comment +
          "</div>" +
          "<div class='comment_button'><button class='comment_edit' onclick='modal(" +
          commentNum +
          ")'>수정</button><button onclick='commentdelete(" +
          commentNum +
          ")'>삭제</button></div>" +
          "</div>";
        $(".comment_list").append(comment_item);
      }
    },
    error: function () {
      alert("에러 발생");
    },
  });
});

function commentregister() {
  let comment = $(".text_comment").val();
  let data = {
    content: comment,
    boardNum: bNum,
  };
  $.ajax({
    type: "POST",
    url: "/comment",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function () {
      window.location.href = "/view?boardNum=" + boardNum;
    },
  });
}

function commentdelete(commentNum) {
  console.log(commentNum);
  $.ajax({
    type: "DELETE",
    url: "/comment/" + commentNum,
    data: JSON.stringify(commentNum),
    contentType: "application/json",
    success: function () {
      window.location.href = "/view?boardNum=" + boardNum;
    },
  });
}


