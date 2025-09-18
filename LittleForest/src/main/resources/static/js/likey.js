document.querySelectorAll(".like-btn").forEach(btn=>{
	btn.addEventListener("click",(e)=>{
		btn.classList.toggle("liked");
		const lpostcard=e.target.closest(".post-card");
		const llikecount=lpostcard.querySelector(".likes-count");
		const llikebtn=lpostcard.querySelector(".like-btn");
		const lcommunityId=lpostcard.querySelector(".community_Id").value;
		const luserId=document.querySelector(".user_Id").value;
		const lformData=new FormData();
		lformData.append("community_Id",lcommunityId);
		//lformData.append("user_Id",luserId);
		fetch('/community/like',{
			method:'POST',
			body:lformData
		}).then(response=>response.text())
		.then(data => {
			const [likes, liked] = data.split(',');
		llikecount.textContent = likes;
		})
			
	})
})

