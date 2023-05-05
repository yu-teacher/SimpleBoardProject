function register() {
  let title = $(".text_title").val();
  let content = $(".text_content").val();

  if (title === "" || content === "") { 
    if (title === "") {
      $(".text_title").focus();  
    } else {
      $(".text_content").focus(); 
    }
    return;  
  }

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
      window.location.href = "/list";
    },
  });
}