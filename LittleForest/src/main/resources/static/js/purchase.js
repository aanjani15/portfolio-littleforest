document.addEventListener("DOMContentLoaded", () => {
	const modal = document.getElementById("popupModal"); // 비료 구매 모달
	const buyBtn = document.getElementById("buy");       // "비료 구매" 버튼
	const closeBtn = document.querySelector(".close");   // 모달 닫기 버튼
	const purchase = document.getElementById("purchase");// 모달 내부 구매 버튼

	// 여러 요소들
	const biyroStock = document.getElementById("biyro_Stock");   // 보유 비료 (버튼 옆)
	const biyroStockm = document.getElementById("biyroStockm"); // 보유 비료 (모달 안)
	const point = document.getElementById("point");             // 보유 포인트
	const soo = document.getElementById("soo");                 // 수량 표시
	const minus = document.getElementById("minus");             // 수량 감소
	const plus = document.getElementById("plus");               // 수량 증가
	const price = document.getElementById("price");             // 가격 표시
	const levelif = document.getElementById("tree_Level");      // 현재 레벨

	// 결과 모달들
	const purpop = document.getElementById("purchasepopup"); // 구매 성공
	const lespop = document.getElementById("lessthenpopup"); // 포인트 부족

	// 요소가 없으면 실행 중단 (iftree=false일 때 방지)
	if (!modal || !purchase || !price || !point || !soo) {
		console.log("구매 모달 요소 없음 → purchase.js 실행 중단");

		return;
	}

	const levelint = levelif ? parseInt(levelif.textContent) : 0;
	console.log("현재 레벨:", levelint);

	let sales = 500;
	let gaesoo = 1;

	// 구매 모달 열기
	if (buyBtn) {
		buyBtn.onclick = () => {
			modal.style.display = "block";
		};
	}

	// 닫기 버튼
	if (closeBtn) {
		closeBtn.onclick = () => {
			modal.style.display = "none";
			if (levelint !== 7) {
				window.location.reload();
			}
		};
	}

	// 구매 버튼
	purchase.onclick = () => {
		const point2 = parseInt(point.textContent);
		const biyro2 = parseInt(biyroStock.textContent);
		const biyro2m = parseInt(biyroStockm.textContent);
		const price2 = parseInt(price.textContent);

		if (point2 >= price2) {
			// 구매 요청
			fetch('/growtree/purchase', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				},
				body: JSON.stringify({ quantity: gaesoo, totalprice: price2 })
			})
				.then(response => response.text())
				.then(() => {
					// 화면 갱신
					biyroStock.textContent = biyro2 + gaesoo;
					biyroStockm.textContent = biyro2m + gaesoo;
					point.textContent = point2 - price2;

					// 구매 성공 모달
					purpop.style.display = "flex";
					document.getElementById("pointHtml2").innerHTML =
						`<h3 align='center'>남은 포인트 : ${point.textContent}P</h3>`;

					// 확인 버튼
					document.getElementById("sucbtn2").onclick = () => {
						purpop.style.display = "none";
					};

					// 초기화
					gaesoo = 1;
					price.textContent = sales;
					soo.textContent = gaesoo;
				});
		} else {
			// 포인트 부족 모달
			lespop.style.display = "flex";
			document.getElementById("pointHtml3").innerHTML =
				`<h3 align='center'>보유 포인트 : ${point2}P</h3>
				 <h3 align='center'>가격 : ${price2}P</h3>`;

			document.getElementById("sucbtn4").onclick = () => {
				lespop.style.display = "none";
			};

			// 초기화
			gaesoo = 1;
			price.textContent = sales;
			soo.textContent = gaesoo;
		}
	};

	// 수량 감소
	if (minus) {
		minus.onclick = () => {
			if (gaesoo > 1) {
				gaesoo--;
				soo.textContent = gaesoo;
				price.textContent = sales * gaesoo;
			} else {
				alert("최소 한 개는 선택하셔야 합니다.");
			}
		};
	}

	// 수량 증가
	if (plus) {
		plus.onclick = () => {
			gaesoo++;
			soo.textContent = gaesoo;
			price.textContent = sales * gaesoo;
		};
	}
});
