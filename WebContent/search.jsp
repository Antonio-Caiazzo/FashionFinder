<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.ProdottoDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Prodotto"%>

<%
String query = request.getParameter("query");
ProdottoDAO prodottoDAO = new ProdottoDAO();
List<Prodotto> prodotti = prodottoDAO.searchProductsByName(query);

if (prodotti != null && !prodotti.isEmpty()) {
	for (Prodotto prodotto : prodotti) {
		if (Boolean.TRUE.equals(prodotto.getIsDeleted()) == false) {
%>
<div data-id="<%=prodotto.getCodice()%>"
	style="display: flex; align-items: center; padding: 10px; border-bottom: 1px solid #ccc; cursor: pointer;">
	<img src="images/<%=prodotto.getImmagine()%>"
		alt="<%=prodotto.getNome()%>"
		style="width: 50px; height: 50px; margin-right: 10px;"> <span><%=prodotto.getNome()%></span>
</div>
<%
}
}
} else {
%>
<p>Nessun prodotto trovato</p>
<%
}
%>
