(function() {
	const btn = document.getElementById('water');
	const stockEl = document.getElementById('water_Stock') || document.getElementById('waterStock');
	const giverEl = document.getElementById('giver');
	const timerEl = document.getElementById('timewatch');

	if (!btn || !stockEl || !giverEl || !timerEl) return;

	// const INTERVAL = 5000; // 테스트 5초
	const INTERVAL = 1000 * 60 * 60 * 12; // 12시간
	

	function todayKey() { return new Date().toISOString().slice(0, 10); }
	function loadState() {
		try { return JSON.parse(localStorage.getItem('waterReceive')) || {}; } catch { return {}; }
	}
	function saveState(s) { localStorage.setItem('waterReceive', JSON.stringify(s)); }

	let state = loadState();
	if (state.date !== todayKey()) {
		state = { date: todayKey(), nextTime: 0 };
		saveState(state);
	}


	function fmt(ms) {
		const h = String(Math.floor(ms / 3600000)).padStart(2, '0');
		const m = String(Math.floor((ms % 3600000) / 60000)).padStart(2, '0');
		const s = String(Math.floor((ms % 60000) / 1000)).padStart(2, '0');
		return `${h}:${m}:${s}`;
	}

	function updateUI() {
		const now = Date.now();
		const remain = (state.nextTime || 0) - now;
		if (remain > 0) {
			btn.disabled = true;
			timerEl.textContent = fmt(remain);
			giverEl.textContent = "대기중";
		} else {
			btn.disabled = false;
			timerEl.textContent = "받을 수 있음";
			giverEl.textContent = "가능";
		}
	}

	btn.onclick = async () => {
		if (Date.now() < (state.nextTime || 0)) return; // 아직 대기중이면 무시
		const current = parseInt(stockEl.textContent) || 0;
		try {
			await fetch('/growtree/give', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
				body: JSON.stringify({ water_Stock: current + 1 })
			});
			state.nextTime = Date.now() + INTERVAL; // 다음 수령 가능 시간 저장
			saveState(state);
			stockEl.textContent = String(current + 1);
			updateUI();
		} catch (e) {
			console.error('[time] receive failed', e);
		}
	};

	setInterval(updateUI, 1000);
	updateUI();
})();
