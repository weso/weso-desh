package es.uniovi.weso.infraestructure;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.uniovi.weso.business.CommandExecutorFactory;
import es.uniovi.weso.business.ServicesFactory;
import es.uniovi.weso.persistence.PersistenceFactory;


/**
 * 
 * Factory Singleton especializado para utilizar spring
 * 
 * @author Francisco Cifuentes
 * 
 */
public final class Factories {
	
	private static ServicesFactory services;
	private static PersistenceFactory persistence;
	private static CommandExecutorFactory executor;
	private final Log log  = LogFactory.getLog(getClass());
	
    private static Factories instance;
	
	public static Factories get() {
		if (instance == null) {
			instance = new Factories();
		}
		return instance;
	}

	private Factories() {
		super();
	}

	public ServicesFactory getServices() {
		return services;
	}

	public void setServices(ServicesFactory services) {
		Factories.services = services;
	}

	public PersistenceFactory getPersistence() {
		return persistence;
	}

	public void setPersistence(PersistenceFactory persistence) {
		Factories.persistence = persistence;
	}

	public CommandExecutorFactory getExecutor() {
		return executor;
	}

	public void setExecutor(CommandExecutorFactory executor) {
		Factories.executor = executor;
	}


	
}
