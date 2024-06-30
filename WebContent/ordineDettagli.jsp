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
		List<Contiene> contieneList = null;
		Object contieneListObj = request.getAttribute("contieneList");
		if (contieneListObj instanceof List<?>) {
			List<?> tempList = (List<?>) contieneListObj;
			boolean validList = true;
			for (Object item : tempList) {
				if (!(item instanceof Contiene)) {
			validList = false;
			break;
				}
			}
			if (validList) {
				@SuppressWarnings("unchecked")
				List<Contiene> checkedList = (List<Contiene>) tempList;
				contieneList = checkedList;
			}
		}
		%>
		<table>
			<caption>
				ORDINE #<%=ordine.getCodice()%></caption>
			<thead>
				<tr>
					<th scope="col">Prodotto nome</th>
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
					prodotto = prodottoDAO.doRetrieveByKey(contiene.getProdottoCodice());
						} catch (SQLException e) {
					response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
						}
						if (prodotto != null) {
					totalCost += prodotto.getCosto() * contiene.getQuantita();
				%>
				<tr>
					<td data-label="Prodotto"><%=prodotto.getNome()%></td>
					<td data-label="Quantità"><%=contiene.getQuantita()%></td>
					<td data-label="Prezzo"><%=prodotto.getCosto()%></td>
				</tr>
				<%
				}
				}
				}
				%>
			</tbody>
		</table>
		<p>
			Totale prezzo dell'ordine: $<%=totalCost%></p>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
