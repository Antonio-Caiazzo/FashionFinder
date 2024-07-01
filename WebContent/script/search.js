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

function loadDoc() {
    const xhttp = new XMLHttpRequest();
    const query = document.getElementById("search-input").value;
    if (query.length > 0) {
        xhttp.onload = function() {
            document.getElementById("search-results").innerHTML = this.responseText;
            attachClickEvent();
        }
        xhttp.open("GET", "search.jsp?query=" + query, true);
        xhttp.send();
    } else {
        document.getElementById("search-results").innerHTML = "";
    }
}

function attachClickEvent() {
    const items = document.querySelectorAll('#search-results div[data-id]');
    items.forEach(item => {
        item.addEventListener('click', () => {
            const productId = item.getAttribute('data-id');
            const contextPath = document.getElementById('search-input').getAttribute('data-context-path');
            window.location.href = contextPath + '/dettagliProdotto?codice=' + productId;
        });
    });
}

function openSearch() {
    document.querySelector(".input-box").classList.add("open");
    document.getElementById("search-results").style.display = "block";
}

function closeSearch() {
    document.querySelector(".input-box").classList.remove("open");
    document.getElementById("search-results").style.display = "none";
}
