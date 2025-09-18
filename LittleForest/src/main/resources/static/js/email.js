// 이메일 인증 코드 요청 함수
    function requestEmailCode() {
      const emailInput = document.getElementById('email');
      const email = emailInput.value.trim();

      if (!email) {
        alert('이메일을 입력해주세요.');
        emailInput.focus();
        return;
      }

      fetch('/sign-up/emailCheck', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email: email })
      })
      .then(response => response.text())
      .then(authCode => {
        alert('인증 코드가 이메일로 전송되었습니다.');
        console.log('서버에서 받은 인증 코드:', authCode);
        document.getElementById('emailCode').value = '';
      })
      .catch(err => {
        alert('이메일 인증 요청 중 오류가 발생했습니다.');
        console.error(err);
      });
    }