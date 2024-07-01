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
		String insertSQL = "INSERT INTO prodotto (nome, descrizione, costo, sesso, immagine, categoria) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, prodotto.getNome());
			statement.setString(2, prodotto.getDescrizione());
			statement.setDouble(3, prodotto.getCosto());
			statement.setString(4, String.valueOf(prodotto.getSesso()));
			statement.setString(5, prodotto.getImmagine());
			statement.setString(6, prodotto.getCategoria());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 1) {
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						prodotto.setCodice(generatedKeys.getInt(1));
					}
				}
			} else {
				throw new SQLException("Errore durante l'inserimento di Prodotto, righe aggiornate: " + rowsAffected);
			}
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'inserimento di Prodotto: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean doDelete(Integer codice) throws SQLException {
		String updateSQL = "UPDATE " + NOME_TABELLA + " SET isDeleted = ? WHERE codice = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DriverManagerConnectionPool.getConnection();
			statement = connection.prepareStatement(updateSQL);

			statement.setBoolean(1, true);
			statement.setInt(2, codice);

			int rowsUpdated = statement.executeUpdate();
			connection.commit();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			if (connection != null) {
				connection.rollback();
			}
			System.out.println("Errore SQL durante l'eliminazione di Prodotto: " + e.getMessage());
			throw e;
		} finally {
			closeResources(connection, statement, null);
		}
	}

	public Prodotto doRetrieveByKey(Integer codice) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE codice = ? AND isDeleted = FALSE";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(selectSQL)) {

			statement.setInt(1, codice);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Prodotto prodotto = new Prodotto();
					prodotto.setCodice(resultSet.getInt("codice"));
					prodotto.setNome(resultSet.getString("nome"));
					prodotto.setDescrizione(resultSet.getString("descrizione"));
					prodotto.setCosto(resultSet.getDouble("costo"));
					prodotto.setSesso(resultSet.getString("sesso").charAt(0));
					prodotto.setImmagine(resultSet.getString("immagine"));
					prodotto.setCategoria(resultSet.getString("categoria"));
					prodotto.setIsDeleted(resultSet.getBoolean("isDeleted"));
					return prodotto;
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di Prodotto: " + e.getMessage());
			throw e;
		}
		return null;
	}

	@Override
	public List<Prodotto> doRetrieveAll(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE isDeleted = FALSE";
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

	public void eliminaLogicamente(int codice) throws SQLException {
		String updateSQL = "UPDATE prodotto SET isDeleted = TRUE WHERE codice = ?";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(updateSQL)) {
			statement.setInt(1, codice);
			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException("Errore durante l'eliminazione logica del prodotto");
			}
			connection.commit();
		} catch (SQLException e) {
			System.out.println("Errore SQL durante l'eliminazione logica di Prodotto: " + e.getMessage());
			throw e;
		}
	}

	public List<Prodotto> doRetrieveAllNonDeleted(String order) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE isDeleted = FALSE";
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
				prodotto.setIsDeleted(resultSet.getBoolean("isDeleted"));
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

	public Prodotto doRetrieveByKeyEvenIfDeleted(Integer codice) throws SQLException {
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE codice = ?";
		try (Connection connection = DriverManagerConnectionPool.getConnection();
				PreparedStatement statement = connection.prepareStatement(selectSQL)) {

			statement.setInt(1, codice);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Prodotto prodotto = new Prodotto();
					prodotto.setCodice(resultSet.getInt("codice"));
					prodotto.setNome(resultSet.getString("nome"));
					prodotto.setDescrizione(resultSet.getString("descrizione"));
					prodotto.setCosto(resultSet.getDouble("costo"));
					prodotto.setSesso(resultSet.getString("sesso").charAt(0));
					prodotto.setImmagine(resultSet.getString("immagine"));
					prodotto.setCategoria(resultSet.getString("categoria"));
					prodotto.setIsDeleted(resultSet.getBoolean("isDeleted"));
					return prodotto;
				}
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero di Prodotto: " + e.getMessage());
			throw e;
		}
		return null;
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
		String selectSQL = "SELECT * FROM " + NOME_TABELLA + " WHERE categoria = ? AND isDeleted = FALSE";
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

	public Collection<Prodotto> getProdottiBySessoNonDeleted(char sesso) throws SQLException {
		String selectSQL = "SELECT * FROM prodotto WHERE sesso = ? AND isDeleted = FALSE";
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
				prodotto.setIsDeleted(resultSet.getBoolean("isDeleted"));
				prodotti.add(prodotto);
			}
		} catch (SQLException e) {
			System.out.println("Errore SQL durante il recupero dei prodotti per sesso: " + e.getMessage());
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

	public List<Prodotto> getPrimiSeiProdottiBySessoNonDeleted(char sesso) throws SQLException {
		String selectSQL = "SELECT * FROM prodotto WHERE sesso = ? AND isDeleted = FALSE LIMIT 6";
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
				prodotto.setIsDeleted(resultSet.getBoolean("isDeleted"));
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
			String query = "SELECT * FROM prodotto WHERE nome LIKE ? AND isDeleted = FALSE";
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
						prodotto.setIsDeleted(rs.getBoolean("isDeleted"));
						prodotti.add(prodotto);
					}
				}
			}
		} catch (SQLException e) {
		}
		return prodotti;
	}
}
