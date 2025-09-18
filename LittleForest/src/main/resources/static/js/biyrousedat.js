/*const biyrobtn = document.getElementById("biyrobtn");
const biyroStocka = document.getElementById("biyro_Stock");
const biyroTimer = document.getElementById("biyrotimer");
<<<<<<< HEAD
const biyroani = document.getElementById("soil-container");
const waterbtn00 = document.getElementById("waterbtn");
const stock0popup = document.getElementById("stock0popup");

function time(a) {
	return a < 10 ? '0' + a : a;
}

function timereload() {
	const biyroUsedAtaValue = document.getElementById("biyro_Used_At").value;
	if (!biyroUsedAtaValue) {
		biyroTimer.style.display = "none";
=======
const biyroani=document.getElementById("soil-container");
const waterbtn00=document.getElementById("waterbtn");

function time(a){
	return a < 10 ? '0' + a : a;
}

function timereload(){
	const biyroUsedAtaValue = document.getElementById("biyro_Used_At").value;
	if(!biyroUsedAtaValue) {
		biyroTimer.style.display="none";
>>>>>>> jinhee
		return;
	}
	const biyrostart = new Date(biyroUsedAtaValue);
	const biyroend = new Date(biyrostart);
	biyroend.setDate(biyroend.getDate() + 3);
<<<<<<< HEAD

	const now = new Date();
	const between = biyroend - now;

	if (between <= 0) {
		biyroTimer.style.display = "none";
=======
	
	const now = new Date();
	const between = biyroend - now;

	if(between <= 0){
		biyroTimer.style.display="none";
>>>>>>> jinhee
		return;
	}

	const totalSeconds = Math.floor(between / 1000);
	const hours = Math.floor(totalSeconds / 3600);
	const minutes = Math.floor((totalSeconds % 3600) / 60);
	const seconds = totalSeconds % 60;

	biyroTimer.textContent = `${time(hours)}:${time(minutes)}:${time(seconds)}`;
}

setInterval(timereload, 1000);
timereload();

biyrobtn.onclick = () => {
	let stocka = parseInt(biyroStocka.textContent);

<<<<<<< HEAD
	if (stocka === 0) {
		stock0popup.style.display = "flex";
		document.getElementById("pointHtml4").innerHTML = `<h3 align='center'>확인 버튼을 누르시고 <b>비료 구매</b> 버튼을 눌러주세요</h3>`
			+ `<h5 align='center'>이제 버튼이 비활성화 됩니다.</h5>`;
		document.getElementById("sucbtnb2").onclick = () => {
			stock0popup.style.display = "none";
		}
		biyrobtn.disabled = true;
		return;
	}

	const biyroUsedAtaValue = document.getElementById("biyro_Used_At").value;
	if (biyroUsedAtaValue) {
		const biyrostart = new Date(biyroUsedAtaValue);
		const biyroend = new Date(biyrostart);
		biyroend.setDate(biyroend.getDate() + 3); // 3일 제한
		if (new Date() < biyroend) {
			alert("아직 비료 재사용 시간이 되지 않았습니다.");
			return; // 타이머 리셋 방지
		}
	}


	fetch('/growtree/biyro', {
=======
	if(stocka === 0){
		alert("비료 재고가 없습니다.");
		return;
	}

	fetch('/biyro', {
>>>>>>> jinhee
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json'
		},
<<<<<<< HEAD
		body: JSON.stringify({ biyro_Stock: stocka })
	})
		.then(response => response.text())
		.then(data => {
			if (data === "성공") {
				stocka = stocka - 1;
				biyroStocka.textContent = stocka;
				biyroani.style.display = "block";
				waterbtn00.disabled = true;
				biyrobtn.disabled = true;
				setTimeout(() => {
					biyroani.style.display = "none";
					waterbtn00.disabled = false;
					// biyrobtn.disabled = false;
				}, 2000);
				// 현재시간 포맷팅 (yyyy-MM-dd HH:mm:ss)
				const now = new Date();
				const formattedNow = `${now.getFullYear()}-${time(now.getMonth() + 1)}-${time(now.getDate())} ${time(now.getHours())}:${time(now.getMinutes())}:${time(now.getSeconds())}`;

				// 숨겨진 input에 업데이트
				document.getElementById("biyro_Used_At").value = formattedNow;
				biyroTimer.style.display = "block";
				// 타이머 리셋
				timereload();

				alert("비료 사용 성공!");

			} else {
				alert("서버 응답: " + data);
			}
		})
		.catch(err => {
			console.error("비료 사용 중 오류:", err);
			alert("비료 사용 중 오류가 발생했습니다.");
		});
}*/

