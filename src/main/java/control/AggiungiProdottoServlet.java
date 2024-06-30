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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		double costo = Double.parseDouble(request.getParameter("costo"));
		char sesso = request.getParameter("sesso").charAt(0);
		String immagine = request.getParameter("immagine");
		String categoria = request.getParameter("categoria");

		Prodotto prodotto = new Prodotto();
		prodotto.setNome(nome);
		prodotto.setDescrizione(descrizione);
		prodotto.setCosto(costo);
		prodotto.setSesso(sesso);
		prodotto.setImmagine(immagine);
		prodotto.setCategoria(categoria);

		ProdottoDAO prodottoDAO = new ProdottoDAO();
		try {
			prodottoDAO.doSave(prodotto);
			response.sendRedirect("gestioneCatalogo.jsp");
		} catch (SQLException e) {
			response.sendRedirect("errorPage.jsp");
		}
	}
}
