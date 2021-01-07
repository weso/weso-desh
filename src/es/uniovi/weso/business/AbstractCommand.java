package es.uniovi.weso.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.uniovi.weso.persistence.PersistenceFactory;


public abstract class AbstractCommand implements Command {
	
	protected PersistenceFactory daoFactory;
	private transient final Log LOG  = LogFactory.getLog(getClass());
	
	protected AbstractCommand(){
		super();
	}
	
	public void setDAOFactory(PersistenceFactory factory) {
		daoFactory = factory;
	}
    

}
