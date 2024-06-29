package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

public class OrdineDAO implements BeanDAO<Ordine, Integer> {

	private static final String NOME_TABELLA = "ordine";
	private DataSource dataSource;

	public OrdineDAO() {

	}

	@Override
	public synchronized void doSave(Ordine ordine) throws SQLException {
		String insertSQL = "INSERT INTO " + NOME_TABELLA + " (data, costo_totale, utente_email) VALUES (?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

			statement.setDate(1, new java.sql.Date(ordine.getData().getTime()));
			statement.setDouble(2, ordine.getCostoTotale());
			statement.setString(3, ordine.getUtenteEmail());

			if (statement.executeUpdate() != 1) {
				throw new SQLException("Errore INSERT INTO");
			}

			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				ordine.setCodice(rs.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'inserimento dell'ordine: " + e.getMessage());
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
	public synchronized boolean doDelete(Integer codice) throws SQLException {
		String deleteSQL = "DELETE FROM " + NOME_TABELLA + " WHERE codice = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteSQL);

			statement.setInt(1, codice);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'eliminazione dell'ordine: " + e.getMessage());
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
	public synchronized Ordine doRetrieveByKey(Integer codice) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE codice = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);

			statement.setInt(1, codice);

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Ordine ordine = new Ordine();
				ordine.setCodice(resultSet.getInt("codice"));
				ordine.setData(resultSet.getDate("data"));
				ordine.setCostoTotale(resultSet.getDouble("costo_totale"));
				ordine.setUtenteEmail(resultSet.getString("utente_email"));
				return ordine;
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero dell'ordine: " + e.getMessage());
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
	public synchronized Collection<Ordine> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA;
		if (order != null && !order.isEmpty()) {
			selectSQL += " ORDER BY " + order;
		}

		List<Ordine> ordini = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Ordine ordine = new Ordine();
				ordine.setCodice(resultSet.getInt("codice"));
				ordine.setData(resultSet.getDate("data"));
				ordine.setCostoTotale(resultSet.getDouble("costo_totale"));
				ordine.setUtenteEmail(resultSet.getString("utente_email"));
				ordini.add(ordine);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di tutti gli ordini: " + e.getMessage());
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
		return ordini;
	}

	public synchronized void updateOrdine(Ordine ordine) throws SQLException {
		String updateSQL = "UPDATE " + NOME_TABELLA
				+ " SET data = ?, costo_totale = ?, utente_email = ? WHERE codice = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateSQL);

			statement.setDate(1, new java.sql.Date(ordine.getData().getTime()));
			statement.setDouble(2, ordine.getCostoTotale());
			statement.setString(3, ordine.getUtenteEmail());
			statement.setInt(4, ordine.getCodice());

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException("Errore durante l'aggiornamento dell'ordine, righe aggiornate: " + rowsUpdated);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'aggiornamento dell'ordine: " + e.getMessage());
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
