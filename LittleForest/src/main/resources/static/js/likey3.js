document.querySelectorAll(".comment-like .like-btn").forEach(btn =>{
	btn.addEventListener("click",function(e){
		btn.classList.toggle("liked");
		const liCommentItem=e.target.closest(".comment-item");
		const lilikeCount=liCommentItem.querySelector(".likes-count");
		console.log(lilikeCount);
		const liuserId=liCommentItem.querySelector(".c2user_Id2").value;
		const liCommentId=liCommentItem.querySelector(".mid").value;
		
		const formDatali=new FormData();
		formDatali.append("user_Id",liuserId);
		formDatali.append("id",liCommentId);
		
		fetch("/community/comment/like",{
			method:'POST',
			body:formDatali
		}).then(response=>response.text())
		.then(data=>{
			const [likes, liked] = data.split(',');
			lilikeCount.textContent = likes;
			
		})
	})
})