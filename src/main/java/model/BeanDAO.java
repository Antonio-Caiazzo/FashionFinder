package model;

import java.sql.SQLException;
import java.util.Collection;

public interface BeanDAO<T, K> {

	void doSave(T data) throws SQLException;

	boolean doDelete(K key) throws SQLException;

	T doRetrieveByKey(K key) throws SQLException;

	Collection<T> doRetrieveAll(String order) throws SQLException;
}
