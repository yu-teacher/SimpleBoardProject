$(document).ready(function () {
  $.ajax({
    type: "GET",
    url: "/board",
    contentType: "charset=utf-8",
    success: function (data) {
      for (let i = 0; i < data.length; i++) {
        let num = data[i].boardNum;
        let title = data[i].title;
        let writer = "라온";
        let date = data[i].regDate;
        let view = data[i].viewCnt;

        let html =
          '<div class="list">' +
          '<div class="num">' +
          num +
          "</div>" +
          '<div class="title">' +
          '<a href="/view?boardNum=' +
          num +
          '">' +
          title +
          "</a>" +
          "</div>" +
          '<div class="writer">' +
          writer +
          "</div>" +
          '<div class="date">' +
          date +
          "</div>" +
          '<div class="view">' +
          view +
          "</div>" +
          "</div>";

        $(".board_list").append(html);
      }
    },
    error: function () {
      alert("에러 발생");
    },
  });
});
