<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*, java.util.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FashionFinder - Checkout</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%@ include file="./layout/navbar.jsp"%>
	<div class="main-content-container">
		<div class="auth-container">
			<div class="auth-container-header">
				<div class="auth-container-header-text">
					<h2>Checkout</h2>
				</div>
			</div>
			<%
			Utente userRegistrato = (Utente) session.getAttribute("userRegistrato");
			IndirizzoDAO indirizzoDAO = new IndirizzoDAO();
			CartaDAO cartaDAO = new CartaDAO();

			Indirizzo indirizzo = null;
			Carta carta = null;

			if (userRegistrato != null) {
				List<Indirizzo> indirizzi = indirizzoDAO.doRetrieveByUtenteEmail(userRegistrato.getEmail());
				if (!indirizzi.isEmpty()) {
					indirizzo = indirizzi.get(0);
				}
				List<Carta> carte = cartaDAO.doRetrieveByUtenteEmail(userRegistrato.getEmail());
				if (!carte.isEmpty()) {
					carta = carte.get(0);
				}
			}
			%>
			<form class="auth-form" action="CheckoutServlet" method="post">

				<div class="auth-container-header-text">Dati di spedizione</div>
				<input class="auth-input" type="text" name="nome" placeholder="Nome"
					value="<%=userRegistrato != null ? userRegistrato.getNome() : ""%>"
					required /> <input class="auth-input" type="text" name="cognome"
					placeholder="Cognome"
					value="<%=userRegistrato != null ? userRegistrato.getCognome() : ""%>"
					required /> <input class="auth-input" type="text" name="cap"
					placeholder="CAP"
					value="<%=indirizzo != null ? indirizzo.getCap() : ""%>" required />
				<input class="auth-input" type="text" name="citta"
					placeholder="Città"
					value="<%=indirizzo != null ? indirizzo.getCitta() : ""%>" required />
				<input class="auth-input" type="text" name="provincia"
					placeholder="Provincia"
					value="<%=indirizzo != null ? indirizzo.getProvincia() : ""%>"
					required /> <input class="auth-input" type="text" name="via"
					placeholder="Via"
					value="<%=indirizzo != null ? indirizzo.getVia() : ""%>" required />
				<input class="auth-input" type="text" name="civico"
					placeholder="Civico"
					value="<%=indirizzo != null ? indirizzo.getCivico() : ""%>"
					required />

				<div class="auth-container-header-text">Dati di pagamento</div>
				<input class="auth-input" type="text" name="titolare"
					placeholder="Titolare Carta"
					value="<%=carta != null ? carta.getNomeTitolare() + " " + carta.getCognomeTitolare() : ""%>"
					required /> <input class="auth-input" type="text"
					name="numeroCarta" placeholder="Numero Carta"
					value="<%=carta != null ? carta.getNumeroCarta() : ""%>" required />
				<input class="auth-input" type="date" name="scadenza"
					placeholder="Scadenza"
					value="<%=carta != null ? carta.getScadenzaCarta() : ""%>" required />

				<button class="auth-button secondary" type="submit">Paga
					Ora</button>

			</form>
		</div>
		<%@ include file="./layout/footer.jsp"%>
	</div>

</body>
</html>
