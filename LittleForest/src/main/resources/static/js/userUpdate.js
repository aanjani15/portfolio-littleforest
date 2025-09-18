const hiddenId = document.getElementById("hidden_Id").value;
const hiddenPhone = document.getElementById("hidden_Phone").value;
const hiddenAddress = document.getElementById("hidden_Address").value;
const hiddenNickname = document.getElementById("hidden_Nickname").value.trim();
const hiddenEmail = document.getElementById("hidden_Email").value.trim();
const upaloneB = document.getElementById("UpaloneB");
const userPopup = document.getElementById("userUpopup");
const userPClose = userPopup.querySelector(".close");
const alonePhoto = document.getElementById("alonePhoto");
const aloneBtn = document.getElementById("aloneBtn");
const uwithB = document.getElementById("UwithB");
const userPopup2 = document.getElementById("userUpopup2");
const userPopup2C = userPopup2.querySelector(".close");
let re1 = 0;

let file;
let profileUrl;
const CLOUD_NAME = "dkaeihkco"; // Cloudinary cloud name
const UPLOAD_PRESET = "community"; // Unsigned preset 이름
const url = `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`;
const newPhoto3 = document.getElementById("newPhoto2");
upaloneB.onclick = () => {
	userPopup.style.display = "flex";
}
aloneRBtn.onclick = () => {
	alonePhoto.value = "";
	newPhoto3.src = "";
}
wantNoBtn.onclick = () => {
	alonePhoto.value = "";
	newPhoto3.src = "";
	profileUrl = "";
	const wantNoData = new FormData();
	wantNoData.append("id", hiddenId);
	wantNoData.append("profileUrl", profileUrl);
	fetch("/mypage/update/profile", {
		method: 'POST',
		body: wantNoData
	}).then(res => res.text())
		.then(data => {
			if (data == "success") {
				if (profileUrl == "") {
					profileUrl = "/image/profile.png";
				}
				document.getElementById("profile-img").src = profileUrl;
				document.querySelector(".profile-img").src = profileUrl;
				document.getElementById("nowPhoto").src = profileUrl;
				setTimeout(() => {
					newPhoto3.src = ""
					document.getElementById("alonePhoto").value = "";
				}, 1500);
				if (re1 === 1) {
					userPopup.style.display = "none";
					userPopup2.style.display = "flex";
				}
			}
		})

}
alonePhoto.addEventListener("change", () => {
	if (alonePhoto.files[0]) {
		file = alonePhoto.files[0];
		const known = URL.createObjectURL(file);
		newPhoto3.src = known;
	}
})
aloneBtn.onclick = async () => {
	if (alonePhoto.files[0]) {
		file = alonePhoto.files[0];
		const aloneData = new FormData();
		aloneData.append("upload_preset", UPLOAD_PRESET);
		aloneData.append("file", file);
		const res = await fetch(url, { method: "POST", body: aloneData });
		if (!res.ok) {
			const errData = await res.json();
			console.error("Cloudinary 에러 응답:", errData);
			throw new Error("이미지 업로드 실패");
		}
		const data = await res.json();
		profileUrl = data.secure_url;
		document.getElementById("newPhoto2").src = profileUrl;

	} else {
		profileUrl = "";
	}
	const lastData = new FormData();
	lastData.append("id", hiddenId);
	lastData.append("profileUrl", profileUrl);
	fetch("/mypage/update/profile", {
		method: 'POST',
		body: lastData
	}).then(res => res.text())
		.then(data => {
			if (data == "success") {
				if (profileUrl == "") {
					profileUrl = "/image/profile.png";
				}
				document.getElementById("profile-img").src = profileUrl;
				document.querySelector(".profile-img").src = profileUrl;
				document.getElementById("nowPhoto").src = profileUrl;
				setTimeout(() => {
					newPhoto3.src = ""
					document.getElementById("alonePhoto").value = "";
				}, 1500);
				if (re1 === 1) {
					userPopup.style.display = "none";
					userPopup2.style.display = "flex";
				}
			}
		})
}
userPClose.onclick = () => {
	userPopup.style.display = "none";
}

//5가지 수정 팝업
uwithB.onclick = () => {
	userPopup2.style.display = "flex";
}
userPopup2C.onclick = () => {
	userPopup2.style.display = "none";
	re1 = 0;
}

