package control;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Utente;
import model.UtenteDAO;
import model.CarrelloDAO;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckLogin() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String redirectedPage = "/login.jsp";
		boolean control = false;

		try {
			UtenteDAO utenteDAO = new UtenteDAO();
			Utente userRegistrato = utenteDAO.doRetrieveByKey(email);

			if (userRegistrato != null) {
				String psw = checkPsw(password);
				if (psw.equals(userRegistrato.getPsw())) {
					control = true;
					request.getSession().setAttribute("userRegistrato", userRegistrato);
					request.getSession().setAttribute("isAdmin", userRegistrato.getIsAdmin());
					request.getSession().setAttribute("email", userRegistrato.getEmail());
					request.getSession().setAttribute("username", userRegistrato.getUsername());

					@SuppressWarnings("unchecked")
					Map<Integer, Integer> sessionCart = (Map<Integer, Integer>) request.getSession()
							.getAttribute("cart");
					if (sessionCart != null) {
						CarrelloDAO carrelloDAO = new CarrelloDAO();
						for (Map.Entry<Integer, Integer> entry : sessionCart.entrySet()) {
							carrelloDAO.addOrUpdateCartItem(email, entry.getKey(), entry.getValue());
						}
						request.getSession().removeAttribute("cart");
					}

					redirectedPage = "/index.jsp";
				}
			}
		} catch (Exception e) {
			;
			redirectedPage = "/login.jsp";
		}

		if (!control) {
			request.getSession().setAttribute("login-error", true);
		} else {
			request.getSession().setAttribute("login-error", false);
		}
		response.sendRedirect(request.getContextPath() + redirectedPage);
	}

	private String checkPsw(String psw) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (Exception e) {
		}
		byte[] messageDigest = md.digest(psw.getBytes());
		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);

		while (hashtext.length() < 128) {
			hashtext = "0" + hashtext;
		}

		return hashtext;
	}
}
