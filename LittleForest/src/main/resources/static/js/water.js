const waterbtn = document.getElementById("waterbtn");
const waterStock3 = document.getElementById("water_Stock")
const waterjson = document.getElementById("waterjson");
const level = document.getElementById("tree_Level");
const biyrobtn3 = document.getElementById("biyrobtn");
const congrulationpopup = document.getElementById("congrulationpopup");
const closeBtnc = document.querySelector("#congrulationpopup .close");
const levelvalue = parseInt(level.textContent);
const waterCountc = document.getElementById("water_Count").value;
const stock0popupw = document.getElementById("stock0popupw");

if (levelvalue == 7) {
	waterbtn.disabled = true;
	biyrobtn3.disabled = true;
	congrulationpopup.style.display = "block";
}

if (closeBtnc) {
	closeBtnc.onclick = () => {
		congrulationpopup.style.display = "none";
	};
}
const bamboobar = document.getElementById("bambooBar");
const level2 = parseInt(level.textContent);
const needexp = [0, 5, 20, 50, 80, 130, 200];
const currentexp = waterCountc;
const levelnow = needexp[level2 - 1];
const levelnext = needexp[level2] || needexp[needexp.length - 1];
const needexp2 = currentexp - levelnow;
const needexp3 = levelnext - levelnow;
const maxlevel = needexp.length - 1;

bamboobar.innerHTML = '<div class="exp-text" id="expText"></div>';

for (let i = 0; i < maxlevel; i++) {
	const seg = document.createElement("div");
	seg.classList.add("segment");
	seg.style.flex = "1";

	if (i < level2 - 1) {
		seg.classList.add("filled");
	} else if (i === level2 - 1) {
		const fillPercent = needexp2 / needexp3;
		seg.style.background = `linear-gradient(to right, #2e7d32 ${fillPercent * 100}%, #ffffff ${fillPercent * 100}%)`;
	} else {
		seg.classList.add("empty");
	}
	bamboobar.appendChild(seg);
}

document.getElementById("expText").textContent = `${currentexp} / ${needexp[6]}`;


// === 물주기 버튼 ===
waterbtn.onclick = () => {
	const waterstock = parseInt(waterStock3.textContent);
	waterbtn.disabled = true;

	// 1. 보유 물 체크
	fetch('/growtree/water')
		.then(res => res.text()) // 문자열 응답
		.then(msg => {
			if (waterstock === 0 || msg.includes("보유 물")) {
				stock0popupw.style.display = "flex";
				document.getElementById("pointHtml5").innerHTML =
					`<h3 align='center'>물 재고가 부족합니다.</h3>`;
				document.getElementById("sucbtnb5").onclick = () => {
					stock0popupw.style.display = "none";
				};
				waterbtn.disabled = true;
				return;
			}

			// 2. 물 사용 (정상일 때)
			return fetch('/growtree/water2', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				},
				body: JSON.stringify({ water_Stock: waterstock - 1 })
			});
		})
		.then(res => {
			if (!res) return; // 위에서 이미 return 한 경우
			return res.json(); // water2 는 JSON 응답
		})
		.then(data => {
			if (!data) return;

			console.log("레벨:", data.treeLevel, "경험치:", data.exp);

			waterjson.style.display = "block";
			setTimeout(() => {
				waterjson.style.display = "none";
				window.location.reload();
				waterbtn.disabled = false;
			}, 2000);

			waterStock3.textContent = waterstock - 1;
		})
		.catch(err => {
			console.error("물 주기 중 오류:", err);
			waterbtn.disabled = false;
		});
};
