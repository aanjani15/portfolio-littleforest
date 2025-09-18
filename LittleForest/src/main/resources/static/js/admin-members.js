function getCsrf() {
	const token = document.querySelector('meta[name="_csrf"]')?.content;
	const header = document.querySelector('meta[name="_csrf_header"]')?.content;
	return (token && header) ? { token, header } : null;
}

async function postForm(url, data, { timeoutMs = 8000 } = {}) {
	const csrf = getCsrf();
	const controller = new AbortController();
	const timer = setTimeout(() => controller.abort(), timeoutMs);

	const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
	if (csrf) headers[csrf.header] = csrf.token;

	const body = new URLSearchParams(data).toString();

	try {
		const resp = await fetch(url, {
			method: 'POST',
			headers,
			body,
			signal: controller.signal,
			credentials: 'same-origin'
		});
		clearTimeout(timer);
		if (!resp.ok) {
			let msg = `HTTP ${resp.status}`;
			try {
				const ct = resp.headers.get('content-type') || '';
				if (ct.includes('application/json')) {
					const j = await resp.json();
					if (j?.message) msg = j.message;
				} else {
					const t = await resp.text();
					if (t) msg = t.slice(0, 200);
				}
			} catch (_) { }
			throw new Error(msg);
		}
		return resp;
	} catch (e) {
		if (e.name === 'AbortError') throw new Error('요청 시간이 초과되었습니다.');
		throw e;
	}
}

function normalizeRole(v) {
	const x = String(v || '').toLowerCase();
	return (x === 'user' || x === 'admin') ? x : null;
}

function isInteractive(el) {
	return !!el.closest('button, a, select, input, textarea, label');
}
function hasSelection() {
	const s = window.getSelection();
	return s && String(s).length > 0;
}

// 간단 토스트
function toast(msg, type = 'info') {
	const el = document.createElement('div');
	el.textContent = msg;
	el.style.position = 'fixed';
	el.style.left = '50%';
	el.style.top = '16px';
	el.style.transform = 'translateX(-50%)';
	el.style.padding = '10px 14px';
	el.style.borderRadius = '10px';
	el.style.background = type === 'error' ? '#fee2e2' : '#ecfeff';
	el.style.color = type === 'error' ? '#991b1b' : '#0c4a6e';
	el.style.border = '1px solid ' + (type === 'error' ? '#fecaca' : '#bae6fd');
	el.style.boxShadow = '0 2px 8px rgba(0,0,0,.08)';
	el.style.zIndex = '9999';
	document.body.appendChild(el);
	setTimeout(() => el.remove(), 2000);
}

// ---------- main ----------
document.addEventListener('DOMContentLoaded', () => {
	// (1) 권한 저장 버튼
	document.querySelectorAll('button.role-save').forEach((btn) => {
		btn.addEventListener('click', async (e) => {
			e.preventDefault();
			e.stopPropagation();

			const id = btn.dataset.id;
			const sel = document.querySelector(`select.role[data-id="${id}"]`);
			if (!id || !sel) return toast('잘못된 요청입니다.', 'error');

			const role = normalizeRole(sel.value);
			if (!role) return toast('유효하지 않은 권한 값입니다.', 'error');

			if (!getCsrf()) return toast('보안 토큰이 없습니다. 다시 로그인해 주세요.', 'error');

			if (btn.dataset.loading === '1') return;
			btn.dataset.loading = '1';
			const oldText = btn.textContent;
			btn.textContent = '저장중…';
			btn.disabled = true;

			try {
				await postForm('/admin/members/role', { id, role });
				toast('권한이 저장되었습니다.');
				// 새로고침 대신 셀만 업데이트하려면 아래 1줄로 대체 가능
				// btn.closest('tr').querySelector('td:nth-child(5)').textContent = role;
				location.reload();
			} catch (err) {
				console.error(err);
				toast(`권한 변경 실패: ${err.message}`, 'error');
			} finally {
				btn.dataset.loading = '0';
				btn.textContent = oldText;
				btn.disabled = false;
			}
		});
	});

	// (2) 행 클릭 → 상세/수정
	const table = document.querySelector('table.list');
	if (table) {
		// 접근성: 포커스 가능하게
		table.querySelectorAll('tbody tr').forEach(tr => tr.tabIndex = 0);

		table.addEventListener('click', (e) => {
			if (e.defaultPrevented) return;
			if (isInteractive(e.target) || hasSelection()) return;

			const tr = e.target.closest('tr.clickable, tbody tr');
			if (!tr) return;
			const id = tr.getAttribute('data-id') || tr.dataset.id;
			if (!id) return;

			// 숫자만 허용하고 싶으면 주석 해제
			// if (!/^\d+$/.test(id)) return;

			window.location.assign(`/admin/members/${encodeURIComponent(id)}`);
		});

		// 엔터키로도 상세 이동
		table.addEventListener('keydown', (e) => {
			if (e.key !== 'Enter') return;
			const tr = e.target.closest('tr.clickable, tbody tr');
			if (!tr) return;
			const id = tr.getAttribute('data-id') || tr.dataset.id;
			if (!id) return;
			window.location.assign(`/admin/members/${encodeURIComponent(id)}`);
		});
	}
});
