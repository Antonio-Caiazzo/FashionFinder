<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Aggiungi Prodotto</title>
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/script/search.js"></script>
<style>
.error-message {
	color: red;
}
</style>
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
			<%
			if (request.getAttribute("errorMessage") != null) {
			%>
			<div class="error-message"><%=request.getAttribute("errorMessage")%></div>
			<%
			}
			%>
			<form class="auth-form" name="productForm"
				action="AggiungiProdottoServlet" method="post"
				enctype="multipart/form-data">
				<div class="auth-container-header-text">Prodotto</div>
				<input class="auth-input" type="text" name="nome" placeholder="Nome"
					required /> <input class="auth-input" type="text"
					name="descrizione" placeholder="Descrizione" required /> <input
					class="auth-input" type="number" step="0.01" name="costo"
					placeholder="Costo" required /> <select class="auth-input"
					name="sesso" required style="margin-bottom: 15px;">
					<option value="">Seleziona sesso</option>
					<option value="Uomo">Uomo</option>
					<option value="Donna">Donna</option>
				</select> <input class="auth-input" type="file" name="immagine"
					placeholder="Immagine" accept="image/*" required /> <input
					class="auth-input" type="text" name="categoria"
					placeholder="Categoria" required />
				<button class="auth-button secondary" type="submit">Aggiungi
					Prodotto</button>
			</form>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
