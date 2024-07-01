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

@WebServlet("/ModificaProdottoServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ModificaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		int codice = Integer.parseInt(request.getParameter("codice"));
		String nome = sanitize(request.getParameter("nome"));
		String descrizione = sanitize(request.getParameter("descrizione"));
		double costo = Double.parseDouble(request.getParameter("costo"));
		String sesso = request.getParameter("sesso");
		String immagine = sanitize(request.getParameter("currentImmagine"));
		String categoria = sanitize(request.getParameter("categoria"));

		if (costo < 0) {
			request.setAttribute("errorMessage", "Il costo non può essere negativo.");
			request.getRequestDispatcher("/modificaProdotto.jsp?prodottoId=" + codice).forward(request, response);
			return;
		}

		if (!sesso.equals("Uomo") && !sesso.equals("Donna")) {
			request.setAttribute("errorMessage", "Seleziona un sesso valido.");
			request.getRequestDispatcher("/modificaProdotto.jsp?prodottoId=" + codice).forward(request, response);
			return;
		}

		Part filePart = request.getPart("immagine");
		String relativeFileName = immagine;

		if (filePart != null && filePart.getSize() > 0) {
			if (!isImageFile(filePart)) {
				request.setAttribute("errorMessage",
						"Per favore carica un file immagine valido (jpg, jpeg, png, gif).");
				request.getRequestDispatcher("/modificaProdotto.jsp?prodottoId=" + codice).forward(request, response);
				return;
			}

			String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
			String applicationPath = getServletContext().getRealPath("");
			String uploadFilePath = applicationPath + File.separator + "images";

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
				relativeFileName = sanitizedFileName;
			} catch (IOException e) {
				request.setAttribute("errorMessage", "Errore durante il caricamento dell'immagine.");
				request.getRequestDispatcher("/modificaProdotto.jsp?prodottoId=" + codice).forward(request, response);
				return;
			}
		}

		Prodotto prodotto = new Prodotto();
		prodotto.setCodice(codice);
		prodotto.setNome(nome);
		prodotto.setDescrizione(descrizione);
		prodotto.setCosto(costo);
		prodotto.setSesso(sesso.charAt(0));
		prodotto.setImmagine(relativeFileName);
		prodotto.setCategoria(categoria);

		ProdottoDAO prodottoDAO = new ProdottoDAO();
		try {
			prodottoDAO.updateProdotto(prodotto);
			response.sendRedirect("gestioneCatalogo.jsp");
		} catch (SQLException e) {
			request.setAttribute("errorMessage", "Errore durante la modifica del prodotto.");
			request.getRequestDispatcher("/modificaProdotto.jsp?prodottoId=" + codice).forward(request, response);
		}
	}
}
