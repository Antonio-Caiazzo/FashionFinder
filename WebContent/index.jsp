<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="model.ProdottoDAO, model.Prodotto, java.util.Collection"%>
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
			<a href="${pageContext.request.contextPath}/prodotti?sesso=u">Esplora
				l'intero catalogo</a>
		</div>
		<div class="product-card-container-dashboard">
			<%
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			Collection<Prodotto> prodottiUomo = prodottoDAO.getPrimiSeiProdottiBySesso('u');
			for (Prodotto prodotto : prodottiUomo) {
			%>
			<div class="product-card">
				<div class="product-card-image-container">
					<img alt="t" class="product-card-image"
						src="<%=request.getContextPath() + "/images/" + prodotto.getImmagine()%>">
				</div>
				<div class="product-info">
					<div class="product-card-image-title"><%=prodotto.getNome()%></div>
					<div class="product-card-image-subTitle"><%=prodotto.getDescrizione()%></div>
					<div class="product-card-image-price"><%=prodotto.getCosto()%>€
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>

		<div class="page-subTitle">Donna</div>
		<div class="page-subTitle-extra">
			<a href="${pageContext.request.contextPath}/prodotti?sesso=d">Esplora
				l'intero catalogo</a>
		</div>
		<div class="product-card-container-dashboard">
			<%
			Collection<Prodotto> prodottiDonna = prodottoDAO.getPrimiSeiProdottiBySesso('d');
			for (Prodotto prodotto : prodottiDonna) {
			%>
			<div class="product-card">
				<div class="product-card-image-container">
					<img alt="t" class="product-card-image"
						src="<%=request.getContextPath() + "/images/" + prodotto.getImmagine()%>">
				</div>
				<div class="product-info">
					<div class="product-card-image-title"><%=prodotto.getNome()%></div>
					<div class="product-card-image-subTitle"><%=prodotto.getDescrizione()%></div>
					<div class="product-card-image-price"><%=prodotto.getCosto()%>€
					</div>
				</div>
			</div>
			<%
			}
			%>
		</div>

		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
