(async function() {
	// 변수 선언
	const yearSelect = document.getElementById('year-select');
	const monthSelect = document.getElementById('month-select');
	let currentYear = new Date().getFullYear();
	let currentMonth = new Date().getMonth() + 1;

	// 서버에서 탄소 배출량 데이터 가져오기
	async function fetchEmissions(year, month) {
		try {
			const res = await fetch(`/api/emissions?year=${year}&month=${month}`);
			if (!res.ok) throw new Error('네트워크 오류');
			return await res.json();
		} catch (e) {
			console.error('emission 데이터 로드 실패:', e);
			return {};
		}
	}

	// 서버에서 결제 금액 데이터 가져오기
	async function fetchPayments(year, month) {
		try {
			const res = await fetch(`/api/payments?year=${year}&month=${month}`);
			if (!res.ok) throw new Error('네트워크 오류');
			return await res.json();
		} catch (e) {
			console.error('payment 데이터 로드 실패:', e);
			return {};
		}
	}

	// 연도 드롭다운 옵션 채우기
	function populateYearSelect() {
		const startYear = currentYear - 10;
		const endYear = currentYear + 10;
		for (let y = startYear; y <= endYear; y++) {
			const option = document.createElement('option');
			option.value = y;
			option.textContent = y + '년';
			if (y === currentYear) option.selected = true;
			yearSelect.appendChild(option);
		}
	}

	// 월 드롭다운 옵션 채우기
	function populateMonthSelect() {
		for (let m = 1; m <= 12; m++) {
			const option = document.createElement('option');
			option.value = m;
			option.textContent = m + '월';
			if (m === currentMonth) option.selected = true;
			monthSelect.appendChild(option);
		}
	}

	// 달력 그리기 함수 (탄소, 결제 금액 모두 표시)
	async function drawCalendar(year, month) {
		const calendarDiv = document.getElementById('calendar');
		calendarDiv.innerHTML = '';

		// 서버에서 데이터 병렬 요청
		const [carbonData, paymentData] = await Promise.all([
			fetchEmissions(year, month),
			fetchPayments(year, month)
		]);

		const table = document.createElement('table');

		// 달력 헤더 (요일)
		const thead = document.createElement('thead');
		const headerRow = document.createElement('tr');
		['일', '월', '화', '수', '목', '금', '토'].forEach(dayName => {
			const th = document.createElement('th');
			th.textContent = dayName;
			headerRow.appendChild(th);
		});
		thead.appendChild(headerRow);
		table.appendChild(thead);

		// 달력 본문 (날짜)
		const tbody = document.createElement('tbody');

		const firstDay = new Date(year, month - 1, 1);
		const lastDay = new Date(year, month, 0);

		const firstWeekday = firstDay.getDay();
		const lastDate = lastDay.getDate();

		let date = 1;
		let done = false;

		for (let week = 0; week < 6; week++) {
			const tr = document.createElement('tr');

			for (let day = 0; day < 7; day++) {
				const td = document.createElement('td');

				if (week === 0 && day < firstWeekday) {
					td.textContent = '';
				} else if (date > lastDate) {
					td.textContent = '';
					done = true;
				} else {
					const dateDiv = document.createElement('div');
					dateDiv.classList.add('date');
					dateDiv.textContent = date;
					td.appendChild(dateDiv);

					// 탄소 배출량 있으면 표시
					if (carbonData[date]) {
						const carbonDiv = document.createElement('div');
						carbonDiv.classList.add('carbon');
						carbonDiv.textContent = `탄소: ${carbonData[date]}kg`;
						td.appendChild(carbonDiv);
					}
					// 결제 금액 있으면 표시
					if (paymentData[date]) {
						const paymentDiv = document.createElement('div');
						paymentDiv.classList.add('payment');
						paymentDiv.textContent = `사용금액: ${paymentData[date].toLocaleString()}원`;
						td.appendChild(paymentDiv);
					}

					date++;
				}

				tr.appendChild(td);
			}

			tbody.appendChild(tr);
			if (done) break;
		}

		table.appendChild(tbody);
		calendarDiv.appendChild(table);
	}

	// 드롭다운 이벤트 등록
	yearSelect.addEventListener('change', () => {
		currentYear = parseInt(yearSelect.value);
		drawCalendar(currentYear, currentMonth);
	});
	monthSelect.addEventListener('change', () => {
		currentMonth = parseInt(monthSelect.value);
		drawCalendar(currentYear, currentMonth);
	});

	// 초기 드롭다운 생성 및 달력 표시
	populateYearSelect();
	populateMonthSelect();
	await drawCalendar(currentYear, currentMonth);

})();
