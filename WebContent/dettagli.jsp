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
		<div class="">
			<%
			Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
			if (prodotto != null) {
			%>
			<div class="">
				<div class="">
					<img alt="<%=prodotto.getNome()%>" class=""
						src="<%=request.getContextPath() + "/images/" + prodotto.getImmagine()%>">
				</div>
				<div class="">
					<h2><%=prodotto.getNome()%></h2>
					<p><%=prodotto.getDescrizione()%></p>
					<p>
						Prezzo:
						<%=prodotto.getCosto()%>
						€
					</p>
					<p>
						Sesso:
						<%
					char sessoChar = prodotto.getSesso();
					String sesso = String.valueOf(sessoChar);
					if (sesso.equalsIgnoreCase("u")) {
						out.println("Uomo");
					} else {
						out.println("D");
					}
					%>
					</p>
					<p>
						Categoria:
						<%=prodotto.getCategoria()%>
					</p>
				</div>
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
