
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="model.Utente"%>
<% if (session.getAttribute("userRegistrato") != null) {
		Utente user = (Utente) session.getAttribute("userRegistrato");
		if(user.getEmail().compareTo("") != 0) {
			response.sendRedirect("index.jsp");
		}
	}
%>
<jsp:useBean id="registeredUser" class="model.Utente" scope="session"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<form action="CheckLogin" method="post">
		<fieldset>
			
			<label for="email">Login</label>
			<input id="email" type="email" name="email" placeholder="enter login" autofocus >
			<br>
			<label for="password">Password</label>
			<input id="password" type="password" name="password" placeholder="enter password">
			<br>
			<input type="submit" value="login">
			<input type="reset" value="Reset">
		</fieldset>
	</form>
	
	<div>
		<% if(session.getAttribute("login-error") != null) { %>
		<h4 style="color: red">Password e/o e-mail sbagliate o inesistenti. Riprova.</h4>
		<% } %>
	</div>
	
</body>
</html>