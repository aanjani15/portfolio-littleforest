function updateCommunityList() {
    // select 값 가져오기
    const sortValue = document.getElementById("idSelect").value;

    // 체크박스 값 가져오기
    const checked = document.querySelectorAll('input[name="type"]:checked');
    const typeValues = Array.from(checked).map(el => el.value);
	
	const mme=document.getElementById("my").checked;
    // URL 파라미터 묶기
    const params = new URLSearchParams();
    if (sortValue) params.append("sort", sortValue);
    typeValues.forEach(t => params.append("type", t));
	if(mme){
		params.append("my","true");
	}
    // 페이지 이동
    location.href = `/community?${params.toString()}`;
}

// 이벤트 등록
document.getElementById("idSelect").addEventListener("change", updateCommunityList);
document.querySelectorAll('input[name="type"]').forEach(cb => cb.addEventListener("change", updateCommunityList));
document.getElementById("my").addEventListener("change",updateCommunityList);
