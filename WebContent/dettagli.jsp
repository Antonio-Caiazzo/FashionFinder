<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Prodotto"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - <%
Prodotto prodottoTitle = (Prodotto) request.getAttribute("prodotto");
if (prodottoTitle != null) {
	out.print(prodottoTitle.getNome());
} else {
	out.print("Dettagli Prodotto");
}
%>
</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<div class="product-detail-container">
			<%
			Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
			if (prodotto != null) {
			%>
			<img alt="<%=prodotto.getNome()%>" class="product-detail-image"
				src="<%=request.getContextPath() + "/images/" + prodotto.getImmagine()%>">
			<div class="product-detail-info-container">
				<div class="product-detail-info-title"><%=prodotto.getNome()%></div>
				<div class="product-detail-info-description"><%=prodotto.getDescrizione()%></div>

				<div class="product-detail-category-container">
					<div class="product-detail-category-title">Categoria</div>
					<div class="product-detail-category-subtitle"><%=prodotto.getCategoria()%></div>
				</div>

				<div class="product-detail-gender-container">
					<div class="product-detail-gender-title">Sesso</div>
					<div class="product-detail-gender-subtitle">
						<%
						char sessoChar = prodotto.getSesso();
						String sesso = String.valueOf(sessoChar);
						if (sesso.equalsIgnoreCase("u")) {
							out.println("Uomo");
						} else if (sesso.equalsIgnoreCase("d")) {
							out.println("Donna");
						} else {
							out.println("Non specificato");
						}
						%>
					</div>
				</div>

				<div class="product-detail-price-container">
					<div class="product-detail-price-title">Prezzo</div>
					<div class="product-detail-price-subtitle"><%=prodotto.getCosto()%>
						€
					</div>
				</div>
				<form action="CarrelloServlet" method="post">
					<input type="hidden" name="action" value="add"> <input
						type="hidden" name="codice" value="<%=prodotto.getCodice()%>">
					<input type="hidden" name="quantity" value="1">
					<button type="submit" class="product-detail-button secondary">Aggiungi
						al carrello</button>
				</form>
			</div>
			<%
			} else {
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
			}
			%>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
