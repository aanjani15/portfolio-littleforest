document.addEventListener('DOMContentLoaded', () => {
	const ranLevel = document.getElementById("tree_Level");
	const parseLevel = parseInt(ranLevel.textContent);
	if (parseLevel == 7) {
		return;
	}
	const ranpop = document.getElementById("randomeventpopup");
	const meadow = document.querySelector(".meadow");
	const random = Math.random();
	const rwaterbtn = document.getElementById("waterbtn");
	const rbiyrobtn = document.getElementById("biyrobtn");
	if (random < 0.22) {
		const meadow2 = 400;
		for (var i = 1; i < meadow2; i++) {
			const weed = document.createElement('div');
			weed.classList.add('weed');
			const x = Math.random() * 100 + "%";
			const h = 50 + Math.random() * 60 + 'px'; // 높이 랜덤
			const delay = Math.random() * 2 + 's';
			weed.style.setProperty('--x', x);
			weed.style.setProperty('--h', h);
			weed.style.setProperty('--delay', delay);
			const leaf = document.createElement('span');
			leaf.classList.add('leaf');
			weed.appendChild(leaf);

			meadow.appendChild(weed);
		}
		rwaterbtn.disabled = true;
		rbiyrobtn.disabled = true;
		setTimeout(() => { ranpop.style.display = "flex" }, 500);
		// 1. F5, Ctrl+R 막기
		/*document.addEventListener("keydown", function(e) {
			if (e.key === "F5" || (e.ctrlKey && (e.key === "r" || e.key === "R"))) {
				e.preventDefault();
			}
		});

		// 2. 마우스 우클릭 새로고침 막기
		document.addEventListener("contextmenu", function(e) {
			e.preventDefault();
		});*/

		const adbtn = document.getElementById("adbtn");
		const allmeadow = meadow.querySelectorAll(".weed");
		const completepopup = document.getElementById("completepopup");
		const sucbtn = document.getElementById("sucbtn");
		adbtn.onclick = () => {
			window.open('https://sites.google.com/view/littleforestad');
			ranpop.style.display = "none";
			setTimeout(() => {
				setTimeout(() => { allmeadow.forEach(weed => meadow.removeChild(weed)) }, 5000);
			}, 7000);
			rwaterbtn.disabled = false;
			rbiyrobtn.disabled = false;

		}
		const rembtn = document.getElementById("removebtn");
		const point0 = document.getElementById("point0");
		rembtn.onclick = () => {
			const pointContent = parseInt(point0.textContent);
			if (pointContent < 100) {
				alert("포인트가 부족합니다<br> 광고보기를 클릭해주세요.")
				return;
			}
			const formData20 = new FormData();
			formData20.append("point", pointContent);
			fetch("/growtree/happen", {
				method: 'POST',
				body: formData20
			}).then(response => response.text()).then(() => {
				completepopup.style.display = "flex";
				document.getElementById("pointHtml").innerHTML = `<h3>결제 포인트 : 100P</h3>` + `<h3>남은 보유 포인트 : ${pointContent - 100}P</h3>`;
				sucbtn.onclick = () => {
					completepopup.style.display = "none";
					ranpop.style.display = "none";
					setTimeout(() => { allmeadow.forEach(weed => meadow.removeChild(weed)) }, 5000);
					rwaterbtn.disabled = false;
					rbiyrobtn.disabled = false;
				}
			})
			point0.textContent = pointContent - 100;
		}
		const ranclose = ranpop.querySelector(".close");

		ranclose.onclick = () => {
			alert("잡초를 제거해야합니다.")
			return;
		}
	}
	else {
		return;
	}
})
