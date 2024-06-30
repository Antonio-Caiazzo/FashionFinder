package control;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CarrelloDAO;
import model.Utente;

@WebServlet("/CarrelloServlet")
public class CarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String codiceParam = request.getParameter("codice");
		String quantityParam = request.getParameter("quantity");

		if (codiceParam == null || quantityParam == null) {
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
			return;
		}

		int codice;
		int quantity;
		try {
			codice = Integer.parseInt(codiceParam);
			quantity = Integer.parseInt(quantityParam);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
			return;
		}

		@SuppressWarnings("unchecked")
		Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new HashMap<>();
		}

		Utente userRegistrato = (Utente) request.getSession().getAttribute("userRegistrato");

		if ("add".equals(action)) {
			cart.put(codice, cart.getOrDefault(codice, 0) + quantity);
		} else if ("update".equals(action)) {
			if (quantity > 0) {
				cart.put(codice, quantity);
			} else {
				cart.remove(codice);
			}
		} else if ("remove".equals(action)) {
			cart.remove(codice);
		}

		if (userRegistrato != null) {
			CarrelloDAO carrelloDAO = new CarrelloDAO();
			if ("remove".equals(action)) {
				carrelloDAO.removeCartItem(userRegistrato.getEmail(), codice);
			} else {
				for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
					carrelloDAO.addOrUpdateCartItem(userRegistrato.getEmail(), entry.getKey(), entry.getValue());
				}
			}
		}

		request.getSession().setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath() + "/carrello.jsp");
	}
}
