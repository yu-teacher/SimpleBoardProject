let userNum;

$(document).ready(function () {
  authCheck();
  let link = document.location.href;
  let temp = link.split(/[&=]/);
  let boardNum = temp[1];
  
  $.ajax({
    url: "/board/" + boardNum,
    type: "GET",
    dataType: "json",
    success: function (data) {  
      $(".text_title").text(data.title);
      $(".text_content").text(data.content);
      $(".text_view").val(data.viewCnt);
    },
  });
});

function update() {
  let link = document.location.href;
  let temp = link.split(/[&=]/);
  let boardNum = temp[1];
  let title = $(".text_title").val();
  let content = $(".text_content").val();
  let viewCnt = $(".text_view").val();
  let data = {
    title,
    content,
    boardNum,
    viewCnt,
    userNum
  };
  $.ajax({
    type: "PUT",
    url: "/board/",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function () {
        window.location.href = "/view?boardNum=" + boardNum;
    },
  });
}

function authCheck() {

  let Authorization = localStorage.getItem("Authorization");

  $.ajax({
      type: 'GET',
      url: '/auth/check',
      dataType: 'JSON',
      contentType: 'application/json; charset=UTF-8',
      headers: {
          'Authorization': Authorization
      },
      success: function (result){
        userNum = result.userNum;
      }
  });
}
