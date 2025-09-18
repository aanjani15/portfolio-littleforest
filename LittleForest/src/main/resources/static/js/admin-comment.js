function confirmModalCmt(message = '삭제하시겠습니까?') {
	return new Promise((resolve) => {
		const modal = document.getElementById('cmt-confirm');
		const msg = document.getElementById('cmt-confirm-msg');
		const okBtn = document.getElementById('cmt-confirm-ok');
		const cancelBtn = document.getElementById('cmt-confirm-cancel');

		msg.textContent = message;
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

		okBtn.addEventListener('click', onOk);
		cancelBtn.addEventListener('click', onCancel);
		modal.addEventListener('click', onBackdrop);
		document.addEventListener('keydown', onKey);

		setTimeout(() => okBtn.focus(), 0);
	});
}

async function deleteCommentAxios(btn) {
	const id = btn.dataset.id;
	if (!id) { alert('댓글 ID 없음'); return; }

	const ok = await confirmModalCmt('정말 삭제하시겠습니까?');
	if (!ok) return;

	try {
		await axios.delete(`/admin/posts/comments/${id}`);
		btn.closest('.cmt')?.remove();
	} catch (e) {
		alert('삭제 실패: ' + (e.response?.data || e.message));
		console.error(e);
	}
}
