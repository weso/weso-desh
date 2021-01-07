package es.uniovi.weso.persistence.dao;


import java.util.List;
import java.util.Map;
import java.io.Serializable;

/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * 
 * The current design is for a state-management oriented persistence layer (for
 * example, there is no UDPATE statement function) that provides automatic
 * transactional dirty checking of business objects in persistent state.
 * 
 * @author Christian Bauer
 */
public interface GenericDAO<T, ID extends Serializable> {

	// Basic CRUD operations
	T findById(ID id, boolean lock);

	List<T> findAll();

	void persist(T entity);

	T merge(T entity);

	void remove(T entity);

	void refresh(T entity);

	// Utility methods for Queries
	List<T> find(String queryString);

	List<T> find(String queryString, Object... values);

	List<T> findByNamedParams(String queryString,
			Map<String, ? extends Object> params);

	List<T> findByNamedQuery(String queryName);

	List<T> findByNamedQuery(String queryName, Object... values);

	List<T> findByNamedQueryAndNamedParams(String queryName,
			Map<String, ? extends Object> params);

	/**
	 * Affects every managed instance in the current persistence context!
	 */
	void flush();

	/**
	 * Affects every managed instance in the current persistence context!
	 */
	void clear();
}
