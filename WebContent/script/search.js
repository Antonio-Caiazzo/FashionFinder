document.addEventListener('DOMContentLoaded', (event) => {
	const toggleMenu = document.querySelector(".toggle-menu");
	const navbarContainerCenter = document.querySelector(".navbar-container-center");
	const navbarContainerRight = document.querySelector(".navbar-container-right");

	toggleMenu.addEventListener("click", () => {
		navbarContainerCenter.classList.toggle("active");
		navbarContainerRight.classList.toggle("active");
	});

	let inputBox = document.querySelector(".input-box"),
		searchIcon = document.querySelector(".search"),
		closeIcon = document.querySelector(".close-icon");

	searchIcon.addEventListener("click", () => {
		inputBox.classList.add("open");
	});

	closeIcon.addEventListener("click", () => {
		inputBox.classList.remove("open");
	});

	// verifica latp delclient
	document.querySelector('form[name="productForm"]').addEventListener('submit', function(e) {
		const costo = document.querySelector('input[name="costo"]').value;
		const sesso = document.querySelector('select[name="sesso"]').value;
		const immagine = document.querySelector('input[name="immagine"]');
		const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
		const errorMessage = document.querySelector('.error-message');

		if (!errorMessage) {
			const errorDiv = document.createElement('div');
			errorDiv.classList.add('error-message');
			document.querySelector('.auth-container').prepend(errorDiv);
		}

		errorMessage.innerHTML = '';

		if (costo < 0) {
			errorMessage.innerHTML += '<p>Il costo non può essere negativo.</p>';
			e.preventDefault();
		}

		if (sesso !== 'Uomo' && sesso !== 'Donna') {
			errorMessage.innerHTML += '<p>Seleziona un sesso valido.</p>';
			e.preventDefault();
		}

		if (immagine.value && !allowedExtensions.exec(immagine.value)) {
			errorMessage.innerHTML += '<p>Per favore carica un file immagine valido (jpg, jpeg, png, gif).</p>';
			immagine.value = '';
			e.preventDefault();
		}
	});
});
