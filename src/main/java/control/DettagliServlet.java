package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/dettagliProdotto")
public class DettagliServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String codiceStr = request.getParameter("codice");
		if (codiceStr != null && !codiceStr.isEmpty()) {
			try {
				int codice = Integer.parseInt(codiceStr);
				ProdottoDAO prodottoDAO = new ProdottoDAO();
				Prodotto prodotto = prodottoDAO.doRetrieveByKey(codice);
				if (prodotto != null) {
					request.setAttribute("prodotto", prodotto);
					request.getRequestDispatcher("/dettagli.jsp").forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
				}
			} catch (NumberFormatException | SQLException e) {
				response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
