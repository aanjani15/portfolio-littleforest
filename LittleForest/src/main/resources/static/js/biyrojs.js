const container = document.getElementById('soil-container');
const soils = container.querySelectorAll('.soil');

soils.forEach((soil) => {
	let size = 150;
	soil.style.width = size + 'px';
	soil.style.height = size + 'px';
	soil.style.left = "0%";
	soil.style.top = "50%";

	// 이미지 뒤집기만 CSS로 적용
	soil.style.transform = "rotate(180deg)";

	anime({
		targets: soil,
		translateY: -400,  // 아래로 이동
		duration: 2200, //2.2초동안 플레이
		easing: 'linear',
		loop: true,
		// complete에서는 translateY만 초기화, rotate는 유지
		complete: function() {
			soil.style.transform = "rotate(180deg)";
			soil.style.translateY = "0px"; // 애니메이션 위치 초기화
		}
	});

});
