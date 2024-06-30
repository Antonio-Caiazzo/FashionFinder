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


});