const reset2 = document.getElementById("reset2");
const back2 = document.getElementById("back2");
const userInfoBtn = document.getElementById("userInfoBtn");
const nickname2 = document.getElementById("nickname2");
const phone2 = document.getElementById("phone2");
const juso = document.getElementById("juso").textContent;
const address22 = document.getElementById("address2");
const detail_address22 = document.getElementById("detail_address2");
const email22 = document.getElementById("email2");
const nickBtn = document.getElementById("nickBtn");
const nickComplete = document.getElementById("nickComplete");
const NormailImage = document.getElementById("NormailImage");
let phone22 = phone2.value;

let ook1 = 0;
let ook2 = 0;
let authCode1;
NormailImage.onclick = () => {
	re1 = 1;
	userPopup2.style.display = "none";
	userPopup.style.display = "flex";

}
reset2.onclick = () => {
	document.getElementById("nickname2").value = "";
	document.getElementById("phone2").value = "";
	document.getElementById("address2").value = "";
	document.getElementById("detail_address2").value = "";
	document.getElementById("email2").value = "";
	document.getElementById("emailCode2").value = "";
	nickComplete.textContent = "";
	ook1 = 0;
	ook2 = 0;
}
back2.onclick = () => {
	document.getElementById("nickname2").value = hiddenNickname;
	document.getElementById("phone2").value = hiddenPhone;
	document.getElementById("email2").value = hiddenEmail;
	ook1 = 1;
	ook2 = 1;
}
nickBtn.onclick = async () => {
	const nickname22 = document.getElementById("nickname2").value.trim();
	if (nickname22 === hiddenNickname) {
		ook1 = 1;
		return;
	} if (!nickname22) {
		nickComplete.innerHTML = `<p align='center' style='color:red;'> 닉네임을 입력해주세요.</p>`;;
		ook1 = 0;
		return;
	}
	const distinctCheck = new URLSearchParams();
	distinctCheck.append("nickname", nickname22);
	const nick = await fetch('/mypage/update/nicknamecheck', {
		method: 'POST',
		body: distinctCheck,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
	});
	const data = await nick.text();
	if (data == "success") {
		nickComplete.innerHTML = `<p align='center' style='color:green;'> 사용 가능한 닉네임입니다.</p>`;
		ook1 = 1;
	} else if (data === "fail") {
		nickComplete.innerHTML = `<p align='center' style='color:red;'> 이미 사용중인 닉네임입니다.</p>`;
	} else if (data === "error") {
		nickComplete.innerHTML = `<p align='center'>서버 에러</p>`;
	}
}
phone2.addEventListener("input", (e) => {
	let value = e.target.value.replace(/[^0-9]/g, "");
	if (value.length < 4) {
		e.target.value = value;
	}
	else if (value.length < 7) {
		e.target.value = value.replace(/(\d{3})(\d+)/, "$1-$2");
	} else if (value.length < 11) {
		e.target.value = value.replace(/(\d{3})(\d{0,4})(\d+)/, "$1-$2-$3");
	}
	else {
		e.target.value = value.replace(/(\d{3})(\d{0,4})(\d{0,4})/, "$1-$2-$3");
	}

	phone22 = e.target.value;
})
address22.readOnly = true;
address22.addEventListener("click", (e) => {
	e.preventDefault();
	alert("주소검색버튼을 눌러주세요")
})
function checkAddress() {

	if (address22.value !== "") {
		detail_address22.readOnly = false;
		detail_address22.onmousedown = null;
	}
	else {
		detail_address22.readOnly = true;
		detail_address22.onmousedown = (e) => {
			e.preventDefault();
			alert("기본 주소가 없습니다")
		}
	}
}
checkAddress();
address22.addEventListener("input", checkAddress);
function openPostcode2() {
	// 팝업 창 크기 설정
	var width = 500; // 가로 크기
	var height = 600; // 세로 크기

	// 화면 중앙 좌표 계산
	var left = (window.innerWidth / 2) - (width / 2);
	var top = (window.innerHeight / 2) - (height / 2);

	new daum.Postcode({
		oncomplete: function(data) {
			// 선택한 주소를 address input에 넣기
			document.getElementById('address2').value = data.address;
			checkAddress();
		}
	}).open({
		left: left,
		top: top,
		width: width,
		height: height
	});
}
//회원수정용
function requestEmailCode2() {
	const emailInput2 = document.getElementById('email2');
	const email2 = emailInput2.value.trim();

	if (!email2) {
		alert('이메일을 입력해주세요.');
		emailInput2.focus();
		return;
	}
	if (email2 === hiddenEmail) {
		alert("이메일이 같습니다")
		ook2 = 1;
		return;
	}

	fetch('/sign-up/emailCheck', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify({ email: email2 })
	})
		.then(response => response.text())
		.then(authCode => {
			authCode1 = authCode;
			alert('인증 코드가 이메일로 전송되었습니다.');
			console.log('서버에서 받은 인증 코드:', authCode);
			document.getElementById('emailCode2').value = '';
		})
		.catch(err => {
			alert('이메일 인증 요청 중 오류가 발생했습니다.');
			console.error(err);
		});
	const checkCode = document.getElementById("checkCode");
	checkCode.onclick = () => {
		if (!email2) {
			checkCode.disabled = true;
		} else {
			checkCode.disabled = false;
		}
		if (email2 === hiddenEmail) {
			checkCode.disabled = true;
		} else {
			checkCode.disabled = false;
		}
		const emailCode22 = document.getElementById("emailCode2").value.trim();
		if (authCode1 === emailCode22) {
			document.getElementById("emailComplete").innerHTML = `<p align='center' style='color:green'>인증코드가 일치합니다.</p>`;
			ook2 = 1;
		} else {
			document.getElementById("emailComplete").innerHTML = `<p align='center' style='color:red'>인증코드가 일치하지 않습니다.</p>`;
		}
	}
}
userInfoBtn.onclick = () => {
	let addr = "";
	if (email22.value.trim() === hiddenEmail) {
		ook2 = 1;
	}
	if (nickname2.value.trim() === hiddenNickname) {
		ook1 = 1;
	}
	if (ook1 != 1 && ook2 != 1) {
		document.getElementById("whereNo").innerHTML = `<p align='center' style='color:red'>닉네임 중복확인과 이메일 인증을 해주세요</p>`;
		return;
	} else if (ook1 != 1) {
		document.getElementById("whereNo").innerHTML = `<p align='center' style='color:red'>닉네임 중복확인을 해주세요<p>`;
		return;
	} else if (ook2 != 1) {
		document.getElementById("whereNo").innerHTML = `<p align='center' style='color:red'>이메일 코드인증을 해주세요<p>`;
		return;
	} else if (ook1 == 1 && ook2 == 1) {
		document.getElementById("whereNo").innerHTML = `<p align='center' style='color:red'>수정중</p>`;
	}
	if (!address22.value) {
		addr = juso;
	} else {
		addr = address22.value.trim() + " " + detail_address22.value.trim();
	}
	const infoData = new FormData();
	infoData.append("id", hiddenId);
	infoData.append("nickname", nickname2.value.trim());
	infoData.append("phone", phone22);
	infoData.append("address", addr);
	infoData.append("email", email22.value.trim());
	fetch("/mypage/update/info", {
		method: 'POST',
		body: infoData
	}).then(response => response.text())
		.then(data => {
			if (data == "success") {
				document.getElementById("juso").innerHTML = addr;
				document.getElementById("address2").value = "";
				document.getElementById("detail_address2").value = "";
				document.getElementById("whereNo").innerHTML = `<p align='center' style='color:blue'>수정 성공</p>`;
				/*setTimeout(()=>{userPopup2.style.display = "none"*/
				setTimeout(() => { document.getElementById("whereNo").innerHTML = "" }, 1500);
				/*},1500);*/
			}
		})
}
const imageArea = document.getElementById("profile-img");
const userImagePopup = document.getElementById("userImagePopup");
const userIup = document.getElementById("userIup");
const userIclose = userImagePopup.querySelector(".close");
const nowArea = document.getElementById("nowPhoto");
const newArea = document.getElementById("newPhoto2");
imageArea.onclick = () => {
	userIup.src = imageArea.src;
	userImagePopup.style.display = "flex";
}
userIclose.onclick = () => {
	userImagePopup.style.display = "none";
}
nowArea.onclick = () => {
	userIup.src = nowArea.src;
	userImagePopup.style.display = "flex";
}
newArea.onclick = () => {
	userIup.src = newArea.src;
	userImagePopup.style.display = "flex";
}
//비밀번호 변경팝업 띄우기
const searchPass = document.getElementById("searchPass");
const userPassPopup = document.getElementById("userPassPopup");
const userPassPClose = userPassPopup.querySelector(".close");
const nowPassword = document.getElementById("nowPassword");
const checkPassword = document.getElementById("checkPassword");
const newPassPopup = document.getElementById("newPassPopup");
searchPass.onclick = () => {
	userPassPopup.style.display = "flex";
}
userPassPClose.onclick = () => {
	userPassPopup.style.display = "none";
	document.getElementById("okPassword").innerHTML = "";
	nowPassword.readOnly = false;
	nowPassword.value = "";
	newPassPopup.style.display = "none";
	document.getElementById("newPassword").value = "";
	document.getElementById("newPassword2").value = "";
	document.getElementById("newInfo").innerHTML = "";
	document.getElementById("newInfo2").innerHTML = "";
}
checkPassword.onclick = () => {
	if (!nowPassword.value) {
		document.getElementById("okPassword").innerHTML = `<p align='center' style='color:red;'>비밀번호를 입력해주세요.</p>`;
		return;
	}
	const searchData = new FormData();
	searchData.append("id", hiddenId);
	searchData.append("password", nowPassword.value.trim());
	fetch("/mypage/update/password", {
		method: 'POST',
		body: searchData
	}).then(res => res.text())
		.then(data => {
			if (data === "success") {
				document.getElementById("okPassword").innerHTML = `<p align='center' style='color:green;'>비밀번호가 일치합니다</p>`;
				nowPassword.readOnly = true;
				newPassPopup.style.display = "block";
			}
			if (data === "fail") {
				document.getElementById("okPassword").innerHTML = `<p align='center' style='color:red;'>비밀번호가 일치하지 않습니다.</p>`;
			}
		})
}
const newPasswordCheck = document.getElementById("newPasswordCheck");
const newPasswordBtn = document.getElementById("newPasswordBtn");
const newInfo = document.getElementById("newInfo");
const newInfo2 = document.getElementById("newInfo2");
const newPassword = document.getElementById("newPassword")
const newPassword2 = document.getElementById("newPassword2")
let op = 0;
newPasswordCheck.onclick = () => {
	if (nowPassword.value.trim() === newPassword.value.trim()) {
		newInfo2.innerHTML = `<p align='center' style='color:red'>기존비밀번호와 일치합니다.</p>`;
		op = 0;
		return;
	}
	if (newPassword.value.trim() === newPassword2.value.trim()) {
		newInfo2.innerHTML = `<p align='center' style='color:green'>비밀번호가 일치합니다.</p>`;
		op = 1;
	}
	else if (newPassword.value.trim() !== newPassword2.value.trim()) {
		newInfo2.innerHTML = `<p align='center' style='color:red'>비밀번호가 일치하지않습니다.</p>`;
		op = 0;
		return;
	}
}
newPasswordBtn.onclick = () => {
	if (newPassword.value.trim() !== nowPassword.value.trim() && newPassword.value.trim() === newPassword2.value.trim()) {
		if (op === 1) {
			const opData = new FormData();
			opData.append("id", hiddenId);
			opData.append("password", newPassword.value.trim());
			fetch("/mypage/update/password/new", {
				method: 'POST',
				body: opData
			}).then(res => res.text())
				.then(data => {
					if (data === "success") {
						newInfo2.innerHTML = `<p align='center' style='color:red'>비밀번호 변경 성공.</p>`;
					}
				})
		}
		else if (op !== 1) {
			newInfo2.innerHTML = `<p align='center' style='color:red'>새로운 비밀번호 확인버튼을 눌러주세요.</p>`;
		}
	}
}
const selectInfo = document.getElementById("selectInfo");
const userInfoPopup = document.getElementById("userInfoPopup");
const userInfoClose = userInfoPopup.querySelector(".close");
selectInfo.onclick = () => {
	userInfoPopup.style.display = "block";
}

const oauth_IdAll = document.getElementById("oauth_IdAll");

function maskingOauth_Id(imsi) {
	if (imsi.length <= 3) {
		return imsi.substring(0) + "*".repeat(imsi.length - 1);
	} else {
		return imsi.substring(0, 3) + "*".repeat(imsi.length - 3);
	}
}
const maskingId = oauth_IdAll.value;
oauth_IdAll.value = maskingOauth_Id(maskingId);
const allsee = document.getElementById("allSee")
allsee.addEventListener("change", () => {
	if (allsee.checked) {
		oauth_IdAll.value = maskingId;
	} else {
		oauth_IdAll.value = oauth_IdAll.value = maskingOauth_Id(maskingId);
	}
})
userInfoClose.onclick = () => {
	userInfoPopup.style.display = "none";
	allsee.checked = false;
	oauth_IdAll.value = oauth_IdAll.value = maskingOauth_Id(maskingId);
}
document.getElementById("userInfoClosing").addEventListener("click", () => {
	userInfoPopup.style.display = "none";
	allsee.checked = false;
	oauth_IdAll.value = oauth_IdAll.value = maskingOauth_Id(maskingId);
})