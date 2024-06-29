const toggleMenu = document.getElementsByClassName("toggle-menu")[0];
const navbarContainerCenter = document.getElementsByClassName(
	"navbar-container-center"
)[0];
const navbarContainerRight = document.getElementsByClassName(
	"navbar-container-right"
)[0];

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