// 1. SDK 초기화 (카카오 REST API 앱 키 사용)
  window.Kakao.init("c2a49ae7428eee2fc07b7fb509ae845a"); 
  console.log("Kakao SDK 초기화 상태:", window.Kakao.isInitialized());

  // 2. 카카오 로그인 함수
  function kakaoLogin() {
      window.Kakao.Auth.login({
          scope: 'profile_nickname, profile_image, account_email',
          success: function(authObj) {
              console.log("✅ 로그인 성공:", authObj);


              // 3. 사용자 정보 요청
              window.Kakao.API.request({
                  url: '/v2/user/me',
                  success: res => {
					  const id = 'kakao_'+ res.id;
                      const kakao_account = res.kakao_account;
                      fetch('/checkemailandid', {
                          method: 'POST', 
						  headers: {
						  	'Content-Type': 'application/json',
						  	},
						  	body: JSON.stringify({ id: id, email: kakao_account.email})
                      })
                          .then(res => res.text())
                          .then(text => {
                              const intValue = parseInt(text, 10);
                              if (intValue == 1) { // 카카오로 로그인
								fetch('/loginkakao', {
									method: 'POST', 
									headers: {
									  'Content-Type': 'application/json',
									  },
									  body: JSON.stringify({ id: id, email: kakao_account.email })
								})
                                window.location.href = "/";
                              }
                              else { // 카카오 회원가입
								alert("가입되지 않은 이메일, 회원가입 페이지로 이동합니다.");

								fetch('/joinkakao/session', {
								  method: 'POST',
								  headers: {
								    'Content-Type': 'application/json',
								  },
								  body: JSON.stringify({ id: id, email: kakao_account.email })
								})
								window.location.href = '/joinkakao';
								  
                              } 
                          });

                  }
              })
                  .catch(err => {
                      console.error('서버 요청 오류:', err);
                  });
          }
      });
  }
