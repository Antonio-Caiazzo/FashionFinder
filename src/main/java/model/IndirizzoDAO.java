package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

public class IndirizzoDAO implements BeanDAO<Indirizzo, Integer> {

	private static final String NOME_TABELLA = "indirizzo";
	private DataSource dataSource;

	public IndirizzoDAO() {

	}

	@Override
	public synchronized void doSave(Indirizzo indirizzo) throws SQLException {
		String insertSQL = "INSERT INTO " + NOME_TABELLA
				+ " (cap, citta, provincia, via, civico, utente_email) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, indirizzo.getCap());
			statement.setString(2, indirizzo.getCitta());
			statement.setString(3, indirizzo.getProvincia());
			statement.setString(4, indirizzo.getVia());
			statement.setInt(5, indirizzo.getCivico());
			statement.setString(6, indirizzo.getUtenteEmail());

			if (statement.executeUpdate() != 1) {
				throw new SQLException("Errore INSERT INTO");
			}

			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				indirizzo.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'inserimento dell'indirizzo: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura del ResultSet: " + e.getMessage());
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura dello Statement: " + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
			}
		}
	}

	@Override
	public synchronized boolean doDelete(Integer id) throws SQLException {
		String deleteSQL = "DELETE FROM " + NOME_TABELLA + " WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteSQL);

			statement.setInt(1, id);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'eliminazione dell'indirizzo: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura dello Statement: " + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
			}
		}
	}

	@Override
	public synchronized Indirizzo doRetrieveByKey(Integer id) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);

			statement.setInt(1, id);

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Indirizzo indirizzo = new Indirizzo();
				indirizzo.setId(resultSet.getInt("id"));
				indirizzo.setCap(resultSet.getInt("cap"));
				indirizzo.setCitta(resultSet.getString("citta"));
				indirizzo.setProvincia(resultSet.getString("provincia"));
				indirizzo.setVia(resultSet.getString("via"));
				indirizzo.setCivico(resultSet.getInt("civico"));
				indirizzo.setUtenteEmail(resultSet.getString("utente_email"));
				return indirizzo;
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero dell'indirizzo: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura del ResultSet: " + e.getMessage());
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura dello Statement: " + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
			}
		}
		return null;
	}

	@Override
	public synchronized Collection<Indirizzo> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA;
		if (order != null && !order.isEmpty()) {
			selectSQL += " ORDER BY " + order;
		}

		List<Indirizzo> indirizzi = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Indirizzo indirizzo = new Indirizzo();
				indirizzo.setId(resultSet.getInt("id"));
				indirizzo.setCap(resultSet.getInt("cap"));
				indirizzo.setCitta(resultSet.getString("citta"));
				indirizzo.setProvincia(resultSet.getString("provincia"));
				indirizzo.setVia(resultSet.getString("via"));
				indirizzo.setCivico(resultSet.getInt("civico"));
				indirizzo.setUtenteEmail(resultSet.getString("utente_email"));
				indirizzi.add(indirizzo);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di tutti gli indirizzi: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura del ResultSet: " + e.getMessage());
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura dello Statement: " + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
			}
		}
		return indirizzi;
	}

	public synchronized void updateIndirizzo(Indirizzo indirizzo) throws SQLException {
		String updateSQL = "UPDATE " + NOME_TABELLA
				+ " SET cap = ?, citta = ?, provincia = ?, via = ?, civico = ?, utente_email = ? WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateSQL);

			statement.setInt(1, indirizzo.getCap());
			statement.setString(2, indirizzo.getCitta());
			statement.setString(3, indirizzo.getProvincia());
			statement.setString(4, indirizzo.getVia());
			statement.setInt(5, indirizzo.getCivico());
			statement.setString(6, indirizzo.getUtenteEmail());
			statement.setInt(7, indirizzo.getId());

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException(
						"Errore durante l'aggiornamento dell'indirizzo, righe aggiornate: " + rowsUpdated);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'aggiornamento dell'indirizzo: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura dello Statement: " + e.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				System.out.println("Errore durante la chiusura della connessione: " + e.getMessage());
			}
		}
	}
}
