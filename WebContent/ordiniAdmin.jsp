<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<div class="oridini-container">
			<div class="ordini-column">
				<div class="ordini-column-title">Cerca per data</div>
				dal: <input type="date" /> al: <input type="date" />
				<button class="secondary">cerca</button>
			</div>

			<div class="ordini-column">
				<div class="ordini-column-title">Cerca per nominativi</div>
				nome: <input type="text" /> cognome: <input type="text" />
				<button class="secondary">cerca</button>
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
				<tr>
					<td data-label="Id">Visa - 3412</td>
					<td data-label="Data">04/01/2016</td>
					<td data-label="Importo Totale">$1,190</td>
					<td data-label="Dettagli"><a href="">dettagli</a></td>
				</tr>
				<tr>
					<td data-label="Id">Visa - 3412</td>
					<td data-label="Data">04/01/2016</td>
					<td data-label="Importo Totale">$1,190</td>
					<td data-label="Dettagli"><a href="">dettagli</a></td>
				</tr>
			</tbody>
		</table>


		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>