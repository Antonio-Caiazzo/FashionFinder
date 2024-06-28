<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection"%>
<%@ page import="model.Prodotto"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Uomo</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<%@ include file="./layout/pageTitle.jsp"%>
		<div class="product-card-container">
			<%
			Object prodottiObject = request.getAttribute("prodotti");
			if (prodottiObject instanceof Collection) {
				Collection<?> prodotti = (Collection<?>) prodottiObject;
				for (Object obj : prodotti) {
					if (obj instanceof Prodotto) {
				Prodotto prodotto = (Prodotto) obj;
			%>
			<div class="product-card">
				<div class="product-card-image-container">
					<img alt="<%=prodotto.getNome()%>" class="product-card-image"
						src="<%=request.getContextPath() + "/images/" + prodotto.getImmagine()%>">
				</div>
				<div class="product-info">
					<div class="product-card-image-title"><%=prodotto.getNome()%></div>
					<div class="product-card-image-subTitle"><%=prodotto.getDescrizione()%></div>
					<div class="product-card-image-price"><%=prodotto.getCosto()%>
						€
					</div>
				</div>
			</div>
			<%
			}
			}
			} else {
			response.sendRedirect("errorPage.jsp");
			}
			%>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
