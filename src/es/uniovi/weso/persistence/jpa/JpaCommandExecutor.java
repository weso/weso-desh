package es.uniovi.weso.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.uniovi.weso.business.BusinessException;
import es.uniovi.weso.business.Command;
import es.uniovi.weso.business.CommandExecutor;
import es.uniovi.weso.infraestructure.Factories;
import es.uniovi.weso.persistence.util.PersistenceUtil;




/**
 * CommandExecutor for JPA, if other persistence layer you need to change
 * things here. This executor is responsible for:
 * 		- Get and close a current EntityManager (EM per Thread) 
 * 			or connection if JDBC
 *      - Manage transaction 
 *      
 *      Si se necesitase cambiar la persistencia esta es la clase a tocar 
 * 
 * @author alb
 */
public class JpaCommandExecutor implements CommandExecutor {
	//private static Log log = LogFactory.getLog(JpaCommandExecutor.class);
	private final Log log  = LogFactory.getLog(getClass());

	public Object execute(Command c) throws BusinessException {
		Object res = null;
		// Note getCurrentEntityManager() instead of getEntityManager()
		try{
		EntityManager em = PersistenceUtil.getCurrentEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();

		try {
			c.setDAOFactory(Factories.get().getPersistence());
			res = c.execute();

			tx.commit();
		} catch (BusinessException bex){
			rollback(c, tx, bex);
			throw bex;
		} catch (RuntimeException rex) {
			rex.printStackTrace();
			rollback(c, tx, rex);
			throw rex;
		} finally {
			if (em.isOpen()){
				em.close();
			}
		}
		} catch(Exception e){
			log.debug("JpaCommandExecutor:"+e.toString());
		}
		return res;
	}

	private void rollback(Command c, EntityTransaction tx, Exception ex) {
		try {
			if (tx.isActive()) {
				tx.rollback();
			}
			log.debug("rollback by exception in " + 
						c.getClass().getName(),	ex);
		} catch (RuntimeException rbEx) {
			log.error("Could not rollback transaction in " + 
						c.getClass().getName(), rbEx);
		}
	}


	
}
