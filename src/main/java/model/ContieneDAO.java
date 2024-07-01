package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContieneDAO implements BeanDAO<Contiene, Integer> {

	private static final String NOME_TABELLA = "contiene";;

	public ContieneDAO() {

	}

	@Override
	public void doSave(Contiene contiene) throws SQLException {
		String insertSQL = "INSERT INTO " + NOME_TABELLA
				+ " (ordine_codice, prodotto_codice, quantita) VALUES (?, ?, ?)";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(insertSQL)) {

			statement.setInt(1, contiene.getOrdineCodice());
			statement.setInt(2, contiene.getProdottoCodice());
			statement.setInt(3, contiene.getQuantita());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new SQLException("Errore durante l'inserimento di Contiene, righe aggiornate: " + rowsAffected);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'inserimento di Contiene: " + e.getMessage());
			throw e;
		}
	}

	public void doSave(int ordineCodice, int prodottoCodice, int quantita, double prezzo) throws SQLException {
		String insertSQL = "INSERT INTO contiene (ordine_codice, prodotto_codice, quantita, prezzo) VALUES (?, ?, ?, ?)";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(insertSQL)) {
			statement.setInt(1, ordineCodice);
			statement.setInt(2, prodottoCodice);
			statement.setInt(3, quantita);
			statement.setDouble(4, prezzo);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Errore SQL durante l'inserimento in contiene: " + e.getMessage());
		}
	}

	@Override
	public boolean doDelete(Integer ordineCodice) throws SQLException {
		String deleteSQL = "DELETE FROM " + NOME_TABELLA + " WHERE ordine_codice = ?";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

			statement.setInt(1, ordineCodice);

			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'eliminazione di Contiene: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Contiene doRetrieveByKey(Integer ordineCodice) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE ordine_codice = ?";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(selectSQL)) {

			statement.setInt(1, ordineCodice);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Contiene contiene = new Contiene();
					contiene.setOrdineCodice(resultSet.getInt("ordine_codice"));
					contiene.setProdottoCodice(resultSet.getInt("prodotto_codice"));
					contiene.setQuantita(resultSet.getInt("quantita"));
					return contiene;
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di Contiene: " + e.getMessage());
			throw e;
		}
		return null;
	}

	@Override
	public Collection<Contiene> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA;
		if (order != null && !order.isEmpty()) {
			selectSQL += " ORDER BY " + order;
		}

		List<Contiene> contieneList = new ArrayList<>();
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(selectSQL);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Contiene contiene = new Contiene();
				contiene.setOrdineCodice(resultSet.getInt("ordine_codice"));
				contiene.setProdottoCodice(resultSet.getInt("prodotto_codice"));
				contiene.setQuantita(resultSet.getInt("quantita"));
				contieneList.add(contiene);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di tutti gli oggetti Contiene: " + e.getMessage());
			throw e;
		}
		return contieneList;
	}

	// Ci serve per modificare la quantità dei prodotti di uno specifico ordine
	public void updateContiene(Contiene contiene) throws SQLException {
		String updateSQL = "UPDATE " + NOME_TABELLA
				+ " SET quantita = ? WHERE ordine_codice = ? AND prodotto_codice = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManagerConnectionPool.getConnection();
			statement = connection.prepareStatement(updateSQL);

			statement.setInt(1, contiene.getQuantita());
			statement.setInt(2, contiene.getOrdineCodice());
			statement.setInt(3, contiene.getProdottoCodice());

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException(
						"Errore durante l'aggiornamento della quantità di Contiene, righe aggiornate: " + rowsUpdated);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'aggiornamento di Contiene: " + e.getMessage());
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					System.out.println("Errore durante la chiusura dello Statement: " + e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
				}
			}
		}
	}

	public List<Contiene> doRetrieveByOrdine(int ordineCodice) throws SQLException {
		String selectSQL = "SELECT * FROM contiene WHERE ordine_codice = ?";
		List<Contiene> contieneList = new ArrayList<>();
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement ps = connection.prepareStatement(selectSQL)) {
			ps.setInt(1, ordineCodice);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Contiene contiene = new Contiene();
					contiene.setOrdineCodice(rs.getInt("ordine_codice"));
					contiene.setProdottoCodice(rs.getInt("prodotto_codice"));
					contiene.setQuantita(rs.getInt("quantita"));
					contiene.setPrezzo(rs.getDouble("prezzo"));
					contieneList.add(contiene);
				}
			}
		}
		return contieneList;
	}

}
