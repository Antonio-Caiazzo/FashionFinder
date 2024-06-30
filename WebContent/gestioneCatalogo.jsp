<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<tr>
					<td scope="row" data-label="Id">1</td>
					<td data-label="Data">Maglia</td>
					<td data-label="Importo totale">Uomo</td>
					<td data-label="Dettagli">
						<button class="secondary">Modifica</button>
					</td>
				</tr>
				<tr>
					<td scope="row" data-label="Id">2</td>
					<td data-label="Data">Maglia</td>
					<td data-label="Importo totale">Uomo</td>
					<td data-label="Dettagli">
						<button class="secondary">Modifica</button>
					</td>
				</tr>
				<tr>
					<td scope="row" data-label="Id">3</td>
					<td data-label="Data">Maglia</td>
					<td data-label="Importo totale">Donna</td>
					<td data-label="Dettagli">
						<button class="secondary">Modifica</button>
					</td>
				</tr>
			</tbody>
		</table>
		<div style="width: 100%; display: flex; justify-content: center;">
			<button class="ordini-button secondary">Aggiungi prodotto</button>
		</div>


		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>