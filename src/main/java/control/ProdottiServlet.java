package control;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/prodotti")
public class ProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProdottoDAO prodottoDAO;

	public void init() throws ServletException {
		super.init();
		prodottoDAO = new ProdottoDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sesso = request.getParameter("sesso");
		if (sesso == null || (!sesso.equals("u") && !sesso.equals("d"))) {
			request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
			return;
		}

		try {
			Collection<Prodotto> prodotti = prodottoDAO.getProdottiBySessoNonDeleted(sesso.charAt(0));
			request.setAttribute("prodotti", prodotti);
			String page = sesso.equals("u") ? "/uomo.jsp" : "/donna.jsp";
			request.getRequestDispatcher(page).forward(request, response);
		} catch (Exception e) {
			request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
