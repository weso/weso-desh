package es.uniovi.weso.persistence.jpa;



import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.uniovi.weso.persistence.dao.GenericDAO;
import es.uniovi.weso.persistence.util.PersistenceUtil;


/**
 * Implements the generic CRUD data access operations using JPA APIs.
 * <p>
 * To write a DAO, subclass and parameterize this class with your persistent class.
 * Of course, assuming that you have a traditional 1:1 approach for Entity:DAO design.
 * <p>
 * You have to inject a current JPA <tt>EntityManager</tt> to use a DAO. 
 * Otherwise, this generic implementation will use 
 * <tt>PersistentUtil.getCurrentEntotyManager()</tt> to obtain the current <tt>EntityManager</tt>.
 *
 * @see JpaPersistenceFactory
 *
 * @author Christian Bauer
 * @author adapted for JPA JSE by Alberto M.F.A.
 */
public abstract class GenericDAOJpa<T, ID extends Serializable>
        implements GenericDAO<T, ID> {

    private Class<T> persistentClass;
    private EntityManager em;
    protected final Log log  = LogFactory.getLog(getClass());
	
    
    @SuppressWarnings("unchecked")
	public GenericDAOJpa() {
        this.persistentClass = (Class<T>) 
        	((ParameterizedType) getClass().getGenericSuperclass())
        			.getActualTypeArguments()[0];
     }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    protected EntityManager getEntityManager() {
        if (em == null){
            em = PersistenceUtil.getCurrentEntityManager();
        }
        return em;
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    public T findById(ID id, boolean lock) {
        T entity;
        entity = (T) getEntityManager().find(getPersistentClass(), id);
        if (lock){
            getEntityManager().lock(entity, LockModeType.WRITE);
        }
        return entity;
    }

    public void persist(T entity) {
        getEntityManager().persist(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(entity);
    }

	@Override
	public T merge(T entity) {
		return getEntityManager().merge(entity);
	}

	@Override
	public void refresh(T entity) {
		getEntityManager().refresh(entity);
	}

    public void flush() {
        getEntityManager().flush();
    }

    public void clear() {
        getEntityManager().clear();
    }
    
    public List<T> findAll(){
    	String queryString = "select x from " 
    		+ persistentClass.getSimpleName() 
    		+ " x";
    	return find(queryString);
    }

	@SuppressWarnings("unchecked")
	public List<T> find(String queryString) {
		return (List<T>)getEntityManager()
			.createQuery(queryString)
			.getResultList();
	}

	public List<T> find(String queryString, Object... values) {
		Query qry = getEntityManager().createQuery(queryString);
		return executeOrdinalParamsQuery(qry, values);
	}

	public List<T> findByNamedParams(String queryString,
			Map<String, ? extends Object> params) {
		Query qry = getEntityManager().createQuery(queryString);
		return executeNominalParamsQuery(qry, params);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(String queryName) {
		return (List<T>)getEntityManager()
			.createNamedQuery(queryName)
			.getResultList();
	}

	public List<T> findByNamedQuery(String queryName, Object... values) {
		Query qry = getEntityManager().createNamedQuery(queryName);
		return executeOrdinalParamsQuery(qry, values);
	}

	@Override
	public List<T> findByNamedQueryAndNamedParams(String queryName,
			Map<String, ? extends Object> params) {
		Query qry = getEntityManager().createNamedQuery(queryName);
		return executeNominalParamsQuery(qry, params);
	}

	@SuppressWarnings("unchecked")
	private List<T> executeOrdinalParamsQuery(Query qry, Object... values) {
		int i = 1;
		for(Object arg: values){
			qry.setParameter(i++, arg);
		}
		return (List<T>) qry.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<T> executeNominalParamsQuery(Query qry, 
			Map<String, ? extends Object> params) {
		for(String name: params.keySet()){
			qry.setParameter(name, params.get(name));
		}
		return (List<T>) qry.getResultList();
	}

}

