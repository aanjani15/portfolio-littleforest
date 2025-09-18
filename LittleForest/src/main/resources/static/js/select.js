const selectpopup = document.getElementById("selectpopup");
const subcomment = document.getElementById("subcomment");
function status(card) {
	const communityid9 = card.querySelector(".community_Id").value;
	console.log(communityid9);
	fetch(`/community/status?id=${communityid9}`)
		.then(response => response.json())
		.then(data => {
			document.getElementById("s2nickname").textContent = "닉네임 :" + data.nickname;
			document.getElementById("s2type").textContent = "타입 : " + data.type;
			document.getElementById("s2likes").textContent = "좋아요 수 : " + data.likes;
			document.getElementById("s2title").textContent = "제목 : " + data.title;
			document.getElementById("s2content").textContent = "내용 : " + data.content;

			const d = new Date(data.created_At);
			var y1 = d.getFullYear();
			var m1 = String(d.getMonth() + 1).padStart(2, '0');
			var d1 = String(d.getDate()).padStart(2, '0');
			var h1 = String(d.getHours()).padStart(2, '0');
			var m2 = String(d.getMinutes()).padStart(2, '0');
			document.getElementById("s2date").textContent = `날짜 : ${y1}-${m1}-${d1} ${h1}:${m2}`;
			selectpopup.style.display = "block";
		})
	// 댓글 가져오기
	fetch(`/community/comment?community_Id=${communityid9}`)
		.then(response => response.json())
		.then(comments => {
			console.log(comments);
			subcomment.innerHTML = "";
			if (comments.length === 0) {
				subcomment.innerHTML = "<p align='center'>댓글이 없습니다.</p>" +
					"<div style='text-align:center;'><button class='idmore2'>자세히 보기</button></div>";
				const mbtn2 = subcomment.querySelector(".idmore2");
				if (mbtn2) {
					mbtn2.onclick = () => {
						window.location.href = `/community/morecomment?community_Id=${communityid9}`;
						console.log(mbtn2);
					}
				}

			} else {
				var length = comments.length;
				subcomment.innerHTML = "<p align='center'>댓글이 " + length + "개 있습니다.</p>" +
					"<div style='text-align:center;'><button class='idmore'>자세히 보기</button></div>";
				const mbtn = subcomment.querySelector(".idmore");
				if (mbtn) {
					mbtn.onclick = () => {
						window.location.href = `/community/morecomment?community_Id=${communityid9}`;
						console.log(mbtn);
					};
				}
			}
		});
}
function closer() {
	selectpopup.style.display = "none";
}

const imagepopup = document.getElementById("imagepopup");
function zimage(wow) {
	const imageup = document.getElementById("imageup");
	imageup.src = wow.src;
	imagepopup.style.display = "flex";
}
const wowclose = document.querySelector("#imagepopup .close");
wowclose.onclick = () => {
	imagepopup.style.display = "none";
}