const biyrobtn = document.getElementById("biyrobtn");
const biyroStocka = document.getElementById("biyro_Stock");
const biyroTimer = document.getElementById("biyrotimer");
const biyroani = document.getElementById("soil-container");
const waterbtn00 = document.getElementById("waterbtn");
const stock0popup = document.getElementById("stock0popup");

// 새로 추가된 모달
const biyroLimitModal = document.getElementById("biyroLimitModal");
const biyroLimitOk = document.getElementById("biyroLimitOk");

function time(a) {
	return a < 10 ? '0' + a : a;
}

function timereload() {
	const biyroUsedAtaValue = document.getElementById("biyro_Used_At").value;
	if (!biyroUsedAtaValue) {
		biyroTimer.style.display = "none";
		return;
	}
	const biyrostart = new Date(biyroUsedAtaValue);
	const biyroend = new Date(biyrostart);
	biyroend.setDate(biyroend.getDate() + 3);

	const now = new Date();
	const between = biyroend - now;

	if (between <= 0) {
		biyroTimer.style.display = "none";
		return;
	}

	const totalSeconds = Math.floor(between / 1000);
	const hours = Math.floor(totalSeconds / 3600);
	const minutes = Math.floor((totalSeconds % 3600) / 60);
	const seconds = totalSeconds % 60;

	biyroTimer.textContent = `${time(hours)}:${time(minutes)}:${time(seconds)}`;
}

setInterval(timereload, 1000);
timereload();

biyrobtn.onclick = () => {
	let stocka = parseInt(biyroStocka.textContent);

	// 재고 부족 처리
	if (stocka === 0) {
		stock0popup.style.display = "flex";
		document.getElementById("pointHtml4").innerHTML =
			`<h3 align='center'>확인 버튼을 누르시고 <b>비료 구매</b> 버튼을 눌러주세요</h3>`
			+ `<h5 align='center'>이제 버튼이 비활성화 됩니다.</h5>`;
		document.getElementById("sucbtnb2").onclick = () => {
			stock0popup.style.display = "none";
		};
		biyrobtn.disabled = true;
		return;
	}

	// 쿨타임 확인
	const biyroUsedAtaValue = document.getElementById("biyro_Used_At").value;
	if (biyroUsedAtaValue) {
		const biyrostart = new Date(biyroUsedAtaValue);
		const biyroend = new Date(biyrostart);
		biyroend.setDate(biyroend.getDate() + 3); // 3일 제한
		if (new Date() < biyroend) {
			// ❌ alert 대신 모달 표시
			biyroLimitModal.style.display = "flex";
			return;
		}
	}

	// 서버 요청
	fetch('/growtree/biyro', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json'
		},
		body: JSON.stringify({ biyro_Stock: stocka })
	})
		.then(response => response.text())
		.then(data => {
			if (data === "성공") {
				stocka = stocka - 1;
				biyroStocka.textContent = stocka;
				biyroani.style.display = "block";
				waterbtn00.disabled = true;
				biyrobtn.disabled = true;
				setTimeout(() => {
					biyroani.style.display = "none";
					waterbtn00.disabled = false;

				}, 2000);

				// 현재시간 포맷팅 (yyyy-MM-dd HH:mm:ss)
				const now = new Date();
				const formattedNow = `${now.getFullYear()}-${time(now.getMonth() + 1)}-${time(now.getDate())} ${time(now.getHours())}:${time(now.getMinutes())}:${time(now.getSeconds())}`;

				// 숨겨진 input에 업데이트
				document.getElementById("biyro_Used_At").value = formattedNow;
				biyroTimer.style.display = "block";
				timereload();

				// 성공 메시지 (모달로 바꾸고 싶으면 여기도 가능)

			} else {
				alert("서버 응답: " + data);
			}
		})
		.catch(err => {
			console.error("비료 사용 중 오류:", err);
			alert("비료 사용 중 오류가 발생했습니다.");
		});
};

// 모달 닫기 버튼
if (biyroLimitOk) {
	biyroLimitOk.onclick = () => {
		biyroLimitModal.style.display = "none";
	};
}
