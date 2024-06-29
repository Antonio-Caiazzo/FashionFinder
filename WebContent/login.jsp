<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="model.Utente"%>
<%
if (session.getAttribute("userRegistrato") != null) {
	Utente user = (Utente) session.getAttribute("userRegistrato");
	if (user.getEmail().compareTo("") != 0) {
		response.sendRedirect("index.jsp");
	}
}
%>
<jsp:useBean id="registeredUser" class="model.Utente" scope="session" />

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
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
					placeholder="enter login" autofocus /> <input class="auth-input"
					id="password" type="password" name="password"
					placeholder="enter password" />
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
	</div>
</body>
</html>
