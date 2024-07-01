package control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Ordine;
import model.OrdineDAO;

@WebServlet("/RicercaOrdiniServlet")
public class RicercaOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RicercaOrdiniServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		OrdineDAO ordineDAO = new OrdineDAO();
		List<Ordine> ordini = null;

		try {
			if ("data".equals(action)) {
				String fromDateStr = request.getParameter("fromDate");
				String toDateStr = request.getParameter("toDate");
				Date fromDate = Date.valueOf(fromDateStr);
				Date toDate = Date.valueOf(toDateStr);
				ordini = ordineDAO.doRetrieveByDateRange(fromDate, toDate);
			} else if ("nominativi".equals(action)) {
				String nome = request.getParameter("nome");
				String cognome = request.getParameter("cognome");
				ordini = ordineDAO.doRetrieveByNomeCognome(nome, cognome);
			}

			request.setAttribute("ordini", ordini);
			request.getRequestDispatcher("/ordiniAdmin.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
		}
	}
}
