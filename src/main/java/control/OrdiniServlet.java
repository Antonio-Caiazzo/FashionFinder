package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Ordine;
import model.OrdineDAO;
import model.Utente;

@WebServlet("/OrdiniServlet")
public class OrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrdiniServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Utente userRegistrato = (Utente) request.getSession().getAttribute("userRegistrato");
		if (userRegistrato == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		OrdineDAO ordineDAO = new OrdineDAO();
		try {
			List<Ordine> ordini = ordineDAO.doRetrieveByUser(userRegistrato.getEmail());
			request.setAttribute("ordini", ordini);
			request.getRequestDispatcher("/ordini.jsp").forward(request, response);
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
		}
	}
}
