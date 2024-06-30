package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CarrelloDAO;
import model.Ordine;
import model.OrdineDAO;
import model.ContieneDAO;
import model.Utente;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utente userRegistrato = (Utente) request.getSession().getAttribute("userRegistrato");
		if (userRegistrato == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		@SuppressWarnings("unchecked")
		Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSession().getAttribute("cart");
		if (cart == null || cart.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/carrello.jsp");
			return;
		}

		OrdineDAO ordineDAO = new OrdineDAO();
		ContieneDAO contieneDAO = new ContieneDAO();
		ProdottoDAO prodottoDAO = new ProdottoDAO();

		double totalCost = 0;
		for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
			int codice = entry.getKey();
			int quantity = entry.getValue();
			Prodotto prodotto = null;
			try {
				prodotto = prodottoDAO.doRetrieveByKey(codice);
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
				return;
			}
			if (prodotto != null) {
				totalCost += prodotto.getCosto() * quantity;
			}
		}

		Ordine ordine = new Ordine();
		ordine.setData(new Date());
		ordine.setCostoTotale(totalCost);
		ordine.setUtenteEmail(userRegistrato.getEmail());

		try {
			ordineDAO.doSave(ordine);

			if (ordine.getCodice() == 0) {
				throw new SQLException("Failed to retrieve order ID.");
			}

			for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
				int codice = entry.getKey();
				int quantity = entry.getValue();
				contieneDAO.doSave(ordine.getCodice(), codice, quantity);
			}

			CarrelloDAO carrelloDAO = new CarrelloDAO();
			carrelloDAO.clearCart(userRegistrato.getEmail());
			request.getSession().removeAttribute("cart");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
			return;
		}

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
}
