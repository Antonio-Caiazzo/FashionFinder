package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProdottoDAO;

@WebServlet("/EliminaProdottoServlet")
public class EliminaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int prodottoId = Integer.parseInt(request.getParameter("prodottoId"));
		ProdottoDAO prodottoDAO = new ProdottoDAO();

		try {
			prodottoDAO.eliminaLogicamente(prodottoId);
			response.sendRedirect("gestioneCatalogo.jsp");
		} catch (SQLException e) {
			response.sendRedirect("errorPage.jsp");
		}
	}
}
