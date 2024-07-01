package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Contiene;
import model.ContieneDAO;
import model.Ordine;
import model.OrdineDAO;

@WebServlet("/OrdineDettagliServlet")
public class OrdineDettagliServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int ordineId = Integer.parseInt(request.getParameter("ordineId"));
		OrdineDAO ordineDAO = new OrdineDAO();
		ContieneDAO contieneDAO = new ContieneDAO();
		try {
			Ordine ordine = ordineDAO.doRetrieveByKey(ordineId);
			List<Contiene> contieneList = contieneDAO.doRetrieveByOrdine(ordineId);

			request.setAttribute("ordine", ordine);
			request.setAttribute("contieneList", contieneList);
			request.getRequestDispatcher("/ordineDettagli.jsp").forward(request, response);
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
		}
	}
}
