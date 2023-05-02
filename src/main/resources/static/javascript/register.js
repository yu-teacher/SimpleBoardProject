function register() {
  let title = $(".text_title").val();
  let content = $(".text_content").val();
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
      location.href = location.host + "/list";
    },
  });
}