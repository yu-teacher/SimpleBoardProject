$(document).ready(function () {
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
    $.ajax({
      type: "GET",
      url: "/board/list/" + currentPageNum,
      contentType: "charset=utf-8",
      success: function (data) {
        totalPages = data.totalPages;
        pageArray = [];

        
        for (let i = 1; i <= totalPages; i++) {
          pageArray.push(i);
        }

        let content = data.content;
        let html = "";

        for (let i = 0; i < content.length; i++) {
          let num = content[i].boardNum;
          let title = content[i].title;
          let writer = content[i].writer;
          let date = content[i].regDate;
          let view = content[i].viewCnt;
          let commentcnt = content[i].commentCnt;
          if (title.length > 12) {
            title = title.substring(0, 9) + "...";
          }
          html +=
            '<div class="list">' +
            '<div class="num">' +
            num +
            "</div>" +
            '<div class="title">' +
            '<a onclick="viewCnt('+num+')" href="/view?boardNum=' +
            num +
            "&pageNum=1" +
            '">' +
            title +"("+commentcnt+")"+
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
        }

   
        $(".board_list").html(html);

        html = "";

        let startPage = Math.floor((currentPageNum - 1) / 5) * 5 + 1;
        let endPage = startPage + 4;
        
        if (endPage > totalPages) {
          endPage = totalPages;
        }

        if (currentPageNum > 0) {
          html += '<a href="#" class="prev">'+"<"+"</a>";
        }

        for (let i = startPage; i <= endPage; i++) {
          if (currentPageNum === i) {
            html += '<a href="#" class="num on">' + i + "</a>";
          } else {
            html += '<a href="#" class="num">' + i + "</a>";
          }
        }

        if (currentPageNum < totalPages) {
          html += '<a href="#" class="next">' + ">" + "</a>";
        }

        $(".board_page").html(html);
      },
    });
  }
});

function viewCnt(boardNum){
  $.ajax({
    type: "PUT",
    url: "/board/view/" + boardNum,
    contentType: "charset=utf-8"
  })
}
