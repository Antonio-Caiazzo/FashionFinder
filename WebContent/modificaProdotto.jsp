<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="model.*, java.util.*, java.sql.SQLException"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Modifica Prodotto</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
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

			<form class="auth-form" action="ModificaProdottoServlet"
				method="post">
				<input type="hidden" name="codice" value="<%=prodotto.getCodice()%>" />
				<div class="auth-container-header-text">Prodotto</div>
				<input class="auth-input" type="text" name="nome" placeholder="Nome"
					value="<%=prodotto.getNome()%>" required /> <input
					class="auth-input" type="text" name="descrizione"
					placeholder="Descrizione" value="<%=prodotto.getDescrizione()%>"
					required /> <input class="auth-input" type="number" step="0.01"
					name="costo" placeholder="Costo" value="<%=prodotto.getCosto()%>"
					required /> <input class="auth-input" type="text" name="sesso"
					placeholder="Sesso" value="<%=prodotto.getSesso()%>" required /> <input
					class="auth-input" type="text" name="immagine"
					placeholder="Immagine" value="<%=prodotto.getImmagine()%>" required />
				<input class="auth-input" type="text" name="categoria"
					placeholder="Categoria" value="<%=prodotto.getCategoria()%>"
					required />


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
