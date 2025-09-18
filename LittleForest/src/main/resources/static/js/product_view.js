
async function payment() {
		// 선택된 라디오 버튼 가져오기
		const selected = document.querySelector('input[name="selectedWallet"]:checked');

		// 선택 안 한 경우 경고
		if (!selected) {
			alert('결제할 계좌를 선택해주세요.');
			return;
		}

		const wallet_Id = Number(selected.value); 
		const product_Id = Number(document.getElementById('pageMeta').dataset.productId);
		const price       = Number(document.getElementById('pageMeta').dataset.price);
			
		console.log('PRODUCT_ID:', product_Id);
		console.log('PRICE:', price);
		console.log('WALLET_ID:', wallet_Id);
		// 이후 원하는 동작 수행 (예: 서버 요청, 페이지 이동 등)

		// 예시: fetch로 서버에 POST 요청
		const amount = 5200;
	
		const merchantUid = 'littleForest_Payment' + Date.now();
		
		/*// 카카오페이 결제창 호출
		const IMP = window.IMP;
		try {IMP?.init?.('imp76384566');} catch (_) { }
		IMP.request_pay({
			pg: 'kakaopay',
			pay_method: 'card',
			merchant_uid: merchantUid,
			name: '결제하기',
			amount: price
		}, async (rsp) => {
			if (!rsp.success) {

				alert('결제가 취소되었거나 실패했습니다.\n' + (rsp.error_msg || ''));
				return;
			}*/
			
			try {
				// 서버 검증 + DB 적립
				const res = await fetch('/product/payment/complete', {
					method: 'POST',
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					body: new URLSearchParams({
						wallet_Id: wallet_Id,
						product_Id: product_Id,
						amount: price,
						type: 'deoisit',
						description: '리틀포레스트 샵 이용'
						
					})
				});
	
				const text = await res.text();
				if (!res.ok) throw new Error(text || '충전 실패');
	
				// 잔액 갱신
				const newBalance = Number(text);
				const balEl = document.getElementById('pointBalance');
				if (balEl) balEl.textContent = newBalance.toLocaleString() + '원';
	
			} catch (e) {
				alert(e.message);
			}
			
			const res = await fetch('/wallet/desBalance', {
		        method: 'POST',
		        headers: { 'Content-Type': 'application/json' },
		        body: JSON.stringify({ wallet_Id, amount })
		      });
		      if (!res.ok) {
		        const msg = await res.text();
		        throw new Error(msg || '잔액 감소 중 오류가 발생했습니다.');
		      }
				  
			alert("결제가 완료되었습니다!");
			window.location.reload();
							
						
			fetch('/api/payment', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify({ accountNumber: accountNumber })
			})
			.then(response => response.json())
			.then(result => {
				alert('결제가 완료되었습니다!');
				console.log(result);
			})
			.catch(error => {
				console.error('결제 중 오류 발생:', error);
				alert('결제에 실패했습니다.');
			});
		// 또는 페이지 이동 예시:
		// window.location.href = `/payment/confirm?account=${accountNumber}`;
}