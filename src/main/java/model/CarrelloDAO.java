package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CarrelloDAO {

	public void addOrUpdateCartItem(String email, int codice, int quantity) {
		try (Connection connection = DriverManagerConnectionPool.getConnection()) {
			String query = "INSERT INTO carrello (utente_email, prodotto_codice, quantita) VALUES (?, ?, ?) "
					+ "ON DUPLICATE KEY UPDATE quantita = ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, email);
				ps.setInt(2, codice);
				ps.setInt(3, quantity);
				ps.setInt(4, quantity);
				ps.executeUpdate();
				connection.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeCartItem(String email, int codice) {
		try (Connection connection = DriverManagerConnectionPool.getConnection()) {
			String query = "DELETE FROM carrello WHERE utente_email = ? AND prodotto_codice = ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, email);
				ps.setInt(2, codice);
				ps.executeUpdate();
				connection.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Map<Integer, Integer> getCartByUser(String email) {
		Map<Integer, Integer> cart = new HashMap<>();
		try (Connection connection = DriverManagerConnectionPool.getConnection()) {
			String query = "SELECT prodotto_codice, quantita FROM carrello WHERE utente_email = ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, email);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						cart.put(rs.getInt("prodotto_codice"), rs.getInt("quantita"));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cart;
	}
}
