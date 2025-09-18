const congrulationpopupbtn = document.getElementById("congrulationpopup");

const historybtn = document.getElementById("historyBtn");
const nobtn = document.getElementById("nobtn");
const morebtn = document.getElementById("morebtn");
const popupident = document.getElementById("popupident");
const waterstockq = document.getElementById("water_Stock");
const biyrostockq = document.getElementById("biyro_Stock");
const biyrousedatq = document.getElementById("biyro_Used_At");
const son7 = document.getElementById("son7");
const son77 = document.getElementById("son77");
const son7Pose = document.getElementById("son7_pose");
const sonclose = son7Pose.querySelector(".close");
const beforeServer = document.getElementById("beforeServer");
let cleanShot;
/*const identbtn=document.getElementById("identbtn");*/
historybtn.onclick = () => {
	location.href = "/growtree/grown";

	console.log();
}
nobtn.onclick = () => {
	congrulationpopupbtn.style.display = "none";
}

morebtn.onclick = () => {
	son7Pose.style.display = "flex";
	const sontrue = document.getElementById("sontrue");
	const sonfalse = document.getElementById("sonfalse");

	//location.href="/growtree/more";
}
sonclose.onclick = () => {
	son7Pose.style.display = "none";
}
sontrue.onclick = async () => {
	son7Pose.style.display = "none";
	congrulationpopupbtn.style.display = "none";
	const shot = await html2canvas(son7);
	shot.toBlob(async (blob) => {
		const previewDiv = document.getElementById("preview");
		const img31 = document.createElement("img");
		img31.src = URL.createObjectURL(blob);
		img31.style.width = "300px";
		img31.style.height = "300px";
		previewDiv.appendChild(img31);
		beforeServer.style.display = "flex";
		cleanShot = previewDiv;
		const SonGoodbtn = document.getElementById("goodBtn");
		SonGoodbtn.onclick = async () => {
			//2clouninary api로 이미지 전송
			const CLOUD_NAME = "dkaeihkco"; // Cloudinary cloud name
			const UPLOAD_PRESET = "community"; // Unsigned preset 이름
			const url = `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`;
			const SonData2 = new FormData();
			SonData2.append("upload_preset", UPLOAD_PRESET);
			SonData2.append("file", blob);

			const res = await fetch(url, { method: "POST", body: SonData2 });

			if (!res.ok) {
				const errData = await res.json();
				console.error("Cloudinary 에러 응답:", errData);
				throw new Error("이미지 업로드 실패");
			}

			const data = await res.json();
			let screenUrl = data.secure_url;

			const SonData3 = new FormData();
			SonData3.append("screenshot", screenUrl);
			fetch("/growtree/more", {
				method: 'POST',
				body: SonData3
			}).then(res => res.text())
				.then(data => {
					if (data == "success") {
						location.href = "/growtree/grown";
					}
				})
		}
	}, "image/png");

}

beforeServer.querySelector(".close").onclick = () => {
	cleanShot.innerHTML = "";
	beforeServer.style.display = "none";

}
sonfalse.onclick = () => {
	let screenshot = "";
	const sonData = new FormData();
	sonData.append("screenshot", screenshot);
	fetch("/growtree/more", {
		method: 'POST',
		body: sonData
	}).then(response => response.text())
		.then(data => {
			if (data == "success") {
				location.href = "/growtree/grown";
			}
		})
}
identbtn.onclick = () => {
	/*let identTree = document.getElementById("identTree").value.trim();
=======
	popupident.style.display="block";
	location.href = "/more";
}

identbtn.onclick = () => {
	let identTree = document.getElementById("identTree").value.trim();
>>>>>>> jinhee

		if (identTree.length < 1 || identTree.length > 5) {
			alert("식별자 이름은 1 ~ 5 자 사이로 입력해주세요.");
			return;
<<<<<<< HEAD
		}*/
	location.href = "/growtree/more";
}
