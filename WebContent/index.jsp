<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fashion Finder</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<%@ include file="./layout/pageTitle.jsp"%>
		<div class="page-subTitle">Uomo</div>
		<%@ include file="./components/card.jsp"%>
		<div class="page-subTitle">Donna</div>
	</div>

	<%@ include file="./layout/footer.jsp"%>
</body>
</html>