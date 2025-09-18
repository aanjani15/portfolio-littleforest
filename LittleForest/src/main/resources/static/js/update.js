const updatepopup = document.getElementById("updatepopup");
const pibclose = document.querySelector("#updatepopup .close");

document.querySelectorAll(".upbtn1").forEach(btn => {
	btn.onclick = (e) => {
		const pibcard = e.target.closest(".post-card");
		const pibcid = pibcard.querySelector(".community_Id").value;
		const pibuid = pibcard.querySelector(".user_Id").value;
		const pibtype = pibcard.querySelector(".post-type").textContent;
		const pibtitle = pibcard.querySelector(".post-tytle").textContent;
		const pibcontent = pibcard.querySelector(".post-content").textContent;
		const pibimage = pibcard.querySelector(".post-image img")?.src || "";
		const photopph = document.getElementById("uphotopath");

		if (pibimage) {
			photopph.src = pibimage;
			photopph.style.display = "block";
		} else {
			photopph.src = "";
			photopph.style.display = "none";
		}

		// 팝업 열기
		updatepopup.style.display = "flex";

		// DOM 요소에 값 넣기
		document.getElementById("uuser_Id").textContent = pibuid;
		document.getElementById("utype").value = pibtype;
		document.getElementById("utitle").value = pibtitle;
		document.getElementById("ucontent").value = pibcontent;
		document.getElementById("uphoto").src = pibimage;

		const cupdbtnre = document.getElementById("cupdbtnre");
		const cupdbtn = document.getElementById("cupdbtn");

		cupdbtnre.onclick = () => {
			document.getElementById("utype").value = "";
			document.getElementById("utitle").value = "";
			document.getElementById("ucontent").value = "";
			document.getElementById("uphoto").value = "";
		};

		cupdbtn.onclick = async () => {
			const ll1 = document.getElementById("utype").value;
			const ll2 = document.getElementById("utitle").value;
			const ll3 = document.getElementById("ucontent").value;
			const ll4 = document.getElementById("uphoto");

			if (!ll1) { alert("타입을 선택해주세요"); return; }
			if (!ll2) { alert("제목을 입력해주세요"); return; }
			if (ll2.length < 1 || ll2.length > 20) { alert("제목은 1~20자 사이로 입력해주세요"); return; }
			if (ll3.length <= 1) { alert("글은 2자 이상 입력해주세요"); return; }

			let lll = null;

			if (ll4.files.length > 0) {
				const file2 = ll4.files[0];
				const CLOUD_NAME2 = "dkaeihkco";
				const UPLOAD_PRESET2 = "community";
				const url2 = `https://api.cloudinary.com/v1_1/${CLOUD_NAME2}/image/upload`;

				const formDataImg1 = new FormData();
				formDataImg1.append("upload_preset", UPLOAD_PRESET2);
				formDataImg1.append("file", file2);

				const res2 = await fetch(url2, { method: "POST", body: formDataImg1 });
				if (!res2.ok) {
					const errData = await res2.json();
					console.error("Cloudinary 에러 응답:", errData);
					alert("이미지 업로드 실패");
					return;
				}

				const data = await res2.json();
				lll = data.secure_url; // 업로드 후 URL
			} else if (ll4.files.length == 0) {
				lll = pibimage; // 기존 이미지 유지
			}

			const formDatall = new FormData();
			formDatall.append("id", pibcid);
			formDatall.append("user_Id", pibuid);
			formDatall.append("type", ll1);
			formDatall.append("title", ll2);
			formDatall.append("content", ll3);
			formDatall.append("photoPath", lll);

			fetch('/community/update', {
				method: 'POST',
				body: formDatall
			}).then(response => response.text())
				.then(data => {
					if (data === "success") {
						alert("수정 성공");
						updatepopup.style.display = "none";
						location.reload();
					} else {
						alert("수정 실패");
					}
				}).catch(err => {
					console.error(err);
					alert("수정 중 오류 발생");
				});
		};
	};
});

pibclose.onclick = () => {
	updatepopup.style.display = "none";
};

document.querySelectorAll(".debtn1").forEach(btn => {
	btn.addEventListener("click", function(e) {
		if (!confirm("삭제하시겠습니까?")) return;

		const pibcardd = e.target.closest(".post-card");
		const pibcidd = pibcardd.querySelector(".community_Id").value;
		const pibuidd = pibcardd.querySelector(".user_Id").value;

		const formDatad = new FormData();
		formDatad.append("id", pibcidd);
		formDatad.append("user_Id", pibuidd);

		fetch('/community/delete', {
			method: 'POST',
			body: formDatad
		})
			.then(response => response.text())
			.then(data => {
				if (data === 'success') {
					console.log(data)
					alert("삭제성공");
					location.reload();
				}
			})
			.catch(error => {
				console.error("삭제 오류:", error);
				alert("삭제 실패");
			});
	});

});
