let calendar = null;
let calendarInitialized = false;

function ymd(d) {
	if (typeof d === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(d)) return d;
	const dt = new Date(d);
	return `${dt.getFullYear()}-${String(dt.getMonth() + 1).padStart(2, '0')}-${String(dt.getDate()).padStart(2, '0')}`;
}

function upsertAttendanceEvent(dateStr, icons) {
	if (!calendar) return;
	const key = ymd(dateStr);
	const ex = calendar.getEventById(`att-${key}`);
	if (ex) ex.remove();
	calendar.getEvents().forEach(e => { if (ymd(e.startStr) === key) e.remove(); });
	const uniqIcons = Array.from(new Set(icons));
	calendar.addEvent({ id: `att-${key}`, title: '출석', start: key, allDay: true, extendedProps: { icons: uniqIcons } });

	const seen = new Set();
	calendar.getEvents().forEach(ev => {
		const k = ymd(ev.startStr);
		if (seen.has(k)) ev.remove(); else seen.add(k);
	});
}

function initCalendar() {
	const calendarEl = document.getElementById('calendar');
	const userIdEl = document.getElementById('id'); // hidden input(id)
	if (!userIdEl) return;
	const userId = userIdEl.value;

	if (calendarEl && !calendarInitialized) {
		axios.get('/attendance/dates', {
			params: { user_Id: userId, _: Date.now() },
			headers: { Accept: 'application/json' }
		})
			.then(res => {
				const dates = res.data || [];
				const norm = Array.from(new Set(dates.map(ymd))).sort();

				const totalEl = document.getElementById('totalAttendance');
				if (totalEl) totalEl.textContent = `총 출석일: ${norm.length}일`;

				function dayOfWeekYMD(s) {
					let [y, m, d] = s.split('-').map(Number);
					if (m < 3) { m += 12; y -= 1; }
					const K = y % 100, J = Math.floor(y / 100);
					const h = (d + Math.floor((13 * (m + 1)) / 5) + K + Math.floor(K / 4) + Math.floor(J / 4) + 5 * J) % 7;
					return (h + 6) % 7; // 0=일 ~ 6=토
				}
				const isWeekendYMD = s => [0, 6].includes(dayOfWeekYMD(s));

				const today = new Date();
				const monthStart = new Date(today.getFullYear(), today.getMonth(), 1);
				const nextMonthStart = new Date(today.getFullYear(), today.getMonth() + 1, 1);

				calendar = new FullCalendar.Calendar(calendarEl, {
					initialView: 'dayGridMonth',
					locale: 'ko',
					initialDate: today,
					headerToolbar: { left: '', center: 'title', right: '' },
					validRange: { start: monthStart, end: nextMonthStart },
					navLinks: false,
					editable: false,
					selectable: false,

					// 컴팩트 레이아웃
					height: 'auto',
					contentHeight: 300,   // 260~280으로 더 줄여도 됨
					aspectRatio: 1.2,     // 1.1~1.3 조절 가능
					expandRows: false,

					dayCellContent: (arg) => String(arg.date.getDate()),

					// 아이콘 렌더
					eventContent: function(info) {
						const wrap = document.createElement('div');
						wrap.className = 'fc-att-icons';
						(info.event.extendedProps?.icons || []).forEach(src => {
							const img = document.createElement('img');
							img.src = src;
							wrap.appendChild(img);
						});
						return { domNodes: [wrap] };
					},
					events: []
				});

				calendar.render();

				// 과거 출석 데이터 렌더
				norm.forEach((d, idx) => {
					const icons = [isWeekendYMD(d) ? '/image/water2.png' : '/image/water1.png'];
					const count = idx + 1;
					if (count === 10 || count === 20 || count === 30) icons.push('/image/fertilizer.png');
					upsertAttendanceEvent(d, icons);
				});

				calendarInitialized = true;
			})
			.catch(e => console.error('달력 초기화 실패:', e));
	}
}

function afterCheck(data) {
	const todayStr = ymd(new Date());
	const icons = [];
	icons.push(data.water === 2 ? '/image/water2.png' : '/image/water1.png');
	if (data.fertilizer) icons.push('/image/fertilizer.png');
	const uniqIcons = Array.from(new Set(icons));
	upsertAttendanceEvent(todayStr, uniqIcons);

	const totalEl = document.getElementById('totalAttendance');
	if (totalEl) totalEl.textContent = `총 출석일: ${data.totalAttendance}일`;

	const attendanceBtn = document.getElementById('attendanceBtn');
	if (attendanceBtn) {
		attendanceBtn.disabled = true;
		attendanceBtn.textContent = '출석 완료';
	}

	const container = document.getElementById('rewardIcons');
	if (container) {
		container.innerHTML = '';
		uniqIcons.forEach(src => {
			const img = document.createElement('img');
			img.src = src;
			/*img.style.width = '36px';
			img.style.height = '36px';
			img.style.display = 'block';*/
			container.appendChild(img);
		});
	}
	const rewardText = document.getElementById('rewardText');
	const rewardModal = document.getElementById('rewardModal');
	if (rewardText && rewardModal) {
		let msg = `물 ${data.water}개 지급!`;
		if (data.fertilizer) msg += ' + 비료 1개 지급!';
		rewardText.textContent = msg;
		rewardModal.classList.add('show');
	}
}

function openAttendanceModal() {
	const modal = document.getElementById('attendanceModal');
	if (modal) modal.classList.add('show');
	if (!calendarInitialized) initCalendar();

	const attendanceBtn = document.getElementById('attendanceBtn');
	const userId = document.getElementById('id')?.value;
	if (!attendanceBtn || !userId) return;

	axios.get('/attendance/today', {
		params: { user_Id: userId, _: Date.now() },
		headers: { Accept: 'application/json' }
	})
		.then(res => {
			const hasChecked = res.data;
			if (hasChecked) {
				attendanceBtn.disabled = true;
				attendanceBtn.textContent = '출석 완료';
				attendanceBtn.onclick = null;
				return;
			}
			attendanceBtn.disabled = false;
			attendanceBtn.textContent = '출석 체크';
			attendanceBtn.onclick = async function() {
				try {
					const { data } = await axios.post('/attendance/check', { user_Id: userId });
					if (!data || !data.success) {
						attendanceBtn.disabled = true;
						attendanceBtn.textContent = '출석 완료';
						return;
					}
					afterCheck(data);
				} catch (e) {
					attendanceBtn.disabled = true;
					attendanceBtn.textContent = '출석 완료';
				}
			};
		});
}

function closeAttendanceModal() {
	document.getElementById('attendanceModal')?.classList.remove('show');
}
function closeRewardModal() {
	document.getElementById('rewardModal')?.classList.remove('show');
}

window.addEventListener('keydown', e => {
	if (e.key === 'Escape') {
		closeAttendanceModal();
		closeRewardModal();
		closeInfoModal?.();
	}
});
window.addEventListener('click', e => {
	const am = document.getElementById('attendanceModal');
	const rm = document.getElementById('rewardModal');
	if (e.target === am) closeAttendanceModal();
	if (e.target === rm) closeRewardModal();
});
