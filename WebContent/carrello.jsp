<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="model.Prodotto"%>
<%@ page import="model.ProdottoDAO"%>
<%@ page import="model.Utente"%>
<%@ page import="model.CarrelloDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>FashionFinder - Carrello</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">

		<div class="header-cart">
			<%@ include file="./layout/pageTitle.jsp"%>
		</div>

		<ul id="shopping-cart">
			<%
			Utente userRegistrato = (Utente) session.getAttribute("userRegistrato");
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			double total = 0;

			if (userRegistrato != null) {
				CarrelloDAO carrelloDAO = new CarrelloDAO();
				cart = carrelloDAO.getCartByUser(userRegistrato.getEmail());
				session.setAttribute("cart", cart);
			}

			if (cart != null && !cart.isEmpty()) {
				for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
					int codice = entry.getKey();
					int quantity = entry.getValue();
					Prodotto prodotto = prodottoDAO.doRetrieveByKey(codice);

					if (prodotto != null) {
				double prezzo = prodotto.getCosto();
				double subtotal = prezzo * quantity;
				total += subtotal;
			%>
			<li class="shopping-item"><img
				src="<%=request.getContextPath() + "/images/" + prodotto.getImmagine()%>"
				alt="<%=prodotto.getNome()%>" />
				<div>
					<h3><%=prodotto.getNome()%></h3>
					<p>
						Prezzo: €<%=String.format("%.2f", prezzo)%></p>
					<form action="CarrelloServlet" method="post" class="form-container">
						<input type="hidden" name="codice"
							value="<%=prodotto.getCodice()%>"> <input type="number"
							name="quantity" value="<%=quantity%>" min="1"
							class="quantity-input" />
						<button type="submit" name="action" value="update"
							class="secondary">Modifica</button>
						<button type="submit" name="action" value="remove" class="danger">Rimuovi</button>
					</form>
				</div></li>
			<%
			}
			}
			} else {
			%>
			<li class="shopping-item">Il carrello è vuoto.</li>
			<%
			}
			%>
		</ul>
		<div class="card-footer">
			<p>
				Totale: €<span id="total-price"><%=String.format("%.2f", total)%></span>
			</p>
			<%
			if (cart != null && !cart.isEmpty()) {
			%>
			<form
				action="<%=(userRegistrato != null) ? "checkout.jsp" : "login.jsp"%>"
				method="get">
				<button type="submit" class="secondary">Paga Ora</button>
			</form>
			<%
			}
			%>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>
</body>
</html>
