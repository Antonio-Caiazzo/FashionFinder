<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
			<input id="username" type="text" name="username" placeholder="enter login" autofocus >
			<br>
			<label for="password">Password</label>
			<input id="password" type="password" name="password" placeholder="enter password">
			<br>
			<input type="submit" value="login">
			<input type="reset" value="Reset">
		</fieldset>
	</form>
	
</body>
</html>