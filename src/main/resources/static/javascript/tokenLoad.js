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
        const loginUserNum = result.userNum;
      }
  });
}

function logout() {
  localStorage.removeItem('Authorization');
  window.location.href = '/login'
}

$(document).ready(function () {
  authCheck();
});