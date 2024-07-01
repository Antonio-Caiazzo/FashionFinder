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

@WebServlet("/AggiungiProdottoServlet")
public class AggiungiProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String sanitizeInput(String input) {
		if (input != null) {
			input = input.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
					.replace("'", "&#x27;");
		}
		return input;

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		double costo = Double.parseDouble(request.getParameter("costo"));
		char sesso = request.getParameter("sesso").charAt(0);
		String immagine = request.getParameter("immagine");
		String categoria = request.getParameter("categoria");

		if (sesso != 'u' && sesso != 'd') {
			response.sendRedirect("errorPage.jsp");
			return;
		}

		Prodotto prodotto = new Prodotto();
		prodotto.setNome(sanitizeInput(nome));
		prodotto.setDescrizione(sanitizeInput(descrizione));
		prodotto.setCosto(costo);
		prodotto.setSesso(sesso);
		prodotto.setImmagine(sanitizeInput(immagine));
		prodotto.setCategoria(sanitizeInput(categoria));

		ProdottoDAO prodottoDAO = new ProdottoDAO();
		try {
			prodottoDAO.doSave(prodotto);
			response.sendRedirect("gestioneCatalogo.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("errorPage.jsp");
		}
	}
}
