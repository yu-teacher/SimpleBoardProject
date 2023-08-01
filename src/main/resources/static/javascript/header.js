function header_load() {

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
        $.ajax({
          type: 'GET',
          url: '/header',
          dataType: 'text',
          success: function(response) {
            $('#head').html(response);
            $(".log").text('LogOut');
            $('.log').click(function() {
              window.location.href = '/login';
            });
          }
      });
      },
      error: function (request, status, error) {
        $.ajax({
          type: 'GET',
          url: '/header',
          dataType: 'text',
          success: function(response) {
            $('#head').html(response);
            $(".log").text('LogIn');
            $('.log').click(function() {
              logout();
            });
          }
      });
      }
  });
}

function logout() {
  localStorage.setItem("Authorization", "");
  window.location.href = '/login';
}

$(document).ready(function () {
  header_load();
});