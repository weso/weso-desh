package es.uniovi.weso.persistence;



/**
 * Interfaz de la factoria que suministra implementaciones reales de la fachada
 * de persistencia para cada Entidad del Modelo
 *
 * @author Francisco
 *
 */
import es.uniovi.weso.persistence.dao.UserDAO;

public interface PersistenceFactory {
	UserDAO getUserDAO();
//	MessageDAO getMessageDAO();
//	WallPostDAO getWallPostDAO();
//	NotificationDAO getNotificationDAO();   
}
