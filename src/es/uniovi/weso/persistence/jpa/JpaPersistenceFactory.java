package es.uniovi.weso.persistence.jpa;

import es.uniovi.weso.business.CommandExecutor;
import es.uniovi.weso.business.CommandExecutorFactory;
import es.uniovi.weso.persistence.PersistenceFactory;
import es.uniovi.weso.persistence.dao.UserDAO;





/**
 * Returns JPA-specific instances of DAOs.
 * 
 * If for a particular DAO there is no additional non-CRUD functionality, we use
 * a nested static class to implement the interface in a generic way. This 
 * allows clean refactoring later on, should the interface implement business 
 * data access methods at some later time. Then, we would externalize the 
 * implementation into its own first-level class.
 */
public class JpaPersistenceFactory implements PersistenceFactory, CommandExecutorFactory {
	
	@Override
	public CommandExecutor getCommandExecutor() {
		return new JpaCommandExecutor();
	}

	
	@Override
	public UserDAO getUserDAO() {
		return new UserDAOJpa();
	}
	


	
}
