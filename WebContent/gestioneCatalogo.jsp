<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*, java.util.*, java.sql.SQLException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Gestione Catalogo</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<table>
			<caption>Catalogo</caption>
			<thead>
				<tr>
					<th scope="col">Id Prodotto</th>
					<th scope="col">Nome Prodotto</th>
					<th scope="col">Sesso</th>
					<th scope="col">Azione</th>
				</tr>
			</thead>
			<tbody>
				<%
				ProdottoDAO prodottoDAO = new ProdottoDAO();
				List<Prodotto> prodotti = null;
				try {
					prodotti = prodottoDAO.doRetrieveAll(null);
				} catch (SQLException e) {
					response.sendRedirect("errorPage.jsp");
				}
				if (prodotti != null) {
					for (Prodotto prodotto : prodotti) {
				%>
				<tr>
					<td scope="row" data-label="Id"><%=prodotto.getCodice()%></td>
					<td data-label="Nome Prodotto"><%=prodotto.getNome()%></td>
					<td data-label="Sesso"><%=prodotto.getSesso()%></td>
					<td data-label="Azione"><a
						href="modificaProdotto.jsp?prodottoId=<%=prodotto.getCodice()%>">
							<button class="secondary">Modifica</button>
					</a></td>
				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
		<div style="width: 100%; display: flex; justify-content: center;">
			<a href="aggiungiProdotto.jsp">
				<button class="ordini-button secondary">Aggiungi prodotto</button>
			</a>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
