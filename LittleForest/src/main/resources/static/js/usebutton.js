const usebtn=document.getElementById("usebutton");
const usepopup=document.getElementById("usepopup");
const useclose=usepopup.querySelector(".close");
const personnalbtn=document.getElementById("personnalbutton");
const perpopup=document.getElementById("perpopup");
const perclose=perpopup.querySelector(".close");
usebtn.onclick=()=>{
	usepopup.style.display="flex";
}
personnalbtn.onclick=()=>{
	perpopup.style.display="flex";
}
useclose.onclick=()=>{
	usepopup.style.display="none";
}
perclose.onclick=()=>{
	perpopup.style.display="none";
}