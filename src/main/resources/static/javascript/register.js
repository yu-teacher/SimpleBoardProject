let userNum;

function register() {
  let title = $(".text_title").val();
  let content = $(".text_content").val();
  let data = {
    title,
    content,
    userNum
  };
  $.ajax({
    type: "POST",
    url: "/board",
    data: JSON.stringify(data),
    contentType: "application/json",
    success: function () {
      location.href = "/list";
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
      },
      error: function (request, status, error) {
      }
  });
}

$(document).ready(function () {
  authCheck();
});