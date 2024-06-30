<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Utente"%>
<%
Object userObject = session.getAttribute("userRegistrato");
if (userObject instanceof Utente) {
	Utente user = (Utente) userObject;
	if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
		response.sendRedirect("index.jsp");
		return;
	} else {
		response.sendRedirect("errorPage.jsp");
	}
}
%>

<jsp:useBean id="registeredUser" class="model.Utente" scope="session" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Registrazione</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="main-content-container">
		<div class="auth-container">
			<div class="auth-container-header">
				<div class="auth-container-header-text">Registrati</div>
			</div>
			<form class="auth-form" action="RegisterUser" method="post">
				<input class="auth-input" id="email" type="email" name="email"
					placeholder="E-mail" required autofocus /> <input
					class="auth-input" id="username" type="text" name="username"
					placeholder="Username" required /> <input class="auth-input"
					id="password" type="password" name="password"
					placeholder="Password" required /> <input class="auth-input"
					id="nome" type="text" name="nome" placeholder="Nome" required /> <input
					class="auth-input" id="cognome" type="text" name="cognome"
					placeholder="Cognome" required /> <input class="auth-input"
					id="data_di_nascita" type="date" name="data_di_nascita"
					placeholder="Data di Nascita" required />
				<button class="auth-button primary" type="submit">Registrati</button>
				<div class="auth-separator">
					<hr class="auth-separator-hr" />
					<div class="auth-separator-text">oppure</div>
					<hr class="auth-separator-hr" />
				</div>
				<button class="auth-button secondary" type="button"
					onclick="window.location.href='login.jsp'">Accedi</button>
			</form>
			<div>
				<%
				if (session.getAttribute("registration-error") != null && (Boolean) session.getAttribute("registration-error")) {
				%>
				<h4 style="color: red">Utente già registrato. Riprova.</h4>
				<%
				}
				%>
			</div>
		</div>
		<%
		userObject = session.getAttribute("userRegistrato");
		if (userObject instanceof Utente) {
			Utente user = (Utente) userObject;
			if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
				response.sendRedirect("index.jsp");
			}
		}
		%>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
