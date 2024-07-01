<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*, java.util.*, java.sql.SQLException"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Gestici Ordini</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<div class="ordini-container">
			<div class="ordini-column">
				<div class="ordini-column-title">Cerca per data</div>
				<form action="RicercaOrdiniServlet" method="post">
					<input type="hidden" name="action" value="data"> dal: <input
						type="date" name="fromDate" /> <br> <br> al: <input
						type="date" name="toDate" /><br>
					<br>
					<button type="submit" class="secondary">Cerca</button>
				</form>
			</div>
			<br>
			<div class="ordini-column">
				<div class="ordini-column-title">Cerca per nominativi</div>
				<form action="RicercaOrdiniServlet" method="post">
					<input type="hidden" name="action" value="nominativi">
					nome: <input type="text" name="nome" /><br> <br>
					cognome: <input type="text" name="cognome" /> <br>
					<br>
					<button type="submit" class="secondary">Cerca</button>
				</form>
			</div>
		</div>
		<table>
			<caption>Ordini</caption>
			<thead>
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Data</th>
					<th scope="col">Importo totale</th>
					<th scope="col">Dettagli</th>
				</tr>
			</thead>
			<tbody>
				<%
				@SuppressWarnings("unchecked")
				List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
				if (ordini != null) {
					for (Ordine ordine : ordini) {
				%>
				<tr>
					<td data-label="Id"><%=ordine.getCodice()%></td>
					<td data-label="Data"><%=ordine.getData()%></td>
					<td data-label="Importo totale"><%=ordine.getCostoTotale()%></td>
					<td data-label="Dettagli"><a
						href="OrdineDettagliServlet?ordineId=<%=ordine.getCodice()%>">dettagli</a></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="4">Nessun ordine trovato</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>

		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
