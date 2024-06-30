<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Utente"%>
<%
Object userObject = session.getAttribute("userRegistrato");
if (userObject instanceof Utente) {
	Utente user = (Utente) userObject;
	if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
		response.sendRedirect("index.jsp");
	}
}
%>

<jsp:useBean id="registeredUser" class="model.Utente" scope="session" />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>FashionFinder - Login</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main-content-container">
		<div class="auth-container">
			<div class="auth-container-header">
				<div class="auth-container-header-text">Accedi</div>
			</div>
			<form class="auth-form" action="CheckLogin" method="post">
				<input class="auth-input" id="email" type="email" name="email"
					placeholder="e-mail" autofocus /> <input class="auth-input"
					id="password" type="password" name="password"
					placeholder="password" />
				<button class="auth-button primary" type="submit">Accedi</button>
				<div class="auth-separator">
					<hr class="auth-separator-hr" />
					<div class="auth-separator-text">oppure</div>
					<hr class="auth-separator-hr" />
				</div>
				<button class="auth-button secondary" type="button"
					onclick="window.location.href='register.jsp'">Registrati</button>
			</form>
			<div>
				<%
				if (session.getAttribute("login-error") != null && (Boolean) session.getAttribute("login-error")) {
				%>
				<h4 style="color: red">Password e/o e-mail sbagliate o
					inesistenti. Riprova.</h4>
				<%
				}
				%>
			</div>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
