<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*, java.util.*, java.sql.SQLException"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Ordini</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<table>
			<caption>I miei ordini</caption>
			<thead>
				<tr>
					<th scope="col">ID Ordine</th>
					<th scope="col">Data</th>
					<th scope="col">Prezzo Totale</th>
					<th scope="col">Dettagli</th>
				</tr>
			</thead>
			<tbody>
				<%
				Utente userRegistrato = (Utente) session.getAttribute("userRegistrato");
				if (userRegistrato != null) {
					OrdineDAO ordineDAO = new OrdineDAO();
					try {
						List<Ordine> ordini = ordineDAO.doRetrieveByUser(userRegistrato.getEmail());
						for (Ordine ordine : ordini) {
				%>
				<tr>
					<td data-label="Id"><%=ordine.getCodice()%></td>
					<td data-label="Data"><%=ordine.getData()%></td>
					<td data-label="Importo Totale"><%=ordine.getCostoTotale()%></td>
					<td data-label="Dettagli"><a
						href="OrdineDettagliServlet?ordineId=<%=ordine.getCodice()%>">dettagli</a></td>
				</tr>
				<%
				}
				} catch (SQLException e) {
				response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
				}
				} else {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
				}
				%>
			</tbody>
		</table>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
