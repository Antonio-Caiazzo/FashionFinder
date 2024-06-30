package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProdottoDAO implements BeanDAO<Prodotto, Integer> {

	private static final String NOME_TABELLA = "prodotto";

	public ProdottoDAO() {
	}

	@Override
	public void doSave(Prodotto prodotto) throws SQLException {
		String insertSQL = "INSERT INTO " + NOME_TABELLA
				+ " (nome, descrizione, costo, sesso, immagine, categoria) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManagerConnectionPool.getConnection();
			statement = connection.prepareStatement(insertSQL);

			statement.setString(1, prodotto.getNome());
			statement.setString(2, prodotto.getDescrizione());
			statement.setDouble(3, prodotto.getCosto());
			statement.setString(4, String.valueOf(prodotto.getSesso()));
			statement.setString(5, prodotto.getImmagine());
			statement.setString(6, prodotto.getCategoria());

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
			connection = DriverManagerConnectionPool.getConnection();
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
			connection = DriverManagerConnectionPool.getConnection();
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
	public List<Prodotto> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA;
		if (order != null && !order.isEmpty()) {
			selectSQL += " ORDER BY " + order;
		}

		List<Prodotto> prodotti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManagerConnectionPool.getConnection();
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
			connection = DriverManagerConnectionPool.getConnection();
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
			connection = DriverManagerConnectionPool.getConnection();
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

	public Collection<Prodotto> getProdottiBySesso(char sesso) throws SQLException {
		String selectSQL = "SELECT * FROM prodotto WHERE sesso = ?";
		List<Prodotto> prodotti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManagerConnectionPool.getConnection();
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, String.valueOf(sesso));
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
			System.out.println("Errore SQL durante il recupero dei prodotti per sesso: " + e.getMessage());
			throw e;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (statement != null)
					statement.close();
				if (connection != null)
					DriverManagerConnectionPool.releaseConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return prodotti;
	}

	public Collection<Prodotto> getPrimiSeiProdottiBySesso(char sesso) throws SQLException {
		String selectSQL = "SELECT * FROM prodotto WHERE sesso = ? LIMIT 6";
		List<Prodotto> prodotti = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManagerConnectionPool.getConnection();
			statement = connection.prepareStatement(selectSQL);
			statement.setString(1, String.valueOf(sesso));
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
			System.out.println("Errore SQL durante il recupero dei primi sei prodotti per sesso: " + e.getMessage());
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
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement ps = connection.prepareStatement(updateSQL)) {
			ps.setString(1, prodotto.getNome());
			ps.setString(2, prodotto.getDescrizione());
			ps.setDouble(3, prodotto.getCosto());
			ps.setString(4, String.valueOf(prodotto.getSesso()));
			ps.setString(5, prodotto.getImmagine());
			ps.setString(6, prodotto.getCategoria());
			ps.setInt(7, prodotto.getCodice());
			ps.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			throw new SQLException("Errore durante l'aggiornamento del prodotto: " + e.getMessage());
		}
	}

	public List<Prodotto> searchProductsByName(String name) {
		List<Prodotto> prodotti = new ArrayList<>();
		try (Connection connection = DriverManagerConnectionPool.getConnection()) {
			String query = "SELECT * FROM prodotto WHERE nome LIKE ?";
			try (PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, "%" + name + "%");
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						Prodotto prodotto = new Prodotto();
						prodotto.setCodice(rs.getInt("codice"));
						prodotto.setNome(rs.getString("nome"));
						prodotto.setDescrizione(rs.getString("descrizione"));
						prodotto.setCosto(rs.getDouble("costo"));
						prodotto.setSesso(rs.getString("sesso").charAt(0));
						prodotto.setImmagine(rs.getString("immagine"));
						prodotto.setCategoria(rs.getString("categoria"));
						prodotti.add(prodotto);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prodotti;
	}
}
