(function() {
	const form = document.getElementById('adminLoginForm');
	const idInput = document.getElementById('username');
	const pwInput = document.getElementById('password');

	idInput.addEventListener('input', () => {
		idInput.value = idInput.value.replace(/[^\d]/g, '');
	});

	form.addEventListener('submit', (e) => {
		if (!idInput.value.trim() || !pwInput.value.trim()) {
			e.preventDefault();
			alert('ID와 비밀번호를 입력하세요.');
			(idInput.value ? pwInput : idInput).focus();
		}
	});
})();
