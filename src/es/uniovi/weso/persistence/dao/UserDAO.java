package es.uniovi.weso.persistence.dao;


import es.uniovi.weso.model.User;

public interface UserDAO extends GenericDAO<User, Long> {

	public User getUser(String username, String password);
	
	public boolean isUser(String username);
	

}
