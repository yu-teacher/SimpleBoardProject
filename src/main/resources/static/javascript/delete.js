function listdelete() {
    let link = document.location.href;
    let boardNum = link.split("=")[1];
    $.ajax({
      type: "DELETE",
      url: "/board/" + boardNum,
      data: JSON.stringify(boardNum),
      contentType: "application/json",
      success: function () {
        window.location.href = "/list";
      },
    });
  }