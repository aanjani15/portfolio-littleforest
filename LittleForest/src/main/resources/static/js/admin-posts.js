function confirmModal(message = '삭제하시겠습니까?') {
	return new Promise((resolve) => {
		const modal = document.getElementById('app-confirm');
		const msgElem = document.getElementById('app-confirm-msg');
		const okBtn = document.getElementById('app-confirm-ok');
		const cancelBtn = document.getElementById('app-confirm-cancel');

		msgElem.textContent = message;
		modal.setAttribute('aria-hidden', 'false');

		const close = (result) => {
			modal.setAttribute('aria-hidden', 'true');
			okBtn.removeEventListener('click', onOk);
			cancelBtn.removeEventListener('click', onCancel);
			modal.removeEventListener('click', onBackdrop);
			document.removeEventListener('keydown', onKey);
			resolve(result);
		};
		const onOk = () => close(true);
		const onCancel = () => close(false);
		const onBackdrop = (e) => { if (e.target === modal) close(false); };
		const onKey = (e) => { if (e.key === 'Escape') close(false); if (e.key === 'Enter') close(true); };

		okBtn.addEventListener('click', onOk, { once: true });
		cancelBtn.addEventListener('click', onCancel, { once: true });
		modal.addEventListener('click', onBackdrop, { once: false });
		document.addEventListener('keydown', onKey, { once: false });

		// 접근성: 기본 포커스
		setTimeout(() => okBtn.focus(), 0);
	});
}

// ========== 기존 함수 교체 ==========
async function deletePostAxios(btn) {
	const id = btn.dataset.id;
	if (!id) { alert('게시글 ID 없음'); return; }

	const ok = await confirmModal(`게시글을 삭제하시겠습니까?`);
	if (!ok) return;

	try {
		await axios.delete(`/admin/posts/${id}`);
		btn.closest('tr')?.remove();
	} catch (e) {
		handleAxiosError(e, '게시글 삭제 실패');
	}
}

function handleAxiosError(e, prefix) {
	const status = e.response?.status;
	const body = e.response?.data;
	if (status === 403) {
		alert(`${prefix}: 403 Forbidden\n- 로그인/권한(ROLE_ADMIN) 또는 CSRF 토큰 문제`);
	} else {
		alert(`${prefix}: ${status ?? '네트워크 에러'}\n${typeof body === 'string' ? body : ''}`);
	}
	console.error(e);
}

async function deleteCommentAxios(btn) {
	const id = btn.dataset.id;
	if (!id) { alert('댓글 ID 없음'); return; }

	const ok = await confirmModal('댓글을 삭제하시겠습니까?');
	if (!ok) return;

	try {
		await axios.delete(`/admin/posts/comments/${id}`);
		btn.closest('.cmt')?.remove();
	} catch (e) {
		alert('삭제 실패: ' + (e.response?.data || e.message));
		console.error(e);
	}
}
