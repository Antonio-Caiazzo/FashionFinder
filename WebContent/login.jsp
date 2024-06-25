<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<form action="Checklogin" method="post">
		<fieldset>
			
			<label for="username">Login</label>
			<input id="username" type="text" name="username" placeholder="enter login" class="LR" autofocus >
			<br>
			<label for="password">Password</label>
			<input id="password" type="password" name="password" placeholder="enter password" class="LR">
			<br>
			<input type="submit" value="login" class="LR">
			<input type="reset" value="Reset" class="LR">
		</fieldset>
	</form>
	
</body>
</html>