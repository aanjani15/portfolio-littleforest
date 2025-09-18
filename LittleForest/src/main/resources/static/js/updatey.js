const updatepopupy = document.getElementById("updatepopupy");
const pibclosey = document.querySelector("#updatepopupy .close");
const upbtn2 = document.getElementById("upbtn2");
const debtn2 = document.getElementById("debtn2");
const cupdbtnrey = document.getElementById("cupdbtnrey");
const cupdbtny = document.getElementById("cupdbtny");

var pibcidy = null;
var pibuidy = null;

if (upbtn2)
	upbtn2.addEventListener("click", async () => {
		const pibcardy = document.querySelector(".community-post"); // 단일 요소
		pibcidy = pibcardy.querySelector(".community_Id3").value;
		pibuidy = pibcardy.querySelector(".user_Id3").value;

		const pibtypey = document.getElementById("type3").textContent;
		const pibtitley = document.getElementById("title3").textContent;
		const pibcontenty = document.getElementById("content3").textContent;
		const pibimagey = document.getElementById("photo3")?.value || "";

		// 팝업 열기
		updatepopupy.style.display = "flex";

		// DOM 요소에 값 넣기
		document.getElementById("uuser_Idy").textContent = pibuidy;
		document.getElementById("utypey").value = pibtypey;
		document.getElementById("utitley").value = pibtitley;
		document.getElementById("ucontenty").value = pibcontenty;

		// 파일 input 초기화
		const fileInput = document.getElementById("uphotoy");
		fileInput.value = "";

		// 이미지 미리보기
		const imgPreview = document.getElementById("uphotopathy");
		imgPreview.src = pibimagey;
		imgPreview.style.display = pibimagey ? "block" : "none";
	});

// 초기화 버튼
cupdbtnrey.onclick = () => {
	document.getElementById("utypey").value = "";
	document.getElementById("utitley").value = "";
	document.getElementById("ucontenty").value = "";
	document.getElementById("uphotoy").value = "";
	document.getElementById("uphotopathy").src = "";
	document.getElementById("uphotopathy").style.display = "none";

};

// 팝업 닫기
pibclosey.onclick = () => {
	updatepopupy.style.display = "none";
};

// 글 실제 수정 요청
cupdbtny.addEventListener("click", async () => {
	const typeVal = document.getElementById("utypey").value;
	const titleVal = document.getElementById("utitley").value;
	const contentVal = document.getElementById("ucontenty").value;
	const fileInput = document.getElementById("uphotoy");
	let photoUrl = document.getElementById("uphotopathy").src; // img src 사용

	if (!typeVal) { alert("타입 선택해주세요"); return; }
	if (!titleVal || titleVal.length < 1 || titleVal.length > 20) { alert("제목 1~20자 입력"); return; }
	if (!contentVal || contentVal.length <= 1) { alert("글 2자 이상 입력"); return; }

	// 파일 업로드
	if (fileInput.files.length > 0) {
		const file = fileInput.files[0];
		const CLOUD_NAME = "dkaeihkco";
		const UPLOAD_PRESET = "community";
		const formDataImg = new FormData();
		formDataImg.append("upload_preset", UPLOAD_PRESET);
		formDataImg.append("file", file);

		const res = await fetch(`https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`, { method: "POST", body: formDataImg });
		if (!res.ok) {
			alert("이미지 업로드 실패");
			return;
		}
		const data = await res.json();
		photoUrl = data.secure_url;
	}

	const formData = new FormData();
	formData.append("id", pibcidy);
	formData.append("user_Id", pibuidy);
	formData.append("type", typeVal);
	formData.append("title", titleVal);
	formData.append("content", contentVal);
	formData.append("photoPath", photoUrl);

	fetch('/community/update', { method: 'POST', body: formData })
		.then(res => res.text())
		.then(data => {
			if (data === "success") {
				alert("수정 성공");
				updatepopupy.style.display = "none";
				location.reload();
			} else {
				alert("수정 실패");
			}
		})
		.catch(err => {
			console.error(err);
			alert("수정 중 오류 발생");
		});
});


debtn2.addEventListener("click", () => {
	if (!confirm("삭제하시겠습니까?")) {
		return;
	}
	const pibcardy2 = document.querySelector(".community-post"); // 단일 요소
	const pibcidd2 = pibcardy2.querySelector(".community_Id3").value;
	const pibuidd2 = pibcardy2.querySelector(".user_Id3").value;

	const formDatad2 = new FormData();
	formDatad2.append("id", pibcidd2);
	formDatad2.append("user_Id", pibuidd2);

	fetch('/community/delete', {
		method: 'POST',
		body: formDatad2
	})
		.then(response => response.text())
		.then(data => {
			if (data === 'success') {
				console.log(data)
				alert("삭제성공");
				location.href = "/community"
			}
		})
		.catch(error => {
			console.error("삭제 오류:", error);
			alert("삭제 실패");
		});
});

