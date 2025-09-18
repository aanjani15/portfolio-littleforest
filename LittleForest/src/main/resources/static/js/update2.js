const cupdatepopup = document.getElementById("cupdatepopup");
const closeq = document.querySelector("#cupdatepopup .close");
const cc2submit = document.getElementById("cc2submit");
const cc2comment = document.querySelector("#cupdatepopup .cc2comment");
const cc2ccid2 = document.getElementById("ccid").value;
console.log(cc2ccid2);

document.querySelectorAll(".upbtn").forEach(btn => {
	btn.onclick = (e) => {
		const commentItem = e.target.closest(".comment-item"); // 해당 댓글 요소
		const contentDiv = commentItem.querySelector(".comment-content"); // 댓글 내용
		cc2comment.value = contentDiv.textContent; // textarea에 내용 넣기
		cc2mid = commentItem.querySelector(".mid").value;
		cc2userId = commentItem.querySelector(".c2user_Id").value;
		cupdatepopup.style.display = "flex";
	}
});

closeq.onclick = () => {
	cupdatepopup.style.display = "none";
}

cc2submit.onclick = () => {
	const t2Comment = cc2comment.value;
	const formData3 = new FormData();
	formData3.append("id", cc2mid);
	formData3.append("user_Id", cc2userId);
	formData3.append("community_Id", cc2ccid2);
	formData3.append("comment_Id", t2Comment);
	fetch('/community/comment/update', {
		method: 'POST',
		body: formData3
	}).then(response => response.text())
		.then(data => {
			alert("수정성공");
			console.log(data);
			location.reload();
		})

}
document.querySelectorAll(".debtn").forEach(btn => {
	btn.onclick = (e) => {
		if (!confirm("삭제하시겠습니까?")) {
			return;
		}
		const commentItem2 = e.target.closest(".comment-item"); // 해당 댓글 요소
		cc2mid2 = commentItem2.querySelector(".mid").value;
		cc2userId2 = commentItem2.querySelector(".c2user_Id").value;
		const comment43 = cc2comment.value;
		const formData4 = new FormData();
		formData4.append("id", cc2mid2);
		formData4.append("user_Id", cc2userId2);
		formData4.append("community_Id", cc2ccid2);
		fetch('/community/comment/delete', {
			method: 'POST',
			body: formData4
		}).then(response => response.text())
			.then(data => {

				alert("삭제되었습니다")
				commentItem2.remove();
				location.reload();
			})

	}
})

