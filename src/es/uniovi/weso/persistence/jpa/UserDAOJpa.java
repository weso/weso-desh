package es.uniovi.weso.persistence.jpa;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.Query;

import es.uniovi.weso.model.User;
import es.uniovi.weso.persistence.dao.UserDAO;

public class UserDAOJpa extends GenericDAOJpa<User, Long> implements UserDAO {



	@Override
	public User getUser(String username, String password) {
		User user = null;
		try {
			Query jpaQuery = getEntityManager().createNamedQuery(
					"findLoginUser");
			jpaQuery.setParameter("username", username);
			jpaQuery.setParameter("password", password);
			user = (User) jpaQuery.getSingleResult();
		} catch (Exception e) {
			log.debug("UserDAOJpa->getUser:" + e.toString());			
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isUser(String username) {
		Set<User> users = new HashSet<User>();
		try {
			Query jpaQuery = getEntityManager().createNamedQuery("isUser");
			jpaQuery.setParameter("username", username);
			users.addAll(jpaQuery.getResultList());
		} catch (Exception e) {
			log.debug("UserDAOJpa->isUser:" + e);	
		}
		return (users.size()>0);
	}


	
}
