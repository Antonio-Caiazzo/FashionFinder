package control;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DriverManagerConnectionPool;
import model.Utente;

/**
 * Servlet implementation class CheckLogin
 */
@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String redirectedPage = "/login.jsp";
		Boolean control = false;
		
		try {
			Connection con = DriverManagerConnectionPool.getConnection();
			String sql = "SELECT email, username, psw, nome, cognome, isAdmin, data_di_nascita FROM utente;";
			
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			
			while(rs.next()) {
				if(email.compareTo(rs.getString(1)) == 0) {
					String psw = checkPsw(password);
					if (psw.compareTo(rs.getString(3)) == 0) {
						control = true;
						Utente userRegistrato = new Utente();
						userRegistrato.setEmail(rs.getString(1));
						userRegistrato.setUsername(rs.getString(2));
						userRegistrato.setPsw(rs.getString(3));
						userRegistrato.setNome(rs.getString(4));
						userRegistrato.setCognome(rs.getString(5));
						userRegistrato.setIsAdmin(rs.getBoolean(6));
						userRegistrato.setDataDiNascita(rs.getDate(7));
						
						request.getSession().setAttribute("userRegistrato", userRegistrato);
						request.getSession().setAttribute("isAdmin", userRegistrato.getIsAdmin());
						request.getSession().setAttribute("email", rs.getString(1));
						request.getSession().setAttribute("username", rs.getString(2));
						
						redirectedPage = "/index.jsp";
						DriverManagerConnectionPool.releaseConnection(con);
						
						
					}
				}
			}
			
		}catch (Exception e) {
			redirectedPage = "/loginPage.jsp";
		}
		if (control == false) {
			request.getSession().setAttribute("login-error", true);
		}
		else {
			request.getSession().setAttribute("login-error", false);
		}
		response.sendRedirect(request.getContextPath() + redirectedPage);
		
	}
	
	private String checkPsw(String psw) {
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-512");
	    }
	    catch (Exception e) {
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
