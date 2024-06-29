package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

public class UtenteDAO implements BeanDAO<Utente, String> {

	private static String NOME_TABELLA = "utente";
	private DataSource dataSource;

	public UtenteDAO() {

	}

	@Override
	public synchronized void doSave(Utente utente) throws SQLException {
		String insertSQL = "INSERT INTO " + NOME_TABELLA
				+ " (email, username, psw, nome, cognome, isAdmin, dataDiNascita) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertSQL);

			statement.setString(1, utente.getEmail());
			statement.setString(2, utente.getUsername());
			statement.setString(3, utente.getPsw());
			statement.setString(4, utente.getNome());
			statement.setString(5, utente.getCognome());
			statement.setBoolean(6, utente.getIsAdmin());
			statement.setDate(7,
					utente.getDataDiNascita() != null ? new java.sql.Date(utente.getDataDiNascita().getTime()) : null);

			statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'inserimento dell'utente: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	@Override
	public synchronized boolean doDelete(String email) throws SQLException {
		String deleteSQL = "DELETE FROM " + NOME_TABELLA + " WHERE email = ?";
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteSQL);

			statement.setString(1, email);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;

		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'eliminazione dell'utente: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}

	@Override
	public synchronized Utente doRetrieveByKey(String email) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE email = ?";
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);

			statement.setString(1, email);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Utente utente = new Utente();
					utente.setEmail(resultSet.getString("email"));
					utente.setUsername(resultSet.getString("username"));
					utente.setPsw(resultSet.getString("psw"));
					utente.setNome(resultSet.getString("nome"));
					utente.setCognome(resultSet.getString("cognome"));
					utente.setIsAdmin(resultSet.getBoolean("isAdmin"));
					utente.setDataDiNascita(resultSet.getDate("dataDiNascita"));
					return utente;
				}
			}

		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero dell'utente: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		return null;
	}

	@Override
	public synchronized Collection<Utente> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA;
		if (order != null && !order.isEmpty()) {
			selectSQL += " ORDER BY " + order;
		}

		List<Utente> utenti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Utente utente = new Utente();
				utente.setEmail(resultSet.getString("email"));
				utente.setUsername(resultSet.getString("username"));
				utente.setPsw(resultSet.getString("psw"));
				utente.setNome(resultSet.getString("nome"));
				utente.setCognome(resultSet.getString("cognome"));
				utente.setIsAdmin(resultSet.getBoolean("isAdmin"));
				utente.setDataDiNascita(resultSet.getDate("dataDiNascita"));
				utenti.add(utente);
			}

		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di tutti gli utenti: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		return utenti;
	}

	public int updateUtente(Utente utente) throws SQLException {
		String updateSQL = "UPDATE " + NOME_TABELLA + " SET "
				+ "username = ?, psw = ?, nome = ?, cognome = ?, isAdmin = ?, dataDiNascita = ? " + "WHERE email = ?";
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateSQL);

			statement.setString(1, utente.getUsername());
			statement.setString(2, utente.getPsw());
			statement.setString(3, utente.getNome());
			statement.setString(4, utente.getCognome());
			statement.setBoolean(5, utente.getIsAdmin());
			statement.setDate(6,
					utente.getDataDiNascita() != null ? new java.sql.Date(utente.getDataDiNascita().getTime()) : null);
			statement.setString(7, utente.getEmail());

			return statement.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'aggiornamento dell'utente: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}
}
