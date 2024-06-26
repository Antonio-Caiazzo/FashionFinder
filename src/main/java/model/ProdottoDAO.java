package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

public class ProdottoDAO implements BeanDAO<Prodotto, Integer> {

	private static final String NOME_TABELLA = "prodotto";
	private DataSource dataSource;

	public ProdottoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void doSave(Prodotto prodotto) throws SQLException {
		String insertSQL = "INSERT INTO " + NOME_TABELLA
				+ " (codice, nome, descrizione, costo, sesso, immagine, categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertSQL);

			statement.setInt(1, prodotto.getCodice());
			statement.setString(2, prodotto.getNome());
			statement.setString(3, prodotto.getDescrizione());
			statement.setDouble(4, prodotto.getCosto());
			statement.setString(5, String.valueOf(prodotto.getSesso()));
			statement.setString(6, prodotto.getImmagine());
			statement.setString(7, prodotto.getCategoria());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new SQLException("Errore durante l'inserimento di Prodotto, righe aggiornate: " + rowsAffected);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'inserimento di Prodotto: " + e.getMessage());
			throw e;
		} finally {
			closeResources(connection, statement, null);
		}
	}

	@Override
	public boolean doDelete(Integer codice) throws SQLException {
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
			System.out.println("Errore SQL durante l'eliminazione di Prodotto: " + e.getMessage());
			throw e;
		} finally {
			closeResources(connection, statement, null);
		}
	}

	@Override
	public Prodotto doRetrieveByKey(Integer codice) throws SQLException {
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
				Prodotto prodotto = new Prodotto();
				prodotto.setCodice(resultSet.getInt("codice"));
				prodotto.setNome(resultSet.getString("nome"));
				prodotto.setDescrizione(resultSet.getString("descrizione"));
				prodotto.setCosto(resultSet.getDouble("costo"));
				prodotto.setSesso(resultSet.getString("sesso").charAt(0));
				prodotto.setImmagine(resultSet.getString("immagine"));
				prodotto.setCategoria(resultSet.getString("categoria"));
				return prodotto;
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di Prodotto: " + e.getMessage());
			throw e;
		} finally {
			closeResources(connection, statement, resultSet);
		}
		return null;
	}

	@Override
	public Collection<Prodotto> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA;
		if (order != null && !order.isEmpty()) {
			selectSQL += " ORDER BY " + order;
		}

		List<Prodotto> prodotti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Prodotto prodotto = new Prodotto();
				prodotto.setCodice(resultSet.getInt("codice"));
				prodotto.setNome(resultSet.getString("nome"));
				prodotto.setDescrizione(resultSet.getString("descrizione"));
				prodotto.setCosto(resultSet.getDouble("costo"));
				prodotto.setSesso(resultSet.getString("sesso").charAt(0));
				prodotto.setImmagine(resultSet.getString("immagine"));
				prodotto.setCategoria(resultSet.getString("categoria"));
				prodotti.add(prodotto);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di tutti gli oggetti Prodotto: " + e.getMessage());
			throw e;
		} finally {
			closeResources(connection, statement, resultSet);
		}
		return prodotti;
	}

	private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
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

	// Serve per recuperare tutti i prodotti di una data categoria
	public Collection<Prodotto> getProdottiByCategoria(String categoria) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE categoria = ?";
		List<Prodotto> prodotti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, categoria);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Prodotto prodotto = new Prodotto();
				prodotto.setCodice(resultSet.getInt("codice"));
				prodotto.setNome(resultSet.getString("nome"));
				prodotto.setDescrizione(resultSet.getString("descrizione"));
				prodotto.setCosto(resultSet.getDouble("costo"));
				prodotto.setSesso(resultSet.getString("sesso").charAt(0));
				prodotto.setImmagine(resultSet.getString("immagine"));
				prodotto.setCategoria(resultSet.getString("categoria"));
				prodotti.add(prodotto);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero dei prodotti per categoria: " + e.getMessage());
			throw e;
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					System.out.println("Errore durante la chiusura del ResultSet: " + e.getMessage());
				}
			}
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
		return prodotti;
	}

	// Serve per recuperare tutti i prodotti inclusi in un ordine specifico
	public Collection<Prodotto> getProdottiInOrdine(Integer ordineCodice) throws SQLException {
		String selectSQL = "SELECT p.* FROM prodotto p INNER JOIN contiene c ON p.codice = c.prodotto_codice WHERE c.ordine_codice = ?";
		List<Prodotto> prodotti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(selectSQL);
			statement.setInt(1, ordineCodice);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Prodotto prodotto = new Prodotto();
				prodotto.setCodice(resultSet.getInt("codice"));
				prodotto.setNome(resultSet.getString("nome"));
				prodotto.setDescrizione(resultSet.getString("descrizione"));
				prodotto.setCosto(resultSet.getDouble("costo"));
				prodotto.setSesso(resultSet.getString("sesso").charAt(0));
				prodotto.setImmagine(resultSet.getString("immagine"));
				prodotto.setCategoria(resultSet.getString("categoria"));
				prodotti.add(prodotto);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero dei prodotti in ordine: " + e.getMessage());
			throw e;
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					System.out.println("Errore durante la chiusura del ResultSet: " + e.getMessage());
				}
			}
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
		return prodotti;
	}

	public void updateProdotto(Prodotto prodotto) throws SQLException {
		String updateSQL = "UPDATE " + NOME_TABELLA
				+ " SET nome = ?, descrizione = ?, costo = ?, sesso = ?, immagine = ?, categoria = ? WHERE codice = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateSQL);

			statement.setString(1, prodotto.getNome());
			statement.setString(2, prodotto.getDescrizione());
			statement.setDouble(3, prodotto.getCosto());
			statement.setString(4, String.valueOf(prodotto.getSesso()));
			statement.setString(5, prodotto.getImmagine());
			statement.setString(6, prodotto.getCategoria());
			statement.setInt(7, prodotto.getCodice());

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException("Errore durante l'aggiornamento del prodotto, righe aggiornate: " + rowsUpdated);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'aggiornamento del prodotto: " + e.getMessage());
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
}
