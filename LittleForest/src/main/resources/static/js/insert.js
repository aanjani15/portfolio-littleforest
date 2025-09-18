const insertbtn = document.getElementById("insbtn");
const insertpopup = document.getElementById("insertpopup");
const cclosebtn = document.querySelector("#insertpopup .close");
const cinsbtn = document.getElementById("cinsbtn");
const cuserId = document.getElementById("cuser_Id");
const ctype = document.getElementById("ctype"); 
const ctitle = document.getElementById("ctitle");
const ccontent = document.getElementById("ccontent");
const cphoto = document.getElementById("cphoto");

insertbtn.onclick = () => {
    insertpopup.style.display = "flex";
};

cclosebtn.onclick = () => {
    insertpopup.style.display = "none";
};

cinsbtn.onclick = async () => {
    const textuserId = parseInt(cuserId.textContent);
    const texttype = ctype.value;
    const texttitle = ctitle.value;
    const textcontent = ccontent.value;

    if (!texttype) { alert("타입을 선택해주세요"); return; }
    if (!texttitle) { alert("제목을 입력해주세요"); return; }
    if (texttitle.length < 1 || texttitle.length > 20) { alert("제목은 1~20자 사이로 입력해주세요"); return; }
    if (textcontent.length <= 1) { alert("글은 2자 이상 입력해주세요"); return; }

    try {
        let photoUrl = "";

        // 이미지 업로드
        if (cphoto.files[0]) {
            const file = cphoto.files[0];
            const CLOUD_NAME = "dkaeihkco"; // Cloudinary cloud name
            const UPLOAD_PRESET = "community"; // Unsigned preset 이름
            const url = `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/image/upload`;

            const formDataImg = new FormData();
            formDataImg.append("upload_preset", UPLOAD_PRESET);
            formDataImg.append("file", file);

            const res = await fetch(url, { method: "POST", body: formDataImg });

            if (!res.ok) {
                const errData = await res.json();
                console.error("Cloudinary 에러 응답:", errData);
                throw new Error("이미지 업로드 실패");
            }

            const data = await res.json();
            photoUrl = data.secure_url;
        }

        // 글 등록
        const formData9 = new FormData();
        formData9.append("user_Id", textuserId);
        formData9.append("type", texttype);
        formData9.append("title", texttitle);
        formData9.append("content", textcontent);
        formData9.append("photo", photoUrl);

        const insertResponse = await fetch("/community/insert", { method: "POST", body: formData9 });
        const data = await insertResponse.text();
        alert("등록성공");
        console.log(data);
        window.location.reload();

    } catch (err) {
        alert("에러 발생");
        console.error(err);
    }
};
