<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*, java.util.*, java.sql.SQLException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Dettagli Ordine</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<%
		Ordine ordine = (Ordine) request.getAttribute("ordine");
		@SuppressWarnings("unchecked")
		List<Contiene> contieneList = (List<Contiene>) request.getAttribute("contieneList");
		%>
		<table>
			<caption>
				ORDINE #<%=ordine != null ? ordine.getCodice() : "Non disponibile"%></caption>
			<thead>
				<tr>
					<th scope="col">Prodotto</th>
					<th scope="col">Quantità</th>
					<th scope="col">Prezzo</th>
				</tr>
			</thead>
			<tbody>
				<%
				double totalCost = 0;
				if (contieneList != null) {
					for (Contiene contiene : contieneList) {
						ProdottoDAO prodottoDAO = new ProdottoDAO();
						Prodotto prodotto = null;
						try {
					prodotto = prodottoDAO.doRetrieveByKeyEvenIfDeleted(contiene.getProdottoCodice());
						} catch (SQLException e) {
					e.printStackTrace();
					response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
						}
						if (prodotto != null) {
					totalCost += contiene.getPrezzo() * contiene.getQuantita();
				%>
				<tr>
					<td data-label="Prodotto"><%=prodotto.getNome()%></td>
					<td data-label="Quantità"><%=contiene.getQuantita()%></td>
					<td data-label="Prezzo"><%=contiene.getPrezzo()%></td>
				</tr>
				<%
				} else {
				%>
				<tr>
					<td colspan="3">Prodotto non trovato per il codice: <%=contiene.getProdottoCodice()%></td>
				</tr>
				<%
				}
				}
				} else {
				%>
				<tr>
					<td colspan="3">Nessun elemento trovato per l'ordine.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<p>
			Totale prezzo dell'ordine:
			<%=String.format("%.2f", totalCost)%>
			€
		</p>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
