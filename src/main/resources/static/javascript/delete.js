function listdelete() {
  const link = document.location.href;
  const temp = link.split(/[&=]/);
  const boardNum = temp[1];
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