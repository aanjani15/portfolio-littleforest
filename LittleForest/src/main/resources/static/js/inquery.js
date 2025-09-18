const goodgood = document.getElementById("goodgood");
document.querySelectorAll(".inqueryU").forEach(btn => {
	btn.onclick = (e) => {
		const inqueryu = e.target.closest(".YourOneInquery");
		const inqueryId1 = inqueryu.querySelector(".inqueryId").value;
		const inqueryUserId1 = inqueryu.querySelector(".inqueryUserId").value;
		const inqueryData1 = new FormData();
		inqueryData1.append("id", inqueryId1);
		inqueryData1.append("user_Id", inqueryUserId1);
		fetch("contact/one", {
			method: 'POST',
			body: inqueryData1
		}).then(res => res.text())
			.then(datas => {
				const data = JSON.parse(datas);
				console.log(data.category)
				document.getElementById("inqueryUPopup").style.display = "flex";
				document.getElementById("iid").textContent = data.id;
				document.getElementById("iuserId").textContent = data.user_Id;
				document.getElementById("inickname").textContent = data.nickname;
				document.getElementById("icategory").value = data.category;
				document.getElementById("ititle").value = data.title;
				document.getElementById("icontent").value = data.content;

			})

	}
})
const inqueryUpopup = document.getElementById("inqueryUPopup");
const inqueryClose = inqueryUpopup.querySelector(".close");
inqueryClose.addEventListener("click", () => {
	inqueryUpopup.style.display = "none";
	document.getElementById("iid").textContent = "";
	document.getElementById("icategory").value = "";
	document.getElementById("ititle").value = "";
	document.getElementById("icontent").value = "";

})
document.querySelectorAll(".inqueryD").forEach(btn => {
	btn.onclick = (e) => {
		if (!confirm("삭제하시겠습니까?")) {
			return;
		}
		const inquerys = e.target.closest(".YourOneInquery");
		const inqueryId = inquerys.querySelector(".inqueryId").value;
		const inqueryUserId = inquerys.querySelector(".inqueryUserId").value;
		const inqueryData = new FormData();
		inqueryData.append("id", inqueryId);
		inqueryData.append("user_Id", inqueryUserId);
		fetch("/contact/delete", {
			method: 'POST',
			body: inqueryData
		}).then(res => res.text())
			.then(data => {
				if (data == "success") {
					goodgood.style.display = "flex";
					document.getElementById("goodMessage").innerHTML = `<p align='center'>문의 삭제에 성공했습니다.</p>`;
					inquerys.remove();
				}
			})

	}
})
document.getElementById("resetreset").addEventListener("click", () => {
	document.getElementById("iid").textContent = "";
	document.getElementById("icategory").value = "";
	document.getElementById("ititle").value = "";
	document.getElementById("icontent").value = "";
})
document.getElementById("readupdater").addEventListener("mouseover", () => {
	document.getElementById("htmlupdater").innerHTML = `<h3 align='center'>초기화선택시 내용 없어짐(x누르고 돌아오면 원상태)</h3>` + `<h3 align='center'>불필요한 문의할 시 문의 제한될 수 있습니다!</h3>` +
		`<h3 align='center'>비속어 사용 금지합니다!</h3>`
})
document.getElementById("readupdater").addEventListener("mouseout", () => {
	document.getElementById("htmlupdater").innerHTML = ""
})
document.getElementById("inqueryUp").addEventListener("click", () => {
	const inid = document.getElementById("iid").textContent;
	const inuserId = document.getElementById("iuserId").textContent;
	const incategory = document.getElementById("icategory").value;
	const intitle = document.getElementById("ititle").value;
	const incontent = document.getElementById("icontent").value;
	if (intitle.length == 0) {
		alert("제목을 입력해주세요")
		return;
	}
	if (incontent.length == 0) {
		alert("글은 5자이상 입력해주세요")
		return;
	}
	if (incategory == "") {
		alert("카테고리를 입력해주세요")
		return;
	}
	const inData = new FormData();
	inData.append("id", inid);
	inData.append("user_Id", inuserId);
	inData.append("category", incategory);
	inData.append("title", intitle);
	inData.append("content", incontent);
	fetch("contact/update", {
		method: 'POST',
		body: inData
	}).then(res => res.text())
		.then(data => {
			if (data == "success") {
				goodgood.style.display = "flex";
				document.getElementById("goodMessage").innerHTML = `<p align='center'>문의 수정에 성공했습니다.</p>`;
			}
		})
})
const inqueryIPopup = document.getElementById("inqueryIPopup");
const inqueryIClose = inqueryIPopup.querySelector(".close");
inqueryI.onclick = () => {
	inqueryIPopup.style.display = "flex";
}
inqueryIClose.onclick = () => {
	inqueryIPopup.style.display = "none";
}
document.getElementById("readregister").addEventListener("mouseover", () => {
	document.getElementById("htmlregister").innerHTML = `<h3 align='center'>카테고리 선택 필수!!(없을시 기타로 입력)</h3>` +
		`<h3 align='center'>제목과 내용 입력 필수</h3>` + `<h3 align='center'>불필요한 문의할 시 문의 제한될 수 있습니다!</h3>` +
		`<h3 align='center'>비속어 사용 금지합니다!</h3>`
})
document.getElementById("readregister").addEventListener("mouseout", () => {
	document.getElementById("htmlregister").innerHTML = ""
})
document.getElementById("inqueryIn").addEventListener("click", () => {
	const iqcategory = document.getElementById("iqcategory").value;
	const iqtitle = document.getElementById("iqtitle").value;
	const iqcontent = document.getElementById("iqcontent").value;
	if (iqcategory == "") {
		alert("제목입려좀")
		return;
	}
	if (iqtitle.length == 0) {
		alert("제목입력좀")
		return;
	}
	if (iqcontent.length == 0) {
		alert("글입력좀")
		return;
	}
	const ain = new FormData();
	ain.append("category", iqcategory);
	ain.append("title", iqtitle);
	ain.append("content", iqcontent);
	fetch("/contact/insert", {
		method: 'POST',
		body: ain
	}).then(res => res.text())
		.then(wow => {
			if (wow == "success") {
				goodgood.style.display = "flex";
				document.getElementById("goodMessage").innerHTML = `<p align='center'>문의 등록에 성공했습니다.</p>`;

			}
		})
		.catch(err => {
			if (err == "error") {
				console.log(err);
			}
		})
})
const carefulPopup = document.getElementById("carefulPopup");
document.getElementById("careful").addEventListener("click", () => {
	carefulPopup.style.display = "flex";
})
const carefulClose = carefulPopup.querySelector(".close");
carefulClose.addEventListener("click", () => {
	carefulPopup.style.display = "none";
})
document.getElementById("goodDelete").addEventListener("click", () => {
	goodgood.style.display = "none";
	document.getElementById("goodMessage").innerHTML = "";
	location.reload();
})
document.querySelectorAll(".answerAdminBtn").forEach(btn => {
	btn.addEventListener("click", (e) => {
		const inqueryu0 = e.target.closest(".YourOneInquery");
		const inqueryId0 = inqueryu0.querySelector(".inqueryId").value;
		const answera0 = e.target.closest(".oneAnswer");
		const answerId = answera0.querySelector(".aid").value;
		document.getElementById("jonglo3").style.display = "flex";
		document.querySelector("#jonglo3 .close").addEventListener("click", () => {
			document.getElementById("jonglo3").style.display = "none";
			document.getElementById("answerContent").value = "";
		})
		const answerToAnswer = document.getElementById("answerToAnswer");
		answerToAnswer.addEventListener("click", () => {
			const answerContent = document.getElementById("answerContent").value;
			if (answerContent.length == 0) {
				alert("답변을 1자이상 입력해주세요")
				return;
			}
			const answerData = new FormData();
			answerData.append("inquery_Id", inqueryId0);
			answerData.append("answer_Id", answerId);
			answerData.append("content", answerContent);
			fetch("/contact/answer/insert", {
				method: 'POST',
				body: answerData
			}).then(res => res.text())
				.then(data => {
					if (data == "success") {
						console.log("답변등록성공")
					}
				})
		})
	})
})