const link = document.location.href;
const temp = link.split(/[&=]/);
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

  let currentPageNum = 1;
let totalPages = 0;
let pageArray = [];
getBoardList();

$(document).on("click", ".num", function () {
  currentPageNum = parseInt($(this).text());
  getBoardList();
});

$(document).on("click", ".prev", function () {
  if (currentPageNum > 1) {
    currentPageNum--;
    getBoardList();
  }
});

$(document).on("click", ".next", function () {
  if (currentPageNum < totalPages) {
    currentPageNum++;
    getBoardList();
  }
});

$(document).on("click", ".first", function () {
  currentPageNum = 1;
  getBoardList();
});

$(document).on("click", ".last", function () {
  currentPageNum = totalPages;
  getBoardList();
});

function getBoardList() {
  let html = "";
  $.ajax({
    type: "GET",
    url: "/comment?boardNum=" + boardNum + "&pageNum=" + currentPageNum,
    contentType: "charset=utf-8",
    success: function (data) {
    let content = data.content;
    $(".comment_list").empty();  // 이전 댓글 목록 초기화
      for (let i = 0; i < content.length; i++) {
        let comment = content[i].content;
        let writer = "라온";
        let date = content[i].regDate;
        let commentNum = content[i].commentNum;
        let comment_item =
          "<div class='comment_item'>"+
          "<div class='info'><dl><dt>작성자</dt><dd>" +
          writer +
          "</dd></dl><dl><dt>작성일</dt><dd>" +
          date +
          "</dd></dl></div>" +
          "<div class='comment_content'>" +
          comment +
          "</div>" +
          "<div class='comment_button'><button class='comment_edit' onclick='modalcall(" +
          commentNum +
          ")'>수정</button><button onclick='commentdelete(" +
          commentNum +
          ")'>삭제</button></div>" +
          "</div>";
        $(".comment_list").append(comment_item);
      }

      html = "";

      totalPages = data.totalPages;
      let startPage = Math.floor((currentPageNum - 1) / 5) * 5 + 1;
      let endPage = startPage + 4;

      if (endPage > totalPages) {
        endPage = totalPages;
      }

      if (currentPageNum > 1) {
        html += '<a href="#" class="prev">' + "<" + "</a>";
      }

      for (let i = startPage; i <= endPage; i++) {
        if (currentPageNum === i) {
          html += '<a href="#" class="num on">' + i + "</a>";
        } else {
          html += '<a href="#" class="num">' + i + "</a>";
        }
      }

      if (currentPageNum < totalPages) {
        html += '<a href="#" class="next">&gt;</a>';
      }

      $(".board_page").html(html);
    },
  });
}
});

function commentregister() {
  let comment = $(".text_comment").val();
  let data = {
    content: comment,
    boardNum: boardNum,
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

function modalcall(commentNum) {
  $('.modal').toggleClass('on');
  $('.modal-background').toggleClass('on');
  let a = "<input type='hidden' value='"+commentNum+"' class='comment-num'>"
  
  
  $.ajax({
    url: "/comment/" + commentNum,
    type: 'GET',
    dataType: 'json',
    success: function(data) {
      $('.modal_textarea').val(data.content); // 댓글 내용을 textarea에 넣어줍니다.
      $(".modal").append(a);
    },
  });
}

function cancelmodal(){
  $('.modal').toggleClass('on');
  $('.modal-background').toggleClass('on');
}

function commentsave() {
  let comment = $('.modal textarea').val();
  let commentNum = $('.comment-num').val();
  let data = {
    content: comment,
    boardNum : boardNum,
    commentNum : commentNum
  };
  $.ajax({
    type: "PUT",
    url: "/comment",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function() {
      $('.modal').toggleClass('on');
      $('.modal-background').toggleClass('on');
      window.location.href = link;
    },
  });
}


function commentcancel(){
  $("textarea").val("");
}