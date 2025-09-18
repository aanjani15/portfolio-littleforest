const tree_img=document.getElementById("tree_img")
const tree_popup=document.getElementById("tree_popup");
const tree_close=document.querySelector("#tree_popup .close");

tree_img.onclick=() => {
	tree_popup.style.display="block";
}
tree_close.onclick=() =>{
	tree_popup.style.display="none";
}