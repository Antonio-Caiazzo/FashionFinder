package control;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Utente;
import model.UtenteDAO;
import model.CarrelloDAO;

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegisterUser() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String dataDiNascita = request.getParameter("data_di_nascita");

		UtenteDAO utenteDAO = new UtenteDAO();
		boolean userExists = false;

		try {
			if (utenteDAO.doesUserExist(email)) {
				userExists = true;
			} else {
				Utente newUser = new Utente();
				newUser.setEmail(email);
				newUser.setUsername(username);
				newUser.setPsw(checkPsw(password));
				newUser.setNome(nome);
				newUser.setCognome(cognome);
				try {
					java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dataDiNascita);
					newUser.setdata_di_nascita(new java.sql.Date(date.getTime()));
				} catch (ParseException e) {
					throw new ServletException("Error parsing date", e);
				}

				newUser.setIsAdmin(false);

				utenteDAO.doSave(newUser);
				request.getSession().setAttribute("userRegistrato", newUser);

				@SuppressWarnings("unchecked")
				Map<Integer, Integer> sessionCart = (Map<Integer, Integer>) request.getSession().getAttribute("cart");
				if (sessionCart != null) {
					CarrelloDAO carrelloDAO = new CarrelloDAO();
					for (Map.Entry<Integer, Integer> entry : sessionCart.entrySet()) {
						carrelloDAO.addOrUpdateCartItem(email, entry.getKey(), entry.getValue());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.getSession().setAttribute("registration-error", true);
			response.sendRedirect("register.jsp");
			return;
		}

		if (userExists) {
			request.getSession().setAttribute("registration-error", true);
		} else {
			request.getSession().setAttribute("registration-error", false);
		}

		response.sendRedirect("register.jsp");
	}

	private String checkPsw(String psw) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
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
