function openPostcode() {
				  // 팝업 창 크기 설정
				  var width = 500; // 가로 크기
				  var height = 600; // 세로 크기

				  // 화면 중앙 좌표 계산
				  var left = (window.innerWidth / 2) - (width / 2);
				  var top = (window.innerHeight / 2) - (height / 2);

				  new daum.Postcode({
				    oncomplete: function(data) {
				      // 선택한 주소를 address input에 넣기
				      document.getElementById('address').value = data.address;
				    }
				  }).open({
				    left: left,
				    top: top,
				    width: width,
				    height: height
				  });
				}