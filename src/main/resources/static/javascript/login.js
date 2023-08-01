const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
  container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
  container.classList.remove("right-panel-active");
});

function signUp() {
  let userId = $('.registerId').val();
  let password = $('.registerPassword').val();
  let username= $('.registerUsername').val();
  let email = $('.registerEmail').val();

  // 입력값이 비어있는지 확인
  if (username.trim() === "" || userId.trim() === "" || password.trim() === "" || email.trim() === "") {
      alert('필수입력란이 비어있습니다.');
      return;
  }

  // 회원가입에 필요한 데이터 생성
  let data = { userId, password, username, email }

  // 서버로 데이터 전송
  ajaxForm('POST', '/member', data, function (result) {
      alert('회원가입 완료');
      location.reload();
  });

}

function login() {

  alert('로그인 시도.');

  let userId = $('.loginId').val();
  let password = $('.loginPassword').val();
  let url = '/id/duplication/' + userId;

  let data = { userId, password };

  alert('ajax 직전');

  ajaxForm('GET', url, null, 
  function (result) {
    alert('ajax 하나 지남');
      if (result) {
          alert('아이디가 존재하지 않습니다.');
          $('.loginId').val('');
          $('.loginPassword').val('');
          $('.loginId').focus();
          return;
      } else {
          ajaxForm('POST', '/login', data,
              function (result) {
                alert('ajax 두개지나고 성공');
                  // 로그인 성공시 로컬스토리지에 토큰 저장함
                  localStorage.setItem("Authorization", result.Authorization);
                  alert('로그인 성공!');
                  window.location.href = '/list'
              },
              function (error) {
                  alert('ajax 두개지나고 오류');
                  alert('비밀번호가 일치하지 않습니다.');
                  $('.loginPassword').val('');
                  $('.loginPassword').focus();
                  return;
              });
          }
      });
}

function ajaxForm(type, url, data, successCallback, errorCallback) {
  $.ajax({
      type: type,
      url: url,
      dataType: 'JSON',
      contentType: 'application/json; charset=UTF-8',
      data: JSON.stringify(data),
      success: successCallback,
      error: errorCallback
  });
}