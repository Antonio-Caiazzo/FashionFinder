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
		<div class="page-subTitle-extra">
			<a href="uomo.jsp">Esplora l'intero catalogo</a>
		</div>

		<%@ include file="./components/card.jsp"%>
		<div class="page-subTitle">Donna</div>
		<div class="page-subTitle-extra">
			<a href="donna.jsp">Esplora l'intero catalogo</a>

		</div>

		<%@ include file="./components/card.jsp"%>
		<%@ include file="./layout/footer.jsp"%>
	</div>


</body>
</html>