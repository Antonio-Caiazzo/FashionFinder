<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Aggiungi Prodotto</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<div class="auth-container">
			<div class="auth-container-header">
				<div class="auth-container-header-text">
					<h2>Aggiungi Prodotto</h2>
				</div>
			</div>

			<form class="auth-form" action="AggiungiProdottoServlet"
				method="post">
				<div class="auth-container-header-text">Prodotto</div>
				<input class="auth-input" type="text" name="nome" placeholder="Nome"
					required /> <input class="auth-input" type="text"
					name="descrizione" placeholder="Descrizione" required /> <input
					class="auth-input" type="number" step="0.01" name="costo"
					placeholder="Costo" required /> <input class="auth-input"
					type="text" name="sesso" placeholder="Sesso (u/d)" required /> <input
					class="auth-input" type="text" name="immagine"
					placeholder="Immagine" required /> <input class="auth-input"
					type="text" name="categoria" placeholder="Categoria" required />
				<button class="auth-button secondary" type="submit">Aggiungi
					Prodotto</button>
			</form>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
