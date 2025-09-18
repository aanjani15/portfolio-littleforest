document.addEventListener('DOMContentLoaded', () => {
	const seedmodal = document.getElementById("popupseed");   // 씨앗 심기 모달
	const seedBtn = document.getElementById("seed");          // 씨앗 심기 버튼
	const closebtn2 = document.querySelector("#popupseed .close");
	const treenamebtn = document.getElementById("treenaming"); // 이름 확정 버튼

	// 씨앗 심기 버튼 클릭 → 모달 열기
	if (seedBtn && seedmodal) {
		seedBtn.onclick = () => {
			seedmodal.style.display = "block";
		};
	}

	// 닫기 버튼 클릭 → 모달 닫기
	if (closebtn2 && seedmodal) {
		closebtn2.onclick = () => {
			seedmodal.style.display = "none";
		};
	}

	// 이름 확정 버튼 클릭
	if (treenamebtn) {
		treenamebtn.onclick = () => {
			// 입력값 가져오기 (null 안전)
			let treeName = (document.getElementById("tree_Name")?.value || "").trim();

			// 길이 검증
			if (treeName.length < 1 || treeName.length > 5) {
				alert("나무 이름은 1 ~ 5 자 사이로 입력해주세요.");
				return;
			}

			// "나무" 자동 붙이기
			if (!treeName.includes("나무")) treeName += "나무";

			// 서버에 등록 요청
			fetch('/growtree/plant', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
				body: JSON.stringify({ tree_Name: treeName })
			})
				.then(r => r.text())
				.then(data => {
					alert("등록 성공했습니다.");
					if (seedmodal) seedmodal.style.display = "none";
					console.log(data)
					location.reload(); // 새로고침
				})
				.catch(err => {
					console.error(err);
					alert("등록 중 오류가 발생했습니다.");
				});
		};
	}
});
