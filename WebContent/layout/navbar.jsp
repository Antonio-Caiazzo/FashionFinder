<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Utente"%>
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

	<div class="navbar-container-right">
		<div>Cerca</div>
		<div>
			<%
			Object userObject = session.getAttribute("userRegistrato");
			if (userObject instanceof Utente) {
				Utente user = (Utente) userObject;
			%>
			<a href="${pageContext.request.contextPath}/Logout">Logout</a>
			<%
			} else {
			%>
			<a href="${pageContext.request.contextPath}/login.jsp">Accedi</a>
			<%
			}
			%>
		</div>
		<div>
			<a href="${pageContext.request.contextPath}/carrello.jsp">Carrello</a>
		</div>
	</div>
</nav>
