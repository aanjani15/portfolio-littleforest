(function() {
	let stomp = null;
	const $ = (id) => document.getElementById(id);
	const msgs = $('chatMsgs'), name = $('chatName'), text = $('chatText'),
		send = $('chatSend'), state = $('chatStatus');

	function connect() {
		const sock = new SockJS('/ws-chat');
		stomp = Stomp.over(sock);
		stomp.debug = null;
		stomp.connect({}, () => {
			state.textContent = '연결됨';
			stomp.subscribe('/topic/chat', (f) => {
				const m = JSON.parse(f.body);
				append(m.sender, m.content, m.ts);
			});
		}, () => { state.textContent = '연결 끊김, 재시도…'; setTimeout(connect, 1200); });
	}

	function sendMsg() {
		if (!stomp || !stomp.connected) return;
		const s = (name.value || 'admin').trim();
		const c = text.value.trim();
		if (!c) return;
		const payload = { sender: s, content: c, ts: Date.now() };
		stomp.send('/app/chat.send', {}, JSON.stringify(payload));
		append(s, c, payload.ts, true);
		text.value = ''; text.focus();
	}

	function append(sender, content, ts, mine) {
		const row = document.createElement('div'); row.className = 'msg' + (mine ? ' me' : '');
		const b = document.createElement('div'); b.className = 'msg-bubble';
		const meta = document.createElement('div'); meta.className = 'meta';
		meta.textContent = `${sender} · ${new Date(ts || Date.now()).toLocaleTimeString()}`;
		const body = document.createElement('div'); body.textContent = content;
		b.appendChild(meta); b.appendChild(body); row.appendChild(b);
		msgs.appendChild(row); msgs.scrollTop = msgs.scrollHeight;
	}

	send.addEventListener('click', sendMsg);
	text.addEventListener('keydown', e => { if (e.key === 'Enter') sendMsg(); });

	connect();
})();
