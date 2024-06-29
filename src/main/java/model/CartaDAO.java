package model;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CartaDAO implements BeanDAO<Carta, String> {

	private static final String NOME_TABELLA = "carta";
	private DataSource dataSource;

	public CartaDAO() {

	}

	@Override
	public void doSave(Carta carta) throws SQLException {
		String insertSQL = "INSERT INTO " + NOME_TABELLA
				+ " (numero_carta, scadenza_carta, nome_titolare, cognome_titolare, utente_email) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(insertSQL)) {

			statement.setString(1, carta.getNumeroCarta());
			statement.setDate(2, new java.sql.Date(carta.getScadenzaCarta().getTime()));
			statement.setString(3, carta.getNomeTitolare());
			statement.setString(4, carta.getCognomeTitolare());
			statement.setString(5, carta.getUtenteEmail());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new SQLException("Errore durante l'inserimento di Carta, righe aggiornate: " + rowsAffected);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'inserimento di Carta: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean doDelete(String numeroCarta) throws SQLException {
		String deleteSQL = "DELETE FROM " + NOME_TABELLA + " WHERE numero_carta = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

			statement.setString(1, numeroCarta);

			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'eliminazione di Carta: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public Carta doRetrieveByKey(String numeroCarta) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE numero_carta = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(selectSQL)) {

			statement.setString(1, numeroCarta);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Carta carta = new Carta();
					carta.setNumeroCarta(resultSet.getString("numero_carta"));
					carta.setScadenzaCarta(resultSet.getDate("scadenza_carta"));
					carta.setNomeTitolare(resultSet.getString("nome_titolare"));
					carta.setCognomeTitolare(resultSet.getString("cognome_titolare"));
					carta.setUtenteEmail(resultSet.getString("utente_email"));
					return carta;
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di Carta: " + e.getMessage());
			throw e;
		}
		return null;
	}

	@Override
	public Collection<Carta> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA;
		if (order != null && !order.isEmpty()) {
			selectSQL += " ORDER BY " + order;
		}

		List<Carta> carteList = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(selectSQL);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Carta carta = new Carta();
				carta.setNumeroCarta(resultSet.getString("numero_carta"));
				carta.setScadenzaCarta(resultSet.getDate("scadenza_carta"));
				carta.setNomeTitolare(resultSet.getString("nome_titolare"));
				carta.setCognomeTitolare(resultSet.getString("cognome_titolare"));
				carta.setUtenteEmail(resultSet.getString("utente_email"));
				carteList.add(carta);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di tutte le carte: " + e.getMessage());
			throw e;
		}
		return carteList;
	}

	public void updateCarta(Carta carta) throws SQLException {
		String updateSQL = "UPDATE " + NOME_TABELLA
				+ " SET scadenza_carta = ?, nome_titolare = ?, cognome_titolare = ?, utente_email = ? "
				+ "WHERE numero_carta = ?";
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(updateSQL)) {

			statement.setDate(1, new java.sql.Date(carta.getScadenzaCarta().getTime()));
			statement.setString(2, carta.getNomeTitolare());
			statement.setString(3, carta.getCognomeTitolare());
			statement.setString(4, carta.getUtenteEmail());
			statement.setString(5, carta.getNumeroCarta());

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException("Errore durante l'aggiornamento di Carta, righe aggiornate: " + rowsUpdated);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'aggiornamento di Carta: " + e.getMessage());
			throw e;
		}
	}
}
