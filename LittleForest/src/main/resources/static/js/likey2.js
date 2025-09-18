const l2likebtn=document.querySelector(".community-like .like-btn");
const l2userId=document.querySelector(".user_Id3").value;
const l2communityId=document.querySelector(".community_Id3").value;
const l2likecount=document.querySelector(".likes-count");
l2likebtn.addEventListener("click",()=>{
	l2likebtn.classList.toggle("liked");
		const l2formData=new FormData();
		l2formData.append("community_Id",l2communityId);
		l2formData.append("user_Id",l2userId);
		fetch('/community/like',{
			method:'POST',
			body:l2formData
		}).then(response=>response.text())
		.then(data => {
			const [likes, liked] = data.split(',');
		l2likecount.textContent = likes;
		    if(liked === 'true') {
		        l2likebtn.classList.add("liked");
		    } else {
		        l2likebtn.classList.remove("liked");
		    }
		})
			
	})

