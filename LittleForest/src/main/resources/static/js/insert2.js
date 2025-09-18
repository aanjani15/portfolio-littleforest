const ccbtn=document.getElementById("ccbtn");
const ccinsrtpopup=document.getElementById("cinsertpopup");
const ccinsrtclose=document.querySelector("#cinsertpopup .close")
const ccsubmit=document.getElementById("ccsubmit");
const cc1userId=document.getElementById("cc1user_Id");
const cccomment=document.getElementById("cccomment");
const cccc=document.getElementById("ccid")
console.log(cc1userId);
ccbtn.onclick=()=>{
	const ccid=document.getElementById("ccid").value;
	ccinsrtpopup.style.display="flex";
}
ccinsrtclose.onclick=()=>{
	ccinsrtpopup.style.display="none";
}
ccsubmit.onclick=()=>{
	const tUserId=parseInt(cc1userId.textContent);
	const tComment=cccomment.value;
	const formData2=new FormData();
	formData2.append("user_Id",tUserId);
	formData2.append("community_Id",cccc.value);
	formData2.append("comment_Id",tComment);
	
	fetch('/community/comment/insert',{
		method:'POST',
		body:formData2
	}).then(response=>response.text())
	.then(data=>{
		alert("댓글등록성공");
		console.log(data);
		location.reload();
	})
}
