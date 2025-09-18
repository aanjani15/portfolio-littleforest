function openInfoModal() {
	const im = document.getElementById('infoModal');
	if (im) im.classList.add('show');
}
function closeInfoModal() {
	const im = document.getElementById('infoModal');
	if (im) {
		im.classList.remove('show');
		im.style.display = '';
	}
}
