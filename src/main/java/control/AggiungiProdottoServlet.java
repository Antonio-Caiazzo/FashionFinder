package control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Prodotto;
import model.ProdottoDAO;

@WebServlet("/AggiungiProdottoServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AggiungiProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "images";

	private String sanitize(String input) {
		if (input != null) {
			input = input.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
					.replace("'", "&#x27;");
		}
		return input;
	}

	private boolean isImageFile(Part filePart) {
		String contentType = filePart.getContentType();
		return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png")
				|| contentType.equals("image/gif") || contentType.equals("image/jpg"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nome = sanitize(request.getParameter("nome"));
		String descrizione = sanitize(request.getParameter("descrizione"));
		double costo = Double.parseDouble(request.getParameter("costo"));
		String sesso = request.getParameter("sesso");
		String categoria = sanitize(request.getParameter("categoria"));

		if (costo < 0) {
			request.setAttribute("errorMessage", "Il costo non può essere negativo.");
			request.getRequestDispatcher("/aggiungiProdotto.jsp").forward(request, response);
			return;
		}

		if (!sesso.equals("Uomo") && !sesso.equals("Donna")) {
			request.setAttribute("errorMessage", "Seleziona un sesso valido.");
			request.getRequestDispatcher("/aggiungiProdotto.jsp").forward(request, response);
			return;
		}

		Part filePart = request.getPart("immagine");
		if (filePart == null || !isImageFile(filePart)) {
			request.setAttribute("errorMessage", "Per favore carica un file immagine valido (jpg, jpeg, png, gif).");
			request.getRequestDispatcher("/aggiungiProdotto.jsp").forward(request, response);
			return;
		}

		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		String applicationPath = getServletContext().getRealPath("");
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

		File uploadDir = new File(uploadFilePath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}

		String sanitizedFileName = fileName.replaceAll("\\s+", "_");
		String filePath = uploadFilePath + File.separator + sanitizedFileName;
		File file = new File(filePath);
		while (file.exists()) {
			String uniqueID = UUID.randomUUID().toString();
			sanitizedFileName = uniqueID + "_" + sanitizedFileName;
			filePath = uploadFilePath + File.separator + sanitizedFileName;
			file = new File(filePath);
		}

		try {
			Files.copy(filePart.getInputStream(), Paths.get(filePath));
		} catch (IOException e) {
			request.setAttribute("errorMessage", "Errore durante il caricamento dell'immagine.");
			request.getRequestDispatcher("/aggiungiProdotto.jsp").forward(request, response);
			return;
		}

		String relativeFileName = sanitizedFileName;

		Prodotto prodotto = new Prodotto();
		prodotto.setNome(sanitize(nome));
		prodotto.setDescrizione(sanitize(descrizione));
		prodotto.setCosto(costo);
		prodotto.setSesso(sesso.charAt(0));
		prodotto.setImmagine(relativeFileName);
		prodotto.setCategoria(sanitize(categoria));

		ProdottoDAO prodottoDAO = new ProdottoDAO();
		try {
			prodottoDAO.doSave(prodotto);
			response.sendRedirect("gestioneCatalogo.jsp");
		} catch (SQLException e) {
			request.setAttribute("errorMessage", "Errore durante il salvataggio del prodotto.");
			request.getRequestDispatcher("/aggiungiProdotto.jsp").forward(request, response);
		}
	}
}
