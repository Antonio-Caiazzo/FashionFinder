<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection"%>
<%@ page import="model.Prodotto"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Donna</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<%@ include file="./layout/pageTitle.jsp"%>
		<div class="product-card-container">
			<%
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			Collection<Prodotto> prodotti = prodottoDAO.getProdottiBySessoNonDeleted('d');
			for (Prodotto prodotto : prodotti) {
			%>
			<div class="product-card">
				<div class="product-card-image-container">
					<a
						href="<%=request.getContextPath()%>/dettagliProdotto?codice=<%=prodotto.getCodice()%>">
						<img alt="<%=prodotto.getNome()%>" class="product-card-image"
						src="<%=request.getContextPath() + "/images/" + prodotto.getImmagine()%>">
					</a>
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
			%>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
