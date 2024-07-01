<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Utente"%>
<%@ page import="model.ProdottoDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Prodotto"%>
<script src="${pageContext.request.contextPath}/script/search.js"></script>

<link rel="stylesheet"
	href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
<nav class="navbar-container">
	<div class="navbar-logo">
		<a href="${pageContext.request.contextPath}/index.jsp">FashionFinder</a>
	</div>

	<div class="navbar-container-center">
		<div class="navbar-container-center-element">
			<a href="${pageContext.request.contextPath}/prodotti?sesso=u">Uomo</a>
		</div>
		<div class="navbar-container-center-element">
			<a href="${pageContext.request.contextPath}/prodotti?sesso=d">Donna</a>
		</div>
	</div>

	<a href="#" class="toggle-menu"> <span class="bar"></span> <span
		class="bar"></span> <span class="bar"></span>
	</a>
	<div class="navbar-container-right">
		<div class="navbar-container-right-element">
			<form>
				<div class="input-box">
					<input type="text" id="search-input"
						placeholder="Cerca prodotto..." onkeyup="loadDoc()"
						data-context-path="${pageContext.request.contextPath}" /> <span
						class="search" onclick="openSearch()"> <i
						class="uil uil-search search-icon"></i>
					</span> <i class="uil uil-times close-icon" onclick="closeSearch()"></i>
					<div id="search-results"
						style="position: absolute; background-color: white; width: 100%; z-index: 1000;"></div>
				</div>
			</form>
		</div>
		<%
		Object userObject = session.getAttribute("userRegistrato");
		if (userObject instanceof Utente) {
			Utente user = (Utente) userObject;
			if (user.getIsAdmin()) {
		%>
		<div class="dropdown">
			Account
			<ul class="dropdown-menu">
				<li><a href="${pageContext.request.contextPath}/ordini.jsp">I
						miei ordini</a></li>
				<li><a
					href="${pageContext.request.contextPath}/gestioneCatalogo.jsp">Gestione
						catalogo</a></li>
				<li><a
					href="${pageContext.request.contextPath}/ordiniAdmin.jsp">Ordini</a></li>
				<li><a href="${pageContext.request.contextPath}/Logout">Logout</a></li>
			</ul>
		</div>
		<%
		} else {
		%>
		<div class="dropdown">
			Account
			<ul class="dropdown-menu">
				<li><a href="${pageContext.request.contextPath}/ordini.jsp">I
						miei ordini</a></li>
				<li><a href="${pageContext.request.contextPath}/Logout">Logout</a></li>
			</ul>
		</div>
		<%
		}
		} else {
		%>
		<div class="dropdown">
			<a href="${pageContext.request.contextPath}/login.jsp">Accedi</a>
		</div>
		<%
		}
		%>
		<div class="navbar-container-right-element">
			<a href="${pageContext.request.contextPath}/carrello.jsp">Carrello</a>
		</div>
	</div>
</nav>

<script>
function loadDoc() {
    const xhttp = new XMLHttpRequest();
    const query = document.getElementById("search-input").value;
    const contextPath = document.getElementById("search-input").getAttribute('data-context-path');
    if (query.length > 0) {
        xhttp.onload = function() {
            document.getElementById("search-results").innerHTML = this.responseText;
            attachClickEvent();
        }
        xhttp.open("GET", contextPath + "/search.jsp?query=" + query, true);
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
</script>
