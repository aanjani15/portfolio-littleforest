Chart.defaults.font.family = `"Inter", system-ui, -apple-system, Segoe UI, Roboto, "Noto Sans KR", sans-serif`;
Chart.defaults.color = "#334155";
Chart.defaults.borderColor = "rgba(15,23,42,.08)";
Chart.defaults.plugins.legend.labels.boxWidth = 14;
Chart.defaults.maintainAspectRatio = false;


// 유틸: 캔버스 그라디언트
function makeGrad(ctx, c1 = "#4f46e5", c2 = "#22d3ee") {
	const g = ctx.createLinearGradient(0, 0, 0, 220);
	g.addColorStop(0, c1); g.addColorStop(1, c2);
	return g;
}
const fmt = n => (n ?? 0).toLocaleString();

// 타입별 한글 라벨 매핑
const labelMap = {
	"CHARGE": "충전",
	"DONATE": "기부",
	"EARN": "적립",
	"GIFT": "선물",
	"SPEND": "사용"
};

// 데이터 로드
document.addEventListener("DOMContentLoaded", async () => {
	const res = await fetch("/admin/stats/all");
	const data = await res.json();
	console.log("📊 전체 데이터", data);

	// ===== KPI =====
	document.querySelector("#kpi-users").innerText = fmt(data.users);
	document.querySelector("#kpi-growing").innerText = fmt(data.growingTrees);
	document.querySelector("#kpi-grown").innerText = fmt(data.grownTrees);
	document.querySelector("#kpi-mau").innerText = fmt(data.mau);

	// ===== 월별 신규 가입자 =====
	(() => {
		const el = document.getElementById("chNewUsers");
		const ctx = el.getContext("2d");
		new Chart(el, {
			type: "line",
			data: {
				labels: (data.monthlyNewUsers ?? []).map(r => r.ym),
				datasets: [{
					label: "신규 가입자",
					data: (data.monthlyNewUsers ?? []).map(r => r.cnt),
					borderColor: "#4f46e5",
					backgroundColor: (ctx) => {
						const g = ctx.chart.ctx.createLinearGradient(0, 0, 0, 220);
						g.addColorStop(0, "rgba(79,70,229,.35)");
						g.addColorStop(1, "rgba(79,70,229,0)");
						return g;
					},
					tension: .35,
					borderWidth: 2.4,
					fill: true,
					pointRadius: 2.5
				}]
			},
			options: {
				scales: {
					x: { ticks: { maxRotation: 0, autoSkip: true } },
					y: { ticks: { callback: v => Math.round(v).toLocaleString() } }
				},
				plugins: { legend: { display: false } }
			}
		});
	})();

	// ===== DAU / WAU / MAU =====
	(() => {
		const el = document.getElementById("chActive");
		new Chart(el, {
			type: "bar",
			data: {
				labels: ["일간", "주간", "월간"],
				datasets: [{
					label: "활성 사용자",
					data: [data.dau, data.wau, data.mau],
					backgroundColor: "rgba(79,70,229,.45)",
					borderColor: "#4f46e5",
					borderWidth: 1.5,
					borderRadius: 10
				}]
			},
			options: {
				scales: {
					y: { ticks: { callback: v => Math.round(v).toLocaleString() } }
				},
				plugins: { legend: { display: false } }
			}
		})
	})();

	// ===== 요일별 출석 =====
	(() => {
		const el = document.getElementById("chWeekday");
		new Chart(el, {
			type: "bar",
			data: {
				labels: (data.weekdayAttendance ?? []).map(r => r.dow),
				datasets: [{
					label: "출석 수",
					data: (data.weekdayAttendance ?? []).map(r => r.cnt),
					backgroundColor: "rgba(245,158,11,.45)",
					borderColor: "#f59e0b",
					borderWidth: 1.5,
					borderRadius: 10
				}]
			},
			options: { plugins: { legend: { display: false } } }
		});
	})();

	// ===== 연속 출석 TOP10 =====
	/*(() => {
		const tb = document.querySelector("#tbStreak");
		tb.innerHTML = `<thead><tr><th>유저ID</th><th>시작</th><th>종료</th><th>일수</th></tr></thead><tbody></tbody>`;
		const body = tb.querySelector("tbody");
		(data.topStreaks ?? []).forEach(r => {
			const tr = document.createElement("tr");
			tr.innerHTML = `<td>${r.userId}</td><td>${r.startDate}</td><td>${r.endDate}</td><td>${r.days}</td>`;
			body.appendChild(tr);
		});
	})();*/

	// ===== 나무 레벨 분포 =====
	(() => {
		const el = document.getElementById("chLevels");
		new Chart(el, {
			type: "doughnut",
			data: {
				labels: (data.treeLevels ?? []).map(r => "레벨 " + r.treeLevel),
				datasets: [{
					data: (data.treeLevels ?? []).map(r => r.cnt),
					backgroundColor: ["#f43f5e", "#fb923c", "#facc15", "#22c55e", "#6366f1", "#a78bfa"],
					borderWidth: 0,
				}]
			},
			options: {
				cutout: "68%",
				plugins: { legend: { position: "bottom" } }
			}
		});
	})();

	// ===== 월별 완성 나무 =====
	(() => {
		const el = document.getElementById("chGrownMonthly");
		new Chart(el, {
			type: "line",
			data: {
				labels: (data.grownMonthly ?? []).map(r => r.ym),
				datasets: [{
					label: "완료된 나무",
					data: (data.grownMonthly ?? []).map(r => r.cnt),
					borderColor: "#16a34a",
					tension: .35,
					borderWidth: 2.4,
					pointRadius: 2.5,
					fill: false
				}]
			},
			options: { plugins: { legend: { display: false } } }
		});
	})();

	// ===== 포인트 (월/유형별) =====
	(() => {
		const rows = data.pointMonthlyByType ?? [];
		const labels = [...new Set(rows.map(r => r.ym))];
		const types = [...new Set(rows.map(r => r.type))];
		const colors = ["#6366f1", "#22d3ee", "#a78bfa", "#f59e0b", "#ef4444", "#10b981"];

		const datasets = types.map((t, i) => ({
			label: labelMap[t] || t,   // ✅ 한글 변환
			data: labels.map(ym => (rows.find(r => r.ym === ym && r.type === t)?.amount) ?? 0),
			backgroundColor: colors[i % colors.length],
			borderRadius: 10
		}));

		new Chart(document.getElementById("chPointType"), {
			type: "bar",
			data: { labels, datasets },
			options: {
				scales: {
					x: { stacked: true },
					y: { stacked: true, ticks: { callback: v => Math.round(v).toLocaleString() } }
				}
			}
		})
	})();

	// ===== 선물 TOP (보낸/받은) =====
	(() => {
		const tbS = document.querySelector("#tbGiftSent");
		const tbR = document.querySelector("#tbGiftRecv");
		tbS.innerHTML = `<thead><tr><th>순위</th><th>유저ID</th><th>포인트</th></tr></thead><tbody></tbody>`;
		tbR.innerHTML = `<thead><tr><th>순위</th><th>유저ID</th><th>포인트</th></tr></thead><tbody></tbody>`;
		const bs = tbS.querySelector("tbody"), br = tbR.querySelector("tbody");
		(data.giftTopSent ?? []).forEach((r, i) => {
			const tr = document.createElement("tr"); tr.innerHTML = `<td>${i + 1}</td><td>${r.sender}</td><td>${fmt(r.amount)}</td>`; bs.appendChild(tr);
		});
		(data.giftTopReceived ?? []).forEach((r, i) => {
			const tr = document.createElement("tr"); tr.innerHTML = `<td>${i + 1}</td><td>${r.receiver}</td><td>${fmt(r.amount)}</td>`; br.appendChild(tr);
		});
	})();

	// ===== 카테고리별 매출 · 탄소 효과 =====
	(() => {
		const el = document.getElementById("chSalesCarbon");
		const labels = (data.salesByCategory ?? []).map(r => r.category);
		const sales = (data.salesByCategory ?? []).map(r => r.sales);
		const carbon = (data.salesByCategory ?? []).map(r => r.carbonEffect);
		const ctx = el.getContext("2d");
		new Chart(el, {
			data: {
				labels,
				datasets: [
					{ type: "bar", label: "매출", data: sales, backgroundColor: makeGrad(ctx, "rgba(79,70,229,.55)", "rgba(34,211,238,.55)"), borderRadius: 10 },
					{ type: "line", label: "탄소 효과", data: carbon, yAxisID: "y1", borderColor: "#ef4444", pointRadius: 2.5, tension: .35 }
				]
			},
			options: {
				scales: {
					y: { position: "left", ticks: { callback: v => Math.round(v).toLocaleString() } },
					y1: { position: "right", grid: { drawOnChartArea: false } }
				}
			}
		})
	})();

	// ===== 월별 탄소 절감 =====
	(() => {
		const el = document.getElementById("chEmission");
		new Chart(el, {
			type: "line",
			data: {
				labels: (data.emissionMonthly ?? []).map(r => r.ym),
				datasets: [{
					label: "탄소 절감",
					data: (data.emissionMonthly ?? []).map(r => r.total),
					borderColor: "#ef4444", borderWidth: 2.4, tension: .35, pointRadius: 2.5,
					backgroundColor: (ctx) => {
						const g = ctx.chart.ctx.createLinearGradient(0, 0, 0, 220);
						g.addColorStop(0, "rgba(239,68,68,.28)");
						g.addColorStop(1, "rgba(239,68,68,0)");
						return g;
					},
					fill: true
				}]
			},
			options: { plugins: { legend: { display: false } } }
		})
	})();

	// ===== 커뮤니티 지표 (게시글 = 라인, 댓글/좋아요 = 바) =====
	(() => {
		const labels = (data.communityPosts ?? []).map(r => r.ym);
		const posts = (data.communityPosts ?? []).map(r => r.cnt);
		const comments = (data.communityComments ?? []).map(r => r.cnt);
		const likes = (data.communityLikes ?? []).map(r => r.cnt);

		new Chart(document.getElementById("chCommunity"), {
			data: {
				labels,
				datasets: [
					// 게시글 → 라인 그래프
					{
						type: "line",
						label: "게시글",
						data: posts,
						borderColor: "#3b82f6",
						borderWidth: 2.5,
						tension: .35,
						pointRadius: 3,
						pointBackgroundColor: "#fff",
						fill: false,
						yAxisID: "y"
					},
					// 댓글 → 바 그래프
					{
						type: "bar",
						label: "댓글",
						data: comments,
						backgroundColor: "rgba(245,158,11,.55)",
						borderColor: "#f59e0b",
						borderWidth: 1.2,
						borderRadius: 6,
						yAxisID: "y1"
					},
					// 좋아요 → 바 그래프
					{
						type: "bar",
						label: "좋아요",
						data: likes,
						backgroundColor: "rgba(236,72,153,.55)",
						borderColor: "#ec4899",
						borderWidth: 1.2,
						borderRadius: 6,
						yAxisID: "y1"
					}
				]
			},
			options: {
				responsive: true,
				scales: {
					y: {
						position: "left",
						ticks: { callback: v => Math.round(v).toLocaleString() }
					},
					y1: {
						position: "right",
						grid: { drawOnChartArea: false },
						ticks: { callback: v => Math.round(v).toLocaleString() }
					}
				},
				plugins: {
					legend: { position: "top" }
				}
			}
		});
	})();



	// ===== 월별 기부 =====
	(() => {
		const labels = (data.donationMonthly ?? []).map(r => r.ym);
		const amount = (data.donationMonthly ?? []).map(r => r.total);
		const donors = (data.donationMonthly ?? []).map(r => r.donors);
		const el = document.getElementById("chDonation"); const ctx = el.getContext("2d");
		new Chart(el, {
			data: {
				labels,
				datasets: [
					{ type: "bar", label: "기부 금액", data: amount, backgroundColor: makeGrad(ctx, "rgba(250,204,21,.55)", "rgba(59,130,246,.55)"), borderRadius: 10 },
					{ type: "line", label: "기부자 수", data: donors, yAxisID: "y1", borderColor: "#0ea5e9", pointRadius: 2.5, tension: .35 }
				]
			},
			options: { scales: { y: { ticks: { callback: v => Math.round(v).toLocaleString() } }, y1: { position: "right", grid: { drawOnChartArea: false } } } }
		})
	})();
});
