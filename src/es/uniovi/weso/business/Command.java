package es.uniovi.weso.business;

import es.uniovi.weso.persistence.PersistenceFactory;


/**
 * Interfaz para todos los comandos
 * 
 * @author alb
 */
public interface Command {
	/**
	 * Recibe la factoria a emplear para obtener los DAOs. Se la establece el 
	 * CommandExecutor antes de invocar al m�todo execute()
	 * 
	 * @param factory
	 */
	void setDAOFactory(PersistenceFactory factory);
	Object execute() throws BusinessException;
}
