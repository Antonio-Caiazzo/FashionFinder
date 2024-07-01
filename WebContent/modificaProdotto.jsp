<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*, java.util.*, java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Modifica Prodotto</title>
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
		<%
		int prodottoId = Integer.parseInt(request.getParameter("prodottoId"));
		ProdottoDAO prodottoDAO = new ProdottoDAO();
		Prodotto prodotto = null;
		try {
			prodotto = prodottoDAO.doRetrieveByKey(prodottoId);
		} catch (SQLException e) {
			response.sendRedirect("errorPage.jsp");
		}
		if (prodotto != null) {
		%>
		<div class="auth-container">
			<div class="auth-container-header">
				<div class="auth-container-header-text">
					<h2>Modifica Prodotto</h2>
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
				action="ModificaProdottoServlet" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="codice" value="<%=prodotto.getCodice()%>" />
				<div class="auth-container-header-text">Prodotto</div>
				<input class="auth-input" type="text" name="nome" placeholder="Nome"
					value="<%=prodotto.getNome()%>" required /> <input
					class="auth-input" type="text" name="descrizione"
					placeholder="Descrizione" value="<%=prodotto.getDescrizione()%>"
					required /> <input class="auth-input" type="number" step="0.01"
					name="costo" placeholder="Costo" value="<%=prodotto.getCosto()%>"
					required /> <select class="auth-input" name="sesso" required
					style="margin-bottom: 15px;">
					<option value="Uomo"
						<%=prodotto.getSesso() == 'U' ? "selected" : ""%>>Uomo</option>
					<option value="Donna"
						<%=prodotto.getSesso() == 'D' ? "selected" : ""%>>Donna</option>
				</select> <input class="auth-input" type="text" name="categoria"
					placeholder="Categoria" value="<%=prodotto.getCategoria()%>"
					required /> <img src="images/<%=prodotto.getImmagine()%>"
					alt="<%=prodotto.getNome()%>"
					style="width: 200px; height: auto; margin-bottom: 15px;"> <input
					class="auth-input" type="file" name="immagine"
					placeholder="Nuova Immagine (opzionale)" accept="image/*" /> <input
					type="hidden" name="currentImmagine"
					value="<%=prodotto.getImmagine()%>" />
				<button class="auth-button secondary" type="submit">Modifica
					Prodotto</button>
			</form>
		</div>
		<%
		} else {
		response.sendRedirect("errorPage.jsp");
		}
		%>
		<%@ include file="./layout/footer.jsp"%>
	</div>

</body>
</html>
