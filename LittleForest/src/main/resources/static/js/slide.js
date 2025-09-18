const sliderWrapper = document.querySelector('.slider-wrapper');
const slides = document.querySelectorAll('.slide');

const slideCount = 3;  // 예시 슬라이드 개수
let currentIndex = 1;   // 1부터 시작
const slideWidth = sliderWrapper.clientWidth / slideCount; // 보통 슬라이드 1개 너비

let intervalId = null;

// 슬라이드 위치 업데이트 함수
function updateSlidePosition() {
	// currentIndex 가 1부터 시작하니까, 0-based 인덱스로 변환
	sliderWrapper.style.transform = `translateX(-${(currentIndex - 1) * 100}%)`;
}


// 다음 슬라이드로 이동
function nextSlide() {
	currentIndex++;
	if (currentIndex > slideCount) {
		currentIndex = 1;
	}
	updateSlidePosition();
}



// 자동 슬라이드 시작
function startAutoSlide() {
	if (intervalId) return; // 중복 방지
	intervalId = setInterval(nextSlide, 3000); // 3초마다 슬라이드 전환
}

// 자동 슬라이드 정지
function stopAutoSlide() {
	clearInterval(intervalId);
	intervalId = null;
}

// 마우스 올리면 슬라이드 멈춤
sliderWrapper.addEventListener('mouseenter', stopAutoSlide);

// 마우스 떠나면 슬라이드 다시 시작
sliderWrapper.addEventListener('mouseleave', startAutoSlide);

// 초기 세팅
updateSlidePosition();
startAutoSlide();