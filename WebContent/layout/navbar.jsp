<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Utente"%>
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
					<input type="text" placeholder="Cerca prodotto..." /> <span class="search">
						<i class="uil uil-search search-icon"></i>
					</span> <i class="uil uil-times close-icon"></i>
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

<script src="${pageContext.request.contextPath}/script/search.js"></script>


